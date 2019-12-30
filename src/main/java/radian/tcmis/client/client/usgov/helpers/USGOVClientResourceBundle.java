package radian.tcmis.client.client.usgov.helpers;

import radian.tcmis.client.share.helpers.ClientResourceBundle;

public class USGOVClientResourceBundle  extends ClientResourceBundle {

     static final Object[][] cHash = {
             {"SERVLET_PACKAGE","radian.tcmis.server7.client.usgov.servlets."},
             {"APP_NAME","United States Government"},
             {"SHOW_LOGON_FRAME","No"},        //10-29-01 whether to show the logon frame or not
             {"USE_GLOBAL_PASSWORD","No"},
             {"GLOBAL_PASSWORD","t"},
             {"LOG_DIR","/temp/logs/usgov"},
             {"VERSION","1.8.05"},
             {"DOWNLOAD_DIR",""},
             {"EXEC_FILE","tcmISRun.exe"},
             {"CONFIG_FILE","tcmis.cfg"},
             {"DEBUG_FILE","cmisapp.deb"},
             {"START_CLASS","radian.tcmis.client.client.usgov.gui.USGOVCmisApp"},
             {"DEBUG","false"},
             {"CLIENT_INITIALS","USGOV"},
             {"MSDS_URL","/tcmIS/usgov/ViewMsds?"},
             {"WASTE_URL","/tcmIS/usgov/wasteprofile?"},
             {"PRINT_SCREEN_URL","/tcmIS/usgov/printscreen?"},
             {"WORK_AREA_NAME","Work Area"},
             {"WORK_AREA_NAME_PLURAL","Work Areas"}
     };

     public USGOVClientResourceBundle(){
            super();
            addHash(cHash);
     }

}