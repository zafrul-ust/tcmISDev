package radian.web.servlets.iai;

import radian.tcmis.server7.share.helpers.ServerResourceBundle;

public class IAIServerResourceBundle  extends ServerResourceBundle {

		 static final Object[][] cHash = {
					{"LOG_DIR","/logsUTC"},
					{"DEBUG","false"},
					{"DB_CLIENT","IAI"},
					{"ORACLE_DRIVER",""},
					{"ORACLE_URL",""},
					{"DB_USER",""},

					{"RELATIVE_HTML","/tcmIS/msds/iai"},
					{"DB_PASS",""},
					{"DB_CLIENT_NAME","IAI"},
					{"WEBS_HOME_WWWS",""},
					{"WEBS_HOME_WWW",""},

					{"HOME_PAGE","/tcmIS/iai/Home.do"},
					{"HOME_IMAGE","/images/iai.gif"},
					{"HELP_HTML_PATH","/tcmIS/help/new/"},

					{"VIEW_MSDS","/tcmIS/iai/ViewMsds?"},
					{"REGISTER_SERVLET","/tcmIS/iai/Register"},
					{"HOME_SERVLET","/tcmIS/iai/Home.do"},
					{"JNLP_MAINCLASS","RadianCCIAI.jar"},
					{"JNLP_DIRECTORY","/jnlp/iai"},
					{"TEST_JNLP_DIRECTORY","/prereleasejnlp/iai"},
					{"TCMIS_START_CLASS","radian.tcmis.client.client.iai.gui.IAICmisApp"},
					{"BATCH_REPORT_SERVLET","/tcmIS/iai/batchreport?"},

					{"ADDUSER_SERVLET","/tcmIS/iai/Register?useraction=AddUser"},
					{"CHGUSER_SERVLET","/tcmIS/iai/Register?useraction=ChgUser"},
					{"ADD_SERVLET","/tcmIS/iai/Register?useraction=Add"},
					{"LOGINREG_SERVLET","/tcmIS/iai/Register?useraction=Login"},
					{"CHANGE_SERVLET","/tcmIS/iai/Register?useraction=Change"},
					{"REGISTER_HOME_SERVLET","/tcmIS/iai/Register?"},
					{"TEST_INSTALL_SERVLET","/tcmIS/iai/Register?useraction=startingtcmis"},
					{"BAR_BUILD_SERVLET","/tcmIS/iai/Msds?limit=yes"},
					{"BUILD_SERVLET","/tcmIS/iai/Msds?"},
					{"SERVLET_DIR_WWW",""},
					{"ERW_TEMPLATE_DIR","/webdata/html/template/"},
					{"BASE_DIR_TCM_WWW","/webdata/html/"},
					{"ERW_REPORT_DIR_ACTIVE","/webdata/html/reports/temp/"},
					{"ERW_REPORT_DIR_BATCH","/webdata/html/reports/"},
					{"WEBS_HOME_WWW_ERW_ACTIVE","/reports/temp/"},
					{"WEBS_HOME_WWW_ERW_BATCH","/reports/"},
					{"CATALOGING_SERVLET","/tcmIS/iai/cataloging?"},
					{"PRINT_SCREEN","/tcmIS/iai/printscreen"},
					{"HELP_SERVLET_PATH","/tcmIS/client/help"},

					{"DROPRECEIVING","Yes"},
					{"RELABELING","No"},
					{"DROPRECEIVING_SERVLET","/tcmIS/iai/dropshipreceivingmain.do"},
					{"DROPRECEIVING_SERVLET_HEADER","/tcmIS/iai/dropshipreceivingmain.do"},
					{"RELABELING_SERVLET","/tcmIS/iai/Relabeling?session=Active"},

			{"COST_REPORT","/tcmIS/iai/betacostreport?"},
			{"SCHEDULDE_DELIVERY","/tcmIS/iai/schdelivery?"},
			{"CANCEL_MR","/tcmIS/iai/cancel?"},
			{"WASTE_ORDER","/tcmIS/iai/wasteorder?"},
			{"WASTE_ADHOC_REPORT","/tcmIS/iai/wastereport?"},
			{"USAGE_ADHOC_REPORT","/tcmIS/iai/usagereport?"},
			{"MATERIAL_MATRIX_REPORT","/tcmIS/iai/matrixreport?"},

			{"SHIPPINGINFO","No"},
			{"SHIPPINGINFO_SERVLET","/tcmIS/iai/shipinfo?session=Active"},
			{"SHIPPINGINFO_SERVLET_HEADER","/tcmIS/iai/shipinfo"},

			{"WEB_ORDER_TRACKING","Yes"},
			{"WEB_ORDER_TRACKING_SERVLET","/tcmIS/iai/ordertrackingmain.do"},

			{"WEB_INVENTORY","Yes"},
			{"WEB_INVENTORY_SERVLET","/tcmIS/iai/inventorymain.do"},

			{"WEB_CATALOG","Yes"},
			{"WEB_CATALOG_SERVLET","/tcmIS/iai/catalog.do"},

					{"WEB_REGISTRATION","Yes"}

		 };

		 public IAIServerResourceBundle(){
						super();
						addHash(cHash);
		 }

}
