package MbemX.example.streaming.platform.Mapper;

import MbemX.example.streaming.platform.Dto.ActivationCodeDto;
import MbemX.example.streaming.platform.Entity.ActivationCode;
import MbemX.example.streaming.platform.Entity.User;
import org.springframework.stereotype.Component;

@Component
public class ActivationCodeMapper {
    public ActivationCodeDto toDto(ActivationCode activationCode){
        if (activationCode == null){
            return null;
        }
        return new ActivationCodeDto(
                activationCode.getId(),
                activationCode.getCode(),
                activationCode.getDateGeneration(),
                activationCode.getExpirationDate(),
                activationCode.getUsed(),
                activationCode.getUser() != null
                        ? activationCode.getUser().getId()
                        : null
        );
    }

    public ActivationCode toEntity(ActivationCodeDto dto, User user){
        if (dto == null){
            return null;
        }
        ActivationCode activationCode = new ActivationCode();
        activationCode.setCode(dto.code());
        activationCode.setId(dto.id());
        activationCode.setUser(user);
        activationCode.setDateGeneration(dto.dateGeneration());
        activationCode.setExpirationDate(dto.expirationDate());

        return activationCode;
    }
}
