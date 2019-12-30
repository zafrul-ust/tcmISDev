package radian.web.servlets.dana;

import radian.tcmis.server7.share.helpers.ServerResourceBundle;

public class DanaServerResourceBundle  extends ServerResourceBundle {

    static final Object[][] cHash = {
      {"LOG_DIR","/logssd"},
      {"DEBUG","true"},
      {"DB_CLIENT","Dana"},
      {"ORACLE_DRIVER",""},
      {"ORACLE_URL",""},
      {"DB_USER",""},

      {"RELATIVE_HTML","/tcmIS/msds/dana"},
      {"DB_PASS",""},
      {"DB_CLIENT_NAME","Dana"},
      {"WEBS_HOME_WWWS",""},
      {"WEBS_HOME_WWW",""},

      {"HOME_PAGE","/tcmIS/dana/Home"},
      {"HOME_IMAGE","/images/buttons/dana/dana.gif"},
      {"HELP_HTML_PATH","/tcmIS/help/new/"},

      {"VIEW_MSDS","/tcmIS/dana/ViewMsds?"},
      {"REGISTER_SERVLET","/tcmIS/dana/Register"},
      {"HOME_SERVLET","/tcmIS/dana/Home"},
      {"JNLP_MAINCLASS","RadianCCDana.jar"},
      {"JNLP_DIRECTORY","/jnlp/dana"},
      {"TEST_JNLP_DIRECTORY","/prereleasejnlp/dana"},
      {"TCMIS_START_CLASS","radian.tcmis.client.client.dana.gui.DanaCmisApp"},
      {"BATCH_REPORT_SERVLET","/tcmIS/dana/batchreport?"},

      {"ADDUSER_SERVLET","/tcmIS/dana/Register?useraction=AddUser"},
      {"CHGUSER_SERVLET","/tcmIS/dana/Register?useraction=ChgUser"},
      {"ADD_SERVLET","/tcmIS/dana/Register?useraction=Add"},
      {"LOGINREG_SERVLET","/tcmIS/dana/Register?useraction=Login"},
      {"CHANGE_SERVLET","/tcmIS/dana/Register?useraction=Change"},
      {"REGISTER_HOME_SERVLET","/tcmIS/dana/Register?"},
      {"TEST_INSTALL_SERVLET","/tcmIS/dana/Register?useraction=startingtcmis"},
      {"BAR_BUILD_SERVLET","/tcmIS/dana/Msds?limit=yes"},
      {"BUILD_SERVLET","/tcmIS/dana/Msds?"},
      {"SERVLET_DIR_WWW",""},
      {"ERW_TEMPLATE_DIR","/webdata/html/template/"},
      {"BASE_DIR_TCM_WWW","/webdata/html/"},
      {"ERW_REPORT_DIR_ACTIVE","/webdata/html/reports/temp/"},
      {"ERW_REPORT_DIR_BATCH","/webdata/html/reports/"},
      {"WEBS_HOME_WWW_ERW_ACTIVE","/reports/temp/"},
      {"WEBS_HOME_WWW_ERW_BATCH","/reports/"},
      {"CATALOGING_SERVLET","/tcmIS/dana/cataloging?"},
      {"CATALOG_SOURCING_SERVLET","/tcmIS/dana/sourcing?"},
      {"PRINT_SCREEN","/tcmIS/dana/printscreen"},
      {"HELP_SERVLET_PATH","/tcmIS/client/help"},

      {"DROPRECEIVING_SERVLET","/tcmIS/dana/dropshipreceivingmain.do"},
      {"DROPRECEIVING_SERVLET_HEADER","/tcmIS/dana/dropshipreceivingmain.do"},
      {"RELABELING_SERVLET","/tcmIS/dana/Relabeling?session=Active"},

      {"DROPRECEIVING","Yes"},
      {"RELABELING","No"},
      {"DROPRECEIVING_SERVLET","/tcmIS/dana/dropshipreceivingmain.do"},
      {"DROPRECEIVING_SERVLET_HEADER","/tcmIS/dana/dropshipreceivingmain.do"},
      {"RELABELING_SERVLET","/tcmIS/dana/Relabeling?session=Active"},

	  {"COST_REPORT","/tcmIS/dana/betacostreport?"},
	  {"SCHEDULDE_DELIVERY","/tcmIS/dana/schdelivery?"},
	  {"CANCEL_MR","/tcmIS/dana/cancel?"},
	  {"WASTE_ORDER","/tcmIS/dana/wasteorder?"},
	  {"WASTE_ADHOC_REPORT","/tcmIS/dana/wastereport?"},
	  {"USAGE_ADHOC_REPORT","/tcmIS/dana/usagereport?"},
	  {"MATERIAL_MATRIX_REPORT","/tcmIS/dana/matrixreport?"},

	  {"SHIPPINGINFO","No"},
	  {"SHIPPINGINFO_SERVLET","/tcmIS/dana/shipinfo?session=Active"},
	  {"SHIPPINGINFO_SERVLET_HEADER","/tcmIS/dana/shipinfo"},

	  {"PROJECTS","Yes"},
	  {"PROJECTS_SERVLET","/tcmIS/dana/showpeiprojectlist.do?"},
	  //{"SEARCH_PERSONNEL_SERVLET","/tcmIS/dana/searchpersonnel?"},

	  {"MONTHLY_INVENTORY_DETAIL","Yes"},
	  {"MONTHLY_INVENTORY_DETAIL_SERVLET","/tcmIS/dana/monthlyinventorydetail.do"},

	  {"OPEN_POS","No"},
	  {"OPEN_POS_SERVLET","/tcmIS/dana/receiving.do"},
          {"WEB_ORDER_TRACKING","Yes"},
          {"WEB_ORDER_TRACKING_SERVLET","/tcmIS/dana/ordertrackingmain.do"},
      {"WEB_REGISTRATION","Yes"}
    };

    public DanaServerResourceBundle(){
        super();
        addHash(cHash);
    }

}
