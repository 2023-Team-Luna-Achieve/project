package backend.backend.noticeboard.validator;

import backend.backend.exception.ErrorCode;
import backend.backend.exception.NotAllowedException;
import backend.backend.noticeboard.entity.NoticeBoard;
import backend.backend.noticeboard.entity.Suggestion;
import backend.backend.user.entity.User;
import org.springframework.stereotype.Component;

@Component
public class AuthorizationValidator {
    public void validateNoticeBoardModifyOrDeletePermission(User user, NoticeBoard noticeBoard) {
        if(!noticeBoard.getUser().getEmail().equals(user.getEmail())) {
            throw new NotAllowedException(ErrorCode.NOT_ALLOWED);
        }
    }

    public void validateSuggestionModifyOrDeletePermission(User user, Suggestion suggestion) {
        if(!suggestion.getUser().getEmail().equals(user.getEmail())) {
            throw new NotAllowedException(ErrorCode.NOT_ALLOWED);
        }
    }
}
