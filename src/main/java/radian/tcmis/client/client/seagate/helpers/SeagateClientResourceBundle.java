package radian.tcmis.client.client.seagate.helpers;

import radian.tcmis.client.share.helpers.ClientResourceBundle;

public class SeagateClientResourceBundle  extends ClientResourceBundle {

     static final Object[][] cHash = {
             {"SERVLET_PACKAGE","radian.tcmis.server7.client.seagate.servlets."},
             {"APP_NAME","Seagate"},
             //it turn out that I don't need this right now {"SHOW_LOGON_FRAME","Yes"},        //10-29-01 whether to show the logon frame or not
             {"USE_GLOBAL_PASSWORD","No"},
             {"GLOBAL_PASSWORD","t"},
             {"LOG_DIR","/temp/logs/seagate"},
             {"VERSION","2.0.01a"},
             {"DOWNLOAD_DIR",""},
             {"EXEC_FILE","tcmISRun.exe"},
             {"CONFIG_FILE","tcmis.cfg"},
             {"DEBUG_FILE","cmisapp.deb"},
             {"START_CLASS","radian.tcmis.client.client.seagate.gui.SeagateCmisApp"},
             {"DEBUG","false"},
             {"CLIENT_INITIALS","Seagate"},
             {"MSDS_URL","/tcmIS/seagate/ViewMsds?"},
             {"WASTE_URL","/tcmIS/seagate/wasteprofile?"},
             {"PRINT_SCREEN_URL","/tcmIS/seagate/printscreen?"},
             {"PRINT_WASTE_LABEL_URL","/tcmIS/seagate/printwastelabel.do?"},
             {"WORK_AREA_NAME","Work Area"},
             {"WORK_AREA_NAME_PLURAL","Work Areas"}
     };

     public SeagateClientResourceBundle(){
            super();
            addHash(cHash);
     }

}