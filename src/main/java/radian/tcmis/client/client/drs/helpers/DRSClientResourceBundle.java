package radian.tcmis.client.client.drs.helpers;

import radian.tcmis.client.share.helpers.ClientResourceBundle;

public class DRSClientResourceBundle  extends ClientResourceBundle {

     static final Object[][] cHash = {
             {"SERVLET_PACKAGE","radian.tcmis.server7.client.drs.servlets."},
             {"APP_NAME","DRS"},
             {"SHOW_LOGON_FRAME","No"},        //10-29-01 whether to show the logon frame or not
             {"USE_GLOBAL_PASSWORD","No"},
             {"GLOBAL_PASSWORD","t"},
             {"LOG_DIR","/temp/logs/drs"},
             {"VERSION","1.8.01"},
             {"DOWNLOAD_DIR",""},     //trong 7-28
             {"EXEC_FILE","tcmISRun.exe"},
             {"CONFIG_FILE","tcmis.cfg"},
             {"DEBUG_FILE","DRScmisapp.deb"},
             {"START_CLASS","radian.tcmis.client.client.drs.gui.DRSCmisApp"},
             {"DEBUG","false"},
             {"CLIENT_INITIALS","DRS"},
             {"MSDS_URL","/tcmIS/drs/ViewMsds?"},
             {"WASTE_URL","/tcmIS/drs/wasteprofile?"},
             {"PRINT_SCREEN_URL","/tcmIS/drs/printscreen?"},
             {"PRINT_WASTE_LABEL_URL","/tcmIS/drs/printwastelabel.do?"},
             {"WORK_AREA_NAME","Work Area"},
             {"WORK_AREA_NAME_PLURAL","Work Areas"}
     };

     public DRSClientResourceBundle(){
            super();
            addHash(cHash);
     }

}