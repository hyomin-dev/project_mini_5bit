package com.travelapp.service;

import com.travelapp.dao.TravelDao;
import com.travelapp.model.TravelVO;
import java.util.List;

public class TravelService {
    private TravelDao travelDao;

    public TravelService() {
        this.travelDao = new TravelDao();
    }

    public void addTravel(TravelVO travel) {
        travelDao.insertTravel(travel);
    }

    public List<TravelVO> getTravelsByPage(int page, int pageSize) {
        return travelDao.getAllTravels(page, pageSize);
    }
}