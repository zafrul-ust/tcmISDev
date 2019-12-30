package radian.web.servlets.cal;

import radian.tcmis.server7.share.helpers.ServerResourceBundle;

public class CALServerResourceBundle  extends ServerResourceBundle {

     static final Object[][] cHash = {
          {"LOG_DIR","/logsCAL"},
          {"DEBUG","false"},
          {"DB_CLIENT","CAL"},
          {"ORACLE_DRIVER",""},
          {"ORACLE_URL",""},
          {"DB_USER",""},

          {"RELATIVE_HTML","/tcmIS/msds/cal"},
          {"DB_PASS",""},
          {"DB_CLIENT_NAME","CAL"},
          {"WEBS_HOME_WWWS",""},
          {"WEBS_HOME_WWW",""},

          {"HOME_PAGE","/tcmIS/united/home.do"},
          {"HOME_IMAGE","/images/cal.gif"},
          {"HELP_HTML_PATH","/tcmIS/help/cal/"},

          {"VIEW_MSDS","/tcmIS/cal/ViewMsds?"},
          {"REGISTER_SERVLET","/tcmIS/cal/Register"},
          {"HOME_SERVLET","/tcmIS/united/home.do"},
          {"JNLP_MAINCLASS","RadianCCCAL.jar"},
          {"JNLP_DIRECTORY","/jnlp/cal"},
          {"TEST_JNLP_DIRECTORY","/prereleasejnlp/cal"},
          {"TCMIS_START_CLASS","radian.tcmis.client.client.cal.gui.CALCmisApp"},
          {"BATCH_REPORT_SERVLET","/tcmIS/united/batchreport?"},

          {"ADDUSER_SERVLET","/tcmIS/cal/Register?useraction=AddUser"},
          {"CHGUSER_SERVLET","/tcmIS/cal/Register?useraction=ChgUser"},
          {"ADD_SERVLET","/tcmIS/cal/Register?useraction=Add"},
          {"LOGINREG_SERVLET","/tcmIS/cal/Register?useraction=Login"},
          {"CHANGE_SERVLET","/tcmIS/cal/Register?useraction=Change"},
          {"REGISTER_HOME_SERVLET","/tcmIS/cal/Register?"},
          {"TEST_INSTALL_SERVLET","/tcmIS/cal/Register?useraction=startingtcmis"},
          {"BAR_BUILD_SERVLET","/tcmIS/cal/Msds?limit=yes"},
          {"BUILD_SERVLET","/tcmIS/cal/Msds?"},
          {"SERVLET_DIR_WWW",""},
          {"ERW_TEMPLATE_DIR","/webdata/html/template/"},
          {"BASE_DIR_TCM_WWW","/webdata/html/"},
          {"ERW_REPORT_DIR_ACTIVE","/webdata/html/reports/temp/"},
          {"ERW_REPORT_DIR_BATCH","/webdata/html/reports/"},
          {"WEBS_HOME_WWW_ERW_ACTIVE","/reports/temp/"},
          {"WEBS_HOME_WWW_ERW_BATCH","/reports/"},
          {"CATALOGING_SERVLET","/tcmIS/cal/cataloging?"},
          {"PRINT_SCREEN","/tcmIS/cal/printscreen"},
          {"HELP_SERVLET_PATH","/tcmIS/client/help"},

          {"DROPRECEIVING","Yes"},
          {"RELABELING","No"},
          {"DROPRECEIVING_SERVLET","/tcmIS/cal/dropshipreceivingmain.do"},
          {"DROPRECEIVING_SERVLET_HEADER","/tcmIS/cal/dropshipreceivingmain.do"},
          {"RELABELING_SERVLET","/tcmIS/cal/Relabeling?session=Active"},

		  {"COST_REPORT","/tcmIS/cal/betacostreport?"},
		  {"SCHEDULDE_DELIVERY","/tcmIS/cal/schdelivery?"},
		  {"CANCEL_MR","/tcmIS/cal/cancel?"},
		  {"WASTE_ORDER","/tcmIS/cal/wasteorder?"},
		  {"WASTE_ADHOC_REPORT","/tcmIS/cal/wastereport?"},
		  {"USAGE_ADHOC_REPORT","/tcmIS/cal/usagereport?"},
		  {"MATERIAL_MATRIX_REPORT","/tcmIS/cal/matrixreport?"},

		  {"SHIPPINGINFO","No"},
		  {"SHIPPINGINFO_SERVLET","/tcmIS/cal/shipinfo?session=Active"},
		  {"SHIPPINGINFO_SERVLET_HEADER","/tcmIS/cal/shipinfo"},

		  {"WEB_ORDER_TRACKING","No"},
		  {"WEB_ORDER_TRACKING_SERVLET","/tcmIS/cal/ordertrackingmain.do"},

		  {"WEB_INVENTORY","No"},
		  {"WEB_INVENTORY_SERVLET","/tcmIS/cal/inventorymain.do"},

		  {"WEB_CATALOG","No"},
		  {"WEB_CATALOG_SERVLET","/tcmIS/cal/catalog.do"},

                  {"WEB_SCAQMD_REPORT","Yes"},
		  {"WEB_SCAQMD_REPORT_SERVLET","/tcmIS/cal/formattedscaqmdreport.do"},

		  {"WEB_REGISTRATION","Yes"},
		  {"NEW_WEB_APPLICATION","Yes"}
		  

     };

     public CALServerResourceBundle(){
            super();
            addHash(cHash);
     }

}