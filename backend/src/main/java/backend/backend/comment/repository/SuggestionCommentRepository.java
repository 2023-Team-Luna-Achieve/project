package backend.backend.comment.repository;


import backend.backend.comment.entity.SuggestionComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SuggestionCommentRepository extends JpaRepository<SuggestionComment, Long> {

}
