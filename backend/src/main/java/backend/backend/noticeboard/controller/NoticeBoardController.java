package backend.backend.noticeboard.controller;

import backend.backend.auth.jwt.CurrentUser;
import backend.backend.noticeboard.dto.NoticeBoardRequestDto;
import backend.backend.noticeboard.dto.NoticeBoardResponseDto;
import backend.backend.noticeboard.service.NoticeBoardService;
import backend.backend.user.entity.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Api(tags = "Noticeboard API", description = "게시판")
@RequiredArgsConstructor
@RequestMapping("/api/notice")
@RestController
public class NoticeBoardController {

    private final NoticeBoardService noticeBoardService;

    // 상아님 코드 전체조회 코드
    @GetMapping("/every/{id}")
    public NoticeBoardResponseDto.PagedNoticeBoardResponseDto getAllNoticeBoards(@PathVariable Long id) {
        return noticeBoardService.getNoticeBoards(5,id);
    }

    //특정 글 조회?
    @ApiOperation(value = "게시판 단일 조회 API", notes = "게시판 단일 조회를 진행한다")
    @GetMapping("/{id}")
    public ResponseEntity<NoticeBoardResponseDto> getNoticeBoardById(@PathVariable Long id) {
        NoticeBoardResponseDto noticeBoardDto = noticeBoardService.getNoticeBoardById(id);
        return ResponseEntity.ok(noticeBoardDto);
    }

    //글 생성
    @ApiOperation(value = "게시판 작성 API", notes = "게시판 작성을 진행한다. \n " +
            "category는 Notice, LostItem, Suggestion 3가지만 입력한다")
    @PostMapping
    public ResponseEntity<NoticeBoardResponseDto> createNoticeBoard(@RequestBody NoticeBoardRequestDto noticeBoardRequestDto, @CurrentUser User user) {
        NoticeBoardResponseDto createdNoticeBoard = noticeBoardService.createNoticeBoard(user, noticeBoardRequestDto);
        return ResponseEntity.ok(createdNoticeBoard);
    }

    //글 수정
    @ApiOperation(value = "게시판 수정 API", notes = "게시판 수정을 진행한다")
    @PutMapping("/{id}")
    public ResponseEntity<NoticeBoardResponseDto> updateNoticeBoard(
            @PathVariable Long id,
            @CurrentUser User user,
            @RequestBody NoticeBoardResponseDto noticeBoardDto)
    {
        NoticeBoardResponseDto updatedNoticeBoard = noticeBoardService.updateNoticeBoard(id, user, noticeBoardDto);
        return ResponseEntity.ok(updatedNoticeBoard);
    }


    //공지사항 글 삭제
    @ApiOperation(value = "게시판 삭제 API", notes = "게시판 삭제를 진행한다")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNoticeBoard(@PathVariable Long id, @CurrentUser User user) {
        noticeBoardService.deleteNoticeBoard(id, user);
        return ResponseEntity.noContent().build();
    }
}