package radian.web.servlets.standardaero;

import radian.tcmis.server7.share.helpers.ServerResourceBundle;

public class StandardAeroServerResourceBundle  extends ServerResourceBundle {

     static final Object[][] cHash = {
          {"LOG_DIR","/logsUTC"},
          {"DEBUG","false"},
          {"DB_CLIENT","STANDARD_AERO"},
          {"ORACLE_DRIVER",""},
          {"ORACLE_URL",""},
          {"DB_USER",""},

          {"RELATIVE_HTML","/tcmIS/msds/standardaero"},
          {"DB_PASS",""},
          {"DB_CLIENT_NAME","STANDARD_AERO"},
          {"WEBS_HOME_WWWS",""},
          {"WEBS_HOME_WWW",""},

          {"HOME_PAGE","/tcmIS/standardaero/home.do"},
          {"HOME_IMAGE","/images/standardaero.gif"},
          {"HELP_HTML_PATH","/tcmIS/help/new/"},

          {"VIEW_MSDS","/tcmIS/standardaero/ViewMsds?"},
          {"REGISTER_SERVLET","/tcmIS/standardaero/Register"},
          {"HOME_SERVLET","/tcmIS/standardaero/home.do"},
          {"JNLP_MAINCLASS","RadianCCStandardAero.jar"},
          {"JNLP_DIRECTORY","/jnlp/standardaero"},
          {"TEST_JNLP_DIRECTORY","/prereleasejnlp/standardaero"},
          {"TCMIS_START_CLASS","radian.tcmis.client.client.standardaero.gui.StandardAeroCmisApp"},
          {"BATCH_REPORT_SERVLET","/tcmIS/standardaero/batchreport?"},

          {"ADDUSER_SERVLET","/tcmIS/standardaero/Register?useraction=AddUser"},
          {"CHGUSER_SERVLET","/tcmIS/standardaero/Register?useraction=ChgUser"},
          {"ADD_SERVLET","/tcmIS/standardaero/Register?useraction=Add"},
          {"LOGINREG_SERVLET","/tcmIS/standardaero/Register?useraction=Login"},
          {"CHANGE_SERVLET","/tcmIS/standardaero/Register?useraction=Change"},
          {"REGISTER_HOME_SERVLET","/tcmIS/standardaero/Register?"},
          {"TEST_INSTALL_SERVLET","/tcmIS/standardaero/Register?useraction=startingtcmis"},
          {"BAR_BUILD_SERVLET","/tcmIS/standardaero/Msds?limit=yes"},
          {"BUILD_SERVLET","/tcmIS/standardaero/Msds?"},
          {"SERVLET_DIR_WWW",""},
          {"ERW_TEMPLATE_DIR","/webdata/html/template/"},
          {"BASE_DIR_TCM_WWW","/webdata/html/"},
          {"ERW_REPORT_DIR_ACTIVE","/webdata/html/reports/temp/"},
          {"ERW_REPORT_DIR_BATCH","/webdata/html/reports/"},
          {"WEBS_HOME_WWW_ERW_ACTIVE","/reports/temp/"},
          {"WEBS_HOME_WWW_ERW_BATCH","/reports/"},
          {"CATALOGING_SERVLET","/tcmIS/standardaero/cataloging?"},
          {"PRINT_SCREEN","/tcmIS/standardaero/printscreen"},
          {"HELP_SERVLET_PATH","/tcmIS/client/help"},

          {"DROPRECEIVING","Yes"},
          {"RELABELING","No"},
          {"DROPRECEIVING_SERVLET","/tcmIS/standardaero/dropshipreceivingmain.do"},
          {"DROPRECEIVING_SERVLET_HEADER","/tcmIS/standardaero/dropshipreceivingmain.do"},
          {"RELABELING_SERVLET","/tcmIS/standardaero/Relabeling?session=Active"},

		  {"COST_REPORT","/tcmIS/standardaero/betacostreport?"},
		  {"SCHEDULDE_DELIVERY","/tcmIS/standardaero/schdelivery?"},
		  {"CANCEL_MR","/tcmIS/standardaero/cancel?"},
		  {"WASTE_ORDER","/tcmIS/standardaero/wasteorder?"},
		  {"WASTE_ADHOC_REPORT","/tcmIS/standardaero/wastereport?"},
		  {"USAGE_ADHOC_REPORT","/tcmIS/standardaero/usagereport?"},
		  {"MATERIAL_MATRIX_REPORT","/tcmIS/standardaero/matrixreport?"},

		  {"SHIPPINGINFO","No"},
		  {"SHIPPINGINFO_SERVLET","/tcmIS/standardaero/shipinfo?session=Active"},
		  {"SHIPPINGINFO_SERVLET_HEADER","/tcmIS/standardaero/shipinfo"},

                  {"WEB_ORDER_TRACKING","Yes"},
                  {"WEB_ORDER_TRACKING_SERVLET","/tcmIS/standardaero/ordertrackingmain.do"},
                  {"WEB_INVENTORY","Yes"},
                  {"WEB_INVENTORY_SERVLET","/tcmIS/standardaero/inventorymain.do"},
                  {"WEB_CATALOG","Yes"},
                  {"WEB_CATALOG_SERVLET","/tcmIS/standardaero/catalog.do"},

           {"WEB_REGISTRATION","Yes"},
         {"NEW_WEB_APPLICATION","Yes"}

     };

     public StandardAeroServerResourceBundle(){
            super();
            addHash(cHash);
     }

}