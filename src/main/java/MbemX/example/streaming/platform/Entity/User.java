package MbemX.example.streaming.platform.Entity;

import MbemX.example.streaming.platform.Enums.ConnectionMethod;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Table(
        name = "users",
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_user_email", columnNames = "email")
        }
)
@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false, unique = true)
    private String email;
    private String password;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ConnectionMethod connectionMethod;
    @Column(nullable = false)
    private Boolean activeAccount = false;
    @Column(nullable = false)
    private LocalDateTime dateCreation;
    private LocalDateTime lastConnection;

}
