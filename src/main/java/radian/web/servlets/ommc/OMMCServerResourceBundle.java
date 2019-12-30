package radian.web.servlets.ommc;

import radian.tcmis.server7.share.helpers.ServerResourceBundle;

public class OMMCServerResourceBundle  extends ServerResourceBundle {

     static final Object[][] cHash = {
          {"LOG_DIR","/logsUTC"},
          {"DEBUG","false"},
          {"DB_CLIENT","OMMC"},
          {"ORACLE_DRIVER",""},
          {"ORACLE_URL",""},
          {"DB_USER",""},

          {"RELATIVE_HTML","/tcmIS/msds/ommc"},
          {"DB_PASS",""},
          {"DB_CLIENT_NAME","OMMC"},
          {"WEBS_HOME_WWWS",""},
          {"WEBS_HOME_WWW",""},

          {"HOME_PAGE","/tcmIS/ommc/home.do"},
          {"HOME_IMAGE","/images/ommc.gif"},
          {"HELP_HTML_PATH","/tcmIS/help/new/"},

          {"VIEW_MSDS","/tcmIS/ommc/ViewMsds?"},
          {"REGISTER_SERVLET","/tcmIS/ommc/Register"},
          {"HOME_SERVLET","/tcmIS/ommc/home.do"},
          {"JNLP_MAINCLASS","RadianCCOMMC.jar"},
          {"JNLP_DIRECTORY","/jnlp/ommc"},
          {"TEST_JNLP_DIRECTORY","/prereleasejnlp/ommc"},
          {"TCMIS_START_CLASS","radian.tcmis.client.client.ommc.gui.OMMCCmisApp"},
          {"BATCH_REPORT_SERVLET","/tcmIS/ommc/batchreport?"},

          {"ADDUSER_SERVLET","/tcmIS/ommc/Register?useraction=AddUser"},
          {"CHGUSER_SERVLET","/tcmIS/ommc/Register?useraction=ChgUser"},
          {"ADD_SERVLET","/tcmIS/ommc/Register?useraction=Add"},
          {"LOGINREG_SERVLET","/tcmIS/ommc/Register?useraction=Login"},
          {"CHANGE_SERVLET","/tcmIS/ommc/Register?useraction=Change"},
          {"REGISTER_HOME_SERVLET","/tcmIS/ommc/Register?"},
          {"TEST_INSTALL_SERVLET","/tcmIS/ommc/Register?useraction=startingtcmis"},
          {"BAR_BUILD_SERVLET","/tcmIS/ommc/Msds?limit=yes"},
          {"BUILD_SERVLET","/tcmIS/ommc/Msds?"},
          {"SERVLET_DIR_WWW",""},
          {"ERW_TEMPLATE_DIR","/webdata/html/template/"},
          {"BASE_DIR_TCM_WWW","/webdata/html/"},
          {"ERW_REPORT_DIR_ACTIVE","/webdata/html/reports/temp/"},
          {"ERW_REPORT_DIR_BATCH","/webdata/html/reports/"},
          {"WEBS_HOME_WWW_ERW_ACTIVE","/reports/temp/"},
          {"WEBS_HOME_WWW_ERW_BATCH","/reports/"},
          {"CATALOGING_SERVLET","/tcmIS/ommc/cataloging?"},
          {"PRINT_SCREEN","/tcmIS/ommc/printscreen"},
          {"HELP_SERVLET_PATH","/tcmIS/client/help"},

          {"DROPRECEIVING","No"},
          {"RELABELING","No"},
          {"DROPRECEIVING_SERVLET","/tcmIS/ommc/dropshipreceivingmain.do"},
          {"DROPRECEIVING_SERVLET_HEADER","/tcmIS/ommc/dropshipreceivingmain.do"},
          {"RELABELING_SERVLET","/tcmIS/ommc/Relabeling?session=Active"},

		  {"COST_REPORT","/tcmIS/ommc/betacostreport?"},
		  {"SCHEDULDE_DELIVERY","/tcmIS/ommc/schdelivery?"},
		  {"CANCEL_MR","/tcmIS/ommc/cancel?"},
		  {"WASTE_ORDER","/tcmIS/ommc/wasteorder?"},
		  {"WASTE_ADHOC_REPORT","/tcmIS/ommc/wastereport?"},
		  {"USAGE_ADHOC_REPORT","/tcmIS/ommc/usagereport?"},
		  {"MATERIAL_MATRIX_REPORT","/tcmIS/ommc/matrixreport?"},

		  {"SHIPPINGINFO","No"},
		  {"SHIPPINGINFO_SERVLET","/tcmIS/ommc/shipinfo?session=Active"},
		  {"SHIPPINGINFO_SERVLET_HEADER","/tcmIS/ommc/shipinfo"},

                  {"WEB_ORDER_TRACKING","Yes"},
                  {"WEB_ORDER_TRACKING_SERVLET","/tcmIS/ommc/ordertrackingmain.do"},
                  {"WEB_INVENTORY","Yes"},
                  {"WEB_INVENTORY_SERVLET","/tcmIS/ommc/inventorymain.do"},
                  {"WEB_CATALOG","Yes"},
                  {"WEB_CATALOG_SERVLET","/tcmIS/ommc/catalog.do"},

           {"WEB_REGISTRATION","Yes"},
			{"NEW_WEB_APPLICATION","Yes"}

	  };

     public OMMCServerResourceBundle(){
            super();
            addHash(cHash);
     }

}