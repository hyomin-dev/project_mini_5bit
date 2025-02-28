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
        while(true){
            try{
                System.out.println("----------------------------------------");
                System.out.println("1. 전체목록");
                System.out.println("2. 권역별 관광지");
                System.out.println("3. 이름 검색");
                System.out.println("4. 조회수별 검색");
                System.out.println("5. 프로그램 끝내기");
                System.out.println();
                System.out.println("번호 선택 : ");

                int choice = scanner.nextInt();
                scanner.nextLine(); //버퍼 제거

                switch(choice){
                    case 1->{}
                    case 2->{}
                    case 3->{
                        mainController.selectByName(inputName());
                    }
                    case 4->{
                        //mainController.selectByCount(); //먼저 전부 가져오는 것으로 테스트*/
                        mainController.selectDistrictByCount();
                    }
                    case 5->{
                        System.out.println("정말로 끝내시겠습니까??(y/n)");
                        if ('y' == scanner.next().toLowerCase().charAt(0)) {
                            mainController.exitProgram();
                            return;  // 프로그램 종료
                        }
                    }
                    case -1->{ //test용 count값 랜덤 배정
                        mainController.insertRandomCount();
                    }
                    case -2->{ //test용 count값 0
                        mainController.insertZeroCount();
                    }
                }
            }catch (InputMismatchException e) {
                // 숫자가 아닌 값이 입력되었을 때 예외 처리
                System.out.println("유효한 숫자를 입력해 주세요.");
                scanner.nextLine();  // 잘못된 입력을 버퍼에서 제거
            } catch(Exception e){

            }
        }
    }

    private void selectByCount() {

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

    public String displayselectDistrictByCount(String message, ArrayList<Travel> list) { //예외처리 아직 안함
        System.out.println("success"+message);
        System.out.println("-------------지역별 우선순위----------------");
        for(int i=0;i<list.size();i++){
            String s = new StringBuilder()
                    .append(i+1+"순위 지역: ")
                    .append(list.get(i).getDistrict())
                    .append(", 조회수: ")
                    .append(list.get(i).getCount())
                    .toString();
            System.out.println(s);
        }
        System.out.print("해당 지역의 인기 관광지를 보고 싶으면 순위를 입력해주세요(이전 화면:-1) ");
        int num = scanner.nextInt();

        if (num==-1)
            return null;
        else
            return list.get(num-1).getDistrict();
    }

    public void displayHome(){
        System.out.println("초기화면으로 돌아갑니다.");
    }

    public void displayError() {
        System.out.println("조회 결과 없음");
    }
    public void displayError(String message) {
        System.out.println("서비스 요청 처리 실패 : "+message);
    }

    public void displayselectTouristAttByCount(String message, ArrayList<Travel> list, String district) {
        System.out.println("success"+message);
        System.out.println("----------------"+district+"지역 --------------------");
        System.out.println("---------------"+"인기 관광지 목록"+"-----------------");
        System.out.println("-------------"+"TOP 5"+"---------------------------");
        for(int i=0;i<list.size();i++){
            String s = new StringBuilder()
                    .append(i+1+". 관광지명: ")
                    .append(list.get(i).getTitle())
                    .append(", 주소: ")
                    .append(list.get(i).getAddress())
                    .append(", 조회수: ")
                    .append(list.get(i).getCount())
                    .toString();
            System.out.println(s);
        }
    }

    public void displayselectByCount(String message, ArrayList<Travel> list) {
        System.out.println("success"+message);
        for(Travel travel:list)
            System.out.println(travel);
    }

    public void displaySuccess(String message) {
        System.out.println("success"+message);
    }
}
