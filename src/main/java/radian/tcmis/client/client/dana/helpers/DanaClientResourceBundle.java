package radian.tcmis.client.client.dana.helpers;

import radian.tcmis.client.share.helpers.ClientResourceBundle;

public class DanaClientResourceBundle  extends ClientResourceBundle {

     static final Object[][] cHash = {
             {"SERVLET_PACKAGE","radian.tcmis.server7.client.dana.servlets."},
             {"APP_NAME","Dana Corporation"},
             {"SHOW_LOGON_FRAME","No"},        //10-29-01 whether to show the logon frame or not
             {"USE_GLOBAL_PASSWORD","No"},
             {"GLOBAL_PASSWORD","t"},
             {"LOG_DIR","/temp/logs/dana"},
             {"VERSION","1.8.05"},
             {"DOWNLOAD_DIR",""},
             {"EXEC_FILE","tcmISRun.exe"},
             {"CONFIG_FILE","tcmis.cfg"},
             {"DEBUG_FILE","cmisapp.deb"},
             {"START_CLASS","radian.tcmis.client.client.dana.gui.DanaCmisApp"},
             {"DEBUG","false"},
             {"CLIENT_INITIALS","Dana"},
             {"MSDS_URL","/tcmIS/dana/ViewMsds?"},
             {"WASTE_URL","/tcmIS/dana/wasteprofile?"},
             {"PRINT_SCREEN_URL","/tcmIS/dana/printscreen?"},
             {"PRINT_WASTE_LABEL_URL","/tcmIS/dana/printwastelabel.do?"},
             {"WORK_AREA_NAME","Work Area"},
             {"WORK_AREA_NAME_PLURAL","Work Areas"}
     };

     public DanaClientResourceBundle(){
            super();
            addHash(cHash);
     }

}