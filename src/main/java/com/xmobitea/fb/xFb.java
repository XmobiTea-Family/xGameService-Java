package com.xmobitea.fb;

import lombok.NonNull;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public final class xFb {
    private final static String GRAPH_FB_API_URL = "graph.facebook.com";
    private final static String GRAPH_FB_API_CONTENT_URL = "/me?fields=id,name,email,friends&access_token=";

    private final static HttpClient client = HttpClient.newHttpClient();

    public static Object getBasicInfoByAccessToken(@NonNull String accessToken) throws IOException, InterruptedException  {
        var request = HttpRequest.newBuilder(URI.create(GRAPH_FB_API_URL + GRAPH_FB_API_CONTENT_URL + accessToken))
                .build();

        return client.send(request, HttpResponse.BodyHandlers.ofString());
    }
}
