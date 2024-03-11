package backend.backend.meetingroom.service;

import backend.backend.meetingroom.dto.MeetingRoomResponse;
import backend.backend.meetingroom.entity.MeetingRoom;
import backend.backend.meetingroom.repository.MeetingRoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MeetingRoomService {
    private final MeetingRoomRepository meetingRoomRepository;

    public void saveMeetingRoom(MeetingRoom meetingRoom) {
        meetingRoomRepository.save(meetingRoom);
    }


    public List<MeetingRoomResponse> getMeetingRooms() {
        List<MeetingRoom> meetingRooms = meetingRoomRepository.findAll();
        return meetingRooms.stream()
                .map(MeetingRoomResponse::from)
                .toList();
    }
}
