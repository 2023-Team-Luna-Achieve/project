package backend.backend.meetingroom.controller;

import backend.backend.meetingroom.dto.MeetingRoomReservationAvailTimeResponse;
import backend.backend.meetingroom.dto.MeetingRoomResponse;
import backend.backend.meetingroom.entity.MeetingRoom;
import backend.backend.meetingroom.service.MeetingRoomService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "Meeting Room API", description = "미팅룸 조회")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/meetingRooms")
public class MeetingRoomController {
    private final MeetingRoomService meetingRoomService;

    @Operation(summary = "예약 가능 동아리방 조회 API", description = "예약 가능한 방을 전체조회 한다.")
    @GetMapping
    public ResponseEntity<List<MeetingRoomResponse>> getMeetingRooms() {
        return ResponseEntity.ok().body(meetingRoomService.getMeetingRooms());
    }


    @Operation(summary = "각 동아리방 별 예약 가능 시간 조회 API", description = "각 방별 예약 가능 시간을 전체조회 한다.")
    @GetMapping("/avail")
    public ResponseEntity<List<MeetingRoomReservationAvailTimeResponse>> getAvailReservationTime() {
        return ResponseEntity.ok().body(meetingRoomService.getReserveAvailTimes());
    }

    @PostConstruct
    public void initMeetingRoom() {
        MeetingRoom meetingRoom1 = new MeetingRoom("Palo Alto", "실리콘밸리의 탄생지(Birthplace of Silicon Valley)로 불리는 미국 캘리포니아주 산타클라라 군에 속한 실리콘밸리 북부의 도시의 이름에서 따온 방입니다.");
        meetingRoomService.saveMeetingRoom(meetingRoom1);

        MeetingRoom meetingRoom2 = new MeetingRoom("Apple", "애플(영어: Apple Inc.)은 미국 캘리포니아의 아이폰, 아이패드, 애플 워치, 에어팟, 아이맥, 맥북, 맥 스튜디오와 맥 프로, 홈팟, 비전 프로, 에어태그 등의 하드웨어");
        meetingRoomService.saveMeetingRoom(meetingRoom2);
    }
}
