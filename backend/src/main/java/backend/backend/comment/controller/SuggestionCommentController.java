package backend.backend.comment.controller;

import backend.backend.auth.jwt.CurrentUser;
import backend.backend.comment.dto.CommentRequest;
import backend.backend.comment.dto.CommentResponse;
import backend.backend.comment.dto.SuggestionCommentResponseDto;
import backend.backend.comment.entity.Comment;
import backend.backend.comment.service.CommentService;
import backend.backend.user.entity.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@Tag(name = "Suggestion Board Comment API", description = "건의사항 댓글")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/comments/")
public class SuggestionCommentController {
    private final CommentService commentService;

    @Operation(summary = "건의사항 댓글 전체 조회 API", description = "특정 건의사항의 댓글을 전체조회 한다.")
    @GetMapping("/suggestion-board/{suggestionBoardId}")
    public ResponseEntity<List<CommentResponse>> getAllCommentsByBoardId(@PathVariable Long suggestionBoardId) {
        return ResponseEntity.ok(commentService.getAllCommentsByBoardId(suggestionBoardId));
    }

    @Operation(summary = "건의사항 댓글 단일 생성 API", description = "특정 건의사항의 댓글을 생성 한다.")
    @PostMapping("/{suggestionBoardId}")
    public ResponseEntity<Void> createComment(@PathVariable Long suggestionBoardId,
                                              @CurrentUser User user,
                                              @RequestBody CommentRequest commentRequestDto) {
        Comment comment = commentService.createComment(user, suggestionBoardId, commentRequestDto);
        return ResponseEntity.created(URI.create("/api/comments/" + comment.getId())).build();
    }

    @Operation(summary = "건의사항 댓글 단일 조회 API", description = "특정 건의사항의 댓글을 단일 조회 한다.")
    @GetMapping("/{suggestionCommentId}")
    public ResponseEntity<CommentResponse> getOneSuggestionComment(@PathVariable Long suggestionCommentId) {
        return ResponseEntity.ok(commentService.findOneComment(suggestionCommentId));
    }

    @Operation(summary = "건의사항 댓글 수정 API", description = "특정 건의사항의 댓글을 수정한다.")
    @PutMapping("/{suggestionCommentId}")
    public ResponseEntity<Void> updateSuggestionComment(@PathVariable Long suggestionCommentId,
                                                        @RequestBody CommentRequest commentRequestDto,
                                                        @CurrentUser User user) {
        commentService.updateComment(user, suggestionCommentId, commentRequestDto);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "건의사항 댓글 삭제 API", description = "특정 건의사항의 댓글을 단일 조회 한다.")
    @DeleteMapping("/{suggestionCommentId}")
    public ResponseEntity<Void> deleteOneSuggestionComment(@PathVariable Long suggestionCommentId,
                                                           @CurrentUser User user) {
        commentService.deleteComment(user, suggestionCommentId);
        return ResponseEntity.noContent().build();
    }
}
