package radian.web.servlets.schafer;
import radian.tcmis.server7.share.helpers.ServerResourceBundle;

public class SchaferServerResourceBundle extends ServerResourceBundle {

	  static final Object[][] cHash = {
	      {"LOG_DIR","/logsschafer"},
	      {"DEBUG","true"},
	      {"DB_CLIENT","SCHAFER_GEAR"},
	      {"ORACLE_DRIVER",""},
	      {"ORACLE_URL",""},
	      {"DB_USER",""},

	      {"RELATIVE_HTML","/tcmIS/msds/schafer"},
	      {"DB_PASS",""},
	      {"DB_CLIENT_NAME","SCHAFER_GEAR"},
	      {"WEBS_HOME_WWWS",""},
	      {"WEBS_HOME_WWW",""},

	      {"HOME_PAGE","/tcmIS/schafer/home.do"},
	      {"HOME_IMAGE","/images/buttons/schafer/schafer.gif"},
	      {"HELP_HTML_PATH","/tcmIS/help/new/"},

	      {"VIEW_MSDS","/tcmIS/schafer/ViewMsds?"},
	      {"REGISTER_SERVLET","/tcmIS/schafer/Register"},
	      {"HOME_SERVLET","/tcmIS/schafer/home.do"},
	      {"JNLP_MAINCLASS","RadianCCSchafer.jar"},
	      {"JNLP_DIRECTORY","/jnlp/schafer"},
	      {"TEST_JNLP_DIRECTORY","/prereleasejnlp/schafer"},
	      {"TCMIS_START_CLASS","radian.tcmis.client.client.schafer.gui.SchaferCmisApp"},
	      {"BATCH_REPORT_SERVLET","/tcmIS/schafer/batchreport?"},

	      {"ADDUSER_SERVLET","/tcmIS/schafer/Register?useraction=AddUser"},
	      {"CHGUSER_SERVLET","/tcmIS/schafer/Register?useraction=ChgUser"},
	      {"ADD_SERVLET","/tcmIS/schafer/Register?useraction=Add"},
	      {"LOGINREG_SERVLET","/tcmIS/schafer/Register?useraction=Login"},
	      {"CHANGE_SERVLET","/tcmIS/schafer/Register?useraction=Change"},
	      {"REGISTER_HOME_SERVLET","/tcmIS/schafer/Register?"},
	      {"TEST_INSTALL_SERVLET","/tcmIS/schafer/Register?useraction=startingtcmis"},
	      {"BAR_BUILD_SERVLET","/tcmIS/schafer/Msds?limit=yes"},
	      {"BUILD_SERVLET","/tcmIS/schafer/Msds?"},
	      {"SERVLET_DIR_WWW",""},
	      {"ERW_TEMPLATE_DIR","/webdata/html/template/"},
	      {"BASE_DIR_TCM_WWW","/webdata/html/"},
	      {"ERW_REPORT_DIR_ACTIVE","/webdata/html/reports/temp/"},
	      {"ERW_REPORT_DIR_BATCH","/webdata/html/reports/"},
	      {"WEBS_HOME_WWW_ERW_ACTIVE","/reports/temp/"},
	      {"WEBS_HOME_WWW_ERW_BATCH","/reports/"},
	      {"CATALOGING_SERVLET","/tcmIS/schafer/cataloging?"},
	      {"PRINT_SCREEN","/tcmIS/schafer/printscreen"},
	      {"HELP_SERVLET_PATH","/tcmIS/client/help"},

	      {"DROPRECEIVING","Yes"},
	      {"RELABELING","No"},
	      {"DROPRECEIVING_SERVLET","/tcmIS/schafer/dropshipreceivingmain.do"},
	      {"DROPRECEIVING_SERVLET_HEADER","/tcmIS/schafer/dropshipreceivingmain.do"},
	      {"RELABELING_SERVLET","/tcmIS/schafer/Relabeling?session=Active"},
	      {"PROCESS_DETAIL_URL","/tcmIS/schafer/processdetail?"},
		  {"COST_REPORT","/tcmIS/schafer/betacostreport?"},
		  {"SCHEDULDE_DELIVERY","/tcmIS/schafer/schdelivery?"},
		  {"CANCEL_MR","/tcmIS/schafer/cancel?"},
		  {"WASTE_ORDER","/tcmIS/schafer/wasteorder?"},
		  {"WASTE_ADHOC_REPORT","/tcmIS/schafer/wastereport?"},
		  {"USAGE_ADHOC_REPORT","/tcmIS/schafer/usagereport?"},
		  {"MATERIAL_MATRIX_REPORT","/tcmIS/schafer/matrixreport?"},

		  {"SHIPPINGINFO","No"},
		  {"SHIPPINGINFO_SERVLET","/tcmIS/schafer/shipinfo?session=Active"},
		  {"SHIPPINGINFO_SERVLET_HEADER","/tcmIS/schafer/shipinfo"},
	          {"WEB_ORDER_TRACKING","Yes"},
	          {"WEB_ORDER_TRACKING_SERVLET","/tcmIS/schafer/ordertrackingmain.do"},
	      {"WEB_REGISTRATION","Yes"},
		  {"NEW_WEB_APPLICATION","Yes"}
	    };
	  
	    public SchaferServerResourceBundle(){
	        super();
	        addHash(cHash);
	    }

	}


