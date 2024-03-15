package backend.backend.meetingroom.dto;

import backend.backend.meetingroom.entity.MeetingRoom;
import lombok.Getter;

public record MeetingRoomResponse(
        Long id,
        String name,
        String description
) {
    public static MeetingRoomResponse from(MeetingRoom meetingRoom) {
        return new MeetingRoomResponse(
                meetingRoom.getId(),
                meetingRoom.getName(),
                meetingRoom.getDescription()
        );
    }
}
