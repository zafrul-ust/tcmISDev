package radian.tcmis.server7.client.acument.dbObjs;

import radian.tcmis.server7.share.dbObjs.*;
import radian.tcmis.server7.share.helpers.*;
import radian.tcmis.server7.client.acument.helpers.*;
import java.util.*;


public class AcumentTcmISDBModel  extends TcmISDBModel {
   public AcumentTcmISDBModel(){
      this("ACUMENT");
   }

   public AcumentTcmISDBModel(String client){
      super(client);
   }

   public AcumentTcmISDBModel(String client,String logonVersion){
      super(client,logonVersion);
   }

   public AcumentTcmISDBModel(String client,String logonVersion,String locale){
      super(client,logonVersion,locale);
   }

	protected ServerResourceBundle getBundle(){
      return (ServerResourceBundle) new AcumentServerResourceBundle();
   }

}