package radian.web.servlets.moog;

import radian.tcmis.server7.share.helpers.ServerResourceBundle;

public class MoogServerResourceBundle  extends ServerResourceBundle {

     static final Object[][] cHash = {
          {"LOG_DIR","/logsUTC"},
          {"DEBUG","false"},
          {"DB_CLIENT","MOOG"},
          {"ORACLE_DRIVER",""},
          {"ORACLE_URL",""},
          {"DB_USER",""},

          {"RELATIVE_HTML","/tcmIS/msds/moog"},
          {"DB_PASS",""},
          {"DB_CLIENT_NAME","MOOG"},
          {"WEBS_HOME_WWWS",""},
          {"WEBS_HOME_WWW",""},

          {"HOME_PAGE","/tcmIS/moog/home.do"},
          {"HOME_IMAGE","/images/moog.gif"},
          {"HELP_HTML_PATH","/tcmIS/help/new/"},

          {"VIEW_MSDS","/tcmIS/moog/ViewMsds?"},
          {"REGISTER_SERVLET","/tcmIS/moog/Register"},
          {"HOME_SERVLET","/tcmIS/moog/home.do"},
          {"JNLP_MAINCLASS","RadianCCMOOG.jar"},
          {"JNLP_DIRECTORY","/jnlp/moog"},
          {"TEST_JNLP_DIRECTORY","/prereleasejnlp/moog"},
          {"TCMIS_START_CLASS","radian.tcmis.client.client.moog.gui.MOOGCmisApp"},
          {"BATCH_REPORT_SERVLET","/tcmIS/moog/batchreport?"},

          {"ADDUSER_SERVLET","/tcmIS/moog/Register?useraction=AddUser"},
          {"CHGUSER_SERVLET","/tcmIS/moog/Register?useraction=ChgUser"},
          {"ADD_SERVLET","/tcmIS/moog/Register?useraction=Add"},
          {"LOGINREG_SERVLET","/tcmIS/moog/Register?useraction=Login"},
          {"CHANGE_SERVLET","/tcmIS/moog/Register?useraction=Change"},
          {"REGISTER_HOME_SERVLET","/tcmIS/moog/Register?"},
          {"TEST_INSTALL_SERVLET","/tcmIS/moog/Register?useraction=startingtcmis"},
          {"BAR_BUILD_SERVLET","/tcmIS/moog/Msds?limit=yes"},
          {"BUILD_SERVLET","/tcmIS/moog/Msds?"},
          {"SERVLET_DIR_WWW",""},
          {"ERW_TEMPLATE_DIR","/webdata/html/template/"},
          {"BASE_DIR_TCM_WWW","/webdata/html/"},
          {"ERW_REPORT_DIR_ACTIVE","/webdata/html/reports/temp/"},
          {"ERW_REPORT_DIR_BATCH","/webdata/html/reports/"},
          {"WEBS_HOME_WWW_ERW_ACTIVE","/reports/temp/"},
          {"WEBS_HOME_WWW_ERW_BATCH","/reports/"},
          {"CATALOGING_SERVLET","/tcmIS/moog/cataloging?"},
          {"PRINT_SCREEN","/tcmIS/moog/printscreen"},
          {"HELP_SERVLET_PATH","/tcmIS/client/help"},

          {"DROPRECEIVING","No"},
          {"RELABELING","No"},
          {"DROPRECEIVING_SERVLET","/tcmIS/moog/dropshipreceivingmain.do"},
          {"DROPRECEIVING_SERVLET_HEADER","/tcmIS/moog/dropshipreceivingmain.do"},
          {"RELABELING_SERVLET","/tcmIS/moog/Relabeling?session=Active"},

		  {"COST_REPORT","/tcmIS/moog/betacostreport?"},
		  {"SCHEDULDE_DELIVERY","/tcmIS/moog/schdelivery?"},
		  {"CANCEL_MR","/tcmIS/moog/cancel?"},
		  {"WASTE_ORDER","/tcmIS/moog/wasteorder?"},
		  {"WASTE_ADHOC_REPORT","/tcmIS/moog/wastereport?"},
		  {"USAGE_ADHOC_REPORT","/tcmIS/moog/usagereport?"},
		  {"MATERIAL_MATRIX_REPORT","/tcmIS/moog/matrixreport?"},

		  {"SHIPPINGINFO","No"},
		  {"SHIPPINGINFO_SERVLET","/tcmIS/moog/shipinfo?session=Active"},
		  {"SHIPPINGINFO_SERVLET_HEADER","/tcmIS/moog/shipinfo"},

                  {"WEB_ORDER_TRACKING","Yes"},
                  {"WEB_ORDER_TRACKING_SERVLET","/tcmIS/moog/ordertrackingmain.do"},
                  {"WEB_INVENTORY","Yes"},
                  {"WEB_INVENTORY_SERVLET","/tcmIS/moog/inventorymain.do"},
                  {"WEB_CATALOG","Yes"},
                  {"WEB_CATALOG_SERVLET","/tcmIS/moog/catalog.do"},

           {"WEB_REGISTRATION","Yes"},
			{"NEW_WEB_APPLICATION","Yes"}

	  };

     public MoogServerResourceBundle(){
            super();
            addHash(cHash);
     }

}