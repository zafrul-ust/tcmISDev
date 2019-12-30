package radian.web.servlets.aerocz;

import radian.tcmis.server7.share.helpers.ServerResourceBundle;

public class AeroczServerResourceBundle  extends ServerResourceBundle {

     static final Object[][] cHash = {
          {"LOG_DIR","/logsUTC"},
          {"DEBUG","false"},
          {"DB_CLIENT","Aerocz"},
          {"ORACLE_DRIVER",""},
          {"ORACLE_URL",""},
          {"DB_USER",""},

          {"RELATIVE_HTML","/tcmIS/msds/aerocz"},
          {"DB_PASS",""},
          {"DB_CLIENT_NAME","Aerocz"},
          {"WEBS_HOME_WWWS",""},
          {"WEBS_HOME_WWW",""},

          {"HOME_PAGE","/tcmIS/aerocz/home.do"},
          {"HOME_IMAGE","/images/aerocz.gif"},
          {"HELP_HTML_PATH","/tcmIS/help/new/"},

          {"VIEW_MSDS","/tcmIS/aerocz/ViewMsds?"},
          {"REGISTER_SERVLET","/tcmIS/aerocz/Register"},
          {"HOME_SERVLET","/tcmIS/aerocz/home.do"},
          {"JNLP_MAINCLASS","RadianCCAerocz.jar"},
          {"JNLP_DIRECTORY","/jnlp/aerocz"},
          {"TEST_JNLP_DIRECTORY","/prereleasejnlp/aerocz"},
          {"TCMIS_START_CLASS","radian.tcmis.client.client.aerocz.gui.AeroczCmisApp"},
          {"BATCH_REPORT_SERVLET","/tcmIS/aerocz/batchreport?"},

          {"ADDUSER_SERVLET","/tcmIS/aerocz/Register?useraction=AddUser"},
          {"CHGUSER_SERVLET","/tcmIS/aerocz/Register?useraction=ChgUser"},
          {"ADD_SERVLET","/tcmIS/aerocz/Register?useraction=Add"},
          {"LOGINREG_SERVLET","/tcmIS/aerocz/Register?useraction=Login"},
          {"CHANGE_SERVLET","/tcmIS/aerocz/Register?useraction=Change"},
          {"REGISTER_HOME_SERVLET","/tcmIS/aerocz/Register?"},
          {"TEST_INSTALL_SERVLET","/tcmIS/aerocz/Register?useraction=startingtcmis"},
          {"BAR_BUILD_SERVLET","/tcmIS/aerocz/Msds?limit=yes"},
          {"BUILD_SERVLET","/tcmIS/aerocz/Msds?"},
          {"SERVLET_DIR_WWW",""},
          {"ERW_TEMPLATE_DIR","/webdata/html/template/"},
          {"BASE_DIR_TCM_WWW","/webdata/html/"},
          {"ERW_REPORT_DIR_ACTIVE","/webdata/html/reports/temp/"},
          {"ERW_REPORT_DIR_BATCH","/webdata/html/reports/"},
          {"WEBS_HOME_WWW_ERW_ACTIVE","/reports/temp/"},
          {"WEBS_HOME_WWW_ERW_BATCH","/reports/"},
          {"CATALOGING_SERVLET","/tcmIS/aerocz/cataloging?"},
          {"PRINT_SCREEN","/tcmIS/aerocz/printscreen"},
          {"HELP_SERVLET_PATH","/tcmIS/client/help"},

          {"DROPRECEIVING","No"},
          {"RELABELING","No"},
          {"DROPRECEIVING_SERVLET","/tcmIS/aerocz/dropshipreceivingmain.do"},
          {"DROPRECEIVING_SERVLET_HEADER","/tcmIS/aerocz/dropshipreceivingmain.do"},
          {"RELABELING_SERVLET","/tcmIS/aerocz/Relabeling?session=Active"},

		  {"COST_REPORT","/tcmIS/aerocz/betacostreport?"},
		  {"SCHEDULDE_DELIVERY","/tcmIS/aerocz/schdelivery?"},
		  {"CANCEL_MR","/tcmIS/aerocz/cancel?"},
		  {"WASTE_ORDER","/tcmIS/aerocz/wasteorder?"},
		  {"WASTE_ADHOC_REPORT","/tcmIS/aerocz/wastereport?"},
		  {"USAGE_ADHOC_REPORT","/tcmIS/aerocz/usagereport?"},
		  {"MATERIAL_MATRIX_REPORT","/tcmIS/aerocz/matrixreport?"},

		  {"SHIPPINGINFO","No"},
		  {"SHIPPINGINFO_SERVLET","/tcmIS/aerocz/shipinfo?session=Active"},
		  {"SHIPPINGINFO_SERVLET_HEADER","/tcmIS/aerocz/shipinfo"},

                  {"WEB_ORDER_TRACKING","Yes"},
                  {"WEB_ORDER_TRACKING_SERVLET","/tcmIS/aerocz/ordertrackingmain.do"},
                  {"WEB_INVENTORY","Yes"},
                  {"WEB_INVENTORY_SERVLET","/tcmIS/aerocz/inventorymain.do"},
                  {"WEB_CATALOG","Yes"},
                  {"WEB_CATALOG_SERVLET","/tcmIS/aerocz/catalog.do"},

           {"WEB_REGISTRATION","Yes"},
			{"NEW_WEB_APPLICATION","Yes"}

	  };

     public AeroczServerResourceBundle(){
            super();
            addHash(cHash);
     }

}