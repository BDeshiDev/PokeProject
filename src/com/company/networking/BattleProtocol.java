package com.company.networking;

import com.google.gson.Gson;

/*
* used for decoding strings between server and client
* So that I don't have to type it by hand
* */
public class BattleProtocol{
   public static String TrainerInfoRequest = "GIVE_TRAINER_INFO";
   public static String TrainerInfoHeader = "TRAINER_INFO";
   public static String WinSignal = "WON";
   public static String LoseSignal = "LOST";
   public static String AttackCommandHeader = "ATTACK_COMMAND";
   public static String SwapCommandHeader = "SWAP_COMMAND";
   public static String battleStartSignal = "START_BATTLE";
   public static String TurnEndOkay = "CAN_END_TURN";

   //these ones are for the real time battle system
   public static String moveMessageHeader = "MOVE";
   public static String setIdMessageHeader = "SET_ID";
   public static  String attackMessageHeader = "USED_THIS_ATTACK";
   public static  String DamageHeader = "DAMAGE_EVENT";
   public static  String SwapEventHeader = "SWAP_EVENT";
   public static  String PauseOrderMessge = "PAUSE_NOW";
   public static  String SwapRequestHeader = "SWAP_REQUEST";
   public static  String ResumeOrderHeader = "RESUME_NOW";
   public static  String koMessage = "GOT_KO'D";
   public static  String turnConfirmHeader = "MENU_EXITED";
   public static  String TurnChargeHeader = "TURN_CHARGE_UPDATE";

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