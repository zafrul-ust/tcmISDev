package radian.web.servlets.goodrich;

import radian.tcmis.server7.share.helpers.ServerResourceBundle;

public class GoodrichServerResourceBundle  extends ServerResourceBundle {

     static final Object[][] cHash = {
          {"LOG_DIR","/logsUTC"},
          {"DEBUG","false"},
          {"DB_CLIENT","Goodrich"},
          {"ORACLE_DRIVER",""},
          {"ORACLE_URL",""},
          {"DB_USER",""},

          {"RELATIVE_HTML","/tcmIS/msds/goodrich"},
          {"DB_PASS",""},
          {"DB_CLIENT_NAME","Goodrich"},
          {"WEBS_HOME_WWWS",""},
          {"WEBS_HOME_WWW",""},

          {"HOME_PAGE","/tcmIS/goodrich/home.do"},
          {"HOME_IMAGE","/images/goodrich.gif"},
          {"HELP_HTML_PATH","/tcmIS/help/new/"},

          {"VIEW_MSDS","/tcmIS/goodrich/ViewMsds?"},
          {"REGISTER_SERVLET","/tcmIS/goodrich/Register"},
          {"HOME_SERVLET","/tcmIS/goodrich/home.do"},
          {"JNLP_MAINCLASS","RadianCCGoodrich.jar"},
          {"JNLP_DIRECTORY","/jnlp/goodrich"},
          {"TEST_JNLP_DIRECTORY","/prereleasejnlp/goodrich"},
          {"TCMIS_START_CLASS","radian.tcmis.client.client.goodrich.gui.GoodrichCmisApp"},
          {"BATCH_REPORT_SERVLET","/tcmIS/goodrich/batchreport?"},

          {"ADDUSER_SERVLET","/tcmIS/goodrich/Register?useraction=AddUser"},
          {"CHGUSER_SERVLET","/tcmIS/goodrich/Register?useraction=ChgUser"},
          {"ADD_SERVLET","/tcmIS/goodrich/Register?useraction=Add"},
          {"LOGINREG_SERVLET","/tcmIS/goodrich/Register?useraction=Login"},
          {"CHANGE_SERVLET","/tcmIS/goodrich/Register?useraction=Change"},
          {"REGISTER_HOME_SERVLET","/tcmIS/goodrich/Register?"},
          {"TEST_INSTALL_SERVLET","/tcmIS/goodrich/Register?useraction=startingtcmis"},
          {"BAR_BUILD_SERVLET","/tcmIS/goodrich/Msds?limit=yes"},
          {"BUILD_SERVLET","/tcmIS/goodrich/Msds?"},
          {"SERVLET_DIR_WWW",""},
          {"ERW_TEMPLATE_DIR","/webdata/html/template/"},
          {"BASE_DIR_TCM_WWW","/webdata/html/"},
          {"ERW_REPORT_DIR_ACTIVE","/webdata/html/reports/temp/"},
          {"ERW_REPORT_DIR_BATCH","/webdata/html/reports/"},
          {"WEBS_HOME_WWW_ERW_ACTIVE","/reports/temp/"},
          {"WEBS_HOME_WWW_ERW_BATCH","/reports/"},
          {"CATALOGING_SERVLET","/tcmIS/goodrich/cataloging?"},
          {"PRINT_SCREEN","/tcmIS/goodrich/printscreen"},
          {"HELP_SERVLET_PATH","/tcmIS/client/help"},

          {"DROPRECEIVING","No"},
          {"RELABELING","No"},
          {"DROPRECEIVING_SERVLET","/tcmIS/goodrich/dropshipreceivingmain.do"},
          {"DROPRECEIVING_SERVLET_HEADER","/tcmIS/goodrich/dropshipreceivingmain.do"},
          {"RELABELING_SERVLET","/tcmIS/goodrich/Relabeling?session=Active"},

		  {"COST_REPORT","/tcmIS/goodrich/betacostreport?"},
		  {"SCHEDULDE_DELIVERY","/tcmIS/goodrich/schdelivery?"},
		  {"CANCEL_MR","/tcmIS/goodrich/cancel?"},
		  {"WASTE_ORDER","/tcmIS/goodrich/wasteorder?"},
		  {"WASTE_ADHOC_REPORT","/tcmIS/goodrich/wastereport?"},
		  {"USAGE_ADHOC_REPORT","/tcmIS/goodrich/usagereport?"},
		  {"MATERIAL_MATRIX_REPORT","/tcmIS/goodrich/matrixreport?"},

		  {"SHIPPINGINFO","No"},
		  {"SHIPPINGINFO_SERVLET","/tcmIS/goodrich/shipinfo?session=Active"},
		  {"SHIPPINGINFO_SERVLET_HEADER","/tcmIS/goodrich/shipinfo"},

                  {"WEB_ORDER_TRACKING","Yes"},
                  {"WEB_ORDER_TRACKING_SERVLET","/tcmIS/goodrich/ordertrackingmain.do"},
                  {"WEB_INVENTORY","Yes"},
                  {"WEB_INVENTORY_SERVLET","/tcmIS/goodrich/inventorymain.do"},
                  {"WEB_CATALOG","Yes"},
                  {"WEB_CATALOG_SERVLET","/tcmIS/goodrich/catalog.do"},

           {"WEB_REGISTRATION","Yes"},
			{"NEW_WEB_APPLICATION","Yes"}

	  };

     public GoodrichServerResourceBundle(){
            super();
            addHash(cHash);
     }

}