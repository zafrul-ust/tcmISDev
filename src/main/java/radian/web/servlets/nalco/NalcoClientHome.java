package radian.web.servlets.nalco;

import radian.tcmis.server7.share.helpers.ServerResourceBundle;

public class NalcoClientHome extends radian.web.servlets.share.ClientHome
{
   public String getClient()
   {
      return "Nalco";
   }
   protected ServerResourceBundle getBundle()
   {
      return (ServerResourceBundle) new NalcoServerResourceBundle();
   }
}