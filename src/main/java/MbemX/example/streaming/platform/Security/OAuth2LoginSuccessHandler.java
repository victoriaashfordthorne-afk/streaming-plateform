package MbemX.example.streaming.platform.Security;

import MbemX.example.streaming.platform.Entity.User;
import MbemX.example.streaming.platform.Enums.ConnectionMethod;
import MbemX.example.streaming.platform.Repository.UserRepository;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;

@Component
public class OAuth2LoginSuccessHandler implements AuthenticationSuccessHandler {

    private final UserRepository userRepository;

    public OAuth2LoginSuccessHandler(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication) throws IOException, ServletException {

        OAuth2User oauth2User = (OAuth2User) authentication.getPrincipal();

        String email = oauth2User.getAttribute("email");
        String name = oauth2User.getAttribute("name");

        User user = userRepository.findByEmail(email)
                .orElseGet(() -> {

                    User newUser = new User();

                    newUser.setName(name);
                    newUser.setEmail(email);
                    newUser.setPassword(null);
                    newUser.setConnectionMethod(ConnectionMethod.GOOGLE);
                    newUser.setActiveAccount(true);
                    newUser.setDateCreation(LocalDateTime.now());

                    return newUser;
                });

        user.setLastConnection(LocalDateTime.now());

        userRepository.save(user);

        response.sendRedirect("/");
    }
}
