package radian.web.servlets.kemfast;

import radian.tcmis.server7.share.helpers.ServerResourceBundle;

public class KemfastSearchMsdsNew  extends radian.web.servlets.share.SearchMsdsNew
{
   public String getClient()
   {
      //System.out.println("\n\n============ Kemfastsearchmsds check it is here....");
      return "Kemfast";
   }
   protected ServerResourceBundle getBundle()
   {
      return (ServerResourceBundle) new KemfastServerResourceBundle();
   }
}