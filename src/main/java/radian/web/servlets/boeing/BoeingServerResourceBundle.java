package radian.web.servlets.boeing;

import radian.tcmis.server7.share.helpers.ServerResourceBundle;

public class BoeingServerResourceBundle  extends ServerResourceBundle {

    static final Object[][] cHash = {
      {"LOG_DIR","/logssd"},
      {"DEBUG","true"},
      {"DB_CLIENT","ULA"},
      {"ORACLE_DRIVER",""},
      {"ORACLE_URL",""},
      {"DB_USER",""},

      {"RELATIVE_HTML","/tcmIS/msds/ula"},
      {"DB_PASS",""},
      {"DB_CLIENT_NAME","Boeing"},
      {"WEBS_HOME_WWWS",""},
      {"WEBS_HOME_WWW",""},

      {"HOME_PAGE","/tcmIS/ula/Home"},
      {"HOME_IMAGE","/images/buttons/boeing/boeing.gif"},
      {"HELP_HTML_PATH","/tcmIS/help/new/"},

      {"VIEW_MSDS","/tcmIS/ula/ViewMsds?"},
      {"REGISTER_SERVLET","/tcmIS/ula/Register"},
      {"HOME_SERVLET","/tcmIS/ula/Home"},
      {"JNLP_MAINCLASS","RadianCCBoeing.jar"},
      {"JNLP_DIRECTORY","/jnlp/boeing"},
      {"TEST_JNLP_DIRECTORY","/prereleasejnlp/boeing"},
      {"TCMIS_START_CLASS","radian.tcmis.client.client.boeing.gui.BoeingCmisApp"},
      {"BATCH_REPORT_SERVLET","/tcmIS/ula/batchreport?"},

      {"ADDUSER_SERVLET","/tcmIS/ula/Register?useraction=AddUser"},
      {"CHGUSER_SERVLET","/tcmIS/ula/Register?useraction=ChgUser"},
      {"ADD_SERVLET","/tcmIS/ula/Register?useraction=Add"},
      {"LOGINREG_SERVLET","/tcmIS/ula/Register?useraction=Login"},
      {"CHANGE_SERVLET","/tcmIS/ula/Register?useraction=Change"},
      {"REGISTER_HOME_SERVLET","/tcmIS/ula/Register?"},
      {"TEST_INSTALL_SERVLET","/tcmIS/ula/Register?useraction=startingtcmis"},
      {"BAR_BUILD_SERVLET","/tcmIS/ula/Msds?limit=yes"},
      {"BUILD_SERVLET","/tcmIS/ula/Msds?"},
      {"SERVLET_DIR_WWW",""},
      {"ERW_TEMPLATE_DIR","/webdata/html/template/"},
      {"BASE_DIR_TCM_WWW","/webdata/html/"},
      {"ERW_REPORT_DIR_ACTIVE","/webdata/html/reports/temp/"},
      {"ERW_REPORT_DIR_BATCH","/webdata/html/reports/"},
      {"WEBS_HOME_WWW_ERW_ACTIVE","/reports/temp/"},
      {"WEBS_HOME_WWW_ERW_BATCH","/reports/"},
      {"CATALOGING_SERVLET","/tcmIS/ula/cataloging?"},
      {"PRINT_SCREEN","/tcmIS/ula/printscreen"},
      {"HELP_SERVLET_PATH","/tcmIS/client/help"},

      {"DROPRECEIVING_SERVLET","/tcmIS/ula/dropshipreceivingmain.do"},
      {"DROPRECEIVING_SERVLET_HEADER","/tcmIS/ula/dropshipreceivingmain.do"},
      {"RELABELING_SERVLET","/tcmIS/ula/Relabeling?session=Active"},

      {"DROPRECEIVING","No"},
      {"RELABELING","No"},
      {"DROPRECEIVING_SERVLET","/tcmIS/ula/dropshipreceivingmain.do"},
      {"DROPRECEIVING_SERVLET_HEADER","/tcmIS/ula/dropshipreceivingmain.do"},
      {"RELABELING_SERVLET","/tcmIS/ula/Relabeling?session=Active"},

	  {"COST_REPORT","/tcmIS/ula/betacostreport?"},
	  {"SCHEDULDE_DELIVERY","/tcmIS/ula/schdelivery?"},
	  {"CANCEL_MR","/tcmIS/ula/cancel?"},
	  {"WASTE_ORDER","/tcmIS/ula/wasteorder?"},
	  {"WASTE_ADHOC_REPORT","/tcmIS/ula/wastereport?"},
	  {"USAGE_ADHOC_REPORT","/tcmIS/ula/usagereport?"},
	  {"MATERIAL_MATRIX_REPORT","/tcmIS/ula/matrixreport?"},

	  {"SHIPPINGINFO","No"},
	  {"SHIPPINGINFO_SERVLET","/tcmIS/ula/shipinfo?session=Active"},
	  {"SHIPPINGINFO_SERVLET_HEADER","/tcmIS/ula/shipinfo"},

	  {"CABINET_INVENTORY","Yes"},
	  {"CABINET_INVENTORY_SERVLET","/tcmIS/ula/clientcabinetinventorymain.do"},
          {"WEB_ORDER_TRACKING","Yes"},
          {"WEB_ORDER_TRACKING_SERVLET","/tcmIS/ula/ordertrackingmain.do"},

       {"WEB_REGISTRATION","Yes"}
    };

    public BoeingServerResourceBundle(){
        super();
        addHash(cHash);
    }

}
