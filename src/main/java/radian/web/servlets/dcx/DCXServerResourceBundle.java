package radian.web.servlets.dcx;

import radian.tcmis.server7.share.helpers.ServerResourceBundle;

public class DCXServerResourceBundle  extends ServerResourceBundle {

     static final Object[][] cHash = {
          {"LOG_DIR","/logsUTC"},
          {"DEBUG","false"},
          {"DB_CLIENT","DCX"},
          {"ORACLE_DRIVER",""},
          {"ORACLE_URL",""},
          {"DB_USER",""},

          {"RELATIVE_HTML","/tcmIS/msds/dcx"},
          {"DB_PASS",""},
          {"DB_CLIENT_NAME","DCX"},
          {"WEBS_HOME_WWWS",""},
          {"WEBS_HOME_WWW",""},

          {"HOME_PAGE","/tcmIS/dcx/Home"},
          {"HOME_IMAGE","/images/dcx.gif"},
          {"HELP_HTML_PATH","/tcmIS/help/new/"},

          {"VIEW_MSDS","/tcmIS/dcx/ViewMsds?"},
          {"REGISTER_SERVLET","/tcmIS/dcx/Register"},
          {"HOME_SERVLET","/tcmIS/dcx/Home"},
          {"JNLP_MAINCLASS","RadianCCDCX.jar"},
          {"JNLP_DIRECTORY","/jnlp/dcx"},
          {"TEST_JNLP_DIRECTORY","/prereleasejnlp/dcx"},
          {"TCMIS_START_CLASS","radian.tcmis.client.client.dcx.gui.DCXCmisApp"},
          {"BATCH_REPORT_SERVLET","/tcmIS/dcx/batchreport?"},

          {"ADDUSER_SERVLET","/tcmIS/dcx/Register?useraction=AddUser"},
          {"CHGUSER_SERVLET","/tcmIS/dcx/Register?useraction=ChgUser"},
          {"ADD_SERVLET","/tcmIS/dcx/Register?useraction=Add"},
          {"LOGINREG_SERVLET","/tcmIS/dcx/Register?useraction=Login"},
          {"CHANGE_SERVLET","/tcmIS/dcx/Register?useraction=Change"},
          {"REGISTER_HOME_SERVLET","/tcmIS/dcx/Register?"},
          {"TEST_INSTALL_SERVLET","/tcmIS/dcx/Register?useraction=startingtcmis"},
          {"BAR_BUILD_SERVLET","/tcmIS/dcx/Msds?limit=yes"},
          {"BUILD_SERVLET","/tcmIS/dcx/Msds?"},
          {"SERVLET_DIR_WWW",""},
          {"ERW_TEMPLATE_DIR","/webdata/html/template/"},
          {"BASE_DIR_TCM_WWW","/webdata/html/"},
          {"ERW_REPORT_DIR_ACTIVE","/webdata/html/reports/temp/"},
          {"ERW_REPORT_DIR_BATCH","/webdata/html/reports/"},
          {"WEBS_HOME_WWW_ERW_ACTIVE","/reports/temp/"},
          {"WEBS_HOME_WWW_ERW_BATCH","/reports/"},
          {"CATALOGING_SERVLET","/tcmIS/dcx/cataloging?"},
          {"PRINT_SCREEN","/tcmIS/dcx/printscreen"},
          {"HELP_SERVLET_PATH","/tcmIS/client/help"},

          {"DROPRECEIVING","No"},
          {"RELABELING","No"},
          {"DROPRECEIVING_SERVLET","/tcmIS/dcx/dropshipreceivingmain.do"},
          {"DROPRECEIVING_SERVLET_HEADER","/tcmIS/dcx/dropshipreceivingmain.do"},
          {"RELABELING_SERVLET","/tcmIS/dcx/Relabeling?session=Active"},

		  {"COST_REPORT","/tcmIS/dcx/betacostreport?"},
		  {"SCHEDULDE_DELIVERY","/tcmIS/dcx/schdelivery?"},
		  {"CANCEL_MR","/tcmIS/dcx/cancel?"},
		  {"WASTE_ORDER","/tcmIS/dcx/wasteorder?"},
		  {"WASTE_ADHOC_REPORT","/tcmIS/dcx/wastereport?"},
		  {"USAGE_ADHOC_REPORT","/tcmIS/dcx/usagereport?"},
		  {"MATERIAL_MATRIX_REPORT","/tcmIS/dcx/matrixreport?"},

		  {"SHIPPINGINFO","No"},
		  {"SHIPPINGINFO_SERVLET","/tcmIS/dcx/shipinfo?session=Active"},
		  {"SHIPPINGINFO_SERVLET_HEADER","/tcmIS/dcx/shipinfo"},

			{"WEB_ORDER_TRACKING","Yes"},
			{"WEB_ORDER_TRACKING_SERVLET","/tcmIS/dcx/ordertrackingmain.do"},

	{"WEB_INVENTORY","Yes"},
	{"WEB_INVENTORY_SERVLET","/tcmIS/dcx/inventorymain.do"},

	{"WEB_CATALOG","Yes"},
	{"WEB_CATALOG_SERVLET","/tcmIS/dcx/catalog.do"},


	{"WEB_REGISTRATION","Yes"}

 };

     public DCXServerResourceBundle(){
            super();
            addHash(cHash);
     }

}