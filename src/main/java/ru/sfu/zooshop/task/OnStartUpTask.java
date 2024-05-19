package ru.sfu.zooshop.task;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import static java.nio.file.Files.createDirectory;
import static java.nio.file.Files.exists;
import static ru.sfu.zooshop.constant.Constant.*;

@Component
@Slf4j
@Profile("dev")
public class OnStartUpTask {
  private static boolean executed = false;

  @EventListener(ContextRefreshedEvent.class)
  public void contextRefreshedEvent() throws Exception {
    if (!executed) {
      try {
        if (!exists(FILE_STORAGE_LOCATION)) createDirectory(FILE_STORAGE_LOCATION);
        if (!exists(PROFILE_PICTURE_STORAGE_LOCATION)) createDirectory(PROFILE_PICTURE_STORAGE_LOCATION);
        if (!exists(CATEGORY_PICTURE_STORAGE_LOCATION)) createDirectory(CATEGORY_PICTURE_STORAGE_LOCATION);
        if (!exists(SUBCATEGORY_PICTURE_STORAGE_LOCATION)) createDirectory(SUBCATEGORY_PICTURE_STORAGE_LOCATION);
        if (!exists(PRODUCT_PICTURE_STORAGE_LOCATION)) createDirectory(PRODUCT_PICTURE_STORAGE_LOCATION);
      } catch (Exception exception) {
        log.error(exception.getMessage());
        throw exception;
      }
      log.info("Directories for uploaded files are:");
      log.info("{}", FILE_STORAGE_LOCATION);
      log.info("{}", PROFILE_PICTURE_STORAGE_LOCATION);
      log.info("{}", CATEGORY_PICTURE_STORAGE_LOCATION);
      log.info("{}", SUBCATEGORY_PICTURE_STORAGE_LOCATION);
      log.info("{}", PRODUCT_PICTURE_STORAGE_LOCATION);
      executed = true;
    }
  }
}
