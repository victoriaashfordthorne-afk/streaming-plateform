package MbemX.example.streaming.platform.Mapper;

import MbemX.example.streaming.platform.Dto.ReactionDto;
import MbemX.example.streaming.platform.Entity.File;
import MbemX.example.streaming.platform.Entity.Reaction;
import MbemX.example.streaming.platform.Entity.User;
import org.springframework.stereotype.Component;

@Component
public class ReactionMapper {
    public ReactionDto toDto(Reaction reaction){
        if (reaction == null){
            return  null;
        }
        return new ReactionDto(
                reaction.getId(),
                reaction.getType(),
                reaction.getDateReaction(),
                reaction.getUser() != null
                        ? reaction.getUser().getId()
                        : null,
                reaction.getFile() != null
                        ? reaction.getFile().getId()
                        : null
        );
    }
    public Reaction toEntity(ReactionDto dto, User user, File file){
        if (dto == null){
            return  null;
        }
        Reaction reaction = new Reaction();
        reaction.setDateReaction(dto.dateReaction());
        reaction.setId(dto.id());
        reaction.setType(dto.type());
        reaction.setUser(user);
        reaction.setFile(file);

        return  reaction;
    }
}
