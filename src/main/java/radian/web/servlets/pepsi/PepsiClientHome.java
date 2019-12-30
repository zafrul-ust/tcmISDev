package radian.web.servlets.pepsi;

import radian.tcmis.server7.share.helpers.ServerResourceBundle;

public class PepsiClientHome extends radian.web.servlets.share.ClientHome
{
   public String getClient()
   {
      return "Pepsi";
   }
   protected ServerResourceBundle getBundle()
   {
      return (ServerResourceBundle) new PepsiServerResourceBundle();
   }
}