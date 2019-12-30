package radian.tcmis.server7.client.magna.dbObjs;

import radian.tcmis.server7.share.dbObjs.*;
import radian.tcmis.server7.share.helpers.*;
import radian.tcmis.server7.client.magna.helpers.*;
import java.util.*;


public class MagnaTcmISDBModel  extends TcmISDBModel {
   public MagnaTcmISDBModel(){
      this("MAGNA");
   }

   public MagnaTcmISDBModel(String client){
      super(client);
   }

   public MagnaTcmISDBModel(String client,String logonVersion){
      super(client,logonVersion);
   }

   public MagnaTcmISDBModel(String client,String logonVersion,String locale){
      super(client,logonVersion,locale);
   }

	protected ServerResourceBundle getBundle(){
      return (ServerResourceBundle) new MagnaServerResourceBundle();
   }

}