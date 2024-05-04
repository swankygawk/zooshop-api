package ru.sfu.zooshop.mapper.category;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.sfu.zooshop.configuration.MapstructMapperConfiguration;
import ru.sfu.zooshop.dto.response.open.category.BasicCategoryResponse;
import ru.sfu.zooshop.dto.response.open.category.RichCategoryResponse;
import ru.sfu.zooshop.entity.CategoryEntity;
import ru.sfu.zooshop.mapper.Default;

import static ru.sfu.zooshop.constant.Constant.DEFAULT_CATEGORY_PICTURE_URL;

@Mapper(config = MapstructMapperConfiguration.class)
public interface CategoryMapper extends Default {
  @Mapping(
    source = "pictureUrl",
    target = "pictureUrl",
    defaultValue = DEFAULT_CATEGORY_PICTURE_URL
  )
  BasicCategoryResponse categoryEntityToBasicCategoryResponse(CategoryEntity categoryEntity);

  @Mapping(
    source = "pictureUrl",
    target = "pictureUrl",
    defaultValue = DEFAULT_CATEGORY_PICTURE_URL
  )
  RichCategoryResponse categoryEntityToRichCategoryResponse(CategoryEntity categoryEntity);

}
