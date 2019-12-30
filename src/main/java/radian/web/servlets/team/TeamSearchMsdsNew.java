package radian.web.servlets.team;

import radian.tcmis.server7.share.helpers.ServerResourceBundle;

public class TeamSearchMsdsNew  extends radian.web.servlets.share.SearchMsdsNew
{
   public String getClient()
   {
      //System.out.println("\n\n============ Teamsearchmsds check it is here....");
      return "Team";
   }
   protected ServerResourceBundle getBundle()
   {
      return (ServerResourceBundle) new TeamServerResourceBundle();
   }
}