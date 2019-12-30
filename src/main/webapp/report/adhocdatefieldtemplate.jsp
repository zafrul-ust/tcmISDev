<script type="text/javascript" src="/js/jquery/jquery-1.6.4.js"></script>

<script language="JavaScript" type="text/javascript">
<!--

    //add all the javascript messages here, this for internationalization of client side javascript messages
    var messagesListDateTemplate = new Array();
messagesListDateTemplate = {        
        pleaseselect:"<fmt:message key="errors.selecta"/>",
        select:"<fmt:message key="label.select"/>",
        pleaseentervalidnumofdays:"<fmt:message key="label.pleaseentervalidnumofdays"/>",
        pleaseentervaliddayofyear:"<fmt:message key="label.pleaseentervaliddayofyear"/>",
        begindaterequired:"<fmt:message key="label.begindaterequired"/>",
        enddaterequired:"<fmt:message key="label.enddaterequired"/>",
        numberofdaysrequired:"<fmt:message key="label.numberofdaysrequired"/>",
        dayofweekrequired:"<fmt:message key="label.dayofweekrequired"/>",
        dayofmonthrequired:"<fmt:message key="label.dayofmonthrequired"/>",
        dayofyearrequired:"<fmt:message key="label.dayofyearrequired"/>"
        };
    
    var pageIdForDate = '${pageId}';


function loadDateFields() {
    <c:if test="${pageId == 'adHocInv' || pageId == 'adHocUsageRpt'}">
	    var blankVal = '${templateBean.reportPeriodType}';	   
	    if(blankVal != '') {	       
	    	document.forms[0][blankVal].checked=true;
	    } else {		    
		    document.forms[0]["specificDates"].checked=true;	    	
	    }
	    var selectMonth = document.getElementById("selDayOfMonth");
	    for(var i=1;i<=31;i++){  
	    	selectMonth.options[selectMonth.options.length] = new Option(i,i);
	    }
	    <c:if test="${templateBean.reportPeriodType == 'dayOfMonth'}">
	          $("selDayOfMonth").value = '${templateBean.selDayOfMonth}';
	    </c:if>
	    handleClick();
    </c:if>    
}

function dayofyear(d) {   // d is a Date object
	var yn = d.getFullYear();
	var mn = d.getMonth();
	var dn = d.getDate();
	var d1 = new Date(yn,0,1,12,0,0); // noon on Jan. 1
	var d2 = new Date(yn,mn,dn,12,0,0); // noon on input date
	var ddiff = Math.round((d2-d1)/864e5);
	return ddiff+1; 
}

function getDayOfYear() {
    var dte = $v("dayOfYearJsp"); 
    j$.ajax({
        type: "POST",
        url: "getdayofyear.do",
        data: { inputdate: dte },
        success: initDayOfYearValue
    });
}

function initDayOfYearValue(results) {
	$("selDayOfYear").value=results;
}

