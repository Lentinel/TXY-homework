package com.edu.controller.admin;

import com.edu.dto.*;
import com.edu.entity.Course;
import com.edu.result.PageResult;
import com.edu.result.Result;
import com.edu.service.CourseService;
import com.edu.vo.CategoryVO;
import com.edu.vo.CourseSaleVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/admin/course")
@Slf4j
public class CourseController {
    @Autowired
    private CourseService courseService;
    //ok
    @GetMapping("/course-page")
    public Result<PageResult> page(@RequestBody CoursePageQueryDTO coursePageQueryDTO)
    {
        log.info("课程分页查询{}", coursePageQueryDTO);
        PageResult pageResult=courseService.pageQuery(coursePageQueryDTO);
        return Result.success(pageResult);
    }

    @GetMapping("/{id}")//ok
    public Result<Course> details(@PathVariable("id")long id)
    {
        log.info("查询课程详情{}",id);
        Course course=courseService.detailQuery(id);
        return Result.success(course);
    }
    @PutMapping("/{id}/review")//Ok
    public Result<String> review(@RequestBody CourseReviewDTO courseReviewDTO)
    {
        log.info("课程审核");
        courseService.review(courseReviewDTO);
        return Result.success();
    }
    @GetMapping("/{id}/sale")//记得不错的话实际上还没实现
    public Result<CourseSaleVO> checkSales(CourseSaleDTO courseSaleDTO)
    {
        log.info("检查销售额:{}",courseSaleDTO);
        CourseSaleVO courseSaleVO= courseService.checkSales(courseSaleDTO);
        return Result.success(courseSaleVO);

    }
    @PutMapping("/{id}/recommend")//ok
    public Result<String > recommend(@PathVariable("id")long id,
                                     @RequestBody Map<String,Integer> recommend)
    {
        log.info("推荐课程 {},{}",id,recommend.get("recommend"));

        courseService.recommend(id,recommend.get("recommend"));
        return Result.success();
    }
    //课程分类管理（我觉得就是标签）
    @GetMapping("/category/{id}")//ok
    public Result<CategoryVO> getCategory(@PathVariable("id")long id)
    {
        log.info("查询课程分类{}",id);
        CategoryVO categoryVO=courseService.getCategory(id);
        return Result.success(categoryVO);
    }
    @PutMapping("/category/{id}")//ok
    public Result<String> updateCategory(@RequestBody CourseCategoryUpdateDTO courseCategoryUpdateDTO)
    {
        log.info("修改课程分类{}",courseCategoryUpdateDTO);
        courseService.updateCategory(courseCategoryUpdateDTO);
        return Result.success();
    }
    @PostMapping("/category/create")//ok
    public Result<String> createCategory(@RequestBody CreateCourseDTO createCourseDTO)
    {
        log.info("创建新分类{}",createCourseDTO);
        courseService.createCategory(createCourseDTO);
        return Result.success();
    }
    @DeleteMapping("/category/{id}")
    public Result<String> deleteCategory(@PathVariable("id")long id)
    {
        log.info("删除分类{}",id);
        courseService.deleteCategory(id);
        return Result.success();
    }
    //ok
    @DeleteMapping("/{courseId}")
    public Result<String> deleteCourse(@PathVariable("courseId")long courseId)
    {
        log.info("删除课程{}",courseId);
        courseService.deleteCourse(courseId);
        return Result.success();
    }
    //ok
    @GetMapping("/category-page")
    public Result<PageResult> categoryPageQuery(@RequestBody CategoryPageQueryDTO categoryPageQueryDTO)
    {
        log.info("分类分页查询{}", categoryPageQueryDTO);
        PageResult pageResult=courseService.categoryPageQuery(categoryPageQueryDTO);
        return Result.success(pageResult);
    }
}