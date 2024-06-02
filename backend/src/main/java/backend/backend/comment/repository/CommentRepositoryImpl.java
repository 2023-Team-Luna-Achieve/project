package backend.backend.comment.repository;

import backend.backend.comment.dto.CommentResponse;
import backend.backend.common.dto.SingleRecordResponse;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static backend.backend.board.entity.QBoard.board;
import static backend.backend.comment.entity.QComment.comment;
import static backend.backend.report.domain.QBlock.block;

@RequiredArgsConstructor
public class CommentRepositoryImpl implements CommentRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    private static final int maxReportCount = 30;

    @Override
    public SingleRecordResponse<CommentResponse> findCommentsByBoardId(Long boardId, String cursor, Long currentUserId) {
        JPAQuery<CommentResponse> query = queryFactory.select(Projections.constructor(CommentResponse.class,
                        comment.id,
                        comment.user.name,
                        comment.user.email,
                        comment.user.affiliation,
                        comment.context,
                        comment.createdAt
                ))
                .from(comment);

        if (hasBlock(currentUserId)) {
            query.leftJoin(block)
                    .on(joinBlockByCommentWriterIdAndBlockedUserAndCurrentUserId(currentUserId))
                    .fetchJoin()
                    .where(
                            hasNoBlockUser()
                    );
        }

        List<CommentResponse> comments = query.where(
                        ltCommentId(cursor),
                        eqBoardId(boardId),
                        ltMaxReportCount(),
                        notDeletedUser()
                )
                .orderBy(comment.id.asc())
                .limit(11)
                .fetch();

        return SingleRecordResponse.convertToSingleRecord(comments);
    }

    private boolean hasBlock(Long currentUserId) {
        return queryFactory.select(block.count())
                .from(block)
                .where(block.blocker.id.eq(currentUserId))
                .fetchFirst() > 0;
    }

    private BooleanExpression hasNoBlockUser() {
        return block.blockedUser.id.isNull();
    }

    private BooleanExpression joinBlockByCommentWriterIdAndBlockedUserAndCurrentUserId(Long currentUserId) {
        return comment.user.id.eq(block.blockedUser.id)
                .and(block.blocker.id.eq(currentUserId));
    }

    private BooleanExpression ltMaxReportCount() {
        return comment.reportCount.lt(maxReportCount);
    }

    private BooleanExpression eqBoardId(Long boardId) {
        return comment.board.id.eq(boardId);
    }

    private BooleanExpression ltCommentId(String cursor) {
        return comment.id.gt(Long.valueOf(cursor));
    }

    private BooleanExpression notDeletedUser() {
        return comment.user.isAccountDeleted.eq(false);
    }

}
