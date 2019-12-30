package radian.web.servlets.lmco;

import radian.tcmis.server7.share.helpers.ServerResourceBundle;

public class LMCOServerResourceBundle  extends ServerResourceBundle {

    static final Object[][] cHash = {
      {"LOG_DIR","/logslmco"},
      {"DEBUG","true"},
      {"DB_CLIENT","LMCO"},
      {"ORACLE_DRIVER",""},
      {"ORACLE_URL",""},
      {"DB_USER",""},

      {"RELATIVE_HTML","/tcmIS/msds/lmco"},
      {"DB_PASS",""},
      {"DB_CLIENT_NAME","LMCO"},
      {"WEBS_HOME_WWWS",""},
      {"WEBS_HOME_WWW",""},

      {"HOME_PAGE","/tcmIS/lmco/Home"},
      {"HOME_IMAGE","/images/buttons/lmco/lmco.gif"},
      {"HELP_HTML_PATH","/tcmIS/help/new/"},

      {"VIEW_MSDS","/tcmIS/lmco/ViewMsds?"},
      {"REGISTER_SERVLET","/tcmIS/lmco/Register"},
      {"HOME_SERVLET","/tcmIS/lmco/Home"},
      {"JNLP_MAINCLASS","RadianCCLMCO.jar"},
      {"JNLP_DIRECTORY","/jnlp/lmco"},
      {"TEST_JNLP_DIRECTORY","/prereleasejnlp/lmco"},
      {"TCMIS_START_CLASS","radian.tcmis.client.client.lmco.gui.LMCOCmisApp"},
      {"BATCH_REPORT_SERVLET","/tcmIS/lmco/batchreport?"},

      {"ADDUSER_SERVLET","/tcmIS/lmco/Register?useraction=AddUser"},
      {"CHGUSER_SERVLET","/tcmIS/lmco/Register?useraction=ChgUser"},
      {"ADD_SERVLET","/tcmIS/lmco/Register?useraction=Add"},
      {"LOGINREG_SERVLET","/tcmIS/lmco/Register?useraction=Login"},
      {"CHANGE_SERVLET","/tcmIS/lmco/Register?useraction=Change"},
      {"REGISTER_HOME_SERVLET","/tcmIS/lmco/Register?"},
      {"TEST_INSTALL_SERVLET","/tcmIS/lmco/Register?useraction=startingtcmis"},
      {"BAR_BUILD_SERVLET","/tcmIS/lmco/Msds?limit=yes"},
      {"BUILD_SERVLET","/tcmIS/lmco/Msds?"},
      {"SERVLET_DIR_WWW",""},
      {"ERW_TEMPLATE_DIR","/webdata/html/template/"},
      {"BASE_DIR_TCM_WWW","/webdata/html/"},
      {"ERW_REPORT_DIR_ACTIVE","/webdata/html/reports/temp/"},
      {"ERW_REPORT_DIR_BATCH","/webdata/html/reports/"},
      {"WEBS_HOME_WWW_ERW_ACTIVE","/reports/temp/"},
      {"WEBS_HOME_WWW_ERW_BATCH","/reports/"},
      {"CATALOGING_SERVLET","/tcmIS/lmco/cataloging?"},
      {"PRINT_SCREEN","/tcmIS/lmco/printscreen"},
      {"HELP_SERVLET_PATH","/tcmIS/client/help"},

      {"DROPRECEIVING","Yes"},
      {"RELABELING","No"},
      {"DROPRECEIVING_SERVLET","/tcmIS/lmco/dropshipreceivingmain.do"},
      {"DROPRECEIVING_SERVLET_HEADER","/tcmIS/lmco/dropshipreceivingmain.do"},
      {"RELABELING_SERVLET","/tcmIS/lmco/Relabeling?session=Active"},
      {"PROCESS_DETAIL_URL","/tcmIS/lmco/processdetail?"},
	  {"COST_REPORT","/tcmIS/lmco/betacostreport?"},
	  {"SCHEDULDE_DELIVERY","/tcmIS/lmco/schdelivery?"},
	  {"CANCEL_MR","/tcmIS/lmco/cancel?"},
	  {"WASTE_ORDER","/tcmIS/lmco/wasteorder?"},
	  {"WASTE_ADHOC_REPORT","/tcmIS/lmco/wastereport?"},
	  {"USAGE_ADHOC_REPORT","/tcmIS/lmco/usagereport?"},
	  {"MATERIAL_MATRIX_REPORT","/tcmIS/lmco/matrixreport?"},

	  {"SHIPPINGINFO","No"},
	  {"SHIPPINGINFO_SERVLET","/tcmIS/lmco/shipinfo?session=Active"},
	  {"SHIPPINGINFO_SERVLET_HEADER","/tcmIS/lmco/shipinfo"},
          {"WEB_ORDER_TRACKING","Yes"},
          {"WEB_ORDER_TRACKING_SERVLET","/tcmIS/lmco/ordertrackingmain.do"},
      {"WEB_REGISTRATION","Yes"}
    };

    public LMCOServerResourceBundle(){
        super();
        addHash(cHash);
    }

}
