package com.multi.controller;


import com.multi.model.DTO.Travel;
import com.multi.service.MainService;
import com.multi.view.MainMenu;

import java.util.ArrayList;


public class MainController {
    private final MainService mainService = new MainService();


    public void selectByName(String name) {
        MainMenu mainMenu = new MainMenu();
        ArrayList<Travel> list = mainService.selectByName(name);

        try{
            if(list.isEmpty()){
                mainMenu.displayNoData();
            }else
                mainMenu.displayMemberList(list);
        }catch (Exception e){
            e.printStackTrace();
            mainMenu.displayError("특정 관광지 선택 실패");
        }
    }

    public void exitProgram() {
        mainService.exitProgram();
    }
}
