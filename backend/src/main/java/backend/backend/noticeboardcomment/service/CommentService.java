package backend.backend.noticeboardcomment.service;

import backend.backend.noticeboardcomment.dto.CommentRequestDto;
import backend.backend.noticeboardcomment.dto.CommentResponseDto;
import backend.backend.user.entity.User;
import org.springframework.stereotype.Service;

import java.util.List;

public interface CommentService {
    List<CommentResponseDto> getAllCommentsByNoticeBoardId(Long noticeBoardId);
    CommentResponseDto createComment(User user, Long noticeBoardId, CommentRequestDto commentRequestDto);
    void deleteComment(Long commentId);
    CommentResponseDto getCommentById(Long commentId);

}