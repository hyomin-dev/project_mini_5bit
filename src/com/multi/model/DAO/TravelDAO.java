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
