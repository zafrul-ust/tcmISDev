package radian.web.servlets.hal;

import radian.tcmis.server7.share.helpers.ServerResourceBundle;

public class HALServerResourceBundle  extends ServerResourceBundle {

     static final Object[][] cHash = {
          {"LOG_DIR","/logsUTC"},
          {"DEBUG","false"},
          {"DB_CLIENT","HAL"},
          {"ORACLE_DRIVER",""},
          {"ORACLE_URL",""},
          {"DB_USER",""},

          {"RELATIVE_HTML","/tcmIS/msds/hal"},
          {"DB_PASS",""},
          {"DB_CLIENT_NAME","HAL"},
          {"WEBS_HOME_WWWS",""},
          {"WEBS_HOME_WWW",""},

          {"HOME_PAGE","/tcmIS/hal/Home"},
          {"HOME_IMAGE","/images/hal.gif"},
          {"HELP_HTML_PATH","/tcmIS/help/new/"},

          {"VIEW_MSDS","/tcmIS/hal/ViewMsds?"},
          {"REGISTER_SERVLET","/tcmIS/hal/Register"},
          {"HOME_SERVLET","/tcmIS/hal/Home"},
          {"JNLP_MAINCLASS","RadianCCHAL.jar"},
          {"JNLP_DIRECTORY","/jnlp/hal"},
          {"TEST_JNLP_DIRECTORY","/prereleasejnlp/hal"},
          {"TCMIS_START_CLASS","radian.tcmis.client.client.hal.gui.HALCmisApp"},
          {"BATCH_REPORT_SERVLET","/tcmIS/hal/batchreport?"},

          {"ADDUSER_SERVLET","/tcmIS/hal/Register?useraction=AddUser"},
          {"CHGUSER_SERVLET","/tcmIS/hal/Register?useraction=ChgUser"},
          {"ADD_SERVLET","/tcmIS/hal/Register?useraction=Add"},
          {"LOGINREG_SERVLET","/tcmIS/hal/Register?useraction=Login"},
          {"CHANGE_SERVLET","/tcmIS/hal/Register?useraction=Change"},
          {"REGISTER_HOME_SERVLET","/tcmIS/hal/Register?"},
          {"TEST_INSTALL_SERVLET","/tcmIS/hal/Register?useraction=startingtcmis"},
          {"BAR_BUILD_SERVLET","/tcmIS/hal/Msds?limit=yes"},
          {"BUILD_SERVLET","/tcmIS/hal/Msds?"},
          {"SERVLET_DIR_WWW",""},
          {"ERW_TEMPLATE_DIR","/webdata/html/template/"},
          {"BASE_DIR_TCM_WWW","/webdata/html/"},
          {"ERW_REPORT_DIR_ACTIVE","/webdata/html/reports/temp/"},
          {"ERW_REPORT_DIR_BATCH","/webdata/html/reports/"},
          {"WEBS_HOME_WWW_ERW_ACTIVE","/reports/temp/"},
          {"WEBS_HOME_WWW_ERW_BATCH","/reports/"},
          {"CATALOGING_SERVLET","/tcmIS/hal/cataloging?"},
          {"PRINT_SCREEN","/tcmIS/hal/printscreen"},
          {"HELP_SERVLET_PATH","/tcmIS/client/help"},

          {"DROPRECEIVING","No"},
          {"RELABELING","No"},
          {"DROPRECEIVING_SERVLET","/tcmIS/hal/dropshipreceivingmain.do"},
          {"DROPRECEIVING_SERVLET_HEADER","/tcmIS/hal/dropshipreceivingmain.do"},
          {"RELABELING_SERVLET","/tcmIS/hal/Relabeling?session=Active"},

		  {"COST_REPORT","/tcmIS/hal/betacostreport?"},
		  {"SCHEDULDE_DELIVERY","/tcmIS/hal/schdelivery?"},
		  {"CANCEL_MR","/tcmIS/hal/cancel?"},
		  {"WASTE_ORDER","/tcmIS/hal/wasteorder?"},
		  {"WASTE_ADHOC_REPORT","/tcmIS/hal/wastereport?"},
		  {"USAGE_ADHOC_REPORT","/tcmIS/hal/usagereport?"},
		  {"MATERIAL_MATRIX_REPORT","/tcmIS/hal/matrixreport?"},

		  {"SHIPPINGINFO","No"},
		  {"SHIPPINGINFO_SERVLET","/tcmIS/hal/shipinfo?session=Active"},
		  {"SHIPPINGINFO_SERVLET_HEADER","/tcmIS/hal/shipinfo"},

                  {"WEB_ORDER_TRACKING","Yes"},
                  {"WEB_ORDER_TRACKING_SERVLET","/tcmIS/hal/ordertrackingmain.do"},
                  {"WEB_INVENTORY","Yes"},
                  {"WEB_INVENTORY_SERVLET","/tcmIS/hal/inventorymain.do"},
                  {"WEB_CATALOG","Yes"},
                  {"WEB_CATALOG_SERVLET","/tcmIS/hal/catalog.do"},

           {"WEB_REGISTRATION","Yes"}

     };

     public HALServerResourceBundle(){
            super();
            addHash(cHash);
     }

}