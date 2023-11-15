package backend.backend.meetingroom.dto;

import backend.backend.meetingroom.entity.MeetingRoom;
import backend.backend.meetingroom.entity.Status;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class MeetingRoomRequestDto {
    private String name;
    private Status status;

    public MeetingRoom toEntity() {
        return MeetingRoom.builder()
                .name(name)
                .status(status)
                .build();
    }
}
