package radian.web.servlets.fec;

import radian.tcmis.server7.share.helpers.ServerResourceBundle;

public class FECServerResourceBundle  extends ServerResourceBundle {

     static final Object[][] cHash = {
          {"LOG_DIR","/logsUTC"},
          {"DEBUG","false"},
          {"DB_CLIENT","FEC"},
          {"ORACLE_DRIVER",""},
          {"ORACLE_URL",""},
          {"DB_USER",""},

          {"RELATIVE_HTML","/tcmIS/msds/fec"},
          {"DB_PASS",""},
          {"DB_CLIENT_NAME","FEC"},
          {"WEBS_HOME_WWWS",""},
          {"WEBS_HOME_WWW",""},

          {"HOME_PAGE","/tcmIS/fec/Home"},
          {"HOME_IMAGE","/images/fec.gif"},
          {"HELP_HTML_PATH","/tcmIS/help/new/"},

          {"VIEW_MSDS","/tcmIS/fec/ViewMsds?"},
          {"REGISTER_SERVLET","/tcmIS/fec/Register"},
          {"HOME_SERVLET","/tcmIS/fec/Home"},
          {"JNLP_MAINCLASS","RadianCCFEC.jar"},
          {"JNLP_DIRECTORY","/jnlp/fec"},
          {"TEST_JNLP_DIRECTORY","/prereleasejnlp/fec"},
          {"TCMIS_START_CLASS","radian.tcmis.client.client.fec.gui.FECCmisApp"},
          {"BATCH_REPORT_SERVLET","/tcmIS/fec/batchreport?"},

          {"ADDUSER_SERVLET","/tcmIS/fec/Register?useraction=AddUser"},
          {"CHGUSER_SERVLET","/tcmIS/fec/Register?useraction=ChgUser"},
          {"ADD_SERVLET","/tcmIS/fec/Register?useraction=Add"},
          {"LOGINREG_SERVLET","/tcmIS/fec/Register?useraction=Login"},
          {"CHANGE_SERVLET","/tcmIS/fec/Register?useraction=Change"},
          {"REGISTER_HOME_SERVLET","/tcmIS/fec/Register?"},
          {"TEST_INSTALL_SERVLET","/tcmIS/fec/Register?useraction=startingtcmis"},
          {"BAR_BUILD_SERVLET","/tcmIS/fec/Msds?limit=yes"},
          {"BUILD_SERVLET","/tcmIS/fec/Msds?"},
          {"SERVLET_DIR_WWW",""},
          {"ERW_TEMPLATE_DIR","/webdata/html/template/"},
          {"BASE_DIR_TCM_WWW","/webdata/html/"},
          {"ERW_REPORT_DIR_ACTIVE","/webdata/html/reports/temp/"},
          {"ERW_REPORT_DIR_BATCH","/webdata/html/reports/"},
          {"WEBS_HOME_WWW_ERW_ACTIVE","/reports/temp/"},
          {"WEBS_HOME_WWW_ERW_BATCH","/reports/"},
          {"CATALOGING_SERVLET","/tcmIS/fec/cataloging?"},
          {"CATALOG_SOURCING_SERVLET","/tcmIS/fec/sourcing?"},
          {"PRINT_SCREEN","/tcmIS/fec/printscreen"},
          {"HELP_SERVLET_PATH","/tcmIS/client/help"},

          {"DROPRECEIVING","Yes"},
          {"RELABELING","No"},
          {"DROPRECEIVING_SERVLET","/tcmIS/fec/dropshipreceivingmain.do"},
          {"DROPRECEIVING_SERVLET_HEADER","/tcmIS/fec/dropshipreceivingmain.do"},
          {"RELABELING_SERVLET","/tcmIS/fec/Relabeling?session=Active"},

		  {"COST_REPORT","/tcmIS/fec/betacostreport?"},
		  {"SCHEDULDE_DELIVERY","/tcmIS/fec/schdelivery?"},
		  {"CANCEL_MR","/tcmIS/fec/cancel?"},
		  {"WASTE_ORDER","/tcmIS/fec/wasteorder?"},
		  {"WASTE_ADHOC_REPORT","/tcmIS/fec/wastereport?"},
		  {"USAGE_ADHOC_REPORT","/tcmIS/fec/usagereport?"},
		  {"MATERIAL_MATRIX_REPORT","/tcmIS/fec/matrixreport?"},

		  {"SHIPPINGINFO","No"},
		  {"SHIPPINGINFO_SERVLET","/tcmIS/fec/shipinfo?session=Active"},
		  {"SHIPPINGINFO_SERVLET_HEADER","/tcmIS/fec/shipinfo"},
                  {"WEB_ORDER_TRACKING","Yes"},
                  {"WEB_ORDER_TRACKING_SERVLET","/tcmIS/fec/ordertrackingmain.do"},
           {"WEB_REGISTRATION","Yes"}

     };

     public FECServerResourceBundle(){
            super();
            addHash(cHash);
     }

}