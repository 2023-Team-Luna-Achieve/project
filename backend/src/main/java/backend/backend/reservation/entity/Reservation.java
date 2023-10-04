package backend.backend.reservation.entity;

import backend.backend.meetingroom.entity.MeetingRoom;
import backend.backend.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Reservation {
    @Id @GeneratedValue
    @Column(name = "reservation_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    private LocalDateTime reservationTime;

    private LocalDateTime created_at;
    private LocalDateTime deleted_at;
    @OneToOne
    @JoinColumn(name = "meetingRoom_id")
    private MeetingRoom meetingRoom;
}
