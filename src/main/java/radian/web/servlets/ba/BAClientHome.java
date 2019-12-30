package radian.web.servlets.ba;

import radian.tcmis.server7.share.helpers.ServerResourceBundle;

public class BAClientHome extends radian.web.servlets.share.ClientHome
{
   public String getClient()
   {
      return "BA";
   }
   protected ServerResourceBundle getBundle()
   {
      return (ServerResourceBundle) new BAServerResourceBundle();
   }
}