package MbemX.example.streaming.platform.Controller;

import MbemX.example.streaming.platform.Dto.FileDto;
import MbemX.example.streaming.platform.Enums.MediaType;
import MbemX.example.streaming.platform.Mapper.FileMapper;
import MbemX.example.streaming.platform.Repository.FileRepository;
import MbemX.example.streaming.platform.Services.FileServices;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/fichiers")
@Tag(
        name = "Files",
        description = "Upload, consultation, download and filtering of media files"
)
public class FileController {

    private final FileServices fileServices;
    private final FileRepository fileRepository;
    private final FileMapper fileMapper;

    public FileController(FileServices fileServices,FileRepository fileRepository,FileMapper fileMapper) {
        this.fileServices = fileServices;
        this.fileRepository = fileRepository;
        this.fileMapper = fileMapper;
    }

    // =========================================================
    // 1. UPLOAD A FILE
    // POST /api/fichiers
    // =========================================================

    @PostMapping
    @Operation(
            summary = "Upload a file",
            description = "Upload a video, photo or music file"
    )
    public ResponseEntity<FileDto> uploadFile(
            @RequestParam("file") MultipartFile file,
            @RequestParam("userId") Long userId
    ) throws IOException {

        FileDto uploadedFile = fileServices.uploadFile(file, userId);

        return ResponseEntity.ok(uploadedFile);
    }


    // =========================================================
    // 2. LIST FILES
    // GET /api/fichiers
    // GET /api/fichiers?type=VIDEO
    // =========================================================

    @GetMapping
    @Operation(
            summary = "List files",
            description = "List files and optionally filter by VIDEO, PHOTO or MUSIC"
    )
    public ResponseEntity<List<FileDto>> findFiles(
            @RequestParam(required = false) MediaType type,
            @RequestParam Long userId
    ) {

        List<FileDto> files;

        if (type == null) {
            files = fileServices.findByUserId(userId);
        } else {
            files = fileServices.findByUserIdAndType(userId, type);
        }

        return ResponseEntity.ok(files);
    }


    // =========================================================
    // 3. VIEW FILE INFORMATION
    // GET /api/fichiers/{id}
    // =========================================================

    @GetMapping("/{id}")
    @Operation(
            summary = "Get file information",
            description = "Get the metadata of one file"
    )
    public ResponseEntity<FileDto> findById(
            @PathVariable Long id
    ) {

        FileDto file = fileServices.findById(id);

        return ResponseEntity.ok(file);
    }
    public List<FileDto> findByUserIdAndType(Long userId, MediaType type) {

        return  fileRepository
                .findByUserIdAndTypeMedia(userId, type)
                .stream()
                .map(fileMapper::fileDto)
                .toList();
    }

    // =========================================================
    // 4. DOWNLOAD / READ FILE
    // GET /api/fichiers/{id}/telecharger
    // =========================================================

    @GetMapping("/{id}/telecharger")
    @Operation(
            summary = "Download a file",
            description = "Download or read the actual uploaded file"
    )
    public ResponseEntity<Resource> downloadFile(
            @PathVariable Long id
    ) throws IOException {

        Resource resource = fileServices.downloadFile(id);

        String contentType = resource.getURL()
                .openConnection()
                .getContentType();

        if (contentType == null) {
            contentType = "application/octet-stream";
        }

        return ResponseEntity.ok()
                .header(
                        HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"" + resource.getFilename() + "\""
                )
                .header(
                        HttpHeaders.CONTENT_TYPE,
                        contentType
                )
                .body(resource);
    }
}