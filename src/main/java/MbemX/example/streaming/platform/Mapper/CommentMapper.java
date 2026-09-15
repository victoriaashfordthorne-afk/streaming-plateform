package MbemX.example.streaming.platform.Mapper;

import MbemX.example.streaming.platform.Dto.CommentDto;
import MbemX.example.streaming.platform.Entity.Comment;
import MbemX.example.streaming.platform.Entity.File;
import MbemX.example.streaming.platform.Entity.User;
import org.springframework.stereotype.Component;

@Component
public class CommentMapper {
    public CommentDto toDto(Comment comment){
        if (comment == null){
            return null;
        }
        return new CommentDto(
                comment.getId(),
                comment.getContent(),
                comment.getDateComment(),
                comment.getUser() != null
                        ? comment.getUser().getId()
                        : null,
                comment.getFile() != null
                        ? comment.getFile().getId()
                        : null

        );
    }

    public Comment toEntity(CommentDto dto, User user,
                            File file){
        if (dto == null){
            return null;
        }
        Comment comment = new Comment();
        comment.setId(dto.id());
        comment.setDateComment(dto.dateComment());
        comment.setContent(dto.content());
        comment.setFile(file);
        return comment;

    }
}
