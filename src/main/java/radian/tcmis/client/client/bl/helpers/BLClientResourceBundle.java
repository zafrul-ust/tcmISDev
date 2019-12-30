package radian.tcmis.client.client.bl.helpers;

import radian.tcmis.client.share.helpers.ClientResourceBundle;

public class BLClientResourceBundle  extends ClientResourceBundle {

     static final Object[][] cHash = {
             {"SERVLET_PACKAGE","radian.tcmis.server7.client.bl.servlets."},
             {"APP_NAME","BL Advanced Ground Support Systems Ltd."},
             {"SHOW_LOGON_FRAME","No"},        //10-29-01 whether to show the logon frame or not
             {"USE_GLOBAL_PASSWORD","No"},
             {"GLOBAL_PASSWORD","t"},
             {"LOG_DIR","/temp/logs/bl"},
             {"VERSION","1.8.05"},
             {"DOWNLOAD_DIR",""},
             {"EXEC_FILE","tcmISRun.exe"},
             {"CONFIG_FILE","tcmis.cfg"},
             {"DEBUG_FILE","cmisapp.deb"},
             {"START_CLASS","radian.tcmis.client.client.bl.gui.BLCmisApp"},
             {"DEBUG","false"},
             {"CLIENT_INITIALS","BL"},
             {"MSDS_URL","/tcmIS/bl/ViewMsds?"},
             {"WASTE_URL","/tcmIS/bl/wasteprofile?"},
             {"PRINT_SCREEN_URL","/tcmIS/bl/printscreen?"},
             {"WORK_AREA_NAME","Work Area"},
             {"WORK_AREA_NAME_PLURAL","Work Areas"}
     };

     public BLClientResourceBundle(){
            super();
            addHash(cHash);
     }

}