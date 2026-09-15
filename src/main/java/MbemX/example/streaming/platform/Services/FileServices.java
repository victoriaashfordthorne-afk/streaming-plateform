package MbemX.example.streaming.platform.Services;

import MbemX.example.streaming.platform.Dto.FileDto;
import MbemX.example.streaming.platform.Entity.File;
import MbemX.example.streaming.platform.Entity.User;
import MbemX.example.streaming.platform.Enums.MediaType;
import MbemX.example.streaming.platform.Mapper.FileMapper;
import MbemX.example.streaming.platform.Repository.FileRepository;
import MbemX.example.streaming.platform.Repository.UserRepository;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.util.UUID;
import java.util.List;



@Service
public class FileServices {
    private final FileRepository fileRepository;
    private final FileMapper fileMapper;
    private final UserRepository userRepository;

    public FileServices(FileRepository fileRepository, FileMapper fileMapper, UserRepository userRepository) {
        this.fileRepository = fileRepository;
        this.fileMapper = fileMapper;
        this.userRepository = userRepository;
    }

    public List<FileDto> findAllFile() {
        List<File> dto = fileRepository.findAll();
        return dto.stream()
                .map(fileMapper::fileDto)
                .toList();
    }

    public FileDto findById(Long id) {
        File file = fileRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("File not found with id:" + id));

        return fileMapper.fileDto(file);
    }

    public FileDto saveFile(FileDto dto) {
        User user = userRepository.findById(dto.userId())
                .orElseThrow(() -> new RuntimeException("User not found with id:" + dto.userId()));

        File file = fileMapper.toEntity(dto, user);
        File saveFile = fileRepository.save(file);
        return fileMapper.fileDto(saveFile);
    }

    public FileDto updateFile(FileDto dto, Long id) {

        File existingFile = fileRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "File not found with id:" + id
                        ));

        User user = userRepository.findById(dto.userId())
                .orElseThrow(() ->
                        new RuntimeException(
                                "User not found with id:" + dto.userId()
                        ));

        existingFile.setOriginalName(dto.originalName());
        existingFile.setStockName(dto.stockName());
        existingFile.setTypeMedia(dto.typeMedia());
        existingFile.setMimetype(dto.mimetype());
        existingFile.setSize(dto.size());
        existingFile.setStoragePath(dto.storagePath());
        existingFile.setDateUpload(dto.dateUpload());
        existingFile.setUser(user);

        File updatedFile = fileRepository.save(existingFile);

        return fileMapper.fileDto(updatedFile);
    }

    public void deleteFile(Long id) {
        File file = fileRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("File not found with id:" + id));

        fileRepository.delete(file);

    }

    public List<FileDto> findByUserId(Long userId) {
        return fileRepository.findByUserId(userId)
                .stream()
                .map(fileMapper::fileDto)
                .toList();
    }

    public FileDto uploadFile(MultipartFile multipartFile, Long userId) throws IOException {

        User user = userRepository.findById(userId)
                .orElseThrow(() ->
                        new RuntimeException("User not found with id: " + userId)
                );

        if (multipartFile.isEmpty()) {
            throw new IllegalArgumentException("The uploaded file is empty");
        }

        String originalName = multipartFile.getOriginalFilename();

        if (originalName == null || originalName.isBlank()) {
            throw new IllegalArgumentException("The file name is missing");
        }

        String contentType = multipartFile.getContentType();

        if (contentType == null) {
            throw new IllegalArgumentException("The file type is unknown");
        }

        MediaType mediaType;

        if (contentType.startsWith("video/")) {
            mediaType = MediaType.VIDEO;
        } else if (contentType.startsWith("image/")) {
            mediaType = MediaType.PHOTO;
        } else if (contentType.startsWith("audio/")) {
            mediaType = MediaType.MUSIC;
        } else {
            throw new IllegalArgumentException("Unsupported file type");
        }

        String folder;

        if (mediaType == MediaType.VIDEO) {
            folder = "uploads/videos";
        } else if (mediaType == MediaType.PHOTO) {
            folder = "uploads/photos";
        } else {
            folder = "uploads/music";
        }

        Path uploadDirectory = Paths.get(folder);

        Files.createDirectories(uploadDirectory);

        String extension = "";

        int dotIndex = originalName.lastIndexOf(".");

        if (dotIndex >= 0) {
            extension = originalName.substring(dotIndex);
        }

        String stockName = UUID.randomUUID() + extension;

        Path filePath = uploadDirectory.resolve(stockName);

        Files.copy(
                multipartFile.getInputStream(),
                filePath,
                StandardCopyOption.REPLACE_EXISTING
        );

        File file = new File();

        file.setOriginalName(originalName);
        file.setStockName(stockName);
        file.setTypeMedia(mediaType);
        file.setMimetype(contentType);
        file.setSize(multipartFile.getSize());
        file.setStoragePath(filePath.toString());
        file.setDateUpload(LocalDateTime.now());
        file.setUser(user);

        File savedFile = fileRepository.save(file);

        return fileMapper.fileDto(savedFile);
    }

    public Resource downloadFile(Long id) throws IOException {

        File file = fileRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("File not found with id: " + id)
                );

        Path path = Paths.get(file.getStoragePath());

        Resource resource = new UrlResource(path.toUri());

        if (!resource.exists() || !resource.isReadable()) {
            throw new RuntimeException("File cannot be read: " + file.getOriginalName());
        }

        return resource;
    }
    public List<FileDto> findByUserIdAndType(Long userId, MediaType type) {

        return fileRepository
                .findByUserIdAndTypeMedia(userId, type)
                .stream()
                .map(fileMapper::fileDto)
                .toList();
    }
}