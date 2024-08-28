package com.revature.services;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.revature.DTOs.HotelDTO;
import com.revature.exceptions.BadRequestException;
import com.revature.exceptions.CustomException;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class HotelAPIService {




    public List<HotelDTO> findHotelsByCityAndState(String location) throws CustomException {
        try {
            // Define the API endpoint and your API key
            String apiKey = "&key=AIzaSyBxUK3IJcgz1dffYlPGonw5P0uLBten9rU";
            String urlString = "https://maps.googleapis.com/maps/api/place/textsearch/json?query=hotels+in+"+location + apiKey;

            // Create a URL object with the API endpoint
            URL url = new URL(urlString);

            // Open an HttpURLConnection to the URL
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            // Set the request method to GET
            connection.setRequestMethod("GET");

            // Get the response code
            int responseCode = connection.getResponseCode();

            // Check if the request was successful
            if (responseCode == HttpURLConnection.HTTP_OK) {
                // Read the response from the input stream
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String inputLine;
                StringBuilder response = new StringBuilder();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }

                // Close the input stream
                in.close();

                // Parse JSON response and create a list of Location objects
                List<HotelDTO> hotels = parseJsonResponse(response.toString());


                return hotels;

            } else {
                System.out.println("GET request failed. Response Code: " + responseCode);
            }

            // Disconnect the connection
            connection.disconnect();

        } catch (Exception e) {
            e.printStackTrace();
        }
       throw new BadRequestException(location);
    }


    public static List<HotelDTO> parseJsonResponse(String jsonResponse) {
        List<HotelDTO> hotels = new ArrayList<>();

        JsonObject jsonObject = JsonParser.parseString(jsonResponse).getAsJsonObject();
        JsonArray results = jsonObject.getAsJsonArray("results");

        for (JsonElement element : results) {
            JsonObject hotelObj = element.getAsJsonObject();

            if(hotelObj.get("business_status").getAsString().equals("OPERATIONAL")) {
                String name = hotelObj.get("name").getAsString();
                String placeId = hotelObj.get("place_id").getAsString();
                String address = hotelObj.get("formatted_address").getAsString();
                double rating = hotelObj.has("rating") ? hotelObj.get("rating").getAsDouble() : 0.0;
                String photoReference = "";

                if (hotelObj.has("photos") && hotelObj.getAsJsonArray("photos").size() > 0) {
                    JsonObject photoObj = hotelObj.getAsJsonArray("photos").get(0).getAsJsonObject();
                    photoReference = fetchImageUrl("https://maps.googleapis.com/maps/api/place/photo?maxwidth=400&photoreference="
                            + photoObj.get("photo_reference").getAsString() + "&key=AIzaSyBxUK3IJcgz1dffYlPGonw5P0uLBten9rU");
                }

                HotelDTO hotel = new HotelDTO(name, rating, photoReference, address, placeId);
                hotels.add(hotel);
            }
        }

        return hotels;
    }


    public static String fetchImageUrl(String urlStr) {
        String finalUrl = "";
        try {
            URL url = new URL(urlStr);

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setInstanceFollowRedirects(false); // Disable automatic redirection handling
            conn.connect();

            String connString = conn.toString();

            finalUrl = connString.substring(connString.indexOf("https://"));


            conn.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return finalUrl;
    }


}
