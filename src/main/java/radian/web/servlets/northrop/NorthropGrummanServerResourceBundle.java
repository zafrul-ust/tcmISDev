package radian.web.servlets.northrop;

import radian.tcmis.server7.share.helpers.ServerResourceBundle;

public class NorthropGrummanServerResourceBundle  extends ServerResourceBundle {

     static final Object[][] cHash = {
          {"LOG_DIR","/logsUTC"},
          {"DEBUG","false"},
          {"DB_CLIENT","NORTHROP_GRUMMAN"},
          {"ORACLE_DRIVER",""},
          {"ORACLE_URL",""},
          {"DB_USER",""},

          {"RELATIVE_HTML","/tcmIS/msds/northrop"},
          {"DB_PASS",""},
          {"DB_CLIENT_NAME","NORTHROP_GRUMMAN"},
          {"WEBS_HOME_WWWS",""},
          {"WEBS_HOME_WWW",""},

          {"HOME_PAGE","/tcmIS/northrop/home.do"},
          {"HOME_IMAGE","/images/northrop.gif"},
          {"HELP_HTML_PATH","/tcmIS/help/new/"},

          {"VIEW_MSDS","/tcmIS/northrop/ViewMsds?"},
          {"REGISTER_SERVLET","/tcmIS/northrop/Register"},
          {"HOME_SERVLET","/tcmIS/northrop/home.do"},
          {"JNLP_MAINCLASS","RadianCCNorthropGrumman.jar"},
          {"JNLP_DIRECTORY","/jnlp/northrop"},
          {"TEST_JNLP_DIRECTORY","/prereleasejnlp/northrop"},
          {"TCMIS_START_CLASS","radian.tcmis.client.client.northrop.gui.NorthropGrummanCmisApp"},
          {"BATCH_REPORT_SERVLET","/tcmIS/northrop/batchreport?"},

          {"ADDUSER_SERVLET","/tcmIS/northrop/Register?useraction=AddUser"},
          {"CHGUSER_SERVLET","/tcmIS/northrop/Register?useraction=ChgUser"},
          {"ADD_SERVLET","/tcmIS/northrop/Register?useraction=Add"},
          {"LOGINREG_SERVLET","/tcmIS/northrop/Register?useraction=Login"},
          {"CHANGE_SERVLET","/tcmIS/northrop/Register?useraction=Change"},
          {"REGISTER_HOME_SERVLET","/tcmIS/northrop/Register?"},
          {"TEST_INSTALL_SERVLET","/tcmIS/northrop/Register?useraction=startingtcmis"},
          {"BAR_BUILD_SERVLET","/tcmIS/northrop/Msds?limit=yes"},
          {"BUILD_SERVLET","/tcmIS/northrop/Msds?"},
          {"SERVLET_DIR_WWW",""},
          {"ERW_TEMPLATE_DIR","/webdata/html/template/"},
          {"BASE_DIR_TCM_WWW","/webdata/html/"},
          {"ERW_REPORT_DIR_ACTIVE","/webdata/html/reports/temp/"},
          {"ERW_REPORT_DIR_BATCH","/webdata/html/reports/"},
          {"WEBS_HOME_WWW_ERW_ACTIVE","/reports/temp/"},
          {"WEBS_HOME_WWW_ERW_BATCH","/reports/"},
          {"CATALOGING_SERVLET","/tcmIS/northrop/cataloging?"},
          {"PRINT_SCREEN","/tcmIS/northrop/printscreen"},
          {"HELP_SERVLET_PATH","/tcmIS/client/help"},

          {"DROPRECEIVING","No"},
          {"RELABELING","No"},
          {"DROPRECEIVING_SERVLET","/tcmIS/northrop/dropshipreceivingmain.do"},
          {"DROPRECEIVING_SERVLET_HEADER","/tcmIS/northrop/dropshipreceivingmain.do"},
          {"RELABELING_SERVLET","/tcmIS/northrop/Relabeling?session=Active"},

		  {"COST_REPORT","/tcmIS/northrop/betacostreport?"},
		  {"SCHEDULDE_DELIVERY","/tcmIS/northrop/schdelivery?"},
		  {"CANCEL_MR","/tcmIS/northrop/cancel?"},
		  {"WASTE_ORDER","/tcmIS/northrop/wasteorder?"},
		  {"WASTE_ADHOC_REPORT","/tcmIS/northrop/wastereport?"},
		  {"USAGE_ADHOC_REPORT","/tcmIS/northrop/usagereport?"},
		  {"MATERIAL_MATRIX_REPORT","/tcmIS/northrop/matrixreport?"},

		  {"SHIPPINGINFO","No"},
		  {"SHIPPINGINFO_SERVLET","/tcmIS/northrop/shipinfo?session=Active"},
		  {"SHIPPINGINFO_SERVLET_HEADER","/tcmIS/northrop/shipinfo"},

                  {"WEB_ORDER_TRACKING","Yes"},
                  {"WEB_ORDER_TRACKING_SERVLET","/tcmIS/northrop/ordertrackingmain.do"},
                  {"WEB_INVENTORY","Yes"},
                  {"WEB_INVENTORY_SERVLET","/tcmIS/northrop/inventorymain.do"},
                  {"WEB_CATALOG","Yes"},
                  {"WEB_CATALOG_SERVLET","/tcmIS/northrop/catalog.do"},

           {"WEB_REGISTRATION","Yes"},
			{"NEW_WEB_APPLICATION","Yes"}

	  };

     public NorthropGrummanServerResourceBundle(){
            super();
            addHash(cHash);
     }

}