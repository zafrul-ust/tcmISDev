package radian.tcmis.server7.client.cv.dbObjs;

import radian.tcmis.server7.client.cv.helpers.CatalogVendorServerResourceBundle;
import radian.tcmis.server7.share.dbObjs.TcmISDBModel;
import radian.tcmis.server7.share.helpers.ServerResourceBundle;

public class CatalogVendorTcmISDBModel  extends TcmISDBModel {
   public CatalogVendorTcmISDBModel(){
      this("CATALOG_VENDOR");
   }

   public CatalogVendorTcmISDBModel(String client){
      super(client);
   }

   public CatalogVendorTcmISDBModel(String client,String logonVersion){
      super(client,logonVersion);
   }

   public CatalogVendorTcmISDBModel(String client,String logonVersion,String locale){
      super(client,logonVersion,locale);
   }

	protected ServerResourceBundle getBundle(){
      return (ServerResourceBundle) new CatalogVendorServerResourceBundle();
   }

}
