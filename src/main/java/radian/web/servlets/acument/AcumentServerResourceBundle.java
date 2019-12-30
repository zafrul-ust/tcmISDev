package radian.web.servlets.acument;
import radian.tcmis.server7.share.helpers.ServerResourceBundle;

public class AcumentServerResourceBundle extends ServerResourceBundle {

	  static final Object[][] cHash = {
	      {"LOG_DIR","/logsacument"},
	      {"DEBUG","true"},
	      {"DB_CLIENT","ACUMENT"},
	      {"ORACLE_DRIVER",""},
	      {"ORACLE_URL",""},
	      {"DB_USER",""},

	      {"RELATIVE_HTML","/tcmIS/msds/acument"},
	      {"DB_PASS",""},
	      {"DB_CLIENT_NAME","ACUMENT"},
	      {"WEBS_HOME_WWWS",""},
	      {"WEBS_HOME_WWW",""},

	      {"HOME_PAGE","/tcmIS/acument/home.do"},
	      {"HOME_IMAGE","/images/buttons/acument/acument.png"},
	      {"HELP_HTML_PATH","/tcmIS/help/new/"},

	      {"VIEW_MSDS","/tcmIS/acument/ViewMsds?"},
	      {"REGISTER_SERVLET","/tcmIS/acument/Register"},
	      {"HOME_SERVLET","/tcmIS/acument/home.do"},
	      {"JNLP_MAINCLASS","RadianCCAcument.jar"},
	      {"JNLP_DIRECTORY","/jnlp/acument"},
	      {"TEST_JNLP_DIRECTORY","/prereleasejnlp/acument"},
	      {"TCMIS_START_CLASS","radian.tcmis.client.client.acument.gui.AcumentCmisApp"},
	      {"BATCH_REPORT_SERVLET","/tcmIS/acument/batchreport?"},

	      {"ADDUSER_SERVLET","/tcmIS/acument/Register?useraction=AddUser"},
	      {"CHGUSER_SERVLET","/tcmIS/acument/Register?useraction=ChgUser"},
	      {"ADD_SERVLET","/tcmIS/acument/Register?useraction=Add"},
	      {"LOGINREG_SERVLET","/tcmIS/acument/Register?useraction=Login"},
	      {"CHANGE_SERVLET","/tcmIS/acument/Register?useraction=Change"},
	      {"REGISTER_HOME_SERVLET","/tcmIS/acument/Register?"},
	      {"TEST_INSTALL_SERVLET","/tcmIS/acument/Register?useraction=startingtcmis"},
	      {"BAR_BUILD_SERVLET","/tcmIS/acument/Msds?limit=yes"},
	      {"BUILD_SERVLET","/tcmIS/acument/Msds?"},
	      {"SERVLET_DIR_WWW",""},
	      {"ERW_TEMPLATE_DIR","/webdata/html/template/"},
	      {"BASE_DIR_TCM_WWW","/webdata/html/"},
	      {"ERW_REPORT_DIR_ACTIVE","/webdata/html/reports/temp/"},
	      {"ERW_REPORT_DIR_BATCH","/webdata/html/reports/"},
	      {"WEBS_HOME_WWW_ERW_ACTIVE","/reports/temp/"},
	      {"WEBS_HOME_WWW_ERW_BATCH","/reports/"},
	      {"CATALOGING_SERVLET","/tcmIS/acument/cataloging?"},
	      {"PRINT_SCREEN","/tcmIS/acument/printscreen"},
	      {"HELP_SERVLET_PATH","/tcmIS/client/help"},

	      {"DROPRECEIVING","Yes"},
	      {"RELABELING","No"},
	      {"DROPRECEIVING_SERVLET","/tcmIS/acument/dropshipreceivingmain.do"},
	      {"DROPRECEIVING_SERVLET_HEADER","/tcmIS/acument/dropshipreceivingmain.do"},
	      {"RELABELING_SERVLET","/tcmIS/acument/Relabeling?session=Active"},
	      {"PROCESS_DETAIL_URL","/tcmIS/acument/processdetail?"},
		  {"COST_REPORT","/tcmIS/acument/betacostreport?"},
		  {"SCHEDULDE_DELIVERY","/tcmIS/acument/schdelivery?"},
		  {"CANCEL_MR","/tcmIS/acument/cancel?"},
		  {"WASTE_ORDER","/tcmIS/acument/wasteorder?"},
		  {"WASTE_ADHOC_REPORT","/tcmIS/acument/wastereport?"},
		  {"USAGE_ADHOC_REPORT","/tcmIS/acument/usagereport?"},
		  {"MATERIAL_MATRIX_REPORT","/tcmIS/acument/matrixreport?"},

		  {"SHIPPINGINFO","No"},
		  {"SHIPPINGINFO_SERVLET","/tcmIS/acument/shipinfo?session=Active"},
		  {"SHIPPINGINFO_SERVLET_HEADER","/tcmIS/acument/shipinfo"},
	          {"WEB_ORDER_TRACKING","Yes"},
	          {"WEB_ORDER_TRACKING_SERVLET","/tcmIS/acument/ordertrackingmain.do"},
	      {"WEB_REGISTRATION","Yes"},
		  {"NEW_WEB_APPLICATION","Yes"}
	    };
	  
	    public AcumentServerResourceBundle(){
	        super();
	        addHash(cHash);
	    }

	}


