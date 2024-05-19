package ru.sfu.zooshop.service.admin;

import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;
import ru.sfu.zooshop.dto.request.admin.product.ProductRequest;
import ru.sfu.zooshop.dto.request.admin.product.SupplyRequest;
import ru.sfu.zooshop.dto.request.admin.productpicture.ProductPictureOrderRequest;
import ru.sfu.zooshop.dto.response.admin.product.AdminAllProductsResponse;
import ru.sfu.zooshop.dto.response.admin.product.AdminRichProductResponse;

import java.util.List;

public interface AdminProductService {
  AdminAllProductsResponse getProducts(Pageable pageable);
  AdminRichProductResponse getProduct(String slug);
  String createProduct(ProductRequest request);
  void updateProduct(String slug, ProductRequest request);
  void toggleProductVisibility(String slug, boolean hidden);
  void uploadProductPictures(String slug, List<MultipartFile> files);
  void deleteProductPicture(String slug, Long id);
  void updatePictureOrder(String slug, ProductPictureOrderRequest request);
  void addProductSupply(String slug, SupplyRequest request);
  void removeProductSupply(String slug, SupplyRequest request);
}
