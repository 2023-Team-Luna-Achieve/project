package backend.backend.common.dto;

import java.util.List;

public record SingleRecordResponse<T>(
        List<T> values,
        int count,
        boolean hasNext,
        String cursor
) {

    public static <T> SingleRecordResponse<T> of(List<T> values, int count, boolean hasNext, String cursor) {
        return new SingleRecordResponse<>(
                values, count, hasNext, cursor
        );
    }

    public static <T> SingleRecordResponse<T> of(List<T> values, boolean hasNext, String cursor) {
        return new SingleRecordResponse<>(values, 0, hasNext, cursor);
    }
}
