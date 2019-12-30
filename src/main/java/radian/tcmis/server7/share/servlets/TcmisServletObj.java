package radian.tcmis.server7.share.servlets;

import java.io.IOException;
import java.util.Hashtable;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import radian.tcmis.server7.share.dbObjs.TcmISDBModel;
import radian.tcmis.server7.share.helpers.HelpObjs;
import radian.tcmis.server7.share.helpers.ServerResourceBundle;


public abstract class TcmisServletObj {

  TcmISDBModel db = null;
  TcmisOutputStreamServer out = null;
  HttpServletRequest reqInfo = null;
  HttpServletResponse response = null;
  String ip = null;
  String token = null;
  Object myRecvObj = null;
  ServerResourceBundle bundle=null;
  //private static org.apache.log4j.Logger reoprtlog = org.apache.log4j.Logger.getLogger("tcmis.web");
  public TcmisServletObj(){

  }

  public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	reqInfo = request;
	//trong 8-7
	this.response = response;
	resetAllVars();
	token = null;
	out = null;
	ip = null;
	myRecvObj = null;
	//reoprtlog.info("Free Memory     "+Runtime.getRuntime().freeMemory()/1000000+" ");
	TcmisInputStreamServer rq = new TcmisInputStreamServer(request,db);

	Hashtable passedObj = rq.readData();
	try {
		ip  = (String) rq.getParameterObject("CLIENT_IP");
		token  = (String) rq.getParameterObject("TOKEN");
	} catch (Exception e) {
		e.printStackTrace();
		HelpObjs.monitor(1,"Error object("+this.getClass().getName()+"): " + e.getMessage(),null);
	}

	HttpInput httpI = new HttpInput(ip,passedObj,db);
	response.setContentType("text/plain");
	out = new TcmisOutputStreamServer (response);
	out.setToken(token);

	if (getBundle().getString("DEBUG").equals("true")) HelpObjs.monitor(1,"Cgi User Profile reveived the data:",getBundle());

	if (!httpI.isAuthenticated()) {
	   out.printDenied();
	   out.close();
	   return;
	}

	// the whole object sent
	myRecvObj = httpI.getSentObj();

	try {
	   getResult();
	} catch (Exception e){
	   e.printStackTrace();
	   HelpObjs.monitor(1,"Error on:getResult" + e.getMessage()+e.getLocalizedMessage()+e.toString(),null);
	   out.close();
	   return;
	}

	try {
	  //System.out.println("---- sending response to client");
	  print(out);
	  out.close();
	} catch (Exception e) {
	  e.printStackTrace();
	}
  }

  //Get Servlet information
  public String getServletInfo(){
	return "radian.tcmis.server7.share.servlets.TcmisServlet Information";
  }

  public String getClientIP(){
	  return this.ip;
  }
  public String getToken() {
	return this.token;
  }
  public String getServerName(){
	  return reqInfo.getServerName();
  }

  public int getServerPort(){
	  return reqInfo.getServerPort();
  }

  public String getServletURI(){
	  return reqInfo.getRequestURI();
  }

  protected abstract void print(TcmisOutputStreamServer out);
  protected abstract void getResult() throws Exception ;
  protected abstract void resetAllVars();
  protected ServerResourceBundle getBundle(){ return bundle; }
  protected TcmISDBModel getDBModel(String client) { return db; }

}
