package radian.web.servlets.volvo;

import radian.tcmis.server7.share.helpers.ServerResourceBundle;

public class VolvoClientHome extends radian.web.servlets.share.ClientHome
{
   public String getClient()
   {
      return "Volvo";
   }
   protected ServerResourceBundle getBundle()
   {
      return (ServerResourceBundle) new VolvoServerResourceBundle();
   }
}