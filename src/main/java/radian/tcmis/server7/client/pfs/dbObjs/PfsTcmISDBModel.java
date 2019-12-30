package radian.tcmis.server7.client.pfs.dbObjs;

import radian.tcmis.server7.share.dbObjs.*;
import radian.tcmis.server7.share.helpers.*;
import radian.tcmis.server7.client.pfs.helpers.PfsServerResourceBundle;

public class PfsTcmISDBModel  extends TcmISDBModel {
   public PfsTcmISDBModel(){
      this("PFS");
   }

   public PfsTcmISDBModel(String client){
      super(client);
   }

   public PfsTcmISDBModel(String client,String logonVersion){
      super(client,logonVersion);
   }

   public PfsTcmISDBModel(String client,String logonVersion,String locale){
      super(client,logonVersion,locale);
   }

	protected ServerResourceBundle getBundle(){
      return (ServerResourceBundle) new PfsServerResourceBundle();
   }

}
