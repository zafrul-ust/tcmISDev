package radian.web.servlets.aeropia;

import radian.tcmis.server7.share.helpers.ServerResourceBundle;

public class AeropiaSearchMsdsNew  extends radian.web.servlets.share.SearchMsdsNew
{
   public String getClient()
   {
      //System.out.println("\n\n============ Aeropiasearchmsds check it is here....");
      return "Aeropia";
   }
   protected ServerResourceBundle getBundle()
   {
      return (ServerResourceBundle) new AeropiaServerResourceBundle();
   }
}