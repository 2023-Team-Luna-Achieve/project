package backend.backend.noticeboard.service;

import backend.backend.noticeboard.dto.NoticeBoardRequestDto;
import backend.backend.noticeboard.dto.NoticeBoardResponseDto;

import java.util.List;

public interface NoticeBoardService {
    List<NoticeBoardResponseDto> getAllNoticeBoards();

    NoticeBoardResponseDto.PagedNoticeBoardResponseDto getNoticeBoards(int size, Long lastNoticeBoardId);

    List<NoticeBoardResponseDto> getNoticeBoards();

    NoticeBoardResponseDto getNoticeBoardById(Long id);
    NoticeBoardResponseDto createNoticeBoard(NoticeBoardRequestDto noticeBoardDto);
    NoticeBoardResponseDto updateNoticeBoard(Long id, NoticeBoardResponseDto noticeBoardDto);
    void deleteNoticeBoard(Long id);

    //커서기반 페이지네이션
//    NoticeBoardResponseDto.PagedNoticeBoardResponseDto getNoticeBoards(int size, Long lastNoticeBoardId);
}