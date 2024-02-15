package backend.backend.comment.service;

import backend.backend.comment.dto.CommentRequestDto;
import backend.backend.common.exception.AuthException;
import backend.backend.common.exception.ErrorCode;
import backend.backend.common.exception.NotFoundException;
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
                .map(noticeBoardComment -> NoticeBoardCommentResponse.of(noticeBoardComment))
                .collect(Collectors.toList());
    }

    @Override
    public NoticeBoardComment createComment(User user, Long noticeBoardId, CommentRequestDto commentRequestDto) {
        NoticeBoard noticeBoard = noticeBoardRepository.findById(noticeBoardId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.BOARD_NOT_FOUND));

        NoticeBoardComment noticeBoardComment = commentRequestDto.toNoticeComment(user, noticeBoard);

        return commentRepository.save(noticeBoardComment);
    }

    @Override
    public NoticeBoardCommentResponse getOneNoticeBoardComment(Long id) {
        NoticeBoardComment noticeBoardComment = findNoticeBoardCommentById(id);
        return NoticeBoardCommentResponse.of(noticeBoardComment);
    }

    @Override
    public void updateNoticeBoardComment(User user, Long commentId, CommentRequestDto commentRequestDto) {
        NoticeBoardComment noticeBoardComment = findNoticeBoardCommentById(commentId);
        if(user.isNotPossibleModifyOrDeletePermission(noticeBoardComment.getUser().getId())) {
            throw new AuthException(ErrorCode.FORBIDDEN);
        }
        noticeBoardComment.update(commentRequestDto);
    }

    @Override
    public void deleteComment(User user, Long commentId) {
        NoticeBoardComment noticeBoardComment = findNoticeBoardCommentById(commentId);
        if(user.isNotPossibleModifyOrDeletePermission(noticeBoardComment.getUser().getId())) {
            throw new AuthException(ErrorCode.FORBIDDEN);
        }
        commentRepository.deleteById(commentId);
    }

    private NoticeBoardComment findNoticeBoardCommentById(Long commentId) {
        return commentRepository.findById(commentId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.COMMENT_NOT_FOUND));
    }
}