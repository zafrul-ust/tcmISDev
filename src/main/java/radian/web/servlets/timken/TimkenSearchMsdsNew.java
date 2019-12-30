package radian.web.servlets.timken;

import radian.tcmis.server7.share.helpers.ServerResourceBundle;

public class TimkenSearchMsdsNew  extends radian.web.servlets.share.SearchMsdsNew
{
   public String getClient()
   {
      //System.out.println("\n\n============ Timkensearchmsds check it is here....");
      return "Timken";
   }
   protected ServerResourceBundle getBundle()
   {
      return (ServerResourceBundle) new TimkenServerResourceBundle();
   }
}