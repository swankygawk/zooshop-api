package ru.sfu.zooshop.mapper.category;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.sfu.zooshop.configuration.MapstructMapperConfiguration;
import ru.sfu.zooshop.entity.SubcategoryEntity;
import ru.sfu.zooshop.dto.response.open.subcategory.BasicSubcategoryResponse;
import ru.sfu.zooshop.mapper.Default;

import static ru.sfu.zooshop.constant.Constant.DEFAULT_SUBCATEGORY_PICTURE_URL;

@Mapper(config = MapstructMapperConfiguration.class)
public interface SubcategoryMapper extends Default {
  @Mapping(
    source = "pictureUrl",
    target = "pictureUrl",
    defaultValue = DEFAULT_SUBCATEGORY_PICTURE_URL
  )
  BasicSubcategoryResponse subcategoryEntityToBasicSubcategoryResponse(SubcategoryEntity subcategoryEntity);
}
