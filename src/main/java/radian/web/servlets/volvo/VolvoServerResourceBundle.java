package radian.web.servlets.volvo;

import radian.tcmis.server7.share.helpers.ServerResourceBundle;

public class VolvoServerResourceBundle  extends ServerResourceBundle {

     static final Object[][] cHash = {
          {"LOG_DIR","/logsUTC"},
          {"DEBUG","false"},
          {"DB_CLIENT","Volvo"},
          {"ORACLE_DRIVER",""},
          {"ORACLE_URL",""},
          {"DB_USER",""},

          {"RELATIVE_HTML","/tcmIS/msds/volvo"},
          {"DB_PASS",""},
          {"DB_CLIENT_NAME","Volvo"},
          {"WEBS_HOME_WWWS",""},
          {"WEBS_HOME_WWW",""},

          {"HOME_PAGE","/tcmIS/volvo/Home"},
          {"HOME_IMAGE","/images/volvo.gif"},
          {"HELP_HTML_PATH","/tcmIS/help/new/"},

          {"VIEW_MSDS","/tcmIS/volvo/ViewMsds?"},
          {"REGISTER_SERVLET","/tcmIS/volvo/Register"},
          {"HOME_SERVLET","/tcmIS/volvo/Home"},
          {"JNLP_MAINCLASS","RadianCCVolvo.jar"},
          {"JNLP_DIRECTORY","/jnlp/volvo"},
          {"TEST_JNLP_DIRECTORY","/prereleasejnlp/volvo"},
          {"TCMIS_START_CLASS","radian.tcmis.client.client.volvo.gui.VolvoCmisApp"},
          {"BATCH_REPORT_SERVLET","/tcmIS/volvo/batchreport?"},

          {"ADDUSER_SERVLET","/tcmIS/volvo/Register?useraction=AddUser"},
          {"CHGUSER_SERVLET","/tcmIS/volvo/Register?useraction=ChgUser"},
          {"ADD_SERVLET","/tcmIS/volvo/Register?useraction=Add"},
          {"LOGINREG_SERVLET","/tcmIS/volvo/Register?useraction=Login"},
          {"CHANGE_SERVLET","/tcmIS/volvo/Register?useraction=Change"},
          {"REGISTER_HOME_SERVLET","/tcmIS/volvo/Register?"},
          {"TEST_INSTALL_SERVLET","/tcmIS/volvo/Register?useraction=startingtcmis"},
          {"BAR_BUILD_SERVLET","/tcmIS/volvo/Msds?limit=yes"},
          {"BUILD_SERVLET","/tcmIS/volvo/Msds?"},
          {"SERVLET_DIR_WWW",""},
          {"ERW_TEMPLATE_DIR","/webdata/html/template/"},
          {"BASE_DIR_TCM_WWW","/webdata/html/"},
          {"ERW_REPORT_DIR_ACTIVE","/webdata/html/reports/temp/"},
          {"ERW_REPORT_DIR_BATCH","/webdata/html/reports/"},
          {"WEBS_HOME_WWW_ERW_ACTIVE","/reports/temp/"},
          {"WEBS_HOME_WWW_ERW_BATCH","/reports/"},
          {"CATALOGING_SERVLET","/tcmIS/volvo/cataloging?"},
          {"PRINT_SCREEN","/tcmIS/volvo/printscreen"},
          {"HELP_SERVLET_PATH","/tcmIS/client/help"},

          {"DROPRECEIVING","No"},
          {"RELABELING","No"},
          {"DROPRECEIVING_SERVLET","/tcmIS/volvo/dropshipreceivingmain.do"},
          {"DROPRECEIVING_SERVLET_HEADER","/tcmIS/volvo/dropshipreceivingmain.do"},
          {"RELABELING_SERVLET","/tcmIS/volvo/Relabeling?session=Active"},

		  {"COST_REPORT","/tcmIS/volvo/betacostreport?"},
		  {"SCHEDULDE_DELIVERY","/tcmIS/volvo/schdelivery?"},
		  {"CANCEL_MR","/tcmIS/volvo/cancel?"},
		  {"WASTE_ORDER","/tcmIS/volvo/wasteorder?"},
		  {"WASTE_ADHOC_REPORT","/tcmIS/volvo/wastereport?"},
		  {"USAGE_ADHOC_REPORT","/tcmIS/volvo/usagereport?"},
		  {"MATERIAL_MATRIX_REPORT","/tcmIS/volvo/matrixreport?"},

		  {"SHIPPINGINFO","No"},
		  {"SHIPPINGINFO_SERVLET","/tcmIS/volvo/shipinfo?session=Active"},
		  {"SHIPPINGINFO_SERVLET_HEADER","/tcmIS/volvo/shipinfo"},

                  {"WEB_ORDER_TRACKING","Yes"},
                  {"WEB_ORDER_TRACKING_SERVLET","/tcmIS/volvo/ordertrackingmain.do"},
                  {"WEB_INVENTORY","Yes"},
                  {"WEB_INVENTORY_SERVLET","/tcmIS/volvo/inventorymain.do"},
                  {"WEB_CATALOG","Yes"},
                  {"WEB_CATALOG_SERVLET","/tcmIS/volvo/catalog.do"},

           {"WEB_REGISTRATION","Yes"}

     };

     public VolvoServerResourceBundle(){
            super();
            addHash(cHash);
     }

}