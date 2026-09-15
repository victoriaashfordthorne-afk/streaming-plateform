package MbemX.example.streaming.platform.Dto;

import MbemX.example.streaming.platform.Enums.EmailStatus;
import MbemX.example.streaming.platform.Enums.EmailType;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

@Schema(description = "History of an email sent by the application")
public record EmailHistoryDto(

        @Schema(description = "Email history identifier", example = "1")
        Long id,

        @Schema(description = "Email recipient", example = "john@example.com")
        String recipient,

        @Schema(description = "Email subject", example = "Account activation")
        String subject,

        @Schema(description = "Type of email", example = "ACTIVATION")
        EmailType type,

        @Schema(description = "Email sending status", example = "SUCCESS")
        EmailStatus status,

        @Schema(description = "Date when the email was sent")
        LocalDateTime dateSent,

        @Schema(description = "ID of the associated user", example = "1")
        Long userId
) {
}
