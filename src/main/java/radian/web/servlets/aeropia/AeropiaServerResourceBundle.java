package radian.web.servlets.aeropia;

import radian.tcmis.server7.share.helpers.ServerResourceBundle;

public class AeropiaServerResourceBundle  extends ServerResourceBundle {

     static final Object[][] cHash = {
          {"LOG_DIR","/logsUTC"},
          {"DEBUG","false"},
          {"DB_CLIENT","Aeropia"},
          {"ORACLE_DRIVER",""},
          {"ORACLE_URL",""},
          {"DB_USER",""},

          {"RELATIVE_HTML","/tcmIS/msds/aeropia"},
          {"DB_PASS",""},
          {"DB_CLIENT_NAME","Aeropia"},
          {"WEBS_HOME_WWWS",""},
          {"WEBS_HOME_WWW",""},

          {"HOME_PAGE","/tcmIS/aeropia/home.do"},
          {"HOME_IMAGE","/images/aeropia.gif"},
          {"HELP_HTML_PATH","/tcmIS/help/new/"},

          {"VIEW_MSDS","/tcmIS/aeropia/ViewMsds?"},
          {"REGISTER_SERVLET","/tcmIS/aeropia/Register"},
          {"HOME_SERVLET","/tcmIS/aeropia/home.do"},
          {"JNLP_MAINCLASS","RadianCCAeropia.jar"},
          {"JNLP_DIRECTORY","/jnlp/aeropia"},
          {"TEST_JNLP_DIRECTORY","/prereleasejnlp/aeropia"},
          {"TCMIS_START_CLASS","radian.tcmis.client.client.aeropia.gui.AeropiaCmisApp"},
          {"BATCH_REPORT_SERVLET","/tcmIS/aeropia/batchreport?"},

          {"ADDUSER_SERVLET","/tcmIS/aeropia/Register?useraction=AddUser"},
          {"CHGUSER_SERVLET","/tcmIS/aeropia/Register?useraction=ChgUser"},
          {"ADD_SERVLET","/tcmIS/aeropia/Register?useraction=Add"},
          {"LOGINREG_SERVLET","/tcmIS/aeropia/Register?useraction=Login"},
          {"CHANGE_SERVLET","/tcmIS/aeropia/Register?useraction=Change"},
          {"REGISTER_HOME_SERVLET","/tcmIS/aeropia/Register?"},
          {"TEST_INSTALL_SERVLET","/tcmIS/aeropia/Register?useraction=startingtcmis"},
          {"BAR_BUILD_SERVLET","/tcmIS/aeropia/Msds?limit=yes"},
          {"BUILD_SERVLET","/tcmIS/aeropia/Msds?"},
          {"SERVLET_DIR_WWW",""},
          {"ERW_TEMPLATE_DIR","/webdata/html/template/"},
          {"BASE_DIR_TCM_WWW","/webdata/html/"},
          {"ERW_REPORT_DIR_ACTIVE","/webdata/html/reports/temp/"},
          {"ERW_REPORT_DIR_BATCH","/webdata/html/reports/"},
          {"WEBS_HOME_WWW_ERW_ACTIVE","/reports/temp/"},
          {"WEBS_HOME_WWW_ERW_BATCH","/reports/"},
          {"CATALOGING_SERVLET","/tcmIS/aeropia/cataloging?"},
          {"PRINT_SCREEN","/tcmIS/aeropia/printscreen"},
          {"HELP_SERVLET_PATH","/tcmIS/client/help"},

          {"DROPRECEIVING","No"},
          {"RELABELING","No"},
          {"DROPRECEIVING_SERVLET","/tcmIS/aeropia/dropshipreceivingmain.do"},
          {"DROPRECEIVING_SERVLET_HEADER","/tcmIS/aeropia/dropshipreceivingmain.do"},
          {"RELABELING_SERVLET","/tcmIS/aeropia/Relabeling?session=Active"},

		  {"COST_REPORT","/tcmIS/aeropia/betacostreport?"},
		  {"SCHEDULDE_DELIVERY","/tcmIS/aeropia/schdelivery?"},
		  {"CANCEL_MR","/tcmIS/aeropia/cancel?"},
		  {"WASTE_ORDER","/tcmIS/aeropia/wasteorder?"},
		  {"WASTE_ADHOC_REPORT","/tcmIS/aeropia/wastereport?"},
		  {"USAGE_ADHOC_REPORT","/tcmIS/aeropia/usagereport?"},
		  {"MATERIAL_MATRIX_REPORT","/tcmIS/aeropia/matrixreport?"},

		  {"SHIPPINGINFO","No"},
		  {"SHIPPINGINFO_SERVLET","/tcmIS/aeropia/shipinfo?session=Active"},
		  {"SHIPPINGINFO_SERVLET_HEADER","/tcmIS/aeropia/shipinfo"},

                  {"WEB_ORDER_TRACKING","Yes"},
                  {"WEB_ORDER_TRACKING_SERVLET","/tcmIS/aeropia/ordertrackingmain.do"},
                  {"WEB_INVENTORY","Yes"},
                  {"WEB_INVENTORY_SERVLET","/tcmIS/aeropia/inventorymain.do"},
                  {"WEB_CATALOG","Yes"},
                  {"WEB_CATALOG_SERVLET","/tcmIS/aeropia/catalog.do"},

           {"WEB_REGISTRATION","Yes"},
			{"NEW_WEB_APPLICATION","Yes"}

	  };

     public AeropiaServerResourceBundle(){
            super();
            addHash(cHash);
     }

}