package radian.tcmis.client.client.kemfast.helpers;

import radian.tcmis.client.share.helpers.ClientResourceBundle;

public class KemfastClientResourceBundle  extends ClientResourceBundle {

     static final Object[][] cHash = {
             {"SERVLET_PACKAGE","radian.tcmis.server7.client.kemfast.servlets."},
             {"APP_NAME","Kemfast Aerospace LTD"},
             {"SHOW_LOGON_FRAME","No"},        //10-29-01 whether to show the logon frame or not
             {"USE_GLOBAL_PASSWORD","No"},
             {"GLOBAL_PASSWORD","t"},
             {"LOG_DIR","/temp/logs/kemfast"},
             {"VERSION","1.8.05"},
             {"DOWNLOAD_DIR",""},
             {"EXEC_FILE","tcmISRun.exe"},
             {"CONFIG_FILE","tcmis.cfg"},
             {"DEBUG_FILE","cmisapp.deb"},
             {"START_CLASS","radian.tcmis.client.client.kemfast.gui.KemfastCmisApp"},
             {"DEBUG","false"},
             {"CLIENT_INITIALS","Kemfast"},
             {"MSDS_URL","/tcmIS/kemfast/ViewMsds?"},
             {"WASTE_URL","/tcmIS/kemfast/wasteprofile?"},
             {"PRINT_SCREEN_URL","/tcmIS/kemfast/printscreen?"},
             {"WORK_AREA_NAME","Work Area"},
             {"WORK_AREA_NAME_PLURAL","Work Areas"}
     };

     public KemfastClientResourceBundle(){
            super();
            addHash(cHash);
     }

}