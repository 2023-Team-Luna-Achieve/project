package backend.backend.noticeboard.controller;

import backend.backend.noticeboard.entity.Notice;
import backend.backend.noticeboard.repository.NoticeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

public class NoticeController {
    @RestController
    @RequestMapping("/notices")
    public static class NoticeController {
        @Autowired
        private backend.backend.noticeboard.repository.NoticeRepository.NoticeRepository noticeRepository;

        public NoticeController(NoticeRepository.NoticeRepository noticeRepository) {
            this.noticeRepository = noticeRepository;
        }

        @GetMapping("/")
        public List<Notice> getAllNotices() {
            return noticeRepository.findAll();
        }

        @GetMapping("/{id}")
        public ResponseEntity<Notice> getNoticeById(@PathVariable Long id) {
            Optional<Notice> notice = noticeRepository.findById(id);
            return notice.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
        }

        @PostMapping("/")
        public Notice createNotice(@RequestBody Notice notice) {
            return noticeRepository.save(notice);
        }

        @PutMapping("/{id}")
        public ResponseEntity<Notice> updateNotice(@PathVariable Long id, @RequestBody Notice updatedNotice) {
            if (!noticeRepository.existsById(id)) {
                return ResponseEntity.notFound().build();
            }
            updatedNotice.setId(id);
            return ResponseEntity.ok(noticeRepository.save(updatedNotice));
        }

        @DeleteMapping("/{id}")
        public ResponseEntity<Void> deleteNotice(@PathVariable Long id) {
            if (!noticeRepository.existsById(id)) {
                return ResponseEntity.notFound().build();
            }
            noticeRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
    }
}
