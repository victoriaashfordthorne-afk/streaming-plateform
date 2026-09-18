package MbemX.example.streaming.platform.Controller;

import MbemX.example.streaming.platform.Dto.EmailHistoryDto;
import MbemX.example.streaming.platform.Services.EmailHistoryServices;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/email-histories")
@Tag(
        name = "Email History",
        description = "Manage email sending history"
)
public class EmailHistoryController {

    private final EmailHistoryServices emailHistoryServices;

    public EmailHistoryController(
            EmailHistoryServices emailHistoryServices) {

        this.emailHistoryServices = emailHistoryServices;
    }

    @GetMapping("/all")
    @Operation(
            summary = "Get all email histories",
            description = "Returns all email sending history records"
    )
    public ResponseEntity<List<EmailHistoryDto>> findAll() {

        return ResponseEntity.ok(
                emailHistoryServices.findAll()
        );
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Get email history by ID",
            description = "Returns one email history record"
    )
    public ResponseEntity<EmailHistoryDto> findById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                emailHistoryServices.findById(id)
        );
    }

    @PostMapping("/add")
    @Operation(
            summary = "Create email history",
            description = "Creates an email history record"
    )
    public ResponseEntity<EmailHistoryDto> saveEmailHistory(
            @RequestBody EmailHistoryDto dto) {

        return ResponseEntity.ok(
                emailHistoryServices.saveEmailHistory(dto)
        );
    }

    @PutMapping("/{id}")
    @Operation(
            summary = "Update email history",
            description = "Updates an email history record"
    )
    public ResponseEntity<EmailHistoryDto> updateHistory(
            @PathVariable Long id,
            @RequestBody EmailHistoryDto dto) {

        return ResponseEntity.ok(
                emailHistoryServices.updateHistory(id, dto)
        );
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Delete email history",
            description = "Deletes an email history record"
    )
    public ResponseEntity<Void> deleteHistory(
            @PathVariable Long id) {

        emailHistoryServices.deleteHistory(id);

        return ResponseEntity.noContent().build();
    }
}