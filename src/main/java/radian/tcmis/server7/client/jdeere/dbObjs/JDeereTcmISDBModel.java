package radian.tcmis.server7.client.jdeere.dbObjs;

import radian.tcmis.server7.share.dbObjs.*;
import radian.tcmis.server7.share.helpers.*;
import radian.tcmis.server7.client.jdeere.helpers.JDeereServerResourceBundle;

public class JDeereTcmISDBModel  extends TcmISDBModel {
   public JDeereTcmISDBModel(){
      this("JDeere");
   }

   public JDeereTcmISDBModel(String client){
      super(client);
   }

   public JDeereTcmISDBModel(String client,String logonVersion){
      super(client,logonVersion);
   }

   public JDeereTcmISDBModel(String client,String logonVersion,String locale){
      super(client,logonVersion,locale);
   }

	protected ServerResourceBundle getBundle(){
      return (ServerResourceBundle) new JDeereServerResourceBundle();
   }

}
