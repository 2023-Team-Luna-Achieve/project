package backend.backend.board.service;

import backend.backend.board.entity.Category;
import backend.backend.common.dto.SingleRecordResponse;
import backend.backend.common.exception.AuthException;
import backend.backend.common.exception.ErrorCode;
import backend.backend.common.exception.NotFoundException;
import backend.backend.board.dto.BoardRequestOnlyJson;
import backend.backend.board.dto.BoardResponse;
import backend.backend.board.entity.Board;
import backend.backend.board.repository.BoardRepository;
import backend.backend.common.image.S3Service;
import backend.backend.image.domain.BoardImage;
import backend.backend.image.repository.BoardImageRepository;
import backend.backend.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;
    private final S3Service s3Service;
    private final BoardImageRepository boardImageRepository;

    @Transactional
    public BoardResponse getBoardById(Long id) {
        Board board = boardRepository.findByIdWithUsername(id)
                .orElseThrow(() -> new NotFoundException(ErrorCode.BOARD_NOT_FOUND));
        board.addViewCount();
        return BoardResponse.from(board);
    }

    public Long createBoard(User user, BoardRequestOnlyJson boardRequestOnlyJson, List<MultipartFile> files) {
        Board board = boardRepository.save(boardRequestOnlyJson.toEntity(user));

        files.forEach(
                file -> {
                    BoardImage image = BoardImage.builder()
                            .imageUrl(s3Service.upload(file))
                            .board(board)
                            .build();

                    boardImageRepository.save(image);
                }
        );

        return board.getId();
    }

    @Transactional
    public void updateBoard(Long id, User user, BoardRequestOnlyJson boardRequestOnlyJson) {
        Board board = boardRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(ErrorCode.BOARD_NOT_FOUND));

        if (user.hasAuthority(board.getUser().getId())) {
            throw new AuthException(ErrorCode.FORBIDDEN);
        }

        board.update(boardRequestOnlyJson.title(), boardRequestOnlyJson.category(), boardRequestOnlyJson.context());
    }

    public void deleteBoard(Long id, User user) {
        Board board = boardRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(ErrorCode.BOARD_NOT_FOUND));
        if (user.hasAuthority(board.getUser().getId())) {
            throw new AuthException(ErrorCode.FORBIDDEN);
        }
        boardRepository.deleteById(id);
    }

    public SingleRecordResponse<BoardResponse> findBoards(Category category, String cursor, User currentUser) {
        return boardRepository.findBoardsByCategory(cursor, category, currentUser.getId());
    }

    public SingleRecordResponse<BoardResponse> findMyBoards(Long userId, Category category, String cursor) {
        return boardRepository.findMyBoardsByCategory(userId, cursor, category);
    }
}
