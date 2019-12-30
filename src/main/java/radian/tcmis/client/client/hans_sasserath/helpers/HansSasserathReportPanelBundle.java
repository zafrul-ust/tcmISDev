package radian.tcmis.client.client.hans_sasserath.helpers;

import radian.tcmis.client.share.helpers.*;
import radian.tcmis.client.share.gui.*;

public class HansSasserathReportPanelBundle  extends ReportPanelBundle {


     public HansSasserathReportPanelBundle(CmisApp cmis){
            super(cmis);
     }
     public ReportInputPanel[] getReports() {
       ReportInputPanel[] shared = super.getReports();
       ReportInputPanel[] clientReports = {
       };
       ReportInputPanel[] rip = new ReportInputPanel[shared.length + clientReports.length];
       int q=0;
       for(int i=0;i<shared.length;i++) {
         rip[q++] = shared[i];
       }
       for(int i=0;i<clientReports.length;i++) {
         rip[q++] = clientReports[i];
       }
       return rip;
     }


}