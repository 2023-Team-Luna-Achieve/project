package backend.backend.noticeboard.service;

import backend.backend.exception.ErrorCode;
import backend.backend.exception.NotFoundException;
import backend.backend.noticeboard.dto.SuggestionRequestDto;
import backend.backend.noticeboard.dto.SuggestionResponseDto;
import backend.backend.noticeboard.entity.Suggestion;
import backend.backend.noticeboard.repository.SuggestionRepository;
import backend.backend.noticeboard.validator.AuthorizationValidator;
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
    private final AuthorizationValidator authorizationValidator;
    private final SuggestionRepository suggestionRepository;
    private static final int PAGE_SIZE = 10; //페이지네이션


    @Override //게시글목록조회 페이지네이션
    public List<SuggestionResponseDto> getSuggestionsByPage(int page) {
        Pageable pageable = PageRequest.of(page, PAGE_SIZE);
        return suggestionRepository.findAll(pageable).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public List<SuggestionResponseDto> getSuggestionsAfterId(Long id, int page) {
        Pageable pageable = PageRequest.of(page, PAGE_SIZE);
        return suggestionRepository.findSuggestionsByIdGreaterThan(id, pageable).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public List<SuggestionResponseDto> getSuggestionsBeforeId(Long id, int page) {
        Pageable pageable = PageRequest.of(page, PAGE_SIZE);
        return suggestionRepository.findSuggestionsByIdLessThan(id, pageable).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public SuggestionResponseDto getSuggestionById(Long id) {
        Suggestion suggestion = suggestionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Suggestion not found with id: " + id));
        return convertToDto(suggestion);
    }

    @Override
    public SuggestionResponseDto createSuggestion(User user, SuggestionRequestDto suggestionRequestDto) {
        Suggestion suggestion = convertToEntity(user, suggestionRequestDto);
        suggestion = suggestionRepository.save(suggestion);
        return convertToDto(suggestion);
    }

    @Override
    public SuggestionResponseDto updateSuggestion(User user, Long id, SuggestionResponseDto suggestionDto) {
        Suggestion suggestion = suggestionRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(ErrorCode.ENTITY_NOT_FOUND));
        authorizationValidator.validateSuggestionModifyOrDeletePermission(user, suggestion);

        suggestion.setTitle(suggestionDto.getTitle());
        suggestion.setCategory(suggestionDto.getCategory());
        suggestion.setContext(suggestionDto.getContext());

        suggestionRepository.save(suggestion);
        return convertToDto(suggestion);
    }

    @Override
    public Page<SuggestionResponseDto> getSuggestions(Pageable pageable, Long offset) {
        Page<Suggestion> suggestions = suggestionRepository.findAllByCursor(pageable, offset);
        return suggestions.map(this::convertToDto);
    }

    @Override
    public void deleteSuggestion(User user, Long id) {
        Suggestion suggestion = suggestionRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(ErrorCode.ENTITY_NOT_FOUND));
        authorizationValidator.validateSuggestionModifyOrDeletePermission(user, suggestion);
        suggestionRepository.deleteById(id);
    }


    private SuggestionResponseDto convertToDto(Suggestion suggestion) {
        if (suggestion == null) {
            return null;
        }

        SuggestionResponseDto suggestionResponseDto = new SuggestionResponseDto();
        suggestionResponseDto.setId(suggestion.getId());
        suggestionResponseDto.setTitle(suggestion.getTitle());
        suggestionResponseDto.setCategory(suggestion.getCategory());
        suggestionResponseDto.setContext(suggestion.getContext());

        return suggestionResponseDto;
    }

    private Suggestion convertToEntity(User user, SuggestionRequestDto suggestionDto) {
        if (suggestionDto == null) {
            return null;
        }
        Suggestion suggestion = new Suggestion();
        suggestion.setTitle(suggestionDto.getTitle());
        suggestion.setCategory(suggestionDto.getCategory());
        suggestion.setContext(suggestionDto.getContext());
        suggestion.setUser(user);
        return suggestion;
    }

}
