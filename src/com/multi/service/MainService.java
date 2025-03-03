package com.multi.service;

import com.multi.common.DBConnectionMgr;
import com.multi.model.DAO.TravelDAO;
import com.multi.model.DTO.Travel;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


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
        Connection conn = null;
        try {
            conn = dbcp.getConnection();
            return travelDAO.selectByName(conn, name);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    public ArrayList<Travel> selectByCount() {
        ArrayList<Travel> list = null;
        try {
            conn = dbcp.getConnection();
            list = travelDAO.selectByCount(conn);
            return list;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }


    }

    public void exitProgram() {
        dbcp.freeConnection(conn);
    }

    public ArrayList<Travel> selectDistrictByCount() {
        ArrayList<Travel> list= null;
        try{
            conn = dbcp.getConnection();
            list = travelDAO.selectDistrictByCount(conn);
            return list;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    public ArrayList<Travel> selectTouristAttByCount(String district) {
        ArrayList<Travel> list = null;
        try{
            conn = dbcp.getConnection();
            list = travelDAO.selectTouristAttByCount(conn, district);
            return list;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public int insertRandomCount() {
        int result = 0;
        try{
            conn = dbcp.getConnection();
            conn.setAutoCommit(false);
            result = travelDAO.insertRandomCount(conn);
            if(result>0)
                if(conn!=null) conn.commit();
            else
                if(conn!=null) conn.rollback();
            return result;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public int insertZeroCount() {
        int result = 0;
        try{
            conn = dbcp.getConnection();
            conn.setAutoCommit(false);
            result = travelDAO.insertZeroCount(conn);
            if(result>0)
                if(conn!=null) conn.commit();
                else
                if(conn!=null) conn.rollback();
            return result;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public int updateTravelCount(ArrayList<Travel> list) {
        int result = 0;
        try{
            conn = dbcp.getConnection();
            conn.setAutoCommit(false);
            result = travelDAO.updateTravelCount(conn,list);
            if(result>0)
                if(conn!=null) conn.commit();
                else
                if(conn!=null) conn.rollback();
            return result;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    //김태용님
    // 지역별 관광지 목록 가져오기
    public List<Travel> getAttractionsByRegion(String region) {
        List<Travel> attractions;
        try (Connection conn = dbcp.getConnection()) {
            attractions = travelDAO.getAttractionsByRegion(conn, region);
        } catch (SQLException e) {
            throw new RuntimeException("DB 연결 오류", e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return attractions;
    }

    // 관광지 번호로 상세 정보 가져오기
    public Travel getAttractionByNo(int attractionNo) {
        Travel attraction = null;
        try (Connection conn = dbcp.getConnection()) {
            attraction = travelDAO.getAttractionByNo(conn, attractionNo);
        } catch (SQLException e) {
            throw new RuntimeException("DB 연결 오류", e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return attraction;
    }

    // 관광지 조회수 증가
    public void increaseAttractionViewCount(int attractionNo) {
        try (Connection conn = dbcp.getConnection()) {
            travelDAO.increaseViewCount(conn, attractionNo);
        } catch (SQLException e) {
            throw new RuntimeException("DB 연결 오류", e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public ArrayList<Travel> selectPage(int currPage, int pageSize) {
        ArrayList<Travel> list = null;
        try{
            conn = dbcp.getConnection();
            list = travelDAO.selectPage(conn,currPage, pageSize);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return list;

    }
}

