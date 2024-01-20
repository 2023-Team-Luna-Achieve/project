package backend.backend.noticeboard.service;

import backend.backend.noticeboard.dto.SuggestionRequestDto;
import backend.backend.noticeboard.dto.SuggestionResponseDto;
import backend.backend.noticeboard.entity.SuggestionBoard;
import backend.backend.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface SuggestionService {
    List<SuggestionResponseDto> getSuggestionsByPage(int page);

    List<SuggestionResponseDto> getSuggestionsAfterId(Long id, int page);

    List<SuggestionResponseDto> getSuggestionsBeforeId(Long id, int page);

    SuggestionResponseDto getSuggestionById(Long id);

    SuggestionBoard createSuggestion(User user, SuggestionRequestDto suggestionRequestDto);

    void updateSuggestion(User user, Long suggestion_id, SuggestionRequestDto suggestionDto);

    Page<SuggestionResponseDto> getSuggestions(Pageable pageable, Long offset);

    void deleteSuggestion(User user, Long id);
}

