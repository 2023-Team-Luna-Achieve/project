package backend.backend.comment.controller;

import backend.backend.auth.jwt.CurrentUser;
import backend.backend.comment.dto.CommentRequestDto;
import backend.backend.comment.dto.SuggestionCommentResponseDto;
import backend.backend.comment.entity.SuggestionComment;
import backend.backend.comment.service.SuggestionCommentService;
import backend.backend.user.entity.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@Api(tags = "Suggestion Board Comment API", description = "건의사항 댓글")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/suggestion-comments/")
public class SuggestionCommentController {
    private final SuggestionCommentService suggestionCommentService;

    @ApiOperation(value = "건의사항 댓글 전체 조회 API", notes = "특정 건의사항의 댓글을 전체조회 한다.")
    @GetMapping("/suggestion-board/{suggestionBoardId}")
    public ResponseEntity<List<SuggestionCommentResponseDto>> getAllCommentsByNoticeBoardId(@PathVariable Long suggestionBoardId) {
        return ResponseEntity.ok(suggestionCommentService.findAllSuggestionCommentsBySuggestionBoardId(suggestionBoardId));
    }

    @ApiOperation(value = "건의사항 댓글 단일 생성 API", notes = "특정 건의사항의 댓글을 생성 한다.")
    @PostMapping("/{suggestionBoardId}")
    public ResponseEntity<Void> createComment(@PathVariable Long suggestionBoardId,
                                              @CurrentUser User user,
                                              @RequestBody CommentRequestDto commentRequestDto) {
        SuggestionComment suggestionComment = suggestionCommentService.createSuggestionComment(user, suggestionBoardId, commentRequestDto);
        return ResponseEntity.created(URI.create("/api/comment/" + suggestionComment.getId())).build();
    }

    @ApiOperation(value = "건의사항 댓글 단일 조회 API", notes = "특정 건의사항의 댓글을 단일 조회 한다.")
    @GetMapping("/{suggestionCommentId}")
    public ResponseEntity<SuggestionCommentResponseDto> getOneSuggestionComment(@PathVariable Long suggestionCommentId) {
        return ResponseEntity.ok(suggestionCommentService.findOneSuggestionComment(suggestionCommentId));
    }

    @ApiOperation(value = "건의사항 댓글 수정 API", notes = "특정 건의사항의 댓글을 수정한다.")
    @PutMapping("/{suggestionCommentId}")
    public ResponseEntity<Void> updateSuggestionComment(@PathVariable Long suggestionCommentId,
                                                        @RequestBody CommentRequestDto commentRequestDto,
                                                        @CurrentUser User user) {
        suggestionCommentService.updateSuggestionComment(user, suggestionCommentId, commentRequestDto);
        return ResponseEntity.ok().build();
    }


    @ApiOperation(value = "건의사항 댓글 삭제 API", notes = "특정 건의사항의 댓글을 단일 조회 한다.")
    @DeleteMapping("/{suggestionCommentId}")
    public ResponseEntity<Void> deleteOneSuggestionComment(@PathVariable Long suggestionCommentId,
                                                           @CurrentUser User user) {
        suggestionCommentService.deleteSuggestionCommentById(user, suggestionCommentId);
        return ResponseEntity.noContent().build();
    }
}
