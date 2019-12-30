package radian.tcmis.server7.client.coproducer.helpers;

import radian.tcmis.server7.share.dbObjs.*;
import radian.tcmis.server7.share.helpers.ServerResourceBundle;

public class CoproducerServerResourceBundle  extends ServerResourceBundle {

	     static final Object[][] cHash = {
	          {"LOG_DIR","/home/servlet/radian/logs/coproducer"},
	          {"DEBUG","false"},
	          {"DB_CLIENT","COPRODUCER"},
	          {"ORACLE_DRIVER","oracle.jdbc.driver.OracleDriver"},

	          {"COST_REPORT","/tcmIS/coproducer/betacostreport?"},
	          {"SCHEDULDE_DELIVERY","/tcmIS/coproducer/schdelivery?"},
	          {"CANCEL_MR","/tcmIS/coproducer/cancel?"},
	          {"WASTE_ORDER","/tcmIS/coproducer/wasteorder?"},
	          {"WASTE_ADHOC_REPORT","/tcmIS/coproducer/wastereport?"},
	          {"USAGE_ADHOC_REPORT","/tcmIS/coproducer/usagereport?"},
	          {"MATERIAL_MATRIX_REPORT","/tcmIS/coproducer/matrixreport?"},
	          {"VIEW_MSDS","/tcmIS/coproducer/ViewMsds?"},
	          {"BATCH_REPORT_SERVLET","/tcmIS/coproducer/batchreport?"},
	          {"REGISTER_SERVLET","/tcmIS/coproducer/Register"},

	          {"SALES_ORDER_LOCATION","www.tcmis.com"},
	          {"SALES_ORDER_PORT","443"},
	          {"SALES_ORDER_URI","/tcmIS/servlet/radian.tcmis.server7.client.coproducer.servlets.CoproducerBuildSalesOrder"},

	          {"ERW_TEMPLATE_DIR","/webdata/html/template/"},
	          {"BASE_DIR_TCM_WWW","/webdata/html/"},
	          {"WEBS_HOME_WWW","https://www.tcmis.com/"},
	          {"WEBS_HOME_WWW_ERW_ACTIVE","https://www.tcmis.com/reports/temp/"},
	          {"WEBS_HOME_WWW_ERW_BATCH","https://www.tcmis.com/reports/"},
	          {"ERW_REPORT_DIR_ACTIVE","/webdata/html/reports/temp/"},
	          {"ERW_REPORT_DIR_BATCH","/webdata/html/reports/"},
	     };

	     public CoproducerServerResourceBundle(){
	            super();
	            addHash(cHash);
	     }

	     public String getDbClient(){
	      return ("COPRODUCER");
	     }

	}