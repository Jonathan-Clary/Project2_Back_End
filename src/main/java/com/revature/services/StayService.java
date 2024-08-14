package com.revature.services;

import com.revature.DAOs.StayDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StayService {

    private StayDAO stayDAO;

    @Autowired
    public StayService(StayDAO stayDAO) {
        this.stayDAO = stayDAO;
    }
}
