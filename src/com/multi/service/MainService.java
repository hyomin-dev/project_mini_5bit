package com.multi.service;

import com.multi.common.DBConnectionMgr;
import com.multi.model.DAO.TravelDAO;

import java.sql.Connection;
import java.sql.SQLException;


public class MainService {
    private final TravelDAO travelDAO;
    Connection conn;
    DBConnectionMgr dbcp;

    public MainService(){
        dbcp = DBConnectionMgr.getInstance();
        if(dbcp.getConnectionCount()==0){
            try {
                dbcp.setInitOpenConnections(10);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        travelDAO = new TravelDAO();

    }

}
