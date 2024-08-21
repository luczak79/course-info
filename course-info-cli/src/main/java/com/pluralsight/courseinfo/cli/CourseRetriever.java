package com.pluralsight.courseinfo.cli;

import com.pluralsight.courseinfo.cli.service.CourseRetrieverService;
import com.pluralsight.courseinfo.cli.service.CourseStorageService;
import com.pluralsight.courseinfo.cli.service.PluralsightCourse;
import com.pluralsight.courseinfo.repository.CourseRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import static java.util.function.Predicate.not;

public class CourseRetriever {

    private static final Logger LOG = LoggerFactory.getLogger(CourseRetriever.class);

    public static void main(String[] args) {

        LOG.info("CourseRetriever starting");
        if (args.length == 0) {
            LOG.warn("Please provide an author name as first argument");
            return;
        }

        try {
            retrieveCourses(args[0]);
        } catch (Exception e) {
            LOG.error("Unexpected error", e);
        }
    }

    private static void retrieveCourses(String authorId) {
        LOG.info("Retrieving courses for author '{}'", authorId);
        CourseRetrieverService courseRetrieverService = new CourseRetrieverService();
        CourseRepository courseRepository = CourseRepository.openCourseRepository("./courses.db");
        CourseStorageService courseStorageService = new CourseStorageService(courseRepository);

        List<PluralsightCourse> coursesToStore = courseRetrieverService.getCoursesFor(authorId)
                .stream()
                .filter(not(PluralsightCourse::isRetired))
                .toList();
        LOG.info("Retrived the following {} courses: {}", coursesToStore.size(), coursesToStore);

        courseStorageService.storePluralsightCourses(coursesToStore);
        LOG.info("Stored pluralsight courses");
    }
}
