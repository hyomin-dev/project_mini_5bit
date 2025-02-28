package com.travelapp;

import com.travelapp.model.TravelVO;
import com.travelapp.service.TravelService;
import java.util.List;
import java.util.Scanner;

public class TravelApp {
    private static final int PAGE_SIZE = 2; // 페이지당 보여줄 항목 수
    private TravelService travelService;

    public TravelApp() {
        this.travelService = new TravelService();
    }

    public void start() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("1. 여행지 목록 보기");
            System.out.println("2. 종료");
            System.out.print("선택: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // 버퍼 비우기

            if (choice == 1) {
                System.out.print("페이지 번호를 입력하세요: ");
                int page = scanner.nextInt();
                scanner.nextLine(); // 버퍼 비우기

                List<TravelVO> travels = travelService.getTravelsByPage(page, PAGE_SIZE);
                for (TravelVO travel : travels) {
                    System.out.println(travel);
                }
            } else if (choice == 2) {
                break;
            } else {
                System.out.println("잘못된 선택입니다.");
            }
        }
        scanner.close();
    }

    public static void main(String[] args) {
        TravelApp app = new TravelApp();
        app.start();
    }
}