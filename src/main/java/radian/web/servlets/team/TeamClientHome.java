package radian.web.servlets.team;

import radian.tcmis.server7.share.helpers.ServerResourceBundle;

public class TeamClientHome extends radian.web.servlets.share.ClientHome
{
   public String getClient()
   {
      return "Team";
   }
   protected ServerResourceBundle getBundle()
   {
      return (ServerResourceBundle) new TeamServerResourceBundle();
   }
}