package radian.web.servlets.swa;

import radian.tcmis.server7.share.helpers.ServerResourceBundle;
/*
import radian.tcmis.swa.server7.client.dbObjs.SWATcmISDBModel;
import radian.tcmis.swa.server7.share.dbObjs.*;
import radian.tcmis.swa.both1.helpers.BothHelpObjs;
import java.util.*;
import radian.tcmis.swa.server7.share.helpers.*;  */

public class SWASearchMsdsNew  extends radian.web.servlets.share.SearchMsdsNew
{
   public String getClient()
   {
      //System.out.println("\n\n============ SWAsearchmsds check it is here....");
      return "SWA";
   }
   protected ServerResourceBundle getBundle()
   {
      return (ServerResourceBundle) new SWAServerResourceBundle();
   }
}