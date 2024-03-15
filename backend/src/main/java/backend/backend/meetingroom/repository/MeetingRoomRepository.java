package backend.backend.meetingroom.repository;

import backend.backend.meetingroom.entity.MeetingRoom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MeetingRoomRepository extends JpaRepository <MeetingRoom, Long>{
}
