package backend.backend.meetingroom.dto;

import backend.backend.meetingroom.entity.MeetingRoom;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MeetingRoomRequestDto {
    private String name;

    public MeetingRoom toEntity() {
        return MeetingRoom.builder()
                .name(name)
                .build();
    }
}