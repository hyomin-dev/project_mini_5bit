package com.multi.view;


import com.multi.controller.MainController;
import com.multi.model.DTO.Travel;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class MainMenu {
    private Scanner scanner = new Scanner(System.in);
    private MainController mainController = new MainController();
    public void menu(){

        do {
            try{
                System.out.println("----------------------------------------");
                System.out.println("1. 전체목록");
                System.out.println("2. 권역별 관광지");
                System.out.println("3. 이름 검색"); //
                System.out.println("4. 조회수별 검색");
                System.out.println("5. 프로그램 끝내기");
                System.out.println();
                System.out.println("번호 선택 : ");

                int choice = scanner.nextInt();
                //scanner.nextLine(); //버퍼 제거

                switch(choice){
                    case 1:

                        break;
                    case 2:

                        break;
                    case 3:
                        mainController.selectByName(inputName());
                        break;
                    case 4:

                        break;
                    case 5:
                        System.out.println("정말로 끝내시겠습니까??(y/n)");
                        if ('y' == scanner.next().toLowerCase().charAt(0)) {
                            mainController.exitProgram();
                            return;  // 프로그램 종료
                        }
                        break;
                    default:
                        System.out.println("번호를 잘못입력하였습니다.");
                        break;
//                    case 1->{}
//                    case 2->{}
//                    case 3->{
//                        mainController.selectByName(inputName());
//                    }
//                    case 4->{
//
//                    }
//                    case 5->{
//                        System.out.println("정말로 끝내시겠습니까??(y/n)");
//                        if ('y' == scanner.next().toLowerCase().charAt(0)) {
//                            mainController.exitProgram();
//                            return;  // 프로그램 종료
//                        }
//                    }
//                    default -> {
//                        System.out.println("번호를 잘못 입력하였습니다.");
//                    }
                }
            } catch (InputMismatchException e) {
                // 숫자가 아닌 값이 입력되었을 때 예외 처리
                System.out.println("유효한 숫자를 입력해 주세요.");
                scanner.nextLine();  // 잘못된 입력을 버퍼에서 제거
            }
        } while (true);
    }

    private String inputName() {
        System.out.println("관광지 이름 검색 : ");

        return scanner.next();
    }


    public void displayNoData() {
        System.out.println("조회된 결과가 없습니다.");
    }

    public void displayMemberList(ArrayList<Travel> list) {
        System.out.println("\n조회된 관광지의 정보는 다음과 같습니다.");
        System.out.println("----------------------------------------------------------");

        for(Travel m: list) {
            System.out.println(m);
        }

        System.out.println("----------------------------------------------------------");
    }

    public void displayError(String message) {
        System.out.println("서비스 요청 처리 실패 : "+message);
    }
}
