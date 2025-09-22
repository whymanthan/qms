package com.queue.desktop;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class ApiClient {
    private final String baseUrl;

    public ApiClient() {
        this("http://localhost:8080");
    }

    public ApiClient(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public String getQueueStatus() throws IOException {
        return get("/api/queue/status");
    }

    public String addToQueue(String name, String phone, String service) throws IOException {
        String body = "name=" + url(name) + "&phoneNumber=" + url(phone) + "&service=" + url(service);
        return postForm("/api/queue/add", body);
    }

    public String callNext() throws IOException {
        return get("/api/queue/next");
    }

    public String removeById(long id) throws IOException {
        return delete("/api/queue/remove/" + id);
    }

    public String clearQueue() throws IOException {
        return delete("/api/queue/clear");
    }

    private String get(String path) throws IOException {
        HttpURLConnection conn = (HttpURLConnection) new URL(baseUrl + path).openConnection();
        conn.setRequestMethod("GET");
        return readResponse(conn);
    }

    private String delete(String path) throws IOException {
        HttpURLConnection conn = (HttpURLConnection) new URL(baseUrl + path).openConnection();
        conn.setRequestMethod("DELETE");
        return readResponse(conn);
    }

    private String postForm(String path, String body) throws IOException {
        byte[] bytes = body.getBytes(StandardCharsets.UTF_8);
        HttpURLConnection conn = (HttpURLConnection) new URL(baseUrl + path).openConnection();
        conn.setRequestMethod("POST");
        conn.setDoOutput(true);
        conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        conn.setRequestProperty("Content-Length", String.valueOf(bytes.length));
        try (OutputStream os = conn.getOutputStream()) {
            os.write(bytes);
        }
        return readResponse(conn);
    }

    private String readResponse(HttpURLConnection conn) throws IOException {
        int code = conn.getResponseCode();
        BufferedReader br = new BufferedReader(new InputStreamReader(
                code >= 200 && code < 300 ? conn.getInputStream() : conn.getErrorStream(), StandardCharsets.UTF_8));
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = br.readLine()) != null) {
            sb.append(line);
        }
        br.close();
        return sb.toString();
    }

    private String url(String s) {
        return URLEncoder.encode(s == null ? "" : s, StandardCharsets.UTF_8);
    }
}


