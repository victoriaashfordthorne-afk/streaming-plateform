package MbemX.example.streaming.platform.Dto;


import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

@Schema(description = "Account activation code information")
public record ActivationCodeDto(

        @Schema(description = "Activation code identifier", example = "1")
        Long id,

        @Schema(description = "4 to 6 digit activation code", example = "4821")
        String code,

        @Schema(description = "Date when the code was generated")
        LocalDateTime dateGeneration,

        @Schema(description = "Date when the code expires")
        LocalDateTime expirationDate,

        @Schema(description = "Whether the code has already been used", example = "false")
        Boolean used,

        @Schema(description = "ID of the user associated with this code", example = "1")
        Long userId
) {
}