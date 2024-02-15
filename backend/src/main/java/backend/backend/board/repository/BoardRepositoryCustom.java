package backend.backend.board.repository;

import backend.backend.common.dto.SingleRecordResponse;
import backend.backend.board.dto.BoardResponse;

public interface BoardRepositoryCustom {
    SingleRecordResponse<BoardResponse> findBoardsByOrderByIdDesc(String cursor);
}
