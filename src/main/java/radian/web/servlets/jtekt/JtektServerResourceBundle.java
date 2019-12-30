package radian.web.servlets.jtekt;
import radian.tcmis.server7.share.helpers.ServerResourceBundle;

public class JtektServerResourceBundle extends ServerResourceBundle {

	  static final Object[][] cHash = {
	      {"LOG_DIR","/logsjtekt"},
	      {"DEBUG","true"},
	      {"DB_CLIENT","JTEKT"},
	      {"ORACLE_DRIVER",""},
	      {"ORACLE_URL",""},
	      {"DB_USER",""},

	      {"RELATIVE_HTML","/tcmIS/msds/jtekt"},
	      {"DB_PASS",""},
	      {"DB_CLIENT_NAME","JTEKT"},
	      {"WEBS_HOME_WWWS",""},
	      {"WEBS_HOME_WWW",""},

	      {"HOME_PAGE","/tcmIS/jtekt/home.do"},
	      {"HOME_IMAGE","/images/buttons/jtekt/jtekt.png"},
	      {"HELP_HTML_PATH","/tcmIS/help/new/"},

	      {"VIEW_MSDS","/tcmIS/jtekt/ViewMsds?"},
	      {"REGISTER_SERVLET","/tcmIS/jtekt/Register"},
	      {"HOME_SERVLET","/tcmIS/jtekt/home.do"},
	      {"JNLP_MAINCLASS","RadianCCJtekt.jar"},
	      {"JNLP_DIRECTORY","/jnlp/jtekt"},
	      {"TEST_JNLP_DIRECTORY","/prereleasejnlp/jtekt"},
	      {"TCMIS_START_CLASS","radian.tcmis.client.client.jtekt.gui.JtektCmisApp"},
	      {"BATCH_REPORT_SERVLET","/tcmIS/jtekt/batchreport?"},

	      {"ADDUSER_SERVLET","/tcmIS/jtekt/Register?useraction=AddUser"},
	      {"CHGUSER_SERVLET","/tcmIS/jtekt/Register?useraction=ChgUser"},
	      {"ADD_SERVLET","/tcmIS/jtekt/Register?useraction=Add"},
	      {"LOGINREG_SERVLET","/tcmIS/jtekt/Register?useraction=Login"},
	      {"CHANGE_SERVLET","/tcmIS/jtekt/Register?useraction=Change"},
	      {"REGISTER_HOME_SERVLET","/tcmIS/jtekt/Register?"},
	      {"TEST_INSTALL_SERVLET","/tcmIS/jtekt/Register?useraction=startingtcmis"},
	      {"BAR_BUILD_SERVLET","/tcmIS/jtekt/Msds?limit=yes"},
	      {"BUILD_SERVLET","/tcmIS/jtekt/Msds?"},
	      {"SERVLET_DIR_WWW",""},
	      {"ERW_TEMPLATE_DIR","/webdata/html/template/"},
	      {"BASE_DIR_TCM_WWW","/webdata/html/"},
	      {"ERW_REPORT_DIR_ACTIVE","/webdata/html/reports/temp/"},
	      {"ERW_REPORT_DIR_BATCH","/webdata/html/reports/"},
	      {"WEBS_HOME_WWW_ERW_ACTIVE","/reports/temp/"},
	      {"WEBS_HOME_WWW_ERW_BATCH","/reports/"},
	      {"CATALOGING_SERVLET","/tcmIS/jtekt/cataloging?"},
	      {"PRINT_SCREEN","/tcmIS/jtekt/printscreen"},
	      {"HELP_SERVLET_PATH","/tcmIS/client/help"},

	      {"DROPRECEIVING","Yes"},
	      {"RELABELING","No"},
	      {"DROPRECEIVING_SERVLET","/tcmIS/jtekt/dropshipreceivingmain.do"},
	      {"DROPRECEIVING_SERVLET_HEADER","/tcmIS/jtekt/dropshipreceivingmain.do"},
	      {"RELABELING_SERVLET","/tcmIS/jtekt/Relabeling?session=Active"},
	      {"PROCESS_DETAIL_URL","/tcmIS/jtekt/processdetail?"},
		  {"COST_REPORT","/tcmIS/jtekt/betacostreport?"},
		  {"SCHEDULDE_DELIVERY","/tcmIS/jtekt/schdelivery?"},
		  {"CANCEL_MR","/tcmIS/jtekt/cancel?"},
		  {"WASTE_ORDER","/tcmIS/jtekt/wasteorder?"},
		  {"WASTE_ADHOC_REPORT","/tcmIS/jtekt/wastereport?"},
		  {"USAGE_ADHOC_REPORT","/tcmIS/jtekt/usagereport?"},
		  {"MATERIAL_MATRIX_REPORT","/tcmIS/jtekt/matrixreport?"},

		  {"SHIPPINGINFO","No"},
		  {"SHIPPINGINFO_SERVLET","/tcmIS/jtekt/shipinfo?session=Active"},
		  {"SHIPPINGINFO_SERVLET_HEADER","/tcmIS/jtekt/shipinfo"},
	          {"WEB_ORDER_TRACKING","Yes"},
	          {"WEB_ORDER_TRACKING_SERVLET","/tcmIS/jtekt/ordertrackingmain.do"},
	      {"WEB_REGISTRATION","Yes"},
		  {"NEW_WEB_APPLICATION","Yes"}
	    };
	  
	    public JtektServerResourceBundle(){
	        super();
	        addHash(cHash);
	    }

	}


