package backend.backend.noticeboard.controller;

import backend.backend.noticeboard.dto.SuggestionRequestDto;
import backend.backend.noticeboard.dto.SuggestionResponseDto;
import backend.backend.noticeboard.entity.Suggestion;
import backend.backend.noticeboard.service.SuggestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/suggestion")
@RequiredArgsConstructor
public class SuggestionController {

    private final SuggestionService suggestionService;
    private final HttpSession httpSession;

    // 건의사항 글 생성 - 수정완료
    @PostMapping("/create")
    public ResponseEntity<SuggestionResponseDto> createSuggestion(@RequestBody SuggestionRequestDto suggestionRequestDto) {
        SuggestionResponseDto createdSuggestion = suggestionService.createSuggestion(suggestionRequestDto);
        return ResponseEntity.ok(createdSuggestion);
    }

    //모든 건의사항 글 목록 조회 - 수정완료
    @GetMapping("/suggestion")
    public List<SuggestionResponseDto> getSuggestionsByPage(@RequestParam(defaultValue = "0") int page) {
        return suggestionService.getSuggestionsByPage(page);
    }

    // 특정 ID 이후의 Suggestion을 가져오는 엔드포인트 (페이지네이션)
    @GetMapping("/after/{id}")
    public ResponseEntity<List<SuggestionResponseDto>> getSuggestionsAfterId(@PathVariable Long id, @RequestParam(defaultValue = "0") int page) {
        List<SuggestionResponseDto> suggestions = suggestionService.getSuggestionsAfterId(id, page);
        return ResponseEntity.ok(suggestions);
    }

    //특정 ID 이전의 Suggestion을 가져오는 엔드포인트 (페이지네이션)
    @GetMapping("/before/{id}")
    public ResponseEntity<List<SuggestionResponseDto>> getSuggestionsBeforeId(@PathVariable Long id, @RequestParam(defaultValue = "0") int page) {
        List<SuggestionResponseDto> suggestions = suggestionService.getSuggestionsBeforeId(id, page);
        return ResponseEntity.ok(suggestions);
    }

    // 특정 글 조회 - 수정중
    @GetMapping("/{suggestion_id}")
    public ResponseEntity<SuggestionResponseDto> getSuggestionById(@PathVariable Long id) {
        SuggestionResponseDto suggestionDto = suggestionService.getSuggestionById(id);
        return ResponseEntity.ok(suggestionDto);
    }

    // 글 수정 - 수정중
    @PatchMapping("/{suggestion_id}")
    public ResponseEntity<SuggestionResponseDto> updateSuggestion(
            @PathVariable Long suggestion_id, @RequestBody SuggestionResponseDto suggestionDto) {
        SuggestionResponseDto updatedSuggestion = suggestionService.updateSuggestion(suggestion_id, suggestionDto);
        return ResponseEntity.ok(updatedSuggestion);
    }

    // 건의사항 글 삭제 - 수정완료
    @DeleteMapping("/{suggestion_id}")
    public ResponseEntity deleteSuggestion(@PathVariable Long suggestion_id) { // id vs suggestion_id ??
        suggestionService.deleteSuggestion(suggestion_id);
        return ResponseEntity.ok().body("게시글이 삭제되었습니다.");
    }
}
