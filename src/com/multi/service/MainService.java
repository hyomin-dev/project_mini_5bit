package com.multi.service;

import com.multi.common.DBConnectionMgr;
import com.multi.model.DAO.TravelDAO;
import com.multi.model.DTO.Travel;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import static com.multi.common.JDBCConnect.getConnection;


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

    public ArrayList<Travel> selectByName(String name) {
        Connection conn = getConnection();
        return travelDAO.selectByName(conn, name);
    }

    public void exitProgram() {
        dbcp.freeConnection(conn);
    }
}
