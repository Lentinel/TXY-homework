package com.edu.mapper;

import com.edu.dto.CategoryPageQueryDTO;
import com.edu.entity.Category;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CategoryMapper {

    Category getById(long id);

    void update(Category category);

    void insert(Category category);

    void deleteCategory(long id);

    void deleteParentId(long id);

    Page<Category> pageQuery(CategoryPageQueryDTO categoryPageQueryDTO);
}
