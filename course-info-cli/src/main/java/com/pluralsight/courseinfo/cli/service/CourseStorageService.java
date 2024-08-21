package com.pluralsight.courseinfo.cli.service;

import com.pluralsight.courseinfo.domain.Course;
import com.pluralsight.courseinfo.repository.CourseRepository;

import java.util.List;

public class CourseStorageService {

    private static final String PS_BASE_URL = "https://app.pluralsight.com";

    private final CourseRepository courseRepository;

    public CourseStorageService(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    public void storePluralsightCourses(List<PluralsightCourse> psCourses) {
        for (PluralsightCourse psCours : psCourses) {
            Course course = new Course(
                    psCours.id(),
                    psCours.title(),
                    psCours.durationInMinutes(),
                    PS_BASE_URL + psCours.contentUrl()
            );
            courseRepository.saveCourse(course);
        }
    }
}
