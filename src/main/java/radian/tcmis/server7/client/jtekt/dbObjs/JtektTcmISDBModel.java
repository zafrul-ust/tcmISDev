package radian.tcmis.server7.client.jtekt.dbObjs;

import radian.tcmis.server7.share.dbObjs.*;
import radian.tcmis.server7.share.helpers.*;
import radian.tcmis.server7.client.jtekt.helpers.*;
import java.util.*;


public class JtektTcmISDBModel  extends TcmISDBModel {
   public JtektTcmISDBModel(){
      this("JTEKT");
   }

   public JtektTcmISDBModel(String client){
      super(client);
   }

   public JtektTcmISDBModel(String client,String logonVersion){
      super(client,logonVersion);
   }

   public JtektTcmISDBModel(String client,String logonVersion,String locale){
      super(client,logonVersion,locale);
   }

	protected ServerResourceBundle getBundle(){
      return (ServerResourceBundle) new JtektServerResourceBundle();
   }

}