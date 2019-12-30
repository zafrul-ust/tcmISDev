package radian.web.servlets.orlite;

import radian.tcmis.server7.share.helpers.ServerResourceBundle;

public class OrliteClientHome extends radian.web.servlets.share.ClientHome
{
   public String getClient()
   {
      return "Orlite";
   }
   protected ServerResourceBundle getBundle()
   {
      return (ServerResourceBundle) new OrliteServerResourceBundle();
   }
}