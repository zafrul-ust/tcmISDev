package radian.web.servlets.imco;

import radian.tcmis.server7.share.helpers.ServerResourceBundle;

public class IMCOClientHome extends radian.web.servlets.share.ClientHome
{
   public String getClient()
   {
      return "IMCO";
   }
   protected ServerResourceBundle getBundle()
   {
      return (ServerResourceBundle) new IMCOServerResourceBundle();
   }
}