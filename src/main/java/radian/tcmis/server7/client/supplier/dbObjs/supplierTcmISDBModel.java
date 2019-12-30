package radian.tcmis.server7.client.supplier.dbObjs;

import radian.tcmis.server7.client.supplier.helpers.supplierServerResourceBundle;
import radian.tcmis.server7.share.dbObjs.TcmISDBModel;
import radian.tcmis.server7.share.helpers.ServerResourceBundle;

public class supplierTcmISDBModel  extends TcmISDBModel {
   public supplierTcmISDBModel(){
      this("supplier");
   }

   public supplierTcmISDBModel(String client){
      super(client);
   }

   public supplierTcmISDBModel(String client,String logonVersion){
      super(client,logonVersion);
   }

   public supplierTcmISDBModel(String client,String logonVersion,String locale){
      super(client,logonVersion,locale);
   }

	protected ServerResourceBundle getBundle(){
      return (ServerResourceBundle) new supplierServerResourceBundle();
   }

}