package radian.tcmis.client.client.alcoa.helpers;

import radian.tcmis.client.share.helpers.ClientResourceBundle;

public class AlcoaClientResourceBundle  extends ClientResourceBundle {

     static final Object[][] cHash = {
             {"SERVLET_PACKAGE","radian.tcmis.server7.client.alcoa.servlets."},
             {"APP_NAME","Alcoa Automotive GmbH"},
             {"SHOW_LOGON_FRAME","No"},        //10-29-01 whether to show the logon frame or not
             {"USE_GLOBAL_PASSWORD","No"},
             {"GLOBAL_PASSWORD","t"},
             {"LOG_DIR","/temp/logs/alcoa"},
             {"VERSION","1.8.05"},
             {"DOWNLOAD_DIR",""},
             {"EXEC_FILE","tcmISRun.exe"},
             {"CONFIG_FILE","tcmis.cfg"},
             {"DEBUG_FILE","cmisapp.deb"},
             {"START_CLASS","radian.tcmis.client.client.alcoa.gui.AlcoaCmisApp"},
             {"DEBUG","false"},
             {"CLIENT_INITIALS","Alcoa"},
             {"MSDS_URL","/tcmIS/alcoa/ViewMsds?"},
             {"WASTE_URL","/tcmIS/alcoa/wasteprofile?"},
             {"PRINT_SCREEN_URL","/tcmIS/alcoa/printscreen?"},
             {"WORK_AREA_NAME","Work Area"},
             {"WORK_AREA_NAME_PLURAL","Work Areas"}
     };

     public AlcoaClientResourceBundle(){
            super();
            addHash(cHash);
     }

}