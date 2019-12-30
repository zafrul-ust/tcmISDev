package radian.web.servlets.cal;

import radian.tcmis.server7.share.helpers.ServerResourceBundle;

public class CALSearchMsdsNew  extends radian.web.servlets.share.SearchMsdsNew
{
   public String getClient()
   {
      //System.out.println("\n\n============ CALsearchmsds check it is here....");
      return "CAL";
   }
   protected ServerResourceBundle getBundle()
   {
      return (ServerResourceBundle) new CALServerResourceBundle();
   }
}