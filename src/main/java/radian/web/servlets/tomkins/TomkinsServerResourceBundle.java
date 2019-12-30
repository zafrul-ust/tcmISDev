package radian.web.servlets.tomkins;

import radian.tcmis.server7.share.helpers.ServerResourceBundle;

public class TomkinsServerResourceBundle  extends ServerResourceBundle {

     static final Object[][] cHash = {
          {"LOG_DIR","/logsUTC"},
          {"DEBUG","false"},
          {"DB_CLIENT","TOMKINS"},
          {"ORACLE_DRIVER",""},
          {"ORACLE_URL",""},
          {"DB_USER",""},

          {"RELATIVE_HTML","/tcmIS/msds/tomkins"},
          {"DB_PASS",""},
          {"DB_CLIENT_NAME","TOMKINS"},
          {"WEBS_HOME_WWWS",""},
          {"WEBS_HOME_WWW",""},

          {"HOME_PAGE","/tcmIS/tomkins/home.do"},
          {"HOME_IMAGE","/images/tomkins.gif"},
          {"HELP_HTML_PATH","/tcmIS/help/new/"},

          {"VIEW_MSDS","/tcmIS/tomkins/ViewMsds?"},
          {"REGISTER_SERVLET","/tcmIS/tomkins/Register"},
          {"HOME_SERVLET","/tcmIS/tomkins/home.do"},
          {"JNLP_MAINCLASS","RadianCCTOMKINS.jar"},
          {"JNLP_DIRECTORY","/jnlp/tomkins"},
          {"TEST_JNLP_DIRECTORY","/prereleasejnlp/tomkins"},
          {"TCMIS_START_CLASS","radian.tcmis.client.client.tomkins.gui.TOMKINSCmisApp"},
          {"BATCH_REPORT_SERVLET","/tcmIS/tomkins/batchreport?"},

          {"ADDUSER_SERVLET","/tcmIS/tomkins/Register?useraction=AddUser"},
          {"CHGUSER_SERVLET","/tcmIS/tomkins/Register?useraction=ChgUser"},
          {"ADD_SERVLET","/tcmIS/tomkins/Register?useraction=Add"},
          {"LOGINREG_SERVLET","/tcmIS/tomkins/Register?useraction=Login"},
          {"CHANGE_SERVLET","/tcmIS/tomkins/Register?useraction=Change"},
          {"REGISTER_HOME_SERVLET","/tcmIS/tomkins/Register?"},
          {"TEST_INSTALL_SERVLET","/tcmIS/tomkins/Register?useraction=startingtcmis"},
          {"BAR_BUILD_SERVLET","/tcmIS/tomkins/Msds?limit=yes"},
          {"BUILD_SERVLET","/tcmIS/tomkins/Msds?"},
          {"SERVLET_DIR_WWW",""},
          {"ERW_TEMPLATE_DIR","/webdata/html/template/"},
          {"BASE_DIR_TCM_WWW","/webdata/html/"},
          {"ERW_REPORT_DIR_ACTIVE","/webdata/html/reports/temp/"},
          {"ERW_REPORT_DIR_BATCH","/webdata/html/reports/"},
          {"WEBS_HOME_WWW_ERW_ACTIVE","/reports/temp/"},
          {"WEBS_HOME_WWW_ERW_BATCH","/reports/"},
          {"CATALOGING_SERVLET","/tcmIS/tomkins/cataloging?"},
          {"PRINT_SCREEN","/tcmIS/tomkins/printscreen"},
          {"HELP_SERVLET_PATH","/tcmIS/client/help"},

          {"DROPRECEIVING","No"},
          {"RELABELING","No"},
          {"DROPRECEIVING_SERVLET","/tcmIS/tomkins/dropshipreceivingmain.do"},
          {"DROPRECEIVING_SERVLET_HEADER","/tcmIS/tomkins/dropshipreceivingmain.do"},
          {"RELABELING_SERVLET","/tcmIS/tomkins/Relabeling?session=Active"},

		  {"COST_REPORT","/tcmIS/tomkins/betacostreport?"},
		  {"SCHEDULDE_DELIVERY","/tcmIS/tomkins/schdelivery?"},
		  {"CANCEL_MR","/tcmIS/tomkins/cancel?"},
		  {"WASTE_ORDER","/tcmIS/tomkins/wasteorder?"},
		  {"WASTE_ADHOC_REPORT","/tcmIS/tomkins/wastereport?"},
		  {"USAGE_ADHOC_REPORT","/tcmIS/tomkins/usagereport?"},
		  {"MATERIAL_MATRIX_REPORT","/tcmIS/tomkins/matrixreport?"},

		  {"SHIPPINGINFO","No"},
		  {"SHIPPINGINFO_SERVLET","/tcmIS/tomkins/shipinfo?session=Active"},
		  {"SHIPPINGINFO_SERVLET_HEADER","/tcmIS/tomkins/shipinfo"},

                  {"WEB_ORDER_TRACKING","Yes"},
                  {"WEB_ORDER_TRACKING_SERVLET","/tcmIS/tomkins/ordertrackingmain.do"},
                  {"WEB_INVENTORY","Yes"},
                  {"WEB_INVENTORY_SERVLET","/tcmIS/tomkins/inventorymain.do"},
                  {"WEB_CATALOG","Yes"},
                  {"WEB_CATALOG_SERVLET","/tcmIS/tomkins/catalog.do"},

           {"WEB_REGISTRATION","Yes"},
			{"NEW_WEB_APPLICATION","Yes"}

	  };

     public TomkinsServerResourceBundle(){
            super();
            addHash(cHash);
     }

}