package backend.backend.user.entity;

import backend.backend.comment.entity.NoticeBoardComment;
import backend.backend.common.domain.BaseEntity;
import backend.backend.noticeboard.entity.NoticeBoard;
import backend.backend.reservation.entity.Reservation;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class User extends BaseEntity {
    @Id
    @GeneratedValue @Column(name = "user_id")
    private Long id;
    private String name;

    private String email;

    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Enumerated(EnumType.STRING)
    private Affiliation affiliation;

    @OneToMany(mappedBy = "user")
    private List<NoticeBoard> noticeBoards;

    @OneToMany(mappedBy = "user")
    private List<NoticeBoardComment> comments;

    @OneToMany(mappedBy = "user")
    private List<Reservation> reservation;

    @Enumerated(EnumType.STRING)
    private AuthProvider provider;
    private String providerId;

    public String roleName() {
        return role.name();
    }

    public boolean isNotPossibleModifyOrDeletePermission(Long userId) {
        return !this.id.equals(userId);
    }
}
