package MbemX.example.streaming.platform.Services;

import MbemX.example.streaming.platform.Dto.ActivationCodeDto;
import MbemX.example.streaming.platform.Entity.ActivationCode;
import MbemX.example.streaming.platform.Entity.User;
import MbemX.example.streaming.platform.Mapper.ActivationCodeMapper;
import MbemX.example.streaming.platform.Repository.ActivationCodeRepository;
import MbemX.example.streaming.platform.Repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

@Service
public class ActivationCodeServices {
    private final ActivationCodeRepository activationCodeRepository;
    private final ActivationCodeMapper activationCodeMapper;
    private final UserRepository userRepository;

    public ActivationCodeServices(ActivationCodeRepository activationCodeRepository,ActivationCodeMapper activationCodeMapper,UserRepository userRepository){
        this.activationCodeMapper = activationCodeMapper;
        this.activationCodeRepository = activationCodeRepository;
        this.userRepository = userRepository;
    }

    public List<ActivationCodeDto> findAllCode(){
        List<ActivationCode> dto = activationCodeRepository.findAll();
        return dto.stream()
                .map(activationCodeMapper::toDto)
                .toList();
    }

    public ActivationCodeDto findById(Long id){
        ActivationCode activationCode = activationCodeRepository.findById(id)
                .orElseThrow(()->new RuntimeException("Activation code not found with id"+id));

        return activationCodeMapper.toDto(activationCode);
    }
    public ActivationCodeDto saveActivationCode(ActivationCodeDto dto){
        User user = userRepository.findById(dto.userId())
                .orElseThrow(()->new RuntimeException("User not found with id"+dto.userId()));

        ActivationCode activationCode = activationCodeMapper.toEntity(dto,user);
        ActivationCode saveActivationCode = activationCodeRepository.save(activationCode);
        return activationCodeMapper.toDto(saveActivationCode);
    }
    public ActivationCodeDto updateCode(Long id,ActivationCodeDto dto){
        ActivationCode existingCode = activationCodeRepository.findById(id)
                .orElseThrow(()->new RuntimeException("Activation code not found with id"+ id));

        User user = userRepository.findById(id)
                .orElseThrow(()->new RuntimeException("User not found with id"+ id));
        existingCode.setCode(dto.code());
        existingCode.setExpirationDate(dto.expirationDate());
        existingCode.setDateGeneration(dto.dateGeneration());
        existingCode.setUser(user);

        ActivationCode updateCode = activationCodeRepository.save(existingCode);
        return activationCodeMapper.toDto(updateCode);
    }
    public void deleteCode(Long id){
        ActivationCode activationCode = activationCodeRepository.findById(id)
                .orElseThrow(()->new RuntimeException("Activation code not found with is"+id));

        activationCodeRepository.delete(activationCode);
    }
    public ActivationCodeDto findByCode(String code){
        ActivationCode activationCode = activationCodeRepository.findByCode(code)
                .orElseThrow(()->new RuntimeException("Activation not found:"+ code));

        return activationCodeMapper.toDto(activationCode);
    }
}
