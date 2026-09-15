package MbemX.example.streaming.platform.Services;

import MbemX.example.streaming.platform.Dto.EmailHistoryDto;
import MbemX.example.streaming.platform.Entity.EmailHistory;
import MbemX.example.streaming.platform.Entity.User;
import MbemX.example.streaming.platform.Mapper.EmailHistoryMapper;
import MbemX.example.streaming.platform.Repository.EmailHistoryRepository;
import MbemX.example.streaming.platform.Repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

@Service
public class EmailHistoryServices {
    private final EmailHistoryRepository emailHistoryRepository;
    private final EmailHistoryMapper emailHistoryMapper;
    private final UserRepository userRepository;

    public EmailHistoryServices(EmailHistoryRepository emailHistoryRepository,EmailHistoryMapper emailHistoryMapper,UserRepository userRepository){
        this.emailHistoryRepository = emailHistoryRepository;
        this.emailHistoryMapper = emailHistoryMapper;
        this.userRepository = userRepository;
    }

    public List<EmailHistoryDto> findAll(){
        List<EmailHistory> dto = emailHistoryRepository.findAll();
        return dto.stream()
                .map(emailHistoryMapper::toDto)
                .toList();
    }

    public EmailHistoryDto findById(Long id){
        EmailHistory emailHistory = emailHistoryRepository.findById(id)
                .orElseThrow(()->new RuntimeException("Email history not found with id:"+id));

        return emailHistoryMapper.toDto(emailHistory);
    }

    public EmailHistoryDto saveEmailHistory(EmailHistoryDto dto){
        User user = userRepository.findById(dto.userId())
                .orElseThrow(()->new RuntimeException("User not found with id:"+id));

        EmailHistory emailHistory = emailHistoryMapper.toEntity(dto);
        EmailHistory savaEmailHistory = emailHistoryRepository.save(emailHistory);
        return emailHistoryMapper.toDto(savaEmailHistory);
    }

    public EmailHistoryDto updateHistory(Long id ,EmailHistoryDto dto){
        EmailHistory existingHistory = emailHistoryRepository.findById(id)
                .orElseThrow(()->new RuntimeException("Email not found with id"+id));

        User user =  userRepository.findById(dto.userId())
                .orElseThrow(()->new RuntimeException("user not found with id"+ dto.userId()));

        existingHistory.setRecipient(dto.recipient());
        existingHistory.setType(dto.type());
        existingHistory.setSubject(dto.subject());
        existingHistory.setStatus(dto.status());
        existingHistory.setDateSent(dto.dateSent());
        existingHistory.setUser(user);
        EmailHistory updateHistory = emailHistoryRepository.save(existingHistory);
        return emailHistoryMapper.toDto(updateHistory);
    }
    public void deleteHistory(Long id){
        EmailHistory emailHistory = emailHistoryRepository.findById(id)
                .orElseThrow(()->new RuntimeException("Email history not found with id:"+id));
        emailHistoryRepository.delete(emailHistory);
    }
}
