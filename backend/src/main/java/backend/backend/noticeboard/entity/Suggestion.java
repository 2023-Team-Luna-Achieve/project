package backend.backend.noticeboard.entity;

//import backend.backend.noticeboardcomment.entity.Comment;
import backend.backend.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Suggestion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "suggestion_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

//    @OneToMany(mappedBy = "suggestion_id")
//    private List<Comment> comments;

    private String title;

    @Enumerated(EnumType.STRING)
    private Category category;

    private String context;

    private LocalDateTime updated_at;

    private LocalDateTime created_at;

    private LocalDateTime deleted_at;

    private int viewCount;

    public void setTitle(String title) {
        this.title = title;
    }
    public void setUser(User user) {
        this.user = user;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void addViewCount() {
        this.viewCount ++;
    }
}