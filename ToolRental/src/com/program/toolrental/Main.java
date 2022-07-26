package com.program.toolrental;

import com.program.toolrental.access.SqlItemAccess;

import java.text.Format;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Main {
    static String driver = "com.mysql.cj.jdbc.Driver";

    static String url = "jdbc:mysql://localhost:3306/mysql";

    static String username = "root";

    static String password= "ToolRental!2022";

    public static void main(String[] args) {

        SqlItemAccess itemAccess = new SqlItemAccess(driver, url, username, password);
        UserInput userInput = new UserInput(itemAccess);

        String toolCode = userInput.getToolCode();
        Item item = itemAccess.getItem(toolCode);

        Date checkoutDate = userInput.checkoutDate();
        item.setCheckoutDate(checkoutDate);

        int rentalDays = userInput.getRentalDays();
        item.setRentalDays(rentalDays);

        int discount = userInput.getDiscount();
        item.setDiscount(discount);

        printContract(item);
    }

    public static void printContract(Item item) {
        NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance();
        Format dateFormat = new SimpleDateFormat("MM/dd/YY");

        System.out.println("Tool Code: " + item.getToolCode());
        System.out.println("Tool Type: " + item.getToolType());
        System.out.println("Tool Brand: " + item.getBrand());
        System.out.println("Rental Days: " + item.getRentalDays());
        System.out.println("Checkout Date: " + dateFormat.format(item.getCheckoutDate()));
        System.out.println("Due Date: " + dateFormat.format(item.getDueDate()));
        System.out.println("Daily rental charge: " + item.getDailyRate());
        System.out.println("Charge days: " + item.getChargeDays());
        System.out.println("Pre-discount charge: " + currencyFormatter.format(item.getInitialCost()));
        System.out.println("Discount Percent: " + item.getDiscount() + "%");
        System.out.println("Discount Amount: " + currencyFormatter.format(item.getDiscountAmount()));
        System.out.println("Final charge: " + currencyFormatter.format(item.getRentalCost()));
    }
}
