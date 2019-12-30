package radian.tcmis.server7.client.zodiac.helpers;

import radian.tcmis.server7.share.dbObjs.*;
import radian.tcmis.server7.share.helpers.ServerResourceBundle;

public class ZodiacServerResourceBundle  extends ServerResourceBundle {

	     static final Object[][] cHash = {
	          {"LOG_DIR","/home/servlet/radian/logs/zodiac"},
	          {"DEBUG","false"},
	          {"DB_CLIENT","ZODIAC"},
	          {"ORACLE_DRIVER","oracle.jdbc.driver.OracleDriver"},

	          {"COST_REPORT","/tcmIS/zodiac/betacostreport?"},
	          {"SCHEDULDE_DELIVERY","/tcmIS/zodiac/schdelivery?"},
	          {"CANCEL_MR","/tcmIS/zodiac/cancel?"},
	          {"WASTE_ORDER","/tcmIS/zodiac/wasteorder?"},
	          {"WASTE_ADHOC_REPORT","/tcmIS/zodiac/wastereport?"},
	          {"USAGE_ADHOC_REPORT","/tcmIS/zodiac/usagereport?"},
	          {"MATERIAL_MATRIX_REPORT","/tcmIS/zodiac/matrixreport?"},
	          {"VIEW_MSDS","/tcmIS/zodiac/ViewMsds?"},
	          {"BATCH_REPORT_SERVLET","/tcmIS/zodiac/batchreport?"},
	          {"REGISTER_SERVLET","/tcmIS/zodiac/Register"},

	          {"SALES_ORDER_LOCATION","www.tcmis.com"},
	          {"SALES_ORDER_PORT","443"},
	          {"SALES_ORDER_URI","/tcmIS/servlet/radian.tcmis.server7.client.zodiac.servlets.ZodiacBuildSalesOrder"},

	          {"ERW_TEMPLATE_DIR","/webdata/html/template/"},
	          {"BASE_DIR_TCM_WWW","/webdata/html/"},
	          {"WEBS_HOME_WWW","https://www.tcmis.com/"},
	          {"WEBS_HOME_WWW_ERW_ACTIVE","https://www.tcmis.com/reports/temp/"},
	          {"WEBS_HOME_WWW_ERW_BATCH","https://www.tcmis.com/reports/"},
	          {"ERW_REPORT_DIR_ACTIVE","/webdata/html/reports/temp/"},
	          {"ERW_REPORT_DIR_BATCH","/webdata/html/reports/"},
	     };

	     public ZodiacServerResourceBundle(){
	            super();
	            addHash(cHash);
	     }

	     //trong 5/15
	     public String getDbClient(){
	      return ("ZODIAC");
	     }

	}