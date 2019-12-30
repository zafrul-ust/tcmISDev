package radian.web.servlets.drs;

import radian.tcmis.server7.share.helpers.ServerResourceBundle;

public class DRSServerResourceBundle  extends ServerResourceBundle {

     static final Object[][] cHash = {
          {"LOG_DIR","/logsdrs"},
          {"DEBUG","false"},
          {"DB_CLIENT","DRS"},
          {"ORACLE_DRIVER",""},
          {"ORACLE_URL",""},
          {"DB_USER",""},

          {"RELATIVE_HTML","/tcmIS/msds/drs"},
          {"DB_PASS",""},
          {"DB_CLIENT_NAME","DRS"},
          {"WEBS_HOME_WWWS",""},
          {"WEBS_HOME_WWW",""},

          {"HOME_PAGE","/tcmIS/drs/Home"},
          {"HOME_IMAGE","/images/buttons/drs/drs.gif"},
          {"HELP_HTML_PATH","/tcmIS/help/new/"},

          {"VIEW_MSDS","/tcmIS/drs/ViewMsds?"},
          {"REGISTER_SERVLET","/tcmIS/drs/Register"},
          {"HOME_SERVLET","/tcmIS/drs/Home"},
          {"JNLP_MAINCLASS","RadianCCDRS.jar"},
          {"JNLP_DIRECTORY","/jnlp/drs"},
          {"TEST_JNLP_DIRECTORY","/prereleasejnlp/drs"},
          {"TCMIS_START_CLASS","radian.tcmis.client.client.drs.gui.DRSCmisApp"},
          {"BATCH_REPORT_SERVLET","/tcmIS/drs/batchreport?"},

          {"ADDUSER_SERVLET","/tcmIS/drs/Register?useraction=AddUser"},
          {"CHGUSER_SERVLET","/tcmIS/drs/Register?useraction=ChgUser"},
          {"ADD_SERVLET","/tcmIS/drs/Register?useraction=Add"},
          {"LOGINREG_SERVLET","/tcmIS/drs/Register?useraction=Login"},
          {"CHANGE_SERVLET","/tcmIS/drs/Register?useraction=Change"},
          {"REGISTER_HOME_SERVLET","/tcmIS/drs/Register?"},
          {"TEST_INSTALL_SERVLET","/tcmIS/drs/Register?useraction=startingtcmis"},
          {"BAR_BUILD_SERVLET","/tcmIS/drs/Msds?limit=yes"},
          {"BUILD_SERVLET","/tcmIS/drs/Msds?"},
          {"SERVLET_DIR_WWW",""},
          {"ERW_TEMPLATE_DIR","/webdata/html/template/"},
          {"BASE_DIR_TCM_WWW","/webdata/html/"},
          {"ERW_REPORT_DIR_ACTIVE","/webdata/html/reports/temp/"},
          {"ERW_REPORT_DIR_BATCH","/webdata/html/reports/"},
          {"WEBS_HOME_WWW_ERW_ACTIVE","/reports/temp/"},
          {"WEBS_HOME_WWW_ERW_BATCH","/reports/"},
          {"CATALOGING_SERVLET","/tcmIS/drs/cataloging?"},
          {"PRINT_SCREEN","/tcmIS/drs/printscreen"},
          {"HELP_SERVLET_PATH","/tcmIS/client/help"},

          {"DROPRECEIVING","Yes"},
          {"RELABELING","No"},
          {"DROPRECEIVING_SERVLET","/tcmIS/drs/dropshipreceivingmain.do"},
          {"DROPRECEIVING_SERVLET_HEADER","/tcmIS/drs/dropshipreceivingmain.do"},
          {"RELABELING_SERVLET","/tcmIS/drs/Relabeling?session=Active"},

		  {"COST_REPORT","/tcmIS/drs/betacostreport?"},
		  {"SCHEDULDE_DELIVERY","/tcmIS/drs/schdelivery?"},
		  {"CANCEL_MR","/tcmIS/drs/cancel?"},
		  {"WASTE_ORDER","/tcmIS/drs/wasteorder?"},
		  {"WASTE_ADHOC_REPORT","/tcmIS/drs/wastereport?"},
		  {"USAGE_ADHOC_REPORT","/tcmIS/drs/usagereport?"},
		  {"MATERIAL_MATRIX_REPORT","/tcmIS/drs/matrixreport?"},

		  {"SHIPPINGINFO","No"},
		  {"SHIPPINGINFO_SERVLET","/tcmIS/drs/shipinfo?session=Active"},
		  {"SHIPPINGINFO_SERVLET_HEADER","/tcmIS/drs/shipinfo"},
                  {"WEB_ORDER_TRACKING","Yes"},
                  {"WEB_ORDER_TRACKING_SERVLET","/tcmIS/drs/ordertrackingmain.do"},
           {"WEB_REGISTRATION","Yes"}
     };

     public DRSServerResourceBundle(){
            super();
            addHash(cHash);
     }

}