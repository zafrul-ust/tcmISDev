package radian.web.servlets.magna;
import radian.tcmis.server7.share.helpers.ServerResourceBundle;

public class MagnaServerResourceBundle extends ServerResourceBundle {

	  static final Object[][] cHash = {
	      {"LOG_DIR","/logsmagna"},
	      {"DEBUG","true"},
	      {"DB_CLIENT","MAGNA"},
	      {"ORACLE_DRIVER",""},
	      {"ORACLE_URL",""},
	      {"DB_USER",""},

	      {"RELATIVE_HTML","/tcmIS/msds/magna"},
	      {"DB_PASS",""},
	      {"DB_CLIENT_NAME","MAGNA"},
	      {"WEBS_HOME_WWWS",""},
	      {"WEBS_HOME_WWW",""},

	      {"HOME_PAGE","/tcmIS/magna/home.do"},
	      {"HOME_IMAGE","/images/buttons/magna/magna.png"},
	      {"HELP_HTML_PATH","/tcmIS/help/new/"},

	      {"VIEW_MSDS","/tcmIS/magna/ViewMsds?"},
	      {"REGISTER_SERVLET","/tcmIS/magna/Register"},
	      {"HOME_SERVLET","/tcmIS/magna/home.do"},
	      {"JNLP_MAINCLASS","RadianCCMagna.jar"},
	      {"JNLP_DIRECTORY","/jnlp/magna"},
	      {"TEST_JNLP_DIRECTORY","/prereleasejnlp/magna"},
	      {"TCMIS_START_CLASS","radian.tcmis.client.client.magna.gui.MagnaCmisApp"},
	      {"BATCH_REPORT_SERVLET","/tcmIS/magna/batchreport?"},

	      {"ADDUSER_SERVLET","/tcmIS/magna/Register?useraction=AddUser"},
	      {"CHGUSER_SERVLET","/tcmIS/magna/Register?useraction=ChgUser"},
	      {"ADD_SERVLET","/tcmIS/magna/Register?useraction=Add"},
	      {"LOGINREG_SERVLET","/tcmIS/magna/Register?useraction=Login"},
	      {"CHANGE_SERVLET","/tcmIS/magna/Register?useraction=Change"},
	      {"REGISTER_HOME_SERVLET","/tcmIS/magna/Register?"},
	      {"TEST_INSTALL_SERVLET","/tcmIS/magna/Register?useraction=startingtcmis"},
	      {"BAR_BUILD_SERVLET","/tcmIS/magna/Msds?limit=yes"},
	      {"BUILD_SERVLET","/tcmIS/magna/Msds?"},
	      {"SERVLET_DIR_WWW",""},
	      {"ERW_TEMPLATE_DIR","/webdata/html/template/"},
	      {"BASE_DIR_TCM_WWW","/webdata/html/"},
	      {"ERW_REPORT_DIR_ACTIVE","/webdata/html/reports/temp/"},
	      {"ERW_REPORT_DIR_BATCH","/webdata/html/reports/"},
	      {"WEBS_HOME_WWW_ERW_ACTIVE","/reports/temp/"},
	      {"WEBS_HOME_WWW_ERW_BATCH","/reports/"},
	      {"CATALOGING_SERVLET","/tcmIS/magna/cataloging?"},
	      {"PRINT_SCREEN","/tcmIS/magna/printscreen"},
	      {"HELP_SERVLET_PATH","/tcmIS/client/help"},

	      {"DROPRECEIVING","Yes"},
	      {"RELABELING","No"},
	      {"DROPRECEIVING_SERVLET","/tcmIS/magna/dropshipreceivingmain.do"},
	      {"DROPRECEIVING_SERVLET_HEADER","/tcmIS/magna/dropshipreceivingmain.do"},
	      {"RELABELING_SERVLET","/tcmIS/magna/Relabeling?session=Active"},
	      {"PROCESS_DETAIL_URL","/tcmIS/magna/processdetail?"},
		  {"COST_REPORT","/tcmIS/magna/betacostreport?"},
		  {"SCHEDULDE_DELIVERY","/tcmIS/magna/schdelivery?"},
		  {"CANCEL_MR","/tcmIS/magna/cancel?"},
		  {"WASTE_ORDER","/tcmIS/magna/wasteorder?"},
		  {"WASTE_ADHOC_REPORT","/tcmIS/magna/wastereport?"},
		  {"USAGE_ADHOC_REPORT","/tcmIS/magna/usagereport?"},
		  {"MATERIAL_MATRIX_REPORT","/tcmIS/magna/matrixreport?"},

		  {"SHIPPINGINFO","No"},
		  {"SHIPPINGINFO_SERVLET","/tcmIS/magna/shipinfo?session=Active"},
		  {"SHIPPINGINFO_SERVLET_HEADER","/tcmIS/magna/shipinfo"},
	          {"WEB_ORDER_TRACKING","Yes"},
	          {"WEB_ORDER_TRACKING_SERVLET","/tcmIS/magna/ordertrackingmain.do"},
	      {"WEB_REGISTRATION","Yes"},
		  {"NEW_WEB_APPLICATION","Yes"}
	    };
	  
	    public MagnaServerResourceBundle(){
	        super();
	        addHash(cHash);
	    }

	}


