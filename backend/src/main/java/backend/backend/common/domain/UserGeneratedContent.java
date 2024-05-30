package backend.backend.common.domain;


import backend.backend.user.entity.User;

public interface UserGeneratedContent {
    void addReportCount();

    void minusReportCount();

    int getReportCount();

    User getUser();
}
