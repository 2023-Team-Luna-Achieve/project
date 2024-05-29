package backend.backend.common.domain;


public interface UserGeneratedContent {
    void addReportCount();

    void minusReportCount();

    int getReportCount();
}
