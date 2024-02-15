package backend.backend.comment.service;

import backend.backend.comment.dto.CommentRequest;
import backend.backend.comment.entity.Comment;
import backend.backend.common.exception.AuthException;
import backend.backend.common.exception.ErrorCode;
import backend.backend.common.exception.NotFoundException;
import backend.backend.comment.dto.CommentResponse;
import backend.backend.comment.repository.CommentRepository;
import backend.backend.board.entity.Board;
import backend.backend.board.repository.BoardRepository;
import backend.backend.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final BoardRepository boardRepository;

    public List<CommentResponse> getAllCommentsByBoardId(Long boardId) {
        return commentRepository.findAllByBoardId(boardId).stream()
                .map(CommentResponse::of)
                .collect(Collectors.toList());
    }

    public Comment createComment(User user, Long boardId, CommentRequest commentRequest) {
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.BOARD_NOT_FOUND));

        Comment comment = commentRequest.toEntity(user, board);

        return commentRepository.save(comment);
    }

    public CommentResponse findOneComment(Long id) {
        Comment comment = findCommentById(id);
        return CommentResponse.of(comment);
    }

    public void updateComment(User user, Long commentId, CommentRequest commentRequest) {
        Comment comment = findCommentById(commentId);
        if(user.hasAuthority(comment.getUser().getId())) {
            throw new AuthException(ErrorCode.FORBIDDEN);
        }
        comment.update(commentRequest.context());
    }

    public void deleteComment(User user, Long commentId) {
        Comment comment = findCommentById(commentId);
        if(user.hasAuthority(comment.getUser().getId())) {
            throw new AuthException(ErrorCode.FORBIDDEN);
        }
        commentRepository.deleteById(commentId);
    }

    private Comment findCommentById(Long commentId) {
        return commentRepository.findById(commentId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.COMMENT_NOT_FOUND));
    }
}