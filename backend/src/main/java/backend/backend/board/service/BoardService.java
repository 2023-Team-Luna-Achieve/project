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
        Long boardsSequenceNumber = boardRepository.countBoardsByCategory(boardRequest.category());
        Board board = boardRequest.toEntity(user, boardsSequenceNumber + 1);
        return  boardRepository.save(board).getId();
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
    //커서기반 페이지네이션
    public SingleRecordResponse<BoardResponse> getBoards(Category category, String cursor) {
        if (category.equals(Category.NOTICE)) {
            if (cursor.equals("0")) {
                return boardRepository.findFirstNoticeBoardsByIdDesc();
            }

            return boardRepository.findNoticeBoardsByOrderByIdDesc(cursor);
        }

        if (category.equals(Category.SUGGESTION)) {
            return boardRepository.findSuggestionBoardsByOrderByIdDesc(cursor);
        }

        return  boardRepository.findLostItemBoardsByOrderByIdDesc(cursor);
    }
}
