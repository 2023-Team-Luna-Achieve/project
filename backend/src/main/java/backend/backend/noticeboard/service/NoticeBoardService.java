package backend.backend.noticeboard.service;

import backend.backend.noticeboard.dto.NoticeBoardDto;

import java.util.List;

public interface NoticeBoardService {
    List<NoticeBoardDto> getAllNoticeBoards();
    NoticeBoardDto getNoticeBoardById(Long id);
    NoticeBoardDto createNoticeBoard(NoticeBoardDto noticeBoardDto);
    NoticeBoardDto updateNoticeBoard(Long id, NoticeBoardDto noticeBoardDto);
    void deleteNoticeBoard(Long id);
}
