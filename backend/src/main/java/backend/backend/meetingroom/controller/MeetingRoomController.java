package backend.backend.meetingroom.controller;

import backend.backend.meetingroom.entity.MeetingRoom;
import backend.backend.meetingroom.service.MeetingRoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.annotation.PostConstruct;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/meetingRoom")
public class MeetingRoomController {
    private final MeetingRoomService meetingRoomService;

//    @PostConstruct
//    public void initMeetingRoom() {
//        MeetingRoom meetingRoom = new MeetingRoom("Palo Alto");
//        meetingRoomService.save(meetingRoom);
//    }
}
