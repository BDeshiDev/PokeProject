package com.company.networking;

import com.google.gson.Gson;

/*
* used for decoding strings between server and client
* So that I don't have to type it by hand
* */
class BattleProtocol{
   static String TrainerInfoRequest = "GIVE_TRAINER_INFO";
   static String TrainerInfoHeader = "TRAINER_INFO";
   static String WinSignal = "WON";
   static String LoseSignal = "LOST";
   static String AttackCommandHeader = "ATTACK_COMMAND";
   static String SwapCommandHeader = "SWAP_COMMAND";
   static String battleStartSignal = "START_BATTLE";
   static String TurnEndOkay = "CAN_END_TURN";


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