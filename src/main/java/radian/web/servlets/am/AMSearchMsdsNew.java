package radian.web.servlets.am;

import radian.tcmis.server7.share.helpers.ServerResourceBundle;

public class AMSearchMsdsNew  extends radian.web.servlets.share.SearchMsdsNew
{
   public String getClient()
   {
      //System.out.println("\n\n============ AMsearchmsds check it is here....");
      return "AM";
   }
   protected ServerResourceBundle getBundle()
   {
      return (ServerResourceBundle) new AMServerResourceBundle();
   }
}