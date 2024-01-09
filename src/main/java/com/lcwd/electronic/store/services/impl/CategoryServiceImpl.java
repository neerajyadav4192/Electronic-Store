package com.lcwd.electronic.store.services.impl;

import com.lcwd.electronic.store.dtos.CategoryDto;
import com.lcwd.electronic.store.dtos.PageableResponse;
import com.lcwd.electronic.store.entities.Category;
import com.lcwd.electronic.store.exceptions.ResourceNotFoundException;
import com.lcwd.electronic.store.helper.Helper;
import com.lcwd.electronic.store.repositories.CategoryRepository;
import com.lcwd.electronic.store.services.CategoryService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class CategoryServiceImpl implements CategoryService {
   @Autowired
    private CategoryRepository categoryRepository;
   @Autowired
   private ModelMapper mapper;

    @Value("${category.profile.image.path}")
    private String imagePath;

    private Logger logger= LoggerFactory.getLogger(CategoryServiceImpl.class);
    @Override
    public CategoryDto create(CategoryDto categoryDto) {
        //creating category id randomly
        String categoryId=UUID.randomUUID().toString();
    categoryDto.setCategoryId(categoryId);
        Category category=mapper.map(categoryDto, Category.class);
        Category savedCategory=categoryRepository.save(category);


        return mapper.map(savedCategory,CategoryDto.class);
    }

    @Override
    public CategoryDto update(CategoryDto categoryDto, String categoryId) {
        //get category of given id
       Category category= categoryRepository.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("Category not found with given id!!"));
       //update category details
        category.setTitle(categoryDto.getTitle());
        category.setDescription(categoryDto.getDescription());
        category.setCoverImage(categoryDto.getCoverImage());
        Category updatedCategory=categoryRepository.save(category);


        return mapper.map(updatedCategory,CategoryDto.class);
    }

    @Override
    public void delete(String categoryId) {
        //get category of given id
        Category category= categoryRepository.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("Category not found with given id!!"));
        //delete category image
        String fullPath=imagePath+category.getCoverImage();
        try {
            Path path = Paths.get(fullPath);
            Files.delete(path);
        }
        catch (NoSuchFileException ex){
            logger.info("User image not found in folder");
            ex.printStackTrace();
        }
        catch (IOException e){
            e.printStackTrace();

        }
        categoryRepository.delete(category);
    }

    @Override
    public PageableResponse<CategoryDto> getAll(int pageNumber,int pageSize,String sortBy,String sortDir) {
        Sort sort=(sortDir.equalsIgnoreCase("desc"))?(Sort.by(sortBy).descending()) :(Sort.by(sortBy).ascending()) ;
        Pageable pageable= PageRequest.of(pageNumber,pageSize,sort);
        Page<Category> page=categoryRepository.findAll(pageable);
        PageableResponse<CategoryDto> pageableResponse=Helper.getPageableResponse(page,CategoryDto.class);

        return pageableResponse;
    }

    @Override
    public CategoryDto get(String categoryId) {
        Category category= categoryRepository.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("Category not found with given id!!"));

        return mapper.map(category,CategoryDto.class);
    }
}
