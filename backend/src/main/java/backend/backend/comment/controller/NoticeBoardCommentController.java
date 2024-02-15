package backend.backend.comment.controller;

import backend.backend.auth.jwt.CurrentUser;
import backend.backend.comment.dto.CommentRequestDto;
import backend.backend.comment.dto.NoticeBoardCommentResponse;
import backend.backend.comment.entity.NoticeBoardComment;
import backend.backend.comment.service.NoticeBoardCommentService;
import backend.backend.user.entity.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@Tag(name = "Notice Board Comment API", description = "공지사항 댓글")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/notice-comments/")
public class NoticeBoardCommentController {
    private final NoticeBoardCommentService commentService;

    @Operation(summary = "공지사항 댓글 전체 조회 API", description = "특정 공지사항의 댓글을 전체조회 한다.")
    @GetMapping("/notice-board/{suggestionBoardId}")
    public ResponseEntity<List<NoticeBoardCommentResponse>> getAllCommentsByNoticeBoardId(@PathVariable Long suggestionBoardId) {
        return ResponseEntity.ok(commentService.getAllCommentsByNoticeBoardId(suggestionBoardId));
    }

    @Operation(summary = "공지사항 댓글 생성 API", description = "특정 공지사항의 댓글을 생성 한다.")
    @PostMapping("/{noticeBoardId}")
    public ResponseEntity<NoticeBoardCommentResponse> createComment(@PathVariable("noticeBoardId") Long noticeBoardId,
                                                                    @RequestBody CommentRequestDto commentRequestDto,
                                                                    @CurrentUser User user) {
        NoticeBoardComment createdComment = commentService.createComment(user, noticeBoardId, commentRequestDto);
        return ResponseEntity.created(URI.create("/api/noticeboardcomment/" + createdComment.getId())).build();
    }

    @Operation(summary = "공지사항 단일 조회 API", description = "공지사항 댓글을 단일 조회 한다.")
    @GetMapping("/{noticeCommentId}")
    public ResponseEntity<NoticeBoardCommentResponse> getCommentById(@PathVariable Long noticeCommentId) {
        return ResponseEntity.ok(commentService.getOneNoticeBoardComment(noticeCommentId));
    }

    @PutMapping("/{noticeCommentId}")
    public ResponseEntity<Void> updateComment(@PathVariable Long noticeCommentId,
                                              @CurrentUser User user,
                                              @RequestBody CommentRequestDto commentRequestDto) {
        commentService.updateNoticeBoardComment(user, noticeCommentId, commentRequestDto);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "공지사항 댓글 삭제 API", description = "공지사항 댓글을 삭제 한다.")
    @DeleteMapping("/{noticeCommentId}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long noticeCommentId,
                                              @CurrentUser User user) {
        commentService.deleteComment(user, noticeCommentId);
        return ResponseEntity.noContent().build();
    }
}
