package tw.com.mid.project;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class TransferFiles {

    public static void main(String[] args) {
        Connection conn = null;
        try {
            // 連接資料庫
            conn = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;databaseName=MidtermProject;userName=watcher;password=p@ssw0rd;encrypt=false;trustServerCertificate=true");

            // 抓取資料
            String urlString = "https://data.tainan.gov.tw/dataset/46c74835-6ecd-4d09-943a-50d4d6a3ce3a/resource/ba2b1a46-dd49-49a0-9f00-346c22bb7e11/download/11011.csv";
            URL url = new URL(urlString);
            URLConnection urlConn = url.openConnection();
            BufferedReader reader = new BufferedReader(new InputStreamReader(urlConn.getInputStream()));

            // 處理資料
            String line;
            int lineNumber = 0;
            while ((line = reader.readLine()) != null) {
                if(lineNumber > 0) {
                String[] data = line.split(",");
                insertIntoDatabase(conn, data); // 將資料插入資料庫
            }
                lineNumber++;
            }
            conn.close();
            } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }

private static void insertIntoDatabase(Connection conn, String[] data) {
    try {
        PreparedStatement pstmt = conn.prepareStatement("INSERT INTO material (time, district, pieces_of_land, Land_area, building_objects, Building_area) VALUES (?, ?, ?, ?, ?, ?)");

        // 根據資料的格式設定對應的欄位值
        pstmt.setString(1, data[0]); // time
        pstmt.setString(2, data[1]); // district
        pstmt.setInt(3, Integer.parseInt(data[2])); // pieces_of_land
        pstmt.setDouble(4, Double.parseDouble(data[3])); // Land_area
        pstmt.setInt(5, Integer.parseInt(data[4])); // building_objects
        pstmt.setDouble(6, Double.parseDouble(data[5])); // Building_area

        pstmt.executeUpdate();
        pstmt.close();
    } catch (SQLException e) {
        e.printStackTrace();
    }
}
}