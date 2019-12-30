package radian.web.servlets.hai;

import radian.tcmis.server7.share.helpers.ServerResourceBundle;

public class HAIServerResourceBundle  extends ServerResourceBundle {

     static final Object[][] cHash = {
          {"LOG_DIR","/logsUTC"},
          {"DEBUG","false"},
          {"DB_CLIENT","HAI"},
          {"ORACLE_DRIVER",""},
          {"ORACLE_URL",""},
          {"DB_USER",""},

          {"RELATIVE_HTML","/tcmIS/msds/hai"},
          {"DB_PASS",""},
          {"DB_CLIENT_NAME","HAI"},
          {"WEBS_HOME_WWWS",""},
          {"WEBS_HOME_WWW",""},

          {"HOME_PAGE","/tcmIS/hai/home.do"},
          {"HOME_IMAGE","/images/hai.gif"},
          {"HELP_HTML_PATH","/tcmIS/help/new/"},

          {"VIEW_MSDS","/tcmIS/hai/ViewMsds?"},
          {"REGISTER_SERVLET","/tcmIS/hai/Register"},
          {"HOME_SERVLET","/tcmIS/hai/home.do"},
          {"JNLP_MAINCLASS","RadianCCHAI.jar"},
          {"JNLP_DIRECTORY","/jnlp/hai"},
          {"TEST_JNLP_DIRECTORY","/prereleasejnlp/hai"},
          {"TCMIS_START_CLASS","radian.tcmis.client.client.hai.gui.HAICmisApp"},
          {"BATCH_REPORT_SERVLET","/tcmIS/hai/batchreport?"},

          {"ADDUSER_SERVLET","/tcmIS/hai/Register?useraction=AddUser"},
          {"CHGUSER_SERVLET","/tcmIS/hai/Register?useraction=ChgUser"},
          {"ADD_SERVLET","/tcmIS/hai/Register?useraction=Add"},
          {"LOGINREG_SERVLET","/tcmIS/hai/Register?useraction=Login"},
          {"CHANGE_SERVLET","/tcmIS/hai/Register?useraction=Change"},
          {"REGISTER_HOME_SERVLET","/tcmIS/hai/Register?"},
          {"TEST_INSTALL_SERVLET","/tcmIS/hai/Register?useraction=startingtcmis"},
          {"BAR_BUILD_SERVLET","/tcmIS/hai/Msds?limit=yes"},
          {"BUILD_SERVLET","/tcmIS/hai/Msds?"},
          {"SERVLET_DIR_WWW",""},
          {"ERW_TEMPLATE_DIR","/webdata/html/template/"},
          {"BASE_DIR_TCM_WWW","/webdata/html/"},
          {"ERW_REPORT_DIR_ACTIVE","/webdata/html/reports/temp/"},
          {"ERW_REPORT_DIR_BATCH","/webdata/html/reports/"},
          {"WEBS_HOME_WWW_ERW_ACTIVE","/reports/temp/"},
          {"WEBS_HOME_WWW_ERW_BATCH","/reports/"},
          {"CATALOGING_SERVLET","/tcmIS/hai/cataloging?"},
          {"PRINT_SCREEN","/tcmIS/hai/printscreen"},
          {"HELP_SERVLET_PATH","/tcmIS/client/help"},

          {"DROPRECEIVING","No"},
          {"RELABELING","No"},
          {"DROPRECEIVING_SERVLET","/tcmIS/hai/dropshipreceivingmain.do"},
          {"DROPRECEIVING_SERVLET_HEADER","/tcmIS/hai/dropshipreceivingmain.do"},
          {"RELABELING_SERVLET","/tcmIS/hai/Relabeling?session=Active"},

		  {"COST_REPORT","/tcmIS/hai/betacostreport?"},
		  {"SCHEDULDE_DELIVERY","/tcmIS/hai/schdelivery?"},
		  {"CANCEL_MR","/tcmIS/hai/cancel?"},
		  {"WASTE_ORDER","/tcmIS/hai/wasteorder?"},
		  {"WASTE_ADHOC_REPORT","/tcmIS/hai/wastereport?"},
		  {"USAGE_ADHOC_REPORT","/tcmIS/hai/usagereport?"},
		  {"MATERIAL_MATRIX_REPORT","/tcmIS/hai/matrixreport?"},

		  {"SHIPPINGINFO","No"},
		  {"SHIPPINGINFO_SERVLET","/tcmIS/hai/shipinfo?session=Active"},
		  {"SHIPPINGINFO_SERVLET_HEADER","/tcmIS/hai/shipinfo"},

                  {"WEB_ORDER_TRACKING","Yes"},
                  {"WEB_ORDER_TRACKING_SERVLET","/tcmIS/hai/ordertrackingmain.do"},
                  {"WEB_INVENTORY","Yes"},
                  {"WEB_INVENTORY_SERVLET","/tcmIS/hai/inventorymain.do"},
                  {"WEB_CATALOG","Yes"},
                  {"WEB_CATALOG_SERVLET","/tcmIS/hai/catalog.do"},

           {"WEB_REGISTRATION","Yes"},
			{"NEW_WEB_APPLICATION","Yes"}

	  };

     public HAIServerResourceBundle(){
            super();
            addHash(cHash);
     }

}