package backend.backend.reservation.entity;

import backend.backend.common.domain.BaseEntity;
import backend.backend.meetingroom.entity.MeetingRoom;
import backend.backend.user.entity.User;
import lombok.*;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Getter
@Builder
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Reservation extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reservation_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    private LocalDateTime reservationStartTime;
    private LocalDateTime reservationEndTime;
    private int members;
    private LocalDateTime created_at;
    private LocalDateTime deleted_at;

    @OneToOne
    @JoinColumn(name = "meetingRoom_id")
    private MeetingRoom meetingRoom;
}
