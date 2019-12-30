package radian.web.servlets.utc;

import radian.tcmis.server7.share.helpers.ServerResourceBundle;

public class UTCServerResourceBundle  extends ServerResourceBundle {

     static final Object[][] cHash = {
          {"LOG_DIR","/logsUTC"},
          {"DEBUG","false"},
          {"DB_CLIENT","UTC"},
          {"ORACLE_DRIVER",""},
          {"ORACLE_URL",""},
          {"DB_USER",""},

          {"RELATIVE_HTML","/tcmIS/msds/utc"},
          {"DB_PASS",""},
          {"DB_CLIENT_NAME","UTC"},
          {"WEBS_HOME_WWWS",""},
          {"WEBS_HOME_WWW",""},

          {"HOME_PAGE","/tcmIS/utc/Home"},
          {"HOME_IMAGE","/images/utc.gif"},
          {"HELP_HTML_PATH","/tcmIS/help/new/"},

          {"VIEW_MSDS","/tcmIS/utc/ViewMsds?"},
		  {"VIEW_NON_MAT_MSDS","/tcmIS/utc/nonmatmsdsview?"},
          {"REGISTER_SERVLET","/tcmIS/utc/Register"},
          {"HOME_SERVLET","/tcmIS/utc/home.do"},
          {"JNLP_MAINCLASS","RadianCCUTC.jar"},
          {"JNLP_DIRECTORY","/jnlp/utc"},
          {"TEST_JNLP_DIRECTORY","/prereleasejnlp/utc"},
          {"TCMIS_START_CLASS","radian.tcmis.client.client.utc.gui.UTCCmisApp"},
          {"BATCH_REPORT_SERVLET","/tcmIS/utc/batchreport?"},

          {"ADDUSER_SERVLET","/tcmIS/utc/Register?useraction=AddUser"},
          {"CHGUSER_SERVLET","/tcmIS/utc/Register?useraction=ChgUser"},
          {"ADD_SERVLET","/tcmIS/utc/Register?useraction=Add"},
          {"LOGINREG_SERVLET","/tcmIS/utc/Register?useraction=Login"},
          {"CHANGE_SERVLET","/tcmIS/utc/Register?useraction=Change"},
          {"REGISTER_HOME_SERVLET","/tcmIS/utc/Register?"},
          {"TEST_INSTALL_SERVLET","/tcmIS/utc/Register?useraction=startingtcmis"},
          {"BAR_BUILD_SERVLET","/tcmIS/utc/Msds?limit=yes"},
          {"BUILD_SERVLET","/tcmIS/utc/Msds?"},
          {"SERVLET_DIR_WWW",""},
          {"ERW_TEMPLATE_DIR","/webdata/html/template/"},
          {"BASE_DIR_TCM_WWW","/webdata/html/"},
          {"ERW_REPORT_DIR_ACTIVE","/webdata/html/reports/temp/"},
          {"ERW_REPORT_DIR_BATCH","/webdata/html/reports/"},
          {"WEBS_HOME_WWW_ERW_ACTIVE","/reports/temp/"},
          {"WEBS_HOME_WWW_ERW_BATCH","/reports/"},
          {"CATALOGING_SERVLET","/tcmIS/utc/cataloging?"},
          {"PRINT_SCREEN","/tcmIS/utc/printscreen"},
          {"HELP_SERVLET_PATH","/tcmIS/client/help"},

          {"DROPRECEIVING","Yes"},
          {"RELABELING","No"},
          {"DROPRECEIVING_SERVLET","/tcmIS/utc/dropshipreceivingmain.do"},
          {"DROPRECEIVING_SERVLET_HEADER","/tcmIS/utc/dropshipreceivingmain.do"},
          {"RELABELING_SERVLET","/tcmIS/utc/Relabeling?session=Active"},

		  {"COST_REPORT","/tcmIS/utc/betacostreport?"},
		  {"SCHEDULDE_DELIVERY","/tcmIS/utc/schdelivery?"},
		  {"CANCEL_MR","/tcmIS/utc/cancel?"},
		  {"WASTE_ORDER","/tcmIS/utc/wasteorder?"},
		  {"WASTE_ADHOC_REPORT","/tcmIS/utc/wastereport?"},
		  {"USAGE_ADHOC_REPORT","/tcmIS/utc/usagereport?"},
		  {"MATERIAL_MATRIX_REPORT","/tcmIS/utc/matrixreport?"},

		  {"SHIPPINGINFO","No"},
		  {"SHIPPINGINFO_SERVLET","/tcmIS/utc/shipinfo?session=Active"},
		  {"SHIPPINGINFO_SERVLET_HEADER","/tcmIS/utc/shipinfo"},

			{"WEB_PURCHASE_REQUESTS","Yes"},
			{"WEB_PURCHASE_REQUESTS_SERVLET","/tcmIS/utc/showbuyorders.do"},
            
        {"CABINET_INVENTORY","Yes"},
	    {"CABINET_INVENTORY_SERVLET","/tcmIS/utc/newclientcabinetinventorymain.do"},

      {"WEB_PURCHASE_ORDERS","Yes"},
			{"WEB_PURCHASE_ORDERS_SERVLET","/tcmIS/utc/showpurchaseorders.do"},
                        {"WEB_ORDER_TRACKING","Yes"},
			{"WEB_ORDER_TRACKING_SERVLET","/tcmIS/utc/ordertrackingmain.do"},
      {"WEB_REGISTRATION","Yes"},
      {"NEW_WEB_APPLICATION","Yes"}
     };

     public UTCServerResourceBundle(){
            super();
            addHash(cHash);
     }

}