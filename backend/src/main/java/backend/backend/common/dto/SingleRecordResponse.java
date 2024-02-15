package backend.backend.common.dto;

import java.util.List;

public record SingleRecordResponse<T>(
        List<T> values,
        boolean hasNext,
        String cursor
) {
    public static <T> SingleRecordResponse<T> of(List<T> values, boolean hasNext, String cursor) {
        return new SingleRecordResponse<>(
                values, hasNext, cursor
        );
    }
}
