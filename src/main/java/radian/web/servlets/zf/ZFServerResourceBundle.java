package radian.web.servlets.zf;

import radian.tcmis.server7.share.helpers.ServerResourceBundle;

public class ZFServerResourceBundle  extends ServerResourceBundle {

     static final Object[][] cHash = {
          {"LOG_DIR","/logsUTC"},
          {"DEBUG","false"},
          {"DB_CLIENT","ZF"},
          {"ORACLE_DRIVER",""},
          {"ORACLE_URL",""},
          {"DB_USER",""},

          {"RELATIVE_HTML","/tcmIS/msds/zf"},
          {"DB_PASS",""},
          {"DB_CLIENT_NAME","ZF"},
          {"WEBS_HOME_WWWS",""},
          {"WEBS_HOME_WWW",""},

          {"HOME_PAGE","/tcmIS/zf/home.do"},
          {"HOME_IMAGE","/images/zf.gif"},
          {"HELP_HTML_PATH","/tcmIS/help/new/"},

          {"VIEW_MSDS","/tcmIS/zf/ViewMsds?"},
          {"REGISTER_SERVLET","/tcmIS/zf/Register"},
          {"HOME_SERVLET","/tcmIS/zf/home.do"},
          {"JNLP_MAINCLASS","RadianCCZF.jar"},
          {"JNLP_DIRECTORY","/jnlp/zf"},
          {"TEST_JNLP_DIRECTORY","/prereleasejnlp/zf"},
          {"TCMIS_START_CLASS","radian.tcmis.client.client.zf.gui.ZFCmisApp"},
          {"BATCH_REPORT_SERVLET","/tcmIS/zf/batchreport?"},

          {"ADDUSER_SERVLET","/tcmIS/zf/Register?useraction=AddUser"},
          {"CHGUSER_SERVLET","/tcmIS/zf/Register?useraction=ChgUser"},
          {"ADD_SERVLET","/tcmIS/zf/Register?useraction=Add"},
          {"LOGINREG_SERVLET","/tcmIS/zf/Register?useraction=Login"},
          {"CHANGE_SERVLET","/tcmIS/zf/Register?useraction=Change"},
          {"REGISTER_HOME_SERVLET","/tcmIS/zf/Register?"},
          {"TEST_INSTALL_SERVLET","/tcmIS/zf/Register?useraction=startingtcmis"},
          {"BAR_BUILD_SERVLET","/tcmIS/zf/Msds?limit=yes"},
          {"BUILD_SERVLET","/tcmIS/zf/Msds?"},
          {"SERVLET_DIR_WWW",""},
          {"ERW_TEMPLATE_DIR","/webdata/html/template/"},
          {"BASE_DIR_TCM_WWW","/webdata/html/"},
          {"ERW_REPORT_DIR_ACTIVE","/webdata/html/reports/temp/"},
          {"ERW_REPORT_DIR_BATCH","/webdata/html/reports/"},
          {"WEBS_HOME_WWW_ERW_ACTIVE","/reports/temp/"},
          {"WEBS_HOME_WWW_ERW_BATCH","/reports/"},
          {"CATALOGING_SERVLET","/tcmIS/zf/cataloging?"},
          {"PRINT_SCREEN","/tcmIS/zf/printscreen"},
          {"HELP_SERVLET_PATH","/tcmIS/client/help"},

          {"DROPRECEIVING","No"},
          {"RELABELING","No"},
          {"DROPRECEIVING_SERVLET","/tcmIS/zf/dropshipreceivingmain.do"},
          {"DROPRECEIVING_SERVLET_HEADER","/tcmIS/zf/dropshipreceivingmain.do"},
          {"RELABELING_SERVLET","/tcmIS/zf/Relabeling?session=Active"},

		  {"COST_REPORT","/tcmIS/zf/betacostreport?"},
		  {"SCHEDULDE_DELIVERY","/tcmIS/zf/schdelivery?"},
		  {"CANCEL_MR","/tcmIS/zf/cancel?"},
		  {"WASTE_ORDER","/tcmIS/zf/wasteorder?"},
		  {"WASTE_ADHOC_REPORT","/tcmIS/zf/wastereport?"},
		  {"USAGE_ADHOC_REPORT","/tcmIS/zf/usagereport?"},
		  {"MATERIAL_MATRIX_REPORT","/tcmIS/zf/matrixreport?"},

		  {"SHIPPINGINFO","No"},
		  {"SHIPPINGINFO_SERVLET","/tcmIS/zf/shipinfo?session=Active"},
		  {"SHIPPINGINFO_SERVLET_HEADER","/tcmIS/zf/shipinfo"},

                  {"WEB_ORDER_TRACKING","Yes"},
                  {"WEB_ORDER_TRACKING_SERVLET","/tcmIS/zf/ordertrackingmain.do"},
                  {"WEB_INVENTORY","Yes"},
                  {"WEB_INVENTORY_SERVLET","/tcmIS/zf/inventorymain.do"},
                  {"WEB_CATALOG","Yes"},
                  {"WEB_CATALOG_SERVLET","/tcmIS/zf/catalog.do"},

           {"WEB_REGISTRATION","Yes"},
			{"NEW_WEB_APPLICATION","Yes"}

	  };

     public ZFServerResourceBundle(){
            super();
            addHash(cHash);
     }

}