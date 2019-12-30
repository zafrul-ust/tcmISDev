package radian.web.servlets.fbm;

import radian.tcmis.server7.share.helpers.ServerResourceBundle;

public class FBMServerResourceBundle  extends ServerResourceBundle {

     static final Object[][] cHash = {
          {"LOG_DIR","/logsUTC"},
          {"DEBUG","false"},
          {"DB_CLIENT","FBM"},
          {"ORACLE_DRIVER",""},
          {"ORACLE_URL",""},
          {"DB_USER",""},

          {"RELATIVE_HTML","/tcmIS/msds/fbm"},
          {"DB_PASS",""},
          {"DB_CLIENT_NAME","FBM"},
          {"WEBS_HOME_WWWS",""},
          {"WEBS_HOME_WWW",""},

          {"HOME_PAGE","/tcmIS/fbm/home.do"},
          {"HOME_IMAGE","/images/fbm.gif"},
          {"HELP_HTML_PATH","/tcmIS/help/new/"},

          {"VIEW_MSDS","/tcmIS/fbm/ViewMsds?"},
          {"REGISTER_SERVLET","/tcmIS/fbm/Register"},
          {"HOME_SERVLET","/tcmIS/fbm/home.do"},
          {"JNLP_MAINCLASS","RadianCCFBM.jar"},
          {"JNLP_DIRECTORY","/jnlp/fbm"},
          {"TEST_JNLP_DIRECTORY","/prereleasejnlp/fbm"},
          {"TCMIS_START_CLASS","radian.tcmis.client.client.fbm.gui.FBMCmisApp"},
          {"BATCH_REPORT_SERVLET","/tcmIS/fbm/batchreport?"},

          {"ADDUSER_SERVLET","/tcmIS/fbm/Register?useraction=AddUser"},
          {"CHGUSER_SERVLET","/tcmIS/fbm/Register?useraction=ChgUser"},
          {"ADD_SERVLET","/tcmIS/fbm/Register?useraction=Add"},
          {"LOGINREG_SERVLET","/tcmIS/fbm/Register?useraction=Login"},
          {"CHANGE_SERVLET","/tcmIS/fbm/Register?useraction=Change"},
          {"REGISTER_HOME_SERVLET","/tcmIS/fbm/Register?"},
          {"TEST_INSTALL_SERVLET","/tcmIS/fbm/Register?useraction=startingtcmis"},
          {"BAR_BUILD_SERVLET","/tcmIS/fbm/Msds?limit=yes"},
          {"BUILD_SERVLET","/tcmIS/fbm/Msds?"},
          {"SERVLET_DIR_WWW",""},
          {"ERW_TEMPLATE_DIR","/webdata/html/template/"},
          {"BASE_DIR_TCM_WWW","/webdata/html/"},
          {"ERW_REPORT_DIR_ACTIVE","/webdata/html/reports/temp/"},
          {"ERW_REPORT_DIR_BATCH","/webdata/html/reports/"},
          {"WEBS_HOME_WWW_ERW_ACTIVE","/reports/temp/"},
          {"WEBS_HOME_WWW_ERW_BATCH","/reports/"},
          {"CATALOGING_SERVLET","/tcmIS/fbm/cataloging?"},
          {"PRINT_SCREEN","/tcmIS/fbm/printscreen"},
          {"HELP_SERVLET_PATH","/tcmIS/client/help"},

          {"DROPRECEIVING","No"},
          {"RELABELING","No"},
          {"DROPRECEIVING_SERVLET","/tcmIS/fbm/dropshipreceivingmain.do"},
          {"DROPRECEIVING_SERVLET_HEADER","/tcmIS/fbm/dropshipreceivingmain.do"},
          {"RELABELING_SERVLET","/tcmIS/fbm/Relabeling?session=Active"},

		  {"COST_REPORT","/tcmIS/fbm/betacostreport?"},
		  {"SCHEDULDE_DELIVERY","/tcmIS/fbm/schdelivery?"},
		  {"CANCEL_MR","/tcmIS/fbm/cancel?"},
		  {"WASTE_ORDER","/tcmIS/fbm/wasteorder?"},
		  {"WASTE_ADHOC_REPORT","/tcmIS/fbm/wastereport?"},
		  {"USAGE_ADHOC_REPORT","/tcmIS/fbm/usagereport?"},
		  {"MATERIAL_MATRIX_REPORT","/tcmIS/fbm/matrixreport?"},

		  {"SHIPPINGINFO","No"},
		  {"SHIPPINGINFO_SERVLET","/tcmIS/fbm/shipinfo?session=Active"},
		  {"SHIPPINGINFO_SERVLET_HEADER","/tcmIS/fbm/shipinfo"},

                  {"WEB_ORDER_TRACKING","Yes"},
                  {"WEB_ORDER_TRACKING_SERVLET","/tcmIS/fbm/ordertrackingmain.do"},
                  {"WEB_INVENTORY","Yes"},
                  {"WEB_INVENTORY_SERVLET","/tcmIS/fbm/inventorymain.do"},
                  {"WEB_CATALOG","Yes"},
                  {"WEB_CATALOG_SERVLET","/tcmIS/fbm/catalog.do"},

           {"WEB_REGISTRATION","Yes"},
			{"NEW_WEB_APPLICATION","Yes"}

	  };

     public FBMServerResourceBundle(){
            super();
            addHash(cHash);
     }

}