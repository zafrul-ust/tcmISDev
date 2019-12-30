package radian.web.servlets.tpl_dhl;

import radian.tcmis.server7.share.helpers.ServerResourceBundle;

public class TplDhlServerResourceBundle  extends ServerResourceBundle {

     static final Object[][] cHash = {
          {"LOG_DIR","/logsUTC"},
          {"DEBUG","false"},
          {"DB_CLIENT","Internal"},
          {"ORACLE_DRIVER",""},
          {"ORACLE_URL",""},
          {"DB_USER",""},

          {"RELATIVE_HTML","/tcmIS/msds/tpl_dhl"},
          {"DB_PASS",""},
          {"DB_CLIENT_NAME","TPL_DHL"},
          {"WEBS_HOME_WWWS",""},
          {"WEBS_HOME_WWW",""},

          {"HOME_PAGE","/tcmIS/tpl_dhl/home.do"},
          {"HOME_IMAGE","/images/logo/tpl_dhl.png"},
          {"HELP_HTML_PATH","/tcmIS/help/new/"},

          {"VIEW_MSDS","/tcmIS/tpl_dhl/ViewMsds?"},
          {"REGISTER_SERVLET","/tcmIS/tpl_dhl/Register"},
          {"HOME_SERVLET","/tcmIS/tpl_dhl/home.do"},
          {"JNLP_MAINCLASS","RadianCCTplDhl.jar"},
          {"JNLP_DIRECTORY","/jnlp/tpl_dhl"},
          {"TEST_JNLP_DIRECTORY","/prereleasejnlp/tpl_dhl"},
          {"TCMIS_START_CLASS","radian.tcmis.client.client.tpl_dhl.gui.TplDhlCmisApp"},
          {"BATCH_REPORT_SERVLET","/tcmIS/tpl_dhl/batchreport?"},

          {"ADDUSER_SERVLET","/tcmIS/tpl_dhl/Register?useraction=AddUser"},
          {"CHGUSER_SERVLET","/tcmIS/tpl_dhl/Register?useraction=ChgUser"},
          {"ADD_SERVLET","/tcmIS/tpl_dhl/Register?useraction=Add"},
          {"LOGINREG_SERVLET","/tcmIS/tpl_dhl/Register?useraction=Login"},
          {"CHANGE_SERVLET","/tcmIS/tpl_dhl/Register?useraction=Change"},
          {"REGISTER_HOME_SERVLET","/tcmIS/tpl_dhl/Register?"},
          {"TEST_INSTALL_SERVLET","/tcmIS/tpl_dhl/Register?useraction=startingtcmis"},
          {"BAR_BUILD_SERVLET","/tcmIS/tpl_dhl/Msds?limit=yes"},
          {"BUILD_SERVLET","/tcmIS/tpl_dhl/Msds?"},
          {"SERVLET_DIR_WWW",""},
          {"ERW_TEMPLATE_DIR","/webdata/html/template/"},
          {"BASE_DIR_TCM_WWW","/webdata/html/"},
          {"ERW_REPORT_DIR_ACTIVE","/webdata/html/reports/temp/"},
          {"ERW_REPORT_DIR_BATCH","/webdata/html/reports/"},
          {"WEBS_HOME_WWW_ERW_ACTIVE","/reports/temp/"},
          {"WEBS_HOME_WWW_ERW_BATCH","/reports/"},
          {"CATALOGING_SERVLET","/tcmIS/tpl_dhl/cataloging?"},
          {"PRINT_SCREEN","/tcmIS/tpl_dhl/printscreen"},
          {"HELP_SERVLET_PATH","/tcmIS/client/help"},

          {"DROPRECEIVING","No"},
          {"RELABELING","No"},
          {"DROPRECEIVING_SERVLET","/tcmIS/tpl_dhl/dropshipreceivingmain.do"},
          {"DROPRECEIVING_SERVLET_HEADER","/tcmIS/tpl_dhl/dropshipreceivingmain.do"},
          {"RELABELING_SERVLET","/tcmIS/tpl_dhl/Relabeling?session=Active"},

		  {"COST_REPORT","/tcmIS/tpl_dhl/betacostreport?"},
		  {"SCHEDULDE_DELIVERY","/tcmIS/tpl_dhl/schdelivery?"},
		  {"CANCEL_MR","/tcmIS/tpl_dhl/cancel?"},
		  {"WASTE_ORDER","/tcmIS/tpl_dhl/wasteorder?"},
		  {"WASTE_ADHOC_REPORT","/tcmIS/tpl_dhl/wastereport?"},
		  {"USAGE_ADHOC_REPORT","/tcmIS/tpl_dhl/usagereport?"},
		  {"MATERIAL_MATRIX_REPORT","/tcmIS/tpl_dhl/matrixreport?"},

		  {"SHIPPINGINFO","No"},
		  {"SHIPPINGINFO_SERVLET","/tcmIS/tpl_dhl/shipinfo?session=Active"},
		  {"SHIPPINGINFO_SERVLET_HEADER","/tcmIS/tpl_dhl/shipinfo"},

                  {"WEB_ORDER_TRACKING","Yes"},
                  {"WEB_ORDER_TRACKING_SERVLET","/tcmIS/tpl_dhl/ordertrackingmain.do"},
                  {"WEB_INVENTORY","Yes"},
                  {"WEB_INVENTORY_SERVLET","/tcmIS/tpl_dhl/inventorymain.do"},
                  {"WEB_CATALOG","Yes"},
                  {"WEB_CATALOG_SERVLET","/tcmIS/tpl_dhl/catalog.do"},

           {"WEB_REGISTRATION","Yes"},
			{"NEW_WEB_APPLICATION","Yes"}

	  };

     public TplDhlServerResourceBundle(){
            super();
            addHash(cHash);
     }

}