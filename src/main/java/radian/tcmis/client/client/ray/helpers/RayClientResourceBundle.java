package radian.tcmis.client.client.ray.helpers;

import radian.tcmis.client.share.helpers.ClientResourceBundle;

public class RayClientResourceBundle  extends ClientResourceBundle {

     static final Object[][] cHash = {
             {"SERVLET_PACKAGE","radian.tcmis.server7.client.ray.servlets."},
             {"APP_NAME","Raytheon"},
             {"SHOW_LOGON_FRAME","No"},        //10-29-01 whether to show the logon frame or not
             {"USE_GLOBAL_PASSWORD","No"},
             {"GLOBAL_PASSWORD","t"},
             {"LOG_DIR","/temp/logs/ray"},
             {"VERSION","1.8.05"},
             {"DOWNLOAD_DIR",""},
             {"EXEC_FILE","tcmISRun.exe"},
             {"CONFIG_FILE","tcmis.cfg"},
             {"DEBUG_FILE","cmisapp.deb"},
             {"START_CLASS","radian.tcmis.client.client.ray.gui.RayCmisApp"},
             {"DEBUG","false"},
             {"CLIENT_INITIALS","Ray"},
             {"MSDS_URL","/tcmIS/ray/ViewMsds?"},
             {"WASTE_URL","/tcmIS/ray/wasteprofile?"},
             {"PRINT_SCREEN_URL","/tcmIS/ray/printscreen?"},
             {"PRINT_WASTE_LABEL_URL","/tcmIS/ray/printwastelabel.do?"},
             {"WORK_AREA_NAME","Work Area"},
             {"WORK_AREA_NAME_PLURAL","Work Areas"}
     };

     public RayClientResourceBundle(){
            super();
            addHash(cHash);
     }

}