package MbemX.example.streaming.platform.Repository;

import MbemX.example.streaming.platform.Entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment,Long> {
    List<Comment>findByFileId(Long fileId);
}
