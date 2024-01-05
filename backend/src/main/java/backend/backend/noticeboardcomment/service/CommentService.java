package backend.backend.noticeboardcomment.service;

import backend.backend.noticeboardcomment.dto.CommentRequestDto;
import backend.backend.noticeboardcomment.dto.CommentResponse;
import backend.backend.user.entity.User;

import java.util.List;

public interface CommentService {
    List<CommentResponse> getAllCommentsByNoticeBoardId(Long noticeBoardId);
    CommentResponse createComment(User user, Long noticeBoardId, CommentRequestDto commentRequestDto);
    void deleteComment(Long commentId);
    CommentResponse getCommentById(Long commentId);
}