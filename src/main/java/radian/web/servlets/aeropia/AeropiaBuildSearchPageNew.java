package radian.web.servlets.aeropia;

import radian.tcmis.server7.share.helpers.ServerResourceBundle;

public class AeropiaBuildSearchPageNew  extends radian.web.servlets.share.BuildSearchPageNew
{
   public String getClient()
   {
      return "Aeropia";
   }
   protected ServerResourceBundle getBundle()
   {
      return (ServerResourceBundle) new AeropiaServerResourceBundle();
   }
}