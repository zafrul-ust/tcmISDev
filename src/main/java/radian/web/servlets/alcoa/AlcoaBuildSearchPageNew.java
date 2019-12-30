package radian.web.servlets.alcoa;

import radian.tcmis.server7.share.helpers.ServerResourceBundle;

public class AlcoaBuildSearchPageNew  extends radian.web.servlets.share.BuildSearchPageNew
{
   public String getClient()
   {
      return "Alcoa";
   }
   protected ServerResourceBundle getBundle()
   {
      return (ServerResourceBundle) new AlcoaServerResourceBundle();
   }
}