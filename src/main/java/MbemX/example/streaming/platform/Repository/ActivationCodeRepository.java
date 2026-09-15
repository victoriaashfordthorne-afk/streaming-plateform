package MbemX.example.streaming.platform.Repository;

import MbemX.example.streaming.platform.Entity.ActivationCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ActivationCodeRepository extends JpaRepository<ActivationCode,Long> {
    Optional<ActivationCode> findByCode(String code);
}
