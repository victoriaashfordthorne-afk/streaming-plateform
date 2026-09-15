package MbemX.example.streaming.platform.Repository;

import MbemX.example.streaming.platform.Entity.File;
import MbemX.example.streaming.platform.Enums.MediaType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FileRepository  extends JpaRepository<File,Long> {
    List<File> findByUserId(Long userId);
    List<File> findByUserIdAndTypeMedia(Long userId, MediaType typeMedia);
}
