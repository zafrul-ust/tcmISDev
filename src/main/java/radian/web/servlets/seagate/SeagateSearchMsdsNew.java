package radian.web.servlets.seagate;

import radian.tcmis.server7.share.helpers.ServerResourceBundle;
/*
import radian.tcmis.seagate.server7.client.dbObjs.SWATcmISDBModel;
import radian.tcmis.seagate.server7.share.dbObjs.*;
import radian.tcmis.seagate.both1.helpers.BothHelpObjs;
import java.util.*;
import radian.tcmis.seagate.server7.share.helpers.*;  */

public class SeagateSearchMsdsNew  extends radian.web.servlets.share.SearchMsdsNew
{
   public String getClient()
   {
      //System.out.println("\n\n============ Seagatesearchmsds check it is here....");
      return "Seagate";
   }
   protected ServerResourceBundle getBundle()
   {
      return (ServerResourceBundle) new SeagateServerResourceBundle();
   }
}