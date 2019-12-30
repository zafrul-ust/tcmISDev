package radian.tcmis.client.client.sd.helpers;

import radian.tcmis.client.share.helpers.ClientResourceBundle;

public class SDClientResourceBundle  extends ClientResourceBundle {

     static final Object[][] cHash = {
             {"SERVLET_PACKAGE","radian.tcmis.server7.client.sd.servlets."},
             {"APP_NAME","Sauer Danfoss"},
             {"SHOW_LOGON_FRAME","No"},        //10-29-01 whether to show the logon frame or not
             {"USE_GLOBAL_PASSWORD","No"},
             {"GLOBAL_PASSWORD","t"},
             {"LOG_DIR","/temp/logs/sd"},
             {"VERSION","1.8.05"},
             {"DOWNLOAD_DIR",""},
             {"EXEC_FILE","tcmISRun.exe"},
             {"CONFIG_FILE","tcmis.cfg"},
             {"DEBUG_FILE","cmisapp.deb"},
             {"START_CLASS","radian.tcmis.client.client.sd.gui.SDCmisApp"},
             {"DEBUG","false"},
             {"CLIENT_INITIALS","SD"},
             {"MSDS_URL","/tcmIS/sd/ViewMsds?"},
             {"WASTE_URL","/tcmIS/sd/wasteprofile?"},
             {"PRINT_SCREEN_URL","/tcmIS/sd/printscreen?"},
             {"PRINT_WASTE_LABEL_URL","/tcmIS/sd/printwastelabel.do?"},
             {"WORK_AREA_NAME","Work Area"},
             {"WORK_AREA_NAME_PLURAL","Work Areas"}
     };

     public SDClientResourceBundle(){
            super();
            addHash(cHash);
     }

}