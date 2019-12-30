package radian.web.servlets.avcorp;

import radian.tcmis.server7.share.helpers.ServerResourceBundle;

public class AvcorpClientHome extends radian.web.servlets.share.ClientHome
{
   public String getClient()
   {
      return "Avcorp";
   }
   protected ServerResourceBundle getBundle()
   {
      return (ServerResourceBundle) new AvcorpServerResourceBundle();
   }
}