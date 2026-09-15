package MbemX.example.streaming.platform.Services;

import MbemX.example.streaming.platform.Dto.ActivationRequest;
import MbemX.example.streaming.platform.Dto.RegisterRequest;
import MbemX.example.streaming.platform.Dto.ResendActivationRequest;
import MbemX.example.streaming.platform.Entity.ActivationCode;
import MbemX.example.streaming.platform.Entity.User;
import MbemX.example.streaming.platform.Enums.ConnectionMethod;
import MbemX.example.streaming.platform.Exception.ResourceNotFoundException;
import MbemX.example.streaming.platform.Repository.ActivationCodeRepository;
import MbemX.example.streaming.platform.Repository.UserRepository;
import MbemX.example.streaming.platform.Dto.LoginRequest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import MbemX.example.streaming.platform.Services.EmailServices;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Random;

@Service
public class AuthenticationService {

    private final UserRepository userRepository;
    private final ActivationCodeRepository activationCodeRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailServices emailServices;
    private final AuthenticationManager authenticationManager;

    public AuthenticationService(
            UserRepository userRepository,
            ActivationCodeRepository activationCodeRepository,
            PasswordEncoder passwordEncoder,
            EmailServices emailServices,
            AuthenticationManager authenticationManager) {

        this.userRepository = userRepository;
        this.activationCodeRepository = activationCodeRepository;
        this.passwordEncoder = passwordEncoder;
        this.emailServices = emailServices;
        this.authenticationManager = authenticationManager;
    }

    public String register(RegisterRequest request) {

        if (userRepository.existsByEmail(request.email())) {
            throw new IllegalArgumentException(
                    "An account with this email already exists"
            );
        }

        User user = new User();

        user.setName(request.name());
        user.setEmail(request.email());
        user.setPassword(
                passwordEncoder.encode(request.password())
        );
        user.setConnectionMethod(ConnectionMethod.EMAIL);
        user.setActiveAccount(false);
        user.setDateCreation(LocalDateTime.now());

        User savedUser = userRepository.save(user);

        String code = generateActivationCode();

        ActivationCode activationCode = new ActivationCode();

        activationCode.setCode(code);
        activationCode.setDateGeneration(LocalDateTime.now());
        activationCode.setExpirationDate(
                LocalDateTime.now().plusMinutes(15)
        );
        activationCode.setUsed(false);
        activationCode.setUser(savedUser);

        activationCodeRepository.save(activationCode);

        emailServices.sendActivationEmail(savedUser, code);

        return "Account created successfully. Check your email for the activation code.";
    }

    public String activate(ActivationRequest request) {

        User user = userRepository.findByEmail(request.email())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "User with email " + request.email() + " not found"
                        )
                );

        ActivationCode activationCode =
                activationCodeRepository.findByCode(request.code())
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Invalid activation code"
                                )
                        );

        if (!activationCode.getUser().getId().equals(user.getId())) {
            throw new IllegalArgumentException(
                    "Activation code does not belong to this account"
            );
        }

        if (activationCode.getUsed()) {
            throw new IllegalArgumentException(
                    "This activation code has already been used"
            );
        }

        if (LocalDateTime.now()
                .isAfter(activationCode.getExpirationDate())) {

            throw new IllegalArgumentException(
                    "This activation code has expired"
            );
        }

        user.setActiveAccount(true);
        userRepository.save(user);

        activationCode.setUsed(true);
        activationCodeRepository.save(activationCode);

        return "Account activated successfully. You can now log in.";
    }

    public String login(LoginRequest request) {

        var authentication =
                authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(
                                request.email(),
                                request.password()
                        )
                );

        SecurityContextHolder.getContext()
                .setAuthentication(authentication);

        return "Login successful";
    }

    public String resendActivationCode(
            ResendActivationRequest request) {

        User user = userRepository.findByEmail(request.email())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "User with email " + request.email() + " not found"
                        )
                );

        if (Boolean.TRUE.equals(user.getActiveAccount())) {
            throw new IllegalArgumentException(
                    "This account is already activated"
            );
        }

        String code = generateActivationCode();

        ActivationCode activationCode = new ActivationCode();

        activationCode.setCode(code);
        activationCode.setDateGeneration(LocalDateTime.now());
        activationCode.setExpirationDate(
                LocalDateTime.now().plusMinutes(15)
        );
        activationCode.setUsed(false);
        activationCode.setUser(user);

        activationCodeRepository.save(activationCode);

        emailServices.sendActivationEmail(user, code);

        return "A new activation code has been sent to your email.";
    }

    private String generateActivationCode() {

        Random random = new Random();

        int code = 100000 + random.nextInt(900000);

        return String.valueOf(code);
    }
}