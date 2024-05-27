package backend.backend.board.repository;

import backend.backend.board.dto.BoardResponse;
import backend.backend.board.entity.Category;
import backend.backend.common.dto.SingleRecordResponse;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static backend.backend.board.entity.QBoard.board;
import static backend.backend.report.domain.QReport.report;

@RequiredArgsConstructor
public class BoardRepositoryImpl implements BoardRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public SingleRecordResponse<BoardResponse> findMyBoardsByCategory(Long userId, String cursor, Category category) {
        List<BoardResponse> boards = queryFactory.select(Projections.constructor(BoardResponse.class,
                        board.id,
                        board.user.name,
                        board.user.email,
                        board.category,
                        board.title,
                        board.context,
                        board.viewCount,
                        board.comments.size(),
                        board.createdAt
                ))
                .from(board)
                .where(
                        eqAuthorId(userId),
                        ltBoardId(cursor),
                        eqCategory(category)
                )
                .orderBy(board.id.desc())
                .limit(11)
                .fetch();

        return SingleRecordResponse.convertToSingleRecord(boards);
    }

    @Override
    public SingleRecordResponse<BoardResponse> findBoardsByCategory(String cursor, Category category, Long currentUserId) {
        JPAQuery<BoardResponse> query = queryFactory.select(Projections.constructor(BoardResponse.class,
                        board.id,
                        board.user.name,
                        board.user.email,
                        board.category,
                        board.title,
                        board.context,
                        board.viewCount,
                        board.comments.size(),
                        board.createdAt
                ))
                .from(board);

        if (hasReport(currentUserId)) {
            query.leftJoin(report)
                    .on(joinReportByBoardWriterIdAndReportedUserAndCurrentUserId(currentUserId))
                    .fetchJoin()
                    .where(
                            hasNoReportedUserIdOrNotBlockUser()
                    );
        }

        List<BoardResponse> boards = query.where(
                        ltBoardId(cursor),
                        eqCategory(category)
                )
                .orderBy(board.id.desc())
                .limit(11)
                .fetch();
        return SingleRecordResponse.convertToSingleRecord(boards);
    }

    private boolean hasReport(Long currentUserId) {
        return queryFactory.select(report.count())
                .from(report)
                .where(report.reporter.id.eq(currentUserId))
                .fetchFirst() > 0;
    }

    private BooleanExpression joinReportByBoardWriterIdAndReportedUserAndCurrentUserId(Long currentUserId) {
        return board.user.id.eq(report.reportedUser.id)
                .and(report.reporter.id.eq(currentUserId));
    }

    private BooleanExpression hasNoReportedUserIdOrNotBlockUser() {
        return report.reportedUser.id.isNull().or(report.isBlockUser.eq(false));
    }

    private BooleanExpression eqAuthorId(Long userId) {
        return board.user.id.eq(userId);
    }

    private BooleanExpression eqCategory(Category category) {
        if (category.equals(Category.NOTICE)) {
            return board.category.eq(Category.NOTICE);
        }

        if (category.equals(Category.SUGGESTION)) {
            return board.category.eq(Category.SUGGESTION);
        }

        return board.category.eq(Category.LOST_ITEM);
    }

    BooleanExpression ltBoardId(String cursor) {
        if (cursor.equals("0")) {
            return board.isNotNull();
        }
        return board.id.lt(Long.valueOf(cursor));
    }
}
