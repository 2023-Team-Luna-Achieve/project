package backend.backend.meetingroom.entity;

import backend.backend.reservation.entity.Reservation;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MeetingRoom {
    @Id @GeneratedValue
    private Long id;

    String name;

//    @OneToOne
//    @JoinColumn(name = "reservation_id")
//    private Reservation reservation;

    public MeetingRoom(String name) {
        this.name = name;
    }
}
