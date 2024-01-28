package tw.com.mid.project;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class DeleteProjectData {

    public static void main(String[] args) {
        Connection conn = null;
        Scanner scanner = new Scanner(System.in);

        try {
            conn = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;databaseName=MidtermProject;userName=watcher;password=p@ssw0rd;encrypt=false;trustServerCertificate=true");

            System.out.println("請輸入要刪除的關鍵字（time, pieces_of_land, Land_area, building_objects, Building_area, district）：");
            String columnName = scanner.nextLine();

            String[] validColumns = {"time", "pieces_of_land", "land_area", "building_objects", "building_area", "district"};
            boolean validInput = false;

            for (String validColumn : validColumns) {
                if (columnName.equals(validColumn)) {
                    validInput = true;
                    break;
                }
            }

            if (!validInput) {
                System.out.println("找不到關鍵字，請輸入正確的關鍵字。");
                conn.close();
              
                return;
            }

            System.out.println("請輸入要刪除的資料：");
            String columnValue = scanner.nextLine();

            String sql = "DELETE FROM material WHERE " + columnName + " = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, columnValue);

            int rowsAffected = pstmt.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("成功刪除資料！");
            } else {
                System.out.println("找不到符合條件的資料，刪除失敗。");
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