package com.program.toolrental;

import com.program.toolrental.access.IItemAccess;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class UserInput {
    private IItemAccess itemAccess;
    private Scanner scanner;

    private static final String ENTER_TOOL_CODE = "Enter the tool code for the tool being rented.";
    private static final String INVALID_TOOL_CODE = "That doesn't seem to be a valid tool code. Please enter the tool code for the tool being rented.";
    private static final String ENTER_DAYS_RENTED = "How many days would you like to rent the tool?";
    private static final String INVALID_NUMBER_RENTAL = "You must enter a number. How many days would you like to rent the tool?";
    private static final String ZERO_NUMBER_RENTAL = "I'm sorry. You have to checkout the tool for at least 1 day. How many days would you like to rent the tool?";
    private static final String ENTER_DISCOUNT = "What discount would you like to apply to the rental?";
    private static final String INVALID_DISCOUNT_RANGE = "The discount must be a number in the range of 0-100. What discount would you like to apply to the rental?";
    private static final String ENTER_CHECKOUT_DATE = "Please enter a checkout date in the format MM/dd/YY.";
    private static final String INVALID_CHECKOUT_DATE = "That does not seem to be a valid checkout date. Please enter a checkout date in the format MM/dd/YY.";
    public UserInput(IItemAccess itemAccess) {
        this.itemAccess = itemAccess;

        this.scanner = new Scanner(System.in);
    }

    public String getToolCode() {
        System.out.println(ENTER_TOOL_CODE);

        String response = scanner.nextLine();

        while (!itemAccess.getValidToolCodes().contains(response)) {
            System.out.println(INVALID_TOOL_CODE);
            response = scanner.nextLine();
        }
        return response;
    }

    public Date checkoutDate() {
        Date checkoutDate = null;

        SimpleDateFormat format = new SimpleDateFormat("MM/dd/yy");

        System.out.println(ENTER_CHECKOUT_DATE);

        boolean hasValidInput = false;

        while (!hasValidInput) {
            try {
                if (scanner.hasNextLine()) {
                    String input = scanner.nextLine();
                    if (null != input && input.trim().length() > 0 && input.trim().length() < 9) {
                        checkoutDate = format.parse(input);
                        hasValidInput = true;
                    } else {
                        System.out.println(INVALID_CHECKOUT_DATE);
                    }
                }
            } catch (Exception e) {
                System.out.println(INVALID_CHECKOUT_DATE);
            }
        }
        return checkoutDate;
    }

    public int getRentalDays() {
        System.out.println(ENTER_DAYS_RENTED);
        int rentalDays = 0;
        boolean hasValidInput = false;

        while (!hasValidInput) {
            if (scanner.hasNextInt()) {
                rentalDays = Integer.parseInt(scanner.nextLine());
                if (rentalDays > 0) {
                    hasValidInput = true;
                } else {
                    System.out.println(ZERO_NUMBER_RENTAL);
                }
            } else {
                scanner.nextLine();
                System.out.println(INVALID_NUMBER_RENTAL);
            }
        }

        return rentalDays;
    }

    public int getDiscount() {
        System.out.println(ENTER_DISCOUNT);

        int discount = 0;
        boolean hasValidInput = false;

        while (!hasValidInput) {
            if (scanner.hasNextDouble()) {
                discount = Integer.parseInt(scanner.nextLine());
                if (discount >= 0 && discount <= 100) {
                    hasValidInput = true;
                } else {
                    System.out.println(INVALID_DISCOUNT_RANGE);
                }
            } else {
                scanner.nextLine();
                System.out.println(INVALID_DISCOUNT_RANGE);
            }
        }
        return discount;
    }
}
