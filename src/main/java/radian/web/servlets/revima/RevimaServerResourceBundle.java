package radian.web.servlets.revima;
import radian.tcmis.server7.share.helpers.ServerResourceBundle;

public class RevimaServerResourceBundle extends ServerResourceBundle {

	  static final Object[][] cHash = {
	      {"LOG_DIR","/logsrevima"},
	      {"DEBUG","true"},
	      {"DB_CLIENT","REVIMA"},
	      {"ORACLE_DRIVER",""},
	      {"ORACLE_URL",""},
	      {"DB_USER",""},

	      {"RELATIVE_HTML","/tcmIS/msds/revima"},
	      {"DB_PASS",""},
	      {"DB_CLIENT_NAME","REVIMA"},
	      {"WEBS_HOME_WWWS",""},
	      {"WEBS_HOME_WWW",""},

	      {"HOME_PAGE","/tcmIS/revima/home.do"},
	      {"HOME_IMAGE","/images/buttons/revima/revima.png"},
	      {"HELP_HTML_PATH","/tcmIS/help/new/"},

	      {"VIEW_MSDS","/tcmIS/revima/ViewMsds?"},
	      {"REGISTER_SERVLET","/tcmIS/revima/Register"},
	      {"HOME_SERVLET","/tcmIS/revima/home.do"},
	      {"JNLP_MAINCLASS","RadianCCRevima.jar"},
	      {"JNLP_DIRECTORY","/jnlp/revima"},
	      {"TEST_JNLP_DIRECTORY","/prereleasejnlp/revima"},
	      {"TCMIS_START_CLASS","radian.tcmis.client.client.revima.gui.RevimaCmisApp"},
	      {"BATCH_REPORT_SERVLET","/tcmIS/revima/batchreport?"},

	      {"ADDUSER_SERVLET","/tcmIS/revima/Register?useraction=AddUser"},
	      {"CHGUSER_SERVLET","/tcmIS/revima/Register?useraction=ChgUser"},
	      {"ADD_SERVLET","/tcmIS/revima/Register?useraction=Add"},
	      {"LOGINREG_SERVLET","/tcmIS/revima/Register?useraction=Login"},
	      {"CHANGE_SERVLET","/tcmIS/revima/Register?useraction=Change"},
	      {"REGISTER_HOME_SERVLET","/tcmIS/revima/Register?"},
	      {"TEST_INSTALL_SERVLET","/tcmIS/revima/Register?useraction=startingtcmis"},
	      {"BAR_BUILD_SERVLET","/tcmIS/revima/Msds?limit=yes"},
	      {"BUILD_SERVLET","/tcmIS/revima/Msds?"},
	      {"SERVLET_DIR_WWW",""},
	      {"ERW_TEMPLATE_DIR","/webdata/html/template/"},
	      {"BASE_DIR_TCM_WWW","/webdata/html/"},
	      {"ERW_REPORT_DIR_ACTIVE","/webdata/html/reports/temp/"},
	      {"ERW_REPORT_DIR_BATCH","/webdata/html/reports/"},
	      {"WEBS_HOME_WWW_ERW_ACTIVE","/reports/temp/"},
	      {"WEBS_HOME_WWW_ERW_BATCH","/reports/"},
	      {"CATALOGING_SERVLET","/tcmIS/revima/cataloging?"},
	      {"PRINT_SCREEN","/tcmIS/revima/printscreen"},
	      {"HELP_SERVLET_PATH","/tcmIS/client/help"},

	      {"DROPRECEIVING","Yes"},
	      {"RELABELING","No"},
	      {"DROPRECEIVING_SERVLET","/tcmIS/revima/dropshipreceivingmain.do"},
	      {"DROPRECEIVING_SERVLET_HEADER","/tcmIS/revima/dropshipreceivingmain.do"},
	      {"RELABELING_SERVLET","/tcmIS/revima/Relabeling?session=Active"},
	      {"PROCESS_DETAIL_URL","/tcmIS/revima/processdetail?"},
		  {"COST_REPORT","/tcmIS/revima/betacostreport?"},
		  {"SCHEDULDE_DELIVERY","/tcmIS/revima/schdelivery?"},
		  {"CANCEL_MR","/tcmIS/revima/cancel?"},
		  {"WASTE_ORDER","/tcmIS/revima/wasteorder?"},
		  {"WASTE_ADHOC_REPORT","/tcmIS/revima/wastereport?"},
		  {"USAGE_ADHOC_REPORT","/tcmIS/revima/usagereport?"},
		  {"MATERIAL_MATRIX_REPORT","/tcmIS/revima/matrixreport?"},

		  {"SHIPPINGINFO","No"},
		  {"SHIPPINGINFO_SERVLET","/tcmIS/revima/shipinfo?session=Active"},
		  {"SHIPPINGINFO_SERVLET_HEADER","/tcmIS/revima/shipinfo"},
	          {"WEB_ORDER_TRACKING","Yes"},
	          {"WEB_ORDER_TRACKING_SERVLET","/tcmIS/revima/ordertrackingmain.do"},
	      {"WEB_REGISTRATION","Yes"},
		  {"NEW_WEB_APPLICATION","Yes"}
	    };
	  
	    public RevimaServerResourceBundle(){
	        super();
	        addHash(cHash);
	    }

	}


