package radian.web.servlets.merck;

import radian.tcmis.server7.share.helpers.ServerResourceBundle;

public class MerckServerResourceBundle  extends ServerResourceBundle {

    static final Object[][] cHash = {
      {"LOG_DIR","/logsmerck"},
      {"DEBUG","true"},
      {"DB_CLIENT","Merck"},
      {"ORACLE_DRIVER",""},
      {"ORACLE_URL",""},
      {"DB_USER",""},

      {"RELATIVE_HTML","/tcmIS/msds/merck"},
      {"DB_PASS",""},
      {"DB_CLIENT_NAME","Merck"},
      {"WEBS_HOME_WWWS",""},
      {"WEBS_HOME_WWW",""},

      {"HOME_PAGE","/tcmIS/merck/Home"},
      {"HOME_IMAGE","/images/buttons/merck/merck.gif"},
      {"HELP_HTML_PATH","/tcmIS/help/new/"},

      {"VIEW_MSDS","/tcmIS/merck/ViewMsds?"},
      {"REGISTER_SERVLET","/tcmIS/merck/Register"},
      {"HOME_SERVLET","/tcmIS/merck/Home"},
      {"JNLP_MAINCLASS","RadianCCMerck.jar"},
      {"JNLP_DIRECTORY","/jnlp/merck"},
      {"TEST_JNLP_DIRECTORY","/prereleasejnlp/merck"},
      {"TCMIS_START_CLASS","radian.tcmis.client.client.merck.gui.MerckCmisApp"},
      {"BATCH_REPORT_SERVLET","/tcmIS/merck/batchreport?"},

      {"ADDUSER_SERVLET","/tcmIS/merck/Register?useraction=AddUser"},
      {"CHGUSER_SERVLET","/tcmIS/merck/Register?useraction=ChgUser"},
      {"ADD_SERVLET","/tcmIS/merck/Register?useraction=Add"},
      {"LOGINREG_SERVLET","/tcmIS/merck/Register?useraction=Login"},
      {"CHANGE_SERVLET","/tcmIS/merck/Register?useraction=Change"},
      {"REGISTER_HOME_SERVLET","/tcmIS/merck/Register?"},
      {"TEST_INSTALL_SERVLET","/tcmIS/merck/Register?useraction=startingtcmis"},
      {"BAR_BUILD_SERVLET","/tcmIS/merck/Msds?limit=yes"},
      {"BUILD_SERVLET","/tcmIS/merck/Msds?"},
      {"SERVLET_DIR_WWW",""},
      {"ERW_TEMPLATE_DIR","/webdata/html/template/"},
      {"BASE_DIR_TCM_WWW","/webdata/html/"},
      {"ERW_REPORT_DIR_ACTIVE","/webdata/html/reports/temp/"},
      {"ERW_REPORT_DIR_BATCH","/webdata/html/reports/"},
      {"WEBS_HOME_WWW_ERW_ACTIVE","/reports/temp/"},
      {"WEBS_HOME_WWW_ERW_BATCH","/reports/"},
      {"CATALOGING_SERVLET","/tcmIS/merck/cataloging?"},
      {"PRINT_SCREEN","/tcmIS/merck/printscreen"},
      {"HELP_SERVLET_PATH","/tcmIS/client/help"},

      {"DROPRECEIVING","Yes"},
      {"RELABELING","No"},
      {"DROPRECEIVING_SERVLET","/tcmIS/merck/dropshipreceivingmain.do"},
      {"DROPRECEIVING_SERVLET_HEADER","/tcmIS/merck/dropshipreceivingmain.do"},
      {"RELABELING_SERVLET","/tcmIS/merck/Relabeling?session=Active"},
      {"PROCESS_DETAIL_URL","/tcmIS/merck/processdetail?"},
	  {"COST_REPORT","/tcmIS/merck/betacostreport?"},
	  {"SCHEDULDE_DELIVERY","/tcmIS/merck/schdelivery?"},
	  {"CANCEL_MR","/tcmIS/merck/cancel?"},
	  {"WASTE_ORDER","/tcmIS/merck/wasteorder?"},
	  {"WASTE_ADHOC_REPORT","/tcmIS/merck/wastereport?"},
	  {"USAGE_ADHOC_REPORT","/tcmIS/merck/usagereport?"},
	  {"MATERIAL_MATRIX_REPORT","/tcmIS/merck/matrixreport?"},

	  {"SHIPPINGINFO","No"},
	  {"SHIPPINGINFO_SERVLET","/tcmIS/merck/shipinfo?session=Active"},
	  {"SHIPPINGINFO_SERVLET_HEADER","/tcmIS/merck/shipinfo"},
          {"WEB_ORDER_TRACKING","Yes"},
          {"WEB_ORDER_TRACKING_SERVLET","/tcmIS/merck/ordertrackingmain.do"},
      {"WEB_REGISTRATION","Yes"}
    };

    public MerckServerResourceBundle(){
        super();
        addHash(cHash);
    }

}
