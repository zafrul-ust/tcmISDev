package radian.tcmis.client.client.swa.helpers;

import radian.tcmis.client.share.helpers.ClientResourceBundle;

public class SWAClientResourceBundle  extends ClientResourceBundle {

     static final Object[][] cHash = {
             //{"SERVLET_PACKAGE","radian.tcmis.swa.server6.client.servlets."},
             {"SERVLET_PACKAGE","radian.tcmis.server7.client.swa.servlets."},
             {"APP_NAME","SOUTHWEST Airlines"},
             {"SHOW_LOGON_FRAME","No"},        //10-29-01 whether to show the logon frame or not
             {"USE_GLOBAL_PASSWORD","No"},
             {"GLOBAL_PASSWORD","t"},
             {"LOG_DIR","/temp/logs/swa"},
             {"VERSION","1.8.05"},
             {"DOWNLOAD_DIR",""},
             {"EXEC_FILE","tcmISRun.exe"},
             {"CONFIG_FILE","tcmis.cfg"},
             {"DEBUG_FILE","cmisapp.deb"},
             //{"START_CLASS","radian.tcmis.swa.client.client.gui.SWACmisApp"},
             {"START_CLASS","radian.tcmis.client.client.swa.gui.SWACmisApp"},
             {"DEBUG","false"},
             {"CLIENT_INITIALS","SWA"},
             {"MSDS_URL","/tcmIS/swa/ViewMsds?"},
             {"WASTE_URL","/tcmIS/swa/wasteprofile?"},
             {"PRINT_SCREEN_URL","/tcmIS/swa/printscreen?"},
             {"WORK_AREA_NAME","Work Area"},
             {"WORK_AREA_NAME_PLURAL","Work Areas"}
     };

     public SWAClientResourceBundle(){
            super();
            addHash(cHash);
     }

}