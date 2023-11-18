package backend.backend.noticeboard.service;

import backend.backend.noticeboard.dto.NoticeBoardRequestDto;
import backend.backend.noticeboard.dto.NoticeBoardResponseDto;
import backend.backend.noticeboard.entity.NoticeBoard;
import backend.backend.noticeboard.repository.NoticeBoardRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class NoticeBoardServiceImpl implements NoticeBoardService {

    private final NoticeBoardRepository noticeBoardRepository;

    public NoticeBoardServiceImpl(NoticeBoardRepository noticeBoardRepository) {
        this.noticeBoardRepository = noticeBoardRepository;
    }

    @Override
    public List<NoticeBoardResponseDto> getAllNoticeBoards() {
        return noticeBoardRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public NoticeBoardResponseDto getNoticeBoardById(Long id) {
        NoticeBoard noticeBoard = noticeBoardRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("NoticeBoard not found with id: " + id));
        return convertToDto(noticeBoard);
    }

    @Override
    public NoticeBoardResponseDto createNoticeBoard(NoticeBoardRequestDto noticeBoardRequestDto) {
        NoticeBoard noticeBoard = convertToEntity(noticeBoardRequestDto);
        noticeBoard = noticeBoardRepository.save(noticeBoard);
        return convertToDto(noticeBoard);
    }

    @Override
    public NoticeBoardResponseDto updateNoticeBoard(Long id, NoticeBoardResponseDto noticeBoardDto) {
        NoticeBoard existingNoticeBoard = noticeBoardRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("NoticeBoard not found with id: " + id));

        existingNoticeBoard.setTitle(noticeBoardDto.getTitle());
        existingNoticeBoard.setCategory(noticeBoardDto.getCategory());
        existingNoticeBoard.setContext(noticeBoardDto.getContext());

        // 여기에 다른 필요한 업데이트 로직을 추가할 수 있습니다.

        noticeBoardRepository.save(existingNoticeBoard);
        return convertToDto(existingNoticeBoard);
    }

    @Override
    public void deleteNoticeBoard(Long id) {
        noticeBoardRepository.deleteById(id);
    }

    private NoticeBoardResponseDto convertToDto(NoticeBoard noticeBoard) {
        if (noticeBoard == null) {
            return null; // 또는 예외를 throw하거나 기본값을 반환할 수 있습니다.
        }

        NoticeBoardResponseDto noticeBoardResponseDto = new NoticeBoardResponseDto();
        noticeBoardResponseDto.setId(noticeBoard.getId());
        noticeBoardResponseDto.setTitle(noticeBoard.getTitle());
        noticeBoardResponseDto.setCategory(noticeBoard.getCategory());
        noticeBoardResponseDto.setContext(noticeBoard.getContext());
        // 나머지 필드 설정

        return noticeBoardResponseDto;
    }

    private NoticeBoard convertToEntity(NoticeBoardRequestDto noticeBoardDto) {

        if (noticeBoardDto == null) {
            return null; // 또는 예외를 throw하거나 기본값을 반환할 수 있습니다.
        }

        NoticeBoard noticeBoard = new NoticeBoard();
        // noticeBoard.setId(noticeBoardDto.getId());
        noticeBoard.setTitle(noticeBoardDto.getTitle());
        noticeBoard.setCategory(noticeBoardDto.getCategory());
        noticeBoard.setContext(noticeBoardDto.getContext());
        // 나머지 필드 설정

        return noticeBoard;  // 이 부분에 중괄호가 누락되면 오류가 발생할 수 있습니다.
    }
}