package radian.tcmis.client.client.hrgivon.helpers;

import radian.tcmis.client.share.helpers.ClientResourceBundle;

public class HrgivonClientResourceBundle  extends ClientResourceBundle {

     static final Object[][] cHash = {
             {"SERVLET_PACKAGE","radian.tcmis.server7.client.hrgivon.servlets."},
             {"APP_NAME","H.R. Givon Ltd."},
             {"SHOW_LOGON_FRAME","No"},        //10-29-01 whether to show the logon frame or not
             {"USE_GLOBAL_PASSWORD","No"},
             {"GLOBAL_PASSWORD","t"},
             {"LOG_DIR","/temp/logs/hrgivon"},
             {"VERSION","1.8.05"},
             {"DOWNLOAD_DIR",""},
             {"EXEC_FILE","tcmISRun.exe"},
             {"CONFIG_FILE","tcmis.cfg"},
             {"DEBUG_FILE","cmisapp.deb"},
             {"START_CLASS","radian.tcmis.client.client.hrgivon.gui.HrgivonCmisApp"},
             {"DEBUG","false"},
             {"CLIENT_INITIALS","Hrgivon"},
             {"MSDS_URL","/tcmIS/hrgivon/ViewMsds?"},
             {"WASTE_URL","/tcmIS/hrgivon/wasteprofile?"},
             {"PRINT_SCREEN_URL","/tcmIS/hrgivon/printscreen?"},
             {"WORK_AREA_NAME","Work Area"},
             {"WORK_AREA_NAME_PLURAL","Work Areas"}
     };

     public HrgivonClientResourceBundle(){
            super();
            addHash(cHash);
     }

}