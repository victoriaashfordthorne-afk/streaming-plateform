package MbemX.example.streaming.platform.Mapper;

import MbemX.example.streaming.platform.Dto.FileDto;
import MbemX.example.streaming.platform.Entity.File;
import MbemX.example.streaming.platform.Entity.User;
import org.springframework.stereotype.Component;

@Component
public class FileMapper {
    public FileDto fileDto(File file){
        if (file == null){
            return null;
        }
        return new FileDto(
                file.getId(),
                file.getOriginalName(),
                file.getStockName(),
                file.getTypeMedia(),
                file.getMimetype(),
                file.getSize(),
                file.getStoragePath(),
                file.getDateUpload(),
                file.getUser() != null
                        ? file.getUser().getId()
                        : null
        );
    }

    public File toEntity(FileDto dto, User user){
        if (dto == null){
            return null;
        }
        File file = new File();
        file.setId(dto.id());
        file.setSize(dto.size());
        file.setStockName(dto.stockName());
        file.setMimetype(dto.mimetype());
        file.setDateUpload(dto.dateUpload());
        file.setOriginalName(dto.originalName());
        file.setStoragePath(dto.storagePath());
        file.setUser(user);
        return file;
    }
}
