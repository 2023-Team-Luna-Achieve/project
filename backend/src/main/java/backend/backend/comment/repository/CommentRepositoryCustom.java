package backend.backend.comment.repository;

import backend.backend.comment.dto.CommentResponse;
import backend.backend.common.dto.SingleRecordResponse;

public interface CommentRepositoryCustom {
    SingleRecordResponse<CommentResponse> findCommentsByBoardId(Long boardId, String cursor);
}
