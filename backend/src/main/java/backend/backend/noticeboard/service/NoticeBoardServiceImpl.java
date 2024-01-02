package backend.backend.noticeboard.service;

import backend.backend.exception.ErrorCode;
import backend.backend.exception.NotFoundException;
import backend.backend.noticeboard.dto.NoticeBoardRequestDto;
import backend.backend.noticeboard.dto.NoticeBoardResponseDto;
import backend.backend.noticeboard.entity.NoticeBoard;
import backend.backend.noticeboard.repository.NoticeBoardRepository;
import backend.backend.noticeboard.validator.AuthorizationValidator;
import backend.backend.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NoticeBoardServiceImpl implements NoticeBoardService {

    private final NoticeBoardRepository noticeBoardRepository;
    private final AuthorizationValidator authorizationValidator;

    @Override
    public NoticeBoardResponseDto getNoticeBoardById(Long id) {
        NoticeBoard noticeBoard = noticeBoardRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(ErrorCode.ENTITY_NOT_FOUND));
        return convertToDto(noticeBoard);
    }

    @Override
    public NoticeBoardResponseDto createNoticeBoard(User user, NoticeBoardRequestDto noticeBoardRequestDto) {
        NoticeBoard noticeBoard = convertToEntity(user, noticeBoardRequestDto);
        noticeBoard = noticeBoardRepository.save(noticeBoard);
        return convertToDto(noticeBoard);
    }

    @Override
    public NoticeBoardResponseDto updateNoticeBoard(Long id, User user, NoticeBoardResponseDto noticeBoardDto) {
        NoticeBoard existingNoticeBoard = noticeBoardRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(ErrorCode.ENTITY_NOT_FOUND));
        authorizationValidator.validateNoticeBoardModifyOrDeletePermission(user, existingNoticeBoard);
        existingNoticeBoard.setTitle(noticeBoardDto.getTitle());
        existingNoticeBoard.setCategory(noticeBoardDto.getCategory());
        existingNoticeBoard.setContext(noticeBoardDto.getContext());

        noticeBoardRepository.save(existingNoticeBoard);
        return convertToDto(existingNoticeBoard);
    }

    @Override
    public void deleteNoticeBoard(Long id, User user) {
        NoticeBoard noticeBoard = noticeBoardRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(ErrorCode.ENTITY_NOT_FOUND));
        authorizationValidator.validateNoticeBoardModifyOrDeletePermission(user, noticeBoard);
        noticeBoardRepository.deleteById(id);
    }

    private NoticeBoardResponseDto convertToDto(NoticeBoard noticeBoard) {
        if (noticeBoard == null) {
            return null;
        }

        NoticeBoardResponseDto noticeBoardResponseDto = new NoticeBoardResponseDto();
        noticeBoardResponseDto.setId(noticeBoard.getId());
        noticeBoardResponseDto.setTitle(noticeBoard.getTitle());
        noticeBoardResponseDto.setCategory(noticeBoard.getCategory());
        noticeBoardResponseDto.setContext(noticeBoard.getContext());
        return noticeBoardResponseDto;
    }

    private NoticeBoard convertToEntity(User user, NoticeBoardRequestDto noticeBoardDto) {
        if (noticeBoardDto == null) {
            return null;
        }

        NoticeBoard noticeBoard = new NoticeBoard();
        noticeBoard.setTitle(noticeBoardDto.getTitle());
        noticeBoard.setCategory(noticeBoardDto.getCategory());
        noticeBoard.setContext(noticeBoardDto.getContext());
        noticeBoard.setUser(user);
        return noticeBoard;
    }

    //커서기반 페이지네이션
    @Override
    public NoticeBoardResponseDto.PagedNoticeBoardResponseDto getNoticeBoards(int size, Long lastNoticeBoardId) {
        PageRequest pageRequest = PageRequest.of(0, size + 1);
        Page<NoticeBoard> page = noticeBoardRepository.findAllByIdLessThanOrderByIdDesc(lastNoticeBoardId, pageRequest);
        List<NoticeBoard> noticeBoards = page.getContent();

        long nextCursor = -1L;
        if (page.hasNext()) {
            nextCursor = noticeBoards.get(noticeBoards.size() - 1).getId();
        }

        NoticeBoardResponseDto.PagedNoticeBoardResponseDto response = new NoticeBoardResponseDto.PagedNoticeBoardResponseDto();
        response.setContents(noticeBoards.stream().map(this::mapToDto).collect(Collectors.toList()));
        response.setTotalElements(noticeBoardRepository.count());
        response.setNextCursor(nextCursor);

        return response;
    }

    private NoticeBoardResponseDto mapToDto(NoticeBoard noticeBoard) {
        if (noticeBoard == null) {
            throw new IllegalArgumentException("NoticeBoard cannot be null");
        }

        NoticeBoardResponseDto noticeBoardResponseDto = new NoticeBoardResponseDto();
        noticeBoardResponseDto.setId(noticeBoard.getId());
        noticeBoardResponseDto.setTitle(noticeBoard.getTitle());
        noticeBoardResponseDto.setCategory(noticeBoard.getCategory());
        noticeBoardResponseDto.setContext(noticeBoard.getContext());

        return noticeBoardResponseDto;
    }
}
