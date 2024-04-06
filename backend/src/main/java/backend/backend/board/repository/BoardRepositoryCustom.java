package backend.backend.board.repository;

import backend.backend.common.dto.SingleRecordResponse;
import backend.backend.board.dto.BoardResponse;

public interface BoardRepositoryCustom {
    SingleRecordResponse<BoardResponse> findNoticeBoardsByOrderByIdDesc(String cursor);

    SingleRecordResponse<BoardResponse> findFirstNoticeBoardsByIdDesc();

    SingleRecordResponse<BoardResponse> findSuggestionBoardsByOrderByIdDesc(String cursor);

    SingleRecordResponse<BoardResponse> findLostItemBoardsByOrderByIdDesc(String cursor);
}
