package backend.backend.board.repository;

import backend.backend.board.dto.BoardResponse;
import backend.backend.board.entity.Category;
import backend.backend.common.dto.SingleRecordResponse;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static backend.backend.board.entity.QBoard.board;

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
    public SingleRecordResponse<BoardResponse> findBoardsByCategory(String cursor, Category category) {
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
                        ltBoardId(cursor),
                        eqCategory(category)
                )
                .orderBy(board.id.desc())
                .limit(11)
                .fetch();

        return SingleRecordResponse.convertToSingleRecord(boards);
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

