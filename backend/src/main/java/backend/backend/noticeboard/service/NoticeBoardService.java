package backend.backend.noticeboard.service;

import backend.backend.noticeboard.dto.NoticeBoardRequestDto;
import backend.backend.noticeboard.dto.NoticeBoardResponseDto;

import java.util.List;

public interface NoticeBoardService {
    List<NoticeBoardResponseDto> getAllNoticeBoards();
    NoticeBoardResponseDto getNoticeBoardById(Long id);
    NoticeBoardResponseDto createNoticeBoard(NoticeBoardRequestDto noticeBoardDto);
    NoticeBoardResponseDto updateNoticeBoard(Long id, NoticeBoardResponseDto noticeBoardDto);
    void deleteNoticeBoard(Long id);
}