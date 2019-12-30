package radian.tcmis.client.client.sales.helpers;

import radian.tcmis.client.share.helpers.ClientResourceBundle;

public class SalesClientResourceBundle  extends ClientResourceBundle {

     static final Object[][] cHash = {
             {"SERVLET_PACKAGE","radian.tcmis.server7.client.sales.servlets."},
             {"APP_NAME","Sales"},
             {"SHOW_LOGON_FRAME","No"},        //10-29-01 whether to show the logon frame or not
             {"USE_GLOBAL_PASSWORD","No"},
             {"GLOBAL_PASSWORD","t"},
             {"LOG_DIR","/temp/logs/sales"},
             {"VERSION","1.8.05"},
             {"DOWNLOAD_DIR",""},
             {"EXEC_FILE","tcmISRun.exe"},
             {"CONFIG_FILE","tcmis.cfg"},
             {"DEBUG_FILE","cmisapp.deb"},
             {"START_CLASS","radian.tcmis.client.client.sales.gui.SalesCmisApp"},
             {"DEBUG","false"},
             {"CLIENT_INITIALS","Sales"},
             {"MSDS_URL","/tcmIS/sales/ViewMsds?"},
             {"WASTE_URL","/tcmIS/sales/wasteprofile?"},
             {"PRINT_SCREEN_URL","/tcmIS/sales/printscreen?"},
             {"WORK_AREA_NAME","Work Area"},
             {"WORK_AREA_NAME_PLURAL","Work Areas"}
     };

     public SalesClientResourceBundle(){
            super();
            addHash(cHash);
     }

}