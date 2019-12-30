package radian.web.servlets.bae;

import radian.tcmis.server7.share.helpers.ServerResourceBundle;

public class BAEServerResourceBundle  extends ServerResourceBundle {

     static final Object[][] cHash = {
          {"LOG_DIR","/logsBAE"},
          {"DEBUG","false"},
          {"DB_CLIENT","BAE"},
          {"ORACLE_DRIVER",""},
          {"ORACLE_URL",""},
          {"DB_USER",""},

          {"RELATIVE_HTML","/tcmIS/msds/bae"},
          {"DB_PASS",""},
          {"DB_CLIENT_NAME","BAE"},
          {"WEBS_HOME_WWWS",""},
          {"WEBS_HOME_WWW",""},

          {"HOME_PAGE","/tcmIS/bae/Home"},
          {"HOME_IMAGE","/images/bae.gif"},
          {"HELP_HTML_PATH","/tcmIS/help/new/"},

          {"VIEW_MSDS","/tcmIS/bae/ViewMsds?"},
          {"REGISTER_SERVLET","/tcmIS/bae/Register"},
          {"HOME_SERVLET","/tcmIS/bae/Home"},
          {"JNLP_MAINCLASS","RadianCCBAE.jar"},
          {"JNLP_DIRECTORY","/jnlp/bae"},
          {"TEST_JNLP_DIRECTORY","/prereleasejnlp/bae"},
          {"TCMIS_START_CLASS","radian.tcmis.client.client.bae.gui.BAECmisApp"},
          {"BATCH_REPORT_SERVLET","/tcmIS/bae/batchreport?"},

          {"ADDUSER_SERVLET","/tcmIS/bae/Register?useraction=AddUser"},
          {"CHGUSER_SERVLET","/tcmIS/bae/Register?useraction=ChgUser"},
          {"ADD_SERVLET","/tcmIS/bae/Register?useraction=Add"},
          {"LOGINREG_SERVLET","/tcmIS/bae/Register?useraction=Login"},
          {"CHANGE_SERVLET","/tcmIS/bae/Register?useraction=Change"},
          {"REGISTER_HOME_SERVLET","/tcmIS/bae/Register?"},
          {"TEST_INSTALL_SERVLET","/tcmIS/bae/Register?useraction=startingtcmis"},
          {"BAR_BUILD_SERVLET","/tcmIS/bae/Msds?limit=yes"},
          {"BUILD_SERVLET","/tcmIS/bae/Msds?"},
          {"SERVLET_DIR_WWW",""},
          {"ERW_TEMPLATE_DIR","/webdata/html/template/"},
          {"BASE_DIR_TCM_WWW","/webdata/html/"},
          {"ERW_REPORT_DIR_ACTIVE","/webdata/html/reports/temp/"},
          {"ERW_REPORT_DIR_BATCH","/webdata/html/reports/"},
          {"WEBS_HOME_WWW_ERW_ACTIVE","/reports/temp/"},
          {"WEBS_HOME_WWW_ERW_BATCH","/reports/"},
          {"CATALOGING_SERVLET","/tcmIS/bae/cataloging?"},
          {"PRINT_SCREEN","/tcmIS/bae/printscreen"},
          {"HELP_SERVLET_PATH","/tcmIS/client/help"},

          {"DROPRECEIVING","Yes"},
          {"RECEIPT_DOCUMENT","Yes"},
          {"RELABELING","No"},
          {"DROPRECEIVING_SERVLET","/tcmIS/bae/dropshipreceivingmain.do"},
          {"DROPRECEIVING_SERVLET_HEADER","/tcmIS/bae/dropshipreceivingmain.do"},
          {"RECEIPT_DOCUMENT_SERVLET","/tcmIS/bae/receiptdocumentviewermain.do"},
          {"RELABELING_SERVLET","/tcmIS/bae/Relabeling?session=Active"},


		  {"COST_REPORT","/tcmIS/bae/betacostreport?"},
		  {"SCHEDULDE_DELIVERY","/tcmIS/bae/schdelivery?"},
		  {"CANCEL_MR","/tcmIS/bae/cancel?"},
		  {"WASTE_ORDER","/tcmIS/bae/wasteorder?"},
		  {"WASTE_ADHOC_REPORT","/tcmIS/bae/wastereport?"},
		  {"USAGE_ADHOC_REPORT","/tcmIS/bae/usagereport?"},
		  {"MATERIAL_MATRIX_REPORT","/tcmIS/bae/matrixreport?"},

		  {"SHIPPINGINFO","No"},
		  {"SHIPPINGINFO_SERVLET","/tcmIS/bae/shipinfo?session=Active"},
		  {"SHIPPINGINFO_SERVLET_HEADER","/tcmIS/bae/shipinfo"},
                  {"WEB_ORDER_TRACKING","Yes"},
                  {"WEB_ORDER_TRACKING_SERVLET","/tcmIS/bae/ordertrackingmain.do"},
          {"WEB_REGISTRATION","Yes"}

     };

     public BAEServerResourceBundle(){
            super();
            addHash(cHash);
     }

}