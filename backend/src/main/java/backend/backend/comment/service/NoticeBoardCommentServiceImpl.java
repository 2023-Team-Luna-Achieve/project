package backend.backend.comment.service;

import backend.backend.comment.dto.CommentRequestDto;
import backend.backend.exception.ErrorCode;
import backend.backend.exception.NotFoundException;
import backend.backend.comment.dto.NoticeBoardCommentResponse;
import backend.backend.comment.entity.NoticeBoardComment;
import backend.backend.comment.repository.NoticeBoardCommentRepository;
import backend.backend.noticeboard.entity.NoticeBoard;
import backend.backend.noticeboard.repository.NoticeBoardRepository;
import backend.backend.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NoticeBoardCommentServiceImpl implements NoticeBoardCommentService {

    private final NoticeBoardCommentRepository commentRepository;
    private final NoticeBoardRepository noticeBoardRepository;

    @Override
    public List<NoticeBoardCommentResponse> getAllCommentsByNoticeBoardId(Long noticeBoardId) {
        return commentRepository.findAllByNoticeBoardId(noticeBoardId).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public NoticeBoardCommentResponse createComment(User user, Long noticeBoardId, CommentRequestDto commentRequestDto) {
        NoticeBoard noticeBoard = noticeBoardRepository.findById(noticeBoardId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.BOARD_NOT_FOUND));

        NoticeBoardComment comment = NoticeBoardComment.builder()
                .noticeBoard(noticeBoard)
                .user(user)
                .context(commentRequestDto.getContext())
                .build();

        comment = commentRepository.save(comment);
        return convertToDto(comment);
    }

    // 기타 필요한 메소드 정의

    private NoticeBoardCommentResponse convertToDto(NoticeBoardComment comment) {
        if (comment == null) {
            return null;
        }
        return new NoticeBoardCommentResponse(
                comment.getId(),
                comment.getNoticeBoard().getId(),
                comment.getUser().getName(),
                comment.getContext()
        );
    }
    @Override
    public void deleteComment(Long commentId) {
        commentRepository.deleteById(commentId);
    }

    @Override
    public NoticeBoardCommentResponse getCommentById(Long commentId) {
        NoticeBoardComment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new RuntimeException("Comment not found with id: " + commentId));
        return convertToDto(comment);
    }
}