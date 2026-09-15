package MbemX.example.streaming.platform.Dto;

import MbemX.example.streaming.platform.Enums.MediaType;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

@Schema(description = "Uploaded media file information")
public record FileDto(

        @Schema(description = "File identifier", example = "1")
        Long id,

        @Schema(description = "Original file name", example = "vacation.mp4")
        String originalName,

        @Schema(description = "Unique stored file name", example = "a8f92c31-vacation.mp4")
        String stockName,

        @Schema(description = "Type of media", example = "VIDEO")
        MediaType typeMedia,

        @Schema(description = "MIME type", example = "video/mp4")
        String mimetype,

        @Schema(description = "File size in bytes", example = "5242880")
        Long size,

        @Schema(description = "Path where the file is stored")
        String storagePath,

        @Schema(description = "Date when the file was uploaded")
        LocalDateTime dateUpload,

        @Schema(description = "ID of the user who owns the file", example = "1")
        Long userId
) {
}
