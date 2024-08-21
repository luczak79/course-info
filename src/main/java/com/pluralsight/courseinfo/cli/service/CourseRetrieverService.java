package com.pluralsight.courseinfo.cli.service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class CourseRetrieverService {

    private static final String PS_URI = "https://app.pluralsight.com/profile/data/author/%s/all-content";

    private static final HttpClient CLIENT = HttpClient.newHttpClient();

    public String getCoursesFor(String authorId) {
        HttpRequest request = HttpRequest
                .newBuilder(URI.create(PS_URI.formatted(authorId)))
                .GET()
                .build();
        try {
            HttpResponse<String> resopnse = CLIENT.send(request, HttpResponse.BodyHandlers.ofString());
            return resopnse.body();
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException("Could not call PLuralsight API", e);
        }

    }
}
