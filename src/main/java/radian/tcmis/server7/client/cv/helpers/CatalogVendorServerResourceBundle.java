package radian.tcmis.server7.client.cv.helpers;

import radian.tcmis.server7.share.dbObjs.*;
import radian.tcmis.server7.share.helpers.ServerResourceBundle;

public class CatalogVendorServerResourceBundle  extends ServerResourceBundle {

	     static final Object[][] cHash = {
	          {"LOG_DIR","/home/servlet/radian/logs/cv"},
	          {"DEBUG","false"},
	          {"DB_CLIENT","CATALOG_VENDOR"},
	          {"ORACLE_DRIVER","oracle.jdbc.driver.OracleDriver"},

	          {"COST_REPORT","/tcmIS/cv/betacostreport?"},
	          {"SCHEDULDE_DELIVERY","/tcmIS/cv/schdelivery?"},
	          {"CANCEL_MR","/tcmIS/cv/cancel?"},
	          {"WASTE_ORDER","/tcmIS/cv/wasteorder?"},
	          {"WASTE_ADHOC_REPORT","/tcmIS/cv/wastereport?"},
	          {"USAGE_ADHOC_REPORT","/tcmIS/cv/usagereport?"},
	          {"MATERIAL_MATRIX_REPORT","/tcmIS/cv/matrixreport?"},
	          {"VIEW_MSDS","/tcmIS/cv/ViewMsds?"},
	          {"BATCH_REPORT_SERVLET","/tcmIS/cv/batchreport?"},
	          {"REGISTER_SERVLET","/tcmIS/cv/Register"},

	          {"SALES_ORDER_LOCATION","www.tcmis.com"},
	          {"SALES_ORDER_PORT","443"},
	          {"SALES_ORDER_URI","/tcmIS/servlet/radian.tcmis.server7.client.cv.servlets.CatalogVendorBuildSalesOrder"},

	          {"ERW_TEMPLATE_DIR","/webdata/html/template/"},
	          {"BASE_DIR_TCM_WWW","/webdata/html/"},
	          {"WEBS_HOME_WWW","https://www.tcmis.com/"},
	          {"WEBS_HOME_WWW_ERW_ACTIVE","https://www.tcmis.com/reports/temp/"},
	          {"WEBS_HOME_WWW_ERW_BATCH","https://www.tcmis.com/reports/"},
	          {"ERW_REPORT_DIR_ACTIVE","/webdata/html/reports/temp/"},
	          {"ERW_REPORT_DIR_BATCH","/webdata/html/reports/"},
	     };

	     public CatalogVendorServerResourceBundle(){
	            super();
	            addHash(cHash);
	     }

	     //trong 5/15
	     public String getDbClient(){
	      return ("CATALOG_VENDOR");
	     }

	}