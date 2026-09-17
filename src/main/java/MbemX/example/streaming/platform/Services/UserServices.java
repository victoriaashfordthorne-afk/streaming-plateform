package MbemX.example.streaming.platform.Services;

import MbemX.example.streaming.platform.Dto.UserDto;
import MbemX.example.streaming.platform.Entity.User;
import MbemX.example.streaming.platform.Mapper.UserMapper;
import MbemX.example.streaming.platform.Repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
public class UserServices {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserServices(UserRepository userRepository,UserMapper userMapper){
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }
    public List<UserDto> getAll() {
        return userRepository.findAll()
                .stream()
                .sorted(Comparator.comparing(User::getId))
                .map(userMapper::toDto)
                .toList();
    }

    public UserDto findById(Long id){
        User user = userRepository.findById(id)
                .orElseThrow(()->new RuntimeException("User not found with id:"+ id));

        return userMapper.toDto(user);
    }
    public UserDto saveUser(UserDto dto){
        User user = userMapper.toEntity(dto);
        User saveUser = userRepository.save(user);

        return userMapper.toDto(saveUser);

    }

    public UserDto update(Long id, UserDto dto) {

        User existingUser = userRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("User not found with id: " + id));
        existingUser.setId(dto.id());
        existingUser.setName(dto.name());
        existingUser.setEmail(dto.email());
        existingUser.setActiveAccount(dto.activeAccount());
        existingUser.setLastConnection(dto.lastConnection());
        existingUser.setDateCreation(dto.dateCreation());

        User updateUser = userRepository.save(existingUser);

        return userMapper.toDto(updateUser);
    }
    public void deleteUser(Long id){
        User user = userRepository.findById(id)
                .orElseThrow(()->new RuntimeException("User not found with id:"+ id));
        userRepository.delete(user);
    }
    public UserDto findByEmail(String email){
        User user = userRepository.findByEmail(email)
                .orElseThrow(()->new RuntimeException("User not found with email:"+ email));
        return userMapper.toDto(user);
    }
}
