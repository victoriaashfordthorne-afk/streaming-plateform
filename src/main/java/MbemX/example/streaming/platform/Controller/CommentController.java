package MbemX.example.streaming.platform.Controller;



import MbemX.example.streaming.platform.Dto.CommentDto;
import MbemX.example.streaming.platform.Services.CommentServices;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/fichiers")
@Tag(
        name = "Comments",
        description = "Manage comments on media files"
)
public class CommentController {

    private final CommentServices commentServices;

    public CommentController(CommentServices commentServices) {
        this.commentServices = commentServices;
    }


    // =========================================================
    // 1. ADD A COMMENT TO A FILE
    // POST /api/fichiers/{id}/commentaires
    // =========================================================

    @PostMapping("/{id}/commentaires")
    @Operation(
            summary = "Add a comment",
            description = "Add a comment to a specific media file"
    )
    public ResponseEntity<CommentDto> saveComment(
            @PathVariable Long id,
            @RequestBody CommentDto dto) {

        /*
         * The file ID comes from the URL.
         * Therefore we use it instead of trusting dto.fileId().
         */
        CommentDto commentDto = new CommentDto(
                dto.id(),
                dto.content(),
                dto.dateComment(),
                dto.userId(),
                id
        );

        CommentDto savedComment =
                commentServices.saveComment(commentDto);

        return ResponseEntity.ok(savedComment);
    }


    // =========================================================
    // 2. GET COMMENTS OF A FILE
    // GET /api/fichiers/{id}/commentaires
    // =========================================================

    @GetMapping("/{id}/commentaires")
    @Operation(
            summary = "Get file comments",
            description = "Get all comments belonging to a specific media file"
    )
    public ResponseEntity<List<CommentDto>> findByFileId(
            @PathVariable Long id) {

        List<CommentDto> comments =
                commentServices.findByFileId(id);

        return ResponseEntity.ok(comments);
    }
}
