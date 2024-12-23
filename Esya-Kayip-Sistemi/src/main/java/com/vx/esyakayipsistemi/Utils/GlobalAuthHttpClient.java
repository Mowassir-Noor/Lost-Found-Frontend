package com.vx.esyakayipsistemi.Utils;

import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.util.Base64;
import java.util.UUID;

public class GlobalAuthHttpClient {

    //singleton to make it global Only one instance of the class exists
    // any attempt to instantiate the class returns the same instance
    private static final HttpClient httpclient= HttpClient.newHttpClient();

    private static String authHeader;


    //set basic auth header with credentials
    public static void setBasicAuth(String email,String password){
        authHeader="Basic "+ Base64.getEncoder().encodeToString((email+":"+password).getBytes());

    }
    public static String getAuthHeader(){
        return authHeader;
    }

    //Creating a request builder with basic auth
    public static HttpRequest.Builder createRequestBuilder(URI uri){
        if(authHeader==null){
            throw new IllegalStateException("Authentication not initialized call setBasicAuth() method first");
        }
        return HttpRequest.newBuilder(uri).setHeader("Authorization",authHeader);
    }

//send a request and return the response
    public static HttpResponse<String> sendRequest(HttpRequest request) throws Exception{
        return httpclient.send(request,HttpResponse.BodyHandlers.ofString());
    }

    // send async request
    public static void sendAsyncRequest(HttpRequest request){
        httpclient.sendAsync(request,HttpResponse.BodyHandlers.ofString()).thenAccept(response -> {
            if (response.statusCode() == 200) {
                System.out.println("Response: "+response.body());
            }
            else{
                System.out.println("failed: "+response.statusCode());
            }
        });
    }


//-----------------------------------------------------------------------------------------------

   // Custom get function to reduce redundency and make the code more manageable
    public static HttpResponse<String> get(String url) throws IOException, InterruptedException {

        HttpRequest request=HttpRequest.newBuilder(URI.create(url))
                .header("Authorization",authHeader)
                .GET()
                .build();
        return httpclient.send(request,HttpResponse.BodyHandlers.ofString());
    }

    //custom post function without authorization header
    public static HttpResponse<String> post(String url, String json) throws Exception{

        HttpRequest request=HttpRequest.newBuilder(URI.create(url))
//                .header("Authorization",authHeader)
                .header("Content-Type","application/json")
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .build();

        return httpclient.send(request,HttpResponse.BodyHandlers.ofString());
    }







//*******************************************************************************************************************
//
//    public static HttpResponse<String> postWithJsonAndImage(String url, String json, File image) throws Exception{
////   generate the boundary for the multipart/form-data
//        String boundary= UUID.randomUUID().toString();
//
////        Creating the body part of the request multipart/form-data
//        String boundaryPrefix="--"+boundary;
//        String contentType="multipart/form-data; boundary="+boundary;
//
////        prepare the json part
//        String jsonPart = boundaryPrefix + "\r\n" +
//                "Content-Disposition: form-data; name=\"itemsDto\"\r\n" +
//                "Content-Type: application/json\r\n\r\n" +
//                json + "\r\n";
//
//        //Prepare the image part
//
//        String imagePart = boundaryPrefix + "\r\n" +
//                "Content-Disposition: form-data; name=\"file\"; filename=\"" + image.getName() + "\"\r\n" +
//                "Content-Type: image/jpeg\r\n\r\n";
//
//
//        //prepare the request body
//
//        byte[] imageBytes = java.nio.file.Files.readAllBytes(image.toPath());
//
//        byte[] body = new byte[
//                jsonPart.getBytes().length
//                        + imagePart.getBytes().length
//                        + imageBytes.length
//                        + (boundaryPrefix + "--\r\n").getBytes().length];
//
//
//        //combine json and image to the final body
//        int pos=0;
//        pos=appendBytes(jsonPart.getBytes(),body,pos);
//        pos=appendBytes(imagePart.getBytes(),body,pos);
//        pos=appendBytes(imageBytes,body,pos);
//        pos=appendBytes((boundaryPrefix+"--\r\n").getBytes(),body,pos);
//
//        //creating the http request to post
//        HttpRequest request =HttpRequest.newBuilder()
//                .header("Authorization",authHeader)
//                .header("Content-Type",contentType)
//                .header("Content-Length",String.valueOf(body.length))
//                .POST(HttpRequest.BodyPublishers.ofByteArray(body))
//                .uri(URI.create(url))
//                .build();
//
//        return httpclient.send(request,HttpResponse.BodyHandlers.ofString());
//
//    }
//
//    //helper function to append bytes to the body
//    private static int appendBytes(byte[] source,byte[] target, int startPos){
//        System.arraycopy(source,0,target,startPos,source.length);
//        return startPos+source.length;
//
//    }


//-------------------------------------------------------------------------------------------------------------------


//    public static HttpResponse<String> postWithJsonAndImage(String url, String json, File image) throws Exception {
//        String boundary = UUID.randomUUID().toString();
//        String boundaryPrefix = "--" + boundary;
//        String contentType = "multipart/form-data; boundary=" + boundary;
//
//        // Prepare JSON part
//        String jsonPart = boundaryPrefix + "\r\n" +
//                "Content-Disposition: form-data; name=\"itemsDto\"\r\n" +
//                "Content-Type: application/json\r\n\r\n" +
//                json + "\r\n";
//
//        // Prepare Image part
//        String imagePartHeader = boundaryPrefix + "\r\n" +
//                "Content-Disposition: form-data; name=\"file\"; filename=\"" + image.getName() + "\"\r\n" +
//                "Content-Type: " + java.nio.file.Files.probeContentType(image.toPath()) + "\r\n\r\n";
//
//        byte[] imageBytes = java.nio.file.Files.readAllBytes(image.toPath());
//        byte[] closingBoundary = (boundaryPrefix + "--\r\n").getBytes();
//
//        // Combine all parts into the request body
//        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
//        outputStream.write(jsonPart.getBytes());
//        outputStream.write(imagePartHeader.getBytes());
//        outputStream.write(imageBytes);
//        outputStream.write(closingBoundary);
//
//        byte[] body = outputStream.toByteArray();
//
//        // Create HTTP request
//        HttpRequest request = HttpRequest.newBuilder()
//                .header("Authorization", authHeader)
//                .header("Content-Type", contentType)
////                .header("Content-Length", String.valueOf(body.length))
//                .POST(HttpRequest.BodyPublishers.ofByteArray(body))
//                .uri(URI.create(url))
//                .build();
//
//        HttpClient httpClient = HttpClient.newHttpClient();
//        return httpClient.send(request, HttpResponse.BodyHandlers.ofString());
//    }
//
//
//************************************************************************************************************************************

