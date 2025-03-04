package com.multi.controller;


import com.multi.model.DTO.Travel;
import com.multi.service.MainService;
import com.multi.view.MainMenu;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class MainController {
    private final MainService mainService = new MainService();
    private final Scanner scanner = new Scanner(System.in); // 김태용님

    public void selectByCount() {
        ArrayList<Travel> list = null;
        MainMenu mainMenu = new MainMenu();
        list = mainService.selectByCount();
        if (!list.isEmpty())
            mainMenu.displayselectByCount("selectByCount OK!!", list);
        else
            mainMenu.displayError();
    }

    public void selectByName(String name) {
        MainMenu mainMenu = new MainMenu();
        ArrayList<Travel> list = mainService.selectByName(name);

        try {
            if (list.isEmpty()) {
                mainMenu.displayNoData();
            } else
                mainMenu.displayMemberList(list);
        } catch (Exception e) {
            e.printStackTrace();
            mainMenu.displayError("특정 관광지 선택 실패");
        }

        if (!list.isEmpty())
            updateTravelCount(list);


    }

    private void updateTravelCount(ArrayList<Travel> list) {
        MainMenu mainMenu = new MainMenu();
        int result = 0;
        result = mainService.updateTravelCount(list);
        if (result > 0)
            mainMenu.displaySuccess("updateTravelCount OK!");
        else
            mainMenu.displayError("updateTravelCount fail");


    }


    public void exitProgram() {
        mainService.exitProgram();
    }

    public void selectDistrictByCount() {
        MainMenu mainMenu = new MainMenu();
        ArrayList<Travel> list = null;
        String district = null;
        list = mainService.selectDistrictByCount();
        if (!list.isEmpty())
            district = mainMenu.displayselectDistrictByCount("selectDistrictByCount OK!!", list);
        else {
            mainMenu.displayError();
            return;
        }

        if (district == null) {
            mainMenu.displayHome();
            return;
        } else
            list = mainService.selectTouristAttByCount(district);

        if (!list.isEmpty())
            mainMenu.displayselectTouristAttByCount("selectTouristAttByCount OK!!", list, district);
        else
            mainMenu.displayError();

    }

    public void insertRandomCount() {
        MainMenu mainMenu = new MainMenu();
        int result = 0;
        result = mainService.insertRandomCount();
        if (result > 0)
            mainMenu.displaySuccess("insertRandomCount");
        else
            mainMenu.displayError();

    }

    public void insertZeroCount() {
        MainMenu mainMenu = new MainMenu();
        int result = 0;
        result = mainService.insertZeroCount();
        if (result > 0)
            mainMenu.displaySuccess("insertZeroCount");
        else
            mainMenu.displayError();
    }

    //김태용님

    // 권역별 관광지 목록 보여주기 --> view로 옮김
   /* public void showRegionMenu() {
        while (true) {
            System.out.println("\n==== 권역별 관광지 목록 ====");
            System.out.println("1. 수도권");
            System.out.println("2. 강원권");
            System.out.println("3. 충청권");
            System.out.println("4. 전라권");
            System.out.println("5. 경상권");
            System.out.println("6. 제주권");
            System.out.println("0. 이전 메뉴로 돌아가기");
            System.out.print("원하시는 권역을 선택하세요: ");

            try {
                int choice = Integer.parseInt(scanner.nextLine());

                if (choice == 0) {
                    return; // 이전 메뉴로 돌아가기
                }

                String region = getRegionByChoice(choice);

                if (region != null) {
                    showAttractionsByRegion(region);
                } else {
                    System.out.println("잘못된 선택입니다. 다시 선택해주세요.");
                }
            } catch (NumberFormatException e) {
                System.out.println("숫자를 입력해주세요.");
            }
        }
    }*/

    // 선택한 번호에 해당하는 권역 반환 --> view로 옮김
   /* private String getRegionByChoice(int choice) {
        return switch (choice) {
            case 1 -> "수도권";
            case 2 -> "강원권";
            case 3 -> "충청권";
            case 4 -> "전라권";
            case 5 -> "경상권";
            case 6 -> "제주권";
            default -> null;
        };
    }*/

    // 지역별 관광지 목록 보여주기
    public void showAttractionsByRegion(String region) {
        List<Travel> attractions = mainService.getAttractionsByRegion(region);

        if (attractions.isEmpty()) {
            System.out.println("해당 권역에는 관광지가 없습니다.");
            return;
        }

        int totalAttractions = attractions.size();
        int itemsPerPage = 5; // 한 페이지당 표시할 관광지 수
        int totalPages = (int) Math.ceil((double) totalAttractions / itemsPerPage);
        int currentPage = 1;

        while (true) {
            System.out.println("\n----------------- " + region + " 관광지 목록 (" + currentPage + "/" + totalPages + ") -----------------");

            // 현재 페이지에 표시할 관광지 범위 계산
            int startIdx = (currentPage - 1) * itemsPerPage;
            int endIdx = Math.min(startIdx + itemsPerPage, totalAttractions);

            // 관광지 목록 표시
            for (int i = startIdx; i < endIdx; i++) {
                Travel attraction = attractions.get(i);
                System.out.println("번호: " + attraction.getNo());
                System.out.println("제목: " + attraction.getTitle());
                System.out.println("설명: " + attraction.getDescription());
                System.out.println("주소: " + attraction.getAddress());
                System.out.println("전화번호: " + attraction.getPhone());
                System.out.println("조회수: " + attraction.getCount());
                System.out.println("--------------------------------------------------------");
            }

            // 페이지 네비게이션 메뉴 표시
//            System.out.println("\n1. 이전 페이지");
//            System.out.println("2. 다음 페이지");
//            //System.out.println("3. 관광지 상세 보기");
//            System.out.println("0. 권역 선택으로 돌아가기");
//            System.out.print("선택: ");
            System.out.println("\n이전 페이지 : 1");
            System.out.println("다음 페이지 : 2");
            System.out.println("권역 선택 화면으로 돌아가기 : 0");
            System.out.print("선택: ");

            try {
                int choice = Integer.parseInt(scanner.nextLine());

                switch (choice) {
                    case 0:
                        return;
                    case 1:
                        if (currentPage > 1) {
                            currentPage--;
                        } else {
                            System.out.println("첫 페이지입니다.");
                        }
                        break;
                    case 2:
                        if (currentPage < totalPages) {
                            currentPage++;
                        } else {
                            System.out.println("마지막 페이지입니다.");
                        }
                        break;
                    case 3:
                        //showAttractionDetail(attractions, startIdx, endIdx);
                        break;
                    default:
                        System.out.println("잘못된 선택입니다.");
                }
            } catch (NumberFormatException e) {
                System.out.println("숫자를 입력해주세요.");
            }
        }
    }

    // selectAttractionByNo, updateViewCount query없어서 상세보기 제거
    /*private void showAttractionDetail(List<Travel> attractions, int startIdx, int endIdx) {
        System.out.print("상세 보기할 관광지 번호를 입력하세요: ");
        try {
            int attractionNo = Integer.parseInt(scanner.nextLine());

            Travel selectedAttraction = null;
            for (int i = startIdx; i < endIdx; i++) {
                Travel attraction = attractions.get(i);
                if (attraction.getNo() == attractionNo) {
                    selectedAttraction = attraction;
                    break;
                }
            }

            if (selectedAttraction != null) {
                // 관광지 상세 정보 표시 및 조회수 증가 처리
                mainService.increaseAttractionViewCount(selectedAttraction.getNo());
                Travel updatedAttraction = mainService.getAttractionByNo(selectedAttraction.getNo());

                System.out.println("\n==== 관광지 상세 정보 ====");
                System.out.println("번호: " + updatedAttraction.getNo());
                System.out.println("지역: " + updatedAttraction.getDistrict());
                System.out.println("제목: " + updatedAttraction.getTitle());
                System.out.println("설명: " + updatedAttraction.getDescription());
                System.out.println("주소: " + updatedAttraction.getAddress());
                System.out.println("전화번호: " + updatedAttraction.getPhone());
                System.out.println("조회수: " + updatedAttraction.getCount());
                System.out.println("===================================");

                System.out.println("\n아무 키나 눌러 계속하세요...");
                scanner.nextLine();
            } else {
                System.out.println("현재 페이지에 해당 번호의 관광지가 없습니다.");
            }
        } catch (NumberFormatException e) {
            System.out.println("올바른 번호를 입력해주세요.");
        }
    }*/

    public boolean selectPage(int currPage, int pageSize) {
        MainMenu mainMenu = new MainMenu();
        ArrayList<Travel> list = null;
        list = mainService.selectPage(currPage, pageSize);

        if (!list.isEmpty()) {
            mainMenu.displayAll(list);
            return false;
        } else {
            mainMenu.displayError();
            return true;

        }
    }
}

