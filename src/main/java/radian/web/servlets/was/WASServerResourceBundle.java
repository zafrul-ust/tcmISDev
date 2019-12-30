package radian.web.servlets.was;

import radian.tcmis.server7.share.helpers.ServerResourceBundle;

public class WASServerResourceBundle  extends ServerResourceBundle {

	     static final Object[][] cHash = {
	          {"LOG_DIR","/logsWAS"},
	          {"DEBUG","false"},
	          {"DB_CLIENT","WHIPPANY_ACTUATION_SYSTEMS"},
	          {"ORACLE_DRIVER",""},
	          {"ORACLE_URL",""},
	          {"DB_USER",""},

	          {"RELATIVE_HTML","/tcmIS/msds/was"},
	          {"DB_PASS",""},
	          {"DB_CLIENT_NAME","WHIPPANY_ACTUATION_SYSTEMS"},
	          {"WEBS_HOME_WWWS",""},
	          {"WEBS_HOME_WWW",""},

	          {"HOME_PAGE","/tcmIS/was/home.do"},
	          {"HOME_SERVLET","/tcmIS/was/home.do"},
	          {"HOME_IMAGE","/images/was.gif"},
	          {"HELP_HTML_PATH","/tcmIS/help/new/"},

	          {"VIEW_MSDS","/tcmIS/was/ViewMsds?"},
			  {"VIEW_NON_MAT_MSDS","/tcmIS/was/nonmatmsdsview?"},
	          {"REGISTER_SERVLET","/tcmIS/was/Register"},
	          {"HOME_SERVLET","/tcmIS/was/home.do"},
	          {"JNLP_MAINCLASS","RadianCCWAS.jar"},
	          {"JNLP_DIRECTORY","/jnlp/was"},
	          {"TEST_JNLP_DIRECTORY","/prereleasejnlp/was"},
	          {"TCMIS_START_CLASS","radian.tcmis.client.client.was.gui.WASCmisApp"},
	          {"BATCH_REPORT_SERVLET","/tcmIS/was/batchreport?"},

	          {"ADDUSER_SERVLET","/tcmIS/was/Register?useraction=AddUser"},
	          {"CHGUSER_SERVLET","/tcmIS/was/Register?useraction=ChgUser"},
	          {"ADD_SERVLET","/tcmIS/was/Register?useraction=Add"},
	          {"LOGINREG_SERVLET","/tcmIS/was/Register?useraction=Login"},
	          {"CHANGE_SERVLET","/tcmIS/was/Register?useraction=Change"},
	          {"REGISTER_HOME_SERVLET","/tcmIS/was/Register?"},
	          {"TEST_INSTALL_SERVLET","/tcmIS/was/Register?useraction=startingtcmis"},
	          {"BAR_BUILD_SERVLET","/tcmIS/was/Msds?limit=yes"},
	          {"BUILD_SERVLET","/tcmIS/was/Msds?"},
	          {"SERVLET_DIR_WWW",""},
	          {"ERW_TEMPLATE_DIR","/webdata/html/template/"},
	          {"BASE_DIR_TCM_WWW","/webdata/html/"},
	          {"ERW_REPORT_DIR_ACTIVE","/webdata/html/reports/temp/"},
	          {"ERW_REPORT_DIR_BATCH","/webdata/html/reports/"},
	          {"WEBS_HOME_WWW_ERW_ACTIVE","/reports/temp/"},
	          {"WEBS_HOME_WWW_ERW_BATCH","/reports/"},
	          {"CATALOGING_SERVLET","/tcmIS/was/cataloging?"},
	          {"PRINT_SCREEN","/tcmIS/was/printscreen"},
	          {"HELP_SERVLET_PATH","/tcmIS/client/help"},

	          {"DROPRECEIVING","Yes"},
	          {"RELABELING","No"},
	          {"DROPRECEIVING_SERVLET","/tcmIS/was/dropshipreceivingmain.do"},
	          {"DROPRECEIVING_SERVLET_HEADER","/tcmIS/was/dropshipreceivingmain.do"},
	          {"RELABELING_SERVLET","/tcmIS/was/Relabeling?session=Active"},

			  {"COST_REPORT","/tcmIS/was/betacostreport?"},
			  {"SCHEDULDE_DELIVERY","/tcmIS/was/schdelivery?"},
			  {"CANCEL_MR","/tcmIS/was/cancel?"},
			  {"WASTE_ORDER","/tcmIS/was/wasteorder?"},
			  {"WASTE_ADHOC_REPORT","/tcmIS/was/wastereport?"},
			  {"USAGE_ADHOC_REPORT","/tcmIS/was/usagereport?"},
			  {"MATERIAL_MATRIX_REPORT","/tcmIS/was/matrixreport?"},

			  {"SHIPPINGINFO","No"},
			  {"SHIPPINGINFO_SERVLET","/tcmIS/was/shipinfo?session=Active"},
			  {"SHIPPINGINFO_SERVLET_HEADER","/tcmIS/was/shipinfo"},

				{"WEB_PURCHASE_REQUESTS","Yes"},
				{"WEB_PURCHASE_REQUESTS_SERVLET","/tcmIS/was/showbuyorders.do"},
	            
	        {"CABINET_INVENTORY","Yes"},
		    {"CABINET_INVENTORY_SERVLET","/tcmIS/was/newclientcabinetinventorymain.do"},

	      {"WEB_PURCHASE_ORDERS","Yes"},
				{"WEB_PURCHASE_ORDERS_SERVLET","/tcmIS/was/showpurchaseorders.do"},
	                        {"WEB_ORDER_TRACKING","Yes"},
				{"WEB_ORDER_TRACKING_SERVLET","/tcmIS/was/ordertrackingmain.do"},
	      {"WEB_REGISTRATION","Yes"},
	      {"NEW_WEB_APPLICATION","Yes"}
	     };

	     public WASServerResourceBundle(){
	            super();
	            addHash(cHash);
	     }

	}
