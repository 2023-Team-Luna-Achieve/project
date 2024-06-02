package backend.backend.report.service;

import backend.backend.common.exception.*;
import backend.backend.report.dto.BlockRequest;
import backend.backend.report.repository.BlockRepository;
import backend.backend.user.entity.Role;
import backend.backend.user.entity.User;
import backend.backend.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BlockService {
    private final UserRepository userRepository;
    private final BlockRepository blockRepository;

    public Long requestBlockUser(User currentUser, BlockRequest blockRequest) {
        User blocker = findBlockerById(currentUser);
        User blockedUser = findBlockedUserByEmail(blockRequest);
        validateNotAlreadyBlocked(blocker, blockedUser);
        validateBlockedUserIsNotAdmin(blockedUser);

        return blockRepository.save(blockRequest.toEntity(blocker, blockedUser)).getId();
    }

    private User findBlockerById(User currentUser) {
        return userRepository.findById(currentUser.getId())
                .orElseThrow(() -> new NotFoundException(ErrorCode.USER_NOT_FOUND));

    }

    private User findBlockedUserByEmail(BlockRequest blockRequest) {
        return userRepository.findUserByEmail(blockRequest.blockedUserEmail())
                .orElseThrow(() -> new NotFoundException(ErrorCode.USER_NOT_FOUND));
    }

    private void validateNotAlreadyBlocked(User blocker, User blockedUser) {
        if (blockRepository.existsByBlockerIdAndBlockedUserId(blocker.getId(), blockedUser.getId())) {
            throw new ReportException(ErrorCode.ALREADY_BLOCKED_USER);
        }
    }

    private void validateBlockedUserIsNotAdmin(User blockedUser) {
        if (blockedUser.getRole().equals(Role.ROLE_ADMIN)) {
            throw new AuthException(ErrorCode.ADMIN_REPORTING_NOT_ALLOWED);
        }
    }
}
