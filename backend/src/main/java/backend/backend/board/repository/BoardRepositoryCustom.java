package backend.backend.board.repository;

import backend.backend.board.entity.Category;
import backend.backend.common.dto.SingleRecordResponse;
import backend.backend.board.dto.BoardResponse;

public interface BoardRepositoryCustom {
    SingleRecordResponse<BoardResponse> findBoardsByCategory(String cursor, Category category, Long currentUserId);

    SingleRecordResponse<BoardResponse> findMyBoardsByCategory(Long userId, String cursor, Category category);
}
