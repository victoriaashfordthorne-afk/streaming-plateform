package MbemX.example.streaming.platform.Controller;

import MbemX.example.streaming.platform.Dto.ReactionDto;
import MbemX.example.streaming.platform.Services.ReactionServices;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reactions")
@Tag(
        name = "Reactions",
        description = "Endpoints for managing reactions"
)
public class ReactionController {

    private final ReactionServices reactionServices;

    public ReactionController(ReactionServices reactionServices) {
        this.reactionServices = reactionServices;
    }

    @GetMapping
    @Operation(
            summary = "Get all reactions",
            description = "Returns all reactions"
    )
    public ResponseEntity<List<ReactionDto>> findAllReaction() {

        return ResponseEntity.ok(
                reactionServices.findAllReaction()
        );
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Get a reaction by ID",
            description = "Returns a reaction using its ID"
    )
    public ResponseEntity<ReactionDto> findById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                reactionServices.findById(id)
        );
    }

    @GetMapping("/user/{userId}/file/{fileId}")
    @Operation(
            summary = "Get a user's reaction on a file",
            description = "Returns the reaction of a specific user on a specific file"
    )
    public ResponseEntity<ReactionDto> findByUserIdAndFileId(
            @PathVariable Long userId,
            @PathVariable Long fileId) {

        return ResponseEntity.ok(
                reactionServices.findByUserIdAndFileId(
                        userId,
                        fileId
                )
        );
    }

    @PostMapping("/add")
    @Operation(
            summary = "Add or change a reaction",
            description = "Adds a like/dislike or changes the user's existing reaction"
    )
    public ResponseEntity<ReactionDto> saveReaction(
            @RequestBody ReactionDto dto) {

        ReactionDto savedReaction =
                reactionServices.saveReaction(dto);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(savedReaction);
    }

    @PutMapping("/{id}")
    @Operation(
            summary = "Update a reaction",
            description = "Updates an existing reaction"
    )
    public ResponseEntity<ReactionDto> updateReaction(
            @PathVariable Long id,
            @RequestBody ReactionDto dto) {

        return ResponseEntity.ok(
                reactionServices.updateReaction(dto, id)
        );
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Delete a reaction",
            description = "Deletes a reaction"
    )
    public ResponseEntity<Void> deleteReaction(
            @PathVariable Long id) {

        reactionServices.deleteReaction(id);

        return ResponseEntity.noContent().build();
    }
}