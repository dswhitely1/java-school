package com.lambdaschool.school.service;

import com.lambdaschool.school.exceptions.ResourceNotFoundException;
import com.lambdaschool.school.model.Instructor;
import com.lambdaschool.school.repository.InstructorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service(value = "instructorService")
public class InstructorServiceImpl implements InstructorService
{
    @Autowired
    private InstructorRepository instructrepos;

    @Override
    public List<Instructor> findAll()
    {
        List<Instructor> list = new ArrayList<>();
        instructrepos.findAll().iterator().forEachRemaining(list::add);
        return list;
    }

    @Override
    public Instructor findById(long id)
    {
        return instructrepos.findById(id).orElseThrow(() -> new ResourceNotFoundException(Long.toString(id)));
    }

    @Override
    public ArrayList<Instructor> findInstructorsByInstructnameEquals(String name)
    {
        ArrayList<Instructor> list = new ArrayList<>();
        instructrepos.findAll().forEach(instructor ->
        {
            if (instructor.getInstructname().equalsIgnoreCase(name))
            {
                list.add(instructor);
            }
        });
        return list;
    }

    @Override
    public Instructor save(Instructor newInstructor)
    {
        
        return null;
    }

    @Override
    public Instructor update(Instructor updateInstructor, long id)
    {
        return null;
    }

    @Override
    public void delete(long id)
    {

    }
}
