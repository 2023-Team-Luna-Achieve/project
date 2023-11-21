package backend.backend.noticeboard.controller;

import backend.backend.noticeboard.dto.NoticeBoardRequestDto;
import backend.backend.noticeboard.dto.NoticeBoardResponseDto;
import backend.backend.noticeboard.service.NoticeBoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;
@RequiredArgsConstructor
@RestController
@RequestMapping("/board")
public class NoticeBoardController {

    private final NoticeBoardService noticeBoardService;

    private final HttpSession httpSession;

    //모든 공지사항 글 목록 조회
    @GetMapping("/notice")
    public List<NoticeBoardResponseDto> getAllNoticeBoards() {
        return noticeBoardService.getAllNoticeBoards();
    }

    //특정 글 조회?
    @GetMapping("/{id}")
    public ResponseEntity<NoticeBoardResponseDto> getNoticeBoardById(@PathVariable Long id) {
        NoticeBoardResponseDto noticeBoardDto = noticeBoardService.getNoticeBoardById(id);
        return ResponseEntity.ok(noticeBoardDto);
    }

    //글 생성
    @PostMapping("/notice")
    public ResponseEntity<NoticeBoardResponseDto> createNoticeBoard(@RequestBody NoticeBoardRequestDto noticeBoardRequestDto) {
        NoticeBoardResponseDto createdNoticeBoard = noticeBoardService.createNoticeBoard(noticeBoardRequestDto);
        return ResponseEntity.ok(createdNoticeBoard);
    }

    //글 수정
    @PutMapping("/{id}")
    public ResponseEntity<NoticeBoardResponseDto> updateNoticeBoard(
            @PathVariable Long id, @RequestBody NoticeBoardResponseDto noticeBoardDto) {
        NoticeBoardResponseDto updatedNoticeBoard = noticeBoardService.updateNoticeBoard(id, noticeBoardDto);
        return ResponseEntity.ok(updatedNoticeBoard);
    }

    //공지사항 글 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNoticeBoard(@PathVariable Long id) {
        noticeBoardService.deleteNoticeBoard(id);
        return ResponseEntity.noContent().build();
    }
}