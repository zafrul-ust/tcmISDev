package radian.tcmis.server7.client.revima.dbObjs;

import radian.tcmis.server7.share.dbObjs.*;
import radian.tcmis.server7.share.helpers.*;
import radian.tcmis.server7.client.revima.helpers.*;
import java.util.*;


public class RevimaTcmISDBModel  extends TcmISDBModel {
   public RevimaTcmISDBModel(){
      this("REVIMA");
   }

   public RevimaTcmISDBModel(String client){
      super(client);
   }

   public RevimaTcmISDBModel(String client,String logonVersion){
      super(client,logonVersion);
   }

   public RevimaTcmISDBModel(String client,String logonVersion,String locale){
      super(client,logonVersion,locale);
   }

	protected ServerResourceBundle getBundle(){
      return (ServerResourceBundle) new RevimaServerResourceBundle();
   }

}