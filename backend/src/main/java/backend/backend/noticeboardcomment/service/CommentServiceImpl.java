package backend.backend.noticeboardcomment.service;

import backend.backend.exception.ErrorCode;
import backend.backend.exception.NotFoundException;
import backend.backend.noticeboardcomment.dto.CommentRequestDto;
import backend.backend.noticeboardcomment.dto.CommentResponseDto;
import backend.backend.noticeboardcomment.entity.NoticeBoardComment;
import backend.backend.noticeboardcomment.repository.CommentRepository;
import backend.backend.noticeboard.entity.NoticeBoard;
import backend.backend.noticeboard.repository.NoticeBoardRepository;
import backend.backend.user.entity.User;
import backend.backend.user.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements backend.backend.noticeboardcomment.service.CommentService {

    private final CommentRepository commentRepository;
    private final NoticeBoardRepository noticeBoardRepository;
    private final UserRepository userRepository;

    public CommentServiceImpl(CommentRepository commentRepository, NoticeBoardRepository noticeBoardRepository, UserRepository userRepository) {
        this.commentRepository = commentRepository;
        this.noticeBoardRepository = noticeBoardRepository;
        this.userRepository = userRepository;
    }

    @Override
    public List<CommentResponseDto> getAllCommentsByNoticeBoardId(Long noticeBoardId) {
        return commentRepository.findAllByNoticeBoardId(noticeBoardId).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public CommentResponseDto createComment(User user, Long noticeBoardId, CommentRequestDto commentRequestDto) {
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

    private CommentResponseDto convertToDto(NoticeBoardComment comment) {
        if (comment == null) {
            return null; // 또는 예외를 throw하거나 기본값을 반환할 수 있습니다.
        }

        CommentResponseDto commentResponseDto = new CommentResponseDto();
        commentResponseDto.setId(comment.getId());
        commentResponseDto.setNoticeBoardId(comment.getNoticeBoard().getId());
        commentResponseDto.setUserId(comment.getUser().getId());
        commentResponseDto.setContext(comment.getContext());
        commentResponseDto.setCreated_at(comment.getCreated_at());
        // 나머지 필드 설정

        return commentResponseDto;
    }
    @Override
    public void deleteComment(Long commentId) {
        commentRepository.deleteById(commentId);
    }

    @Override
    public CommentResponseDto getCommentById(Long commentId) {
        NoticeBoardComment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new RuntimeException("Comment not found with id: " + commentId));
        return convertToDto(comment);
    }
}