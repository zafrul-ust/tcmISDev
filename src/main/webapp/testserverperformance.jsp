<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
        "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<title>testing testing... </title>
<body>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%
java.util.Date startDate = new java.util.Date();
for(int i=0;i<1000000;i++) {
  //to be or not to be, that is the question....
}
java.util.Date endDate = new java.util.Date();
long timeSpan = endDate.getTime() - startDate.getTime();
com.tcmis.common.admin.process.MailProcess.sendEmail("deverror@haastcm.com", "", "foo@haastcm.com", "testing server performance", "It took "+timeSpan+" milliseconds from "+request.getRemoteHost()+" on "+java.net.InetAddress.getLocalHost()+"");
%>
It took <%=timeSpan %> milliseconds from <%= request.getRemoteHost() %> on <%= java.net.InetAddress.getLocalHost() %>
</body>
</html>
