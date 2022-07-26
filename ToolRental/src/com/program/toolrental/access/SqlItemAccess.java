package com.program.toolrental.access;

import com.program.toolrental.Item;
import java.sql.*;
import java.util.ArrayList;

public class SqlItemAccess implements IItemAccess{
    private String driver;

    private String url;

    private String username;

    private String password;

    public SqlItemAccess() {

    }

    public SqlItemAccess(String driver, String url, String username, String password){
        this.driver = driver;
        this.url = url;
        this.username = username;
        this.password = password;
    }

    public void setDriver(String driver) {
        this.driver = driver;
    }

    public void setUrl(String url){
        this.url = url;
    }

    public void setUsername(String username){
        this.username = username;
    }

    public void setPassword(String password){
        this.password = password;
    }

    private Connection createConnection() {
        Connection connection = null;

        try {
            Class.forName(driver);
            try {
                connection = DriverManager.getConnection(url, username, password);
            } catch (SQLException ex) {
                System.out.println("Failed to create the database connection.");

            }
        } catch (ClassNotFoundException ex) {
            // log an exception. for example:
            System.out.println("Driver not found.");
        }
        return connection;
    }


    @Override
    public ArrayList<String> getValidToolCodes() {
        ArrayList<String> allToolCodes = new ArrayList<>();
        try(Connection connection = createConnection())
        {
            String sql = "SELECT tool_code FROM toolrental.tools";

            PreparedStatement statement = connection.prepareStatement(sql);

            ResultSet result = statement.executeQuery();

            while(result.next()){
                allToolCodes.add(result.getString("tool_code"));
            }
        } catch(Exception ex){

        }

        return allToolCodes;
    }

    @Override
    public Item getItem(String toolCode) {
        Item itemInfo = new Item();
        try(Connection connection = createConnection())
        {
            String sql = "SELECT * FROM toolrental.tools WHERE tool_code = ?;";

            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, toolCode);

            ResultSet result = statement.executeQuery();

            while(result.next()){
                itemInfo.setToolCode(result.getString("tool_code"));
                itemInfo.setToolType(result.getString("tool_type"));
                itemInfo.setBrand(result.getString("brand"));
                itemInfo.setDailyRate(result.getDouble("daily_rate"));
                itemInfo.setWeekdayCharge(result.getString("weekday_charge").equals("Yes"));
                itemInfo.setWeekendCharge(result.getString("weekend_charge").equals("Yes"));
                itemInfo.setHolidayCharge(result.getString("holiday_charge").equals("Yes"));
            }
        } catch(Exception ex){
        }
        return itemInfo;
    }
}
