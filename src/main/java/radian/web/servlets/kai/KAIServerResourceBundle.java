package radian.web.servlets.kai;

import radian.tcmis.server7.share.helpers.ServerResourceBundle;

public class KAIServerResourceBundle  extends ServerResourceBundle {

     static final Object[][] cHash = {
          {"LOG_DIR","/logsUTC"},
          {"DEBUG","false"},
          {"DB_CLIENT","KOREA_AEROSPACE_INDUSTRIES"},
          {"ORACLE_DRIVER",""},
          {"ORACLE_URL",""},
          {"DB_USER",""},

          {"RELATIVE_HTML","/tcmIS/msds/kai"},
          {"DB_PASS",""},
          {"DB_CLIENT_NAME","KOREA_AEROSPACE_INDUSTRIES"},
          {"WEBS_HOME_WWWS",""},
          {"WEBS_HOME_WWW",""},

          {"HOME_PAGE","/tcmIS/kai/home.do"},
          {"HOME_IMAGE","/images/kai.gif"},
          {"HELP_HTML_PATH","/tcmIS/help/new/"},

          {"VIEW_MSDS","/tcmIS/kai/ViewMsds?"},
          {"REGISTER_SERVLET","/tcmIS/kai/Register"},
          {"HOME_SERVLET","/tcmIS/kai/home.do"},
          {"JNLP_MAINCLASS","RadianCCKAI.jar"},
          {"JNLP_DIRECTORY","/jnlp/kai"},
          {"TEST_JNLP_DIRECTORY","/prereleasejnlp/kai"},
          {"TCMIS_START_CLASS","radian.tcmis.client.client.kai.gui.KAICmisApp"},
          {"BATCH_REPORT_SERVLET","/tcmIS/kai/batchreport?"},

          {"ADDUSER_SERVLET","/tcmIS/kai/Register?useraction=AddUser"},
          {"CHGUSER_SERVLET","/tcmIS/kai/Register?useraction=ChgUser"},
          {"ADD_SERVLET","/tcmIS/kai/Register?useraction=Add"},
          {"LOGINREG_SERVLET","/tcmIS/kai/Register?useraction=Login"},
          {"CHANGE_SERVLET","/tcmIS/kai/Register?useraction=Change"},
          {"REGISTER_HOME_SERVLET","/tcmIS/kai/Register?"},
          {"TEST_INSTALL_SERVLET","/tcmIS/kai/Register?useraction=startingtcmis"},
          {"BAR_BUILD_SERVLET","/tcmIS/kai/Msds?limit=yes"},
          {"BUILD_SERVLET","/tcmIS/kai/Msds?"},
          {"SERVLET_DIR_WWW",""},
          {"ERW_TEMPLATE_DIR","/webdata/html/template/"},
          {"BASE_DIR_TCM_WWW","/webdata/html/"},
          {"ERW_REPORT_DIR_ACTIVE","/webdata/html/reports/temp/"},
          {"ERW_REPORT_DIR_BATCH","/webdata/html/reports/"},
          {"WEBS_HOME_WWW_ERW_ACTIVE","/reports/temp/"},
          {"WEBS_HOME_WWW_ERW_BATCH","/reports/"},
          {"CATALOGING_SERVLET","/tcmIS/kai/cataloging?"},
          {"PRINT_SCREEN","/tcmIS/kai/printscreen"},
          {"HELP_SERVLET_PATH","/tcmIS/client/help"},

          {"DROPRECEIVING","No"},
          {"RELABELING","No"},
          {"DROPRECEIVING_SERVLET","/tcmIS/kai/dropshipreceivingmain.do"},
          {"DROPRECEIVING_SERVLET_HEADER","/tcmIS/kai/dropshipreceivingmain.do"},
          {"RELABELING_SERVLET","/tcmIS/kai/Relabeling?session=Active"},

		  {"COST_REPORT","/tcmIS/kai/betacostreport?"},
		  {"SCHEDULDE_DELIVERY","/tcmIS/kai/schdelivery?"},
		  {"CANCEL_MR","/tcmIS/kai/cancel?"},
		  {"WASTE_ORDER","/tcmIS/kai/wasteorder?"},
		  {"WASTE_ADHOC_REPORT","/tcmIS/kai/wastereport?"},
		  {"USAGE_ADHOC_REPORT","/tcmIS/kai/usagereport?"},
		  {"MATERIAL_MATRIX_REPORT","/tcmIS/kai/matrixreport?"},

		  {"SHIPPINGINFO","No"},
		  {"SHIPPINGINFO_SERVLET","/tcmIS/kai/shipinfo?session=Active"},
		  {"SHIPPINGINFO_SERVLET_HEADER","/tcmIS/kai/shipinfo"},

                  {"WEB_ORDER_TRACKING","Yes"},
                  {"WEB_ORDER_TRACKING_SERVLET","/tcmIS/kai/ordertrackingmain.do"},
                  {"WEB_INVENTORY","Yes"},
                  {"WEB_INVENTORY_SERVLET","/tcmIS/kai/inventorymain.do"},
                  {"WEB_CATALOG","Yes"},
                  {"WEB_CATALOG_SERVLET","/tcmIS/kai/catalog.do"},

           {"WEB_REGISTRATION","Yes"},
			{"NEW_WEB_APPLICATION","Yes"}

	  };

     public KAIServerResourceBundle(){
            super();
            addHash(cHash);
     }

}