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
    public SingleRecordResponse<FcmNotificationResponse> findNotifications(Long userId, String cursor) {
        List<FcmNotificationResponse> fcmNotifications = queryFactory.select(Projections.constructor(FcmNotificationResponse.class,
                        fcmNotification.fcmNotificationCategory,
                        fcmNotification.targetId,
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

        return SingleRecordResponse.convertToSingleRecord(fcmNotifications);
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
}
