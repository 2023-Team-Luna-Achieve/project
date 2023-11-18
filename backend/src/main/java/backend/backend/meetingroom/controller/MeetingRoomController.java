package backend.backend.meetingroom.controller;

import backend.backend.meetingroom.dto.MeetingRoomRequestDto;
import backend.backend.meetingroom.entity.MeetingRoom;
import backend.backend.meetingroom.service.MeetingRoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/meetingRoom")
public class MeetingRoomController {
    private final MeetingRoomService meetingRoomService;
    @PostMapping
    public void create(@RequestBody MeetingRoomRequestDto meetingRoomRequestDto) {
        MeetingRoom meetingRoom = new MeetingRoom(meetingRoomRequestDto.getName(), meetingRoomRequestDto.getStatus());
        meetingRoomService.save(meetingRoom);
    }

}
