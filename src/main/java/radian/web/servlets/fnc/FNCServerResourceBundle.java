package radian.web.servlets.fnc;

import radian.tcmis.server7.share.helpers.ServerResourceBundle;

public class FNCServerResourceBundle  extends ServerResourceBundle {

	    static final Object[][] cHash = {
	      {"LOG_DIR","/logsfnc"},
	      {"DEBUG","true"},
	      {"DB_CLIENT","FNC"},
	      {"ORACLE_DRIVER",""},
	      {"ORACLE_URL",""},
	      {"DB_USER",""},

	      {"RELATIVE_HTML","/tcmIS/msds/fnc"},
	      {"DB_PASS",""},
	      {"DB_CLIENT_NAME","FNC"},
	      {"WEBS_HOME_WWWS",""},
	      {"WEBS_HOME_WWW",""},

	      {"HOME_PAGE","/tcmIS/fnc/home.do"},
	      {"HOME_IMAGE","/images/buttons/fnc/fnc.png"},
	      {"HELP_HTML_PATH","/tcmIS/help/new/"},

	      {"VIEW_MSDS","/tcmIS/fnc/ViewMsds?"},
	      {"REGISTER_SERVLET","/tcmIS/fnc/Register"},
	      {"HOME_SERVLET","/tcmIS/fnc/home.do"},
	      {"JNLP_MAINCLASS","RadianCCFNC.jar"},
	      {"JNLP_DIRECTORY","/jnlp/fnc"},
	      {"TEST_JNLP_DIRECTORY","/prereleasejnlp/fnc"},
	      {"TCMIS_START_CLASS","radian.tcmis.client.client.fnc.gui.FNCCmisApp"},
	      {"BATCH_REPORT_SERVLET","/tcmIS/fnc/batchreport?"},

	      {"ADDUSER_SERVLET","/tcmIS/fnc/Register?useraction=AddUser"},
	      {"CHGUSER_SERVLET","/tcmIS/fnc/Register?useraction=ChgUser"},
	      {"ADD_SERVLET","/tcmIS/fnc/Register?useraction=Add"},
	      {"LOGINREG_SERVLET","/tcmIS/fnc/Register?useraction=Login"},
	      {"CHANGE_SERVLET","/tcmIS/fnc/Register?useraction=Change"},
	      {"REGISTER_HOME_SERVLET","/tcmIS/fnc/Register?"},
	      {"TEST_INSTALL_SERVLET","/tcmIS/fnc/Register?useraction=startingtcmis"},
	      {"BAR_BUILD_SERVLET","/tcmIS/fnc/Msds?limit=yes"},
	      {"BUILD_SERVLET","/tcmIS/fnc/Msds?"},
	      {"SERVLET_DIR_WWW",""},
	      {"ERW_TEMPLATE_DIR","/webdata/html/template/"},
	      {"BASE_DIR_TCM_WWW","/webdata/html/"},
	      {"ERW_REPORT_DIR_ACTIVE","/webdata/html/reports/temp/"},
	      {"ERW_REPORT_DIR_BATCH","/webdata/html/reports/"},
	      {"WEBS_HOME_WWW_ERW_ACTIVE","/reports/temp/"},
	      {"WEBS_HOME_WWW_ERW_BATCH","/reports/"},
	      {"CATALOGING_SERVLET","/tcmIS/fnc/cataloging?"},
	      {"PRINT_SCREEN","/tcmIS/fnc/printscreen"},
	      {"HELP_SERVLET_PATH","/tcmIS/client/help"},

	      {"DROPRECEIVING","Yes"},
	      {"RELABELING","No"},
	      {"DROPRECEIVING_SERVLET","/tcmIS/fnc/dropshipreceivingmain.do"},
	      {"DROPRECEIVING_SERVLET_HEADER","/tcmIS/fnc/dropshipreceivingmain.do"},
	      {"RELABELING_SERVLET","/tcmIS/fnc/Relabeling?session=Active"},
	      {"PROCESS_DETAIL_URL","/tcmIS/fnc/processdetail?"},
		  {"COST_REPORT","/tcmIS/fnc/betacostreport?"},
		  {"SCHEDULDE_DELIVERY","/tcmIS/fnc/schdelivery?"},
		  {"CANCEL_MR","/tcmIS/fnc/cancel?"},
		  {"WASTE_ORDER","/tcmIS/fnc/wasteorder?"},
		  {"WASTE_ADHOC_REPORT","/tcmIS/fnc/wastereport?"},
		  {"USAGE_ADHOC_REPORT","/tcmIS/fnc/usagereport?"},
		  {"MATERIAL_MATRIX_REPORT","/tcmIS/fnc/matrixreport?"},

		  {"SHIPPINGINFO","No"},
		  {"SHIPPINGINFO_SERVLET","/tcmIS/fnc/shipinfo?session=Active"},
		  {"SHIPPINGINFO_SERVLET_HEADER","/tcmIS/fnc/shipinfo"},
	          {"WEB_ORDER_TRACKING","Yes"},
	          {"WEB_ORDER_TRACKING_SERVLET","/tcmIS/fnc/ordertrackingmain.do"},  
	      {"WEB_REGISTRATION","Yes"}
	    };

	    public FNCServerResourceBundle(){
	        super();
	        addHash(cHash);
	    }

	}
