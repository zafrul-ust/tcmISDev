package radian.web.servlets.avcorp;

import radian.tcmis.server7.share.helpers.ServerResourceBundle;

public class AvcorpBuildSearchPageNew  extends radian.web.servlets.share.BuildSearchPageNew
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