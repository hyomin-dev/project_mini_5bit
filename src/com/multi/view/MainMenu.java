package com.multi.view;

import com.multi.controller.MainController;
import com.multi.model.DTO.Travel;

import java.util.ArrayList;
import java.util.Scanner;

public class MainMenu {
    private Scanner scanner = new Scanner(System.in);
    private MainController mainController = new MainController();
    public void menu(){
        while(true){
            try{
                System.out.println("----------------------------------------");
                System.out.println("1. 전체목록");
                System.out.println("2. 권역별 관광지");
                System.out.println("3. 이름 검색");
                System.out.println("4. 조회수별 검색");
                int choice = scanner.nextInt();
                scanner.nextLine(); //버퍼 제거
                switch(choice){
                    case 1->{}
                    case 2->{}
                    case 3->{}
                    case 4->{

                    }
                    case 5->{}
                }
            }catch(Exception e){

            }
        }
    }


}
