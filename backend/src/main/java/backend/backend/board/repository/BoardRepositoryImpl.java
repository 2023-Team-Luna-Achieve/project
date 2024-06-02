package backend.backend.board.repository;

import backend.backend.board.dto.BoardResponse;
import backend.backend.board.entity.Category;
import backend.backend.common.dto.SingleRecordResponse;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static backend.backend.board.entity.QBoard.board;
import static backend.backend.report.domain.QBlock.block;

@RequiredArgsConstructor
public class BoardRepositoryImpl implements BoardRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    private static final int maxReportCount = 30;

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

        if (hasBlock(currentUserId)) {
            query.leftJoin(block)
                    .on(joinBlockByBoardWriterIdAndReportedUserAndCurrentUserId(currentUserId))
                    .fetchJoin()
                    .where(
                            hasNoBlockedUserIdOrNotBlockUser()
                    );
        }

        List<BoardResponse> boards = query.where(
                        ltBoardId(cursor),
                        eqCategory(category),
                        ltMaxReportCount(),
                        notDeletedUser()
                )
                .orderBy(board.id.desc())
                .limit(11)
                .fetch();
        return SingleRecordResponse.convertToSingleRecord(boards);
    }

    private boolean hasBlock(Long currentUserId) {
        return queryFactory.select(block.count())
                .from(block)
                .where(block.blocker.id.eq(currentUserId))
                .fetchFirst() > 0;
    }

    private BooleanExpression joinBlockByBoardWriterIdAndReportedUserAndCurrentUserId(Long currentUserId) {
        return board.user.id.eq(block.blockedUser.id)
                .and(block.blocker.id.eq(currentUserId));
    }

    private BooleanExpression hasNoBlockedUserIdOrNotBlockUser() {
        return block.blockedUser.id.isNull();
    }

    private BooleanExpression ltMaxReportCount() {
        return board.reportCount.lt(maxReportCount);
    }

    private BooleanExpression notDeletedUser() {
        return board.user.isAccountDeleted.eq(false);
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
