package radian.web.servlets.swa;

import radian.tcmis.server7.share.helpers.ServerResourceBundle;

public class SWAServerResourceBundle  extends ServerResourceBundle {

     static final Object[][] cHash = {
          {"LOG_DIR","/logsSWA"},
          {"DEBUG","false"},
          {"DB_CLIENT","Southwest Airlines"},
          {"ORACLE_DRIVER",""},
          {"ORACLE_URL",""},
          {"DB_USER",""},

          {"RELATIVE_HTML","/tcmIS/msds/swa"},
          {"DB_PASS",""},
          {"DB_CLIENT_NAME","SWA"},
          {"WEBS_HOME_WWWS",""},
          {"WEBS_HOME_WWW",""},

          {"HOME_PAGE","/tcmIS/swa/Home"},
          {"HOME_IMAGE","/images/buttons/swa/swa.gif"},
          {"HELP_HTML_PATH","/tcmIS/help/new/"},

          {"VIEW_MSDS","/tcmIS/swa/ViewMsds?"},
          {"REGISTER_SERVLET","/tcmIS/swa/Register"},
          {"HOME_SERVLET","/tcmIS/swa/Home"},
          {"JNLP_MAINCLASS","RadianCCSWA.jar"},
          {"JNLP_DIRECTORY","/jnlp/swa"},
          {"TEST_JNLP_DIRECTORY","/prereleasejnlp/swa"},
          {"TCMIS_START_CLASS","radian.tcmis.client.client.swa.gui.SWACmisApp"},
          {"BATCH_REPORT_SERVLET","/tcmIS/swa/batchreport?"},

          {"ADDUSER_SERVLET","/tcmIS/swa/Register?useraction=AddUser"},
          {"CHGUSER_SERVLET","/tcmIS/swa/Register?useraction=ChgUser"},
          {"ADD_SERVLET","/tcmIS/swa/Register?useraction=Add"},
          {"LOGINREG_SERVLET","/tcmIS/swa/Register?useraction=Login"},
          {"CHANGE_SERVLET","/tcmIS/swa/Register?useraction=Change"},
          {"REGISTER_HOME_SERVLET","/tcmIS/swa/Register?"},
          {"TEST_INSTALL_SERVLET","/tcmIS/swa/Register?useraction=startingtcmis"},
          {"BAR_BUILD_SERVLET","/tcmIS/swa/Msds?limit=yes"},
          {"BUILD_SERVLET","/tcmIS/swa/Msds?"},
          {"SERVLET_DIR_WWW",""},
          {"ERW_TEMPLATE_DIR","/webdata/html/template/"},
          {"BASE_DIR_TCM_WWW","/webdata/html/"},
          {"ERW_REPORT_DIR_ACTIVE","/webdata/html/reports/temp/"},
          {"ERW_REPORT_DIR_BATCH","/webdata/html/reports/"},
          {"WEBS_HOME_WWW_ERW_ACTIVE","/reports/temp/"},
          {"WEBS_HOME_WWW_ERW_BATCH","/reports/"},
          {"CATALOGING_SERVLET","/tcmIS/swa/cataloging?"},
          {"PRINT_SCREEN","/tcmIS/swa/printscreen"},
          {"HELP_SERVLET_PATH","/tcmIS/client/help"},

          {"DROPRECEIVING","No"},
          {"RELABELING","No"},
          {"DROPRECEIVING_SERVLET","/tcmIS/swa/dropshipreceivingmain.do"},
          {"DROPRECEIVING_SERVLET_HEADER","/tcmIS/swa/dropshipreceivingmain.do"},
          {"RELABELING_SERVLET","/tcmIS/swa/Relabeling?session=Active"},

		  {"COST_REPORT","/tcmIS/swa/betacostreport?"},
		  {"SCHEDULDE_DELIVERY","/tcmIS/swa/schdelivery?"},
		  {"CANCEL_MR","/tcmIS/swa/cancel?"},
		  {"WASTE_ORDER","/tcmIS/swa/wasteorder?"},
		  {"WASTE_ADHOC_REPORT","/tcmIS/swa/wastereport?"},
		  {"USAGE_ADHOC_REPORT","/tcmIS/swa/usagereport?"},
		  {"MATERIAL_MATRIX_REPORT","/tcmIS/swa/matrixreport?"},

		  {"SHIPPINGINFO","No"},
		  {"SHIPPINGINFO_SERVLET","/tcmIS/swa/shipinfo?session=Active"},
		  {"SHIPPINGINFO_SERVLET_HEADER","/tcmIS/swa/shipinfo"},
                  {"WEB_ORDER_TRACKING","Yes"},
                  {"WEB_ORDER_TRACKING_SERVLET","/tcmIS/swa/ordertrackingmain.do"},
           {"WEB_REGISTRATION","Yes"}
     };

     public SWAServerResourceBundle(){
            super();
            addHash(cHash);
     }

}