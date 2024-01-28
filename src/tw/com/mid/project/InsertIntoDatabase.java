package tw.com.mid.project;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class InsertIntoDatabase {

    public static void main(String[] args) {
        Connection conn = null;
        Scanner scanner = new Scanner(System.in);

        try {
            conn = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;databaseName=MidtermProject;userName=watcher;password=p@ssw0rd;encrypt=false;trustServerCertificate=true");

            System.out.println("請輸入要新增的資料：");
            System.out.print("時間 (time): ");
            String time = scanner.nextLine();
            System.out.print("地區 (district): ");
            String district = scanner.nextLine();
            System.out.print("地塊數量 (pieces_of_land): ");
            int piecesOfLand = scanner.nextInt();
            System.out.print("土地面積 (land_area): ");
            double landArea = scanner.nextDouble();
            System.out.print("建物物件數量 (building_objects): ");
            int buildingObjects = scanner.nextInt();
            System.out.print("建物面積 (building_area): ");
            double buildingArea = scanner.nextDouble();

            String sql = "INSERT INTO material (time, district, pieces_of_land, Land_area, building_objects, Building_area) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, time);
            pstmt.setString(2, district);
            pstmt.setInt(3, piecesOfLand);
            pstmt.setDouble(4, landArea);
            pstmt.setInt(5, buildingObjects);
            pstmt.setDouble(6, buildingArea);

            int rowsAffected = pstmt.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("成功新增資料！");
            } else {
                System.out.println("新增資料失敗。");
            }

            pstmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            scanner.close();
        }
    }
}
