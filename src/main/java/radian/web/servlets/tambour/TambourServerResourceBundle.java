package radian.web.servlets.tambour;

import radian.tcmis.server7.share.helpers.ServerResourceBundle;

public class TambourServerResourceBundle  extends ServerResourceBundle {

     static final Object[][] cHash = {
          {"LOG_DIR","/logsUTC"},
          {"DEBUG","false"},
          {"DB_CLIENT","Tambour"},
          {"ORACLE_DRIVER",""},
          {"ORACLE_URL",""},
          {"DB_USER",""},

          {"RELATIVE_HTML","/tcmIS/msds/tambour"},
          {"DB_PASS",""},
          {"DB_CLIENT_NAME","Tambour"},
          {"WEBS_HOME_WWWS",""},
          {"WEBS_HOME_WWW",""},

          {"HOME_PAGE","/tcmIS/tambour/home.do"},
          {"HOME_IMAGE","/images/tambour.gif"},
          {"HELP_HTML_PATH","/tcmIS/help/new/"},

          {"VIEW_MSDS","/tcmIS/tambour/ViewMsds?"},
          {"REGISTER_SERVLET","/tcmIS/tambour/Register"},
          {"HOME_SERVLET","/tcmIS/tambour/home.do"},
          {"JNLP_MAINCLASS","RadianCCTambour.jar"},
          {"JNLP_DIRECTORY","/jnlp/tambour"},
          {"TEST_JNLP_DIRECTORY","/prereleasejnlp/tambour"},
          {"TCMIS_START_CLASS","radian.tcmis.client.client.tambour.gui.TambourCmisApp"},
          {"BATCH_REPORT_SERVLET","/tcmIS/tambour/batchreport?"},

          {"ADDUSER_SERVLET","/tcmIS/tambour/Register?useraction=AddUser"},
          {"CHGUSER_SERVLET","/tcmIS/tambour/Register?useraction=ChgUser"},
          {"ADD_SERVLET","/tcmIS/tambour/Register?useraction=Add"},
          {"LOGINREG_SERVLET","/tcmIS/tambour/Register?useraction=Login"},
          {"CHANGE_SERVLET","/tcmIS/tambour/Register?useraction=Change"},
          {"REGISTER_HOME_SERVLET","/tcmIS/tambour/Register?"},
          {"TEST_INSTALL_SERVLET","/tcmIS/tambour/Register?useraction=startingtcmis"},
          {"BAR_BUILD_SERVLET","/tcmIS/tambour/Msds?limit=yes"},
          {"BUILD_SERVLET","/tcmIS/tambour/Msds?"},
          {"SERVLET_DIR_WWW",""},
          {"ERW_TEMPLATE_DIR","/webdata/html/template/"},
          {"BASE_DIR_TCM_WWW","/webdata/html/"},
          {"ERW_REPORT_DIR_ACTIVE","/webdata/html/reports/temp/"},
          {"ERW_REPORT_DIR_BATCH","/webdata/html/reports/"},
          {"WEBS_HOME_WWW_ERW_ACTIVE","/reports/temp/"},
          {"WEBS_HOME_WWW_ERW_BATCH","/reports/"},
          {"CATALOGING_SERVLET","/tcmIS/tambour/cataloging?"},
          {"PRINT_SCREEN","/tcmIS/tambour/printscreen"},
          {"HELP_SERVLET_PATH","/tcmIS/client/help"},

          {"DROPRECEIVING","No"},
          {"RELABELING","No"},
          {"DROPRECEIVING_SERVLET","/tcmIS/tambour/dropshipreceivingmain.do"},
          {"DROPRECEIVING_SERVLET_HEADER","/tcmIS/tambour/dropshipreceivingmain.do"},
          {"RELABELING_SERVLET","/tcmIS/tambour/Relabeling?session=Active"},

		  {"COST_REPORT","/tcmIS/tambour/betacostreport?"},
		  {"SCHEDULDE_DELIVERY","/tcmIS/tambour/schdelivery?"},
		  {"CANCEL_MR","/tcmIS/tambour/cancel?"},
		  {"WASTE_ORDER","/tcmIS/tambour/wasteorder?"},
		  {"WASTE_ADHOC_REPORT","/tcmIS/tambour/wastereport?"},
		  {"USAGE_ADHOC_REPORT","/tcmIS/tambour/usagereport?"},
		  {"MATERIAL_MATRIX_REPORT","/tcmIS/tambour/matrixreport?"},

		  {"SHIPPINGINFO","No"},
		  {"SHIPPINGINFO_SERVLET","/tcmIS/tambour/shipinfo?session=Active"},
		  {"SHIPPINGINFO_SERVLET_HEADER","/tcmIS/tambour/shipinfo"},

                  {"WEB_ORDER_TRACKING","Yes"},
                  {"WEB_ORDER_TRACKING_SERVLET","/tcmIS/tambour/ordertrackingmain.do"},
                  {"WEB_INVENTORY","Yes"},
                  {"WEB_INVENTORY_SERVLET","/tcmIS/tambour/inventorymain.do"},
                  {"WEB_CATALOG","Yes"},
                  {"WEB_CATALOG_SERVLET","/tcmIS/tambour/catalog.do"},

           {"WEB_REGISTRATION","Yes"},
			{"NEW_WEB_APPLICATION","Yes"}

	  };

     public TambourServerResourceBundle(){
            super();
            addHash(cHash);
     }

}