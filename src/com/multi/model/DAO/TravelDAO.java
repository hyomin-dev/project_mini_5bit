package com.multi.model.DAO;


import java.io.FileReader;
import java.io.IOException;

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

}
