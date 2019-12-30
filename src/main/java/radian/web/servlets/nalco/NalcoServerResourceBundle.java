package radian.web.servlets.nalco;

import radian.tcmis.server7.share.helpers.ServerResourceBundle;

public class NalcoServerResourceBundle  extends ServerResourceBundle {

     static final Object[][] cHash = {
          {"LOG_DIR","/logsUTC"},
          {"DEBUG","false"},
          {"DB_CLIENT","Nalco"},
          {"ORACLE_DRIVER",""},
          {"ORACLE_URL",""},
          {"DB_USER",""},

          {"RELATIVE_HTML","/tcmIS/msds/nalco"},
          {"DB_PASS",""},
          {"DB_CLIENT_NAME","Nalco"},
          {"WEBS_HOME_WWWS",""},
          {"WEBS_HOME_WWW",""},

          {"HOME_PAGE","/tcmIS/nalco/Home"},
          {"HOME_IMAGE","/images/nalco.gif"},
          {"HELP_HTML_PATH","/tcmIS/help/new/"},

          {"VIEW_MSDS","/tcmIS/nalco/ViewMsds?"},
          {"REGISTER_SERVLET","/tcmIS/nalco/Register"},
          {"HOME_SERVLET","/tcmIS/nalco/Home"},
          {"JNLP_MAINCLASS","RadianCCNalco.jar"},
          {"JNLP_DIRECTORY","/jnlp/nalco"},
          {"TEST_JNLP_DIRECTORY","/prereleasejnlp/nalco"},
          {"TCMIS_START_CLASS","radian.tcmis.client.client.nalco.gui.NalcoCmisApp"},
          {"BATCH_REPORT_SERVLET","/tcmIS/nalco/batchreport?"},

          {"ADDUSER_SERVLET","/tcmIS/nalco/Register?useraction=AddUser"},
          {"CHGUSER_SERVLET","/tcmIS/nalco/Register?useraction=ChgUser"},
          {"ADD_SERVLET","/tcmIS/nalco/Register?useraction=Add"},
          {"LOGINREG_SERVLET","/tcmIS/nalco/Register?useraction=Login"},
          {"CHANGE_SERVLET","/tcmIS/nalco/Register?useraction=Change"},
          {"REGISTER_HOME_SERVLET","/tcmIS/nalco/Register?"},
          {"TEST_INSTALL_SERVLET","/tcmIS/nalco/Register?useraction=startingtcmis"},
          {"BAR_BUILD_SERVLET","/tcmIS/nalco/Msds?limit=yes"},
          {"BUILD_SERVLET","/tcmIS/nalco/Msds?"},
          {"SERVLET_DIR_WWW",""},
          {"ERW_TEMPLATE_DIR","/webdata/html/template/"},
          {"BASE_DIR_TCM_WWW","/webdata/html/"},
          {"ERW_REPORT_DIR_ACTIVE","/webdata/html/reports/temp/"},
          {"ERW_REPORT_DIR_BATCH","/webdata/html/reports/"},
          {"WEBS_HOME_WWW_ERW_ACTIVE","/reports/temp/"},
          {"WEBS_HOME_WWW_ERW_BATCH","/reports/"},
          {"CATALOGING_SERVLET","/tcmIS/nalco/cataloging?"},
          {"PRINT_SCREEN","/tcmIS/nalco/printscreen"},
          {"HELP_SERVLET_PATH","/tcmIS/client/help"},

          {"DROPRECEIVING","Yes"},
          {"RELABELING","No"},
          {"DROPRECEIVING_SERVLET","/tcmIS/nalco/dropshipreceivingmain.do"},
          {"DROPRECEIVING_SERVLET_HEADER","/tcmIS/nalco/dropshipreceivingmain.do"},
          {"RELABELING_SERVLET","/tcmIS/nalco/Relabeling?session=Active"},

		  {"COST_REPORT","/tcmIS/nalco/betacostreport?"},
		  {"SCHEDULDE_DELIVERY","/tcmIS/nalco/schdelivery?"},
		  {"CANCEL_MR","/tcmIS/nalco/cancel?"},
		  {"WASTE_ORDER","/tcmIS/nalco/wasteorder?"},
		  {"WASTE_ADHOC_REPORT","/tcmIS/nalco/wastereport?"},
		  {"USAGE_ADHOC_REPORT","/tcmIS/nalco/usagereport?"},
		  {"MATERIAL_MATRIX_REPORT","/tcmIS/nalco/matrixreport?"},

		  {"SHIPPINGINFO","No"},
		  {"SHIPPINGINFO_SERVLET","/tcmIS/nalco/shipinfo?session=Active"},
		  {"SHIPPINGINFO_SERVLET_HEADER","/tcmIS/nalco/shipinfo"},

                  {"WEB_ORDER_TRACKING","Yes"},
                  {"WEB_ORDER_TRACKING_SERVLET","/tcmIS/nalco/ordertrackingmain.do"},
                  {"WEB_INVENTORY","Yes"},
                  {"WEB_INVENTORY_SERVLET","/tcmIS/nalco/inventorymain.do"},
                  {"WEB_CATALOG","Yes"},
                  {"WEB_CATALOG_SERVLET","/tcmIS/nalco/catalog.do"},

           {"WEB_REGISTRATION","Yes"}

     };

     public NalcoServerResourceBundle(){
            super();
            addHash(cHash);
     }

}