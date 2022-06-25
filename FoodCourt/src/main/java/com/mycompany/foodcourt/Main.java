package com.mycompany.foodcourt;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args) {
        ScheduledExecutorService foodCourt = Executors.newScheduledThreadPool(7);
        foodCourt.submit(new FoodCourt(0,"FaCai"));
        foodCourt.submit(new FoodCourt(1,"126 Mixed Rice"));
        foodCourt.submit(new FoodCourt(2,"Thong Kee"));
        foodCourt.submit(new FoodCourt(3,"Allison"));
        foodCourt.submit(new FoodCourt(4,"Uncle Soon"));
        foodCourt.submit(new FoodCourt(5,"Bulgogi House"));
        foodCourt.submit(new FoodCourt(6,"Menya Hanabi"));
        
        foodCourt.scheduleAtFixedRate(new GenerateCustomer(), 5, 1, TimeUnit.SECONDS);
    }
}