package radian.web.servlets.verasun;

import radian.tcmis.server7.share.helpers.ServerResourceBundle;

public class VerasunClientHome extends radian.web.servlets.share.ClientHome
{
   public String getClient()
   {
      return "Verasun";
   }
   protected ServerResourceBundle getBundle()
   {
      return (ServerResourceBundle) new VerasunServerResourceBundle();
   }
}