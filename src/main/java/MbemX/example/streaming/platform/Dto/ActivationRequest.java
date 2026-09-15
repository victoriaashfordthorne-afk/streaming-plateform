package MbemX.example.streaming.platform.Dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Request used to activate a user account")
public record ActivationRequest(

        @Schema(
                description = "User's email address",
                example = "john@gmail.com"
        )
        String email,

        @Schema(
                description = "Activation code received by email",
                example = "483921"
        )
        String code
) {
}
