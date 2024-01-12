package backend.backend.comment.controller;

import backend.backend.auth.jwt.CurrentUser;
import backend.backend.comment.dto.CommentRequestDto;
import backend.backend.comment.dto.NoticeBoardCommentResponse;
import backend.backend.comment.service.NoticeBoardCommentService;
import backend.backend.user.entity.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@Api(tags = "Notice Board Comment API", description = "공지사항 댓글")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/notice-comments/")
public class NoticeBoardCommentController {
    private final NoticeBoardCommentService commentService;

    @ApiOperation(value = "공지사항 댓글 전체 조회 API", notes = "특정 공지사항의 댓글을 전체조회 한다.")
    @GetMapping("/notice-board/{suggestionBoardId}")
    public ResponseEntity<List<NoticeBoardCommentResponse>> getAllCommentsByNoticeBoardId(@PathVariable Long suggestionBoardId) {
        return ResponseEntity.ok(commentService.getAllCommentsByNoticeBoardId(suggestionBoardId));
    }

    @ApiOperation(value = "공지사항 댓글 생성 API", notes = "특정 공지사항의 댓글을 생성 한다.")
    @PostMapping("/{noticeBoardId}")
    public ResponseEntity<NoticeBoardCommentResponse> createComment(@PathVariable("noticeBoardId") Long noticeBoardId,
                                                                    @RequestBody CommentRequestDto commentRequestDto,
                                                                    @CurrentUser User user) {
        NoticeBoardCommentResponse createdComment = commentService.createComment(user, noticeBoardId, commentRequestDto);
        return ResponseEntity.created(URI.create("/api/comment/" + createdComment.getId())).build();
    }

    @ApiOperation(value = "공지사항 단일 조회 API", notes = "공지사항 댓글을 단일 조회 한다.")
    @GetMapping("/{noticeCommentId}")
        public ResponseEntity<NoticeBoardCommentResponse> getCommentById(@PathVariable Long noticeCommentId) {
        NoticeBoardCommentResponse commentDto = commentService.getCommentById(noticeCommentId);
            return ResponseEntity.ok(commentDto);
    }

    @ApiOperation(value = "공지사항 댓글 삭제 API", notes = "공지사항 댓글을 삭제 한다.")
    @DeleteMapping("/{noticeCommentId}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long commentId) {
        commentService.deleteComment(commentId);
        return ResponseEntity.noContent().build();
    }
}
