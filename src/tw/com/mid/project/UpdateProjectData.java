package tw.com.mid.project;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class UpdateProjectData {

    public static void main(String[] args) {
        Connection conn = null;
        Scanner scanner = new Scanner(System.in);

        try {
            conn = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;databaseName=MidtermProject;userName=watcher;password=p@ssw0rd;encrypt=false;trustServerCertificate=true");

            System.out.println("請輸入要更新的欄位（time, pieces_of_land, land_area, building_objects, building_area, district）：");
            String columnToUpdate = scanner.nextLine();

            // 檢查輸入的欄位是否存在
            if (!isColumnExists(conn, columnToUpdate)) {
                System.out.println("找不到該欄位。");
                scanner.close();
                return;
            }

            System.out.println("請輸入要更改資料的地區名稱（district）：");
            String district = scanner.nextLine();

            // 檢查輸入的主鍵是否存在
            if (!isDistrictExists(conn, district)) {
                System.out.println("查無地區名稱。");
                scanner.close();
                return;
            }

            System.out.println("請輸入更新資訊：");
            String updatedInfo = scanner.nextLine();

            String sql = "UPDATE material SET " + columnToUpdate + " = ? WHERE district = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, updatedInfo);
            pstmt.setString(2, district);

            int rowsAffected = pstmt.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("成功更新資料！");
            } 

            pstmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            scanner.close();
        }
    }

    // 檢查欄位是否存在
    private static boolean isColumnExists(Connection conn, String columnName) throws SQLException {
        ResultSet resultSet = conn.getMetaData().getColumns(null, null, "material", columnName);
        return resultSet.next();
    }

    // 檢查主鍵是否存在
    private static boolean isDistrictExists(Connection conn, String district) throws SQLException {
        String sql = "SELECT district FROM material WHERE district = ?";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, district);
        ResultSet resultSet = pstmt.executeQuery();
        return resultSet.next();
    }
}