package MbemX.example.streaming.platform.Mapper;

import MbemX.example.streaming.platform.Dto.UserDto;
import MbemX.example.streaming.platform.Entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public UserDto toDto(User user) {
        if (user == null) {
            return null;
        }
        return new UserDto(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getConnectionMethod(),
                user.getActiveAccount(),
                user.getDateCreation(),
                user.getLastConnection()
        );
    }

    public User toEntity(UserDto dto){
        if (dto == null){
            return null;
        }
        User user = new User();
        user.setId(dto.id());
        user.setName(dto.name());
        user.setEmail(dto.email());
        user.setDateCreation(dto.dateCreation());
        user.setActiveAccount(dto.activeAccount());
        user.setLastConnection(dto.lastConnection());
        user.setConnectionMethod(dto.connectionMethod());

        return user;
    }
}
