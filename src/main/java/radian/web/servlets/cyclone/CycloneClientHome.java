package radian.web.servlets.cyclone;

import radian.tcmis.server7.share.helpers.ServerResourceBundle;

public class CycloneClientHome extends radian.web.servlets.share.ClientHome
{
   public String getClient()
   {
      return "Cyclone";
   }
   protected ServerResourceBundle getBundle()
   {
      return (ServerResourceBundle) new CycloneServerResourceBundle();
   }
}