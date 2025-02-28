package com.travelapp.dao;

import com.travelapp.model.TravelVO;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TravelDao {
    private static final String URL = "jdbc:mysql://localhost:3306/travel_db";
    private static final String USER = "root";
    private static final String PASSWORD = "mysql";

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public void insertTravel(TravelVO travel) {
        String sql = "INSERT INTO travel (location, destination, description) VALUES (?, ?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, travel.getLocation());
            pstmt.setString(2, travel.getDestination());
            pstmt.setString(3, travel.getDescription());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<TravelVO> getAllTravels(int page, int pageSize) {
        List<TravelVO> travels = new ArrayList<>();
        String sql = "SELECT * FROM travel LIMIT ? OFFSET ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, pageSize);
            pstmt.setInt(2, (page - 1) * pageSize);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                TravelVO travel = new TravelVO();
                travel.setId(rs.getInt("id"));
                travel.setLocation(rs.getString("location"));
                travel.setDestination(rs.getString("destination"));
                travel.setDescription(rs.getString("description"));
                travels.add(travel);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return travels;
    }
}