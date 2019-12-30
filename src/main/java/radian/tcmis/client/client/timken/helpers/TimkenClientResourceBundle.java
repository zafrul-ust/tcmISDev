package radian.tcmis.client.client.timken.helpers;

import radian.tcmis.client.share.helpers.ClientResourceBundle;

public class TimkenClientResourceBundle  extends ClientResourceBundle {

     static final Object[][] cHash = {
             {"SERVLET_PACKAGE","radian.tcmis.server7.client.timken.servlets."},
             {"APP_NAME","Timken Corporation"},
             {"SHOW_LOGON_FRAME","No"},        //10-29-01 whether to show the logon frame or not
             {"USE_GLOBAL_PASSWORD","No"},
             {"GLOBAL_PASSWORD","t"},
             {"LOG_DIR","/temp/logs/timken"},
             {"VERSION","1.8.05"},
             {"DOWNLOAD_DIR",""},
             {"EXEC_FILE","tcmISRun.exe"},
             {"CONFIG_FILE","tcmis.cfg"},
             {"DEBUG_FILE","cmisapp.deb"},
             {"START_CLASS","radian.tcmis.client.client.timken.gui.TimkenCmisApp"},
             {"DEBUG","false"},
             {"CLIENT_INITIALS","Timken"},
             {"MSDS_URL","/tcmIS/timken/ViewMsds?"},
             {"WASTE_URL","/tcmIS/timken/wasteprofile?"},
             {"PRINT_SCREEN_URL","/tcmIS/timken/printscreen?"},
             {"WORK_AREA_NAME","Work Area"},
             {"WORK_AREA_NAME_PLURAL","Work Areas"}
     };

     public TimkenClientResourceBundle(){
            super();
            addHash(cHash);
     }

}