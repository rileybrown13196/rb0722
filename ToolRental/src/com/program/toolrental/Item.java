package com.program.toolrental;

import com.program.toolrental.utilities.DateLogic;

import java.util.Calendar;
import java.util.Date;

public class Item {
    private String toolCode;
    private String toolType;
    private String brand;
    private int rentalDays;
    private Double dailyRate;
    private Date checkoutDate;
    private boolean isWeekdayCharge;
    private boolean isWeekendCharge;
    private boolean isHolidayCharge;
    private int discount;

    public Date getCheckoutDate() {
        return checkoutDate;
    }

    public void setCheckoutDate(Date checkoutDate) {
        this.checkoutDate = checkoutDate;
    }

    public String getToolCode() {
        return toolCode;
    }

    public void setToolCode(String toolCode) {
        this.toolCode = toolCode;
    }

    public String getToolType() {
        return toolType;
    }

    public void setToolType(String toolType) {
        this.toolType = toolType;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public int getRentalDays() {
        return rentalDays;
    }

    public void setRentalDays(int rentalDays) {
        this.rentalDays = rentalDays;
    }

    public Double getDailyRate() {
        return dailyRate;
    }

    public void setDailyRate(Double dailyRate) {
        this.dailyRate = dailyRate;
    }

    public boolean getWeekdayCharge() {
        return isWeekdayCharge;
    }

    public void setWeekdayCharge(boolean isWeekdayCharge) {
        this.isWeekdayCharge = isWeekdayCharge;
    }

    public boolean getWeekendCharge() {
        return isWeekendCharge;
    }

    public void setWeekendCharge(boolean isWeekendCharge) {
        this.isWeekendCharge = isWeekendCharge;
    }

    public boolean getHolidayCharge() {
        return isHolidayCharge;
    }

    public void setHolidayCharge(boolean isHolidayCharge) {
        this.isHolidayCharge = isHolidayCharge;
    }

    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }

    public Double getDiscountAmount() {
        Double discountAsPercent = (discount / 100.00);
        Double discountAmount;
        if((discount % 100) == 50){
            discountAmount = getInitialCost()*discountAsPercent;
            discountAmount = discountAmount + 0.005;
        }
        else {
            discountAmount = getInitialCost()*discountAsPercent;
        }
        return discountAmount;
    }

    public Double getRentalCost() {
        return getInitialCost() - getDiscountAmount();
    }

    public Double getInitialCost() {
        return getChargeDays() * dailyRate;
    }

    public Date getDueDate() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(checkoutDate);

        cal.add(Calendar.DATE, rentalDays);

        return cal.getTime();
    }

    public int getChargeDays() {
        int chargeDays = 0;

        Calendar cal = Calendar.getInstance();
        cal.setTime(checkoutDate);

        for (int ratePeriod = 0; ratePeriod < rentalDays; ratePeriod++) {
            cal.add(Calendar.DATE, 1);
            int month = cal.get(Calendar.MONTH);
            int dayOfMonth = cal.get(Calendar.DAY_OF_MONTH);
            int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);

            if (DateLogic.isWeekday(dayOfWeek) && isWeekdayCharge && !DateLogic.isHoliday(month, dayOfMonth, dayOfWeek)) {
                chargeDays++;
            }
            if (DateLogic.isWeekend(dayOfWeek) && isWeekendCharge && !DateLogic.isHoliday(month, dayOfMonth, dayOfWeek)) {
                chargeDays++;
            }
            if (DateLogic.isHoliday(month, dayOfMonth, dayOfWeek) && isHolidayCharge) {
                chargeDays++;
            }
        }
        return chargeDays;
    }
}
