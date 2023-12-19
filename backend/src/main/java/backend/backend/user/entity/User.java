package backend.backend.user.entity;

import backend.backend.noticeboard.entity.NoticeBoard;
import backend.backend.noticeboardcomment.entity.Comment;
import backend.backend.reservation.entity.Reservation;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id @GeneratedValue
    @Column(name = "user_id")
    private Long id;
    private String name;
    private String email;
    private String password;
    private LocalDateTime created_at;
    @Enumerated(EnumType.STRING)
    private Role role;

    @Enumerated(EnumType.STRING)
    private Affiliation affiliation;

    @OneToMany(mappedBy = "user")
    private List<NoticeBoard> noticeBoards;

    @OneToMany(mappedBy = "user")
    private List<Comment> comments;

    @OneToMany(mappedBy = "user")
    private List<Reservation> reservation;

    @Enumerated(EnumType.STRING)
    private AuthProvider provider;
    private String providerId;

    public String roleName() {
        return role.name();
    }
}
