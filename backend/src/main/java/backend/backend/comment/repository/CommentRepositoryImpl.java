package backend.backend.comment.repository;

import backend.backend.comment.dto.CommentResponse;
import backend.backend.common.dto.SingleRecordResponse;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static backend.backend.comment.entity.QComment.comment;
import static backend.backend.report.domain.QBlock.block;

@RequiredArgsConstructor
public class CommentRepositoryImpl implements CommentRepositoryCustom {

    private final JPAQueryFactory queryFactory;

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


        if (hasReport(currentUserId)) {
            query.leftJoin(block)
                    .on(joinReportByCommentWriterIdAndReportedUserAndCurrentUserId(currentUserId))
                    .fetchJoin()
                    .where(
                            hasNoReportedUserIdOrNotBlockUser()
                    );
        }

        List<CommentResponse> comments = query.where(
                        ltCommentId(cursor),
                        eqBoardId(boardId)
                )
                .orderBy(comment.id.asc())
                .limit(11)
                .fetch();

        return SingleRecordResponse.convertToSingleRecord(comments);
    }

    private boolean hasReport(Long currentUserId) {
        return queryFactory.select(block.count())
                .from(block)
                .where(block.blocker.id.eq(currentUserId))
                .fetchFirst() > 0;
    }

    private BooleanExpression hasNoReportedUserIdOrNotBlockUser() {
        return block.blockedUser.id.isNull();
    }

    private BooleanExpression joinReportByCommentWriterIdAndReportedUserAndCurrentUserId(Long currentUserId) {
        return comment.user.id.eq(block.blockedUser.id)
                .and(block.blocker.id.eq(currentUserId));
    }

    private BooleanExpression eqBoardId(Long boardId) {
        return comment.board.id.eq(boardId);
    }

    private BooleanExpression ltCommentId(String cursor) {
        return comment.id.gt(Long.valueOf(cursor));
    }
}
