package radian.web.servlets.drs;

import radian.tcmis.server7.share.helpers.ServerResourceBundle;
/*
import radian.tcmis.drs.server7.client.dbObjs.SWATcmISDBModel;
import radian.tcmis.drs.server7.share.dbObjs.*;
import radian.tcmis.drs.both1.helpers.BothHelpObjs;
import java.util.*;
import radian.tcmis.drs.server7.share.helpers.*;  */

public class DRSSearchMsdsNew  extends radian.web.servlets.share.SearchMsdsNew
{
   public String getClient()
   {
      //System.out.println("\n\n============ DRSsearchmsds check it is here....");
      return "DRS";
   }
   protected ServerResourceBundle getBundle()
   {
      return (ServerResourceBundle) new DRSServerResourceBundle();
   }
}