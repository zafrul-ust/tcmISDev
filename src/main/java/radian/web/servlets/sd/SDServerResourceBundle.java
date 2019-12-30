package radian.web.servlets.sd;

import radian.tcmis.server7.share.helpers.ServerResourceBundle;

public class SDServerResourceBundle  extends ServerResourceBundle {

    static final Object[][] cHash = {
      {"LOG_DIR","/logssd"},
      {"DEBUG","true"},
      {"DB_CLIENT","SD"},
      {"ORACLE_DRIVER",""},
      {"ORACLE_URL",""},
      {"DB_USER",""},

      {"RELATIVE_HTML","/tcmIS/msds/sd"},
      {"DB_PASS",""},
      {"DB_CLIENT_NAME","SD"},
      {"WEBS_HOME_WWWS",""},
      {"WEBS_HOME_WWW",""},

      {"HOME_PAGE","/tcmIS/sd/Home"},
      {"HOME_IMAGE","/images/buttons/sd/sd.gif"},
      {"HELP_HTML_PATH","/tcmIS/help/new/"},

      {"VIEW_MSDS","/tcmIS/sd/ViewMsds?"},
      {"REGISTER_SERVLET","/tcmIS/sd/Register"},
      {"HOME_SERVLET","/tcmIS/sd/Home"},
      {"JNLP_MAINCLASS","RadianCCSD.jar"},
      {"JNLP_DIRECTORY","/jnlp/sd"},
      {"TEST_JNLP_DIRECTORY","/prereleasejnlp/sd"},
      {"TCMIS_START_CLASS","radian.tcmis.client.client.sd.gui.SDCmisApp"},
      {"BATCH_REPORT_SERVLET","/tcmIS/sd/batchreport?"},

      {"ADDUSER_SERVLET","/tcmIS/sd/Register?useraction=AddUser"},
      {"CHGUSER_SERVLET","/tcmIS/sd/Register?useraction=ChgUser"},
      {"ADD_SERVLET","/tcmIS/sd/Register?useraction=Add"},
      {"LOGINREG_SERVLET","/tcmIS/sd/Register?useraction=Login"},
      {"CHANGE_SERVLET","/tcmIS/sd/Register?useraction=Change"},
      {"REGISTER_HOME_SERVLET","/tcmIS/sd/Register?"},
      {"TEST_INSTALL_SERVLET","/tcmIS/sd/Register?useraction=startingtcmis"},
      {"BAR_BUILD_SERVLET","/tcmIS/sd/Msds?limit=yes"},
      {"BUILD_SERVLET","/tcmIS/sd/Msds?"},
      {"SERVLET_DIR_WWW",""},
      {"ERW_TEMPLATE_DIR","/webdata/html/template/"},
      {"BASE_DIR_TCM_WWW","/webdata/html/"},
      {"ERW_REPORT_DIR_ACTIVE","/webdata/html/reports/temp/"},
      {"ERW_REPORT_DIR_BATCH","/webdata/html/reports/"},
      {"WEBS_HOME_WWW_ERW_ACTIVE","/reports/temp/"},
      {"WEBS_HOME_WWW_ERW_BATCH","/reports/"},
      {"CATALOGING_SERVLET","/tcmIS/sd/cataloging?"},
      {"PRINT_SCREEN","/tcmIS/sd/printscreen"},
      {"HELP_SERVLET_PATH","/tcmIS/client/help"},

      {"DROPRECEIVING_SERVLET","/tcmIS/sd/dropshipreceivingmain.do"},
      {"DROPRECEIVING_SERVLET_HEADER","/tcmIS/sd/dropshipreceivingmain.do"},
      {"RELABELING_SERVLET","/tcmIS/sd/Relabeling?session=Active"},

      {"DROPRECEIVING","No"},
      {"RELABELING","No"},
      {"DROPRECEIVING_SERVLET","/tcmIS/sd/dropshipreceivingmain.do"},
      {"DROPRECEIVING_SERVLET_HEADER","/tcmIS/sd/dropshipreceivingmain.do"},
      {"RELABELING_SERVLET","/tcmIS/sd/Relabeling?session=Active"},

	  {"COST_REPORT","/tcmIS/sd/betacostreport?"},
	  {"SCHEDULDE_DELIVERY","/tcmIS/sd/schdelivery?"},
	  {"CANCEL_MR","/tcmIS/sd/cancel?"},
	  {"WASTE_ORDER","/tcmIS/sd/wasteorder?"},
	  {"WASTE_ADHOC_REPORT","/tcmIS/sd/wastereport?"},
	  {"USAGE_ADHOC_REPORT","/tcmIS/sd/usagereport?"},
	  {"MATERIAL_MATRIX_REPORT","/tcmIS/sd/matrixreport?"},

	  {"SHIPPINGINFO","No"},
	  {"SHIPPINGINFO_SERVLET","/tcmIS/sd/shipinfo?session=Active"},
	  {"SHIPPINGINFO_SERVLET_HEADER","/tcmIS/sd/shipinfo"},
          {"WEB_ORDER_TRACKING","Yes"},
          {"WEB_ORDER_TRACKING_SERVLET","/tcmIS/sd/ordertrackingmain.do"},
       {"WEB_REGISTRATION","Yes"}
    };

    public SDServerResourceBundle(){
        super();
        addHash(cHash);
    }

}
