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




//    public <T extends Identifiable> SingleRecordResponse<T> convertToSingleRecord(List<T> responses) {
//        if (responses.isEmpty()) {
//            return SingleRecordResponse.of(responses, false, "0");
//        }
//        boolean hasNext = existNextPage(responses);
//        String cursor = generateCursor(responses);
//        return SingleRecordResponse.of(responses, hasNext, cursor);
//    }
//
//    private <T> boolean existNextPage(List<T> responses) {
//        if (responses.size() > 10) {
//            responses.remove(10);
//            return true;
//        }
//
//        return false;
//    }
//
//    private <T extends Identifiable> String generateCursor(List<T> responses) {
//        return String.valueOf(responses.get(responses.size() - 1).getId());
//    }
}
