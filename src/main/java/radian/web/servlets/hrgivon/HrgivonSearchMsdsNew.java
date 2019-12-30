package radian.web.servlets.hrgivon;

import radian.tcmis.server7.share.helpers.ServerResourceBundle;

public class HrgivonSearchMsdsNew  extends radian.web.servlets.share.SearchMsdsNew
{
   public String getClient()
   {
      //System.out.println("\n\n============ Hrgivonsearchmsds check it is here....");
      return "Hrgivon";
   }
   protected ServerResourceBundle getBundle()
   {
      return (ServerResourceBundle) new HrgivonServerResourceBundle();
   }
}