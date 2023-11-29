package backend.backend.noticeboard.service;

import backend.backend.noticeboard.dto.SuggestionRequestDto;
import backend.backend.noticeboard.dto.SuggestionResponseDto;

import java.util.List;

public interface SuggestionService {
    public abstract List<SuggestionResponseDto> getSuggestionsByPage(int page);
    public abstract List<SuggestionResponseDto> getSuggestionsAfterId(Long id, int page);
    public abstract List<SuggestionResponseDto> getSuggestionsBeforeId(Long id, int page);

    public abstract SuggestionResponseDto getSuggestionById(Long id);

    public abstract SuggestionResponseDto createSuggestion(SuggestionRequestDto suggestionRequestDto);

    public abstract SuggestionResponseDto updateSuggestion(Long id, SuggestionResponseDto suggestionDto);

    void deleteSuggestion(Long id);
}

