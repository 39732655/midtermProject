package tw.com.mid.images;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class InsertImageToSQL {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("請輸入圖片的檔名：");
        String fileName = scanner.nextLine();
        String filePath = "C:\\Action(JDBC)\\workspace\\MyMidtermProject\\imageInput\\" + fileName; // 修改成使用者輸入的檔名

        try {
            File image = new File(filePath);
            if (!image.exists()) {
                System.out.println("未找到檔名！");
                return;
            }

            Connection conn = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;databaseName=MidtermProject;userName=watcher;password=p@ssw0rd;encrypt=false;trustServerCertificate=true");

            FileInputStream fis = new FileInputStream(image);

            String sql = "INSERT INTO ImageData (imageName, imageContent) VALUES (?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, image.getName());
            pstmt.setBinaryStream(2, fis, (int) image.length());

            pstmt.executeUpdate();

            pstmt.close();
            fis.close();
            conn.close();

            System.out.println("圖片成功寫入資料庫！");
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        } finally {
            scanner.close();
        }
    }
}