    // above mehtods are not  working  it took me 6 hours to solve this problem

public static HttpResponse<String> postWithJsonAndImage(String url, String json, File image) throws Exception {
    String boundary = UUID.randomUUID().toString();
    String boundaryPrefix = "--" + boundary;
    String contentType = "multipart/form-data; boundary=" + boundary;
    // preparing json part
    String jsonPart = boundaryPrefix + "\r\n" +
            "Content-Disposition: form-data; name=\"itemsDto\"\r\n" +
            "Content-Type: application/json\r\n\r\n" +
            json + "\r\n";
    // preparing image part
    String imagePartHeader = boundaryPrefix + "\r\n" +
            "Content-Disposition: form-data; name=\"file\"; filename=\"" + image.getName() + "\"\r\n" +
            "Content-Type: " + Files.probeContentType(image.toPath()) + "\r\n\r\n";

    byte[] imageBytes = Files.readAllBytes(image.toPath());
    byte[] closingBoundary = ("\r\n" + boundaryPrefix + "--\r\n").getBytes();
    // combine all parts of the body
    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    outputStream.write(jsonPart.getBytes());
    outputStream.write(imagePartHeader.getBytes());
    outputStream.write(imageBytes);
    outputStream.write(closingBoundary);

    byte[] body = outputStream.toByteArray();

    // creating http request
    HttpRequest request = HttpRequest.newBuilder()
            .header("Authorization", authHeader)
            .header("Content-Type", contentType)
            .POST(HttpRequest.BodyPublishers.ofByteArray(body))
            .uri(URI.create(url))
            .build();

    HttpClient httpClient = HttpClient.newHttpClient();
    return httpClient.send(request, HttpResponse.BodyHandlers.ofString());
}






    //creating custom delete request to delete any item

    public static HttpResponse<String> delete(String url) throws Exception{
        HttpRequest request=HttpRequest.newBuilder(URI.create(url))
                .header("Authorization",authHeader)
                .DELETE()
                .build();

        return httpclient.send(request,HttpResponse.BodyHandlers.ofString());
    }


//    deleteMethod Overloaded
    public static HttpResponse<String> delete(String url, @NotNull Long id) throws Exception{
        String itemToDelete=url+"/"+id.toString();
        HttpRequest request=HttpRequest.newBuilder(URI.create(itemToDelete))
                .header("Authorization",authHeader)
                .DELETE()
                .build();

        return httpclient.send(request,HttpResponse.BodyHandlers.ofString());
    }



}
