package com.multi.service;

import com.multi.common.DBConnectionMgr;
import com.multi.common.exception.CustomerException;
import com.multi.model.DAO.TravelDao;
import com.multi.model.DTO.TravelVO;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class TravelService {
    private final TravelDao travelDao;
    Connection conn;
    DBConnectionMgr dbcp;

    public TravelService(){
        dbcp = DBConnectionMgr.getInstance();
        if(dbcp.getConnectionCount()==0){
            try {
                dbcp.setInitOpenConnections(10);
            } catch (SQLException e) {
//                throw new RuntimeException(e);
                System.err.println(e.getMessage());
            }
        }
        travelDao = new TravelDao();

    }

    public ArrayList<TravelVO> selectByName(String name) throws CustomerException {
        Connection conn = null;
        try {
            conn = dbcp.getConnection();
            return travelDao.selectByName(conn, name);
        }catch(CustomerException e){
            throw e;
        }
        catch (Exception e) {
//            throw new RuntimeException(e);
            throw new CustomerException(e.getMessage());
        }

    }

   /* public ArrayList<TravelVO> selectByCount() {
        ArrayList<TravelVO> list = null;
        try {
            conn = dbcp.getConnection();
            list = travelDao.selectByCount(conn);
            return list;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }*/

    public void exitProgram() {
        dbcp.freeConnection(conn);
    }

    public ArrayList<TravelVO> selectDistrictByCount() throws CustomerException {
        ArrayList<TravelVO> list= null;
        try{
            conn = dbcp.getConnection();
            list = travelDao.selectDistrictByCount(conn);
            return list;
        } catch(CustomerException e){
            throw e;
        }
        catch (Exception e) {
//            throw new RuntimeException(e);
            throw new CustomerException(e.getMessage());
        }

    }

    public HashMap<TravelVO,Integer> selectTouristAttByCount(String district) throws CustomerException {
        HashMap<TravelVO,Integer> map = null;
        try{
            conn = dbcp.getConnection();
            map = travelDao.selectTouristAttByCount(conn, district);
            return map;
        } catch(CustomerException e){
            throw e;
        }
        catch (Exception e) {
//            throw new RuntimeException(e);
            throw new CustomerException(e.getMessage());
        }
    }

    public int insertRandomCount() throws CustomerException {
        int result = 0;
        try{
            conn = dbcp.getConnection();
            conn.setAutoCommit(false);
            result = travelDao.insertRandomCount(conn);
            if(result>0)
                if(conn!=null) conn.commit();
            else
                if(conn!=null) conn.rollback();
            return result;
        } catch(CustomerException e){
            throw e;
        }
        catch (Exception e) {
//            throw new RuntimeException(e);
            throw new CustomerException(e.getMessage());
        }
    }

    public int insertZeroCount() throws CustomerException {
        int result = 0;
        try{
            conn = dbcp.getConnection();
            conn.setAutoCommit(false);
            result = travelDao.insertZeroCount(conn);
            if(result>0)
                if(conn!=null) conn.commit();
                else
                if(conn!=null) conn.rollback();
            return result;
        } catch(CustomerException e){
            throw e;
        }
        catch (Exception e) {
//            throw new RuntimeException(e);
            throw new CustomerException(e.getMessage());
        }
    }

    public int updateTravelCount(ArrayList<TravelVO> list) throws CustomerException {
        int result = 0;
        try{
            conn = dbcp.getConnection();
            conn.setAutoCommit(false);
            result = travelDao.updateTravelCount(conn,list);
            if(result>0)
                if(conn!=null) conn.commit();
            else
                if(conn!=null) conn.rollback();
            return result;
        } catch(CustomerException e){
            throw e;
        }
        catch (Exception e) {
//            throw new RuntimeException(e);
            throw new CustomerException(e.getMessage());
        }

    }

    //김태용님
    // 지역별 관광지 목록 가져오기
    public List<TravelVO> getAttractionsByRegion(String region) throws CustomerException {
        List<TravelVO> attractions;
        try (Connection conn = dbcp.getConnection()) {
            attractions = travelDao.getAttractionsByRegion(conn, region);
        } catch(CustomerException e){
            throw e;
        } catch (Exception e) {
//            throw new RuntimeException(e);
            throw new CustomerException(e.getMessage());
        }
        return attractions;
    }

    // 관광지 번호로 상세 정보 가져오기
   /* public TravelVO getAttractionByNo(int attractionNo) {
        TravelVO attraction = null;
        try (Connection conn = dbcp.getConnection()) {
            attraction = travelDao.getAttractionByNo(conn, attractionNo);
        } catch (SQLException e) {
            throw new RuntimeException("DB 연결 오류", e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return attraction;
    }*/

    // 관광지 조회수 증가
   /* public void increaseAttractionViewCount(int attractionNo) {
        try (Connection conn = dbcp.getConnection()) {
            travelDao.increaseViewCount(conn, attractionNo);
        } catch (SQLException e) {
            throw new RuntimeException("DB 연결 오류", e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }*/

    public ArrayList<TravelVO> selectPage(int currPage, int pageSize) throws CustomerException {
        ArrayList<TravelVO> list = null;
        try{
            conn = dbcp.getConnection();
            list = travelDao.selectPage(conn,currPage, pageSize);
        } catch(CustomerException e){
            throw e;
        } catch (Exception e) {
//            throw new RuntimeException(e);
            throw new CustomerException(e.getMessage());
        }

        return list;

    }
}

