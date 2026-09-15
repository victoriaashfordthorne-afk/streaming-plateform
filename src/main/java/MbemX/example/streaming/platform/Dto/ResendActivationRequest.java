package MbemX.example.streaming.platform.Dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Request used to resend an activation code")
public record ResendActivationRequest(

        @Schema(
                description = "User's email address",
                example = "john@gmail.com"
        )
        String email
) {
}
