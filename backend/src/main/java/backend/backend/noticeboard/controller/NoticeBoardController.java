package backend.backend.noticeboard.controller;

import backend.backend.noticeboard.dto.NoticeBoardDto;
import backend.backend.noticeboard.service.NoticeBoardService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/board")
public class NoticeBoardController {

    private final NoticeBoardService noticeBoardService;

    public NoticeBoardController(NoticeBoardService noticeBoardService) {
        this.noticeBoardService = noticeBoardService;
    }

    @GetMapping("/")
    public List<NoticeBoardDto> getAllNoticeBoards() {
        return noticeBoardService.getAllNoticeBoards();
    }

    @GetMapping("/{id}")
    public ResponseEntity<NoticeBoardDto> getNoticeBoardById(@PathVariable Long id) {
        NoticeBoardDto noticeBoardDto = noticeBoardService.getNoticeBoardById(id);
        return ResponseEntity.ok(noticeBoardDto);
    }

    @PostMapping("/")
    public ResponseEntity<NoticeBoardDto> createNoticeBoard(@RequestBody NoticeBoardDto noticeBoardDto) {
        NoticeBoardDto createdNoticeBoard = noticeBoardService.createNoticeBoard(noticeBoardDto);
        return ResponseEntity.ok(createdNoticeBoard);
    }

    @PutMapping("/{id}")
    public ResponseEntity<NoticeBoardDto> updateNoticeBoard(
            @PathVariable Long id, @RequestBody NoticeBoardDto noticeBoardDto) {
        NoticeBoardDto updatedNoticeBoard = noticeBoardService.updateNoticeBoard(id, noticeBoardDto);
        return ResponseEntity.ok(updatedNoticeBoard);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNoticeBoard(@PathVariable Long id) {
        noticeBoardService.deleteNoticeBoard(id);
        return ResponseEntity.noContent().build();
    }
}
