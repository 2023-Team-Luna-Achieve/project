package backend.backend.noticeboard.service;

import backend.backend.noticeboard.dto.NoticeBoardRequestDto;
import backend.backend.noticeboard.dto.NoticeBoardResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface NoticeBoardService {
    Page<NoticeBoardResponseDto> getAllNoticeBoards(Pageable pageable, Long cursor);
    NoticeBoardResponseDto getNoticeBoardById(Long id);
    NoticeBoardResponseDto createNoticeBoard(NoticeBoardRequestDto noticeBoardDto);
    NoticeBoardResponseDto updateNoticeBoard(Long id, NoticeBoardResponseDto noticeBoardDto);
    void deleteNoticeBoard(Long id);
}
