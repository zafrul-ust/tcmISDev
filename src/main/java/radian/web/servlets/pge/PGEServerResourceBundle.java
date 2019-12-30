package radian.web.servlets.pge;

import radian.tcmis.server7.share.helpers.ServerResourceBundle;

public class PGEServerResourceBundle  extends ServerResourceBundle {

     static final Object[][] cHash = {
          {"LOG_DIR","/logsUTC"},
          {"DEBUG","false"},
          {"DB_CLIENT","PGE"},
          {"ORACLE_DRIVER",""},
          {"ORACLE_URL",""},
          {"DB_USER",""},

          {"RELATIVE_HTML","/tcmIS/msds/pge"},
          {"DB_PASS",""},
          {"DB_CLIENT_NAME","PGE"},
          {"WEBS_HOME_WWWS",""},
          {"WEBS_HOME_WWW",""},

          {"HOME_PAGE","/tcmIS/pge/Home"},
          {"HOME_IMAGE","/images/pge.gif"},
          {"HELP_HTML_PATH","/tcmIS/help/new/"},

          {"VIEW_MSDS","/tcmIS/pge/ViewMsds?"},
          {"REGISTER_SERVLET","/tcmIS/pge/Register"},
          {"HOME_SERVLET","/tcmIS/pge/Home"},
          {"JNLP_MAINCLASS","RadianCCPGE.jar"},
          {"JNLP_DIRECTORY","/jnlp/pge"},
          {"TEST_JNLP_DIRECTORY","/prereleasejnlp/pge"},
          {"TCMIS_START_CLASS","radian.tcmis.client.client.pge.gui.PGECmisApp"},
          {"BATCH_REPORT_SERVLET","/tcmIS/pge/batchreport?"},

          {"ADDUSER_SERVLET","/tcmIS/pge/Register?useraction=AddUser"},
          {"CHGUSER_SERVLET","/tcmIS/pge/Register?useraction=ChgUser"},
          {"ADD_SERVLET","/tcmIS/pge/Register?useraction=Add"},
          {"LOGINREG_SERVLET","/tcmIS/pge/Register?useraction=Login"},
          {"CHANGE_SERVLET","/tcmIS/pge/Register?useraction=Change"},
          {"REGISTER_HOME_SERVLET","/tcmIS/pge/Register?"},
          {"TEST_INSTALL_SERVLET","/tcmIS/pge/Register?useraction=startingtcmis"},
          {"BAR_BUILD_SERVLET","/tcmIS/pge/Msds?limit=yes"},
          {"BUILD_SERVLET","/tcmIS/pge/Msds?"},
          {"SERVLET_DIR_WWW",""},
          {"ERW_TEMPLATE_DIR","/webdata/html/template/"},
          {"BASE_DIR_TCM_WWW","/webdata/html/"},
          {"ERW_REPORT_DIR_ACTIVE","/webdata/html/reports/temp/"},
          {"ERW_REPORT_DIR_BATCH","/webdata/html/reports/"},
          {"WEBS_HOME_WWW_ERW_ACTIVE","/reports/temp/"},
          {"WEBS_HOME_WWW_ERW_BATCH","/reports/"},
          {"CATALOGING_SERVLET","/tcmIS/pge/cataloging?"},
          {"PRINT_SCREEN","/tcmIS/pge/printscreen"},
          {"HELP_SERVLET_PATH","/tcmIS/client/help"},

          {"DROPRECEIVING","No"},
          {"RELABELING","No"},
          {"DROPRECEIVING_SERVLET","/tcmIS/pge/dropshipreceivingmain.do"},
          {"DROPRECEIVING_SERVLET_HEADER","/tcmIS/pge/dropshipreceivingmain.do"},
          {"RELABELING_SERVLET","/tcmIS/pge/Relabeling?session=Active"},

		  {"COST_REPORT","/tcmIS/pge/betacostreport?"},
		  {"SCHEDULDE_DELIVERY","/tcmIS/pge/schdelivery?"},
		  {"CANCEL_MR","/tcmIS/pge/cancel?"},
		  {"WASTE_ORDER","/tcmIS/pge/wasteorder?"},
		  {"WASTE_ADHOC_REPORT","/tcmIS/pge/wastereport?"},
		  {"USAGE_ADHOC_REPORT","/tcmIS/pge/usagereport?"},
		  {"MATERIAL_MATRIX_REPORT","/tcmIS/pge/matrixreport?"},

		  {"SHIPPINGINFO","No"},
		  {"SHIPPINGINFO_SERVLET","/tcmIS/pge/shipinfo?session=Active"},
		  {"SHIPPINGINFO_SERVLET_HEADER","/tcmIS/pge/shipinfo"},

		  {"WEB_ORDER_TRACKING","Yes"},
		  {"WEB_ORDER_TRACKING_SERVLET","/tcmIS/pge/ordertrackingmain.do"},

		  {"WEB_INVENTORY","Yes"},
		  {"WEB_INVENTORY_SERVLET","/tcmIS/pge/inventorymain.do"},

		  {"WEB_CATALOG","Yes"},
		  {"WEB_CATALOG_SERVLET","/tcmIS/pge/catalog.do"},

           {"WEB_REGISTRATION","Yes"}

     };

     public PGEServerResourceBundle(){
            super();
            addHash(cHash);
     }

}