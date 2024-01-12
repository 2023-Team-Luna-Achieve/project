package backend.backend.comment.service;

import backend.backend.comment.dto.CommentRequestDto;
import backend.backend.comment.dto.NoticeBoardCommentResponse;
import backend.backend.user.entity.User;

import java.util.List;

public interface NoticeBoardCommentService {
    List<NoticeBoardCommentResponse> getAllCommentsByNoticeBoardId(Long noticeBoardId);
    NoticeBoardCommentResponse createComment(User user, Long noticeBoardId, CommentRequestDto commentRequestDto);
    void deleteComment(Long commentId);
    NoticeBoardCommentResponse getCommentById(Long commentId);
}