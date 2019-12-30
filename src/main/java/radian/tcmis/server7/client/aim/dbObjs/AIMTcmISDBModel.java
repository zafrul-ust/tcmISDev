package radian.tcmis.server7.client.aim.dbObjs;

import radian.tcmis.server7.share.dbObjs.*;
import radian.tcmis.server7.share.helpers.*;
import radian.tcmis.server7.client.aim.helpers.*;
import java.util.*;


public class AIMTcmISDBModel  extends TcmISDBModel {
   public AIMTcmISDBModel(){
      this("AIM_NORWAY");
   }

   public AIMTcmISDBModel(String client){
      super(client);
   }

   public AIMTcmISDBModel(String client,String logonVersion){
      super(client,logonVersion);
   }

   public AIMTcmISDBModel(String client,String logonVersion,String locale){
      super(client,logonVersion,locale);
   }

	protected ServerResourceBundle getBundle(){
      return (ServerResourceBundle) new AIMServerResourceBundle();
   }

}