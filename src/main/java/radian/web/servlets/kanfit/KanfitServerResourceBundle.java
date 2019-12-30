package radian.web.servlets.kanfit;

import radian.tcmis.server7.share.helpers.ServerResourceBundle;

public class KanfitServerResourceBundle  extends ServerResourceBundle {

     static final Object[][] cHash = {
          {"LOG_DIR","/logsUTC"},
          {"DEBUG","false"},
          {"DB_CLIENT","Kanfit"},
          {"ORACLE_DRIVER",""},
          {"ORACLE_URL",""},
          {"DB_USER",""},

          {"RELATIVE_HTML","/tcmIS/msds/kanfit"},
          {"DB_PASS",""},
          {"DB_CLIENT_NAME","Kanfit"},
          {"WEBS_HOME_WWWS",""},
          {"WEBS_HOME_WWW",""},

          {"HOME_PAGE","/tcmIS/kanfit/Home"},
          {"HOME_IMAGE","/images/kanfit.gif"},
          {"HELP_HTML_PATH","/tcmIS/help/new/"},

          {"VIEW_MSDS","/tcmIS/kanfit/ViewMsds?"},
          {"REGISTER_SERVLET","/tcmIS/kanfit/Register"},
          {"HOME_SERVLET","/tcmIS/kanfit/Home"},
          {"JNLP_MAINCLASS","RadianCCKanfit.jar"},
          {"JNLP_DIRECTORY","/jnlp/kanfit"},
          {"TEST_JNLP_DIRECTORY","/prereleasejnlp/kanfit"},
          {"TCMIS_START_CLASS","radian.tcmis.client.client.kanfit.gui.KanfitCmisApp"},
          {"BATCH_REPORT_SERVLET","/tcmIS/kanfit/batchreport?"},

          {"ADDUSER_SERVLET","/tcmIS/kanfit/Register?useraction=AddUser"},
          {"CHGUSER_SERVLET","/tcmIS/kanfit/Register?useraction=ChgUser"},
          {"ADD_SERVLET","/tcmIS/kanfit/Register?useraction=Add"},
          {"LOGINREG_SERVLET","/tcmIS/kanfit/Register?useraction=Login"},
          {"CHANGE_SERVLET","/tcmIS/kanfit/Register?useraction=Change"},
          {"REGISTER_HOME_SERVLET","/tcmIS/kanfit/Register?"},
          {"TEST_INSTALL_SERVLET","/tcmIS/kanfit/Register?useraction=startingtcmis"},
          {"BAR_BUILD_SERVLET","/tcmIS/kanfit/Msds?limit=yes"},
          {"BUILD_SERVLET","/tcmIS/kanfit/Msds?"},
          {"SERVLET_DIR_WWW",""},
          {"ERW_TEMPLATE_DIR","/webdata/html/template/"},
          {"BASE_DIR_TCM_WWW","/webdata/html/"},
          {"ERW_REPORT_DIR_ACTIVE","/webdata/html/reports/temp/"},
          {"ERW_REPORT_DIR_BATCH","/webdata/html/reports/"},
          {"WEBS_HOME_WWW_ERW_ACTIVE","/reports/temp/"},
          {"WEBS_HOME_WWW_ERW_BATCH","/reports/"},
          {"CATALOGING_SERVLET","/tcmIS/kanfit/cataloging?"},
          {"PRINT_SCREEN","/tcmIS/kanfit/printscreen"},
          {"HELP_SERVLET_PATH","/tcmIS/client/help"},

          {"DROPRECEIVING","No"},
          {"RELABELING","No"},
          {"DROPRECEIVING_SERVLET","/tcmIS/kanfit/dropshipreceivingmain.do"},
          {"DROPRECEIVING_SERVLET_HEADER","/tcmIS/kanfit/dropshipreceivingmain.do"},
          {"RELABELING_SERVLET","/tcmIS/kanfit/Relabeling?session=Active"},

		  {"COST_REPORT","/tcmIS/kanfit/betacostreport?"},
		  {"SCHEDULDE_DELIVERY","/tcmIS/kanfit/schdelivery?"},
		  {"CANCEL_MR","/tcmIS/kanfit/cancel?"},
		  {"WASTE_ORDER","/tcmIS/kanfit/wasteorder?"},
		  {"WASTE_ADHOC_REPORT","/tcmIS/kanfit/wastereport?"},
		  {"USAGE_ADHOC_REPORT","/tcmIS/kanfit/usagereport?"},
		  {"MATERIAL_MATRIX_REPORT","/tcmIS/kanfit/matrixreport?"},

		  {"SHIPPINGINFO","No"},
		  {"SHIPPINGINFO_SERVLET","/tcmIS/kanfit/shipinfo?session=Active"},
		  {"SHIPPINGINFO_SERVLET_HEADER","/tcmIS/kanfit/shipinfo"},

                  {"WEB_ORDER_TRACKING","Yes"},
                  {"WEB_ORDER_TRACKING_SERVLET","/tcmIS/kanfit/ordertrackingmain.do"},
                  {"WEB_INVENTORY","Yes"},
                  {"WEB_INVENTORY_SERVLET","/tcmIS/kanfit/inventorymain.do"},
                  {"WEB_CATALOG","Yes"},
                  {"WEB_CATALOG_SERVLET","/tcmIS/kanfit/catalog.do"},

           {"WEB_REGISTRATION","Yes"}

     };

     public KanfitServerResourceBundle(){
            super();
            addHash(cHash);
     }

}