package radian.tcmis.client.client.haargaz.helpers;

import radian.tcmis.client.share.helpers.ClientResourceBundle;

public class HaargazClientResourceBundle  extends ClientResourceBundle {

     static final Object[][] cHash = {
             {"SERVLET_PACKAGE","radian.tcmis.server7.client.haargaz.servlets."},
             {"APP_NAME","Haargaz Technopach Metal Industries Ltd."},
             {"SHOW_LOGON_FRAME","No"},        //10-29-01 whether to show the logon frame or not
             {"USE_GLOBAL_PASSWORD","No"},
             {"GLOBAL_PASSWORD","t"},
             {"LOG_DIR","/temp/logs/haargaz"},
             {"VERSION","1.8.05"},
             {"DOWNLOAD_DIR",""},
             {"EXEC_FILE","tcmISRun.exe"},
             {"CONFIG_FILE","tcmis.cfg"},
             {"DEBUG_FILE","cmisapp.deb"},
             {"START_CLASS","radian.tcmis.client.client.haargaz.gui.HaargazCmisApp"},
             {"DEBUG","false"},
             {"CLIENT_INITIALS","Haargaz"},
             {"MSDS_URL","/tcmIS/haargaz/ViewMsds?"},
             {"WASTE_URL","/tcmIS/haargaz/wasteprofile?"},
             {"PRINT_SCREEN_URL","/tcmIS/haargaz/printscreen?"},
             {"PRINT_WASTE_LABEL_URL","/tcmIS/haargaz/printwastelabel.do?"},
             {"WORK_AREA_NAME","Work Area"},
             {"WORK_AREA_NAME_PLURAL","Work Areas"}
     };

     public HaargazClientResourceBundle(){
            super();
            addHash(cHash);
     }

}