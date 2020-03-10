package com.lambdaschool.school.service;

import com.lambdaschool.school.model.Instructor;

import java.util.ArrayList;
import java.util.List;

public interface InstructorService
{
    List<Instructor> findAll();

    Instructor findById(long id);

    ArrayList<Instructor> findInstructorsByInstructnameEquals (String name);

    Instructor save(Instructor newInstructor);

    Instructor update(Instructor updateInstructor, long id);

    void delete(long id);
}
