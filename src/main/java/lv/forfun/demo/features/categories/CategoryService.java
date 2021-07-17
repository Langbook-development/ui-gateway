package lv.forfun.demo.features.categories;

import lombok.RequiredArgsConstructor;
import lv.forfun.demo.api.categories.CategoriesResponse;
import lv.forfun.demo.api.categories.CategoryDto;
import lv.forfun.demo.domain.Category;
import lv.forfun.demo.domain.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository repository;

    public CategoriesResponse getAll() {
        List<CategoryDto> dtos = repository.findAll().stream()
                .map(this::map)
                .collect(Collectors.toList());
        return new CategoriesResponse()
                .setCategories(dtos);
    }

    private CategoryDto map(Category category) {
        return new CategoryDto()
                .setId(category.getId().toString())
                .setCaption(category.getCaption());
    }
}
