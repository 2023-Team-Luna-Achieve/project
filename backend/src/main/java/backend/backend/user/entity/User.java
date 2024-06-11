package backend.backend.user.entity;

import backend.backend.common.domain.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

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

    @ColumnDefault("0")
    private int reservationCount;

    @Enumerated(EnumType.STRING)
    private AuthProvider provider;

    private String providerId;

    private boolean isAccountDeleted;

    @PrePersist
    public void defaultDeleteAccountSetting() {
        this.isAccountDeleted = false;
    }

    public String roleName() {
        return role.name();
    }

    public void updateName(String name) {
        this.name = name;
    }

    public boolean hasAuthority(Long userId) {
        return !this.id.equals(userId);
    }

    public void addReservationCount() {
        this.reservationCount ++;
    }

    public void updatePassword(String password) {
        this.password = password;
    }

    public void deleteAccount() {
        this.isAccountDeleted = true;
    }
}
