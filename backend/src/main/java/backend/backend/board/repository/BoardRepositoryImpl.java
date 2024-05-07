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
    public SingleRecordResponse<BoardResponse> findFirstNoticeBoardsByIdDesc() {
        List<BoardResponse> boards = queryFactory.select(Projections.constructor(BoardResponse.class,
                        board.id,
                        board.sequenceNumber,
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
                        eqNoticeCategory()
                )
                .orderBy(board.sequenceNumber.desc())
                .limit(11)
                .fetch();

        return convertToSingleRecord(boards);
    }

    @Override
    public SingleRecordResponse<BoardResponse> findFirstSuggestionBoardsByIdDesc() {
        List<BoardResponse> boards = queryFactory.select(Projections.constructor(BoardResponse.class,
                        board.id,
                        board.sequenceNumber,
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
                        eqSuggestionCategory()
                )
                .orderBy(board.sequenceNumber.desc())
                .limit(11)
                .fetch();

        return convertToSingleRecord(boards);
    }

    @Override
    public SingleRecordResponse<BoardResponse> findFirstLostBoardsByIdDesc() {
        List<BoardResponse> boards = queryFactory.select(Projections.constructor(BoardResponse.class,
                        board.id,
                        board.sequenceNumber,
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
                        eqLostItemCategory()
                )
                .orderBy(board.sequenceNumber.desc())
                .limit(11)
                .fetch();

        return convertToSingleRecord(boards);
    }


    @Override
    public SingleRecordResponse<BoardResponse> findMyNoticeBoardsByIdDesc(Long userId) {
        List<BoardResponse> boards = queryFactory.select(Projections.constructor(BoardResponse.class,
                        board.id,
                        board.sequenceNumber,
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
                        eqNoticeCategory()
                )
                .orderBy(board.sequenceNumber.desc())
                .limit(11)
                .fetch();

        return convertToSingleRecord(boards);
    }

    @Override
    public SingleRecordResponse<BoardResponse> findNoticeBoardsByOrderByIdDesc(String cursor) {
        List<BoardResponse> boards = queryFactory.select(Projections.constructor(BoardResponse.class,
                        board.id,
                        board.sequenceNumber,
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
                        ltBoardSequenceNumber(cursor),
                        eqNoticeCategory()
                )
                .orderBy(board.sequenceNumber.desc())
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
                        ltBoardSequenceNumber(cursor),
                        eqSuggestionCategory()
                )
                .orderBy(board.sequenceNumber.desc())
                .limit(11)
                .fetch();

        return convertToSingleRecord(boards);
    }

    @Override
    public SingleRecordResponse<BoardResponse> findMySuggestionBoardsByIdDesc(Long userId) {
        List<BoardResponse> boards = queryFactory.select(Projections.constructor(BoardResponse.class,
                        board.id,
                        board.sequenceNumber,
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
                        eqSuggestionCategory()
                )
                .orderBy(board.sequenceNumber.desc())
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
                        ltBoardSequenceNumber(cursor),
                        eqLostItemCategory()
                )
                .orderBy(board.sequenceNumber.desc())
                .limit(11)
                .fetch();

        return convertToSingleRecord(boards);
    }

    private BooleanExpression eqSuggestionCategory() {
        return board.category.eq(Category.SUGGESTION);
    }

    private BooleanExpression eqAuthorId(Long userId) {
        return board.user.id.eq(userId);
    }

    private BooleanExpression eqNoticeCategory() {
        return board.category.eq(Category.NOTICE);
    }

    private BooleanExpression eqLostItemCategory() {
        return board.category.eq(Category.LOST_ITEM);
    }

    @Override
    public int getMyBoardsCount(Long userId) {
        return Integer.parseInt(String.valueOf(queryFactory.select(board.count())
                .from(board)
                .where(eqAuthorId(userId)).fetchOne()));
    }

    SingleRecordResponse<BoardResponse> convertToSingleRecord(List<BoardResponse> boards) {
        if (boards.isEmpty()) {
            return SingleRecordResponse.of(boards, false, "0");
        }
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

