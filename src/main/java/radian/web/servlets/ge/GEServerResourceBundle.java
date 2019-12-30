package radian.web.servlets.ge;

import radian.tcmis.server7.share.helpers.ServerResourceBundle;

public class GEServerResourceBundle  extends ServerResourceBundle {

     static final Object[][] cHash = {
          {"LOG_DIR","/logsUTC"},
          {"DEBUG","false"},
          {"DB_CLIENT","GE"},
          {"ORACLE_DRIVER",""},
          {"ORACLE_URL",""},
          {"DB_USER",""},

          {"RELATIVE_HTML","/tcmIS/msds/ge"},
          {"DB_PASS",""},
          {"DB_CLIENT_NAME","GE"},
          {"WEBS_HOME_WWWS",""},
          {"WEBS_HOME_WWW",""},

          {"HOME_PAGE","/tcmIS/ge/home.do"},
          {"HOME_IMAGE","/images/ge.gif"},
          {"HELP_HTML_PATH","/tcmIS/help/new/"},

          {"VIEW_MSDS","/tcmIS/ge/ViewMsds?"},
          {"REGISTER_SERVLET","/tcmIS/ge/Register"},
          {"HOME_SERVLET","/tcmIS/ge/home.do"},
          {"JNLP_MAINCLASS","RadianCCGE.jar"},
          {"JNLP_DIRECTORY","/jnlp/ge"},
          {"TEST_JNLP_DIRECTORY","/prereleasejnlp/ge"},
          {"TCMIS_START_CLASS","radian.tcmis.client.client.ge.gui.GECmisApp"},
          {"BATCH_REPORT_SERVLET","/tcmIS/ge/batchreport?"},

          {"ADDUSER_SERVLET","/tcmIS/ge/Register?useraction=AddUser"},
          {"CHGUSER_SERVLET","/tcmIS/ge/Register?useraction=ChgUser"},
          {"ADD_SERVLET","/tcmIS/ge/Register?useraction=Add"},
          {"LOGINREG_SERVLET","/tcmIS/ge/Register?useraction=Login"},
          {"CHANGE_SERVLET","/tcmIS/ge/Register?useraction=Change"},
          {"REGISTER_HOME_SERVLET","/tcmIS/ge/Register?"},
          {"TEST_INSTALL_SERVLET","/tcmIS/ge/Register?useraction=startingtcmis"},
          {"BAR_BUILD_SERVLET","/tcmIS/ge/Msds?limit=yes"},
          {"BUILD_SERVLET","/tcmIS/ge/Msds?"},
          {"SERVLET_DIR_WWW",""},
          {"ERW_TEMPLATE_DIR","/webdata/html/template/"},
          {"BASE_DIR_TCM_WWW","/webdata/html/"},
          {"ERW_REPORT_DIR_ACTIVE","/webdata/html/reports/temp/"},
          {"ERW_REPORT_DIR_BATCH","/webdata/html/reports/"},
          {"WEBS_HOME_WWW_ERW_ACTIVE","/reports/temp/"},
          {"WEBS_HOME_WWW_ERW_BATCH","/reports/"},
          {"CATALOGING_SERVLET","/tcmIS/ge/cataloging?"},
          {"PRINT_SCREEN","/tcmIS/ge/printscreen"},
          {"HELP_SERVLET_PATH","/tcmIS/client/help"},

          {"DROPRECEIVING","No"},
          {"RELABELING","No"},
          {"DROPRECEIVING_SERVLET","/tcmIS/ge/dropshipreceivingmain.do"},
          {"DROPRECEIVING_SERVLET_HEADER","/tcmIS/ge/dropshipreceivingmain.do"},
          {"RELABELING_SERVLET","/tcmIS/ge/Relabeling?session=Active"},

		  {"COST_REPORT","/tcmIS/ge/betacostreport?"},
		  {"SCHEDULDE_DELIVERY","/tcmIS/ge/schdelivery?"},
		  {"CANCEL_MR","/tcmIS/ge/cancel?"},
		  {"WASTE_ORDER","/tcmIS/ge/wasteorder?"},
		  {"WASTE_ADHOC_REPORT","/tcmIS/ge/wastereport?"},
		  {"USAGE_ADHOC_REPORT","/tcmIS/ge/usagereport?"},
		  {"MATERIAL_MATRIX_REPORT","/tcmIS/ge/matrixreport?"},

		  {"SHIPPINGINFO","No"},
		  {"SHIPPINGINFO_SERVLET","/tcmIS/ge/shipinfo?session=Active"},
		  {"SHIPPINGINFO_SERVLET_HEADER","/tcmIS/ge/shipinfo"},

                  {"WEB_ORDER_TRACKING","Yes"},
                  {"WEB_ORDER_TRACKING_SERVLET","/tcmIS/ge/ordertrackingmain.do"},
                  {"WEB_INVENTORY","Yes"},
                  {"WEB_INVENTORY_SERVLET","/tcmIS/ge/inventorymain.do"},
                  {"WEB_CATALOG","Yes"},
                  {"WEB_CATALOG_SERVLET","/tcmIS/ge/catalog.do"},

           {"WEB_REGISTRATION","Yes"},
			{"NEW_WEB_APPLICATION","Yes"}

	  };

     public GEServerResourceBundle(){
            super();
            addHash(cHash);
     }

}