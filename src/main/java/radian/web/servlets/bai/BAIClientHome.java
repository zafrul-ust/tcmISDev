package radian.web.servlets.bai;

import radian.tcmis.server7.share.helpers.ServerResourceBundle;

public class BAIClientHome extends radian.web.servlets.share.ClientHome
{
   public String getClient()
   {
      return "BAI";
   }
   protected ServerResourceBundle getBundle()
   {
      return (ServerResourceBundle) new BAIServerResourceBundle();
   }
}