package com.multi.model.DAO;

import com.multi.common.DBConnectionMgr;
import com.multi.common.exception.CustomerException;
import com.multi.model.DTO.TravelVO;

import java.io.FileReader;
import java.io.IOException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

public class TravelDao {
    private Properties prop;

    public TravelDao(){
        try{
            prop = new Properties();
            prop.load(new FileReader("./resources/query.properties"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public ArrayList<TravelVO> selectByName(Connection conn, String name) throws CustomerException{
        ArrayList<TravelVO> list = new ArrayList<>();

        // SQL 쿼리 가져오기
        String sql = prop.getProperty("selectByName");
        if (sql == null || sql.trim().isEmpty()) {
            throw new IllegalArgumentException("SQL 쿼리가 설정되지 않았습니다: selectByName");
        }

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, "%" + name + "%"); // LIKE 검색을 위한 % 추가

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    TravelVO m = new TravelVO();
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
        }
        catch (SQLException e) {
            throwSQLException(e);
        }
        finally {
            DBConnectionMgr.getInstance().freeConnection(conn);
        }
        return list;
    }

    public ArrayList<TravelVO> selectDistrictByCount(Connection conn) throws CustomerException{
        String sql = prop.getProperty("selectDistrictByCount");

        ArrayList<TravelVO> list = new ArrayList<>();

        try(PreparedStatement pstmt = conn.prepareStatement(sql);
            ){
            try(ResultSet rs = pstmt.executeQuery();){
                while(rs.next()){
                    TravelVO travel = new TravelVO();

                    travel.setDistrict(rs.getString("district"));

                    travel.setCount(rs.getInt("sum_count"));
                    list.add(travel);
                }
            }
        }catch (SQLException e) {
            throwSQLException(e);
        }
        return list;
    }

    public HashMap<TravelVO,Integer> selectTouristAttByCount(Connection conn, String district) throws CustomerException {
        String sql = prop.getProperty("selectTouristAttByCount");

        HashMap<TravelVO,Integer> map = new HashMap<>();

        try( PreparedStatement pstmt = conn.prepareStatement(sql);
             ){
            pstmt.setString(1,district);
            try(ResultSet rs  = pstmt.executeQuery();){
                while(rs.next()){
                    TravelVO travel = new TravelVO();
                    travel.setTitle(rs.getString("title"));
                    travel.setCount(rs.getInt("count"));
                    map.put(travel,rs.getInt("rank_count")); //관광지의 rank 값을 map의 value로 저장
                }
            }
        }catch (SQLException e) {
            throwSQLException(e);
        }
        return map;
    }

    public int insertRandomCount(Connection conn) throws CustomerException {
        String sql = prop.getProperty("insertRandomCount");

        int result = 0;
        try(PreparedStatement pstmt = conn.prepareStatement(sql);){
            result = pstmt.executeUpdate();
        }catch (SQLException e) {
            throwSQLException(e);
        }
        return result;
    }

    public int insertZeroCount(Connection conn) throws CustomerException {
        String sql = prop.getProperty("insertZeroCount");
        int result = 0;
        try(PreparedStatement pstmt  = conn.prepareStatement(sql);){
            result = pstmt.executeUpdate();
        }catch (SQLException e) {
            throwSQLException(e);
        }
        return result;
    }

    public int updateTravelCount(Connection conn,ArrayList<TravelVO> list) throws CustomerException {
        int result = 0;
        String sql = prop.getProperty("updateTravelCount");

        try(PreparedStatement pstmt = conn.prepareStatement(sql);){
            for(TravelVO travel :list){
                pstmt.setInt(1,travel.getNo());
                result += pstmt.executeUpdate();
            }
        }catch (SQLException e) {
            throwSQLException(e);
        }
        return result;
    }

    //김태용님
    // 지역별 관광지 목록을 조회하는 메서드
    public List<TravelVO> getAttractionsByRegion(Connection conn, String region) throws CustomerException {
        List<TravelVO> attractions = new ArrayList<>();

        String sql = prop.getProperty("selectAttractionsByRegion"); // query.properties에서 SQL 가져오기

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, region);

            try(ResultSet rs = pstmt.executeQuery();){
                while (rs.next()) {
                    TravelVO attraction = new TravelVO(
                            rs.getInt("no"),
                            rs.getString("district"),
                            rs.getString("title"),
                            rs.getString("description"),
                            rs.getString("address"),
                            rs.getString("phone"),
                            rs.getInt("count")
                    );
                    attractions.add(attraction);
                }
            }
        } catch (SQLException e) {
            throwSQLException(e);
        }
        return attractions;
    }

    // 관광지 번호로 상세 정보 조회하는 메서드
    public TravelVO getAttractionByNo(Connection conn, int attractionNo) {
        TravelVO attraction = null;
        String sql = prop.getProperty("selectAttractionByNo");

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, attractionNo);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                attraction = new TravelVO(
                        rs.getInt("no"),
                        rs.getString("district"),
                        rs.getString("title"),
                        rs.getString("description"),
                        rs.getString("address"),
                        rs.getString("phone"),
                        rs.getInt("count")
                );
            }
        } catch (SQLException e) {
            throwSQLException(e);
        }
        return attraction;
    }

    // 조회수 증가 메서드
    public int increaseViewCount(Connection conn, int attractionNo) {
        String sql = prop.getProperty("updateTravelCount");
        int result = 0;
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, attractionNo);
            result = pstmt.executeUpdate();
        } catch (SQLException e) {
            throwSQLException(e);
        }
        return result;
    }

    public ArrayList<TravelVO> selectPage(Connection conn,int currPage, int pageSize) throws CustomerException {
        ArrayList<TravelVO> list = new ArrayList<>();
        String sql = prop.getProperty("selectPage");

        try(PreparedStatement pstmt = conn.prepareStatement(sql);
            ){
            pstmt.setInt(1, currPage*pageSize);
            pstmt.setInt(2,pageSize);
            try(ResultSet rs  = pstmt.executeQuery();){
                while(rs.next()){
                    TravelVO travel = new TravelVO();
                    travel.setNo(rs.getInt("no"));
                    travel.setDistrict(rs.getString("district"));
                    travel.setTitle(rs.getString("title"));
                    travel.setDescription(rs.getString("description"));
                    travel.setAddress(rs.getString("address"));
                    travel.setPhone(rs.getString("phone"));
                    travel.setCount(rs.getInt("count"));
                    list.add(travel);
                }
            }
        }catch (SQLException e){
            throwSQLException(e);
        }
        return list;
    }

    private void throwSQLException(SQLException e) throws CustomerException{

        throw new CustomerException("데이터베이스 오류 발생!! "+e.getMessage());

    }
}

