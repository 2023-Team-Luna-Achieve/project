package backend.backend.noticeboard.controller;

import backend.backend.noticeboard.dto.NoticeBoardRequestDto;
import backend.backend.noticeboard.dto.NoticeBoardResponseDto;
import backend.backend.noticeboard.service.NoticeBoardService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "Noticeboard API", description = "게시판")
@RequiredArgsConstructor
@RestController
public class NoticeBoardController {

    private final NoticeBoardService noticeBoardService;

    @ApiOperation(value = "게시판 전체 조회 API", notes = "존재하는 전체 게시판 조회를 진행한다")
    @GetMapping("/notice")
    public List<NoticeBoardResponseDto> getAllNoticeBoards() {
        return noticeBoardService.getAllNoticeBoards();
    }

    @ApiOperation(value = "게시판 단일 조회 API", notes = "게시판 단일 조회를 진행한다")
    @GetMapping("/{id}")
    public ResponseEntity<NoticeBoardResponseDto> getNoticeBoardById(@PathVariable Long id) {
        NoticeBoardResponseDto noticeBoardDto = noticeBoardService.getNoticeBoardById(id);
        return ResponseEntity.ok(noticeBoardDto);
    }

    @ApiOperation(value = "게시판 작성 API", notes = "게시판 작성을 진행한다. \n " +
            "category는 Notice, LostItem, Suggestion 3가지만 입력한다")
    @PostMapping("/notice")
    public ResponseEntity<NoticeBoardResponseDto> createNoticeBoard(@RequestBody NoticeBoardRequestDto noticeBoardRequestDto) {
        NoticeBoardResponseDto createdNoticeBoard = noticeBoardService.createNoticeBoard(noticeBoardRequestDto);
        return ResponseEntity.ok(createdNoticeBoard);
    }

    @ApiOperation(value = "게시판 수정 API", notes = "게시판 수정을 진행한다")
    @PutMapping("/{id}")
    public ResponseEntity<NoticeBoardResponseDto> updateNoticeBoard(
            @PathVariable Long id, @RequestBody NoticeBoardResponseDto noticeBoardDto) {
        NoticeBoardResponseDto updatedNoticeBoard = noticeBoardService.updateNoticeBoard(id, noticeBoardDto);
        return ResponseEntity.ok(updatedNoticeBoard);
    }

    @ApiOperation(value = "게시판 삭제 API", notes = "게시판 삭제를 진행한다")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNoticeBoard(@PathVariable Long id) {
        noticeBoardService.deleteNoticeBoard(id);
        return ResponseEntity.noContent().build();
    }
}