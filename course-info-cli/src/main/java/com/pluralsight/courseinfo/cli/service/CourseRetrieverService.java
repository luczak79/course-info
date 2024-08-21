package com.pluralsight.courseinfo.cli.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

public class CourseRetrieverService {

    private static final String PS_URI = "https://app.pluralsight.com/profile/data/author/%s/all-content";

    private static final HttpClient CLIENT = HttpClient
            .newBuilder()
            .followRedirects(HttpClient.Redirect.ALWAYS)
            .build();

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    public List<PluralsightCourse> getCoursesFor(String authorId) {
        HttpRequest request = HttpRequest
                .newBuilder(URI.create(PS_URI.formatted(authorId)))
                .GET()
                .build();
        try {
            HttpResponse<String> resopnse = CLIENT.send(request, HttpResponse.BodyHandlers.ofString());
            return switch(resopnse.statusCode()) {
                case 200 -> toPluralsightCourses(resopnse);
                case 404 -> List.of();
                default -> throw new IllegalStateException("Pluralsight API call filed with status code: " + resopnse.statusCode());
            };
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException("Could not call PLuralsight API", e);
        }

    }

    private static List<PluralsightCourse> toPluralsightCourses(HttpResponse<String> resopnse) throws JsonProcessingException {
        JavaType returnType = OBJECT_MAPPER.getTypeFactory()
                        .constructCollectionType(List.class, PluralsightCourse.class);
        return OBJECT_MAPPER.readValue(resopnse.body(), returnType);
    }
}
