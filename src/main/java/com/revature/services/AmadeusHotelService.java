package com.revature.services;

import com.revature.models.Hotel;
import com.revature.services.DTOs.AmadeusHotelResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.*;

@Service
public class AmadeusHotelService {

    private final HotelService hotelService;
    private final RestTemplate restTemplate;
    private final AmadeusAuthService amadeusAuthService;


    @Autowired
    public AmadeusHotelService(HotelService hotelService, RestTemplate restTemplate, AmadeusAuthService amadeusAuthService) {
        this.hotelService = hotelService;
        this.restTemplate = restTemplate;
        this.amadeusAuthService = amadeusAuthService;
    }

    public void fetchAndSaveHotelsByCity(String cityCode) {
        String amadeusApiUrl = buildAmadeusApiUrl(cityCode);
        String accessToken = amadeusAuthService.getAccessToken();

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);

        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<AmadeusHotelResponse[]> response = restTemplate.exchange(amadeusApiUrl, HttpMethod.GET, entity, AmadeusHotelResponse[].class);

        if (response.getStatusCode() == HttpStatus.OK) {
            AmadeusHotelResponse[] hotels = response.getBody();
            if (hotels != null) {
                for (AmadeusHotelResponse apiHotel : hotels) {
                    Hotel hotel = mapToHotelEntity(apiHotel);
                    hotelService.saveOrUpdateHotel(hotel);
                }
            }
        }
    }

    private String buildAmadeusApiUrl(String cityCode) {
        return "https://test.api.amadeus.com/v1/reference-data/locations/hotels/by-city?cityCode=" + cityCode;
    }

    private Hotel mapToHotelEntity(AmadeusHotelResponse apiHotel) {
        Hotel hotel = new Hotel();
        hotel.setHotelId(apiHotel.getId());
        hotel.setHotelName(apiHotel.getName());
        hotel.setAddress(apiHotel.getAddress().getLines().get(0));
        return hotel;
    }
}