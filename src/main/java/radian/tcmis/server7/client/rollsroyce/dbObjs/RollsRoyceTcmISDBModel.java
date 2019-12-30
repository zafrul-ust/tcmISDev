package radian.tcmis.server7.client.rollsroyce.dbObjs;

import radian.tcmis.server7.share.dbObjs.*;
import radian.tcmis.server7.share.helpers.ServerResourceBundle;
import radian.tcmis.server7.client.rollsroyce.helpers.*;

public class RollsRoyceTcmISDBModel  extends TcmISDBModel {
   public RollsRoyceTcmISDBModel(){
      this("ROLLS_ROYCE");
   }

   public RollsRoyceTcmISDBModel(String client){
      super(client);
   }

   public RollsRoyceTcmISDBModel(String client,String logonVersion){
      super(client,logonVersion);
   }

   public RollsRoyceTcmISDBModel(String client,String logonVersion,String locale){
      super(client,logonVersion,locale);
   }

	protected ServerResourceBundle getBundle(){
      return (ServerResourceBundle) new RollsRoyceServerResourceBundle();
   }

}