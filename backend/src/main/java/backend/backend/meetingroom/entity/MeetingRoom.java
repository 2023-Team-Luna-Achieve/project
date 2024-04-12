package backend.backend.meetingroom.entity;

import lombok.*;

import jakarta.persistence.*;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class MeetingRoom {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "meeting_room_id")
    private Long id;
    String name;
    String description;

    public MeetingRoom(String name, String description) {
        this.name = name;
        this.description = description;
    }
}
