package com.pluralsight.courseinfo.repository;

import com.pluralsight.courseinfo.domain.Course;

import java.util.List;

public interface CourseRepository {

    void saveCourse(Course course);
    List<Course> getAllCourses();

    static CourseRepository openCourseRepository(String databasefile) {
        return new CourseJdbcRepository(databasefile);
    }

}
