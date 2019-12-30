package radian.tcmis.server7.client.qos.dbObjs;

import radian.tcmis.server7.share.dbObjs.*;
import radian.tcmis.server7.share.helpers.ServerResourceBundle;
import radian.tcmis.server7.client.qos.helpers.*;

public class QOSTcmISDBModel  extends TcmISDBModel {
   public QOSTcmISDBModel(){
      this("QOS");
   }

   public QOSTcmISDBModel(String client){
      super(client);
   }

   public QOSTcmISDBModel(String client,String logonVersion){
      super(client,logonVersion);
   }

   public QOSTcmISDBModel(String client,String logonVersion,String locale){
      super(client,logonVersion,locale);
   }

	protected ServerResourceBundle getBundle(){
      return (ServerResourceBundle) new QOSServerResourceBundle();
   }

}