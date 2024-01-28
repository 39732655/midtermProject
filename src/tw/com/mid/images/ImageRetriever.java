package tw.com.mid.images;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class ImageRetriever {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Connection conn = null;
        
        try {
            conn = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;databaseName=MidtermProject;userName=watcher;password=p@ssw0rd;encrypt=false;trustServerCertificate=true");
            
            System.out.println("請輸入圖片的ID：");
            int imageId = scanner.nextInt();

            String sql = "SELECT imageContent FROM ImageData WHERE imageId = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, imageId);

            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                byte[] imageData = rs.getBytes("imageContent");
                saveImageToFile(imageData, "C:\\Action(JDBC)\\workspace\\MyMidtermProject\\imageOutput\\image02.jpg"); 
                System.out.println("圖片已成功儲存！");
            } else {
                System.out.println("未找到圖片！");
            }
            
            pstmt.close();
            conn.close();
            scanner.close();
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }

    private static void saveImageToFile(byte[] imageData, String filePath) throws IOException {
        try (OutputStream fos = new FileOutputStream(filePath)) {
            fos.write(imageData);
        }
    }
}
