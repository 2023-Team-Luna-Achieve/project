package backend.backend.noticeboardcomment.controller;

import backend.backend.noticeboardcomment.dto.CommentRequestDto;
import backend.backend.noticeboardcomment.dto.CommentResponseDto;
import backend.backend.noticeboardcomment.service.CommentService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "Comment API", description = "댓글")
@RequiredArgsConstructor
@RestController
@RequestMapping("/comments")
public class CommentController {

    private final CommentService commentService;

    @GetMapping("/notice-board/{noticeBoardId}")
    public List<CommentResponseDto> getAllCommentsByNoticeBoardId(@PathVariable Long noticeBoardId) {
        return commentService.getAllCommentsByNoticeBoardId(noticeBoardId);
    }

    @PostMapping("/create")
    public ResponseEntity<CommentResponseDto> createComment(@RequestBody CommentRequestDto commentRequestDto) {
        CommentResponseDto createdComment = commentService.createComment(commentRequestDto);
        return ResponseEntity.ok(createdComment);
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long commentId) {
        commentService.deleteComment(commentId);
        return ResponseEntity.noContent().build();
    }

    // 추가적인 기능이 있다면 여기에 추가

    @GetMapping("/{commentId}")
    public ResponseEntity<CommentResponseDto> getCommentById(@PathVariable Long commentId) {
        CommentResponseDto commentDto = commentService.getCommentById(commentId);
        return ResponseEntity.ok(commentDto);
    }
}