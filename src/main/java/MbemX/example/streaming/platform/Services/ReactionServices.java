package MbemX.example.streaming.platform.Services;

import MbemX.example.streaming.platform.Dto.ReactionDto;
import MbemX.example.streaming.platform.Entity.File;
import MbemX.example.streaming.platform.Entity.Reaction;
import MbemX.example.streaming.platform.Entity.User;
import MbemX.example.streaming.platform.Mapper.ReactionMapper;
import MbemX.example.streaming.platform.Repository.FileRepository;
import MbemX.example.streaming.platform.Repository.ReactionRepository;
import MbemX.example.streaming.platform.Repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReactionServices {
    private final ReactionRepository reactionRepository;
    private final ReactionMapper reactionMapper;
    private final UserRepository userRepository;
    private final FileRepository fileRepository;

    public ReactionServices(ReactionRepository reactionRepository, ReactionMapper reactionMapper, UserRepository userRepository, FileRepository fileRepository) {
        this.reactionRepository = reactionRepository;
        this.reactionMapper = reactionMapper;
        this.fileRepository = fileRepository;
        this.userRepository = userRepository;
    }

    public List<ReactionDto> findAllReaction() {
        List<Reaction> dto = reactionRepository.findAll();
        return dto.stream()
                .map(reactionMapper::toDto)
                .toList();

    }

    public ReactionDto findById(Long id) {
        Reaction reaction = reactionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Reaction not found with id:" + id));
        return reactionMapper.toDto(reaction);
    }

    public ReactionDto saveReaction(ReactionDto dto) {

        User user = userRepository.findById(dto.userId())
                .orElseThrow(() ->
                        new RuntimeException(
                                "User not found with id:" + dto.userId()
                        ));

        File file = fileRepository.findById(dto.fileId())
                .orElseThrow(() ->
                        new RuntimeException(
                                "File not found with id:" + dto.fileId()
                        ));

        Reaction reaction = reactionRepository
                .findByUserIdAndFileId(dto.userId(), dto.fileId())
                .orElse(null);

        if (reaction == null) {

            reaction = reactionMapper.toEntity(
                    dto,
                    user,
                    file
            );

        } else {

            reaction.setType(dto.type());
            reaction.setDateReaction(dto.dateReaction());
        }

        Reaction savedReaction = reactionRepository.save(reaction);

        return reactionMapper.toDto(savedReaction);
    }

    public ReactionDto updateReaction(ReactionDto dto ,Long id){
        Reaction existingReaction = reactionRepository.findById(id)
                .orElseThrow(()->new RuntimeException("Reaction not found with id:"+id));

        User user = userRepository.findById(dto.userId())
                .orElseThrow(()->new RuntimeException("User not found with id:"+ dto.userId()));

        File file = fileRepository.findById(dto.fileId())
                .orElseThrow(()->new RuntimeException("File not found with id:"+dto.fileId()));

        existingReaction.setDateReaction(dto.dateReaction());
        existingReaction.setType(dto.type());
        existingReaction.setUser(user);
        existingReaction.setFile(file);

        Reaction updateReaction = reactionRepository.save(existingReaction);
        return reactionMapper.toDto(updateReaction);
    }

    public void deleteReaction(Long id){
        Reaction reaction = reactionRepository.findById(id)
                .orElseThrow(()->new RuntimeException("Reaction not found with id:"+ id));
        reactionRepository.delete(reaction);

    }

    public ReactionDto findByUserIdAndFileId(Long userId,Long fileId){
        Reaction reaction = reactionRepository.findByUserIdAndFileId(userId,fileId)
                .orElseThrow(()->new RuntimeException("Reaction not found"));
        return reactionMapper.toDto(reaction);
    }
}
