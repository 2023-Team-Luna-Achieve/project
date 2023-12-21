//package backend.backend.noticeboardcomment.service;
//
//import backend.backend.noticeboard.repository.SuggestionRepository;
//import backend.backend.noticeboardcomment.dto.SuggestionCommentResponseDto;
//import backend.backend.noticeboardcomment.entity.SuggestionComment;
//import backend.backend.noticeboardcomment.repository.SuggestionCommentRepository;
//import backend.backend.user.repository.UserRepository;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//import java.util.stream.Collectors;
//
//@Service
//public class SuggestionCommentServiceImpl implements backend.backend.noticeboardcomment.service.SuggestionCommentService{
//
//    private final SuggestionCommentRepository suggestionCommentRepository;
//    private final SuggestionRepository suggestionRepository;
//    private final UserRepository userRepository;
//
//    public SuggestionCommentServiceImpl(SuggestionCommentRepository suggestionCommentRepository, SuggestionRepository suggestionRepository, UserRepository userRepository) {
//        this.suggestionCommentRepository = suggestionCommentRepository;
//        this.suggestionRepository = suggestionRepository;
//        this.userRepository = userRepository;
//    }
//
//    @Override
//    public List<SuggestionCommentResponseDto> getAllCommentsBySuggestionId(Long suggestionId) {
//        return suggestionCommentRepository.findAllBySuggestionId(suggestionId).stream()
//                .map(this::convertToDto)
//                .collect(Collectors.toList());
//    }
//
//
//    //기타 필요한 메소드
//    private SuggestionCommentResponseDto convertToDto(SuggestionComment suggestionComment) {
//        if (suggestionComment == null) {
//            return null;
//        }
//
//        SuggestionCommentResponseDto suggestionCommentResponseDto = new SuggestionCommentResponseDto();
//        suggestionCommentResponseDto.setId(suggestionComment.getId());
//        suggestionCommentResponseDto.setSuggestionId(suggestionComment.getSuggestion().getId());
//        suggestionCommentResponseDto.setUserId(suggestionComment.getUser().getId());
//        suggestionCommentResponseDto.setContext(suggestionComment.getContext());
//        suggestionCommentResponseDto.setCreated_at(suggestionComment.getCreated_at());
//
//        return suggestionCommentResponseDto;
//    }
//
//
//}
