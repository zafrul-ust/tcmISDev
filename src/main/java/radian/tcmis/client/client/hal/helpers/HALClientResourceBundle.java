package radian.tcmis.client.client.hal.helpers;

import radian.tcmis.client.share.helpers.ClientResourceBundle;

public class HALClientResourceBundle  extends ClientResourceBundle {

     static final Object[][] cHash = {
             {"SERVLET_PACKAGE","radian.tcmis.server7.client.hal.servlets."},
             {"APP_NAME","Hindustan Aeronautics Limited"},
             {"SHOW_LOGON_FRAME","No"},        //10-29-01 whether to show the logon frame or not
             {"USE_GLOBAL_PASSWORD","No"},
             {"GLOBAL_PASSWORD","t"},
             {"LOG_DIR","/temp/logs/hal"},
             {"VERSION","1.8.05"},
             {"DOWNLOAD_DIR",""},
             {"EXEC_FILE","tcmISRun.exe"},
             {"CONFIG_FILE","tcmis.cfg"},
             {"DEBUG_FILE","cmisapp.deb"},
             {"START_CLASS","radian.tcmis.client.client.hal.gui.HALCmisApp"},
             {"DEBUG","false"},
             {"CLIENT_INITIALS","HAL"},
             {"MSDS_URL","/tcmIS/hal/ViewMsds?"},
             {"WASTE_URL","/tcmIS/hal/wasteprofile?"},
             {"PRINT_SCREEN_URL","/tcmIS/hal/printscreen?"},
             {"WORK_AREA_NAME","Work Area"},
             {"WORK_AREA_NAME_PLURAL","Work Areas"}
     };

     public HALClientResourceBundle(){
            super();
            addHash(cHash);
     }

}