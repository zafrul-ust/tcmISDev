package radian.web.servlets.timken;

import radian.tcmis.server7.share.helpers.ServerResourceBundle;

public class TimkenServerResourceBundle  extends ServerResourceBundle {

     static final Object[][] cHash = {
          {"LOG_DIR","/logsUTC"},
          {"DEBUG","false"},
          {"DB_CLIENT","Timken"},
          {"ORACLE_DRIVER",""},
          {"ORACLE_URL",""},
          {"DB_USER",""},

          {"RELATIVE_HTML","/tcmIS/msds/timken"},
          {"DB_PASS",""},
          {"DB_CLIENT_NAME","Timken"},
          {"WEBS_HOME_WWWS",""},
          {"WEBS_HOME_WWW",""},

          {"HOME_PAGE","/tcmIS/timken/Home"},
          {"HOME_IMAGE","/images/timken.gif"},
          {"HELP_HTML_PATH","/tcmIS/help/new/"},

          {"VIEW_MSDS","/tcmIS/timken/ViewMsds?"},
          {"REGISTER_SERVLET","/tcmIS/timken/Register"},
          {"HOME_SERVLET","/tcmIS/timken/Home"},
          {"JNLP_MAINCLASS","RadianCCTimken.jar"},
          {"JNLP_DIRECTORY","/jnlp/timken"},
          {"TEST_JNLP_DIRECTORY","/prereleasejnlp/timken"},
          {"TCMIS_START_CLASS","radian.tcmis.client.client.timken.gui.TimkenCmisApp"},
          {"BATCH_REPORT_SERVLET","/tcmIS/timken/batchreport?"},

          {"ADDUSER_SERVLET","/tcmIS/timken/Register?useraction=AddUser"},
          {"CHGUSER_SERVLET","/tcmIS/timken/Register?useraction=ChgUser"},
          {"ADD_SERVLET","/tcmIS/timken/Register?useraction=Add"},
          {"LOGINREG_SERVLET","/tcmIS/timken/Register?useraction=Login"},
          {"CHANGE_SERVLET","/tcmIS/timken/Register?useraction=Change"},
          {"REGISTER_HOME_SERVLET","/tcmIS/timken/Register?"},
          {"TEST_INSTALL_SERVLET","/tcmIS/timken/Register?useraction=startingtcmis"},
          {"BAR_BUILD_SERVLET","/tcmIS/timken/Msds?limit=yes"},
          {"BUILD_SERVLET","/tcmIS/timken/Msds?"},
          {"SERVLET_DIR_WWW",""},
          {"ERW_TEMPLATE_DIR","/webdata/html/template/"},
          {"BASE_DIR_TCM_WWW","/webdata/html/"},
          {"ERW_REPORT_DIR_ACTIVE","/webdata/html/reports/temp/"},
          {"ERW_REPORT_DIR_BATCH","/webdata/html/reports/"},
          {"WEBS_HOME_WWW_ERW_ACTIVE","/reports/temp/"},
          {"WEBS_HOME_WWW_ERW_BATCH","/reports/"},
          {"CATALOGING_SERVLET","/tcmIS/timken/cataloging?"},
          {"PRINT_SCREEN","/tcmIS/timken/printscreen"},
          {"HELP_SERVLET_PATH","/tcmIS/client/help"},

          {"DROPRECEIVING","No"},
          {"RELABELING","No"},
          {"DROPRECEIVING_SERVLET","/tcmIS/timken/dropshipreceivingmain.do"},
          {"DROPRECEIVING_SERVLET_HEADER","/tcmIS/timken/dropshipreceivingmain.do"},
          {"RELABELING_SERVLET","/tcmIS/timken/Relabeling?session=Active"},

		  {"COST_REPORT","/tcmIS/timken/betacostreport?"},
		  {"SCHEDULDE_DELIVERY","/tcmIS/timken/schdelivery?"},
		  {"CANCEL_MR","/tcmIS/timken/cancel?"},
		  {"WASTE_ORDER","/tcmIS/timken/wasteorder?"},
		  {"WASTE_ADHOC_REPORT","/tcmIS/timken/wastereport?"},
		  {"USAGE_ADHOC_REPORT","/tcmIS/timken/usagereport?"},
		  {"MATERIAL_MATRIX_REPORT","/tcmIS/timken/matrixreport?"},

		  {"SHIPPINGINFO","No"},
		  {"SHIPPINGINFO_SERVLET","/tcmIS/timken/shipinfo?session=Active"},
		  {"SHIPPINGINFO_SERVLET_HEADER","/tcmIS/timken/shipinfo"},

                  {"WEB_ORDER_TRACKING","Yes"},
                  {"WEB_ORDER_TRACKING_SERVLET","/tcmIS/timken/ordertrackingmain.do"},
                  {"WEB_INVENTORY","Yes"},
                  {"WEB_INVENTORY_SERVLET","/tcmIS/timken/inventorymain.do"},
                  {"WEB_CATALOG","Yes"},
                  {"WEB_CATALOG_SERVLET","/tcmIS/timken/catalog.do"},

           {"WEB_REGISTRATION","Yes"}

     };

     public TimkenServerResourceBundle(){
            super();
            addHash(cHash);
     }

}