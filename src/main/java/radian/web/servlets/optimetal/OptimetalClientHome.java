package radian.web.servlets.optimetal;

import radian.tcmis.server7.share.helpers.ServerResourceBundle;

public class OptimetalClientHome extends radian.web.servlets.share.ClientHome
{
   public String getClient()
   {
      return "Optimetal";
   }
   protected ServerResourceBundle getBundle()
   {
      return (ServerResourceBundle) new OptimetalServerResourceBundle();
   }
}