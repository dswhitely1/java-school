package com.lambdaschool.school.service;

import com.lambdaschool.school.model.Course;
import com.lambdaschool.school.view.CountStudentsInCourses;

import java.util.ArrayList;
import java.util.List;

public interface CourseService
{
    ArrayList<Course> findAll();

    List<CountStudentsInCourses> getCountStudentsInCourse();

    void delete(long id);
}
