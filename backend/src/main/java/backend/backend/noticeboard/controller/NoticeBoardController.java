package backend.backend.noticeboard.controller;

import backend.backend.auth.jwt.CurrentUser;
import backend.backend.noticeboard.dto.NoticeBoardRequestDto;
import backend.backend.noticeboard.dto.NoticeBoardResponseDto;
import backend.backend.noticeboard.service.NoticeBoardService;
import backend.backend.user.entity.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@Tag(name = "Notice Board API", description = "공지사항 게시판")
@RequiredArgsConstructor
@RequestMapping("/api/noticeboard")
@RestController
public class NoticeBoardController {

    private final NoticeBoardService noticeBoardService;

    // 상아님 코드 전체조회 코드
    @Operation(summary = "공지사항 전체조회 API", description = "공지사항 조희를 진행한다(페이지네이션)")
    @GetMapping("/cursor/{id}")
    public NoticeBoardResponseDto.PagedNoticeBoardResponseDto getAllNoticeBoards(@PathVariable Long id) {
        return noticeBoardService.getNoticeBoards(10,id);
    }

    @Operation(summary = "공지사항 단일 조회 API", description = "공지사항 단일 조회를 진행한다")
    @GetMapping("/{id}")
    public ResponseEntity<NoticeBoardResponseDto> getNoticeBoardById(@PathVariable Long id) {
        NoticeBoardResponseDto noticeBoardDto = noticeBoardService.getNoticeBoardById(id);
        return ResponseEntity.ok(noticeBoardDto);
    }

    //글 생성
    @Operation(summary = "공지사항 작성 API", description = "공지사항 작성을 진행한다. \n " +
            "category는 Notice, LostItem, Suggestion 3가지만 입력한다")
    @PostMapping
    public ResponseEntity<NoticeBoardResponseDto> createNoticeBoard(@RequestBody NoticeBoardRequestDto noticeBoardRequestDto, @CurrentUser User user) {
        Long noticeBoardId = noticeBoardService.createNoticeBoard(user, noticeBoardRequestDto);
        return ResponseEntity.created(URI.create("/api/noticeboard/" + noticeBoardId)).build();
    }

    //글 수정
    @Operation(summary = "공지사항 수정 API", description = "공지사항 수정을 진행한다")
    @PutMapping("/{noticeBoardId}")
    public ResponseEntity<NoticeBoardResponseDto> updateNoticeBoard(
            @PathVariable Long noticeBoardId,
            @CurrentUser User user,
            @RequestBody NoticeBoardRequestDto noticeBoardDto)
    {
        noticeBoardService.updateNoticeBoard(noticeBoardId, user, noticeBoardDto);
        return ResponseEntity.ok().build();
    }

    //공지사항 글 삭제
    @Operation(summary = "공지사항 삭제 API", description = "공지사항 삭제를 진행한다")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNoticeBoard(@PathVariable Long id, @CurrentUser User user) {
        noticeBoardService.deleteNoticeBoard(id, user);
        return ResponseEntity.noContent().build();
    }
}