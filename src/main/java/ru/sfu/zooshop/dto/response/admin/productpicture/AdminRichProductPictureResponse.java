package ru.sfu.zooshop.dto.response.admin.productpicture;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.sfu.zooshop.dto.response.admin.AdminAuditResponse;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AdminRichProductPictureResponse extends AdminAuditResponse {
  private Long id;
  private String url;
}
