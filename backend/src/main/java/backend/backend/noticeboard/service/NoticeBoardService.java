package backend.backend.noticeboard.service;

import backend.backend.noticeboard.dto.NoticeBoardRequestDto;
import backend.backend.noticeboard.dto.NoticeBoardResponseDto;
import backend.backend.user.entity.User;
public interface NoticeBoardService {
//    Page<NoticeBoardResponseDto> getAllNoticeBoards(Pageable pageable, Long offset);

    NoticeBoardResponseDto.PagedNoticeBoardResponseDto getNoticeBoards(int size, Long lastNoticeBoardId);

    NoticeBoardResponseDto getNoticeBoardById(Long id);

    Long createNoticeBoard(User user, NoticeBoardRequestDto noticeBoardDto);

    void updateNoticeBoard(Long id, User user, NoticeBoardRequestDto noticeBoardDto);

    void deleteNoticeBoard(Long id, User user);
}

    //커서기반 페이지네이션
//    NoticeBoardResponseDto.PagedNoticeBoardResponseDto getNoticeBoards(int size, Long lastNoticeBoardId);

