package radian.web.servlets.ael;

import radian.tcmis.server7.share.helpers.ServerResourceBundle;

public class AeroEcoBuildSearchPageNew  extends radian.web.servlets.share.BuildSearchPageNew
{
   public String getClient()
   {
      return "AeroEco";
   }
   protected ServerResourceBundle getBundle()
   {
      return (ServerResourceBundle) new AeroEcoServerResourceBundle();
   }
}