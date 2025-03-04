package com.multi.controller;


import com.multi.common.exception.CustomerException;
import com.multi.model.DTO.TravelVO;
import com.multi.service.TravelService;
import com.multi.view.MainMenu;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;


public class MainController {
    private final TravelService travelService = new TravelService();
    private final Scanner scanner = new Scanner(System.in); // 김태용님


    public void selectByName(String name) {
        MainMenu mainMenu = new MainMenu();
        ArrayList<TravelVO> list=null;

        try{
            list = travelService.selectByName(name);
            if(list.isEmpty()){
                mainMenu.displayNoData();
            }else
                mainMenu.displayMemberList(list);
            if(!list.isEmpty())
                updateTravelCount(list);

        }catch(CustomerException e){
            System.err.println(e.getMessage());
        }
        catch (Exception e){
            e.printStackTrace();
            mainMenu.displayError("특정 관광지 선택 실패");
        }
    }

    private void updateTravelCount(ArrayList<TravelVO> list) {
        MainMenu mainMenu = new MainMenu();
        try{
            int result = 0;
            result = travelService.updateTravelCount(list);
            if(result>0)
                mainMenu.displaySuccess("updateTravelCount OK!");
            else
                mainMenu.displayError("updateTravelCount fail");
        }catch(CustomerException e){
            System.err.println(e.getMessage());
        }
    }

    public void exitProgram() {
        travelService.exitProgram();
    }

    public void selectDistrictByCount() {
        MainMenu mainMenu = new MainMenu();
        try{
            ArrayList<TravelVO> list = null;

            String district=null;
            list = travelService.selectDistrictByCount();
            if(!list.isEmpty())
                district = mainMenu.displayselectDistrictByCount("selectDistrictByCount OK!!",list);
            else {
                mainMenu.displayError("selectDistrictByCount fail");
                return;
            }
            selectTouristAttByCount(district);
        }catch(CustomerException e){
            System.err.println(e.getMessage());
        }
    }

    public void selectTouristAttByCount(String district){
        MainMenu mainMenu = new MainMenu();
        try{
            HashMap<TravelVO,Integer> map = null;

            if(district==null) {
                mainMenu.displayHome();
                return;
            }
            else
                map = travelService.selectTouristAttByCount(district);

            if(!map.isEmpty())
                mainMenu.displayselectTouristAttByCount("selectTouristAttByCount OK!!",map,district);
            else
                mainMenu.displayError("조회 결과 없습니다.");
        }catch(CustomerException e){
            System.err.println(e.getMessage());
        }
    }

    public void insertRandomCount() {
        MainMenu mainMenu = new MainMenu();
        try{
            int result = 0;
            result = travelService.insertRandomCount();
            if(result>0)
                mainMenu.displaySuccess("insertRandomCount OK!!");
            else
                mainMenu.displayError("insertRandomCount fail");
        }catch(CustomerException e){
            System.err.println(e.getMessage());
        }
    }

    public void insertZeroCount() {
        MainMenu mainMenu = new MainMenu();
        try{
            int result = 0;
            result = travelService.insertZeroCount();
            if(result>0)
                mainMenu.displaySuccess("insertZeroCount OK!!");
            else
                mainMenu.displayError("insertZeroCount fail");
        }catch(CustomerException e){
            System.err.println(e.getMessage());
        }
    }

    //김태용님
    public void showAttractionsByRegion(String region) {
        MainMenu mainMenu = new MainMenu();
        try{
            List<TravelVO> attractions = travelService.getAttractionsByRegion(region);

            if (attractions.isEmpty()) {
//            System.out.println("해당 지역에는 관광지가 없습니다.");
                mainMenu.displayMessage("해당 지역에는 관광지가 없습니다.");
                return;
            }else
                mainMenu.showAttractionsByRegion(attractions,region);
        }catch(CustomerException e){
            System.err.println(e.getMessage());
        }
    }

    public boolean selectPage(int currPage, int pageSize) {
        MainMenu mainMenu = new MainMenu();
        boolean result=false;
        try{
            ArrayList<TravelVO> list = null;
            list = travelService.selectPage(currPage, pageSize);

            if(!list.isEmpty()) {
                mainMenu.displayAll(list);
            }
            else {
                //mainMenu.displayError("조회 결과 없습니다.");
                result = true;
            }
        }catch(CustomerException e){
            System.err.println(e.getMessage());
        }
       return result;
    }

    public void increaseAttractionViewCount(int no) {
        try{
            travelService.increaseAttractionViewCount(no);
        }catch(CustomerException e){
            System.err.println(e.getMessage());
        }
    }

    public TravelVO getAttractionByNo(int no) {

        TravelVO travelVO = null;
        try{
            travelVO = travelService.getAttractionByNo(no);
        }catch(CustomerException e){
            System.err.println(e.getMessage());
        }
        return travelVO;
    }
}

