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
                        comment.user.name,
                        comment.user.email,
                        comment.user.affiliation,
                        comment.context,
                        comment.createdAt
                ))
                .from(comment)
                .where(ltCommentId(cursor),
                        eqBoardId(boardId)
                )
                .orderBy(comment.id.asc())
                .limit(11)
                .fetch();

        return SingleRecordResponse.convertToSingleRecord(comments);
    }

    private BooleanExpression eqBoardId(Long boardId) {
        return comment.board.id.eq(boardId);
    }

    private BooleanExpression ltCommentId(String cursor) {
        return comment.id.gt(Long.valueOf(cursor));
    }
}
