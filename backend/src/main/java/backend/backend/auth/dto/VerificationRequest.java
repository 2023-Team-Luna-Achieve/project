package backend.backend.auth.dto;

import lombok.*;

//@Getter
//@NoArgsConstructor(access = AccessLevel.PROTECTED)
//@AllArgsConstructor
public record VerificationRequest(
    String email,
    String code
) {
}
