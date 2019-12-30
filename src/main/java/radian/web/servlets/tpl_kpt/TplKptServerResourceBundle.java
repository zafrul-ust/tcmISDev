package radian.web.servlets.tpl_kpt;

import radian.tcmis.server7.share.helpers.ServerResourceBundle;

public class TplKptServerResourceBundle  extends ServerResourceBundle {

     static final Object[][] cHash = {
          {"LOG_DIR","/logsUTC"},
          {"DEBUG","false"},
          {"DB_CLIENT","Internal"},
          {"ORACLE_DRIVER",""},
          {"ORACLE_URL",""},
          {"DB_USER",""},

          {"RELATIVE_HTML","/tcmIS/msds/tpl_kpt"},
          {"DB_PASS",""},
          {"DB_CLIENT_NAME","tpl_kpt"},
          {"WEBS_HOME_WWWS",""},
          {"WEBS_HOME_WWW",""},

          {"HOME_PAGE","/tcmIS/tpl_kpt/home.do"},
          {"HOME_IMAGE","/images/logo/tpl_kpt.png"},
          {"HELP_HTML_PATH","/tcmIS/help/new/"},

          {"VIEW_MSDS","/tcmIS/tpl_kpt/ViewMsds?"},
          {"REGISTER_SERVLET","/tcmIS/tpl_kpt/Register"},
          {"HOME_SERVLET","/tcmIS/tpl_kpt/home.do"},
          {"JNLP_MAINCLASS","RadianCCTplKpt.jar"},
          {"JNLP_DIRECTORY","/jnlp/tpl_kpt"},
          {"TEST_JNLP_DIRECTORY","/prereleasejnlp/tpl_kpt"},
          {"TCMIS_START_CLASS","radian.tcmis.client.client.tpl_kpt.gui.TplKptCmisApp"},
          {"BATCH_REPORT_SERVLET","/tcmIS/tpl_kpt/batchreport?"},

          {"ADDUSER_SERVLET","/tcmIS/tpl_kpt/Register?useraction=AddUser"},
          {"CHGUSER_SERVLET","/tcmIS/tpl_kpt/Register?useraction=ChgUser"},
          {"ADD_SERVLET","/tcmIS/tpl_kpt/Register?useraction=Add"},
          {"LOGINREG_SERVLET","/tcmIS/tpl_kpt/Register?useraction=Login"},
          {"CHANGE_SERVLET","/tcmIS/tpl_kpt/Register?useraction=Change"},
          {"REGISTER_HOME_SERVLET","/tcmIS/tpl_kpt/Register?"},
          {"TEST_INSTALL_SERVLET","/tcmIS/tpl_kpt/Register?useraction=startingtcmis"},
          {"BAR_BUILD_SERVLET","/tcmIS/tpl_kpt/Msds?limit=yes"},
          {"BUILD_SERVLET","/tcmIS/tpl_kpt/Msds?"},
          {"SERVLET_DIR_WWW",""},
          {"ERW_TEMPLATE_DIR","/webdata/html/template/"},
          {"BASE_DIR_TCM_WWW","/webdata/html/"},
          {"ERW_REPORT_DIR_ACTIVE","/webdata/html/reports/temp/"},
          {"ERW_REPORT_DIR_BATCH","/webdata/html/reports/"},
          {"WEBS_HOME_WWW_ERW_ACTIVE","/reports/temp/"},
          {"WEBS_HOME_WWW_ERW_BATCH","/reports/"},
          {"CATALOGING_SERVLET","/tcmIS/tpl_kpt/cataloging?"},
          {"PRINT_SCREEN","/tcmIS/tpl_kpt/printscreen"},
          {"HELP_SERVLET_PATH","/tcmIS/client/help"},

          {"DROPRECEIVING","No"},
          {"RELABELING","No"},
          {"DROPRECEIVING_SERVLET","/tcmIS/tpl_kpt/dropshipreceivingmain.do"},
          {"DROPRECEIVING_SERVLET_HEADER","/tcmIS/tpl_kpt/dropshipreceivingmain.do"},
          {"RELABELING_SERVLET","/tcmIS/tpl_kpt/Relabeling?session=Active"},

		  {"COST_REPORT","/tcmIS/tpl_kpt/betacostreport?"},
		  {"SCHEDULDE_DELIVERY","/tcmIS/tpl_kpt/schdelivery?"},
		  {"CANCEL_MR","/tcmIS/tpl_kpt/cancel?"},
		  {"WASTE_ORDER","/tcmIS/tpl_kpt/wasteorder?"},
		  {"WASTE_ADHOC_REPORT","/tcmIS/tpl_kpt/wastereport?"},
		  {"USAGE_ADHOC_REPORT","/tcmIS/tpl_kpt/usagereport?"},
		  {"MATERIAL_MATRIX_REPORT","/tcmIS/tpl_kpt/matrixreport?"},

		  {"SHIPPINGINFO","No"},
		  {"SHIPPINGINFO_SERVLET","/tcmIS/tpl_kpt/shipinfo?session=Active"},
		  {"SHIPPINGINFO_SERVLET_HEADER","/tcmIS/tpl_kpt/shipinfo"},

                  {"WEB_ORDER_TRACKING","Yes"},
                  {"WEB_ORDER_TRACKING_SERVLET","/tcmIS/tpl_kpt/ordertrackingmain.do"},
                  {"WEB_INVENTORY","Yes"},
                  {"WEB_INVENTORY_SERVLET","/tcmIS/tpl_kpt/inventorymain.do"},
                  {"WEB_CATALOG","Yes"},
                  {"WEB_CATALOG_SERVLET","/tcmIS/tpl_kpt/catalog.do"},

           {"WEB_REGISTRATION","Yes"},
			{"NEW_WEB_APPLICATION","Yes"}

	  };

     public TplKptServerResourceBundle(){
            super();
            addHash(cHash);
     }

}