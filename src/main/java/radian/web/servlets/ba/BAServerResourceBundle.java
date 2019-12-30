package radian.web.servlets.ba;

import radian.tcmis.server7.share.helpers.ServerResourceBundle;

public class BAServerResourceBundle  extends ServerResourceBundle {

     static final Object[][] cHash = {
          {"LOG_DIR","/logsUTC"},
          {"DEBUG","false"},
          {"DB_CLIENT","BA"},
          {"ORACLE_DRIVER",""},
          {"ORACLE_URL",""},
          {"DB_USER",""},

          {"RELATIVE_HTML","/tcmIS/msds/ba"},
          {"DB_PASS",""},
          {"DB_CLIENT_NAME","BA"},
          {"WEBS_HOME_WWWS",""},
          {"WEBS_HOME_WWW",""},

          {"HOME_PAGE","/tcmIS/ba/home.do"},
          {"HOME_IMAGE","/images/ba.gif"},
          {"HELP_HTML_PATH","/tcmIS/help/new/"},

          {"VIEW_MSDS","/tcmIS/ba/ViewMsds?"},
          {"REGISTER_SERVLET","/tcmIS/ba/Register"},
          {"HOME_SERVLET","/tcmIS/ba/home.do"},
          {"JNLP_MAINCLASS","RadianCCBA.jar"},
          {"JNLP_DIRECTORY","/jnlp/ba"},
          {"TEST_JNLP_DIRECTORY","/prereleasejnlp/ba"},
          {"TCMIS_START_CLASS","radian.tcmis.client.client.ba.gui.BACmisApp"},
          {"BATCH_REPORT_SERVLET","/tcmIS/ba/batchreport?"},

          {"ADDUSER_SERVLET","/tcmIS/ba/Register?useraction=AddUser"},
          {"CHGUSER_SERVLET","/tcmIS/ba/Register?useraction=ChgUser"},
          {"ADD_SERVLET","/tcmIS/ba/Register?useraction=Add"},
          {"LOGINREG_SERVLET","/tcmIS/ba/Register?useraction=Login"},
          {"CHANGE_SERVLET","/tcmIS/ba/Register?useraction=Change"},
          {"REGISTER_HOME_SERVLET","/tcmIS/ba/Register?"},
          {"TEST_INSTALL_SERVLET","/tcmIS/ba/Register?useraction=startingtcmis"},
          {"BAR_BUILD_SERVLET","/tcmIS/ba/Msds?limit=yes"},
          {"BUILD_SERVLET","/tcmIS/ba/Msds?"},
          {"SERVLET_DIR_WWW",""},
          {"ERW_TEMPLATE_DIR","/webdata/html/template/"},
          {"BASE_DIR_TCM_WWW","/webdata/html/"},
          {"ERW_REPORT_DIR_ACTIVE","/webdata/html/reports/temp/"},
          {"ERW_REPORT_DIR_BATCH","/webdata/html/reports/"},
          {"WEBS_HOME_WWW_ERW_ACTIVE","/reports/temp/"},
          {"WEBS_HOME_WWW_ERW_BATCH","/reports/"},
          {"CATALOGING_SERVLET","/tcmIS/ba/cataloging?"},
          {"PRINT_SCREEN","/tcmIS/ba/printscreen"},
          {"HELP_SERVLET_PATH","/tcmIS/client/help"},

          {"DROPRECEIVING","No"},
          {"RELABELING","No"},
          {"DROPRECEIVING_SERVLET","/tcmIS/ba/dropshipreceivingmain.do"},
          {"DROPRECEIVING_SERVLET_HEADER","/tcmIS/ba/dropshipreceivingmain.do"},
          {"RELABELING_SERVLET","/tcmIS/ba/Relabeling?session=Active"},

		  {"COST_REPORT","/tcmIS/ba/betacostreport?"},
		  {"SCHEDULDE_DELIVERY","/tcmIS/ba/schdelivery?"},
		  {"CANCEL_MR","/tcmIS/ba/cancel?"},
		  {"WASTE_ORDER","/tcmIS/ba/wasteorder?"},
		  {"WASTE_ADHOC_REPORT","/tcmIS/ba/wastereport?"},
		  {"USAGE_ADHOC_REPORT","/tcmIS/ba/usagereport?"},
		  {"MATERIAL_MATRIX_REPORT","/tcmIS/ba/matrixreport?"},

		  {"SHIPPINGINFO","No"},
		  {"SHIPPINGINFO_SERVLET","/tcmIS/ba/shipinfo?session=Active"},
		  {"SHIPPINGINFO_SERVLET_HEADER","/tcmIS/ba/shipinfo"},

                  {"WEB_ORDER_TRACKING","Yes"},
                  {"WEB_ORDER_TRACKING_SERVLET","/tcmIS/ba/ordertrackingmain.do"},
                  {"WEB_INVENTORY","Yes"},
                  {"WEB_INVENTORY_SERVLET","/tcmIS/ba/inventorymain.do"},
                  {"WEB_CATALOG","Yes"},
                  {"WEB_CATALOG_SERVLET","/tcmIS/ba/catalog.do"},

           {"WEB_REGISTRATION","Yes"},
			{"NEW_WEB_APPLICATION","Yes"}

	  };

     public BAServerResourceBundle(){
            super();
            addHash(cHash);
     }

}