package backend.backend.noticeboard.service;

import backend.backend.noticeboard.dto.SuggestionRequestDto;
import backend.backend.noticeboard.dto.SuggestionResponseDto;
import backend.backend.noticeboard.entity.Suggestion;
import backend.backend.noticeboard.repository.SuggestionRepository;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;


import java.util.List;
import java.util.stream.Collectors;

@Service
public class SuggestionServiceImpl implements SuggestionService {

    private final SuggestionRepository suggestionRepository;
    private static final int PAGE_SIZE = 10; //페이지네이션

    public SuggestionServiceImpl(SuggestionRepository suggestionRepository) {
        this.suggestionRepository = suggestionRepository;
    }

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
    public SuggestionResponseDto createSuggestion(SuggestionRequestDto suggestionRequestDto) {
        Suggestion suggestion = convertToEntity(suggestionRequestDto);
        suggestion = suggestionRepository.save(suggestion);
        return convertToDto(suggestion);
    }

    @Override
    public SuggestionResponseDto updateSuggestion(Long id, SuggestionResponseDto suggestionDto) {
        Suggestion existingSuggestion = suggestionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Suggestion not found with id: " + id));

        existingSuggestion.setTitle(suggestionDto.getTitle());
        existingSuggestion.setCategory(suggestionDto.getCategory());
        existingSuggestion.setContext(suggestionDto.getContext());

        suggestionRepository.save(existingSuggestion);
        return convertToDto(existingSuggestion);
    }

    @Override
    public void deleteSuggestion(Long id) { suggestionRepository.deleteById(id); }


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

    private Suggestion convertToEntity(SuggestionRequestDto suggestionDto) {
        if (suggestionDto == null) {
            return null;
        }
        Suggestion suggestion = new Suggestion();
        suggestion.setTitle(suggestionDto.getTitle());
        suggestion.setCategory(suggestionDto.getCategory());
        suggestion.setContext(suggestionDto.getContext());

        return suggestion;
    }

}
