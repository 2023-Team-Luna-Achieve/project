package backend.backend.noticeboard.service;

import backend.backend.noticeboard.dto.SuggestionRequestDto;
import backend.backend.noticeboard.dto.SuggestionResponseDto;

import java.util.List;

public interface SuggestionService {
    public abstract List<SuggestionResponseDto> getAllSuggestion();

    public abstract SuggestionResponseDto getSuggestionById(Long id);

    public abstract SuggestionResponseDto createSuggestion(SuggestionRequestDto suggestionRequestDto);

    public abstract SuggestionResponseDto updateSuggestion(Long id, SuggestionResponseDto suggestionDto);

    //    void deleteSuggestion(Long id);
}

