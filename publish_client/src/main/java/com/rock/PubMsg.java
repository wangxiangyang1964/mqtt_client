package com.rock;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttTopic;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.MqttPersistenceException;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.eclipse.paho.client.mqttv3.MqttDeliveryToken;

public class PubMsg {
	  private static String broker = "tcp://59.110.238.76:1883";
	  private static String userName = "henry_002";
	  private static String passWord = "henry_002";

	  private static MqttClient connect(String clientId,String userName,String password) throws MqttException {
		    MemoryPersistence persistence = new MemoryPersistence();
		    MqttClient mqttClient = new MqttClient(broker, clientId, persistence);

		    MqttConnectOptions connOpts = new MqttConnectOptions();
		    connOpts.setCleanSession(true);
		    connOpts.setUserName(userName);
		    connOpts.setPassword(password.toCharArray());
		    connOpts.setConnectionTimeout(10);
		    connOpts.setKeepAliveInterval(20);
		    connOpts.setAutomaticReconnect(true);

        CallBack callback = new CallBack(mqttClient,connOpts);
        mqttClient.setCallback(callback);

		    mqttClient.connect(connOpts);
		    return mqttClient;
	  }

	  private static void pub(MqttClient sampleClient) {
        try{
            MqttTopic topic = sampleClient.getTopic("/public/TEST/123");
            MqttMessage message = new MqttMessage("Hello World".getBytes());
            message.setQos(0);

            while(true){
                Thread.sleep(1000);
                MqttDeliveryToken token = topic.publish(message);
                while (!token.isComplete()){
                    token.waitForCompletion(1000);
                }
			          System.out.println("pub-->" + new String(message.getPayload(),"UTF-8"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
		    //MqttMessage message = new MqttMessage("ertwersdfas".getBytes());
		    //message.setQos(qos);
		    //message.setRetained(false);
		    //sampleClient.publish("/public/TEST/123", message);
	  }

	  private static void publish(String clientId) throws MqttException{
		    MqttClient mqttClient = connect(clientId,userName,passWord);
    		if (mqttClient != null) {
			      pub(mqttClient);
		    }
	  }

	  public static void main(String[] args) throws MqttException {
		    publish("client-id-0");
	  }
}

