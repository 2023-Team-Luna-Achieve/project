package backend.backend.noticeboard.service;

import backend.backend.common.exception.AuthException;
import backend.backend.common.exception.ErrorCode;
import backend.backend.common.exception.NotFoundException;
import backend.backend.noticeboard.dto.SuggestionRequestDto;
import backend.backend.noticeboard.dto.SuggestionResponseDto;
import backend.backend.noticeboard.entity.SuggestionBoard;
import backend.backend.noticeboard.repository.SuggestionBoardRepository;
import backend.backend.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;


import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SuggestionServiceImpl implements SuggestionService {
    private final SuggestionBoardRepository suggestionBoardRepository;
    private static final int PAGE_SIZE = 10; //페이지네이션

    @Override //게시글목록조회 페이지네이션
    public List<SuggestionResponseDto> getSuggestionsByPage(int page) {
        Pageable pageable = PageRequest.of(page, PAGE_SIZE);
        return suggestionBoardRepository.findAll(pageable).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public List<SuggestionResponseDto> getSuggestionsAfterId(Long id, int page) {
        Pageable pageable = PageRequest.of(page, PAGE_SIZE);
        return suggestionBoardRepository.findSuggestionsByIdGreaterThan(id, pageable).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public List<SuggestionResponseDto> getSuggestionsBeforeId(Long id, int page) {
        Pageable pageable = PageRequest.of(page, PAGE_SIZE);
        return suggestionBoardRepository.findSuggestionsByIdLessThan(id, pageable).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public SuggestionResponseDto getSuggestionById(Long id) {
        SuggestionBoard suggestion = suggestionBoardRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(ErrorCode.BOARD_NOT_FOUND));
        return convertToDto(suggestion);
    }

    @Override
    public SuggestionBoard createSuggestion(User user, SuggestionRequestDto suggestionRequestDto) {
        SuggestionBoard suggestion = convertToEntity(user, suggestionRequestDto);
        return suggestionBoardRepository.save(suggestion);
    }

    @Override
    public void updateSuggestion(User user, Long suggestionId, SuggestionRequestDto suggestionDto) {
        SuggestionBoard suggestionBoard = findBoardById(suggestionId);
        if(user.isNotPossibleModifyOrDeletePermission(suggestionBoard.getUser().getId())) {
            throw new AuthException(ErrorCode.FORBIDDEN);
        }

        suggestionBoard.setTitle(suggestionDto.getTitle());
        suggestionBoard.setCategory(suggestionDto.getCategory());
        suggestionBoard.setContext(suggestionDto.getContext());

        suggestionBoardRepository.save(suggestionBoard);
    }

    @Override
    public Page<SuggestionResponseDto> getSuggestions(Pageable pageable, Long offset) {
        Page<SuggestionBoard> suggestions = suggestionBoardRepository.findAllByCursor(pageable, offset);
        return suggestions.map(this::convertToDto);
    }

    @Override
    public void deleteSuggestion(User user, Long suggestionId) {
        SuggestionBoard suggestionBoard = findBoardById(suggestionId);
        if(user.isNotPossibleModifyOrDeletePermission(suggestionBoard.getUser().getId())) {
            throw new AuthException(ErrorCode.FORBIDDEN);
        }

        suggestionBoardRepository.deleteById(suggestionId);
    }

    private SuggestionResponseDto convertToDto(SuggestionBoard suggestion) {
        if (suggestion == null) {
            return null;
        }

        return new SuggestionResponseDto(
                suggestion.getId(),
                suggestion.getUser().getName(),
                suggestion.getTitle(),
                suggestion.getCategory(),
                suggestion.getContext()
        );
    }

    private SuggestionBoard convertToEntity(User user, SuggestionRequestDto suggestionDto) {
        if (suggestionDto == null) {
            return null;
        }
        SuggestionBoard suggestion = new SuggestionBoard();
        suggestion.setTitle(suggestionDto.getTitle());
        suggestion.setCategory(suggestionDto.getCategory());
        suggestion.setContext(suggestionDto.getContext());
        suggestion.setUser(user);
        return suggestion;
    }

    private SuggestionBoard findBoardById(Long suggestionId) {
        return suggestionBoardRepository.findById(suggestionId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.BOARD_NOT_FOUND));
    }
}
