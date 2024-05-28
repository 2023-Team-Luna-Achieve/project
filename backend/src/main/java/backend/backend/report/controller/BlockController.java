package backend.backend.report.controller;

import backend.backend.auth.jwt.CurrentUser;
import backend.backend.report.dto.BlockRequest;
import backend.backend.report.service.BlockService;
import backend.backend.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequestMapping("/api/blocks")
@RequiredArgsConstructor
public class BlockController {
    private final BlockService blockService;
    @PostMapping
    public ResponseEntity<Void> blockUser (@CurrentUser User currentUser, @RequestBody BlockRequest blockRequest) {
        Long blockId = blockService.requestBlockUser(currentUser, blockRequest);
        return ResponseEntity.created(URI.create("/api/blocks/" + blockId)).build();
    }
}
