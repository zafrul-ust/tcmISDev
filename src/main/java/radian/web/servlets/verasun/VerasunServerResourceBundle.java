package radian.web.servlets.verasun;

import radian.tcmis.server7.share.helpers.ServerResourceBundle;

public class VerasunServerResourceBundle  extends ServerResourceBundle {

     static final Object[][] cHash = {
          {"LOG_DIR","/logsUTC"},
          {"DEBUG","false"},
          {"DB_CLIENT","Verasun"},
          {"ORACLE_DRIVER",""},
          {"ORACLE_URL",""},
          {"DB_USER",""},

          {"RELATIVE_HTML","/tcmIS/msds/verasun"},
          {"DB_PASS",""},
          {"DB_CLIENT_NAME","Verasun"},
          {"WEBS_HOME_WWWS",""},
          {"WEBS_HOME_WWW",""},

          {"HOME_PAGE","/tcmIS/verasun/Home"},
          {"HOME_IMAGE","/images/verasun.gif"},
          {"HELP_HTML_PATH","/tcmIS/help/new/"},

          {"VIEW_MSDS","/tcmIS/verasun/ViewMsds?"},
          {"REGISTER_SERVLET","/tcmIS/verasun/Register"},
          {"HOME_SERVLET","/tcmIS/verasun/Home"},
          {"JNLP_MAINCLASS","RadianCCVerasun.jar"},
          {"JNLP_DIRECTORY","/jnlp/verasun"},
          {"TEST_JNLP_DIRECTORY","/prereleasejnlp/verasun"},
          {"TCMIS_START_CLASS","radian.tcmis.client.client.verasun.gui.VerasunCmisApp"},
          {"BATCH_REPORT_SERVLET","/tcmIS/verasun/batchreport?"},

          {"ADDUSER_SERVLET","/tcmIS/verasun/Register?useraction=AddUser"},
          {"CHGUSER_SERVLET","/tcmIS/verasun/Register?useraction=ChgUser"},
          {"ADD_SERVLET","/tcmIS/verasun/Register?useraction=Add"},
          {"LOGINREG_SERVLET","/tcmIS/verasun/Register?useraction=Login"},
          {"CHANGE_SERVLET","/tcmIS/verasun/Register?useraction=Change"},
          {"REGISTER_HOME_SERVLET","/tcmIS/verasun/Register?"},
          {"TEST_INSTALL_SERVLET","/tcmIS/verasun/Register?useraction=startingtcmis"},
          {"BAR_BUILD_SERVLET","/tcmIS/verasun/Msds?limit=yes"},
          {"BUILD_SERVLET","/tcmIS/verasun/Msds?"},
          {"SERVLET_DIR_WWW",""},
          {"ERW_TEMPLATE_DIR","/webdata/html/template/"},
          {"BASE_DIR_TCM_WWW","/webdata/html/"},
          {"ERW_REPORT_DIR_ACTIVE","/webdata/html/reports/temp/"},
          {"ERW_REPORT_DIR_BATCH","/webdata/html/reports/"},
          {"WEBS_HOME_WWW_ERW_ACTIVE","/reports/temp/"},
          {"WEBS_HOME_WWW_ERW_BATCH","/reports/"},
          {"CATALOGING_SERVLET","/tcmIS/verasun/cataloging?"},
          {"PRINT_SCREEN","/tcmIS/verasun/printscreen"},
          {"HELP_SERVLET_PATH","/tcmIS/client/help"},

          {"DROPRECEIVING","No"},
          {"RELABELING","No"},
          {"DROPRECEIVING_SERVLET","/tcmIS/verasun/dropshipreceivingmain.do"},
          {"DROPRECEIVING_SERVLET_HEADER","/tcmIS/verasun/dropshipreceivingmain.do"},
          {"RELABELING_SERVLET","/tcmIS/verasun/Relabeling?session=Active"},

		  {"COST_REPORT","/tcmIS/verasun/betacostreport?"},
		  {"SCHEDULDE_DELIVERY","/tcmIS/verasun/schdelivery?"},
		  {"CANCEL_MR","/tcmIS/verasun/cancel?"},
		  {"WASTE_ORDER","/tcmIS/verasun/wasteorder?"},
		  {"WASTE_ADHOC_REPORT","/tcmIS/verasun/wastereport?"},
		  {"USAGE_ADHOC_REPORT","/tcmIS/verasun/usagereport?"},
		  {"MATERIAL_MATRIX_REPORT","/tcmIS/verasun/matrixreport?"},

		  {"SHIPPINGINFO","No"},
		  {"SHIPPINGINFO_SERVLET","/tcmIS/verasun/shipinfo?session=Active"},
		  {"SHIPPINGINFO_SERVLET_HEADER","/tcmIS/verasun/shipinfo"},

			{"WEB_INVENTORY","Yes"},
		  {"WEB_INVENTORY_SERVLET","/tcmIS/verasun/inventorymain.do"},
                  {"WEB_ORDER_TRACKING","Yes"},
                  {"WEB_ORDER_TRACKING_SERVLET","/tcmIS/verasun/ordertrackingmain.do"},
      {"WEB_REGISTRATION","Yes"}

     };

     public VerasunServerResourceBundle(){
            super();
            addHash(cHash);
     }

}