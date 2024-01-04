package backend.backend.noticeboard.service;

import backend.backend.noticeboard.dto.NoticeBoardRequestDto;
import backend.backend.noticeboard.dto.NoticeBoardResponseDto;
import backend.backend.noticeboard.validator.AuthorizationValidator;
import backend.backend.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface NoticeBoardService {
//    Page<NoticeBoardResponseDto> getAllNoticeBoards(Pageable pageable, Long offset);

    NoticeBoardResponseDto.PagedNoticeBoardResponseDto getNoticeBoards(int size, Long lastNoticeBoardId);

    NoticeBoardResponseDto getNoticeBoardById(Long id);

    NoticeBoardResponseDto createNoticeBoard(User user, NoticeBoardRequestDto noticeBoardDto);

    NoticeBoardResponseDto updateNoticeBoard(Long id, User user, NoticeBoardRequestDto noticeBoardDto);

    void deleteNoticeBoard(Long id, User user);
}


    //커서기반 페이지네이션
//    NoticeBoardResponseDto.PagedNoticeBoardResponseDto getNoticeBoards(int size, Long lastNoticeBoardId);

