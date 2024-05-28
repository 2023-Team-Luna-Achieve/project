package backend.backend.report.service;

import backend.backend.common.exception.ErrorCode;
import backend.backend.common.exception.NotFoundException;
import backend.backend.common.exception.ReportException;
import backend.backend.report.dto.BlockRequest;
import backend.backend.report.repository.BlockRepository;
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
        User blocker = userRepository.findById(currentUser.getId())
                .orElseThrow(() -> new NotFoundException(ErrorCode.USER_NOT_FOUND));

        User blockedUser = userRepository.findByEmail(blockRequest.blockedUserEmail())
                .orElseThrow(() -> new NotFoundException(ErrorCode.USER_NOT_FOUND));

        if (blockRepository.existsByBlockerIdAndBlockedUserId(blocker.getId(), blockedUser.getId())) {
            throw new ReportException(ErrorCode.ALREADY_BLOCKED_USER);
        }

        return blockRepository.save(blockRequest.toEntity(blocker, blockedUser)).getId();
    }
}
