package radian.web.servlets.hrgivon;

import radian.tcmis.server7.share.helpers.ServerResourceBundle;

public class HrgivonServerResourceBundle  extends ServerResourceBundle {

     static final Object[][] cHash = {
          {"LOG_DIR","/logsUTC"},
          {"DEBUG","false"},
          {"DB_CLIENT","Hrgivon"},
          {"ORACLE_DRIVER",""},
          {"ORACLE_URL",""},
          {"DB_USER",""},

          {"RELATIVE_HTML","/tcmIS/msds/hrgivon"},
          {"DB_PASS",""},
          {"DB_CLIENT_NAME","Hrgivon"},
          {"WEBS_HOME_WWWS",""},
          {"WEBS_HOME_WWW",""},

          {"HOME_PAGE","/tcmIS/hrgivon/home.do"},
          {"HOME_IMAGE","/images/hrgivon.gif"},
          {"HELP_HTML_PATH","/tcmIS/help/new/"},

          {"VIEW_MSDS","/tcmIS/hrgivon/ViewMsds?"},
          {"REGISTER_SERVLET","/tcmIS/hrgivon/Register"},
          {"HOME_SERVLET","/tcmIS/hrgivon/home.do"},
          {"JNLP_MAINCLASS","RadianCCHrgivon.jar"},
          {"JNLP_DIRECTORY","/jnlp/hrgivon"},
          {"TEST_JNLP_DIRECTORY","/prereleasejnlp/hrgivon"},
          {"TCMIS_START_CLASS","radian.tcmis.client.client.hrgivon.gui.HrgivonCmisApp"},
          {"BATCH_REPORT_SERVLET","/tcmIS/hrgivon/batchreport?"},

          {"ADDUSER_SERVLET","/tcmIS/hrgivon/Register?useraction=AddUser"},
          {"CHGUSER_SERVLET","/tcmIS/hrgivon/Register?useraction=ChgUser"},
          {"ADD_SERVLET","/tcmIS/hrgivon/Register?useraction=Add"},
          {"LOGINREG_SERVLET","/tcmIS/hrgivon/Register?useraction=Login"},
          {"CHANGE_SERVLET","/tcmIS/hrgivon/Register?useraction=Change"},
          {"REGISTER_HOME_SERVLET","/tcmIS/hrgivon/Register?"},
          {"TEST_INSTALL_SERVLET","/tcmIS/hrgivon/Register?useraction=startingtcmis"},
          {"BAR_BUILD_SERVLET","/tcmIS/hrgivon/Msds?limit=yes"},
          {"BUILD_SERVLET","/tcmIS/hrgivon/Msds?"},
          {"SERVLET_DIR_WWW",""},
          {"ERW_TEMPLATE_DIR","/webdata/html/template/"},
          {"BASE_DIR_TCM_WWW","/webdata/html/"},
          {"ERW_REPORT_DIR_ACTIVE","/webdata/html/reports/temp/"},
          {"ERW_REPORT_DIR_BATCH","/webdata/html/reports/"},
          {"WEBS_HOME_WWW_ERW_ACTIVE","/reports/temp/"},
          {"WEBS_HOME_WWW_ERW_BATCH","/reports/"},
          {"CATALOGING_SERVLET","/tcmIS/hrgivon/cataloging?"},
          {"PRINT_SCREEN","/tcmIS/hrgivon/printscreen"},
          {"HELP_SERVLET_PATH","/tcmIS/client/help"},

          {"DROPRECEIVING","No"},
          {"RELABELING","No"},
          {"DROPRECEIVING_SERVLET","/tcmIS/hrgivon/dropshipreceivingmain.do"},
          {"DROPRECEIVING_SERVLET_HEADER","/tcmIS/hrgivon/dropshipreceivingmain.do"},
          {"RELABELING_SERVLET","/tcmIS/hrgivon/Relabeling?session=Active"},

		  {"COST_REPORT","/tcmIS/hrgivon/betacostreport?"},
		  {"SCHEDULDE_DELIVERY","/tcmIS/hrgivon/schdelivery?"},
		  {"CANCEL_MR","/tcmIS/hrgivon/cancel?"},
		  {"WASTE_ORDER","/tcmIS/hrgivon/wasteorder?"},
		  {"WASTE_ADHOC_REPORT","/tcmIS/hrgivon/wastereport?"},
		  {"USAGE_ADHOC_REPORT","/tcmIS/hrgivon/usagereport?"},
		  {"MATERIAL_MATRIX_REPORT","/tcmIS/hrgivon/matrixreport?"},

		  {"SHIPPINGINFO","No"},
		  {"SHIPPINGINFO_SERVLET","/tcmIS/hrgivon/shipinfo?session=Active"},
		  {"SHIPPINGINFO_SERVLET_HEADER","/tcmIS/hrgivon/shipinfo"},

                  {"WEB_ORDER_TRACKING","Yes"},
                  {"WEB_ORDER_TRACKING_SERVLET","/tcmIS/hrgivon/ordertrackingmain.do"},
                  {"WEB_INVENTORY","Yes"},
                  {"WEB_INVENTORY_SERVLET","/tcmIS/hrgivon/inventorymain.do"},
                  {"WEB_CATALOG","Yes"},
                  {"WEB_CATALOG_SERVLET","/tcmIS/hrgivon/catalog.do"},

           {"WEB_REGISTRATION","Yes"},
			{"NEW_WEB_APPLICATION","Yes"}

	  };

     public HrgivonServerResourceBundle(){
            super();
            addHash(cHash);
     }

}