package com.multi.model.DAO;


import com.multi.common.DBConnectionMgr;
import com.multi.model.DTO.Travel;

import java.io.FileReader;
import java.io.IOException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Properties;

public class TravelDAO {
    private Properties prop;

    public TravelDAO(){
        try{
            prop = new Properties();
            prop.load(new FileReader("./resources/query.properties"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public ArrayList<Travel> selectByName(Connection conn, String name) {
        ArrayList<Travel> list = new ArrayList<>();

        // SQL 쿼리 가져오기
        String sql = prop.getProperty("selectByName");
        if (sql == null || sql.trim().isEmpty()) {
            throw new IllegalArgumentException("SQL 쿼리가 설정되지 않았습니다: selectByName");
        }

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, "%" + name + "%"); // LIKE 검색을 위한 % 추가

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Travel m = new Travel();
                    m.setNo(rs.getInt("no"));
                    m.setDistrict(rs.getString("district"));
                    m.setTitle(rs.getString("title"));
                    m.setDescription(rs.getString("description"));
                    m.setAddress(rs.getString("address"));
                    m.setPhone(rs.getString("phone"));
                    m.setCount(rs.getInt("count"));
                    list.add(m);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("데이터베이스 오류 발생", e);
        } finally {
//            DBConnectionMgr dbcp = new DBConnectionMgr();
//            dbcp.freeConnection(conn);
            DBConnectionMgr.getInstance().freeConnection(conn);
        }
        return list;
    }

    public ArrayList<Travel> selectByCount(Connection conn) {
        String sql = prop.getProperty("selectAll");
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        ArrayList<Travel> list = new ArrayList<>();
        try{
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();
            while(rs.next()){
                Travel travel = new Travel();
                travel.setNo(rs.getInt("no"));
                travel.setDistrict(rs.getString("district"));
                travel.setTitle(rs.getString("title"));
                travel.setDescription(rs.getString("description"));
                travel.setAddress(rs.getString("address"));
                travel.setPhone(rs.getString("phone"));
                travel.setCount(rs.getInt("count"));
                list.add(travel);
            }
            return list;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally{
            DBConnectionMgr.getInstance().freeConnection(pstmt,rs);
        }



    }

    public ArrayList<Travel> selectDistrictByCount(Connection conn) {
        String sql = prop.getProperty("selectDistrictByCount");
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        ArrayList<Travel> list = new ArrayList<>();
        try{
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();
            while(rs.next()){
                Travel travel = new Travel();

                travel.setDistrict(rs.getString("district"));

                travel.setCount(rs.getInt("sum_count"));
                list.add(travel);
            }
            return list;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally{
            DBConnectionMgr.getInstance().freeConnection(pstmt,rs);
        }
    }

    public ArrayList<Travel> selectTouristAttByCount(Connection conn, String district) {
        String sql = prop.getProperty("selectTouristAttByCount");
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        ArrayList<Travel> list = new ArrayList<>();
        try{
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1,district);
            rs = pstmt.executeQuery();
            while(rs.next()){
                Travel travel = new Travel();
                travel.setTitle(rs.getString("title"));
                travel.setAddress(rs.getString("address"));
                travel.setCount(rs.getInt("count"));
                list.add(travel);
            }
            return list;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally{
            DBConnectionMgr.getInstance().freeConnection(pstmt,rs);
        }

    }

    public int insertRandomCount(Connection conn) {
        String sql = prop.getProperty("insertRandomCount");
        PreparedStatement pstmt = null;
        int result = 0;
        try{
            pstmt = conn.prepareStatement(sql);
            result = pstmt.executeUpdate();
            return result;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally{
            DBConnectionMgr.getInstance().freeConnection(pstmt);
        }
    }

    public int insertZeroCount(Connection conn) {
        String sql = prop.getProperty("insertZeroCount");
        PreparedStatement pstmt = null;
        int result = 0;
        try{
            pstmt = conn.prepareStatement(sql);
            result = pstmt.executeUpdate();
            return result;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally{
            DBConnectionMgr.getInstance().freeConnection(pstmt);
        }

    }
}

