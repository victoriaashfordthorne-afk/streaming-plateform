package MbemX.example.streaming.platform.Services;

import MbemX.example.streaming.platform.Dto.CommentDto;
import MbemX.example.streaming.platform.Entity.Comment;
import MbemX.example.streaming.platform.Entity.File;
import MbemX.example.streaming.platform.Entity.User;
import MbemX.example.streaming.platform.Mapper.CommentMapper;
import MbemX.example.streaming.platform.Repository.CommentRepository;
import MbemX.example.streaming.platform.Repository.FileRepository;
import MbemX.example.streaming.platform.Repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

@Service
public class CommentServices {
    private final CommentRepository commentRepository;
    private final CommentMapper commentMapper;
    private final UserRepository userRepository;
    private FileRepository fileRepository;

    public CommentServices(CommentRepository commentRepository,CommentMapper commentMapper,UserRepository userRepository,FileRepository fileRepository){
        this.commentRepository = commentRepository;
        this.commentMapper = commentMapper;
        this.userRepository = userRepository;
        this.fileRepository = fileRepository;
    }

    public List<CommentDto> findAll(){
        List<Comment> dto = commentRepository.findAll();
        return dto.stream()
                .map(commentMapper::toDto)
                .toList();
    }

    public CommentDto findById(Long id){
        Comment comment = commentRepository.findById(id)
                .orElseThrow(()->new RuntimeException("Comment not found with id:"+ id));

        return commentMapper.toDto(comment);
    }

    public CommentDto saveComment(CommentDto dto){
        User user =  userRepository.findById(dto.userId())
                .orElseThrow(()->new RuntimeException("User not found with id:"+ dto.userId()));

        File file = fileRepository.findById(dto.fileId())
                .orElseThrow(()->new RuntimeException("File not found with id"+dto.fileId()));
        Comment comment = commentMapper.toEntity(dto,user,file);
        Comment saveComment = commentRepository.save(comment);
        return commentMapper.toDto(saveComment);

    }

    public CommentDto updateComment(Long id,CommentDto dto){
        Comment existingComment = commentRepository.findById(id)
                .orElseThrow(()->new RuntimeException("Comment not found with id"+id));

        User user = userRepository.findById(dto.userId())
                .orElseThrow(()->new RuntimeException("User not found id"+ dto.userId()));
        File file = fileRepository.findById(dto.fileId())
                .orElseThrow(()->new RuntimeException("File not found with id"+ dto.fileId()));

        existingComment.setDateComment(dto.dateComment());
        existingComment.setContent(dto.content());
        existingComment.setUser(user);
        existingComment.setFile(file);
        Comment updateComment = commentRepository.save(existingComment);
        return commentMapper.toDto(updateComment);
    }

    public void deleteComment(Long id){
        Comment comment = commentRepository.findById(id)
                .orElseThrow(()->new RuntimeException("Comment not found with id:"+id));

                commentRepository.delete(comment);
    }

    public List<CommentDto> findByFileId(Long id){
        return commentRepository.findByFileId(id)
                .stream()
                .map(commentMapper::toDto)
                .toList();
    }
}
