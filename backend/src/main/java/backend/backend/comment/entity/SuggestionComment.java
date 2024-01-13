package backend.backend.comment.entity;

import backend.backend.comment.dto.CommentRequestDto;
import backend.backend.noticeboard.entity.SuggestionBoard;
import backend.backend.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class SuggestionComment extends BaseEntity {
    @Id
    @GeneratedValue
    @Column(name = "comment_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "suggestionBoard_id")
    private SuggestionBoard suggestionBoard;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    private String context;

    public SuggestionComment update(CommentRequestDto commentRequestDto) {
        this.context = commentRequestDto.getContext();
        return this;
    }
}
