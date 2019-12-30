package radian.web.servlets.volvo;

import radian.tcmis.server7.share.helpers.ServerResourceBundle;

public class VolvoBuildSearchPageNew  extends radian.web.servlets.share.BuildSearchPageNew
{
   public String getClient()
   {
      return "Volvo";
   }
   protected ServerResourceBundle getBundle()
   {
      return (ServerResourceBundle) new VolvoServerResourceBundle();
   }
}