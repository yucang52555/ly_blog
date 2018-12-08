package com.lengyan.lyblog.service.impl;

import com.lengyan.lyblog.model.domain.Category;
import com.lengyan.lyblog.repository.CategoryRepository;
import com.lengyan.lyblog.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * <pre>
 *     分类业务逻辑实现类
 * </pre>
 *
 * @author : lengyan
 * @date : 2017/11/30
 */
@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    /**
     * 保存/修改分类目录
     *
     * @param category 分类目录
     * @return Category
     */
    @Override
    public Category saveByCategory(Category category) {
        return categoryRepository.save(category);
    }

    /**
     * 根据编号移除分类目录
     *
     * @param cateId 分类目录编号
     * @return Category
     */
    @Override
    public Category removeByCateId(Long cateId) {
        Optional<Category> category = this.findByCateId(cateId);
        categoryRepository.delete(category.get());
        return category.get();
    }

    /**
     * 查询所有分类目录
     *
     * @return List
     */
    @Override
    public List<Category> findAllCategories() {
        return categoryRepository.findAll();
    }

    /**
     * 根据编号查询分类目录
     *
     * @param cateId 分类编号
     * @return Category
     */
    @Override
    public Optional<Category> findByCateId(Long cateId) {
        return categoryRepository.findById(cateId);
    }

    /**
     * 根据分类目录路径查询，用于验证是否已经存在该路径
     *
     * @param cateUrl cateUrl
     * @return Category
     */
    @Override
    public Category findByCateUrl(String cateUrl) {
        return categoryRepository.findCategoryByCateUrl(cateUrl);
    }

    /**
     * 将分类字符串集合转化为Category泛型集合
     *
     * @param strings strings
     * @return List
     */
    @Override
    public List<Category> strListToCateList(List<String> strings) {
        if (null == strings) {
            return null;
        }
        List<Category> categories = new ArrayList<>();
        Optional<Category> category = null;
        for (String str : strings) {
            category = findByCateId(Long.parseLong(str));
            categories.add(category.get());
        }
        return categories;
    }
}
