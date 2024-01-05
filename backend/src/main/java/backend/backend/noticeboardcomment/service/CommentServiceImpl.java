package backend.backend.noticeboardcomment.service;

import backend.backend.exception.ErrorCode;
import backend.backend.exception.NotFoundException;
import backend.backend.noticeboardcomment.dto.CommentRequestDto;
import backend.backend.noticeboardcomment.dto.CommentResponse;
import backend.backend.noticeboardcomment.entity.NoticeBoardComment;
import backend.backend.noticeboardcomment.repository.CommentRepository;
import backend.backend.noticeboard.entity.NoticeBoard;
import backend.backend.noticeboard.repository.NoticeBoardRepository;
import backend.backend.user.entity.User;
import backend.backend.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements backend.backend.noticeboardcomment.service.CommentService {

    private final CommentRepository commentRepository;
    private final NoticeBoardRepository noticeBoardRepository;

    @Override
    public List<CommentResponse> getAllCommentsByNoticeBoardId(Long noticeBoardId) {
        return commentRepository.findAllByNoticeBoardId(noticeBoardId).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public CommentResponse createComment(User user, Long noticeBoardId, CommentRequestDto commentRequestDto) {
        NoticeBoard noticeBoard = noticeBoardRepository.findById(noticeBoardId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.BOARD_NOT_FOUND));

        NoticeBoardComment comment = NoticeBoardComment.builder()
                .noticeBoard(noticeBoard)
                .user(user)
                .context(commentRequestDto.getContext())
                .created_at(LocalDateTime.now())
                .updated_at(LocalDateTime.now())
                // 나머지 필드 설정
                .build();

        comment = commentRepository.save(comment);
        return convertToDto(comment);
    }

    // 기타 필요한 메소드 정의

    private CommentResponse convertToDto(NoticeBoardComment comment) {
        if (comment == null) {
            return null;
        }
        return new CommentResponse(
                comment.getId(),
                comment.getNoticeBoard().getId(),
                comment.getUser().getName(),
                comment.getContext(),
                comment.getCreated_at()
        );
    }
    @Override
    public void deleteComment(Long commentId) {
        commentRepository.deleteById(commentId);
    }

    @Override
    public CommentResponse getCommentById(Long commentId) {
        NoticeBoardComment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new RuntimeException("Comment not found with id: " + commentId));
        return convertToDto(comment);
    }
}