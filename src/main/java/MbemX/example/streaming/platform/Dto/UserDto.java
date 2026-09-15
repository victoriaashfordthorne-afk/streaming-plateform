package MbemX.example.streaming.platform.Dto;

import MbemX.example.streaming.platform.Enums.ConnectionMethod;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;


@Schema(description = "User information")
public record UserDto(

        @Schema(description = "User identifier", example = "1")
        Long id,

        @Schema(description = "User name", example = "John Doe")
        String name,

        @Schema(description = "Unique user email", example = "john@example.com")
        String email,

        @Schema(description = "Connection method", example = "EMAIL")
        ConnectionMethod connectionMethod,

        @Schema(description = "Whether the account is active", example = "true")
        Boolean activeAccount,

        @Schema(description = "Account creation date")
        LocalDateTime dateCreation,

        @Schema(description = "Last connection date")
        LocalDateTime lastConnection
) {
}
