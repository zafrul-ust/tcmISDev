package radian.web.servlets.ael;

import radian.tcmis.server7.share.helpers.ServerResourceBundle;

public class AeroEcoServerResourceBundle  extends ServerResourceBundle {

    static final Object[][] cHash = {
      {"LOG_DIR","/logssd"},
      {"DEBUG","true"},
      {"DB_CLIENT","AeroEco"},
      {"ORACLE_DRIVER",""},
      {"ORACLE_URL",""},
      {"DB_USER",""},

      {"RELATIVE_HTML","/tcmIS/msds/ael"},
      {"DB_PASS",""},
      {"DB_CLIENT_NAME","AeroEco"},
      {"WEBS_HOME_WWWS",""},
      {"WEBS_HOME_WWW",""},

      {"HOME_PAGE","/tcmIS/ael/Home"},
      {"HOME_IMAGE","/images/buttons/ael/ael.gif"},
      {"HELP_HTML_PATH","/tcmIS/help/new/"},

      {"VIEW_MSDS","/tcmIS/ael/ViewMsds?"},
      {"REGISTER_SERVLET","/tcmIS/ael/Register"},
      {"HOME_SERVLET","/tcmIS/ael/Home"},
      {"JNLP_MAINCLASS","RadianCCAeroEco.jar"},
      {"JNLP_DIRECTORY","/jnlp/ael"},
      {"TEST_JNLP_DIRECTORY","/prereleasejnlp/ael"},
      {"TCMIS_START_CLASS","radian.tcmis.client.client.ael.gui.AeroEcoCmisApp"},
      {"BATCH_REPORT_SERVLET","/tcmIS/ael/batchreport?"},

      {"ADDUSER_SERVLET","/tcmIS/ael/Register?useraction=AddUser"},
      {"CHGUSER_SERVLET","/tcmIS/ael/Register?useraction=ChgUser"},
      {"ADD_SERVLET","/tcmIS/ael/Register?useraction=Add"},
      {"LOGINREG_SERVLET","/tcmIS/ael/Register?useraction=Login"},
      {"CHANGE_SERVLET","/tcmIS/ael/Register?useraction=Change"},
      {"REGISTER_HOME_SERVLET","/tcmIS/ael/Register?"},
      {"TEST_INSTALL_SERVLET","/tcmIS/ael/Register?useraction=startingtcmis"},
      {"BAR_BUILD_SERVLET","/tcmIS/ael/Msds?limit=yes"},
      {"BUILD_SERVLET","/tcmIS/ael/Msds?"},
      {"SERVLET_DIR_WWW",""},
      {"ERW_TEMPLATE_DIR","/webdata/html/template/"},
      {"BASE_DIR_TCM_WWW","/webdata/html/"},
      {"ERW_REPORT_DIR_ACTIVE","/webdata/html/reports/temp/"},
      {"ERW_REPORT_DIR_BATCH","/webdata/html/reports/"},
      {"WEBS_HOME_WWW_ERW_ACTIVE","/reports/temp/"},
      {"WEBS_HOME_WWW_ERW_BATCH","/reports/"},
      {"CATALOGING_SERVLET","/tcmIS/ael/cataloging?"},
      {"PRINT_SCREEN","/tcmIS/ael/printscreen"},
      {"HELP_SERVLET_PATH","/tcmIS/client/help"},

      {"DROPRECEIVING_SERVLET","/tcmIS/ael/dropshipreceivingmain.do"},
      {"DROPRECEIVING_SERVLET_HEADER","/tcmIS/ael/dropshipreceivingmain.do"},
      {"RELABELING_SERVLET","/tcmIS/ael/Relabeling?session=Active"},

      {"DROPRECEIVING","No"},
      {"RELABELING","No"},
      {"DROPRECEIVING_SERVLET","/tcmIS/ael/dropshipreceivingmain.do"},
      {"DROPRECEIVING_SERVLET_HEADER","/tcmIS/ael/dropshipreceivingmain.do"},
      {"RELABELING_SERVLET","/tcmIS/ael/Relabeling?session=Active"},

	  {"COST_REPORT","/tcmIS/ael/betacostreport?"},
	  {"SCHEDULDE_DELIVERY","/tcmIS/ael/schdelivery?"},
	  {"CANCEL_MR","/tcmIS/ael/cancel?"},
	  {"WASTE_ORDER","/tcmIS/ael/wasteorder?"},
	  {"WASTE_ADHOC_REPORT","/tcmIS/ael/wastereport?"},
	  {"USAGE_ADHOC_REPORT","/tcmIS/ael/usagereport?"},
	  {"MATERIAL_MATRIX_REPORT","/tcmIS/ael/matrixreport?"},

	  {"SHIPPINGINFO","No"},
	  {"SHIPPINGINFO_SERVLET","/tcmIS/ael/shipinfo?session=Active"},
	  {"SHIPPINGINFO_SERVLET_HEADER","/tcmIS/ael/shipinfo"},
          {"WEB_ORDER_TRACKING","Yes"},
          {"WEB_ORDER_TRACKING_SERVLET","/tcmIS/ael/ordertrackingmain.do"},
       {"WEB_REGISTRATION","Yes"}
    };

    public AeroEcoServerResourceBundle(){
        super();
        addHash(cHash);
    }

}
