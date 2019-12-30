package radian.web.servlets.dd;

import radian.tcmis.server7.share.helpers.ServerResourceBundle;

public class DDServerResourceBundle  extends ServerResourceBundle {

     static final Object[][] cHash = {
          {"LOG_DIR","/logsUTC"},
          {"DEBUG","false"},
          {"DB_CLIENT","DD"},
          {"ORACLE_DRIVER",""},
          {"ORACLE_URL",""},
          {"DB_USER",""},

          {"RELATIVE_HTML","/tcmIS/msds/dd"},
          {"DB_PASS",""},
          {"DB_CLIENT_NAME","DD"},
          {"WEBS_HOME_WWWS",""},
          {"WEBS_HOME_WWW",""},

          {"HOME_PAGE","/tcmIS/dd/home.do"},
          {"HOME_IMAGE","/images/dd.gif"},
          {"HELP_HTML_PATH","/tcmIS/help/new/"},

          {"VIEW_MSDS","/tcmIS/dd/ViewMsds?"},
          {"REGISTER_SERVLET","/tcmIS/dd/Register"},
          {"HOME_SERVLET","/tcmIS/dd/home.do"},
          {"JNLP_MAINCLASS","RadianCCDD.jar"},
          {"JNLP_DIRECTORY","/jnlp/dd"},
          {"TEST_JNLP_DIRECTORY","/prereleasejnlp/dd"},
          {"TCMIS_START_CLASS","radian.tcmis.client.client.dd.gui.DDCmisApp"},
          {"BATCH_REPORT_SERVLET","/tcmIS/dd/batchreport?"},

          {"ADDUSER_SERVLET","/tcmIS/dd/Register?useraction=AddUser"},
          {"CHGUSER_SERVLET","/tcmIS/dd/Register?useraction=ChgUser"},
          {"ADD_SERVLET","/tcmIS/dd/Register?useraction=Add"},
          {"LOGINREG_SERVLET","/tcmIS/dd/Register?useraction=Login"},
          {"CHANGE_SERVLET","/tcmIS/dd/Register?useraction=Change"},
          {"REGISTER_HOME_SERVLET","/tcmIS/dd/Register?"},
          {"TEST_INSTALL_SERVLET","/tcmIS/dd/Register?useraction=startingtcmis"},
          {"BAR_BUILD_SERVLET","/tcmIS/dd/Msds?limit=yes"},
          {"BUILD_SERVLET","/tcmIS/dd/Msds?"},
          {"SERVLET_DIR_WWW",""},
          {"ERW_TEMPLATE_DIR","/webdata/html/template/"},
          {"BASE_DIR_TCM_WWW","/webdata/html/"},
          {"ERW_REPORT_DIR_ACTIVE","/webdata/html/reports/temp/"},
          {"ERW_REPORT_DIR_BATCH","/webdata/html/reports/"},
          {"WEBS_HOME_WWW_ERW_ACTIVE","/reports/temp/"},
          {"WEBS_HOME_WWW_ERW_BATCH","/reports/"},
          {"CATALOGING_SERVLET","/tcmIS/dd/cataloging?"},
          {"PRINT_SCREEN","/tcmIS/dd/printscreen"},
          {"HELP_SERVLET_PATH","/tcmIS/client/help"},

          {"DROPRECEIVING","No"},
          {"RELABELING","No"},
			 {"DROPRECEIVING_SERVLET","/tcmIS/dd/dropshipreceivingmain.do"},
          {"DROPRECEIVING_SERVLET_HEADER","/tcmIS/dd/dropshipreceivingmain.do"},
          {"RELABELING_SERVLET","/tcmIS/dd/Relabeling?session=Active"},

		  {"COST_REPORT","/tcmIS/dd/betacostreport?"},
		  {"SCHEDULDE_DELIVERY","/tcmIS/dd/schdelivery?"},
		  {"CANCEL_MR","/tcmIS/dd/cancel?"},
		  {"WASTE_ORDER","/tcmIS/dd/wasteorder?"},
		  {"WASTE_ADHOC_REPORT","/tcmIS/dd/wastereport?"},
		  {"USAGE_ADHOC_REPORT","/tcmIS/dd/usagereport?"},
		  {"MATERIAL_MATRIX_REPORT","/tcmIS/dd/matrixreport?"},

		  {"SHIPPINGINFO","No"},
		  {"SHIPPINGINFO_SERVLET","/tcmIS/dd/shipinfo?session=Active"},
		  {"SHIPPINGINFO_SERVLET_HEADER","/tcmIS/dd/shipinfo"},

                  {"WEB_ORDER_TRACKING","Yes"},
                  {"WEB_ORDER_TRACKING_SERVLET","/tcmIS/dd/ordertrackingmain.do"},
                  {"WEB_INVENTORY","Yes"},
                  {"WEB_INVENTORY_SERVLET","/tcmIS/dd/inventorymain.do"},
                  {"WEB_CATALOG","Yes"},
                  {"WEB_CATALOG_SERVLET","/tcmIS/dd/catalog.do"},

           {"WEB_REGISTRATION","Yes"},
	   {"NEW_WEB_APPLICATION","Yes"}

     };

     public DDServerResourceBundle(){
            super();
            addHash(cHash);
     }

}
