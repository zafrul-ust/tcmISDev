package radian.web.servlets.orlite;

import radian.tcmis.server7.share.helpers.ServerResourceBundle;

public class OrliteServerResourceBundle  extends ServerResourceBundle {

     static final Object[][] cHash = {
          {"LOG_DIR","/logsUTC"},
          {"DEBUG","false"},
          {"DB_CLIENT","Orlite"},
          {"ORACLE_DRIVER",""},
          {"ORACLE_URL",""},
          {"DB_USER",""},

          {"RELATIVE_HTML","/tcmIS/msds/orlite"},
          {"DB_PASS",""},
          {"DB_CLIENT_NAME","Orlite"},
          {"WEBS_HOME_WWWS",""},
          {"WEBS_HOME_WWW",""},

          {"HOME_PAGE","/tcmIS/orlite/home.do"},
          {"HOME_IMAGE","/images/orlite.gif"},
          {"HELP_HTML_PATH","/tcmIS/help/new/"},

          {"VIEW_MSDS","/tcmIS/orlite/ViewMsds?"},
          {"REGISTER_SERVLET","/tcmIS/orlite/Register"},
          {"HOME_SERVLET","/tcmIS/orlite/home.do"},
          {"JNLP_MAINCLASS","RadianCCOrlite.jar"},
          {"JNLP_DIRECTORY","/jnlp/orlite"},
          {"TEST_JNLP_DIRECTORY","/prereleasejnlp/orlite"},
          {"TCMIS_START_CLASS","radian.tcmis.client.client.orlite.gui.OrliteCmisApp"},
          {"BATCH_REPORT_SERVLET","/tcmIS/orlite/batchreport?"},

          {"ADDUSER_SERVLET","/tcmIS/orlite/Register?useraction=AddUser"},
          {"CHGUSER_SERVLET","/tcmIS/orlite/Register?useraction=ChgUser"},
          {"ADD_SERVLET","/tcmIS/orlite/Register?useraction=Add"},
          {"LOGINREG_SERVLET","/tcmIS/orlite/Register?useraction=Login"},
          {"CHANGE_SERVLET","/tcmIS/orlite/Register?useraction=Change"},
          {"REGISTER_HOME_SERVLET","/tcmIS/orlite/Register?"},
          {"TEST_INSTALL_SERVLET","/tcmIS/orlite/Register?useraction=startingtcmis"},
          {"BAR_BUILD_SERVLET","/tcmIS/orlite/Msds?limit=yes"},
          {"BUILD_SERVLET","/tcmIS/orlite/Msds?"},
          {"SERVLET_DIR_WWW",""},
          {"ERW_TEMPLATE_DIR","/webdata/html/template/"},
          {"BASE_DIR_TCM_WWW","/webdata/html/"},
          {"ERW_REPORT_DIR_ACTIVE","/webdata/html/reports/temp/"},
          {"ERW_REPORT_DIR_BATCH","/webdata/html/reports/"},
          {"WEBS_HOME_WWW_ERW_ACTIVE","/reports/temp/"},
          {"WEBS_HOME_WWW_ERW_BATCH","/reports/"},
          {"CATALOGING_SERVLET","/tcmIS/orlite/cataloging?"},
          {"PRINT_SCREEN","/tcmIS/orlite/printscreen"},
          {"HELP_SERVLET_PATH","/tcmIS/client/help"},

          {"DROPRECEIVING","No"},
          {"RELABELING","No"},
          {"DROPRECEIVING_SERVLET","/tcmIS/orlite/dropshipreceivingmain.do"},
          {"DROPRECEIVING_SERVLET_HEADER","/tcmIS/orlite/dropshipreceivingmain.do"},
          {"RELABELING_SERVLET","/tcmIS/orlite/Relabeling?session=Active"},

		  {"COST_REPORT","/tcmIS/orlite/betacostreport?"},
		  {"SCHEDULDE_DELIVERY","/tcmIS/orlite/schdelivery?"},
		  {"CANCEL_MR","/tcmIS/orlite/cancel?"},
		  {"WASTE_ORDER","/tcmIS/orlite/wasteorder?"},
		  {"WASTE_ADHOC_REPORT","/tcmIS/orlite/wastereport?"},
		  {"USAGE_ADHOC_REPORT","/tcmIS/orlite/usagereport?"},
		  {"MATERIAL_MATRIX_REPORT","/tcmIS/orlite/matrixreport?"},

		  {"SHIPPINGINFO","No"},
		  {"SHIPPINGINFO_SERVLET","/tcmIS/orlite/shipinfo?session=Active"},
		  {"SHIPPINGINFO_SERVLET_HEADER","/tcmIS/orlite/shipinfo"},

                  {"WEB_ORDER_TRACKING","Yes"},
                  {"WEB_ORDER_TRACKING_SERVLET","/tcmIS/orlite/ordertrackingmain.do"},
                  {"WEB_INVENTORY","Yes"},
                  {"WEB_INVENTORY_SERVLET","/tcmIS/orlite/inventorymain.do"},
                  {"WEB_CATALOG","Yes"},
                  {"WEB_CATALOG_SERVLET","/tcmIS/orlite/catalog.do"},

           {"WEB_REGISTRATION","Yes"},
			{"NEW_WEB_APPLICATION","Yes"}

	  };

     public OrliteServerResourceBundle(){
            super();
            addHash(cHash);
     }

}