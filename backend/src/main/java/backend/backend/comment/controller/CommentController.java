package backend.backend.comment.controller;

import backend.backend.auth.jwt.CurrentUser;
import backend.backend.comment.dto.CommentRequest;
import backend.backend.comment.dto.CommentResponse;
import backend.backend.comment.entity.Comment;
import backend.backend.comment.service.CommentService;
import backend.backend.common.dto.SingleRecordResponse;
import backend.backend.user.entity.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@Tag(name = "Notice Board Comment API", description = "공지사항 댓글")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/comments")
public class CommentController {
    private final CommentService commentService;

    @Operation(summary = "공지사항 댓글 전체 조회 API", description = "특정 공지사항의 댓글을 전체조회 한다.")
    @GetMapping("/boards/{boardId}")
    public ResponseEntity<SingleRecordResponse<CommentResponse>> getAllCommentsByBoardId(@RequestParam(required = false) String cursor,
                                                                                         @PathVariable Long boardId) {
        return ResponseEntity.ok(commentService.getAllCommentsByBoardId(boardId, cursor));
    }

    @Operation(summary = "공지사항 댓글 생성 API", description = "특정 공지사항의 댓글을 생성 한다.")
    @PostMapping
    public ResponseEntity<CommentResponse> createComment(@RequestBody CommentRequest commentRequest,
                                                         @CurrentUser User user) {
        Comment createdComment = commentService.createComment(user, commentRequest);
        return ResponseEntity.created(URI.create("/api/comments/" + createdComment.getId())).build();
    }

    @Operation(summary = "공지사항 단일 조회 API", description = "공지사항 댓글을 단일 조회 한다.")
    @GetMapping("/{commentId}")
    public ResponseEntity<CommentResponse> getCommentById(@PathVariable Long commentId) {
        return ResponseEntity.ok(commentService.findOneComment(commentId));
    }

    @PutMapping("/{commentId}")
    public ResponseEntity<Void> updateComment(@PathVariable Long commentId,
                                              @CurrentUser User user,
                                              @RequestBody CommentRequest commentRequestDto) {
        commentService.updateComment(user, commentId, commentRequestDto);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "공지사항 댓글 삭제 API", description = "공지사항 댓글을 삭제 한다.")
    @DeleteMapping("/{commentId}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long commentId,
                                              @CurrentUser User user) {
        commentService.deleteComment(user, commentId);
        return ResponseEntity.noContent().build();
    }
}
