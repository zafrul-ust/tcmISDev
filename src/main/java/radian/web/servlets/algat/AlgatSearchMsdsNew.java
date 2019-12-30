package radian.web.servlets.algat;

import radian.tcmis.server7.share.helpers.ServerResourceBundle;

public class AlgatSearchMsdsNew  extends radian.web.servlets.share.SearchMsdsNew
{
   public String getClient()
   {
      //System.out.println("\n\n============ Algatsearchmsds check it is here....");
      return "Algat";
   }
   protected ServerResourceBundle getBundle()
   {
      return (ServerResourceBundle) new AlgatServerResourceBundle();
   }
}