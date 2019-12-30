package radian.tcmis.client.client.am.helpers;

import radian.tcmis.client.share.helpers.ClientResourceBundle;

public class AMClientResourceBundle  extends ClientResourceBundle {

     static final Object[][] cHash = {
             {"SERVLET_PACKAGE","radian.tcmis.server7.client.am.servlets."},
             {"APP_NAME","ArvinMeritor"},
             {"SHOW_LOGON_FRAME","No"},        //10-29-01 whether to show the logon frame or not
             {"USE_GLOBAL_PASSWORD","No"},
             {"GLOBAL_PASSWORD","t"},
             {"LOG_DIR","/temp/logs/am"},
             {"VERSION","1.8.05"},
             {"DOWNLOAD_DIR",""},
             {"EXEC_FILE","tcmISRun.exe"},
             {"CONFIG_FILE","tcmis.cfg"},
             {"DEBUG_FILE","cmisapp.deb"},
             {"START_CLASS","radian.tcmis.client.client.am.gui.AMCmisApp"},
             {"DEBUG","false"},
             {"CLIENT_INITIALS","AM"},
             {"MSDS_URL","/tcmIS/am/ViewMsds?"},
             {"WASTE_URL","/tcmIS/am/wasteprofile?"},
             {"PRINT_SCREEN_URL","/tcmIS/am/printscreen?"},
             {"WORK_AREA_NAME","Work Area"},
             {"WORK_AREA_NAME_PLURAL","Work Areas"}
     };

     public AMClientResourceBundle(){
            super();
            addHash(cHash);
     }

}