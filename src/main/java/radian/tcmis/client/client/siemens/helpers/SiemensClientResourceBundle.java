package radian.tcmis.client.client.siemens.helpers;

import radian.tcmis.client.share.helpers.ClientResourceBundle;

public class SiemensClientResourceBundle  extends ClientResourceBundle {

     static final Object[][] cHash = {
             {"SERVLET_PACKAGE","radian.tcmis.server7.client.siemens.servlets."},
             {"APP_NAME","Siemens AG"},
             {"SHOW_LOGON_FRAME","No"},        //10-29-01 whether to show the logon frame or not
             {"USE_GLOBAL_PASSWORD","No"},
             {"GLOBAL_PASSWORD","t"},
             {"LOG_DIR","/temp/logs/siemens"},
             {"VERSION","1.8.05"},
             {"DOWNLOAD_DIR",""},
             {"EXEC_FILE","tcmISRun.exe"},
             {"CONFIG_FILE","tcmis.cfg"},
             {"DEBUG_FILE","cmisapp.deb"},
             {"START_CLASS","radian.tcmis.client.client.siemens.gui.SiemensCmisApp"},
             {"DEBUG","false"},
             {"CLIENT_INITIALS","Siemens"},
             {"MSDS_URL","/tcmIS/siemens/ViewMsds?"},
             {"WASTE_URL","/tcmIS/siemens/wasteprofile?"},
             {"PRINT_SCREEN_URL","/tcmIS/siemens/printscreen?"},
             {"WORK_AREA_NAME","Work Area"},
             {"WORK_AREA_NAME_PLURAL","Work Areas"}
     };

     public SiemensClientResourceBundle(){
            super();
            addHash(cHash);
     }

}