package backend.backend.meetingroom.service;

import backend.backend.meetingroom.entity.MeetingRoom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MeetingRoomService extends JpaRepository <MeetingRoom, Long>{
}
