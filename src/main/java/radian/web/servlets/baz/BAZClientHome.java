package radian.web.servlets.baz;

import radian.tcmis.server7.share.helpers.ServerResourceBundle;

public class BAZClientHome extends radian.web.servlets.share.ClientHome
{
   public String getClient()
   {
      return "BAZ";
   }
   protected ServerResourceBundle getBundle()
   {
      return (ServerResourceBundle) new BAZServerResourceBundle();
   }
}