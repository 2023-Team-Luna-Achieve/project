package backend.backend.noticeboard.controller;

import backend.backend.noticeboard.dto.NoticeBoardRequestDto;
import backend.backend.noticeboard.dto.NoticeBoardResponseDto;
import backend.backend.noticeboard.service.NoticeBoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@RequiredArgsConstructor
@RestController
@RequestMapping("/board")
public class NoticeBoardController {

    private final NoticeBoardService noticeBoardService;
    private final HttpSession httpSession;

    @GetMapping("/api/notice")
    public Page<NoticeBoardResponseDto> getAllNoticeBoards(Pageable pageable, @RequestParam(value = "cursor", required = false) Long cursor) {
        return noticeBoardService.getAllNoticeBoards(pageable, cursor);
    }

    @GetMapping("/api/notice/{id}")
    public ResponseEntity<NoticeBoardResponseDto> getNoticeBoardById(@PathVariable Long id) {
        NoticeBoardResponseDto noticeBoardDto = noticeBoardService.getNoticeBoardById(id);
        return ResponseEntity.ok(noticeBoardDto);
    }

    @PostMapping("/api/notice")
    public ResponseEntity<NoticeBoardResponseDto> createNoticeBoard(@RequestBody NoticeBoardRequestDto noticeBoardRequestDto) {
        NoticeBoardResponseDto createdNoticeBoard = noticeBoardService.createNoticeBoard(noticeBoardRequestDto);
        return ResponseEntity.ok(createdNoticeBoard);
    }

    @PutMapping("/api/notice/{id}")
    public ResponseEntity<NoticeBoardResponseDto> updateNoticeBoard(
            @PathVariable Long id, @RequestBody NoticeBoardResponseDto noticeBoardDto) {
        NoticeBoardResponseDto updatedNoticeBoard = noticeBoardService.updateNoticeBoard(id, noticeBoardDto);
        return ResponseEntity.ok(updatedNoticeBoard);
    }

    @DeleteMapping("/api/notice/{id}")
    public ResponseEntity<Void> deleteNoticeBoard(@PathVariable Long id) {
        noticeBoardService.deleteNoticeBoard(id);
        return ResponseEntity.noContent().build();
    }
}