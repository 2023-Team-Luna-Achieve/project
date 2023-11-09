package backend.backend.suggestionboard.service;

import backend.backend.suggestionboard.controller.SuggestionController;
import backend.backend.suggestionboard.dto.SuggestionPostDto;
import backend.backend.suggestionboard.dto.SuggestionPatchDto;
import backend.backend.suggestionboard.dto.SuggestionResponseDto;
import backend.backend.suggestionboard.entity.Suggestion;
import backend.backend.suggestionboard.mapper.SuggestionMapper;
import backend.backend.suggestionboard.repository.SuggestionRepository;
import backend.backend.user.*

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class SuggestionService {
    private final SuggestionRepository.suggestionRepository;
    private final UserRepository.userRepository;
    private final UserService.userService;

    @Transactional
    public Suggestion createSuggestion(Suggestion suggestion, long userId) {
        suggestion.setMember(userRepository.findById(userId));
        User user = userService.findVerifiedUser(userId);
        user.addSuggestions(suggestion);
        suggestion.setCreatedAt(LocalDateTime.now());
        suggestion.setModifiedAt(LocalDateTime.now());

        return suggestionRepository.save(suggestion);
    }

    public Suggestion updateSuggestion(Suggestion suggestion, long suggestionId, List<String> imageNames, Long loginUserId) {
        Suggestion findSuggestion = findVerifiedSuggestion(suggestionId);

        // 작성자와 수정자가 동일인물인지 검증
        userService.verifyIsSameUser(findSuggestion.getMember(), loginUserId);

        Optional.ofNullable(suggestion.getTitle())
                .ifPresent(title -> findSuggestion.setTitle(title));
        Optional.ofNullable(suggestion.getBody())
                .ifPresent(body -> findSuggestion.setBody(body));

        findSuggestion.setModifiedAt(LocalDateTime.now());

        return suggestionRepository.save(findSuggestion);
    }

    public Suggestion findVerifiedSuggestion(long suggestionId) {
        return suggestionRepository.findById(suggestionId)
                .orElseThrow(() -> new BusinessLogicException(ExceptionCode.SUGGESTION_NOT_FOUND));
    }

    public Suggestion findSuggestion(HttpServletRequest request, long suggestionId) {
        Suggestion suggestion = findVerifiedSuggestion(suggestionId);
        viewCounter.verifyIsViewed(request, suggestion);
        suggestionRepository.save(suggestion);
        return suggestion;
    }

    @Transactional
    public void deleteSuggestion(long suggestionId, Long loginUserId) {
        Suggestion suggestion = findVerifiedSuggestion(suggestionId);
        userService.verifyIsSameUser(suggestion.getMember(), loginUserId);
        suggestionRepository.delete(suggestion);
    }

    public Page<Suggestion> findSuggestions(int page, int size) {
        return suggestionRepository.findAll(PageRequest.of(page, size, Sort.by("id").descending()));
    }

    public Page<Suggestion> findSuggestionsByUserId(int page, int size, long userId) {
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by("id").descending());
        Page<Suggestion> userSuggestions = suggestionRepository.findByUserId(userId, pageRequest);
        return userSuggestions;
    }

    ///게시글 생성,수정 시간
//    LocalDateTime now = LocalDateTime.now();
//    suggestion.setCreatedAt(now);
//    suggestion.setModifiedAt(now);

}