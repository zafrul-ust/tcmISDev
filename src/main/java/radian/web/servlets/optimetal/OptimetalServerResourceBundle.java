package radian.web.servlets.optimetal;

import radian.tcmis.server7.share.helpers.ServerResourceBundle;

public class OptimetalServerResourceBundle  extends ServerResourceBundle {

     static final Object[][] cHash = {
          {"LOG_DIR","/logsUTC"},
          {"DEBUG","false"},
          {"DB_CLIENT","Optimetal"},
          {"ORACLE_DRIVER",""},
          {"ORACLE_URL",""},
          {"DB_USER",""},

          {"RELATIVE_HTML","/tcmIS/msds/optimetal"},
          {"DB_PASS",""},
          {"DB_CLIENT_NAME","Optimetal"},
          {"WEBS_HOME_WWWS",""},
          {"WEBS_HOME_WWW",""},

          {"HOME_PAGE","/tcmIS/optimetal/home.do"},
          {"HOME_IMAGE","/images/optimetal.gif"},
          {"HELP_HTML_PATH","/tcmIS/help/new/"},

          {"VIEW_MSDS","/tcmIS/optimetal/ViewMsds?"},
          {"REGISTER_SERVLET","/tcmIS/optimetal/Register"},
          {"HOME_SERVLET","/tcmIS/optimetal/home.do"},
          {"JNLP_MAINCLASS","RadianCCOptimetal.jar"},
          {"JNLP_DIRECTORY","/jnlp/optimetal"},
          {"TEST_JNLP_DIRECTORY","/prereleasejnlp/optimetal"},
          {"TCMIS_START_CLASS","radian.tcmis.client.client.optimetal.gui.OptimetalCmisApp"},
          {"BATCH_REPORT_SERVLET","/tcmIS/optimetal/batchreport?"},

          {"ADDUSER_SERVLET","/tcmIS/optimetal/Register?useraction=AddUser"},
          {"CHGUSER_SERVLET","/tcmIS/optimetal/Register?useraction=ChgUser"},
          {"ADD_SERVLET","/tcmIS/optimetal/Register?useraction=Add"},
          {"LOGINREG_SERVLET","/tcmIS/optimetal/Register?useraction=Login"},
          {"CHANGE_SERVLET","/tcmIS/optimetal/Register?useraction=Change"},
          {"REGISTER_HOME_SERVLET","/tcmIS/optimetal/Register?"},
          {"TEST_INSTALL_SERVLET","/tcmIS/optimetal/Register?useraction=startingtcmis"},
          {"BAR_BUILD_SERVLET","/tcmIS/optimetal/Msds?limit=yes"},
          {"BUILD_SERVLET","/tcmIS/optimetal/Msds?"},
          {"SERVLET_DIR_WWW",""},
          {"ERW_TEMPLATE_DIR","/webdata/html/template/"},
          {"BASE_DIR_TCM_WWW","/webdata/html/"},
          {"ERW_REPORT_DIR_ACTIVE","/webdata/html/reports/temp/"},
          {"ERW_REPORT_DIR_BATCH","/webdata/html/reports/"},
          {"WEBS_HOME_WWW_ERW_ACTIVE","/reports/temp/"},
          {"WEBS_HOME_WWW_ERW_BATCH","/reports/"},
          {"CATALOGING_SERVLET","/tcmIS/optimetal/cataloging?"},
          {"PRINT_SCREEN","/tcmIS/optimetal/printscreen"},
          {"HELP_SERVLET_PATH","/tcmIS/client/help"},

          {"DROPRECEIVING","No"},
          {"RELABELING","No"},
          {"DROPRECEIVING_SERVLET","/tcmIS/optimetal/dropshipreceivingmain.do"},
          {"DROPRECEIVING_SERVLET_HEADER","/tcmIS/optimetal/dropshipreceivingmain.do"},
          {"RELABELING_SERVLET","/tcmIS/optimetal/Relabeling?session=Active"},

		  {"COST_REPORT","/tcmIS/optimetal/betacostreport?"},
		  {"SCHEDULDE_DELIVERY","/tcmIS/optimetal/schdelivery?"},
		  {"CANCEL_MR","/tcmIS/optimetal/cancel?"},
		  {"WASTE_ORDER","/tcmIS/optimetal/wasteorder?"},
		  {"WASTE_ADHOC_REPORT","/tcmIS/optimetal/wastereport?"},
		  {"USAGE_ADHOC_REPORT","/tcmIS/optimetal/usagereport?"},
		  {"MATERIAL_MATRIX_REPORT","/tcmIS/optimetal/matrixreport?"},

		  {"SHIPPINGINFO","No"},
		  {"SHIPPINGINFO_SERVLET","/tcmIS/optimetal/shipinfo?session=Active"},
		  {"SHIPPINGINFO_SERVLET_HEADER","/tcmIS/optimetal/shipinfo"},

                  {"WEB_ORDER_TRACKING","Yes"},
                  {"WEB_ORDER_TRACKING_SERVLET","/tcmIS/optimetal/ordertrackingmain.do"},
                  {"WEB_INVENTORY","Yes"},
                  {"WEB_INVENTORY_SERVLET","/tcmIS/optimetal/inventorymain.do"},
                  {"WEB_CATALOG","Yes"},
                  {"WEB_CATALOG_SERVLET","/tcmIS/optimetal/catalog.do"},

           {"WEB_REGISTRATION","Yes"},
			{"NEW_WEB_APPLICATION","Yes"}

	  };

     public OptimetalServerResourceBundle(){
            super();
            addHash(cHash);
     }

}