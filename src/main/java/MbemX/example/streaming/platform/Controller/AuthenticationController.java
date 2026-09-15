package MbemX.example.streaming.platform.Controller;

import MbemX.example.streaming.platform.Dto.ActivationRequest;
import MbemX.example.streaming.platform.Dto.RegisterRequest;
import MbemX.example.streaming.platform.Dto.ResendActivationRequest;
import MbemX.example.streaming.platform.Services.AuthenticationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import MbemX.example.streaming.platform.Dto.LoginRequest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import java.io.IOException;

@RestController
@RequestMapping("/api/auth")
@Tag(
        name = "Authentication",
        description = "Endpoints for registration, activation, login and logout"
)
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    public AuthenticationController(AuthenticationService authenticationService){
        this.authenticationService = authenticationService;
    }
    @PostMapping("/inscription")
    @Operation(
            summary = "Create a new account",
            description = "Creates an inactive account and sends an activation code by email"
    )
    public ResponseEntity<String> inscription(@RequestBody RegisterRequest request){
        String message = authenticationService.register(request);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(message);
    }

    @PostMapping("/connexion")
    @Operation(
            summary = "Login",
            description = "Authenticates a user using email and password"
    )
    public ResponseEntity<String> connexion(
            @RequestBody LoginRequest request) {

        String message = authenticationService.login(request);

        return ResponseEntity.ok(message);
    }
    @PostMapping("/verification-code")
    @Operation(
            summary = "Verify activation code",
            description = "Activates a user account using the code received by email"
    )

    public ResponseEntity<String> verificationCode(@RequestBody ActivationRequest request){
        String message = authenticationService.activate(request);
        return ResponseEntity
                .ok(message);
    }
    @PostMapping("/renvoi-code")
    @Operation(
            summary = "Resend activation code",
            description = "Generates and sends a new activation code"
    )
    public ResponseEntity<String> renvoiCode(@RequestBody ResendActivationRequest request){
        String message = authenticationService.resendActivationCode(request);
        return ResponseEntity.ok(message);
    }

    @PostMapping("/deconnexion")
    @Operation(
            summary = "Logout",
            description = "Disconnects the currently authenticated user"
    )
    public ResponseEntity<String> deconnexion(){
        SecurityContextHolder.clearContext();
        return ResponseEntity.ok("Deconnexion reussie");
    }
    @GetMapping("/connexion-google")
    @Operation(
            summary = "Start Google authentication",
            description = "Redirects the user to Google for authentication"
    )
    public void connexionGoogle(
            HttpServletResponse response) throws IOException {

        response.sendRedirect("/oauth2/authorization/google");
    }
    @GetMapping("/callback-google")
    @Operation(
            summary = "Google authentication callback",
            description = "Receives the result of the Google authentication"
    )
    public ResponseEntity<String> callbackGoogle(
            Authentication authentication) {

        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body("Google authentication failed");
        }

        return ResponseEntity.ok(
                "Google authentication successful"
        );
    }
}
