package radian.tcmis.server7.client.sales.dbObjs;

import radian.tcmis.server7.share.dbObjs.*;
import radian.tcmis.server7.share.helpers.ServerResourceBundle;
import radian.tcmis.server7.client.sales.helpers.*;

public class SalesTcmISDBModel  extends TcmISDBModel {
   public SalesTcmISDBModel(){
      this("Sales");
   }

   public SalesTcmISDBModel(String client){
      super(client);
   }

   public SalesTcmISDBModel(String client,String logonVersion){
      super(client,logonVersion);
   }

   public SalesTcmISDBModel(String client,String logonVersion,String locale){
      super(client,logonVersion,locale);
   }

	protected ServerResourceBundle getBundle(){
      return (ServerResourceBundle) new SalesServerResourceBundle();
   }

}