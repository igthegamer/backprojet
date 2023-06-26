package work.example.demo.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
@Getter
@Setter
@NoArgsConstructor
@Entity
public class token {
    @SequenceGenerator(
            name="token_sequence",
            sequenceName ="token_sequence",
            allocationSize = 1
    )
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "token_sequence"
    )
    private  Long id;
    @Column(nullable = false)
    private String token;
    @Column(nullable = false)

    private LocalDateTime createdAt;
    @Column(nullable = false)

    private LocalDateTime expiresAt;

    private LocalDateTime confirmedAt;
    @ManyToOne
    @JoinColumn(
            nullable = false,
            name = "user_id"
    )
    private User user;

    public token(String Token,
                 LocalDateTime createdAt,
                 LocalDateTime expiredAt,
                 User user) {
        this.token = Token;
        this.createdAt = createdAt;
        this.expiresAt = expiredAt;
        this.user = user;
    }
}
