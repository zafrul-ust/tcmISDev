package radian.web.servlets.usgov;

import radian.tcmis.server7.share.helpers.ServerResourceBundle;

public class USGOVServerResourceBundle  extends ServerResourceBundle {

     static final Object[][] cHash = {
          {"LOG_DIR","/logsUTC"},
          {"DEBUG","false"},
          {"DB_CLIENT","USGOV"},
          {"ORACLE_DRIVER",""},
          {"ORACLE_URL",""},
          {"DB_USER",""},

          {"RELATIVE_HTML","/tcmIS/msds/usgov"},
          {"DB_PASS",""},
          {"DB_CLIENT_NAME","USGOV"},
          {"WEBS_HOME_WWWS",""},
          {"WEBS_HOME_WWW",""},

          {"HOME_PAGE","/tcmIS/usgov/Home"},
          {"HOME_IMAGE","/images/usgov.gif"},
          {"HELP_HTML_PATH","/tcmIS/help/new/"},

          {"VIEW_MSDS","/tcmIS/usgov/ViewMsds?"},
          {"REGISTER_SERVLET","/tcmIS/usgov/Register"},
          {"HOME_SERVLET","/tcmIS/usgov/Home"},
          {"JNLP_MAINCLASS","RadianCCUSGOV.jar"},
          {"JNLP_DIRECTORY","/jnlp/usgov"},
          {"TEST_JNLP_DIRECTORY","/prereleasejnlp/usgov"},
          {"TCMIS_START_CLASS","radian.tcmis.client.client.usgov.gui.USGOVCmisApp"},
          {"BATCH_REPORT_SERVLET","/tcmIS/usgov/batchreport?"},

          {"ADDUSER_SERVLET","/tcmIS/usgov/Register?useraction=AddUser"},
          {"CHGUSER_SERVLET","/tcmIS/usgov/Register?useraction=ChgUser"},
          {"ADD_SERVLET","/tcmIS/usgov/Register?useraction=Add"},
          {"LOGINREG_SERVLET","/tcmIS/usgov/Register?useraction=Login"},
          {"CHANGE_SERVLET","/tcmIS/usgov/Register?useraction=Change"},
          {"REGISTER_HOME_SERVLET","/tcmIS/usgov/Register?"},
          {"TEST_INSTALL_SERVLET","/tcmIS/usgov/Register?useraction=startingtcmis"},
          {"BAR_BUILD_SERVLET","/tcmIS/usgov/Msds?limit=yes"},
          {"BUILD_SERVLET","/tcmIS/usgov/Msds?"},
          {"SERVLET_DIR_WWW",""},
          {"ERW_TEMPLATE_DIR","/webdata/html/template/"},
          {"BASE_DIR_TCM_WWW","/webdata/html/"},
          {"ERW_REPORT_DIR_ACTIVE","/webdata/html/reports/temp/"},
          {"ERW_REPORT_DIR_BATCH","/webdata/html/reports/"},
          {"WEBS_HOME_WWW_ERW_ACTIVE","/reports/temp/"},
          {"WEBS_HOME_WWW_ERW_BATCH","/reports/"},
          {"CATALOGING_SERVLET","/tcmIS/usgov/cataloging?"},
          {"PRINT_SCREEN","/tcmIS/usgov/printscreen"},
          {"HELP_SERVLET_PATH","/tcmIS/client/help"},

          {"DROPRECEIVING","No"},
          {"RELABELING","No"},
          {"DROPRECEIVING_SERVLET","/tcmIS/usgov/dropshipreceivingmain.do"},
          {"DROPRECEIVING_SERVLET_HEADER","/tcmIS/usgov/dropshipreceivingmain.do"},
          {"RELABELING_SERVLET","/tcmIS/usgov/Relabeling?session=Active"},

		  {"COST_REPORT","/tcmIS/usgov/betacostreport?"},
		  {"SCHEDULDE_DELIVERY","/tcmIS/usgov/schdelivery?"},
		  {"CANCEL_MR","/tcmIS/usgov/cancel?"},
		  {"WASTE_ORDER","/tcmIS/usgov/wasteorder?"},
		  {"WASTE_ADHOC_REPORT","/tcmIS/usgov/wastereport?"},
		  {"USAGE_ADHOC_REPORT","/tcmIS/usgov/usagereport?"},
		  {"MATERIAL_MATRIX_REPORT","/tcmIS/usgov/matrixreport?"},

		  {"SHIPPINGINFO","No"},
		  {"SHIPPINGINFO_SERVLET","/tcmIS/usgov/shipinfo?session=Active"},
		  {"SHIPPINGINFO_SERVLET_HEADER","/tcmIS/usgov/shipinfo"},

                  {"WEB_ORDER_TRACKING","No"},
                  {"WEB_ORDER_TRACKING_SERVLET","/tcmIS/usgov/ordertrackingmain.do"},
                  {"WEB_INVENTORY","No"},
                  {"WEB_INVENTORY_SERVLET","/tcmIS/usgov/inventorymain.do"},
                  {"WEB_CATALOG","No"},
                  {"WEB_CATALOG_SERVLET","/tcmIS/usgov/catalog.do"},
          {"SHOW_HOME","No"},
          {"SHOW_GUI","No"},
          {"SHOW_HELP","No"},
          {"WEB_REGISTRATION","No"}

     };

     public USGOVServerResourceBundle(){
            super();
            addHash(cHash);
     }

}