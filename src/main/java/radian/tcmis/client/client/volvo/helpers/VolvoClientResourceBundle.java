package radian.tcmis.client.client.volvo.helpers;

import radian.tcmis.client.share.helpers.ClientResourceBundle;

public class VolvoClientResourceBundle  extends ClientResourceBundle {

     static final Object[][] cHash = {
             {"SERVLET_PACKAGE","radian.tcmis.server7.client.volvo.servlets."},
             {"APP_NAME","VOLVO"},
             {"SHOW_LOGON_FRAME","No"},        //10-29-01 whether to show the logon frame or not
             {"USE_GLOBAL_PASSWORD","No"},
             {"GLOBAL_PASSWORD","t"},
             {"LOG_DIR","/temp/logs/volvo"},
             {"VERSION","1.8.05"},
             {"DOWNLOAD_DIR",""},
             {"EXEC_FILE","tcmISRun.exe"},
             {"CONFIG_FILE","tcmis.cfg"},
             {"DEBUG_FILE","cmisapp.deb"},
             {"START_CLASS","radian.tcmis.client.client.volvo.gui.VolvoCmisApp"},
             {"DEBUG","false"},
             {"CLIENT_INITIALS","Volvo"},
             {"MSDS_URL","/tcmIS/volvo/ViewMsds?"},
             {"WASTE_URL","/tcmIS/volvo/wasteprofile?"},
             {"PRINT_SCREEN_URL","/tcmIS/volvo/printscreen?"},
             {"WORK_AREA_NAME","Work Area"},
             {"WORK_AREA_NAME_PLURAL","Work Areas"}
     };

     public VolvoClientResourceBundle(){
            super();
            addHash(cHash);
     }

}