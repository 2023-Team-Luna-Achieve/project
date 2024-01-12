package backend.backend.comment.service;

import backend.backend.comment.dto.CommentRequestDto;
import backend.backend.comment.dto.SuggestionCommentResponseDto;
import backend.backend.comment.entity.SuggestionComment;
import backend.backend.comment.repository.SuggestionCommentRepository;
import backend.backend.exception.AuthException;
import backend.backend.exception.ErrorCode;
import backend.backend.exception.NotFoundException;
import backend.backend.noticeboard.entity.SuggestionBoard;
import backend.backend.noticeboard.repository.SuggestionBoardRepository;
import backend.backend.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SuggestionCommentService {
    private final SuggestionBoardRepository suggestionBoardRepository;
    private final SuggestionCommentRepository suggestionCommentRepository;

    public SuggestionComment createSuggestionComment(User user, Long suggestionBoardId, CommentRequestDto commentRequestDto) {
        SuggestionBoard suggestBoard = findSuggestBoardById(suggestionBoardId);
        SuggestionComment suggestionComment = commentRequestDto.toEntity(user, suggestBoard);
        return suggestionCommentRepository.save(suggestionComment);
    }

    public SuggestionCommentResponseDto findOneSuggestionComment(Long suggestionCommentId) {
        SuggestionComment suggestionComment = findSuggestCommentById(suggestionCommentId);
        return new SuggestionCommentResponseDto(
                suggestionComment.getId(),
                suggestionComment.getUser().getName(),
                suggestionComment.getContext());
    }

    public List<SuggestionCommentResponseDto> findAllSuggestionCommentsBySuggestionBoardId(Long suggestionBoardId) {
        SuggestionBoard suggestionBoard = findSuggestBoardById(suggestionBoardId);

        return  suggestionBoard.getComments()
                .stream().map(suggestionComment -> new SuggestionCommentResponseDto(
                        suggestionComment.getId(),
                        suggestionComment.getUser().getName(),
                        suggestionComment.getContext()))
                .collect(Collectors.toList());
    }

    @Transactional
    public void updateSuggestionComment(User user, Long suggestionCommentId, CommentRequestDto commentRequestDto) {
        SuggestionComment suggestionComment = findSuggestCommentById(suggestionCommentId);
        if (user.isNotPossibleModifyOrDeletePermission(suggestionComment.getUser().getId())) {
            throw new AuthException(ErrorCode.NOT_ALLOWED);
        }
        suggestionComment.update(commentRequestDto);
    }

    public void deleteSuggestionCommentById(User user, Long suggestionCommentId) {
        SuggestionComment suggestComment = findSuggestCommentById(suggestionCommentId);
        if (user.isNotPossibleModifyOrDeletePermission(suggestComment.getUser().getId())) {
            throw new AuthException(ErrorCode.NOT_ALLOWED);
        }
        suggestionCommentRepository.deleteById(suggestionCommentId);
    }

    private SuggestionComment findSuggestCommentById(Long suggestionCommentId) {
        return suggestionCommentRepository.findById(suggestionCommentId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.COMMENT_NOT_FOUND));
    }

    private SuggestionBoard findSuggestBoardById(Long suggestionBoardId) {
        return suggestionBoardRepository.findById(suggestionBoardId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.COMMENT_NOT_FOUND));
    }
}
