package ru.sfu.zooshop.service;

import org.springframework.web.multipart.MultipartFile;
import ru.sfu.zooshop.enumeration.FileType;

import java.nio.file.Path;

public interface FileService {
  byte[] getFile(Path path, String fileName);
  String saveFile(MultipartFile file, Path path, String fileName, FileType type);
  void deleteFile(Path path, String fileName);
}
