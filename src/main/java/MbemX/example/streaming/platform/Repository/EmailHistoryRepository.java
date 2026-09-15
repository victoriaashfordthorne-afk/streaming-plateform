package MbemX.example.streaming.platform.Repository;

import MbemX.example.streaming.platform.Entity.EmailHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmailHistoryRepository extends JpaRepository<EmailHistory,Long> {
}
