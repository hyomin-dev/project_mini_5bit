package com.multi.view;

import com.multi.controller.MainController;
import com.multi.model.DTO.TravelVO;

import java.util.*;

public class MainMenu {
    private Scanner scanner = new Scanner(System.in);
    private MainController mainController = new MainController();

    public void menu() {
        while (true) {
            try {
                System.out.println("\n-------------------- 관광지 정보 검색 프로그램 --------------------\n");
                System.out.println("1. 전체목록");       //전상민
                System.out.println("2. 권역별 관광지");   //김태용
                System.out.println("3. 이름 검색");      //신유경
                System.out.println("4. 조회수별 검색");   //최효민
                System.out.println("0. 프로그램 종료");   //안태희 - DB 연결
                System.out.println();
                System.out.println("검색할 번호를 선택하세요 : ");

                int choice = scanner.nextInt();
                scanner.nextLine(); //버퍼 제거
                switch (choice) {
                    case 1 -> {
                        // 전상민
                        // 전체목록 보여주기 8개씩 14개 보여주기, 최 상단에 페이지 번호, 보여줄 목록: 지역, 관광지명,
                        selectPage();
                    }
                    case 2 -> {
                        // 김태용
                        //mainController.showRegionMenu();
                        showRegionMenu();
                    }
                    case 3 -> {
                        // 신유경
                        mainController.selectByName(inputName());
                    }
                    case 4 -> {
                        // 최효민
                        mainController.selectDistrictByCount();
                    }
                    case 5 -> {
                        System.out.println("정말로 끝내시겠습니까??(y/n)");
                        if ('y' == scanner.next().toLowerCase().charAt(0)) {
                            mainController.exitProgram();
                            return;  // 프로그램 종료
                        }
                    }
                    case -1 -> { //test용 count값 랜덤 배정
                        mainController.insertRandomCount();
                    }
                    case -2 -> { //test용 count값 0
                        mainController.insertZeroCount();
                    }
                }
            } catch (NumberFormatException e) {
                System.out.println("숫자를 입력해주세요.");
            } catch (InputMismatchException e) {
                // 숫자가 아닌 값이 입력되었을 때 예외 처리
                System.out.println("유효한 숫자를 입력해 주세요.");
                scanner.nextLine();  // 잘못된 입력을 버퍼에서 제거
            } catch (Exception e) {

            }
        }
    }

    private void selectPage() { //1번 기능
        int pageSize = 8;
        int currPage = 0;
        boolean isFirst = true;

        mainController.selectPage(currPage, pageSize);
        System.out.println("--------현재 페이지: " + (currPage + 1) + "페이지------------------");
        while (true) {
            if (!isFirst) //처음이 아니라면 실행
                System.out.println("--------현재 페이지: " + (currPage + 1) + "페이지------------------");
            isFirst = false;

            System.out.println("이전 페이지 p");
            System.out.println("다음 페이지: n");
            System.out.println("종료: e");

            char key = scanner.next().trim().toLowerCase().charAt(0);
            if (key == 'e')
                break;
            else if (key == 'p') {
                System.out.println("이전페이지");
                if (currPage == 0) {
                    System.out.println("현재 페이지가 첫 페이지 입니다.");
                } else {
                    mainController.selectPage(--currPage, pageSize);
                }
            } else if (key == 'n') {
                if (mainController.selectPage(++currPage, pageSize)) {//true: 현재 페이지가 마지막 페이지임
                    --currPage;
                    System.out.println("현재 페이지가 마지막 페이지입니다.");
                }
            } else
                System.out.println("p, n, e중 하나를 입력하세요");
        }
        scanner.nextLine(); //버퍼 비우기
    }

    private void showRegionMenu() { // 2번 기능
        while (true) {
            System.out.println("\n==== 권역별 관광지 목록 ====");
            System.out.println("1. 수도권");
            System.out.println("2. 강원권");
            System.out.println("3. 충청권");
            System.out.println("4. 전라권");
            System.out.println("5. 경상권");
            System.out.println("6. 제주권");
            System.out.println("0. 이전 메뉴로 돌아가기");
            System.out.println();
            System.out.print("원하시는 권역을 선택하세요: ");

            try {
                int choice = Integer.parseInt(scanner.nextLine());

                if (choice == 0) {
                    return; // 이전 메뉴로 돌아가기
                }

                String region = getRegionByChoice(choice);

                if (region != null) {
                    mainController.showAttractionsByRegion(region);
                } else {
                    System.out.println("잘못된 선택입니다. 다시 선택해주세요.");
                }
            } catch (NumberFormatException e) {
                System.out.println("숫자를 입력해주세요.");
            }
        }
    }

    private String getRegionByChoice(int choice) { // 2번 기능
        return switch (choice) {
            case 1 -> "수도권";
            case 2 -> "강원권";
            case 3 -> "충청권";
            case 4 -> "전라권";
            case 5 -> "경상권";
            case 6 -> "제주권";
            default -> null;
        };
    }

