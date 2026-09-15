package MbemX.example.streaming.platform.Controller;



import MbemX.example.streaming.platform.Dto.ActivationCodeDto;
import MbemX.example.streaming.platform.Services.ActivationCodeServices;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/activation-codes")
@Tag(
        name = "Activation Codes",
        description = "Manage account activation codes"
)
public class ActivationCodeController {

    private final ActivationCodeServices activationCodeServices;

    public ActivationCodeController(
            ActivationCodeServices activationCodeServices) {

        this.activationCodeServices = activationCodeServices;
    }


    // =========================================================
    // 1. GET ALL ACTIVATION CODES
    // GET /api/activation-codes
    // =========================================================

    @GetMapping("/all")
    @Operation(
            summary = "Get all activation codes",
            description = "Returns all activation codes"
    )
    public ResponseEntity<List<ActivationCodeDto>> findAllCode() {

        return ResponseEntity.ok(
                activationCodeServices.findAllCode()
        );
    }


    // =========================================================
    // 2. GET ACTIVATION CODE BY ID
    // GET /api/activation-codes/{id}
    // =========================================================

    @GetMapping("/{id}")
    @Operation(
            summary = "Get activation code by ID",
            description = "Returns one activation code"
    )
    public ResponseEntity<ActivationCodeDto> findById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                activationCodeServices.findById(id)
        );
    }


    // =========================================================
    // 3. GET ACTIVATION CODE BY CODE
    // GET /api/activation-codes/code/{code}
    // =========================================================

    @GetMapping("/code/{code}")
    @Operation(
            summary = "Find activation code",
            description = "Find an activation code using its code"
    )
    public ResponseEntity<ActivationCodeDto> findByCode(
            @PathVariable String code) {

        return ResponseEntity.ok(
                activationCodeServices.findByCode(code)
        );
    }


    // =========================================================
    // 4. CREATE ACTIVATION CODE
    // POST /api/activation-codes
    // =========================================================

    @PostMapping("/add")
    @Operation(
            summary = "Create activation code",
            description = "Creates an activation code for a user"
    )
    public ResponseEntity<ActivationCodeDto> saveActivationCode(
            @RequestBody ActivationCodeDto dto) {

        return ResponseEntity.ok(
                activationCodeServices.saveActivationCode(dto)
        );
    }


    // =========================================================
    // 5. UPDATE ACTIVATION CODE
    // PUT /api/activation-codes/{id}
    // =========================================================

    @PutMapping("/{id}")
    @Operation(
            summary = "Update activation code",
            description = "Updates an existing activation code"
    )
    public ResponseEntity<ActivationCodeDto> updateCode(
            @PathVariable Long id,
            @RequestBody ActivationCodeDto dto) {

        return ResponseEntity.ok(
                activationCodeServices.updateCode(id, dto)
        );
    }


    // =========================================================
    // 6. DELETE ACTIVATION CODE
    // DELETE /api/activation-codes/{id}
    // =========================================================

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Delete activation code",
            description = "Deletes an activation code"
    )
    public ResponseEntity<Void> deleteCode(
            @PathVariable Long id) {

        activationCodeServices.deleteCode(id);

        return ResponseEntity.noContent().build();
    }
}
