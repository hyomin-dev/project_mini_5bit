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
    private Properties prop = null;

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
            DBConnectionMgr dbcp = new DBConnectionMgr();
            dbcp.freeConnection(conn);
        }
        return list;
    }
}
