package backend.backend.user.entity;

import backend.backend.comment.entity.Comment;
import backend.backend.common.domain.BaseEntity;
import backend.backend.board.entity.Board;
import backend.backend.reservation.entity.Reservation;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import java.util.List;

@Getter
@Builder
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class User extends BaseEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;
    private String name;

    @Column(unique = true)
    @Email
    private String email;

    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Enumerated(EnumType.STRING)
    private Affiliation affiliation;

//    @OneToMany(mappedBy = "user")
//    private List<Board> boards;
//
//    @OneToMany(mappedBy = "user")
//    private List<Comment> comments;
//
//    @OneToMany(mappedBy = "user")
//    private List<Reservation> reservation;

    @ColumnDefault("0")
    private int reservationCount;

    @Enumerated(EnumType.STRING)
    private AuthProvider provider;

    private String providerId;

    public String roleName() {
        return role.name();
    }

    public void update(String name) {
        this.name = name;
    }

    public boolean hasAuthority(Long userId) {
        return !this.id.equals(userId);
    }

    public void addReservationCount() {
        this.reservationCount ++;
    }
}
