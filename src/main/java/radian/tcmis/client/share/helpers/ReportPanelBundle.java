package radian.tcmis.client.share.helpers;

import radian.tcmis.client.share.gui.*;

public class ReportPanelBundle {
     protected CmisApp cmis;
     public ReportPanelBundle(CmisApp cmis){
       this.cmis = cmis;
     }
     public ReportInputPanel[] getReports() {
        int count = 0;
        if (cmis.formattedUsage) {
          count++;
        }
        if (cmis.formattedVOC) {
          count++;
        }
        if (cmis.formattedMSDS) {
          count++;
        }
        if (cmis.adHocUsage) {
          count++;
        }
        if (cmis.materialMatrix) {
          count++;
        }
        if (cmis.wasteUser) {
          count++;
        }
        if (cmis.hourVocUsage) {
          count++;
        }

        ReportInputPanel[] shareReports = new ReportInputPanel[count];
        int i = 0;
         if (cmis.formattedUsage) {
          shareReports[i++] = new ReportableUsageReport(cmis);
         }
         if (cmis.formattedVOC) {
          shareReports[i++] = new ReportableUsageReport(cmis,true);
         }
         if (cmis.formattedMSDS) {
          shareReports[i++] = new MSDSRevReport(cmis);
         }
         if (cmis.hourVocUsage) {
          shareReports[i++] = new AdHocVocHourly(cmis);
         }
         shareReports[i++] = new AdHocUsageReport(cmis);
         shareReports[i++] = new MaterialMatrix(cmis);
         if (cmis.wasteUser) {
          shareReports[i++] = new WasteAhocReport(cmis);
         }
        return shareReports;
     }
}



