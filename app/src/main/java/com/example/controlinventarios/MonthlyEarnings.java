package com.example.controlinventarios;

public class MonthlyEarnings {
    private String day;
    private String month;
    private String year;
    private int earnings;

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public int getEarnings() {
        return earnings;
    }

    public void setEarnings(int earnings) {
        this.earnings = earnings;
    }

    public MonthlyEarnings(String day, String month, String year, int earnings) {
        this.day = day;
        this.month = month;
        this.year = year;
        this.earnings = earnings;
    }
}
