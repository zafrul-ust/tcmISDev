package radian.web.servlets.boeingcomair;

import radian.tcmis.server7.share.helpers.ServerResourceBundle;

public class BoeingComairServerResourceBundle  extends ServerResourceBundle {

	static final Object[][] cHash = {
		{"LOG_DIR","/logsUTC"},
		{"DEBUG","false"},
		{"DB_CLIENT","BOEING_COMAIR"},
		{"ORACLE_DRIVER",""},
		{"ORACLE_URL",""},
		{"DB_USER",""},

		{"RELATIVE_HTML","/tcmIS/msds/boeing"},
		{"DB_PASS",""},
		{"DB_CLIENT_NAME","BOEING_CO"},
		{"WEBS_HOME_WWWS",""},
		{"WEBS_HOME_WWW",""},

		{"HOME_PAGE","/tcmIS/boeing/home.do"},
		{"HOME_IMAGE","/images/log/logo_HASS.gif"},
		{"HELP_HTML_PATH","/tcmIS/help/new/"},

		{"VIEW_MSDS","/tcmIS/boeing/ViewMsds?"},
		{"REGISTER_SERVLET","/tcmIS/boeing/Register"},
		{"HOME_SERVLET","/tcmIS/boeing/home.do"},
		{"JNLP_MAINCLASS","RadianCCBoeingComair.jar"},
		{"JNLP_DIRECTORY","/jnlp/boeingcomair"},
		{"TEST_JNLP_DIRECTORY","/prereleasejnlp/boeingcomair"},
		{"TCMIS_START_CLASS","radian.tcmis.client.client.boeingcomair.gui.BoeingComairCmisApp"},
		{"BATCH_REPORT_SERVLET","/tcmIS/boeing/batchreport?"},

		{"ADDUSER_SERVLET","/tcmIS/boeing/Register?useraction=AddUser"},
		{"CHGUSER_SERVLET","/tcmIS/boeing/Register?useraction=ChgUser"},
		{"ADD_SERVLET","/tcmIS/boeing/Register?useraction=Add"},
		{"LOGINREG_SERVLET","/tcmIS/boeing/Register?useraction=Login"},
		{"CHANGE_SERVLET","/tcmIS/boeing/Register?useraction=Change"},
		{"REGISTER_HOME_SERVLET","/tcmIS/boeing/Register?"},
		{"TEST_INSTALL_SERVLET","/tcmIS/boeing/Register?useraction=startingtcmis"},
		{"BAR_BUILD_SERVLET","/tcmIS/boeing/Msds?limit=yes"},
		{"BUILD_SERVLET","/tcmIS/boeing/Msds?"},
		{"SERVLET_DIR_WWW",""},
		{"ERW_TEMPLATE_DIR","/webdata/html/template/"},
		{"BASE_DIR_TCM_WWW","/webdata/html/"},
		{"ERW_REPORT_DIR_ACTIVE","/webdata/html/reports/temp/"},
		{"ERW_REPORT_DIR_BATCH","/webdata/html/reports/"},
		{"WEBS_HOME_WWW_ERW_ACTIVE","/reports/temp/"},
		{"WEBS_HOME_WWW_ERW_BATCH","/reports/"},
		{"CATALOGING_SERVLET","/tcmIS/boeing/cataloging?"},
		{"PRINT_SCREEN","/tcmIS/boeing/printscreen"},
		{"HELP_SERVLET_PATH","/tcmIS/client/help"},

		{"DROPRECEIVING","Yes"},
		{"RELABELING","No"},
		{"DROPRECEIVING_SERVLET","/tcmIS/boeing/dropshipreceivingmain.do"},
		{"DROPRECEIVING_SERVLET_HEADER","/tcmIS/boeing/dropshipreceivingmain.do"},
		{"RELABELING_SERVLET","/tcmIS/boeing/Relabeling?session=Active"},

		{"COST_REPORT","/tcmIS/boeing/betacostreport?"},
		{"SCHEDULDE_DELIVERY","/tcmIS/boeing/schdelivery?"},
		{"CANCEL_MR","/tcmIS/boeing/cancel?"},
		{"WASTE_ORDER","/tcmIS/boeing/wasteorder?"},
		{"WASTE_ADHOC_REPORT","/tcmIS/boeing/wastereport?"},
		{"USAGE_ADHOC_REPORT","/tcmIS/boeing/usagereport?"},
		{"MATERIAL_MATRIX_REPORT","/tcmIS/boeing/matrixreport?"},

		{"SHIPPINGINFO","No"},
		{"SHIPPINGINFO_SERVLET","/tcmIS/boeing/shipinfo?session=Active"},
		{"SHIPPINGINFO_SERVLET_HEADER","/tcmIS/boeing/shipinfo"},

		{"WEB_ORDER_TRACKING","Yes"},
		{"WEB_ORDER_TRACKING_SERVLET","/tcmIS/boeing/ordertrackingmain.do"},
		{"WEB_INVENTORY","Yes"},
		{"WEB_INVENTORY_SERVLET","/tcmIS/boeing/inventorymain.do"},
		{"WEB_CATALOG","Yes"},
		{"WEB_CATALOG_SERVLET","/tcmIS/boeing/catalog.do"},

		{"WEB_REGISTRATION","Yes"},
		{"NEW_WEB_APPLICATION","Yes"}

	};

	public BoeingComairServerResourceBundle(){
		super();
		addHash(cHash);
	}

}