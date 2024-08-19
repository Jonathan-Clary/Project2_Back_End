package com.revature.services.DTOs;
import java.util.List;

public class AmadeusHotelResponse {

    private Long id;
    private String name;
    private Address address;

    public static class Address {
        private List<String> lines;

        public List<String> getLines() {
            return lines;
        }

        public void setLines(List<String> lines) {
            this.lines = lines;
        }
    }

    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }
}