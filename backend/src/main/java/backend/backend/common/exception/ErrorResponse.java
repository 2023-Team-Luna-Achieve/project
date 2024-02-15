package backend.backend.common.exception;

import lombok.*;

@Builder
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class ErrorResponse {
    private String businessCode;
    private String errorMessage;
}