// -->
</script>

   <table id="searchMaskTable" width="100%" border="0" cellpadding="0"> 
     <tr>
        <td colspan="2"> 
            <table>
             <tr>
               <td class="optionTitleBoldLeft" nowrap>
                <input type="radio" name="reportPeriodType" id="specificDates" value="specificDates" onclick="handleClick(this);"/>
			    <span style="width:100%">
			        <fmt:message key="label.begindate"/>:&nbsp;
			        <input type="hidden" name="today" id="today" value='<tcmis:getDateTag numberOfDaysFromToday="1" datePattern="${dateFormatPattern}"/>' />
			        <html:text size="10" property="beginDateJsp" styleClass="inputBox" styleId="beginDateJsp"
			                      onclick="return getCalendar(document.forms[0].beginDateJsp,null,$('today'),null,document.forms[0].endDateJsp);"
			                      onfocus="blur();"/> <a href="javascript: void(0);" ID="linkbeginDateJsp"
			                      STYLE="color:#0000ff;cursor:pointer;text-decoration:underline"> </a>
			        &nbsp;
			        <fmt:message key="label.enddate"/>:&nbsp;
			        <input type="hidden" name="todayEnd" id="todayEnd" value='<tcmis:getDateTag numberOfDaysFromToday="0" datePattern="${dateFormatPattern}"/>' />
			        <html:text size="10" property="endDateJsp" styleClass="inputBox" styleId="endDateJsp"
			                      onclick="return getCalendar(document.forms[0].endDateJsp,null,null,document.forms[0].beginDateJsp,$('todayEnd'));"
			                      onfocus="blur();"/><a href="javascript: void(0);" ID="linkendDateJsp"
			                      STYLE="color:#0000ff;cursor:pointer;text-decoration:underline"></a>
			    </span>
			    &nbsp;&nbsp;&nbsp;&nbsp;		
			    </td>	 
                <td class="optionTitleBoldLeft" nowrap="true">
                    <input type="radio" name="reportPeriodType" id="numberOfDays" value="numberOfDays" onclick="handleClick(this);"/>
                    <fmt:message key="label.Last"/>&nbsp;<input type="text" name="numOfDays" class="inputBox" id="numOfDays" value="${templateBean.numOfDays}" maxlength="3" size="2" />&nbsp;<fmt:message key="label.days"/>
                    &nbsp;&nbsp;&nbsp;&nbsp;
                </td>            
                <td class="optionTitleBoldLeft" nowrap="true">
                    <input type="radio" name="reportPeriodType" id="dayOfWeek" value="dayOfWeek" onclick="handleClick(this);" />
                    <fmt:message key="label.since"/>&nbsp; 
                    <select class="selectBox" name="selDayOfWeek" id="selDayOfWeek">
                        <option value="" <c:if test="${templateBean.selDayOfWeek == ''}">selected</c:if>><fmt:message key="label.select"/></option>
                        <option value="1" <c:if test="${templateBean.selDayOfWeek == '1'}">selected</c:if>><fmt:message key="label.sunday"/></option>
                        <option value="2" <c:if test="${templateBean.selDayOfWeek == '2'}">selected</c:if>><fmt:message key="label.monday"/></option>
                        <option value="3" <c:if test="${templateBean.selDayOfWeek == '3'}">selected</c:if>><fmt:message key="label.tuesday"/></option>
                        <option value="4" <c:if test="${templateBean.selDayOfWeek == '4'}">selected</c:if>><fmt:message key="label.wednesday"/></option>
                        <option value="5" <c:if test="${templateBean.selDayOfWeek == '5'}">selected</c:if>><fmt:message key="label.thursday"/></option>
                        <option value="6" <c:if test="${templateBean.selDayOfWeek == '6'}">selected</c:if>><fmt:message key="label.friday"/></option>
                        <option value="7" <c:if test="${templateBean.selDayOfWeek == '7'}">selected</c:if>><fmt:message key="label.saturday"/></option>
                    </select>
                    &nbsp;&nbsp;&nbsp;&nbsp;
                </td>
                <td class="optionTitleBoldLeft" nowrap="true">
                    <input type="radio" name="reportPeriodType" id="dayOfMonth" value="dayOfMonth" onclick="handleClick(this);" />
                    <fmt:message key="label.sincedayofmonth"/>&nbsp;
                    <select class="selectBox" name="selDayOfMonth" id="selDayOfMonth">
                        <option value="" <c:if test="${templateBean.selDayOfMonth == ''}">selected</c:if>><fmt:message key="label.select"/></option>
                    </select>
                    &nbsp;&nbsp;&nbsp;&nbsp;
               </td>
               
               <%--comment out for now tngo 120914, need to replace this with month and date dropdown (and calculate day of year from dropdowns)
               <td class="optionTitleBoldLeft" nowrap="true">
                    <input type="radio" name="reportPeriodType" id="dayOfYear" value="dayOfYear" onclick="handleClick(this);" />
                    <fmt:message key="label.sinceday"/>&nbsp;
                    <input class="inputBox" type="text" name="selDayOfYear" id="selDayOfYear" value="${templateBean.selDayOfYear}" maxlength="3" size="2" />&nbsp;<fmt:message key="label.ofyear"/>&nbsp;
                    <input type="hidden" name="now" id="now" value='<tcmis:getDateTag numberOfDaysFromToday="1" datePattern="${dateFormatPattern}"/>' />
                    <html:text  onchange="getDayOfYear()" size="10" property="dayOfYearJsp" styleClass="inputBox" styleId="dayOfYearJsp"
                                  onclick="return getCalendar(document.forms[0].dayOfYearJsp,null,null,null,null);"
                                  onfocus="blur();"/> <a href="javascript: void(0);" ID="linkDayOfYearJsp"
                                  STYLE="color:#0000ff;cursor:pointer;text-decoration:underline"> </a>
                </td>
                --%>
            </tr>
         </table>
       </td>
     </tr>
   </table>        
