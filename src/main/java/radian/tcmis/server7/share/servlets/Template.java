package radian.tcmis.server7.share.servlets;

import java.util.*;


public class Template extends TcmisServletObj{
  //Client Version
  String function = null;
  Object    myRecvObj = null;
  Hashtable mySendObj = null;

  protected void resetAllVars(){
    function = null;
    myRecvObj= null;
  }

  protected void getResult(){
    // using myRecvObj you get the function the way you want
    if(function.equalsIgnoreCase("something")) {
      //do something
    }
  }

  protected void print(TcmisOutputStreamServer out){
    try {
      out.sendObject((Hashtable) mySendObj);
    }catch(Exception e){
      e.printStackTrace();
    }
  }

}
