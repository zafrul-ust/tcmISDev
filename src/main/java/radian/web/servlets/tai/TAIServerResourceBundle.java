package radian.web.servlets.tai;

import radian.tcmis.server7.share.helpers.ServerResourceBundle;

public class TAIServerResourceBundle  extends ServerResourceBundle {

     static final Object[][] cHash = {
          {"LOG_DIR","/logsUTC"},
          {"DEBUG","false"},
          {"DB_CLIENT","TAI"},
          {"ORACLE_DRIVER",""},
          {"ORACLE_URL",""},
          {"DB_USER",""},

          {"RELATIVE_HTML","/tcmIS/msds/tai"},
          {"DB_PASS",""},
          {"DB_CLIENT_NAME","TAI"},
          {"WEBS_HOME_WWWS",""},
          {"WEBS_HOME_WWW",""},

          {"HOME_PAGE","/tcmIS/tai/home.do"},
          {"HOME_IMAGE","/images/tai.gif"},
          {"HELP_HTML_PATH","/tcmIS/help/new/"},

          {"VIEW_MSDS","/tcmIS/tai/ViewMsds?"},
          {"REGISTER_SERVLET","/tcmIS/tai/Register"},
          {"HOME_SERVLET","/tcmIS/tai/home.do"},
          {"JNLP_MAINCLASS","RadianCCTAI.jar"},
          {"JNLP_DIRECTORY","/jnlp/tai"},
          {"TEST_JNLP_DIRECTORY","/prereleasejnlp/tai"},
          {"TCMIS_START_CLASS","radian.tcmis.client.client.tai.gui.TAICmisApp"},
          {"BATCH_REPORT_SERVLET","/tcmIS/tai/batchreport?"},

          {"ADDUSER_SERVLET","/tcmIS/tai/Register?useraction=AddUser"},
          {"CHGUSER_SERVLET","/tcmIS/tai/Register?useraction=ChgUser"},
          {"ADD_SERVLET","/tcmIS/tai/Register?useraction=Add"},
          {"LOGINREG_SERVLET","/tcmIS/tai/Register?useraction=Login"},
          {"CHANGE_SERVLET","/tcmIS/tai/Register?useraction=Change"},
          {"REGISTER_HOME_SERVLET","/tcmIS/tai/Register?"},
          {"TEST_INSTALL_SERVLET","/tcmIS/tai/Register?useraction=startingtcmis"},
          {"BAR_BUILD_SERVLET","/tcmIS/tai/Msds?limit=yes"},
          {"BUILD_SERVLET","/tcmIS/tai/Msds?"},
          {"SERVLET_DIR_WWW",""},
          {"ERW_TEMPLATE_DIR","/webdata/html/template/"},
          {"BASE_DIR_TCM_WWW","/webdata/html/"},
          {"ERW_REPORT_DIR_ACTIVE","/webdata/html/reports/temp/"},
          {"ERW_REPORT_DIR_BATCH","/webdata/html/reports/"},
          {"WEBS_HOME_WWW_ERW_ACTIVE","/reports/temp/"},
          {"WEBS_HOME_WWW_ERW_BATCH","/reports/"},
          {"CATALOGING_SERVLET","/tcmIS/tai/cataloging?"},
          {"PRINT_SCREEN","/tcmIS/tai/printscreen"},
          {"HELP_SERVLET_PATH","/tcmIS/client/help"},

          {"DROPRECEIVING","No"},
          {"RELABELING","No"},
          {"DROPRECEIVING_SERVLET","/tcmIS/tai/dropshipreceivingmain.do"},
          {"DROPRECEIVING_SERVLET_HEADER","/tcmIS/tai/dropshipreceivingmain.do"},
          {"RELABELING_SERVLET","/tcmIS/tai/Relabeling?session=Active"},

		  {"COST_REPORT","/tcmIS/tai/betacostreport?"},
		  {"SCHEDULDE_DELIVERY","/tcmIS/tai/schdelivery?"},
		  {"CANCEL_MR","/tcmIS/tai/cancel?"},
		  {"WASTE_ORDER","/tcmIS/tai/wasteorder?"},
		  {"WASTE_ADHOC_REPORT","/tcmIS/tai/wastereport?"},
		  {"USAGE_ADHOC_REPORT","/tcmIS/tai/usagereport?"},
		  {"MATERIAL_MATRIX_REPORT","/tcmIS/tai/matrixreport?"},

		  {"SHIPPINGINFO","No"},
		  {"SHIPPINGINFO_SERVLET","/tcmIS/tai/shipinfo?session=Active"},
		  {"SHIPPINGINFO_SERVLET_HEADER","/tcmIS/tai/shipinfo"},

                  {"WEB_ORDER_TRACKING","Yes"},
                  {"WEB_ORDER_TRACKING_SERVLET","/tcmIS/tai/ordertrackingmain.do"},
                  {"WEB_INVENTORY","Yes"},
                  {"WEB_INVENTORY_SERVLET","/tcmIS/tai/inventorymain.do"},
                  {"WEB_CATALOG","Yes"},
                  {"WEB_CATALOG_SERVLET","/tcmIS/tai/catalog.do"},

           {"WEB_REGISTRATION","Yes"},
			{"NEW_WEB_APPLICATION","Yes"}

	  };

     public TAIServerResourceBundle(){
            super();
            addHash(cHash);
     }

}