package radian.web.servlets.imco;

import radian.tcmis.server7.share.helpers.ServerResourceBundle;

public class IMCOBuildSearchPageNew  extends radian.web.servlets.share.BuildSearchPageNew
{
   public String getClient()
   {
      return "IMCO";
   }
   protected ServerResourceBundle getBundle()
   {
      return (ServerResourceBundle) new IMCOServerResourceBundle();
   }
}