package backend.backend.comment.service;

import backend.backend.comment.dto.CommentRequestDto;
import backend.backend.comment.dto.NoticeBoardCommentResponse;
import backend.backend.comment.entity.NoticeBoardComment;
import backend.backend.user.entity.User;

import java.util.List;

public interface NoticeBoardCommentService {
    List<NoticeBoardCommentResponse> getAllCommentsByNoticeBoardId(Long noticeBoardId);
    NoticeBoardComment createComment(User user, Long noticeBoardId, CommentRequestDto commentRequestDto);

    void updateNoticeBoardComment(User user, Long commentId, CommentRequestDto commentRequestDto);

    void deleteComment(User user, Long commentId);
    NoticeBoardCommentResponse getOneNoticeBoardComment(Long commentId);
}