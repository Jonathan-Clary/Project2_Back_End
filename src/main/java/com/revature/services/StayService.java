package com.revature.services;

import com.revature.DAOs.StayDAO;
import com.revature.models.Stay;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StayService {

    private StayDAO stayDAO;

    @Autowired
    public StayService(StayDAO stayDAO) {
        this.stayDAO = stayDAO;
    }

    //STAYS-HISTORY: Service for Getting Stay History
    public List<Stay> getStaysByUserId(int userId) {
        return stayDAO.findByUserId(userId);
    }
}
