package com.stackroute.keepnote.service;

import java.util.List;

import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.stackroute.keepnote.exception.CategoryDoesNoteExistsException;
import com.stackroute.keepnote.exception.CategoryNotCreatedException;
import com.stackroute.keepnote.exception.CategoryNotFoundException;
import com.stackroute.keepnote.model.Category;
import com.stackroute.keepnote.repository.CategoryRepository;

/*
* Service classes are used here to implement additional business logic/validation 
* This class has to be annotated with @Service annotation.
* @Service - It is a specialization of the component annotation. It doesn't currently 
* provide any additional behavior over the @Component annotation, but it's a good idea 
* to use @Service over @Component in service-layer classes because it specifies intent 
* better. Additionally, tool support and additional behavior might rely on it in the 
* future.
* */
@Service
public class CategoryServiceImpl implements CategoryService {

	/*
	 * Autowiring should be implemented for the CategoryRepository. (Use
	 * Constructor-based autowiring) Please note that we should not create any
	 * object using the new keyword.
	 */
	@Autowired
	private CategoryRepository categoryRepository;

	public CategoryServiceImpl(CategoryRepository categoryRepository) {
		this.categoryRepository = categoryRepository;
	}
	/*
	 * This method should be used to save a new category.Call the corresponding
	 * method of Respository interface.
	 */
	public Category createCategory(Category category) throws CategoryNotCreatedException {
		category.setCategoryCreationDate(new Date());
		Category category1 = categoryRepository.insert(category);

		if (category1 != null) {
			return category1;
		} else {
			throw new CategoryNotCreatedException("CategoryNotCreatedException");
		}
	}

	/*
	 * This method should be used to delete an existing category.Call the
	 * corresponding method of Respository interface.
	 */
	public boolean deleteCategory(String categoryId) throws CategoryDoesNoteExistsException {

		try {
			categoryRepository.deleteById(categoryId);
			return true;
		} catch (Exception e) {
			throw new CategoryDoesNoteExistsException("CategoryDoesNoteExistsException");
		}

	}

	/*
	 * This method should be used to update a existing category.Call the
	 * corresponding method of Respository interface.
	 */
	public Category updateCategory(Category category, String categoryId) {
		categoryRepository.save(category);
		return categoryRepository.findById(categoryId).get();
//		if (categoryRepository.existsById(categoryId)) {
//			Category category2 = categoryRepository.findById(categoryId).get();
//			if (category2 != null) {
//				category2.setCategoryCreatedBy(category.getCategoryCreatedBy());
//				category2.setCategoryCreationDate(category.getCategoryCreationDate());
//				category2.setCategoryDescription(category.getCategoryDescription());
//				category2.setCategoryName(category2.getCategoryName());
//				Category save = categoryRepository.save(category2);
//				return save;
//			} else {
//				return null;
//			}
//		} else {
//			return null;
//		}

	}

	/*
	 * This method should be used to get a category by categoryId.Call the
	 * corresponding method of Respository interface.
	 */
	public Category getCategoryById(String categoryId) throws CategoryNotFoundException {

		Category c = null;
		try {
			c = categoryRepository.findById(categoryId).get();
			if (c == null) {
				throw new CategoryNotFoundException("CategoryNotFoundException");
			} else {
				return c;
			}
		} catch (Exception e) {
			throw new CategoryNotFoundException("CategoryNotFoundException");
		}
	}

	/*
	 * This method should be used to get a category by userId.Call the corresponding
	 * method of Respository interface.
	 */
	public List<Category> getAllCategoryByUserId(String userId) {
		return categoryRepository.findAllCategoryByCategoryCreatedBy(userId);
		}

}
