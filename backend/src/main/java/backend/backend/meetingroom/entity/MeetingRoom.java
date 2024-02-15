package backend.backend.meetingroom.entity;

import lombok.*;

import jakarta.persistence.*;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class MeetingRoom {
    @Id @GeneratedValue
    private Long id;
    String name;

    public MeetingRoom(String name) {
        this.name = name;
    }
}
