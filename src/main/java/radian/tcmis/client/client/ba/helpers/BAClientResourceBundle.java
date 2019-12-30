package radian.tcmis.client.client.ba.helpers;

import radian.tcmis.client.share.helpers.ClientResourceBundle;

public class BAClientResourceBundle  extends ClientResourceBundle {

     static final Object[][] cHash = {
             {"SERVLET_PACKAGE","radian.tcmis.server7.client.ba.servlets."},
             {"APP_NAME","British Airways"},
             {"SHOW_LOGON_FRAME","No"},        //10-29-01 whether to show the logon frame or not
             {"USE_GLOBAL_PASSWORD","No"},
             {"GLOBAL_PASSWORD","t"},
             {"LOG_DIR","/temp/logs/ba"},
             {"VERSION","1.8.05"},
             {"DOWNLOAD_DIR",""},
             {"EXEC_FILE","tcmISRun.exe"},
             {"CONFIG_FILE","tcmis.cfg"},
             {"DEBUG_FILE","cmisapp.deb"},
             {"START_CLASS","radian.tcmis.client.client.ba.gui.BACmisApp"},
             {"DEBUG","false"},
             {"CLIENT_INITIALS","BA"},
             {"MSDS_URL","/tcmIS/ba/ViewMsds?"},
             {"WASTE_URL","/tcmIS/ba/wasteprofile?"},
             {"PRINT_SCREEN_URL","/tcmIS/ba/printscreen?"},
             {"WORK_AREA_NAME","Work Area"},
             {"WORK_AREA_NAME_PLURAL","Work Areas"}
     };

     public BAClientResourceBundle(){
            super();
            addHash(cHash);
     }

}