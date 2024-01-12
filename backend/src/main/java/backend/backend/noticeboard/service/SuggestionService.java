package backend.backend.noticeboard.service;

import backend.backend.noticeboard.dto.SuggestionRequestDto;
import backend.backend.noticeboard.dto.SuggestionResponseDto;
import backend.backend.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface SuggestionService {
    public abstract List<SuggestionResponseDto> getSuggestionsByPage(int page);

    public abstract List<SuggestionResponseDto> getSuggestionsAfterId(Long id, int page);

    public abstract List<SuggestionResponseDto> getSuggestionsBeforeId(Long id, int page);

    public abstract SuggestionResponseDto getSuggestionById(Long id);

    public abstract SuggestionResponseDto createSuggestion(User user, SuggestionRequestDto suggestionRequestDto);

    public abstract SuggestionResponseDto updateSuggestion(User user, Long suggestion_id, SuggestionResponseDto suggestionDto);

    public abstract Page<SuggestionResponseDto> getSuggestions(Pageable pageable, Long offset);

    void deleteSuggestion(User user, Long id);
}

