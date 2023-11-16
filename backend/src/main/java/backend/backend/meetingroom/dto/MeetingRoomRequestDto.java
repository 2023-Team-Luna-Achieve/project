package backend.backend.meetingroom.dto;

import backend.backend.meetingroom.entity.MeetingRoom;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class MeetingRoomRequestDto {
    private String name;

    public MeetingRoom toEntity() {
        return MeetingRoom.builder()
                .name(name)
                .build();
    }
}
