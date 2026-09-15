package MbemX.example.streaming.platform.Dto;



import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Request used to authenticate a user")
public record LoginRequest(

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
