package tw.com.mid.project;

import java.util.Scanner;

import tw.com.mid.images.ImageRetriever;
import tw.com.mid.images.InsertImageToSQL;

public class ActionProject {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        do {
            System.out.println("請選擇功能：");
            System.out.println("1. 新增資料");
            System.out.println("2. 刪除資料");
            System.out.println("3. 修改資料");
            System.out.println("4. 查詢資料");
            System.out.println("5. 將資料轉入進SQL");
            System.out.println("6. 圖片寫入進SQL");
            System.out.println("7. 圖片轉出到資料庫");
            System.out.println("8. 離開");

            if (scanner.hasNextInt()) {
                int choice = scanner.nextInt();
                scanner.nextLine(); // 消耗換行符號

                switch (choice) {
                    case 1:
                    	InsertIntoDatabase.main(args);
                        break;
                    case 2:
                        DeleteProjectData.main(args);
                        break;
                    case 3:
                    	UpdateProjectData.main(args);
                        break;
                    case 4:
                    	 QueryData.main(args);
                    	break;
                    case 5:
                    	TransferFiles.main(args); 
                        break;
                    case 6:
                        InsertImageToSQL.main(args);
                        break;
                    case 7:
                    	ImageRetriever.main(args);
                    	break;
                    case 8:
                        System.out.println("程式結束。");
                        scanner.close();
                        return;
                    default:
                        System.out.println("請輸入有效的選擇。");
                        break;
                }
                scanner.close();
                return;
            } else {
                scanner.nextLine(); // 消耗無效的輸入
                System.out.println("請輸入有效的選擇。");
            }
        } while (true);
    }
}
