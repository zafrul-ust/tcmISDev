package radian.web.servlets.ael;

import radian.tcmis.server7.share.helpers.ServerResourceBundle;

public class AeroEcoClientHome extends radian.web.servlets.share.ClientHome
{
   public String getClient()
   {
      return "AeroEco";
   }
   protected ServerResourceBundle getBundle()
   {
      return (ServerResourceBundle) new AeroEcoServerResourceBundle();
   }
}