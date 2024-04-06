package backend.backend.comment.repository;

import backend.backend.comment.dto.CommentResponse;
import backend.backend.common.dto.SingleRecordResponse;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static backend.backend.comment.entity.QComment.comment;

@RequiredArgsConstructor
public class CommentRepositoryImpl implements CommentRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public SingleRecordResponse<CommentResponse> findCommentsByBoardId(Long boardId, String cursor) {
        List<CommentResponse> comments = queryFactory.select(Projections.constructor(CommentResponse.class,
                        comment.id,
                        comment.sequenceNumber,
                        comment.user.name,
                        comment.user.email,
                        comment.user.affiliation,
                        comment.context,
                        comment.createdAt
                ))
                .from(comment)
                .where(ltCommentSequenceNumber(cursor),
                        eqBoardId(boardId)
                )
                .orderBy(comment.sequenceNumber.desc())
                .limit(11)
                .fetch();

        return convertToSingleRecord(comments);
    }

    private BooleanExpression eqBoardId(Long boardId) {
        return comment.board.id.eq(boardId);
    }

    private BooleanExpression ltCommentSequenceNumber(String cursor) {
        return comment.sequenceNumber.lt(Long.valueOf(cursor));
    }

    private SingleRecordResponse<CommentResponse> convertToSingleRecord(List<CommentResponse> comments) {
        boolean hasNext = existNextPage(comments);
        String cursor = generateCursor(comments);
        return SingleRecordResponse.of(comments, hasNext, cursor);
    }

    private String generateCursor(List<CommentResponse> comments) {
        CommentResponse commentResponse = comments.get(comments.size() - 1);
        return String.valueOf(commentResponse.sequenceNumber());
    }

    private boolean existNextPage(List<CommentResponse> comments) {
        if (comments.size() > 10) {
            comments.remove(10);
            return true;
        }

        return false;
    }
}
