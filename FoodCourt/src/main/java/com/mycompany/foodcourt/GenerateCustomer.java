package com.mycompany.foodcourt;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import java.util.Random;

public class GenerateCustomer implements Runnable{
    
    int id = 1;
    String cusName;
    String foodStand;

    @Override
    public void run() {
        cusName = "Customer "+ id;
        Random random = new Random();
        int choice = random.nextInt(7);
        foodStand = "Stand " + choice;
        
        ConnectionFactory factory = new ConnectionFactory();
        try (Connection connection = factory.newConnection()) {
            Channel channel = connection.createChannel();
            channel.exchangeDeclare("FoodCourt", "direct");
            
            System.out.println("["+ cusName +"]" + ": "
                    + "I have decided to eat in Food " + foodStand + ".");

            channel.basicPublish("FoodCourt", foodStand, false, null, cusName.getBytes());
        } catch (Exception ex) {}
        id++;
    }
}