package backend.backend.suggestionboard.entity;

import backend.backend.comment.entity.Comment;
import backend.backend.noticeboard.entity.NoticeBoard;
import backend.backend.reservation.entity.Reservation;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "suggestion")
public class Suggestion extends Auditable {

    @Column(nullable = false, name = "title")
    private String title;

    @Column(length = 2000, nullable = false, name = "body")
    private String body;

    private Long views = 0L;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;


    @OneToMany(mappedBy = "suggestion",cascade = {CascadeType.PERSIST,CascadeType.REMOVE})
    private List<Comment> comments = new ArrayList<>();

    public void addComment(Comment comment){
        if (comment.getSuggestion() != this) comment.setSuggestion(this);
        comments.add(comment);
    //건의사항댓글이랑 공지댓글 패키지를 따로 만들어야하는가??



}