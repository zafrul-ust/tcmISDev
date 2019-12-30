package radian.web.servlets.avcorp;

import radian.tcmis.server7.share.helpers.ServerResourceBundle;

public class AvcorpServerResourceBundle  extends ServerResourceBundle {

     static final Object[][] cHash = {
          {"LOG_DIR","/logsUTC"},
          {"DEBUG","false"},
          {"DB_CLIENT","Avcorp"},
          {"ORACLE_DRIVER",""},
          {"ORACLE_URL",""},
          {"DB_USER",""},

          {"RELATIVE_HTML","/tcmIS/msds/avcorp"},
          {"DB_PASS",""},
          {"DB_CLIENT_NAME","Avcorp"},
          {"WEBS_HOME_WWWS",""},
          {"WEBS_HOME_WWW",""},

          {"HOME_PAGE","/tcmIS/avcorp/home.do"},
          {"HOME_IMAGE","/images/avcorp.gif"},
          {"HELP_HTML_PATH","/tcmIS/help/new/"},

          {"VIEW_MSDS","/tcmIS/avcorp/ViewMsds?"},
          {"REGISTER_SERVLET","/tcmIS/avcorp/Register"},
          {"HOME_SERVLET","/tcmIS/avcorp/home.do"},
          {"JNLP_MAINCLASS","RadianCCAvcorp.jar"},
          {"JNLP_DIRECTORY","/jnlp/avcorp"},
          {"TEST_JNLP_DIRECTORY","/prereleasejnlp/avcorp"},
          {"TCMIS_START_CLASS","radian.tcmis.client.client.avcorp.gui.AvcorpCmisApp"},
          {"BATCH_REPORT_SERVLET","/tcmIS/avcorp/batchreport?"},

          {"ADDUSER_SERVLET","/tcmIS/avcorp/Register?useraction=AddUser"},
          {"CHGUSER_SERVLET","/tcmIS/avcorp/Register?useraction=ChgUser"},
          {"ADD_SERVLET","/tcmIS/avcorp/Register?useraction=Add"},
          {"LOGINREG_SERVLET","/tcmIS/avcorp/Register?useraction=Login"},
          {"CHANGE_SERVLET","/tcmIS/avcorp/Register?useraction=Change"},
          {"REGISTER_HOME_SERVLET","/tcmIS/avcorp/Register?"},
          {"TEST_INSTALL_SERVLET","/tcmIS/avcorp/Register?useraction=startingtcmis"},
          {"BAR_BUILD_SERVLET","/tcmIS/avcorp/Msds?limit=yes"},
          {"BUILD_SERVLET","/tcmIS/avcorp/Msds?"},
          {"SERVLET_DIR_WWW",""},
          {"ERW_TEMPLATE_DIR","/webdata/html/template/"},
          {"BASE_DIR_TCM_WWW","/webdata/html/"},
          {"ERW_REPORT_DIR_ACTIVE","/webdata/html/reports/temp/"},
          {"ERW_REPORT_DIR_BATCH","/webdata/html/reports/"},
          {"WEBS_HOME_WWW_ERW_ACTIVE","/reports/temp/"},
          {"WEBS_HOME_WWW_ERW_BATCH","/reports/"},
          {"CATALOGING_SERVLET","/tcmIS/avcorp/cataloging?"},
          {"PRINT_SCREEN","/tcmIS/avcorp/printscreen"},
          {"HELP_SERVLET_PATH","/tcmIS/client/help"},

          {"DROPRECEIVING","No"},
          {"RELABELING","No"},
          {"DROPRECEIVING_SERVLET","/tcmIS/avcorp/dropshipreceivingmain.do"},
          {"DROPRECEIVING_SERVLET_HEADER","/tcmIS/avcorp/dropshipreceivingmain.do"},
          {"RELABELING_SERVLET","/tcmIS/avcorp/Relabeling?session=Active"},

		  {"COST_REPORT","/tcmIS/avcorp/betacostreport?"},
		  {"SCHEDULDE_DELIVERY","/tcmIS/avcorp/schdelivery?"},
		  {"CANCEL_MR","/tcmIS/avcorp/cancel?"},
		  {"WASTE_ORDER","/tcmIS/avcorp/wasteorder?"},
		  {"WASTE_ADHOC_REPORT","/tcmIS/avcorp/wastereport?"},
		  {"USAGE_ADHOC_REPORT","/tcmIS/avcorp/usagereport?"},
		  {"MATERIAL_MATRIX_REPORT","/tcmIS/avcorp/matrixreport?"},

		  {"SHIPPINGINFO","No"},
		  {"SHIPPINGINFO_SERVLET","/tcmIS/avcorp/shipinfo?session=Active"},
		  {"SHIPPINGINFO_SERVLET_HEADER","/tcmIS/avcorp/shipinfo"},

                  {"WEB_ORDER_TRACKING","Yes"},
                  {"WEB_ORDER_TRACKING_SERVLET","/tcmIS/avcorp/ordertrackingmain.do"},
                  {"WEB_INVENTORY","Yes"},
                  {"WEB_INVENTORY_SERVLET","/tcmIS/avcorp/inventorymain.do"},
                  {"WEB_CATALOG","Yes"},
                  {"WEB_CATALOG_SERVLET","/tcmIS/avcorp/catalog.do"},

           {"WEB_REGISTRATION","Yes"},
			{"NEW_WEB_APPLICATION","Yes"}

	  };

     public AvcorpServerResourceBundle(){
            super();
            addHash(cHash);
     }

}