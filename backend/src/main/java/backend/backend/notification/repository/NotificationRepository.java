package backend.backend.notification.repository;

import backend.backend.notification.domain.FcmNotification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepository extends JpaRepository<FcmNotification, Long> {

}
