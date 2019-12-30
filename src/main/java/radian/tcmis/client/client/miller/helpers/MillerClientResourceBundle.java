package radian.tcmis.client.client.miller.helpers;

import radian.tcmis.client.share.helpers.ClientResourceBundle;

public class MillerClientResourceBundle  extends ClientResourceBundle {

     static final Object[][] cHash = {
             {"SERVLET_PACKAGE","radian.tcmis.server7.client.miller.servlets."},
             {"APP_NAME","Miller"},
             {"SHOW_LOGON_FRAME","No"},        //10-29-01 whether to show the logon frame or not
             {"USE_GLOBAL_PASSWORD","No"},
             {"GLOBAL_PASSWORD","t"},
             {"LOG_DIR","/temp/logs/miller"},
             {"VERSION","1.8.05"},
             {"DOWNLOAD_DIR",""},
             {"EXEC_FILE","tcmISRun.exe"},
             {"CONFIG_FILE","tcmis.cfg"},
             {"DEBUG_FILE","cmisapp.deb"},
             {"START_CLASS","radian.tcmis.client.client.miller.gui.MillerCmisApp"},
             {"DEBUG","false"},
             {"CLIENT_INITIALS","Miller"},
             {"MSDS_URL","/tcmIS/miller/ViewMsds?"},
             {"WASTE_URL","/tcmIS/miller/wasteprofile?"},
             {"PRINT_SCREEN_URL","/tcmIS/miller/printscreen?"},
             {"WORK_AREA_NAME","Work Area"},
             {"WORK_AREA_NAME_PLURAL","Work Areas"}
     };

     public MillerClientResourceBundle(){
            super();
            addHash(cHash);
     }

}