package radian.web.servlets.maeet;

import radian.tcmis.server7.share.helpers.ServerResourceBundle;

public class MAEETServerResourceBundle  extends ServerResourceBundle {

     static final Object[][] cHash = {
          {"LOG_DIR","/logsUTC"},
          {"DEBUG","false"},
          {"DB_CLIENT","MAEET"},
          {"ORACLE_DRIVER",""},
          {"ORACLE_URL",""},
          {"DB_USER",""},

          {"RELATIVE_HTML","/tcmIS/msds/maeet"},
          {"DB_PASS",""},
          {"DB_CLIENT_NAME","MAEET"},
          {"WEBS_HOME_WWWS",""},
          {"WEBS_HOME_WWW",""},

          {"HOME_PAGE","/tcmIS/maeet/Home"},
          {"HOME_IMAGE","/images/maeet.gif"},
          {"HELP_HTML_PATH","/tcmIS/help/new/"},

          {"VIEW_MSDS","/tcmIS/maeet/ViewMsds?"},
          {"REGISTER_SERVLET","/tcmIS/maeet/Register"},
          {"HOME_SERVLET","/tcmIS/maeet/Home"},
          {"JNLP_MAINCLASS","RadianCCMAEET.jar"},
          {"JNLP_DIRECTORY","/jnlp/maeet"},
          {"TEST_JNLP_DIRECTORY","/prereleasejnlp/maeet"},
          {"TCMIS_START_CLASS","radian.tcmis.client.client.maeet.gui.MAEETCmisApp"},
          {"BATCH_REPORT_SERVLET","/tcmIS/maeet/batchreport?"},

          {"ADDUSER_SERVLET","/tcmIS/maeet/Register?useraction=AddUser"},
          {"CHGUSER_SERVLET","/tcmIS/maeet/Register?useraction=ChgUser"},
          {"ADD_SERVLET","/tcmIS/maeet/Register?useraction=Add"},
          {"LOGINREG_SERVLET","/tcmIS/maeet/Register?useraction=Login"},
          {"CHANGE_SERVLET","/tcmIS/maeet/Register?useraction=Change"},
          {"REGISTER_HOME_SERVLET","/tcmIS/maeet/Register?"},
          {"TEST_INSTALL_SERVLET","/tcmIS/maeet/Register?useraction=startingtcmis"},
          {"BAR_BUILD_SERVLET","/tcmIS/maeet/Msds?limit=yes"},
          {"BUILD_SERVLET","/tcmIS/maeet/Msds?"},
          {"SERVLET_DIR_WWW",""},
          {"ERW_TEMPLATE_DIR","/webdata/html/template/"},
          {"BASE_DIR_TCM_WWW","/webdata/html/"},
          {"ERW_REPORT_DIR_ACTIVE","/webdata/html/reports/temp/"},
          {"ERW_REPORT_DIR_BATCH","/webdata/html/reports/"},
          {"WEBS_HOME_WWW_ERW_ACTIVE","/reports/temp/"},
          {"WEBS_HOME_WWW_ERW_BATCH","/reports/"},
          {"CATALOGING_SERVLET","/tcmIS/maeet/cataloging?"},
          {"PRINT_SCREEN","/tcmIS/maeet/printscreen"},
          {"HELP_SERVLET_PATH","/tcmIS/client/help"},

          {"DROPRECEIVING","No"},
          {"RELABELING","No"},
          {"DROPRECEIVING_SERVLET","/tcmIS/maeet/dropshipreceivingmain.do"},
          {"DROPRECEIVING_SERVLET_HEADER","/tcmIS/maeet/dropshipreceivingmain.do"},
          {"RELABELING_SERVLET","/tcmIS/maeet/Relabeling?session=Active"},

		  {"COST_REPORT","/tcmIS/maeet/betacostreport?"},
		  {"SCHEDULDE_DELIVERY","/tcmIS/maeet/schdelivery?"},
		  {"CANCEL_MR","/tcmIS/maeet/cancel?"},
		  {"WASTE_ORDER","/tcmIS/maeet/wasteorder?"},
		  {"WASTE_ADHOC_REPORT","/tcmIS/maeet/wastereport?"},
		  {"USAGE_ADHOC_REPORT","/tcmIS/maeet/usagereport?"},
		  {"MATERIAL_MATRIX_REPORT","/tcmIS/maeet/matrixreport?"},

		  {"SHIPPINGINFO","No"},
		  {"SHIPPINGINFO_SERVLET","/tcmIS/maeet/shipinfo?session=Active"},
		  {"SHIPPINGINFO_SERVLET_HEADER","/tcmIS/maeet/shipinfo"},

                  {"WEB_ORDER_TRACKING","Yes"},
                  {"WEB_ORDER_TRACKING_SERVLET","/tcmIS/maeet/ordertrackingmain.do"},
                  {"WEB_INVENTORY","Yes"},
                  {"WEB_INVENTORY_SERVLET","/tcmIS/maeet/inventorymain.do"},
                  {"WEB_CATALOG","Yes"},
                  {"WEB_CATALOG_SERVLET","/tcmIS/maeet/catalog.do"},

           {"WEB_REGISTRATION","Yes"}

     };

     public MAEETServerResourceBundle(){
            super();
            addHash(cHash);
     }

}