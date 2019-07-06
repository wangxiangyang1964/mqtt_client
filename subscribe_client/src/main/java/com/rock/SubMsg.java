package com.rock;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.eclipse.paho.client.mqttv3.MqttClientPersistence;

public class SubMsg {

     private static String broker = "tcp://59.110.238.76:1883";
     private static String userName = "henry_002";
     private static String passWord = "henry_002";

     private static MqttClient connect(String clientId) throws MqttException{
    	   MemoryPersistence persistence = new MemoryPersistence();
         MqttClient mqttClient = new MqttClient(broker, clientId, persistence);
 
    	   MqttConnectOptions connOpts = new MqttConnectOptions();
         connOpts.setUserName(userName);
         connOpts.setPassword(passWord.toCharArray());
    	   connOpts.setCleanSession(false);
         connOpts.setConnectionTimeout(10);
         connOpts.setKeepAliveInterval(20);
         connOpts.setAutomaticReconnect(true);
        
         //回调处理类
         CallBack callback = new CallBack(mqttClient,connOpts);
         mqttClient.setCallback(callback);
         
         mqttClient.connect(connOpts);

    	   return mqttClient;
     }

     public static void sub(MqttClient mqttClient) throws MqttException{
         String[] topic = {"/public/TEST/123","/public/TEST/456"};
         int[] qos = {0,0};
         mqttClient.subscribe(topic, qos);
     }

    private static void runsub(String clientId) throws MqttException{
        MqttClient mqttClient = connect(clientId);
    	  if(mqttClient != null){
			      sub(mqttClient);
    	}
    }

    public static void main(String[] args) throws MqttException{
    		runsub("client-id-1");
    }
}

