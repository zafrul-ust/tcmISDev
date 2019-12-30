package radian.web.servlets.aim;
import radian.tcmis.server7.share.helpers.ServerResourceBundle;

public class AIMServerResourceBundle extends ServerResourceBundle {

	  static final Object[][] cHash = {
	      {"LOG_DIR","/logsaim"},
	      {"DEBUG","true"},
	      {"DB_CLIENT","AIM_NORWAY"},
	      {"ORACLE_DRIVER",""},
	      {"ORACLE_URL",""},
	      {"DB_USER",""},

	      {"RELATIVE_HTML","/tcmIS/msds/aim"},
	      {"DB_PASS",""},
	      {"DB_CLIENT_NAME","AIM_NORWAY"},
	      {"WEBS_HOME_WWWS",""},
	      {"WEBS_HOME_WWW",""},

	      {"HOME_PAGE","/tcmIS/aim/home.do"},
	      {"HOME_IMAGE","/images/buttons/aim/aim.jpg"},
	      {"HELP_HTML_PATH","/tcmIS/help/new/"},

	      {"VIEW_MSDS","/tcmIS/aim/ViewMsds?"},
	      {"REGISTER_SERVLET","/tcmIS/aim/Register"},
	      {"HOME_SERVLET","/tcmIS/aim/home.do"},
	      {"JNLP_MAINCLASS","RadianCCAIM.jar"},
	      {"JNLP_DIRECTORY","/jnlp/aim"},
	      {"TEST_JNLP_DIRECTORY","/prereleasejnlp/aim"},
	      {"TCMIS_START_CLASS","radian.tcmis.client.client.aim.gui.AIMCmisApp"},
	      {"BATCH_REPORT_SERVLET","/tcmIS/aim/batchreport?"},

	      {"ADDUSER_SERVLET","/tcmIS/aim/Register?useraction=AddUser"},
	      {"CHGUSER_SERVLET","/tcmIS/aim/Register?useraction=ChgUser"},
	      {"ADD_SERVLET","/tcmIS/aim/Register?useraction=Add"},
	      {"LOGINREG_SERVLET","/tcmIS/aim/Register?useraction=Login"},
	      {"CHANGE_SERVLET","/tcmIS/aim/Register?useraction=Change"},
	      {"REGISTER_HOME_SERVLET","/tcmIS/aim/Register?"},
	      {"TEST_INSTALL_SERVLET","/tcmIS/aim/Register?useraction=startingtcmis"},
	      {"BAR_BUILD_SERVLET","/tcmIS/aim/Msds?limit=yes"},
	      {"BUILD_SERVLET","/tcmIS/aim/Msds?"},
	      {"SERVLET_DIR_WWW",""},
	      {"ERW_TEMPLATE_DIR","/webdata/html/template/"},
	      {"BASE_DIR_TCM_WWW","/webdata/html/"},
	      {"ERW_REPORT_DIR_ACTIVE","/webdata/html/reports/temp/"},
	      {"ERW_REPORT_DIR_BATCH","/webdata/html/reports/"},
	      {"WEBS_HOME_WWW_ERW_ACTIVE","/reports/temp/"},
	      {"WEBS_HOME_WWW_ERW_BATCH","/reports/"},
	      {"CATALOGING_SERVLET","/tcmIS/aim/cataloging?"},
	      {"PRINT_SCREEN","/tcmIS/aim/printscreen"},
	      {"HELP_SERVLET_PATH","/tcmIS/client/help"},

	      {"DROPRECEIVING","Yes"},
	      {"RELABELING","No"},
	      {"DROPRECEIVING_SERVLET","/tcmIS/aim/dropshipreceivingmain.do"},
	      {"DROPRECEIVING_SERVLET_HEADER","/tcmIS/aim/dropshipreceivingmain.do"},
	      {"RELABELING_SERVLET","/tcmIS/aim/Relabeling?session=Active"},
	      {"PROCESS_DETAIL_URL","/tcmIS/aim/processdetail?"},
		  {"COST_REPORT","/tcmIS/aim/betacostreport?"},
		  {"SCHEDULDE_DELIVERY","/tcmIS/aim/schdelivery?"},
		  {"CANCEL_MR","/tcmIS/aim/cancel?"},
		  {"WASTE_ORDER","/tcmIS/aim/wasteorder?"},
		  {"WASTE_ADHOC_REPORT","/tcmIS/aim/wastereport?"},
		  {"USAGE_ADHOC_REPORT","/tcmIS/aim/usagereport?"},
		  {"MATERIAL_MATRIX_REPORT","/tcmIS/aim/matrixreport?"},

		  {"SHIPPINGINFO","No"},
		  {"SHIPPINGINFO_SERVLET","/tcmIS/aim/shipinfo?session=Active"},
		  {"SHIPPINGINFO_SERVLET_HEADER","/tcmIS/aim/shipinfo"},
	          {"WEB_ORDER_TRACKING","Yes"},
	          {"WEB_ORDER_TRACKING_SERVLET","/tcmIS/aim/ordertrackingmain.do"},
	      {"WEB_REGISTRATION","Yes"},
		  {"NEW_WEB_APPLICATION","Yes"}
	    };
	  
	    public AIMServerResourceBundle(){
	        super();
	        addHash(cHash);
	    }

	}


