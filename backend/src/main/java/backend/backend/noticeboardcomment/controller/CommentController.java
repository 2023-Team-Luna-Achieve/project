//package backend.backend.noticeboardcomment.controller;
//
//import backend.backend.noticeboardcomment.dto.CommentRequestDto;
//import backend.backend.noticeboardcomment.dto.CommentResponseDto;
//import backend.backend.noticeboardcomment.service.CommentService;
//import io.swagger.annotations.Api;
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@Api(tags = "Comment API", description = "댓글")
//@RequiredArgsConstructor
//@RestController
//@RequestMapping("/comments")
//public class CommentController {
//
//    private final CommentService commentService;
//
//    @GetMapping("/notice-board/{noticeBoardId}")
//    public List<CommentResponseDto> getAllCommentsByNoticeBoardId(@PathVariable Long noticeBoardId) {
//        return commentService.getAllCommentsByNoticeBoardId(noticeBoardId);
//    }
//
//    @PostMapping("/create")
//    public ResponseEntity<CommentResponseDto> createComment(@RequestBody CommentRequestDto commentRequestDto) {
//        CommentResponseDto createdComment = commentService.createComment(commentRequestDto);
//        return ResponseEntity.ok(createdComment);
//    }
//
//    @DeleteMapping("/{commentId}")
//    public ResponseEntity<Void> deleteComment(@PathVariable Long commentId) {
//        commentService.deleteComment(commentId);
//        return ResponseEntity.noContent().build();
//    }
//
//    // 추가적인 기능이 있다면 여기에 추가
//
//    @GetMapping("/{commentId}")
//    public ResponseEntity<CommentResponseDto> getCommentById(@PathVariable Long commentId) {
//        CommentResponseDto commentDto = commentService.getCommentById(commentId);
//        return ResponseEntity.ok(commentDto);
//    }
//}


package backend.backend.noticeboardcomment.controller;

import backend.backend.auth.jwt.CurrentUser;
import backend.backend.noticeboardcomment.dto.CommentRequestDto;
import backend.backend.noticeboardcomment.dto.CommentResponse;
import backend.backend.noticeboardcomment.service.CommentService;
import backend.backend.user.entity.User;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@ApiOperation(value = "게시글 댓글 API", notes = "회원 작성글의 댓글을 작성한다.")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class CommentController {
    private final CommentService commentService;

    @GetMapping("/{noticeBoardId}")
    public List<CommentResponse> getAllCommentsByNoticeBoardId(@PathVariable Long noticeBoardId) {
        return commentService.getAllCommentsByNoticeBoardId(noticeBoardId);
    }

    @PostMapping("/noticeboard/{noticeBoardId}")
    public ResponseEntity<CommentResponse> createComment(@PathVariable("noticeBoardId") Long noticeBoardId,
                                                         @RequestBody CommentRequestDto commentRequestDto,
                                                         @CurrentUser User user) {
        CommentResponse createdComment = commentService.createComment(user, noticeBoardId, commentRequestDto);
        return ResponseEntity.ok(createdComment);
    }

    @GetMapping("/comments/{commentId}")
        public ResponseEntity<CommentResponse> getCommentById(@PathVariable Long commentId) {
        CommentResponse commentDto = commentService.getCommentById(commentId);
            return ResponseEntity.ok(commentDto);
    }

    @DeleteMapping("/comments/{commentId}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long commentId) {
        commentService.deleteComment(commentId);
        return ResponseEntity.noContent().build();
    }
}
