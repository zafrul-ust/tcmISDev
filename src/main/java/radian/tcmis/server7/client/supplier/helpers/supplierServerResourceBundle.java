package radian.tcmis.server7.client.supplier.helpers;

import radian.tcmis.server7.share.helpers.ServerResourceBundle;

public class supplierServerResourceBundle  extends ServerResourceBundle {

     static final Object[][] cHash = {
          {"LOG_DIR","/home/servlet/radian/logs/usgov"},
          {"DEBUG","false"},
          {"DB_CLIENT","Tcm_Ops"},
          {"ORACLE_DRIVER","oracle.jdbc.driver.OracleDriver"},
          {"SALES_ORDER_LOCATION","www.tcmis.com"},
          {"SALES_ORDER_PORT","443"},
          {"SALES_ORDER_URI","/tcmIS/servlet/radian.tcmis.server7.client.usgov.servlets.USGOVBuildSalesOrder"}
     };

     public supplierServerResourceBundle(){
            super();
            addHash(cHash);
     }

     public String getDbClient(){
      return ("SUPPLIER");
     }
}