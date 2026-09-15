package MbemX.example.streaming.platform.Dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

@Schema(description = "Comment made on a media file")
public record CommentDto(

        @Schema(description = "Comment identifier", example = "1")
        Long id,

        @Schema(description = "Comment content", example = "This video is amazing!")
        String content,

        @Schema(description = "Date when the comment was created")
        LocalDateTime dateComment,

        @Schema(description = "ID of the user who made the comment", example = "1")
        Long userId,

        @Schema(description = "ID of the media file being commented on", example = "5")
        Long fileId
) {
}
