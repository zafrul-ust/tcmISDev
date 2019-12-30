package radian.web.servlets.nalco;

import radian.tcmis.server7.share.helpers.ServerResourceBundle;

public class NalcoBuildSearchPageNew  extends radian.web.servlets.share.BuildSearchPageNew
{
   public String getClient()
   {
      return "Nalco";
   }
   protected ServerResourceBundle getBundle()
   {
      return (ServerResourceBundle) new NalcoServerResourceBundle();
   }
}