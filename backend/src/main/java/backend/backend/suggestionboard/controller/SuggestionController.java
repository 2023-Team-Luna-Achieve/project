package backend.backend.suggestionboard.controller;

import backend.backend.suggestionboard.service.SuggestionService;
import backend.backend.suggestionboard.dto.SuggestionPostDto;
import backend.backend.suggestionboard.dto.SuggestionPatchDto;
import backend.backend.suggestionboard.dto.SuggestionResponseDto;
import backend.backend.suggestionboard.entity.Suggestion;
import backend.backend.suggestionboard.mapper.SuggestionMapper;
import backend.backend.suggestionboard.repository.SuggestionRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/suggestion")
@RequiredArgsConstructor
public class SuggestionController {

    private final SuggestionService suggestionService;
    private final SuggestionMapper suggestionMapper;

    // 건의사항 글 생성
    @Transactional
    @PostMapping
    public ResponseEntity postSuggestion(@PathVariable("user-id") Long userId, @Valid @RequestBody SuggestionPostDto suggestionPostDto) throws IOException {
        Suggestion suggestion = suggestionService.createSuggestion(suggestionMapper.toSuggestion(suggestionPostDto), userId);
        return new ResponseEntity<>(suggestionMapper.toSuggestionResponseDto(suggestion), HttpStatus.CREATED);
    }

    // 특정 글 조회
    @GetMapping("/{suggestion_id}")
    public ResponseEntity getSuggestion(HttpServletRequest request, @PathVariable("suggestion_id") Long suggestionId) {
        Suggestion suggestion = suggestionService.findSuggestion(request, suggestionId);
        return new ResponseEntity<>(suggestionMapper.toSuggestionResponseDto(suggestion), HttpStatus.OK);
    }

    // 글 수정
    @PatchMapping("/{suggestion_id}")
    public ResponseEntity patchSuggestion(@PathVariable("suggestion_id") Long suggestionId, @Valid @RequestBody SuggestionPatchDto suggestionPatchDto) {
        Suggestion suggestion = suggestionService.updateSuggestion(suggestionMapper.toSuggestion(suggestionPatchDto), suggestionId);
        return new ResponseEntity<>(suggestionMapper.toSuggestionResponseDto(suggestion), HttpStatus.CREATED);
    }

    // 글 삭제
    @DeleteMapping("/{suggestion_id}")
    public ResponseEntity deleteSuggestion(@PathVariable("suggestion_id") Long suggestionId) {
        suggestionService.deleteSuggestion(suggestionId);
        return new ResponseEntity<>("게시글이 삭제되었습니다.", HttpStatus.OK);
    }
}
