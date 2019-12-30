package radian.web.servlets.bai;

import radian.tcmis.server7.share.helpers.ServerResourceBundle;

public class BAIServerResourceBundle  extends ServerResourceBundle {

     static final Object[][] cHash = {
          {"LOG_DIR","/logsUTC"},
          {"DEBUG","false"},
          {"DB_CLIENT","BAI"},
          {"ORACLE_DRIVER",""},
          {"ORACLE_URL",""},
          {"DB_USER",""},

          {"RELATIVE_HTML","/tcmIS/msds/bai"},
          {"DB_PASS",""},
          {"DB_CLIENT_NAME","BAI"},
          {"WEBS_HOME_WWWS",""},
          {"WEBS_HOME_WWW",""},

          {"HOME_PAGE","/tcmIS/bai/home.do"},
          {"HOME_IMAGE","/images/bai.gif"},
          {"HELP_HTML_PATH","/tcmIS/help/new/"},

          {"VIEW_MSDS","/tcmIS/bai/ViewMsds?"},
          {"REGISTER_SERVLET","/tcmIS/bai/Register"},
          {"HOME_SERVLET","/tcmIS/bai/home.do"},
          {"JNLP_MAINCLASS","RadianCCBAI.jar"},
          {"JNLP_DIRECTORY","/jnlp/bai"},
          {"TEST_JNLP_DIRECTORY","/prereleasejnlp/bai"},
          {"TCMIS_START_CLASS","radian.tcmis.client.client.bai.gui.BAICmisApp"},
          {"BATCH_REPORT_SERVLET","/tcmIS/bai/batchreport?"},

          {"ADDUSER_SERVLET","/tcmIS/bai/Register?useraction=AddUser"},
          {"CHGUSER_SERVLET","/tcmIS/bai/Register?useraction=ChgUser"},
          {"ADD_SERVLET","/tcmIS/bai/Register?useraction=Add"},
          {"LOGINREG_SERVLET","/tcmIS/bai/Register?useraction=Login"},
          {"CHANGE_SERVLET","/tcmIS/bai/Register?useraction=Change"},
          {"REGISTER_HOME_SERVLET","/tcmIS/bai/Register?"},
          {"TEST_INSTALL_SERVLET","/tcmIS/bai/Register?useraction=startingtcmis"},
          {"BAR_BUILD_SERVLET","/tcmIS/bai/Msds?limit=yes"},
          {"BUILD_SERVLET","/tcmIS/bai/Msds?"},
          {"SERVLET_DIR_WWW",""},
          {"ERW_TEMPLATE_DIR","/webdata/html/template/"},
          {"BASE_DIR_TCM_WWW","/webdata/html/"},
          {"ERW_REPORT_DIR_ACTIVE","/webdata/html/reports/temp/"},
          {"ERW_REPORT_DIR_BATCH","/webdata/html/reports/"},
          {"WEBS_HOME_WWW_ERW_ACTIVE","/reports/temp/"},
          {"WEBS_HOME_WWW_ERW_BATCH","/reports/"},
          {"CATALOGING_SERVLET","/tcmIS/bai/cataloging?"},
          {"PRINT_SCREEN","/tcmIS/bai/printscreen"},
          {"HELP_SERVLET_PATH","/tcmIS/client/help"},

          {"DROPRECEIVING","No"},
          {"RELABELING","No"},
          {"DROPRECEIVING_SERVLET","/tcmIS/bai/dropshipreceivingmain.do"},
          {"DROPRECEIVING_SERVLET_HEADER","/tcmIS/bai/dropshipreceivingmain.do"},
          {"RELABELING_SERVLET","/tcmIS/bai/Relabeling?session=Active"},

		  {"COST_REPORT","/tcmIS/bai/betacostreport?"},
		  {"SCHEDULDE_DELIVERY","/tcmIS/bai/schdelivery?"},
		  {"CANCEL_MR","/tcmIS/bai/cancel?"},
		  {"WASTE_ORDER","/tcmIS/bai/wasteorder?"},
		  {"WASTE_ADHOC_REPORT","/tcmIS/bai/wastereport?"},
		  {"USAGE_ADHOC_REPORT","/tcmIS/bai/usagereport?"},
		  {"MATERIAL_MATRIX_REPORT","/tcmIS/bai/matrixreport?"},

		  {"SHIPPINGINFO","No"},
		  {"SHIPPINGINFO_SERVLET","/tcmIS/bai/shipinfo?session=Active"},
		  {"SHIPPINGINFO_SERVLET_HEADER","/tcmIS/bai/shipinfo"},

                  {"WEB_ORDER_TRACKING","Yes"},
                  {"WEB_ORDER_TRACKING_SERVLET","/tcmIS/bai/ordertrackingmain.do"},
                  {"WEB_INVENTORY","Yes"},
                  {"WEB_INVENTORY_SERVLET","/tcmIS/bai/inventorymain.do"},
                  {"WEB_CATALOG","Yes"},
                  {"WEB_CATALOG_SERVLET","/tcmIS/bai/catalog.do"},

           {"WEB_REGISTRATION","Yes"},
			{"NEW_WEB_APPLICATION","Yes"}

	  };

     public BAIServerResourceBundle(){
            super();
            addHash(cHash);
     }

}