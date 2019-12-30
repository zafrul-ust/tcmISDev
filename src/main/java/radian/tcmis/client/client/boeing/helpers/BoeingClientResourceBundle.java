package radian.tcmis.client.client.boeing.helpers;

import radian.tcmis.client.share.helpers.ClientResourceBundle;

public class BoeingClientResourceBundle  extends ClientResourceBundle {

     static final Object[][] cHash = {
             {"SERVLET_PACKAGE","radian.tcmis.server7.client.boeing.servlets."},
             {"APP_NAME","United Launch Alliance"},
             {"SHOW_LOGON_FRAME","No"},        //10-29-01 whether to show the logon frame or not
             {"USE_GLOBAL_PASSWORD","No"},
             {"GLOBAL_PASSWORD","t"},
             {"LOG_DIR","/temp/logs/boeing"},
             {"VERSION","1.8.05"},
             {"DOWNLOAD_DIR",""},
             {"EXEC_FILE","tcmISRun.exe"},
             {"CONFIG_FILE","tcmis.cfg"},
             {"DEBUG_FILE","cmisapp.deb"},
             {"START_CLASS","radian.tcmis.client.client.boeing.gui.BoeingCmisApp"},
             {"DEBUG","false"},
             {"CLIENT_INITIALS","Boeing"},
             {"MSDS_URL","/tcmIS/ula/ViewMsds?"},
             {"WASTE_URL","/tcmIS/ula/wasteprofile?"},
             {"PRINT_SCREEN_URL","/tcmIS/ula/printscreen?"},
             {"PRINT_WASTE_LABEL_URL","/tcmIS/ula/printwastelabel.do?"},
             {"WORK_AREA_NAME","Work Area"},
             {"WORK_AREA_NAME_PLURAL","Work Areas"}
     };

     public BoeingClientResourceBundle(){
            super();
            addHash(cHash);
     }

}