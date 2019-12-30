package radian.web.servlets.l3;

import radian.tcmis.server7.share.helpers.ServerResourceBundle;

public class L3ClientHome extends radian.web.servlets.share.ClientHome
{
   public String getClient()
   {
      return "L3";
   }
   protected ServerResourceBundle getBundle()
   {
      return (ServerResourceBundle) new L3ServerResourceBundle();
   }
}