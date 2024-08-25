package com.revature.services;

import com.amadeus.Amadeus;
import com.amadeus.Params;
import com.amadeus.exceptions.ResponseException;
import com.amadeus.resources.Hotel;
import com.revature.models.LocalHotel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.amadeus.exceptions.ResponseException;
import java.util.UUID;

@Service
public class AmadeusHotelService {

    private final HotelService hotelService;
    private final Amadeus amadeus;

    @Autowired
    public AmadeusHotelService(HotelService hotelService,
                               @Value("${amadeus.apiKey}") String apiKey,
                               @Value("${amadeus.apiSecret}") String apiSecret) {
        this.hotelService = hotelService;
        this.amadeus = Amadeus
                .builder(apiKey, apiSecret)
                .build();
    }

// WORK IN PROGRESS
public void fetchAndSaveHotelsByCity(String cityCode) {
    try {
        // Fetch hotel data from Amadeus API
        Hotel[] hotels = amadeus.referenceData.locations.hotels.byCity.get(Params.with("cityCode", cityCode));

        // Process each hotel
        for (Hotel amadeusHotel : hotels) {
            LocalHotel localHotel = new LocalHotel();
            localHotel.setHotelId(UUID.randomUUID());
            localHotel.setHotelName(amadeusHotel.getName());

            // Set the apiHotelId from the Amadeus API
            localHotel.setApiHotelId(amadeusHotel.getHotelId());

            // Handle hotel address
            if (amadeusHotel.getAddress() != null) {
                String address = amadeusHotel.getAddress().getLines() != null
                        ? String.join(", ", amadeusHotel.getAddress().getLines())
                        : "Unknown Address";
                localHotel.setAddress(address);
            } else {
                localHotel.setAddress("Unknown Address");
            }

            // Save or update the LocalHotel entity
            hotelService.saveOrUpdateHotel(localHotel);
        }

    } catch (ResponseException e) {
        System.err.println("Failed to fetch hotels: " + e.getMessage());
        e.printStackTrace();
    }
}


}
