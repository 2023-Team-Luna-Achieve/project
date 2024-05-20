package backend.backend.common.dto;

import java.util.List;

public record SingleRecordResponse<T>(
        List<T> values,
        boolean hasNext,
        String cursor
) {

    public static <t extends Identifiable> SingleRecordResponse<t> convertToSingleRecord(List<t> responses) {
        if (responses.isEmpty()) {
            return SingleRecordResponse.of(responses, false, "0");
        }
        boolean hasNext = existNextPage(responses);
        String cursor = generateCursor(responses);
        return SingleRecordResponse.of(responses, hasNext, cursor);
    }

    private static <t> boolean existNextPage(List<t> responses) {
        if (responses.size() > 10) {
            responses.remove(10);
            return true;
        }

        return false;
    }

    private static <t extends Identifiable> String generateCursor(List<t> responses) {
        return String.valueOf(responses.get(responses.size() - 1).getId());
    }

    public static <T> SingleRecordResponse<T> of(List<T> values, boolean hasNext, String cursor) {
        return new SingleRecordResponse<>(
                values, hasNext, cursor
        );
    }
}
