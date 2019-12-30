package radian.web.servlets.baz;

import radian.tcmis.server7.share.helpers.ServerResourceBundle;

public class BAZServerResourceBundle  extends ServerResourceBundle {

     static final Object[][] cHash = {
          {"LOG_DIR","/logsUTC"},
          {"DEBUG","false"},
          {"DB_CLIENT","BAZ"},
          {"ORACLE_DRIVER",""},
          {"ORACLE_URL",""},
          {"DB_USER",""},

          {"RELATIVE_HTML","/tcmIS/msds/baz"},
          {"DB_PASS",""},
          {"DB_CLIENT_NAME","BAZ"},
          {"WEBS_HOME_WWWS",""},
          {"WEBS_HOME_WWW",""},

          {"HOME_PAGE","/tcmIS/baz/Home"},
          {"HOME_IMAGE","/images/baz.gif"},
          {"HELP_HTML_PATH","/tcmIS/help/new/"},

          {"VIEW_MSDS","/tcmIS/baz/ViewMsds?"},
          {"REGISTER_SERVLET","/tcmIS/baz/Register"},
          {"HOME_SERVLET","/tcmIS/baz/Home"},
          {"JNLP_MAINCLASS","RadianCCBAZ.jar"},
          {"JNLP_DIRECTORY","/jnlp/baz"},
          {"TEST_JNLP_DIRECTORY","/prereleasejnlp/baz"},
          {"TCMIS_START_CLASS","radian.tcmis.client.client.baz.gui.BAZCmisApp"},
          {"BATCH_REPORT_SERVLET","/tcmIS/baz/batchreport?"},

          {"ADDUSER_SERVLET","/tcmIS/baz/Register?useraction=AddUser"},
          {"CHGUSER_SERVLET","/tcmIS/baz/Register?useraction=ChgUser"},
          {"ADD_SERVLET","/tcmIS/baz/Register?useraction=Add"},
          {"LOGINREG_SERVLET","/tcmIS/baz/Register?useraction=Login"},
          {"CHANGE_SERVLET","/tcmIS/baz/Register?useraction=Change"},
          {"REGISTER_HOME_SERVLET","/tcmIS/baz/Register?"},
          {"TEST_INSTALL_SERVLET","/tcmIS/baz/Register?useraction=startingtcmis"},
          {"BAR_BUILD_SERVLET","/tcmIS/baz/Msds?limit=yes"},
          {"BUILD_SERVLET","/tcmIS/baz/Msds?"},
          {"SERVLET_DIR_WWW",""},
          {"ERW_TEMPLATE_DIR","/webdata/html/template/"},
          {"BASE_DIR_TCM_WWW","/webdata/html/"},
          {"ERW_REPORT_DIR_ACTIVE","/webdata/html/reports/temp/"},
          {"ERW_REPORT_DIR_BATCH","/webdata/html/reports/"},
          {"WEBS_HOME_WWW_ERW_ACTIVE","/reports/temp/"},
          {"WEBS_HOME_WWW_ERW_BATCH","/reports/"},
          {"CATALOGING_SERVLET","/tcmIS/baz/cataloging?"},
          {"PRINT_SCREEN","/tcmIS/baz/printscreen"},
          {"HELP_SERVLET_PATH","/tcmIS/client/help"},

          {"DROPRECEIVING","No"},
          {"RELABELING","No"},
          {"DROPRECEIVING_SERVLET","/tcmIS/baz/dropshipreceivingmain.do"},
          {"DROPRECEIVING_SERVLET_HEADER","/tcmIS/baz/dropshipreceivingmain.do"},
          {"RELABELING_SERVLET","/tcmIS/baz/Relabeling?session=Active"},

		  {"COST_REPORT","/tcmIS/baz/betacostreport?"},
		  {"SCHEDULDE_DELIVERY","/tcmIS/baz/schdelivery?"},
		  {"CANCEL_MR","/tcmIS/baz/cancel?"},
		  {"WASTE_ORDER","/tcmIS/baz/wasteorder?"},
		  {"WASTE_ADHOC_REPORT","/tcmIS/baz/wastereport?"},
		  {"USAGE_ADHOC_REPORT","/tcmIS/baz/usagereport?"},
		  {"MATERIAL_MATRIX_REPORT","/tcmIS/baz/matrixreport?"},

		  {"SHIPPINGINFO","No"},
		  {"SHIPPINGINFO_SERVLET","/tcmIS/baz/shipinfo?session=Active"},
		  {"SHIPPINGINFO_SERVLET_HEADER","/tcmIS/baz/shipinfo"},

                  {"WEB_ORDER_TRACKING","Yes"},
                  {"WEB_ORDER_TRACKING_SERVLET","/tcmIS/baz/ordertrackingmain.do"},
                  {"WEB_INVENTORY","Yes"},
                  {"WEB_INVENTORY_SERVLET","/tcmIS/baz/inventorymain.do"},
                  {"WEB_CATALOG","Yes"},
                  {"WEB_CATALOG_SERVLET","/tcmIS/baz/catalog.do"},

           {"WEB_REGISTRATION","Yes"}

     };

     public BAZServerResourceBundle(){
            super();
            addHash(cHash);
     }

}