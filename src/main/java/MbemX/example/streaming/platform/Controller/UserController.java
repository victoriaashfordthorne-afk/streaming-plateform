package MbemX.example.streaming.platform.Controller;

import MbemX.example.streaming.platform.Dto.UserDto;
import MbemX.example.streaming.platform.Services.UserServices;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
@Tag(name = "user",
description = "Endpoint for user")

public class UserController {
    private final UserServices userServices;

    public UserController(UserServices userServices){
        this.userServices = userServices;
    }

    @GetMapping("/all")
    @Operation(
            summary = "Get all users",
            description = "Returns the list of all users"
    )
    public ResponseEntity<List<UserDto>> findAllUser() {
        return ResponseEntity.ok(userServices.getAll());
    }
    @GetMapping("/{id}")
    @Operation(
            summary = "Get a user by ID",
            description = "Returns a user using their ID"
    )
    public ResponseEntity<UserDto> findAll(@PathVariable Long id){
        return ResponseEntity.ok(userServices.findById(id));
    }
    @GetMapping("email/{email}")
    @Operation(
            summary = "Get a user by email",
            description = "Returns a user using their email address"
    )
    public ResponseEntity<UserDto> findByEmail(@PathVariable String email){
        return ResponseEntity.ok(userServices.findByEmail(email));
    }

    @PostMapping("/add")
    @Operation(
            summary = "Create a user",
            description = "Creates a new user"
    )
    public ResponseEntity<UserDto> saveUser(@RequestBody UserDto dto){
        UserDto saveUser = userServices.saveUser(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(saveUser);
    }
    @PutMapping("/{id}")
    @Operation(
            summary = "Update a user",
            description = "Updates an existing user"
    )
    public ResponseEntity<UserDto> updateUser(@PathVariable Long id,@RequestBody UserDto dto){
        return ResponseEntity.ok(userServices.update(id,dto));
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Delete a user",
            description = "Deletes a user using their ID"
    )
    public ResponseEntity<Void> deleteUser(@PathVariable Long id){
        userServices.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}
