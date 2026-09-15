package MbemX.example.streaming.platform.Dto;

import MbemX.example.streaming.platform.Enums.ReactionType;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

@Schema(description = "Reaction made on a media file")
public record ReactionDto(

        @Schema(description = "Reaction identifier", example = "1")
        Long id,

        @Schema(description = "Reaction type", example = "LIKES")
        ReactionType type,

        @Schema(description = "Date when the reaction was created")
        LocalDateTime dateReaction,

        @Schema(description = "ID of the user who reacted", example = "1")
        Long userId,

        @Schema(description = "ID of the media file", example = "5")
        Long fileId
) {
}