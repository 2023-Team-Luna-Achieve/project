package backend.backend.comment.entity;

import backend.backend.comment.dto.CommentRequestDto;
import backend.backend.common.domain.BaseEntity;
import backend.backend.noticeboard.entity.NoticeBoard;
import backend.backend.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.*;

@Entity
@Getter
@Builder
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor
@AllArgsConstructor
public class NoticeBoardComment extends BaseEntity {
    @Id
    @GeneratedValue
    @Column(name = "comment_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "noticeBoard_id")
    private NoticeBoard noticeBoard;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    private String context;

    public NoticeBoardComment update(CommentRequestDto commentRequestDto) {
        this.context = commentRequestDto.getContext();
        return this;
    }
}