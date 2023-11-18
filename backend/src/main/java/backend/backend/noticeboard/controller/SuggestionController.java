//package backend.backend.noticeboard.controller;
//
//import backend.backend.suggestionboard.dto.SuggestionPatchDto;
//import backend.backend.suggestionboard.dto.SuggestionResponseDto;
//import backend.backend.suggestionboard.service.SuggestionService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpSession;
//import javax.validation.Valid;
//
//@RestController
//@RequestMapping("/api/suggestion")
//@RequiredArgsConstructor
//public class SuggestionController {
//
//    private final SuggestionService suggestionService;
//    private final HttpSession httpSession;
//
//    // 건의사항 글 생성
//    @PostMapping("/create")
//    public ResponseEntity<SuggestionResponseDto> createSuggestion(@RequestBody SuggestionResponseDto suggestionResponseDto) {
//        SuggestionResponseDto createdSuggestion = suggestionService.createSuggestion(suggestionResponseDto);
//        return ResponseEntity.ok(createdSuggestion);
//    }
//
//
//    // 특정 글 조회
//    @GetMapping("/{suggestion_id}")
//    public ResponseEntity getSuggestion(HttpServletRequest request, @PathVariable("suggestion_id") Long suggestionId) {
//        Suggestion suggestion = suggestionService.findSuggestion(request, suggestionId);
//        return new ResponseEntity<>(suggestionMapper.toSuggestionResponseDto(suggestion), HttpStatus.OK);
//    }
//
//    // 글 수정
//    @PatchMapping("/{suggestion_id}")
//    public ResponseEntity patchSuggestion(@PathVariable("suggestion_id") Long suggestionId, @Valid @RequestBody SuggestionPatchDto suggestionPatchDto) {
//        Suggestion suggestion = suggestionService.updateSuggestion(suggestionMapper.toSuggestion(suggestionPatchDto), suggestionId);
//        return new ResponseEntity<>(suggestionMapper.toSuggestionResponseDto(suggestion), HttpStatus.CREATED);
//    }
//
//    // 글 삭제
//    @DeleteMapping("/{suggestion_id}")
//    public ResponseEntity deleteSuggestion(@PathVariable("suggestion_id") Long suggestionId) {
//        suggestionService.deleteSuggestion(suggestionId);
//        return new ResponseEntity<>("게시글이 삭제되었습니다.", HttpStatus.OK);
//    }
//}
