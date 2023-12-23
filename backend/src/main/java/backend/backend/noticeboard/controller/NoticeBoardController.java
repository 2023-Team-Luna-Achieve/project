package backend.backend.noticeboard.controller;

import backend.backend.noticeboard.dto.NoticeBoardRequestDto;
import backend.backend.noticeboard.dto.NoticeBoardResponseDto;
import backend.backend.noticeboard.service.NoticeBoardService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "Noticeboard API", description = "게시판")
@RequiredArgsConstructor
@RequestMapping("/api/notice")
@RestController
public class NoticeBoardController {

    private final NoticeBoardService noticeBoardService;


    // 서인님 코드 전체조회 코드
    @GetMapping
    public Page<NoticeBoardResponseDto> getAllNoticeBoards(Pageable pageable, @RequestParam(value = "cursor", required = false) Long cursor) {
        return noticeBoardService.getAllNoticeBoards(pageable, cursor);
    }

    // 상아님 코드 전체조회 코드
    @GetMapping("/{id}")
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
    public ResponseEntity<NoticeBoardResponseDto> createNoticeBoard(@RequestBody NoticeBoardRequestDto noticeBoardRequestDto) {
        NoticeBoardResponseDto createdNoticeBoard = noticeBoardService.createNoticeBoard(noticeBoardRequestDto);
        return ResponseEntity.ok(createdNoticeBoard);
    }


    //글 수정
    @ApiOperation(value = "게시판 수정 API", notes = "게시판 수정을 진행한다")
    @PutMapping("/{id}")
    public ResponseEntity<NoticeBoardResponseDto> updateNoticeBoard(
            @PathVariable Long id, @RequestBody NoticeBoardResponseDto noticeBoardDto) {
        NoticeBoardResponseDto updatedNoticeBoard = noticeBoardService.updateNoticeBoard(id, noticeBoardDto);
        return ResponseEntity.ok(updatedNoticeBoard);
    }


    //공지사항 글 삭제
    @ApiOperation(value = "게시판 삭제 API", notes = "게시판 삭제를 진행한다")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNoticeBoard(@PathVariable Long id) {
        noticeBoardService.deleteNoticeBoard(id);
        return ResponseEntity.noContent().build();
    }
}