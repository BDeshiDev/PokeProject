package com.company.networking;

import com.google.gson.Gson;

/*
* used for decoding strings between server and client
* So that I don't have to type it by hand
* */
public class BattleProtocol{
   static String TrainerInfoRequest = "GIVE_TRAINER_INFO";
   static String TrainerInfoHeader = "TRAINER_INFO";
   static String WinSignal = "WON";
   static String LoseSignal = "LOST";
   static String AttackCommandHeader = "ATTACK_COMMAND";
   static String SwapCommandHeader = "SWAP_COMMAND";
   static String battleStartSignal = "START_BATTLE";
   static String TurnEndOkay = "CAN_END_TURN";

   //these ones are for the real time battle system
   public static String moveMessageHeader = "MOVE";
   public static String setIdMessageHeader = "SET_ID";
   public static  String attackMessageHeader = "USED_THIS_ATTACK";
   public static  String DamageHeader = "DAMAGE_EVENT";
   public static  String SwapEventHeader = "SWAP_EVENT";
   public static  String PauseOrderHeader = "PAUSE_NOW";
   public static  String SwapRequestHeader = "SWAP_REQUEST";
   public static  String ResumeOrderHeader = "RESUME_NOW";

   public static Gson gson = new Gson();

   public static String createMessage(Object objectToConvert, String... headers){
      StringBuffer messageHolder = new StringBuffer();
      for (String h:headers) {
         messageHolder.append(h);
      }
      messageHolder.append(gson.toJson(objectToConvert));
      return  messageHolder.toString();
   }

}