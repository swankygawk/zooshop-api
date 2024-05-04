package ru.sfu.zooshop.service.impl;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.sfu.zooshop.enumeration.FileType;
import ru.sfu.zooshop.exception.ApiException;
import ru.sfu.zooshop.service.FileService;

import java.nio.file.Path;

import static java.nio.file.Files.*;
import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;
import static org.springframework.web.servlet.support.ServletUriComponentsBuilder.fromCurrentContextPath;

@Slf4j
@Service
@Transactional(rollbackOn = Exception.class)
public class FileServiceImpl implements FileService {
  @Override
  public byte[] getFile(Path path, String fileName) {
    try {
      return readAllBytes(path.resolve(fileName));
    } catch (Exception exception) {
      log.error(exception.getMessage());
      throw new ApiException("An error occurred while getting file");
    }
  }

  @Override
  public String saveFile(MultipartFile file, Path path, String fileName, FileType type) {
    try {
      copy(file.getInputStream(), path.resolve(fileName), REPLACE_EXISTING);
    } catch (Exception exception) {
      log.error(exception.getMessage());
      throw new ApiException("An error occurred while saving file");
    }
    return fromCurrentContextPath().path(type.getLocationOnServer() + fileName).toUriString();
  }

  @Override
  public void deleteFile(Path path, String fileName) {
    try {
      deleteIfExists(path.resolve(fileName));
    } catch (Exception exception) {
      log.error(exception.getMessage());
      throw new ApiException("An error occurred while deleting file");
    }
  }

}
