package backend.backend.board.repository;

import backend.backend.common.dto.SingleRecordResponse;
import backend.backend.board.dto.BoardResponse;
import com.querydsl.jpa.impl.JPAQuery;

public interface BoardRepositoryCustom {
    SingleRecordResponse<BoardResponse> findNoticeBoardsByOrderByIdDesc(String cursor);

    SingleRecordResponse<BoardResponse> findFirstNoticeBoardsByIdDesc();

    SingleRecordResponse<BoardResponse> findMyNoticeBoardsByIdDesc(Long userId);

    SingleRecordResponse<BoardResponse> findSuggestionBoardsByOrderByIdDesc(String cursor);

    SingleRecordResponse<BoardResponse> findLostItemBoardsByOrderByIdDesc(String cursor);

    int getMyBoardsCount(Long userId);
}
