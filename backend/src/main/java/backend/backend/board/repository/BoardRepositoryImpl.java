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
    public SingleRecordResponse<BoardResponse> findNoticeBoardsByOrderByIdDesc(String cursor) {
        List<BoardResponse> boards = queryFactory.select(Projections.constructor(BoardResponse.class,
                        board.id,
                        board.sequenceNumber,
                        board.user.name,
                        board.category,
                        board.title,
                        board.context,
                        board.viewCount
                ))
                .from(board)
                .where(
                        ltBoardSequenceNumber(cursor),
                        eqNoticeCategory()
                )
                .orderBy(board.id.desc())
                .limit(11)
                .fetch();

        return convertToSingleRecord(boards);
    }

    @Override
    public SingleRecordResponse<BoardResponse> findSuggestionBoardsByOrderByIdDesc(String cursor) {
        List<BoardResponse> boards = queryFactory.select(Projections.constructor(BoardResponse.class,
                        board.id,
                        board.sequenceNumber,
                        board.user.name,
                        board.category,
                        board.title,
                        board.context,
                        board.viewCount
                ))
                .from(board)
                .where(
                        ltBoardSequenceNumber(cursor),
                        eqSuggestionCategory()
                )
                .orderBy(board.id.desc())
                .limit(11)
                .fetch();

        return convertToSingleRecord(boards);
    }

    @Override
    public SingleRecordResponse<BoardResponse> findLostItemBoardsByOrderByIdDesc(String cursor) {
        List<BoardResponse> boards = queryFactory.select(Projections.constructor(BoardResponse.class,
                        board.id,
                        board.sequenceNumber,
                        board.user.name,
                        board.category,
                        board.title,
                        board.context,
                        board.viewCount
                ))
                .from(board)
                .where(
                        ltBoardSequenceNumber(cursor),
                        eqLostItemCategory()
                )
                .orderBy(board.id.desc())
                .limit(11)
                .fetch();

        return convertToSingleRecord(boards);
    }

    private BooleanExpression eqSuggestionCategory()  {
        return board.category.eq(Category.SUGGESTION);
    }

    private BooleanExpression eqNoticeCategory() {
        return board.category.eq(Category.NOTICE);
    }

    private BooleanExpression eqLostItemCategory()  {
        return board.category.eq(Category.LOST_ITEM);
    }

    SingleRecordResponse<BoardResponse> convertToSingleRecord(List<BoardResponse> boards) {
        boolean hasNext = existNextPage(boards);
        String cursor = generateCursor(boards);
        return SingleRecordResponse.of(boards, hasNext, cursor);
    }

    private boolean existNextPage(List<BoardResponse> boards) {
        if (boards.size() > 10) {
            boards.remove(10);
            return true;
        }

        return false;
    }

    private String generateCursor(List<BoardResponse> boards) {
        BoardResponse boardResponse = boards.get(boards.size() - 1);
        return String.valueOf(boardResponse.sequenceNumber());
    }

    BooleanExpression ltBoardSequenceNumber(String cursor) {
        return board.sequenceNumber.lt(Long.valueOf(cursor));
    }
}

