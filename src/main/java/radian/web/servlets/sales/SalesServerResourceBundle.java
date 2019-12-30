package radian.web.servlets.sales;

import radian.tcmis.server7.share.helpers.ServerResourceBundle;

public class SalesServerResourceBundle  extends ServerResourceBundle {

     static final Object[][] cHash = {
          {"LOG_DIR","/logsUTC"},
          {"DEBUG","false"},
          {"DB_CLIENT","Sales"},
          {"ORACLE_DRIVER",""},
          {"ORACLE_URL",""},
          {"DB_USER",""},

          {"RELATIVE_HTML","/tcmIS/msds/sales"},
          {"DB_PASS",""},
          {"DB_CLIENT_NAME","Sales"},
          {"WEBS_HOME_WWWS",""},
          {"WEBS_HOME_WWW",""},

          {"HOME_PAGE","/tcmIS/sales/home.do"},
          {"HOME_IMAGE","/images/sales.gif"},
          {"HELP_HTML_PATH","/tcmIS/help/new/"},

          {"VIEW_MSDS","/tcmIS/sales/ViewMsds?"},
          {"REGISTER_SERVLET","/tcmIS/sales/Register"},
          {"HOME_SERVLET","/tcmIS/sales/home.do"},
          {"JNLP_MAINCLASS","RadianCCSales.jar"},
          {"JNLP_DIRECTORY","/jnlp/sales"},
          {"TEST_JNLP_DIRECTORY","/prereleasejnlp/sales"},
          {"TCMIS_START_CLASS","radian.tcmis.client.client.sales.gui.SalesCmisApp"},
          {"BATCH_REPORT_SERVLET","/tcmIS/sales/batchreport?"},

          {"ADDUSER_SERVLET","/tcmIS/sales/Register?useraction=AddUser"},
          {"CHGUSER_SERVLET","/tcmIS/sales/Register?useraction=ChgUser"},
          {"ADD_SERVLET","/tcmIS/sales/Register?useraction=Add"},
          {"LOGINREG_SERVLET","/tcmIS/sales/Register?useraction=Login"},
          {"CHANGE_SERVLET","/tcmIS/sales/Register?useraction=Change"},
          {"REGISTER_HOME_SERVLET","/tcmIS/sales/Register?"},
          {"TEST_INSTALL_SERVLET","/tcmIS/sales/Register?useraction=startingtcmis"},
          {"BAR_BUILD_SERVLET","/tcmIS/sales/Msds?limit=yes"},
          {"BUILD_SERVLET","/tcmIS/sales/Msds?"},
          {"SERVLET_DIR_WWW",""},
          {"ERW_TEMPLATE_DIR","/webdata/html/template/"},
          {"BASE_DIR_TCM_WWW","/webdata/html/"},
          {"ERW_REPORT_DIR_ACTIVE","/webdata/html/reports/temp/"},
          {"ERW_REPORT_DIR_BATCH","/webdata/html/reports/"},
          {"WEBS_HOME_WWW_ERW_ACTIVE","/reports/temp/"},
          {"WEBS_HOME_WWW_ERW_BATCH","/reports/"},
          {"CATALOGING_SERVLET","/tcmIS/sales/cataloging?"},
          {"PRINT_SCREEN","/tcmIS/sales/printscreen"},
          {"HELP_SERVLET_PATH","/tcmIS/client/help"},

          {"DROPRECEIVING","No"},
          {"RELABELING","No"},
          {"DROPRECEIVING_SERVLET","/tcmIS/sales/dropshipreceivingmain.do"},
          {"DROPRECEIVING_SERVLET_HEADER","/tcmIS/sales/dropshipreceivingmain.do"},
          {"RELABELING_SERVLET","/tcmIS/sales/Relabeling?session=Active"},

		  {"COST_REPORT","/tcmIS/sales/betacostreport?"},
		  {"SCHEDULDE_DELIVERY","/tcmIS/sales/schdelivery?"},
		  {"CANCEL_MR","/tcmIS/sales/cancel?"},
		  {"WASTE_ORDER","/tcmIS/sales/wasteorder?"},
		  {"WASTE_ADHOC_REPORT","/tcmIS/sales/wastereport?"},
		  {"USAGE_ADHOC_REPORT","/tcmIS/sales/usagereport?"},
		  {"MATERIAL_MATRIX_REPORT","/tcmIS/sales/matrixreport?"},

		  {"SHIPPINGINFO","No"},
		  {"SHIPPINGINFO_SERVLET","/tcmIS/sales/shipinfo?session=Active"},
		  {"SHIPPINGINFO_SERVLET_HEADER","/tcmIS/sales/shipinfo"},

                  {"WEB_ORDER_TRACKING","Yes"},
                  {"WEB_ORDER_TRACKING_SERVLET","/tcmIS/sales/ordertrackingmain.do"},
                  {"WEB_INVENTORY","Yes"},
                  {"WEB_INVENTORY_SERVLET","/tcmIS/sales/inventorymain.do"},
                  {"WEB_CATALOG","Yes"},
                  {"WEB_CATALOG_SERVLET","/tcmIS/sales/catalog.do"},

           {"WEB_REGISTRATION","Yes"},
			{"NEW_WEB_APPLICATION","Yes"}

	  };

     public SalesServerResourceBundle(){
            super();
            addHash(cHash);
     }

}