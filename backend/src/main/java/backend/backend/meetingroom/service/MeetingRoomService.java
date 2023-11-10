package backend.backend.meetingroom.service;

import backend.backend.meetingroom.entity.MeetingRoom;
import backend.backend.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MeetingRoomService extends JpaRepository <MeetingRoom, Long>{
}
