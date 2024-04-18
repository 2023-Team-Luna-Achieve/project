package backend.backend.comment.service;

import backend.backend.comment.dto.CommentRequest;
import backend.backend.comment.entity.Comment;
import backend.backend.common.dto.SingleRecordResponse;
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

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final BoardRepository boardRepository;

    public SingleRecordResponse<CommentResponse> getAllCommentsByBoardId(Long boardId, String cursor) {
        String largestCommentSequenceNumber = commentRepository.getLastSequenceNumber(boardId);
        if (cursor.equals("0")) {
            return commentRepository.findCommentsByBoardId(boardId, Objects.requireNonNullElse(largestCommentSequenceNumber, "0"));
        }
        return commentRepository.findCommentsByBoardId(boardId, largestCommentSequenceNumber);
    }

    public Comment createComment(User user, CommentRequest commentRequest) {
        Board board = boardRepository.findById(commentRequest.boardId())
                .orElseThrow(() -> new NotFoundException(ErrorCode.BOARD_NOT_FOUND));

        Long sequenceNumber  = (long) board.getComments().size();
        Comment comment = commentRequest.toEntity(user, board, sequenceNumber);

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