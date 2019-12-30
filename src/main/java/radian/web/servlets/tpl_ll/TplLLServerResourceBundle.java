package radian.web.servlets.tpl_ll;

import radian.tcmis.server7.share.helpers.ServerResourceBundle;

public class TplLLServerResourceBundle  extends ServerResourceBundle {

     static final Object[][] cHash = {
          {"LOG_DIR","/logsUTC"},
          {"DEBUG","false"},
          {"DB_CLIENT","Internal"},
          {"ORACLE_DRIVER",""},
          {"ORACLE_URL",""},
          {"DB_USER",""},

          {"RELATIVE_HTML","/tcmIS/msds/tpl_ll"},
          {"DB_PASS",""},
          {"DB_CLIENT_NAME","TPL_LL"},
          {"WEBS_HOME_WWWS",""},
          {"WEBS_HOME_WWW",""},

          {"HOME_PAGE","/tcmIS/tpl_ll/home.do"},
          {"HOME_IMAGE","/images/logo/tpl_ll.png"},
          {"HELP_HTML_PATH","/tcmIS/help/new/"},

          {"VIEW_MSDS","/tcmIS/tpl_ll/ViewMsds?"},
          {"REGISTER_SERVLET","/tcmIS/tpl_ll/Register"},
          {"HOME_SERVLET","/tcmIS/tpl_ll/home.do"},
          {"JNLP_MAINCLASS","RadianCCTplLL.jar"},
          {"JNLP_DIRECTORY","/jnlp/tpl_ll"},
          {"TEST_JNLP_DIRECTORY","/prereleasejnlp/tpl_ll"},
          {"TCMIS_START_CLASS","radian.tcmis.client.client.tpl_ll.gui.TplLLCmisApp"},
          {"BATCH_REPORT_SERVLET","/tcmIS/tpl_ll/batchreport?"},

          {"ADDUSER_SERVLET","/tcmIS/tpl_ll/Register?useraction=AddUser"},
          {"CHGUSER_SERVLET","/tcmIS/tpl_ll/Register?useraction=ChgUser"},
          {"ADD_SERVLET","/tcmIS/tpl_ll/Register?useraction=Add"},
          {"LOGINREG_SERVLET","/tcmIS/tpl_ll/Register?useraction=Login"},
          {"CHANGE_SERVLET","/tcmIS/tpl_ll/Register?useraction=Change"},
          {"REGISTER_HOME_SERVLET","/tcmIS/tpl_ll/Register?"},
          {"TEST_INSTALL_SERVLET","/tcmIS/tpl_ll/Register?useraction=startingtcmis"},
          {"BAR_BUILD_SERVLET","/tcmIS/tpl_ll/Msds?limit=yes"},
          {"BUILD_SERVLET","/tcmIS/tpl_ll/Msds?"},
          {"SERVLET_DIR_WWW",""},
          {"ERW_TEMPLATE_DIR","/webdata/html/template/"},
          {"BASE_DIR_TCM_WWW","/webdata/html/"},
          {"ERW_REPORT_DIR_ACTIVE","/webdata/html/reports/temp/"},
          {"ERW_REPORT_DIR_BATCH","/webdata/html/reports/"},
          {"WEBS_HOME_WWW_ERW_ACTIVE","/reports/temp/"},
          {"WEBS_HOME_WWW_ERW_BATCH","/reports/"},
          {"CATALOGING_SERVLET","/tcmIS/tpl_ll/cataloging?"},
          {"PRINT_SCREEN","/tcmIS/tpl_ll/printscreen"},
          {"HELP_SERVLET_PATH","/tcmIS/client/help"},

          {"DROPRECEIVING","No"},
          {"RELABELING","No"},
          {"DROPRECEIVING_SERVLET","/tcmIS/tpl_ll/dropshipreceivingmain.do"},
          {"DROPRECEIVING_SERVLET_HEADER","/tcmIS/tpl_ll/dropshipreceivingmain.do"},
          {"RELABELING_SERVLET","/tcmIS/tpl_ll/Relabeling?session=Active"},

		  {"COST_REPORT","/tcmIS/tpl_ll/betacostreport?"},
		  {"SCHEDULDE_DELIVERY","/tcmIS/tpl_ll/schdelivery?"},
		  {"CANCEL_MR","/tcmIS/tpl_ll/cancel?"},
		  {"WASTE_ORDER","/tcmIS/tpl_ll/wasteorder?"},
		  {"WASTE_ADHOC_REPORT","/tcmIS/tpl_ll/wastereport?"},
		  {"USAGE_ADHOC_REPORT","/tcmIS/tpl_ll/usagereport?"},
		  {"MATERIAL_MATRIX_REPORT","/tcmIS/tpl_ll/matrixreport?"},

		  {"SHIPPINGINFO","No"},
		  {"SHIPPINGINFO_SERVLET","/tcmIS/tpl_ll/shipinfo?session=Active"},
		  {"SHIPPINGINFO_SERVLET_HEADER","/tcmIS/tpl_ll/shipinfo"},

                  {"WEB_ORDER_TRACKING","Yes"},
                  {"WEB_ORDER_TRACKING_SERVLET","/tcmIS/tpl_ll/ordertrackingmain.do"},
                  {"WEB_INVENTORY","Yes"},
                  {"WEB_INVENTORY_SERVLET","/tcmIS/tpl_ll/inventorymain.do"},
                  {"WEB_CATALOG","Yes"},
                  {"WEB_CATALOG_SERVLET","/tcmIS/tpl_ll/catalog.do"},

           {"WEB_REGISTRATION","Yes"},
			{"NEW_WEB_APPLICATION","Yes"}

	  };

     public TplLLServerResourceBundle(){
            super();
            addHash(cHash);
     }

}