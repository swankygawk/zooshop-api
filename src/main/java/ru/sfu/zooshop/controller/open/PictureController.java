package ru.sfu.zooshop.controller.open;

import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.sfu.zooshop.service.FileService;

import static org.springframework.http.MediaType.IMAGE_PNG_VALUE;
import static ru.sfu.zooshop.constant.Constant.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/picture")
@Validated
public class PictureController {
  private final FileService fileService;

  @GetMapping(
    path = "/profile/{name}",
    produces = IMAGE_PNG_VALUE
  )
  public byte[] getProfilePicture(@PathVariable("name") @NotBlank(message = "Name must not be empty") String name) {
    return fileService.getFile(PROFILE_PICTURE_STORAGE_LOCATION, name);
  }

  @GetMapping(
    path = "/category/{name}",
    produces = IMAGE_PNG_VALUE
  )
  public byte[] getCategoryPicture(@PathVariable("name") @NotBlank(message = "Name must not be empty") String name) {
    return fileService.getFile(CATEGORY_PICTURE_STORAGE_LOCATION, name);
  }

  @GetMapping(
    path = "/subcategory/{name}",
    produces = IMAGE_PNG_VALUE
  )
  public byte[] getCSubcategoryPicture(@PathVariable("name") @NotBlank(message = "Name must not be empty") String name) {
    return fileService.getFile(SUBCATEGORY_PICTURE_STORAGE_LOCATION, name);
  }

  @GetMapping(
    path = "/product/{name}",
    produces = IMAGE_PNG_VALUE
  )
  public byte[] getProductPicture(@PathVariable("name") @NotBlank(message = "Name must not be empty") String name) {
    return fileService.getFile(PRODUCT_PICTURE_STORAGE_LOCATION, name);
  }
}
