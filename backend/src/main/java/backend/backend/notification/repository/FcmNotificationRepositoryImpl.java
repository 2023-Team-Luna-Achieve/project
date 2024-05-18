package backend.backend.notification.repository;

import backend.backend.common.dto.SingleRecordResponse;
import backend.backend.notification.dto.FcmNotificationResponse;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static backend.backend.notification.domain.QFcmNotification.fcmNotification;

@RequiredArgsConstructor
public class FcmNotificationRepositoryImpl implements FcmNotificationRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    @Override
    public SingleRecordResponse<FcmNotificationResponse> getNotifications(Long userId, String cursor) {
        List<FcmNotificationResponse> fcmNotifications = queryFactory.select(Projections.constructor(FcmNotificationResponse.class,
                        fcmNotification.id,
                        fcmNotification.content,
                        fcmNotification.isRead
                ))
                .from(fcmNotification)
                .where(
                        ltCursor(cursor),
                        eqReceiverId(userId)
                )
                .orderBy(fcmNotification.id.desc())
                .limit(11)
                .fetch();

        return convertToSingleRecord(fcmNotifications);
    }

    private BooleanExpression ltCursor(String cursor) {
        if (cursor.equals("0")) {
            return fcmNotification.isNotNull();
        }

        return fcmNotification.id.lt(Long.valueOf(cursor));
    }

    private BooleanExpression eqReceiverId(Long userId) {
        return fcmNotification.receiverId.eq(userId);
    }

    private SingleRecordResponse<FcmNotificationResponse> convertToSingleRecord(List<FcmNotificationResponse> fcmNotificationResponses) {
        if (fcmNotificationResponses.isEmpty()) {
            return SingleRecordResponse.of(fcmNotificationResponses, false, "0");
        }
        boolean hasNext = existNextPage(fcmNotificationResponses);
        String cursor = generateCursor(fcmNotificationResponses);
        return SingleRecordResponse.of(fcmNotificationResponses, hasNext, cursor);
    }

    boolean existNextPage(List<FcmNotificationResponse> fcmNotificationResponses) {
        if (fcmNotificationResponses.size() > 10) {
            fcmNotificationResponses.remove(10);
            return true;
        }

        return false;
    }

    String generateCursor(List<FcmNotificationResponse> fcmNotificationResponses) {
        return String.valueOf(fcmNotificationResponses.get(fcmNotificationResponses.size() - 1).notificationId());
    }
}
