package radian.web.servlets.gema;

import radian.tcmis.server7.share.helpers.ServerResourceBundle;

public class GemaServerResourceBundle  extends ServerResourceBundle {

     static final Object[][] cHash = {
          {"LOG_DIR","/logs"},
          {"DEBUG","false"},
          {"DB_CLIENT","GEMA"},
          {"ORACLE_DRIVER",""},
          {"ORACLE_URL",""},
          {"DB_USER",""},

          {"RELATIVE_HTML","/tcmIS/msds/gema"},
          {"DB_PASS",""},
          {"DB_CLIENT_NAME","GEMA"},
          {"WEBS_HOME_WWWS",""},
          {"WEBS_HOME_WWW",""},

          {"HOME_PAGE","/tcmIS/gema/Home"},
          {"HOME_IMAGE","/images/gema.gif"},
          {"HELP_HTML_PATH","/tcmIS/help/new/"},

          {"VIEW_MSDS","/tcmIS/gema/ViewMsds?"},
          {"REGISTER_SERVLET","/tcmIS/gema/Register"},
          {"HOME_SERVLET","/tcmIS/gema/Home"},
          {"JNLP_MAINCLASS","RadianCCGema.jar"},
          {"JNLP_DIRECTORY","/jnlp/gema"},
          {"TEST_JNLP_DIRECTORY","/prereleasejnlp/gema"},
          {"TCMIS_START_CLASS","radian.tcmis.client.client.gema.gui.GemaCmisApp"},
          {"BATCH_REPORT_SERVLET","/tcmIS/gema/batchreport?"},

          {"ADDUSER_SERVLET","/tcmIS/gema/Register?useraction=AddUser"},
          {"CHGUSER_SERVLET","/tcmIS/gema/Register?useraction=ChgUser"},
          {"ADD_SERVLET","/tcmIS/gema/Register?useraction=Add"},
          {"LOGINREG_SERVLET","/tcmIS/gema/Register?useraction=Login"},
          {"CHANGE_SERVLET","/tcmIS/gema/Register?useraction=Change"},
          {"REGISTER_HOME_SERVLET","/tcmIS/gema/Register?"},
          {"TEST_INSTALL_SERVLET","/tcmIS/gema/Register?useraction=startingtcmis"},
          {"BAR_BUILD_SERVLET","/tcmIS/gema/Msds?limit=yes"},
          {"BUILD_SERVLET","/tcmIS/gema/Msds?"},
          {"SERVLET_DIR_WWW",""},
          {"ERW_TEMPLATE_DIR","/webdata/html/template/"},
          {"BASE_DIR_TCM_WWW","/webdata/html/"},
          {"ERW_REPORT_DIR_ACTIVE","/webdata/html/reports/temp/"},
          {"ERW_REPORT_DIR_BATCH","/webdata/html/reports/"},
          {"WEBS_HOME_WWW_ERW_ACTIVE","/reports/temp/"},
          {"WEBS_HOME_WWW_ERW_BATCH","/reports/"},
          {"CATALOGING_SERVLET","/tcmIS/gema/cataloging?"},
          {"PRINT_SCREEN","/tcmIS/gema/printscreen"},
          {"HELP_SERVLET_PATH","/tcmIS/client/help"},

          {"DROPRECEIVING","No"},
          {"RELABELING","No"},
          {"DROPRECEIVING_SERVLET","/tcmIS/gema/dropshipreceivingmain.do"},
          {"DROPRECEIVING_SERVLET_HEADER","/tcmIS/gema/dropshipreceivingmain.do"},
          {"RELABELING_SERVLET","/tcmIS/gema/Relabeling?session=Active"},

		  {"COST_REPORT","/tcmIS/gema/betacostreport?"},
		  {"SCHEDULDE_DELIVERY","/tcmIS/gema/schdelivery?"},
		  {"CANCEL_MR","/tcmIS/gema/cancel?"},
		  {"WASTE_ORDER","/tcmIS/gema/wasteorder?"},
		  {"WASTE_ADHOC_REPORT","/tcmIS/gema/wastereport?"},
		  {"USAGE_ADHOC_REPORT","/tcmIS/gema/usagereport?"},
		  {"MATERIAL_MATRIX_REPORT","/tcmIS/gema/matrixreport?"},

		  {"SHIPPINGINFO","No"},
		  {"SHIPPINGINFO_SERVLET","/tcmIS/gema/shipinfo?session=Active"},
		  {"SHIPPINGINFO_SERVLET_HEADER","/tcmIS/gema/shipinfo"},
                  {"WEB_ORDER_TRACKING","Yes"},
                  {"WEB_ORDER_TRACKING_SERVLET","/tcmIS/gema/ordertrackingmain.do"},
           {"WEB_REGISTRATION","Yes"}

     };

     public GemaServerResourceBundle(){
            super();
            addHash(cHash);
     }

}