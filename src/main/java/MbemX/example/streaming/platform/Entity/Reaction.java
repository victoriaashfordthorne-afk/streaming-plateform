package MbemX.example.streaming.platform.Entity;

import MbemX.example.streaming.platform.Enums.ReactionType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(
        name = "reactions",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_reaction_user_file",
                        columnNames = {"user_id", "file_id"}
                )
        }
)
public class Reaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private ReactionType type;
    @Column(nullable = false)
    private LocalDateTime dateReaction;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "file_id", nullable = false)
    private File file;

}
