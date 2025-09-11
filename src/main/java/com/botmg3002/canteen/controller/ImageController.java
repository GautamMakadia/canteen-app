package com.botmg3002.canteen.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.botmg3002.canteen.model.Image;
import com.botmg3002.canteen.repository.ImageRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("image")
public class ImageController {

    private final String uploadDir = "/images";

    @Autowired
    private ImageRepository imageRepository;

    @GetMapping("{id}")
    public ResponseEntity<Resource> getImage(@PathVariable Long id) {
        Image img = imageRepository.findById(id)
                .orElseThrow(
                        () -> new HttpClientErrorException(HttpStatus.NOT_FOUND, "No Image Found!"));

        try {
            Path filePath = Paths.get(uploadDir).resolve(img.getImageUrl()).normalize();
            Resource resource = new UrlResource(filePath.toUri());

            if (!resource.exists()) {
                return ResponseEntity.notFound().build();
            }

            String contentType = Files.probeContentType(filePath);

            if (contentType == null) {
                contentType = "application/octet-stream";
            }

            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(contentType))
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filePath.getFileName())
                    .body(resource);

        } catch (MalformedURLException e) {
            return ResponseEntity.badRequest().build();
        } catch (IOException e) {   
            return ResponseEntity.internalServerError().build();
        }

    }

    @PostMapping()
    public ResponseEntity<Void> uploadImage(@RequestParam MultipartFile file) {

        if (file.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        try {
            Path filePath = Paths.get(uploadDir, file.getOriginalFilename());
            Files.copy(file.getInputStream(), filePath);

            Image image = new Image();
            image.setImageUrl(file.getOriginalFilename());

            image = imageRepository.save(image);

            return ResponseEntity.created(URI.create("/image/"+image.getId())).build();

        } catch (IOException ex) {
            throw new HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
        }

    }

}
