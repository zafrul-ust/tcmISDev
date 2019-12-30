package radian.web.servlets.pepsi;

import radian.tcmis.server7.share.helpers.ServerResourceBundle;

public class PepsiSearchMsdsNew  extends radian.web.servlets.share.SearchMsdsNew
{
   public String getClient()
   {
      //System.out.println("\n\n============ Pepsisearchmsds check it is here....");
      return "Pepsi";
   }
   protected ServerResourceBundle getBundle()
   {
      return (ServerResourceBundle) new PepsiServerResourceBundle();
   }
}