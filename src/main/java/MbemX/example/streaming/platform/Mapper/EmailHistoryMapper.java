package MbemX.example.streaming.platform.Mapper;

import MbemX.example.streaming.platform.Dto.EmailHistoryDto;
import MbemX.example.streaming.platform.Entity.EmailHistory;
import MbemX.example.streaming.platform.Entity.User;
import org.springframework.stereotype.Component;

@Component
public class EmailHistoryMapper {
    public EmailHistoryDto toDto(EmailHistory emailHistory){
        if (emailHistory == null){
            return null;
        }
        return new EmailHistoryDto(
              emailHistory.getId(),
              emailHistory.getRecipient(),
              emailHistory.getSubject(),
              emailHistory.getType(),
              emailHistory.getStatus(),
              emailHistory.getDateSent(),
                emailHistory.getUser() != null
                        ? emailHistory.getUser().getId()
                        : null

        );
    }

    public EmailHistory toEntity(EmailHistoryDto dto){
        if (dto == null){
            return null;
        }
        EmailHistory emailHistory = new EmailHistory();
        emailHistory.setId(dto.id());
        emailHistory.setRecipient(dto.recipient());
        emailHistory.setType(dto.type());
        emailHistory.setStatus(dto.status());
        emailHistory.setSubject(dto.subject());

        return emailHistory;
    }
}
