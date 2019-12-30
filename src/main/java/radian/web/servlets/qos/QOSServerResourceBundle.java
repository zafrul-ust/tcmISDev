package radian.web.servlets.qos;

import radian.tcmis.server7.share.helpers.ServerResourceBundle;

public class QOSServerResourceBundle  extends ServerResourceBundle {

     static final Object[][] cHash = {
          {"LOG_DIR","/logsUTC"},
          {"DEBUG","false"},
          {"DB_CLIENT","QOS"},
          {"ORACLE_DRIVER",""},
          {"ORACLE_URL",""},
          {"DB_USER",""},

          {"RELATIVE_HTML","/tcmIS/msds/qos"},
          {"DB_PASS",""},
          {"DB_CLIENT_NAME","QOS"},
          {"WEBS_HOME_WWWS",""},
          {"WEBS_HOME_WWW",""},

          {"HOME_PAGE","/tcmIS/qos/Home"},
          {"HOME_IMAGE","/images/qos.gif"},
          {"HELP_HTML_PATH","/tcmIS/help/new/"},

          {"VIEW_MSDS","/tcmIS/qos/ViewMsds?"},
          {"REGISTER_SERVLET","/tcmIS/qos/Register"},
          {"HOME_SERVLET","/tcmIS/qos/Home"},
          {"JNLP_MAINCLASS","RadianCCQOS.jar"},
          {"JNLP_DIRECTORY","/jnlp/qos"},
          {"TEST_JNLP_DIRECTORY","/prereleasejnlp/qos"},
          {"TCMIS_START_CLASS","radian.tcmis.client.client.qos.gui.QOSCmisApp"},
          {"BATCH_REPORT_SERVLET","/tcmIS/qos/batchreport?"},

          {"ADDUSER_SERVLET","/tcmIS/qos/Register?useraction=AddUser"},
          {"CHGUSER_SERVLET","/tcmIS/qos/Register?useraction=ChgUser"},
          {"ADD_SERVLET","/tcmIS/qos/Register?useraction=Add"},
          {"LOGINREG_SERVLET","/tcmIS/qos/Register?useraction=Login"},
          {"CHANGE_SERVLET","/tcmIS/qos/Register?useraction=Change"},
          {"REGISTER_HOME_SERVLET","/tcmIS/qos/Register?"},
          {"TEST_INSTALL_SERVLET","/tcmIS/qos/Register?useraction=startingtcmis"},
          {"BAR_BUILD_SERVLET","/tcmIS/qos/Msds?limit=yes"},
          {"BUILD_SERVLET","/tcmIS/qos/Msds?"},
          {"SERVLET_DIR_WWW",""},
          {"ERW_TEMPLATE_DIR","/webdata/html/template/"},
          {"BASE_DIR_TCM_WWW","/webdata/html/"},
          {"ERW_REPORT_DIR_ACTIVE","/webdata/html/reports/temp/"},
          {"ERW_REPORT_DIR_BATCH","/webdata/html/reports/"},
          {"WEBS_HOME_WWW_ERW_ACTIVE","/reports/temp/"},
          {"WEBS_HOME_WWW_ERW_BATCH","/reports/"},
          {"CATALOGING_SERVLET","/tcmIS/qos/cataloging?"},
          {"PRINT_SCREEN","/tcmIS/qos/printscreen"},
          {"HELP_SERVLET_PATH","/tcmIS/client/help"},

          {"DROPRECEIVING","Yes"},
          {"RELABELING","No"},
          {"DROPRECEIVING_SERVLET","/tcmIS/qos/dropshipreceivingmain.do"},
          {"DROPRECEIVING_SERVLET_HEADER","/tcmIS/qos/dropshipreceivingmain.do"},
          {"RELABELING_SERVLET","/tcmIS/qos/Relabeling?session=Active"},

		  {"COST_REPORT","/tcmIS/qos/betacostreport?"},
		  {"SCHEDULDE_DELIVERY","/tcmIS/qos/schdelivery?"},
		  {"CANCEL_MR","/tcmIS/qos/cancel?"},
		  {"WASTE_ORDER","/tcmIS/qos/wasteorder?"},
		  {"WASTE_ADHOC_REPORT","/tcmIS/qos/wastereport?"},
		  {"USAGE_ADHOC_REPORT","/tcmIS/qos/usagereport?"},
		  {"MATERIAL_MATRIX_REPORT","/tcmIS/qos/matrixreport?"},

		  {"SHIPPINGINFO","No"},
		  {"SHIPPINGINFO_SERVLET","/tcmIS/qos/shipinfo?session=Active"},
		  {"SHIPPINGINFO_SERVLET_HEADER","/tcmIS/qos/shipinfo"},
                  {"WEB_ORDER_TRACKING","Yes"},
                  {"WEB_ORDER_TRACKING_SERVLET","/tcmIS/qos/ordertrackingmain.do"},
           {"WEB_REGISTRATION","Yes"}

     };

     public QOSServerResourceBundle(){
            super();
            addHash(cHash);
     }

}