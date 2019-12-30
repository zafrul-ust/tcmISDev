package radian.web.servlets.gkn;

import radian.tcmis.server7.share.helpers.ServerResourceBundle;

public class GKNServerResourceBundle  extends ServerResourceBundle {

     static final Object[][] cHash = {
          {"LOG_DIR","/logsUTC"},
          {"DEBUG","false"},
          {"DB_CLIENT","GKN"},
          {"ORACLE_DRIVER",""},
          {"ORACLE_URL",""},
          {"DB_USER",""},

          {"RELATIVE_HTML","/tcmIS/msds/gkn"},
          {"DB_PASS",""},
          {"DB_CLIENT_NAME","GKN"},
          {"WEBS_HOME_WWWS",""},
          {"WEBS_HOME_WWW",""},

          {"HOME_PAGE","/tcmIS/gkn/home.do"},
          {"HOME_IMAGE","/images/gkn.gif"},
          {"HELP_HTML_PATH","/tcmIS/help/new/"},

          {"VIEW_MSDS","/tcmIS/gkn/ViewMsds?"},
          {"REGISTER_SERVLET","/tcmIS/gkn/Register"},
          {"HOME_SERVLET","/tcmIS/gkn/home.do"},
          {"JNLP_MAINCLASS","RadianCCGKN.jar"},
          {"JNLP_DIRECTORY","/jnlp/gkn"},
          {"TEST_JNLP_DIRECTORY","/prereleasejnlp/gkn"},
          {"TCMIS_START_CLASS","radian.tcmis.client.client.gkn.gui.GKNCmisApp"},
          {"BATCH_REPORT_SERVLET","/tcmIS/gkn/batchreport?"},

          {"ADDUSER_SERVLET","/tcmIS/gkn/Register?useraction=AddUser"},
          {"CHGUSER_SERVLET","/tcmIS/gkn/Register?useraction=ChgUser"},
          {"ADD_SERVLET","/tcmIS/gkn/Register?useraction=Add"},
          {"LOGINREG_SERVLET","/tcmIS/gkn/Register?useraction=Login"},
          {"CHANGE_SERVLET","/tcmIS/gkn/Register?useraction=Change"},
          {"REGISTER_HOME_SERVLET","/tcmIS/gkn/Register?"},
          {"TEST_INSTALL_SERVLET","/tcmIS/gkn/Register?useraction=startingtcmis"},
          {"BAR_BUILD_SERVLET","/tcmIS/gkn/Msds?limit=yes"},
          {"BUILD_SERVLET","/tcmIS/gkn/Msds?"},
          {"SERVLET_DIR_WWW",""},
          {"ERW_TEMPLATE_DIR","/webdata/html/template/"},
          {"BASE_DIR_TCM_WWW","/webdata/html/"},
          {"ERW_REPORT_DIR_ACTIVE","/webdata/html/reports/temp/"},
          {"ERW_REPORT_DIR_BATCH","/webdata/html/reports/"},
          {"WEBS_HOME_WWW_ERW_ACTIVE","/reports/temp/"},
          {"WEBS_HOME_WWW_ERW_BATCH","/reports/"},
          {"CATALOGING_SERVLET","/tcmIS/gkn/cataloging?"},
          {"PRINT_SCREEN","/tcmIS/gkn/printscreen"},
          {"HELP_SERVLET_PATH","/tcmIS/client/help"},

          {"DROPRECEIVING","No"},
          {"RELABELING","No"},
          {"DROPRECEIVING_SERVLET","/tcmIS/gkn/dropshipreceivingmain.do"},
          {"DROPRECEIVING_SERVLET_HEADER","/tcmIS/gkn/dropshipreceivingmain.do"},
          {"RELABELING_SERVLET","/tcmIS/gkn/Relabeling?session=Active"},

		  {"COST_REPORT","/tcmIS/gkn/betacostreport?"},
		  {"SCHEDULDE_DELIVERY","/tcmIS/gkn/schdelivery?"},
		  {"CANCEL_MR","/tcmIS/gkn/cancel?"},
		  {"WASTE_ORDER","/tcmIS/gkn/wasteorder?"},
		  {"WASTE_ADHOC_REPORT","/tcmIS/gkn/wastereport?"},
		  {"USAGE_ADHOC_REPORT","/tcmIS/gkn/usagereport?"},
		  {"MATERIAL_MATRIX_REPORT","/tcmIS/gkn/matrixreport?"},

		  {"SHIPPINGINFO","No"},
		  {"SHIPPINGINFO_SERVLET","/tcmIS/gkn/shipinfo?session=Active"},
		  {"SHIPPINGINFO_SERVLET_HEADER","/tcmIS/gkn/shipinfo"},

                  {"WEB_ORDER_TRACKING","Yes"},
                  {"WEB_ORDER_TRACKING_SERVLET","/tcmIS/gkn/ordertrackingmain.do"},
                  {"WEB_INVENTORY","Yes"},
                  {"WEB_INVENTORY_SERVLET","/tcmIS/gkn/inventorymain.do"},
                  {"WEB_CATALOG","Yes"},
                  {"WEB_CATALOG_SERVLET","/tcmIS/gkn/catalog.do"},

           {"WEB_REGISTRATION","Yes"},
			{"NEW_WEB_APPLICATION","Yes"}

	  };

     public GKNServerResourceBundle(){
            super();
            addHash(cHash);
     }

}