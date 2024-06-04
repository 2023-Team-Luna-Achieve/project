package backend.backend.board.controller;

import backend.backend.auth.jwt.CurrentUser;
import backend.backend.board.dto.BoardRequestOnlyJson;
import backend.backend.board.dto.BoardResponse;
import backend.backend.board.entity.Category;
import backend.backend.board.service.BoardService;
import backend.backend.common.dto.SingleRecordResponse;
import backend.backend.user.entity.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.net.URI;
import java.util.List;

@Tag(name = "Board API", description = "공지사항 게시판")
@RequiredArgsConstructor
@RequestMapping("/api/boards")
@RestController
public class BoardController {
    private final BoardService boardService;

    @Operation(summary = "공지사항 전체조회 API", description = "공지사항 조희를 진행한다")
    @GetMapping
    public ResponseEntity<SingleRecordResponse<BoardResponse>> getAllBoards(@CurrentUser User user,
                                                                            @RequestParam(value = "category") Category category,
                                                                            @RequestParam(required = false) String cursor) {
        return ResponseEntity.ok().body(boardService.findBoards(category, cursor, user));
    }

    @Operation(summary = "본인이 작성한 공지사항 API", description = "작성한 공지사항 조희를 진행한다")
    @GetMapping("/my")
    public ResponseEntity<SingleRecordResponse<BoardResponse>> getAllMyBoards(@CurrentUser User user,
                                                                              @RequestParam(value = "category") Category category,
                                                                              @RequestParam(required = false) String cursor) {
        return ResponseEntity.ok().body(boardService.findMyBoards(user.getId(), category, cursor));
    }

    @Operation(summary = "공지사항 단일 조회 API", description = "공지사항 단일 조회를 진행한다")
    @GetMapping("/{boardId}")
    public ResponseEntity<BoardResponse> getBoardById(@PathVariable Long boardId) {
        BoardResponse boardResponse = boardService.getBoardById(boardId);
        return ResponseEntity.ok(boardResponse);
    }

    @Operation(summary = "공지사항 작성 API", description = "공지사항 작성을 진행한다. \n " +
            "category는 NOTICE, LOST_ITEM, SUGGESTION 3가지만 입력한다")
    @PostMapping
    public ResponseEntity<BoardResponse> createBoard(@CurrentUser User user,
                                                     @RequestPart BoardRequestOnlyJson boardRequestOnlyJson,
                                                     @RequestPart(required = false) List<MultipartFile> files) {
        Long boardId = boardService.createBoard(user, boardRequestOnlyJson, files);
        return ResponseEntity.created(URI.create("/api/boards/" + boardId)).build();
    }

    @Operation(summary = "공지사항 수정 API", description = "공지사항 수정을 진행한다")
    @PutMapping("/{boardId}")
    public ResponseEntity<BoardResponse> updateBoard(@CurrentUser User user,
                                                     @PathVariable Long boardId,
                                                     @RequestBody BoardRequestOnlyJson boardRequestOnlyJson) {
        boardService.updateBoard(boardId, user, boardRequestOnlyJson);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "공지사항 삭제 API", description = "공지사항 삭제를 진행한다")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBoard(@CurrentUser User user,
                                            @PathVariable Long id) {
        boardService.deleteBoard(id, user);
        return ResponseEntity.noContent().build();
    }
}