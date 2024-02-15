package backend.backend.meetingroom.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MeetingRoom {
    @Id @GeneratedValue
    private Long id;
    String name;

    public MeetingRoom(String name) {
        this.name = name;
    }
}
