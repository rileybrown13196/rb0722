package com.program.unittests;

import com.program.toolrental.Item;
import com.program.toolrental.UserInput;
import com.program.toolrental.access.SqlItemAccess;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.text.Format;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UnitTests {
    static String driver = "com.mysql.cj.jdbc.Driver";

    static String url = "jdbc:mysql://localhost:3306/mysql";

    static String username = "root";

    static String password= "ToolRental!2022";

    /*
    Test1 verifies that a proper error is thrown when a user enters a discount greater than 0-100.
     */
    @Test
    public void test1(){
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        String simulatedUserInput = "JAKR" + System.getProperty("line.separator")
                + "9/13/15" + System.getProperty("line.separator")
                + "5" + System.getProperty("line.separator")
                + "101" + System.getProperty("line.separator")
                + "5" + System.getProperty("line.separator");
        System.setIn(new ByteArrayInputStream(simulatedUserInput.getBytes()));

        SqlItemAccess itemAccess = new SqlItemAccess(driver, url, username, password);
        UserInput userInput = new UserInput(itemAccess);

        String toolCode = userInput.getToolCode();
        Item item = itemAccess.getItem(toolCode);

        Date checkoutDate = userInput.checkoutDate();
        item.setCheckoutDate(checkoutDate);

        int rentalDays = userInput.getRentalDays();
        item.setRentalDays(rentalDays);

        userInput.getDiscount();
        String expectedOutput = "The discount must be a number in the range of 0-100. What discount would you like to apply to the rental?";
        boolean containsError = outContent.toString().contains(expectedOutput);
        Assertions.assertTrue(containsError);

    }

    /*
    Test2 verifies the following:
    Main functionality checks:
    1. Is the weekend and Holiday charge being calculated correctly for ladders.
    2. Is the discount amount being calculated correctly, rounding the discount amount up half cents.
    Output checks:
    1. The proper info for the contract is generated
    2. The checkout date and due date are displayed correctly, and in the correct format.
    3. Ladders have no charge on Holidays, but there is a weekday charge and weekend charge.
    4. The charge amount before discounts should be displayed correctly and in the correct format.
    5. The discount amount should be displayed correctly and in the correct format.
    6. The final charge amount should be displayed correctly, showing initialCost - discountAmount.
     */
    @Test
    public void test2(){
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        String simulatedUserInput = "LADW" + System.getProperty("line.separator")
                + "7/2/20" + System.getProperty("line.separator")
                + "3" + System.getProperty("line.separator")
                + "10" + System.getProperty("line.separator");
        System.setIn(new ByteArrayInputStream(simulatedUserInput.getBytes()));

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

        NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance();
        Format dateFormat = new SimpleDateFormat("MM/dd/YY");

        String expectedCheckoutDate = "07/02/20";
        int expectedChargedDays = 2;
        String expectedDueDate = "07/05/20";
        String expectedPreDiscountCharge = "$3.98";
        String expectedDiscountAmount = "$0.40";
        String expectedDiscountPercent = "10%";
        String expectedFinalCharge = "$3.58";

        String actualCheckoutDate = dateFormat.format(item.getCheckoutDate());
        int actualChargedDays = item.getChargeDays();
        String actualDueDate = dateFormat.format(item.getDueDate());
        String actualPreDiscountCharge = currencyFormatter.format(item.getInitialCost());
        String actualDiscountAmount = currencyFormatter.format(item.getDiscountAmount());
        String actualDiscountPercent = item.getDiscount() + "%";
        String actualFinalCharge = currencyFormatter.format(item.getRentalCost());

        Assertions.assertEquals(expectedCheckoutDate,actualCheckoutDate);
        Assertions.assertEquals(expectedChargedDays,actualChargedDays);
        Assertions.assertEquals(expectedDueDate, actualDueDate);
        Assertions.assertEquals(expectedPreDiscountCharge, actualPreDiscountCharge);
        Assertions.assertEquals(expectedDiscountAmount, actualDiscountAmount);
        Assertions.assertEquals(expectedDiscountPercent, actualDiscountPercent);
        Assertions.assertEquals(expectedFinalCharge,actualFinalCharge);

    }

    /*
    Test3 verifies the following:
    Main functionality checks:
    1. Is the weekend and Holiday charge being calculated correctly for chainsaws.
    2. Is the discount amount being calculated correctly, rounding the discount amount in cents correctly.
    3. Specifically checking 4th of July logic.
    Output checks.
    1. The proper info for the contract is generated.
    2. The checkout date and due date are displayed correctly, and in the correct format.
    3. Chainsaws have a weekday and holiday charge, but now charge for weekends.
    4. The charge amount before discounts should be displayed correctly and in the correct format.
    5. The discount amount should be displayed correctly and in the correct format.
    6. The final charge amount should be displayed correctly, showing initialCost - discountAmount.
     */
    @Test
    public void test3(){
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        String simulatedUserInput = "CHNS" + System.getProperty("line.separator")
                + "7/2/15" + System.getProperty("line.separator")
                + "5" + System.getProperty("line.separator")
                + "25" + System.getProperty("line.separator");
        System.setIn(new ByteArrayInputStream(simulatedUserInput.getBytes()));

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

        NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance();
        Format dateFormat = new SimpleDateFormat("MM/dd/YY");

        String expectedCheckoutDate = "07/02/15";
        int expectedChargedDays = 3;
        String expectedDueDate = "07/07/15";
        String expectedPreDiscountCharge = "$4.47";
        String expectedDiscountAmount = "$1.12";
        String expectedDiscountPercent = "25%";
        String expectedFinalCharge = "$3.35";

        String actualCheckoutDate = dateFormat.format(item.getCheckoutDate());
        int actualChargedDays = item.getChargeDays();
        String actualDueDate = dateFormat.format(item.getDueDate());
        String actualPreDiscountCharge = currencyFormatter.format(item.getInitialCost());
        String actualDiscountAmount = currencyFormatter.format(item.getDiscountAmount());
        String actualDiscountPercent = item.getDiscount() + "%";
        String actualFinalCharge = currencyFormatter.format(item.getRentalCost());

        Assertions.assertEquals(expectedCheckoutDate,actualCheckoutDate);
        Assertions.assertEquals(expectedChargedDays,actualChargedDays);
        Assertions.assertEquals(expectedDueDate, actualDueDate);
        Assertions.assertEquals(expectedPreDiscountCharge, actualPreDiscountCharge);
        Assertions.assertEquals(expectedDiscountAmount, actualDiscountAmount);
        Assertions.assertEquals(expectedDiscountPercent, actualDiscountPercent);
        Assertions.assertEquals(expectedFinalCharge,actualFinalCharge);
    }

    /*
    Test4 verifies the following:
    Main functionality checks:
    1. Specifically checking laborDay functionality.
    2. Is the discount amount being calculated correctly, rounding the discount amount up half cents.
    3. Is the Brand displaying specifically DeWalt.
    Output checks.
    1. The proper info for the contract is generated
    2. The checkout date and due date are displayed correctly, and in the correct format.
    3. Jack hammers have no weekend or holiday charges.
    4. The charge amount before discounts should be displayed correctly and in the correct format.
    5. The discount amount should be displayed correctly and in the correct format.
    6. The final charge amount should be displayed correctly, initialCost - discountAmount.
     */
    @Test
    public void test4(){
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        String simulatedUserInput = "JAKD" + System.getProperty("line.separator")
                + "9/3/15" + System.getProperty("line.separator")
                + "6" + System.getProperty("line.separator")
                + "0" + System.getProperty("line.separator");
        System.setIn(new ByteArrayInputStream(simulatedUserInput.getBytes()));

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

        NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance();
        Format dateFormat = new SimpleDateFormat("MM/dd/YY");

        String expectedBrand = "DeWalt";
        String expectedCheckoutDate = "09/03/15";
        int expectedChargedDays = 3;
        String expectedDueDate = "09/09/15";
        String expectedPreDiscountCharge = "$8.97";
        String expectedDiscountAmount = "$0.00";
        String expectedDiscountPercent = "0%";
        String expectedFinalCharge = "$8.97";

        String actualBrand = item.getBrand();
        String actualCheckoutDate = dateFormat.format(item.getCheckoutDate());
        int actualChargedDays = item.getChargeDays();
        String actualDueDate = dateFormat.format(item.getDueDate());
        String actualPreDiscountCharge = currencyFormatter.format(item.getInitialCost());
        String actualDiscountAmount = currencyFormatter.format(item.getDiscountAmount());
        String actualDiscountPercent = item.getDiscount() + "%";
        String actualFinalCharge = currencyFormatter.format(item.getRentalCost());

        Assertions.assertEquals(expectedBrand, actualBrand);
        Assertions.assertEquals(expectedCheckoutDate,actualCheckoutDate);
        Assertions.assertEquals(expectedChargedDays,actualChargedDays);
        Assertions.assertEquals(expectedDueDate, actualDueDate);
        Assertions.assertEquals(expectedPreDiscountCharge, actualPreDiscountCharge);
        Assertions.assertEquals(expectedDiscountAmount, actualDiscountAmount);
        Assertions.assertEquals(expectedDiscountPercent, actualDiscountPercent);
        Assertions.assertEquals(expectedFinalCharge,actualFinalCharge);
    }

    /*
    Test5 verifies the following:
    Main functionality checks:
    1. Are the weekend and Holiday charge being calculated correctly for jackhammers.
    2. Is the Brand displaying specifically Ridgid.
    3. Verifying charge date calculation is happening on the NEXT day, including due date.
    Output checks.
    1. The proper info for the contract is generated
    2. The checkout date and due date are displayed correctly, and in the correct format mm/dd/yy.
    3. Jackhammers have no holiday or weekend charges, but there is a weekday charge.
    4. The charge amount before discounts should be displayed correctly and in the correct format.
    5. The discount amount should be displayed correctly and in the correct format.
    6. The final charge amount should be displayed correctly, showing initialCost - discountAmount.
     */
    @Test
    public void test5(){
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        String simulatedUserInput = "JAKR" + System.getProperty("line.separator")
                + "7/2/15" + System.getProperty("line.separator")
                + "9" + System.getProperty("line.separator")
                + "0" + System.getProperty("line.separator");
        System.setIn(new ByteArrayInputStream(simulatedUserInput.getBytes()));

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

        NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance();
        Format dateFormat = new SimpleDateFormat("MM/dd/YY");

        String expectedBrand = "Ridgid";
        String expectedCheckoutDate = "07/02/15";
        int expectedChargedDays = 5;
        String expectedDueDate = "07/11/15";
        String expectedPreDiscountCharge = "$14.95";
        String expectedDiscountAmount = "$0.00";
        String expectedDiscountPercent = "0%";
        String expectedFinalCharge = "$14.95";

        String actualBrand = item.getBrand();
        String actualCheckoutDate = dateFormat.format(item.getCheckoutDate());
        int actualChargedDays = item.getChargeDays();
        String actualDueDate = dateFormat.format(item.getDueDate());
        String actualPreDiscountCharge = currencyFormatter.format(item.getInitialCost());
        String actualDiscountAmount = currencyFormatter.format(item.getDiscountAmount());
        String actualDiscountPercent = item.getDiscount() + "%";
        String actualFinalCharge = currencyFormatter.format(item.getRentalCost());

        Assertions.assertEquals(expectedBrand, actualBrand);
        Assertions.assertEquals(expectedCheckoutDate,actualCheckoutDate);
        Assertions.assertEquals(expectedChargedDays,actualChargedDays);
        Assertions.assertEquals(expectedDueDate, actualDueDate);
        Assertions.assertEquals(expectedPreDiscountCharge, actualPreDiscountCharge);
        Assertions.assertEquals(expectedDiscountAmount, actualDiscountAmount);
        Assertions.assertEquals(expectedDiscountPercent, actualDiscountPercent);
        Assertions.assertEquals(expectedFinalCharge,actualFinalCharge);
    }

    /*
   Test6 verifies the following:
   Main functionality checks:
   1. Are the weekend and Holiday charge being calculated correctly for jackhammers.
   2. Is the Brand displaying specifically Ridgid.
   3. Checking functionality of discount and rounding up of Total Charge and discount amount.
   Output checks.
   1. The proper info for the contract is generated
   2. The checkout date and due date are displayed correctly, and in the correct format mm/dd/yy.
   3. Jackhammers have no holiday or weekend charges, but there is a weekday charge.
   4. The charge amount before discounts should be displayed correctly and in the correct format.
   5. The discount amount should be displayed correctly and in the correct format.
   6. The final charge amount should be displayed correctly, showing initialCost - discountAmount.
    */
    @Test
    public void test6(){
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        String simulatedUserInput = "JAKR" + System.getProperty("line.separator")
                + "7/2/20" + System.getProperty("line.separator")
                + "4" + System.getProperty("line.separator")
                + "50" + System.getProperty("line.separator");
        System.setIn(new ByteArrayInputStream(simulatedUserInput.getBytes()));

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

        NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance();
        Format dateFormat = new SimpleDateFormat("MM/dd/YY");

        String expectedBrand = "Ridgid";
        String expectedCheckoutDate = "07/02/20";
        int expectedChargedDays = 1;
        String expectedDueDate = "07/06/20";
        String expectedPreDiscountCharge = "$2.99";
        String expectedDiscountAmount = "$1.50";
        String expectedDiscountPercent = "50%";
        String expectedFinalCharge = "$1.49";

        String actualBrand = item.getBrand();
        String actualCheckoutDate = dateFormat.format(item.getCheckoutDate());
        int actualChargedDays = item.getChargeDays();
        String actualDueDate = dateFormat.format(item.getDueDate());
        String actualPreDiscountCharge = currencyFormatter.format(item.getInitialCost());
        String actualDiscountAmount = currencyFormatter.format(item.getDiscountAmount());
        String actualDiscountPercent = item.getDiscount() + "%";
        String actualFinalCharge = currencyFormatter.format(item.getRentalCost());

        Assertions.assertEquals(expectedBrand, actualBrand);
        Assertions.assertEquals(expectedCheckoutDate,actualCheckoutDate);
        Assertions.assertEquals(expectedChargedDays,actualChargedDays);
        Assertions.assertEquals(expectedDueDate, actualDueDate);
        Assertions.assertEquals(expectedPreDiscountCharge, actualPreDiscountCharge);
        Assertions.assertEquals(expectedDiscountAmount, actualDiscountAmount);
        Assertions.assertEquals(expectedDiscountPercent, actualDiscountPercent);
        Assertions.assertEquals(expectedFinalCharge,actualFinalCharge);
    }

    /*
   verifyPrintContractInformation verifies the following:
   Additional Test to verify all contract info is provided with the appropriate responses.
    */
    @Test
    public void verifyPrintContractInformation(){
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        String simulatedUserInput = "JAKR" + System.getProperty("line.separator")
                + "7/2/20" + System.getProperty("line.separator")
                + "5" + System.getProperty("line.separator")
                + "55" + System.getProperty("line.separator");
        System.setIn(new ByteArrayInputStream(simulatedUserInput.getBytes()));

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

        NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance();
        Format dateFormat = new SimpleDateFormat("MM/dd/YY");

        String expectedToolCode = "JAKR";
        String expectedToolType = "Jackhammer";
        String expectedBrand = "Ridgid";
        int expectedRentalDays = 5;
        String expectedCheckoutDate = "07/02/20";
        String expectedDueDate = "07/07/20";
        Double expectedDailyRate = 2.99;
        int expectedChargedDays = 2;
        String expectedPreDiscountCharge = "$5.98";
        String expectedDiscountPercent = "55%";
        String expectedDiscountAmount = "$3.29";
        String expectedFinalCharge = "$2.69";

        String actualToolCode = item.getToolCode();
        String actualToolType = item.getToolType();
        String actualBrand = item.getBrand();
        int actualRentalDays = item.getRentalDays();
        String actualCheckoutDate = dateFormat.format(item.getCheckoutDate());
        String actualDueDate = dateFormat.format(item.getDueDate());
        Double actualDailyRate = item.getDailyRate();
        int actualChargedDays = item.getChargeDays();
        String actualPreDiscountCharge = currencyFormatter.format(item.getInitialCost());
        String actualDiscountAmount = currencyFormatter.format(item.getDiscountAmount());
        String actualDiscountPercent = item.getDiscount() + "%";
        String actualFinalCharge = currencyFormatter.format(item.getRentalCost());

        Assertions.assertEquals(expectedToolCode, actualToolCode);
        Assertions.assertEquals(expectedToolType, actualToolType);
        Assertions.assertEquals(expectedBrand, actualBrand);
        Assertions.assertEquals(expectedRentalDays, actualRentalDays);
        Assertions.assertEquals(expectedCheckoutDate,actualCheckoutDate);
        Assertions.assertEquals(expectedDueDate, actualDueDate);
        Assertions.assertEquals(expectedDailyRate, actualDailyRate);
        Assertions.assertEquals(expectedChargedDays,actualChargedDays);
        Assertions.assertEquals(expectedPreDiscountCharge, actualPreDiscountCharge);
        Assertions.assertEquals(expectedDiscountAmount, actualDiscountAmount);
        Assertions.assertEquals(expectedDiscountPercent, actualDiscountPercent);
        Assertions.assertEquals(expectedFinalCharge,actualFinalCharge);
    }

}
