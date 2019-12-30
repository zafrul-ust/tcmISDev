package radian.tcmis.server7.client.gd.dbObjs;

import radian.tcmis.server7.share.dbObjs.*;
import radian.tcmis.server7.share.helpers.ServerResourceBundle;
import radian.tcmis.server7.client.gd.helpers.*;

public class GDTcmISDBModel  extends TcmISDBModel {
   public GDTcmISDBModel(){
      this("GD");
   }

   public GDTcmISDBModel(String client){
      super(client);
   }

   public GDTcmISDBModel(String client,String logonVersion){
      super(client,logonVersion);
   }

   public GDTcmISDBModel(String client,String logonVersion,String locale){
      super(client,logonVersion,locale);
   }

	protected ServerResourceBundle getBundle(){
      return (ServerResourceBundle) new GDServerResourceBundle();
   }

}