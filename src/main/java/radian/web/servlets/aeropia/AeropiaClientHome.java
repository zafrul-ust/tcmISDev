package radian.web.servlets.aeropia;

import radian.tcmis.server7.share.helpers.ServerResourceBundle;

public class AeropiaClientHome extends radian.web.servlets.share.ClientHome
{
   public String getClient()
   {
      return "Aeropia";
   }
   protected ServerResourceBundle getBundle()
   {
      return (ServerResourceBundle) new AeropiaServerResourceBundle();
   }
}