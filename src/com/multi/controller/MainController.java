package com.multi.controller;


import com.multi.model.DTO.Travel;
import com.multi.service.MainService;
import com.multi.view.MainMenu;

import java.util.ArrayList;


public class MainController {
    private final MainService mainService = new MainService();


    public void selectByCount() {
        ArrayList<Travel> list = null;
        MainMenu mainMenu = new MainMenu();
        list = mainService.selectByCount();
        if(!list.isEmpty())
            mainMenu.displayselectByCount("selectByCount OK!!",list);
        else
            mainMenu.displayError();


    }

    public void exitProgram() {
        mainService.exitProgram();
    }

    public void selectDistrictByCount() {
        MainMenu mainMenu = new MainMenu();
        ArrayList<Travel> list = null;
        String district=null;
        list = mainService.selectDistrictByCount();
        if(!list.isEmpty())
            district = mainMenu.displayselectDistrictByCount("selectDistrictByCount OK!!",list);
        else {
            mainMenu.displayError();
            return;
        }

        if(district==null) {
            mainMenu.displayHome();
            return;
        }
        else
            list = mainService.selectTouristAttByCount(district);

        if(!list.isEmpty())
            mainMenu.displayselectTouristAttByCount("selectTouristAttByCount OK!!",list,district);
        else
            mainMenu.displayError();

    }

    public void insertRandomCount() {
        MainMenu mainMenu = new MainMenu();
        int result = 0;
        result = mainService.insertRandomCount();
        if(result>0)
            mainMenu.displaySuccess("insertRandomCount");
        else
            mainMenu.displayError();

    }

    public void insertZeroCount() {
        MainMenu mainMenu = new MainMenu();
        int result = 0;
        result = mainService.insertZeroCount();
        if(result>0)
            mainMenu.displaySuccess("insertZeroCount");
        else
            mainMenu.displayError();
    }
}
