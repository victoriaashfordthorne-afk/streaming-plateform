package MbemX.example.streaming.platform.Dto;

import io.swagger.v3.oas.annotations.media.Schema;
@Schema(description = "Request used to create a new account")
public record RegisterRequest(

        @Schema(
                description = "User's full name",
                example = "John Doe"
        )
        String name,

        @Schema(
                description = "User's email address",
                example = "john@gmail.com"
        )
        String email,

        @Schema(
                description = "User's password",
                example = "Password123!"
        )
        String password
) {
}
