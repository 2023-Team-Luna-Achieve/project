package backend.backend.noticeboard.controller;

import backend.backend.noticeboard.dto.SuggestionRequestDto;
import backend.backend.noticeboard.dto.SuggestionResponseDto;
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
    public List<SuggestionResponseDto> getAllSuggestion() {
        return suggestionService.getAllSuggestion();
    }

    // 특정 글 조회 - 수정중
//    @GetMapping("/{suggestion_id}")
//    public ResponseEntity getSuggestion(HttpServletRequest request, @PathVariable("suggestion_id") Long suggestionId) {
//        Suggestion suggestion = suggestionService.findSuggestion(request, suggestionId);
//        return new ResponseEntity<>(suggestionMapper.toSuggestionResponseDto(suggestion), HttpStatus.OK);
//    }

    // 글 수정 - 수정중
//    @PatchMapping("/{suggestion_id}")
//    public ResponseEntity patchSuggestion(@PathVariable("suggestion_id") Long suggestionId, @Valid @RequestBody SuggestionPatchDto suggestionPatchDto) {
//        Suggestion suggestion = suggestionService.updateSuggestion(suggestionMapper.toSuggestion(suggestionPatchDto), suggestionId);
//        return new ResponseEntity<>(suggestionMapper.toSuggestionResponseDto(suggestion), HttpStatus.CREATED);
//    }

    // 건의사항 글 삭제 - 수정완료
    @DeleteMapping("/{suggestion_id}")
    public ResponseEntity deleteSuggestion(@PathVariable Long suggestion_id) { // id vs suggestion_id ??
        suggestionService.deleteSuggestion(suggestion_id);
        return ResponseEntity.ok().body("게시글이 삭제되었습니다.");
    }
}
