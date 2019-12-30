package radian.web.servlets.sastech;

import radian.tcmis.server7.share.helpers.ServerResourceBundle;

public class SasTechServerResourceBundle  extends ServerResourceBundle {

     static final Object[][] cHash = {
          {"LOG_DIR","/logssastech"},
          {"DEBUG","false"},
          {"DB_CLIENT","SAS_TECH"},
          {"ORACLE_DRIVER",""},
          {"ORACLE_URL",""},
          {"DB_USER",""},

          {"RELATIVE_HTML","/tcmIS/msds/sastech"},
          {"DB_PASS",""},
          {"DB_CLIENT_NAME","SAS_TECH"},
          {"WEBS_HOME_WWWS",""},
          {"WEBS_HOME_WWW",""},

          {"HOME_PAGE","/tcmIS/sastech/home.do"},
          {"HOME_IMAGE","/images/Wescologo_global.jpg"},
          {"HELP_HTML_PATH","/tcmIS/help/new/"},

          {"VIEW_MSDS","/tcmIS/sastech/ViewMsds?"},
          {"REGISTER_SERVLET","/tcmIS/sastech/Register"},
          {"HOME_SERVLET","/tcmIS/sastech/home.do"},
          {"JNLP_MAINCLASS","RadianCCSASTECH.jar"},
          {"JNLP_DIRECTORY","/jnlp/sastech"},
          {"TEST_JNLP_DIRECTORY","/prereleasejnlp/sastech"},
          {"TCMIS_START_CLASS","radian.tcmis.client.client.ba.gui.SASTECHCmisApp"},
          {"BATCH_REPORT_SERVLET","/tcmIS/sastech/batchreport?"},

          {"ADDUSER_SERVLET","/tcmIS/sastech/Register?useraction=AddUser"},
          {"CHGUSER_SERVLET","/tcmIS/sastech/Register?useraction=ChgUser"},
          {"ADD_SERVLET","/tcmIS/sastech/Register?useraction=Add"},
          {"LOGINREG_SERVLET","/tcmIS/sastech/Register?useraction=Login"},
          {"CHANGE_SERVLET","/tcmIS/sastech/Register?useraction=Change"},
          {"REGISTER_HOME_SERVLET","/tcmIS/sastech/Register?"},
          {"TEST_INSTALL_SERVLET","/tcmIS/sastech/Register?useraction=startingtcmis"},
          {"BAR_BUILD_SERVLET","/tcmIS/sastech/Msds?limit=yes"},
          {"BUILD_SERVLET","/tcmIS/sastech/Msds?"},
          {"SERVLET_DIR_WWW",""},
          {"ERW_TEMPLATE_DIR","/webdata/html/template/"},
          {"BASE_DIR_TCM_WWW","/webdata/html/"},
          {"ERW_REPORT_DIR_ACTIVE","/webdata/html/reports/temp/"},
          {"ERW_REPORT_DIR_BATCH","/webdata/html/reports/"},
          {"WEBS_HOME_WWW_ERW_ACTIVE","/reports/temp/"},
          {"WEBS_HOME_WWW_ERW_BATCH","/reports/"},
          {"CATALOGING_SERVLET","/tcmIS/sastech/cataloging?"},
          {"PRINT_SCREEN","/tcmIS/sastech/printscreen"},
          {"HELP_SERVLET_PATH","/tcmIS/client/help"},

          {"DROPRECEIVING","No"},
          {"RELABELING","No"},
          {"DROPRECEIVING_SERVLET","/tcmIS/sastech/dropshipreceivingmain.do"},
          {"DROPRECEIVING_SERVLET_HEADER","/tcmIS/sastech/dropshipreceivingmain.do"},
          {"RELABELING_SERVLET","/tcmIS/sastech/Relabeling?session=Active"},

		  {"COST_REPORT","/tcmIS/sastech/betacostreport?"},
		  {"SCHEDULDE_DELIVERY","/tcmIS/sastech/schdelivery?"},
		  {"CANCEL_MR","/tcmIS/sastech/cancel?"},
		  {"WASTE_ORDER","/tcmIS/sastech/wasteorder?"},
		  {"WASTE_ADHOC_REPORT","/tcmIS/sastech/wastereport?"},
		  {"USAGE_ADHOC_REPORT","/tcmIS/sastech/usagereport?"},
		  {"MATERIAL_MATRIX_REPORT","/tcmIS/sastech/matrixreport?"},

		  {"SHIPPINGINFO","No"},
		  {"SHIPPINGINFO_SERVLET","/tcmIS/sastech/shipinfo?session=Active"},
		  {"SHIPPINGINFO_SERVLET_HEADER","/tcmIS/sastech/shipinfo"},

                  {"WEB_ORDER_TRACKING","Yes"},
                  {"WEB_ORDER_TRACKING_SERVLET","/tcmIS/sastech/ordertrackingmain.do"},
                  {"WEB_INVENTORY","Yes"},
                  {"WEB_INVENTORY_SERVLET","/tcmIS/sastech/inventorymain.do"},
                  {"WEB_CATALOG","Yes"},
                  {"WEB_CATALOG_SERVLET","/tcmIS/sastech/catalog.do"},

           {"WEB_REGISTRATION","Yes"},
			{"NEW_WEB_APPLICATION","Yes"}

	  };

     public SasTechServerResourceBundle(){
            super();
            addHash(cHash);
     }

}