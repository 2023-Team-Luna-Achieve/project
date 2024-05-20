package backend.backend.board.service;

import backend.backend.board.entity.Category;
import backend.backend.common.dto.SingleRecordResponse;
import backend.backend.common.exception.AuthException;
import backend.backend.common.exception.ErrorCode;
import backend.backend.common.exception.NotFoundException;
import backend.backend.board.dto.BoardRequest;
import backend.backend.board.dto.BoardResponse;
import backend.backend.board.entity.Board;
import backend.backend.board.repository.BoardRepository;
import backend.backend.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;

    @Transactional
    public BoardResponse getBoardById(Long id) {
        Board board = boardRepository.findByIdWithUsername(id)
                .orElseThrow(() -> new NotFoundException(ErrorCode.BOARD_NOT_FOUND));
        board.addViewCount();
        return BoardResponse.from(board);
    }

    public Long createBoard(User user, BoardRequest boardRequest) {
        Board board = boardRequest.toEntity(user);
        return boardRepository.save(board).getId();
    }

    @Transactional
    public void updateBoard(Long id, User user, BoardRequest boardRequest) {
        Board board = boardRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(ErrorCode.BOARD_NOT_FOUND));

        if (user.hasAuthority(board.getUser().getId())) {
            throw new AuthException(ErrorCode.FORBIDDEN);
        }

        board.update(boardRequest.title(), boardRequest.category(), boardRequest.context());
    }

    public void deleteBoard(Long id, User user) {
        Board board = boardRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(ErrorCode.BOARD_NOT_FOUND));
        if (user.hasAuthority(board.getUser().getId())) {
            throw new AuthException(ErrorCode.FORBIDDEN);
        }
        boardRepository.deleteById(id);
    }

    public SingleRecordResponse<BoardResponse> findBoards(Category category, String cursor) {
        return boardRepository.findBoardsByCategory(cursor, category);
    }

    public SingleRecordResponse<BoardResponse> findMyBoards(Long userId, Category category, String cursor) {
        return boardRepository.findMyBoardsByCategory(userId, cursor, category);
    }
}
