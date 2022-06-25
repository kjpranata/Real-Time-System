package com.mycompany.foodcourt;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 *
 * @author Kevin Jericho
 */
public class FoodCourt implements Runnable{
    
    int standId;
    String standName;

    public FoodCourt(int standId, String standName) {
        this.standId = standId;
        this.standName = "Staff Stand " + standName;
    }
    
    @Override
    public void run() {
        try {
            ConnectionFactory factory = new ConnectionFactory();
            Connection connection = factory.newConnection();
            Channel channel = connection.createChannel();
            channel.exchangeDeclare("FoodCourt", "direct");
            String queue = channel.queueDeclare().getQueue();
            channel.queueBind(queue, "FoodCourt", "Stand "+ standId);

            channel.basicConsume(queue, true, (consumerTag, message) -> {
                String msg = new String(message.getBody(), "UTF-8");
                System.out.println("["+standName+"]" + ": Hello "+ msg + 
                        "! We will serve you our foods as soon as possible.");
                
                try {
                    TimeUnit.SECONDS.sleep(2);
                } catch (InterruptedException ex) {}

                System.out.println("["+standName+"]" + "Enjoy your meal, " + msg + "~!");
            }, consumerTag -> {});
        } catch (IOException | TimeoutException e) {}
    }
}
