package radian.tcmis.server7.client.hexcel.dbObjs;

import radian.tcmis.server7.share.dbObjs.*;
import radian.tcmis.server7.share.helpers.ServerResourceBundle;
import radian.tcmis.server7.client.hexcel.helpers.*;

public class HexcelTcmISDBModel  extends TcmISDBModel {
   public HexcelTcmISDBModel(){
      this("Hexcel");
   }

   public HexcelTcmISDBModel(String client){
      super(client);
   }

   public HexcelTcmISDBModel(String client,String logonVersion){
      super(client,logonVersion);
   }

   public HexcelTcmISDBModel(String client,String logonVersion,String locale){
      super(client,logonVersion,locale);
   }

	protected ServerResourceBundle getBundle(){
      return (ServerResourceBundle) new HexcelServerResourceBundle();
   }

}