package radian.web.servlets.pepsi;

import radian.tcmis.server7.share.helpers.ServerResourceBundle;

public class PepsiServerResourceBundle  extends ServerResourceBundle {

     static final Object[][] cHash = {
          {"LOG_DIR","/logsUTC"},
          {"DEBUG","false"},
          {"DB_CLIENT","Pepsi"},
          {"ORACLE_DRIVER",""},
          {"ORACLE_URL",""},
          {"DB_USER",""},

          {"RELATIVE_HTML","/tcmIS/msds/pepsi"},
          {"DB_PASS",""},
          {"DB_CLIENT_NAME","Pepsi"},
          {"WEBS_HOME_WWWS",""},
          {"WEBS_HOME_WWW",""},

          {"HOME_PAGE","/tcmIS/pepsi/Home"},
          {"HOME_IMAGE","/images/pepsi.gif"},
          {"HELP_HTML_PATH","/tcmIS/help/new/"},

          {"VIEW_MSDS","/tcmIS/pepsi/ViewMsds?"},
          {"REGISTER_SERVLET","/tcmIS/pepsi/Register"},
          {"HOME_SERVLET","/tcmIS/pepsi/Home"},
          {"JNLP_MAINCLASS","RadianCCPepsi.jar"},
          {"JNLP_DIRECTORY","/jnlp/pepsi"},
          {"TEST_JNLP_DIRECTORY","/prereleasejnlp/pepsi"},
          {"TCMIS_START_CLASS","radian.tcmis.client.client.pepsi.gui.PepsiCmisApp"},
          {"BATCH_REPORT_SERVLET","/tcmIS/pepsi/batchreport?"},

          {"ADDUSER_SERVLET","/tcmIS/pepsi/Register?useraction=AddUser"},
          {"CHGUSER_SERVLET","/tcmIS/pepsi/Register?useraction=ChgUser"},
          {"ADD_SERVLET","/tcmIS/pepsi/Register?useraction=Add"},
          {"LOGINREG_SERVLET","/tcmIS/pepsi/Register?useraction=Login"},
          {"CHANGE_SERVLET","/tcmIS/pepsi/Register?useraction=Change"},
          {"REGISTER_HOME_SERVLET","/tcmIS/pepsi/Register?"},
          {"TEST_INSTALL_SERVLET","/tcmIS/pepsi/Register?useraction=startingtcmis"},
          {"BAR_BUILD_SERVLET","/tcmIS/pepsi/Msds?limit=yes"},
          {"BUILD_SERVLET","/tcmIS/pepsi/Msds?"},
          {"SERVLET_DIR_WWW",""},
          {"ERW_TEMPLATE_DIR","/webdata/html/template/"},
          {"BASE_DIR_TCM_WWW","/webdata/html/"},
          {"ERW_REPORT_DIR_ACTIVE","/webdata/html/reports/temp/"},
          {"ERW_REPORT_DIR_BATCH","/webdata/html/reports/"},
          {"WEBS_HOME_WWW_ERW_ACTIVE","/reports/temp/"},
          {"WEBS_HOME_WWW_ERW_BATCH","/reports/"},
          {"CATALOGING_SERVLET","/tcmIS/pepsi/cataloging?"},
          {"PRINT_SCREEN","/tcmIS/pepsi/printscreen"},
          {"HELP_SERVLET_PATH","/tcmIS/client/help"},

          {"DROPRECEIVING","Yes"},
          {"RELABELING","No"},
          {"DROPRECEIVING_SERVLET","/tcmIS/pepsi/dropshipreceivingmain.do"},
          {"DROPRECEIVING_SERVLET_HEADER","/tcmIS/pepsi/dropshipreceivingmain.do"},
          {"RELABELING_SERVLET","/tcmIS/pepsi/Relabeling?session=Active"},

		  {"COST_REPORT","/tcmIS/pepsi/betacostreport?"},
		  {"SCHEDULDE_DELIVERY","/tcmIS/pepsi/schdelivery?"},
		  {"CANCEL_MR","/tcmIS/pepsi/cancel?"},
		  {"WASTE_ORDER","/tcmIS/pepsi/wasteorder?"},
		  {"WASTE_ADHOC_REPORT","/tcmIS/pepsi/wastereport?"},
		  {"USAGE_ADHOC_REPORT","/tcmIS/pepsi/usagereport?"},
		  {"MATERIAL_MATRIX_REPORT","/tcmIS/pepsi/matrixreport?"},

		  {"SHIPPINGINFO","No"},
		  {"SHIPPINGINFO_SERVLET","/tcmIS/pepsi/shipinfo?session=Active"},
		  {"SHIPPINGINFO_SERVLET_HEADER","/tcmIS/pepsi/shipinfo"},

                  {"WEB_ORDER_TRACKING","Yes"},
                  {"WEB_ORDER_TRACKING_SERVLET","/tcmIS/pepsi/ordertrackingmain.do"},
                  {"WEB_INVENTORY","Yes"},
                  {"WEB_INVENTORY_SERVLET","/tcmIS/pepsi/inventorymain.do"},
                  {"WEB_CATALOG","Yes"},
                  {"WEB_CATALOG_SERVLET","/tcmIS/pepsi/catalog.do"},

           {"WEB_REGISTRATION","Yes"}

     };

     public PepsiServerResourceBundle(){
            super();
            addHash(cHash);
     }

}