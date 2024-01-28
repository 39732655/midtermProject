package tw.com.mid.project;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class QueryData {

    public static void main(String[] args) {
        Connection conn = null;
        Scanner scanner = new Scanner(System.in);

        try {
            conn = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;databaseName=MidtermProject;userName=watcher;password=p@ssw0rd;encrypt=false;trustServerCertificate=true");

            System.out.println("請輸入地區：");
            String district = scanner.nextLine();

            String sql = "SELECT * FROM material WHERE district = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, district);

            ResultSet rs = pstmt.executeQuery();

            boolean found = false; // 判斷是否找到地區資料

            while (rs.next()) {
                found = true;

                // 列印到控制台
                String time = rs.getString("time");
                String piecesOfLand = rs.getString("pieces_of_land");
                String landArea = rs.getString("Land_area");
                String buildingObjects = rs.getString("building_objects");
                String buildingArea = rs.getString("Building_area");

                System.out.println("Time: " + time + ", Pieces of Land: " + piecesOfLand + ", Land Area: " + landArea +
                        ", Building Objects: " + buildingObjects + ", Building Area: " + buildingArea);

                // 如果找到資料才創建檔案並寫入
                if (found) {
                    String filePath = System.getProperty("user.home") + "/Desktop/" + district + ".txt";
                    File file = new File(filePath);

                    try (FileWriter writer = new FileWriter(file)) {
                        writer.write("Time: " + time + ", Pieces of Land: " + piecesOfLand + ", Land Area: " + landArea +
                                ", Building Objects: " + buildingObjects + ", Building Area: " + buildingArea + "\n");
                    }
                }
            }

            if (!found) {
                System.out.println("查無資料");
            }

            rs.close();
            pstmt.close();
            conn.close();
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        } finally {
            scanner.close();
        }
    }
}