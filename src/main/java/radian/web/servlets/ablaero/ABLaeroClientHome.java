package radian.web.servlets.ablaero;

import radian.tcmis.server7.share.helpers.ServerResourceBundle;

public class ABLaeroClientHome extends radian.web.servlets.share.ClientHome
{
   public String getClient()
   {
      return "ABLaero";
   }
   protected ServerResourceBundle getBundle()
   {
      return (ServerResourceBundle) new ABLaeroServerResourceBundle();
   }
}