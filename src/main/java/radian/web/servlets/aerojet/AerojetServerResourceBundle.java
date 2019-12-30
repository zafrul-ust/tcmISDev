package radian.web.servlets.aerojet;

import radian.tcmis.server7.share.helpers.ServerResourceBundle;

public class AerojetServerResourceBundle  extends ServerResourceBundle {

    static final Object[][] cHash = {
      {"LOG_DIR","/logsaerojet"},
      {"DEBUG","true"},
      {"DB_CLIENT","Aerojet"},
      {"ORACLE_DRIVER",""},
      {"ORACLE_URL",""},
      {"DB_USER",""},

      {"RELATIVE_HTML","/tcmIS/msds/aerojet"},
      {"DB_PASS",""},
      {"DB_CLIENT_NAME","Aerojet"},
      {"WEBS_HOME_WWWS",""},
      {"WEBS_HOME_WWW",""},

      {"HOME_PAGE","/tcmIS/aerojet/Home"},
      {"HOME_IMAGE","/images/buttons/aerojet/aerojet.png"},
      {"HELP_HTML_PATH","/tcmIS/help/new/"},

      {"VIEW_MSDS","/tcmIS/aerojet/ViewMsds?"},
 /*     {"REGISTER_SERVLET","/tcmIS/aerojet/Register"},
      {"HOME_SERVLET","/tcmIS/aerojet/Home"},
      {"JNLP_MAINCLASS","RadianCCLMCO.jar"},
      {"JNLP_DIRECTORY","/jnlp/aerojet"},
      {"TEST_JNLP_DIRECTORY","/prereleasejnlp/aerojet"},
      {"TCMIS_START_CLASS","radian.tcmis.client.client.lmco.gui.LMCOCmisApp"},
      {"BATCH_REPORT_SERVLET","/tcmIS/aerojet/batchreport?"},

      {"ADDUSER_SERVLET","/tcmIS/aerojet/Register?useraction=AddUser"},
      {"CHGUSER_SERVLET","/tcmIS/aerojet/Register?useraction=ChgUser"},
      {"ADD_SERVLET","/tcmIS/aerojet/Register?useraction=Add"},
      {"LOGINREG_SERVLET","/tcmIS/aerojet/Register?useraction=Login"},
      {"CHANGE_SERVLET","/tcmIS/aerojet/Register?useraction=Change"},
      {"REGISTER_HOME_SERVLET","/tcmIS/aerojet/Register?"},
      {"TEST_INSTALL_SERVLET","/tcmIS/aerojet/Register?useraction=startingtcmis"},
      {"BAR_BUILD_SERVLET","/tcmIS/aerojet/Msds?limit=yes"},
      {"BUILD_SERVLET","/tcmIS/aerojet/Msds?"},
      {"SERVLET_DIR_WWW",""},
      {"ERW_TEMPLATE_DIR","/webdata/html/template/"},
      {"BASE_DIR_TCM_WWW","/webdata/html/"},
      {"ERW_REPORT_DIR_ACTIVE","/webdata/html/reports/temp/"},
      {"ERW_REPORT_DIR_BATCH","/webdata/html/reports/"},
      {"WEBS_HOME_WWW_ERW_ACTIVE","/reports/temp/"},
      {"WEBS_HOME_WWW_ERW_BATCH","/reports/"},
      {"CATALOGING_SERVLET","/tcmIS/aerojet/cataloging?"},
      {"PRINT_SCREEN","/tcmIS/aerojet/printscreen"},
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
 */     {"WEB_REGISTRATION","Yes"}
    };

    public AerojetServerResourceBundle(){
        super();
        addHash(cHash);
    }

}