    public void showAttractionsByRegion(List<TravelVO> attractions, String region) { //2번 기능
        int totalAttractions = attractions.size();
        int itemsPerPage = 5; // 한 페이지당 표시할 관광지 수
        int totalPages = (int) Math.ceil((double) totalAttractions / itemsPerPage);
        int currentPage = 1;

        while (true) {
            System.out.println("\n==== " + region + " 관광지 목록 (" + currentPage + "/" + totalPages + ") ====");

            // 현재 페이지에 표시할 관광지 범위 계산
            int startIdx = (currentPage - 1) * itemsPerPage;
            int endIdx = Math.min(startIdx + itemsPerPage, totalAttractions);

            // 관광지 목록 표시
            for (int i = startIdx; i < endIdx; i++) {
                TravelVO attraction = attractions.get(i);
                System.out.println("번호: " + attraction.getNo());
                System.out.println("제목: " + attraction.getTitle());
                System.out.println("설명: " + attraction.getDescription());
                System.out.println("주소: " + attraction.getAddress());
                System.out.println("전화번호: " + attraction.getPhone());
                System.out.println("조회수: " + attraction.getCount());
                System.out.println("===================================");
            }

            // 페이지 네비게이션 메뉴 표시
            System.out.println("\n1. 이전 페이지");
            System.out.println("2. 다음 페이지");
            System.out.println("0. 권역 선택으로 돌아가기");
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
                    default:
                        System.out.println("잘못된 선택입니다.");
                }
            } catch (NumberFormatException e) {
                System.out.println("숫자를 입력해주세요.");
            }
        }
    }


    private String inputName() {
        System.out.println("관광지 이름 검색 : ");

        return scanner.next();
    }

    // ↓↓↓↓↓ controller에서 호출하는 함수

    public void displayNoData() {
        System.out.println();
        System.out.println("조회된 결과가 없습니다.");
        System.out.println();
    }

    public void displayMemberList(ArrayList<TravelVO> list) {
        System.out.println("\n조회된 관광지의 정보는 다음과 같습니다.");
        System.out.println("----------------------------------------------------------");

        for (TravelVO m : list) {
            System.out.println(m);
        }

        System.out.println("----------------------------------------------------------");
    }

    public String displayselectDistrictByCount(String message, ArrayList<TravelVO> list){ //예외처리 함
       displaySuccess(message);
        System.out.println("--------지역별 우선순위--------");
        for (int i = 0; i < list.size(); i++) {
            String s = new StringBuilder()
                    .append(i + 1 + "순위 지역: ")
                    .append(list.get(i).getDistrict())
                    .append(", 조회수: ")
                    .append(list.get(i).getCount())
                    .toString();
            System.out.println(s);
        }
        while(true){
            try{
                System.out.print("해당 지역의 인기 관광지를 보고 싶으면 순위를 입력해주세요(이전 화면:-1) ");
                int num = Integer.parseInt(scanner.next().trim());
                //scanner.nextLine();
                if (num == -1) {
                    //break;
                    return null;
                }
                else if(1<=num && num<=6)
                    return list.get(num - 1).getDistrict();
            }catch(NumberFormatException e){
                System.err.println("숫자를 입력하세요.");
            }
        }
    }

    public void displayHome() {
        System.out.println();
        System.out.println("메뉴 화면으로 돌아갑니다.");
        System.out.println();
    }

    public void displayError() {
        System.out.println();
        System.out.println("조회된 결과가 없습니다.");
        System.out.println();
    }

    public void displayError(String message) {
        System.out.println();
        System.out.println("서비스 요청 처리 실패 : " + message);
        System.out.println();
    }

    public void displaySuccess(String message) {
        System.out.println();
        System.out.println("success" + message);
        System.out.println();
    }

    public void displayselectTouristAttByCount(String message, HashMap<TravelVO,Integer> map, String district) {
        displaySuccess(message);
        System.out.println("------------------------ " + district + " 지역 -----------------------");
        System.out.println("----------------------" + "인기 관광지 목록" + "-----------------------");
        List<TravelVO> keySet = new ArrayList<>(map.keySet());
        keySet.sort((o1,o2)->map.get(o1).compareTo(map.get(o2)));
        String format = "순위: %d, 조회수: %-2d, 지역: %3s, 관광지: %s\n";
        for(TravelVO travelVo : keySet){
            int rank = map.get(travelVo);
            System.out.printf(format,rank,travelVo.getCount(),district,travelVo.getTitle());
        }
    }

    public void displayselectByCount(String message, ArrayList<TravelVO> list) {
        System.out.println("success" + message);
        for (TravelVO travel : list)
            System.out.println(travel);
    }



    public void displayAll(ArrayList<TravelVO> list) {
        for (TravelVO travel : list)
            System.out.println(travel);

    }

    public void displayMessage(String message) {
        System.out.println();
        System.out.println(message);
        System.out.println();
    }


}
