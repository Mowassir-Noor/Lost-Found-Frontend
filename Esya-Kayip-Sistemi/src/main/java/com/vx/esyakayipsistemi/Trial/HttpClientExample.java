package com.vx.esyakayipsistemi.Trial;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.vx.esyakayipsistemi.Utils.GlobalAuthHttpClient;
import org.apache.hc.core5.http.io.entity.EmptyInputStream;
import org.apache.http.HttpEntity;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.util.EntityUtils;

public class HttpClientExample {
    public static void main(String[] args) throws Exception {
        GlobalAuthHttpClient.setBasicAuth("mowassiradmin@gmail.com","mowassiradmin");
        HttpClient client = HttpClient.newHttpClient();

        Path filePath = Paths.get("C:/Users/hpw/Desktop/devre analizi sorulari2.jpg");
        String jsonData = "{\"itemName\":\"batman\"}"; // Replace with actual JSON data

        HttpEntity multipart = MultipartEntityBuilder.create()
                .setMode(HttpMultipartMode.BROWSER_COMPATIBLE)
                .addBinaryBody("file", filePath.toFile(), ContentType.APPLICATION_OCTET_STREAM, filePath.getFileName().toString())
                .addTextBody("itemsDto", jsonData, ContentType.APPLICATION_JSON)
                .build();

        // Convert HttpEntity to InputStream
        InputStream entityStream = multipart.isStreaming() ? multipart.getContent() : EmptyInputStream.INSTANCE;
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[8192];
        int bytesRead;
        while ((bytesRead = entityStream.read(buffer)) != -1) {
            byteArrayOutputStream.write(buffer, 0, bytesRead);
        }

        String boundary = multipart.getContentType().getValue().split("boundary=")[1];
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/admin/items"))
                .header("Authorization", GlobalAuthHttpClient.getAuthHeader())
                .header("Content-Type", "multipart/form-data; boundary=" + boundary)
                .POST(HttpRequest.BodyPublishers.ofByteArray(byteArrayOutputStream.toByteArray()))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        // Debugging output
        System.out.println("Request URI: " + request.uri());
        System.out.println("Request Headers: " + request.headers());
        System.out.println("Request Body: " + byteArrayOutputStream.toString());
        System.out.println("Response Code: " + response.statusCode());
        System.out.println("Response Body: " + response.body());
    }
}