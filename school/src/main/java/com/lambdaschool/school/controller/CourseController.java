package com.lambdaschool.school.controller;

import com.lambdaschool.school.model.Course;
import com.lambdaschool.school.service.CourseService;
import com.lambdaschool.school.view.CountStudentsInCourses;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/courses")
public class CourseController
{
    private static final Logger logger = LoggerFactory.getLogger(CourseController.class);

    @Autowired
    private CourseService courseService;

    @ApiOperation(value = "Return all courses using Paging and Sorting",
                  response = Course.class,
                  responseContainer = "List")
    @ApiImplicitParams({@ApiImplicitParam(name = "page",
                                          dataType = "integer",
                                          paramType = "query",
                                          value = "Results page you want to retrieve(0..N)"), @ApiImplicitParam(name = "size",
                                                                                                                dataType = "integer",
                                                                                                                paramType = "query",
                                                                                                                value = "Number of Records per page"), @ApiImplicitParam(name = "sort",
                                                                                                                                                                         allowMultiple = true,
                                                                                                                                                                         dataType = "string",
                                                                                                                                                                         paramType = "query",

                                                                                                                                                                         value = "Sorting criteria in the format: property(,asc|desc).  Default sort order is ascending.  Multiple sort criteria are supported")})
    @ApiResponses({@ApiResponse(code = 200,
                                message = "Courses found")})
    @GetMapping(value = "/courses",
                produces = {"application/json"})
    public ResponseEntity<?> listAllCoursesSorted(
            @PageableDefault(page = 0,
                             size = 5)
                    Pageable pageable, HttpServletRequest request)
    {
        logger.info(request.getMethod() + " " + request.getRequestURI() + " accessed");
        ArrayList<Course> myCourses = courseService.findAll(pageable);
        return new ResponseEntity<>(myCourses, HttpStatus.OK);
    }

    @ApiOperation(value = "Returns all courses",
                  response = Course.class,
                  responseContainer = "List")
    @GetMapping(value = "/allcourses",
                produces = {"application/json"})
    public ResponseEntity<?> listAllCourses(Pageable pageable, HttpServletRequest request)
    {
        logger.info(request.getMethod() + " " + request.getRequestURI() + " accessed");
        ArrayList<Course> myCourses = courseService.findAll(pageable);
        return new ResponseEntity<>(myCourses, HttpStatus.OK);
    }

    @ApiOperation(value = "Returns Courses and Number of Students enrolled",
                  response = CountStudentsInCourses.class,
                  responseContainer = "List")
    @GetMapping(value = "/studcount",
                produces = {"application/json"})
    public ResponseEntity<?> getCountStudentsInCourses(HttpServletRequest request)
    {
        logger.info(request.getMethod() + " " + request.getRequestURI() + " accessed");
        List<CountStudentsInCourses> list = courseService.getCountStudentsInCourse();
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @ApiOperation(value = "Delete a course with the provided course id",
                  response = void.class)
    @DeleteMapping("/courses/{courseid}")
    public ResponseEntity<?> deleteCourseById(
            @PathVariable
                    long courseid, HttpServletRequest request)
    {
        logger.info(request.getMethod() + " " + request.getRequestURI() + " accessed");
        courseService.delete(courseid);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
