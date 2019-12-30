package radian.web.servlets.tai;

import radian.tcmis.server7.share.helpers.ServerResourceBundle;

public class TAISearchMsdsNew  extends radian.web.servlets.share.SearchMsdsNew
{
   public String getClient()
   {
      //System.out.println("\n\n============ TAIsearchmsds check it is here....");
      return "TAI";
   }
   protected ServerResourceBundle getBundle()
   {
      return (ServerResourceBundle) new TAIServerResourceBundle();
   }
}