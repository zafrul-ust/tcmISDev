<?xml version = "1.0" encoding = "UTF-8"?>
<xsl:stylesheet version = "1.0"
  xmlns = "http://www.tcmis.com/namespace/batch"
	xmlns:invhd = "http://www.tcmis.com/namespace/databean/com.tcmis.generated.tcm_ops.Cr658HeaderViewImpl"
	xmlns:invit = "http://www.tcmis.com/namespace/databean/com.tcmis.generated.tcm_ops.Cr658ItemViewImpl"
	xmlns:xsl = "http://www.w3.org/1999/XSL/Transform"
	xmlns:str = "http://www.tcmis.com/namespaces/strings"
	xmlns:txt = "http://www.tcmis.com/namespaces/text"
	xmlns:date = "http://www.tcmis.com/namespaces/date"
	xmlns:xsi = "http://www.w3.org/2001/XMLSchema-instance"
	xsi:schemaLocation = "http://www.w3.org/1999/XSL/Transform/xslt.xsd"
	extension-element-prefixes = "str txt date">

	<xsl:output method = "text"/>
	
	<xsl:include href = "dates.xsl"/>
	<xsl:include href = "text.xsl"/>
	<!--
		Produce a UTC CR658 Invoice Report

	<xsl:template match = "/">    
		<xsl:apply-templates/>
	</xsl:template>
	-->
<xsl:template match="/">
  <xsl:apply-templates select="root"/>
</xsl:template>
	
	<xsl:template match = "root">
		<xsl:text>STR0A</xsl:text>

		<xsl:call-template name = "txt:justify">    
			<xsl:with-param name = "width" select = "'17'"/>    
			<xsl:with-param name = "value" select = "company"/>
		</xsl:call-template>

		<xsl:call-template name = "format-date">        
			<xsl:with-param name = "date" select = "current-date-time"/>  
                        <xsl:with-param name="format" select="1" />               
		</xsl:call-template>


		<xsl:call-template name = "txt:justify">        
			<xsl:with-param name = "width">9</xsl:with-param>        
			<xsl:with-param name = "value" select = "collno"/>        
			<xsl:with-param name = "align" select = " 'right' "/>        
			<xsl:with-param name = "fill-char" select = " '0' "/>    
		</xsl:call-template>

		<xsl:call-template name = "str:dup">        
			<xsl:with-param name = "input" select = " ' ' "/>        
			<xsl:with-param name = "count">690</xsl:with-param>    
		</xsl:call-template>     
<!--    
		<xsl:apply-templates/> 
-->
  <xsl:apply-templates select="invoices"/>
  <xsl:apply-templates select="TRAILER"/>
	</xsl:template>

	<xsl:template match = "invoices">
<xsl:for-each select="Cr658HeaderViewBean">
  	<xsl:call-template name = "txt:justify">        
  		<xsl:with-param name = "width">5</xsl:with-param>        
  		<xsl:with-param name = "value" select = "recordId"/>
  	</xsl:call-template>

	
  	<xsl:call-template name = "txt:justify">        
  		<xsl:with-param name = "width">4</xsl:with-param>        
  		<xsl:with-param name = "value" select = "bukrs"/>
  	</xsl:call-template> 

		

  	<xsl:call-template name = "txt:justify">        
  		<xsl:with-param name = "width">16</xsl:with-param>        
  		<xsl:with-param name = "value" select = "xblnr"/>
  		<xsl:with-param name="truncate">no</xsl:with-param>
  	</xsl:call-template>

		
		<xsl:call-template name = "txt:justify">        
			<xsl:with-param name = "width">8</xsl:with-param>        
			<xsl:with-param name = "value">    
				<xsl:call-template name = "format-date">        
					<xsl:with-param name = "date" select = "bldat"/>  
                	        	<xsl:with-param name = "format" select = "2"/>                
				</xsl:call-template>
			</xsl:with-param>    
		</xsl:call-template>

		

		<xsl:call-template name = "txt:justify">        
			<xsl:with-param name = "width">10</xsl:with-param>        
			<xsl:with-param name = "value" select = "lifnr"/>
		</xsl:call-template>  

		

		<xsl:call-template name = "txt:justify">        
			<xsl:with-param name = "width">25</xsl:with-param>        
			<xsl:with-param name = "value" select = "bktxt"/>    
		</xsl:call-template> 

		

		<xsl:call-template name = "txt:justify">        
			<xsl:with-param name = "width">16</xsl:with-param>        
			<xsl:with-param name = "value" select = "cblnr"/>    
		</xsl:call-template>  

		

		<xsl:call-template name = "txt:justify">        
			<xsl:with-param name = "width">3</xsl:with-param>        
			<xsl:with-param name = "value" select = "waers"/>
		</xsl:call-template>    

		

		<xsl:call-template name="txt:change-trailing-sign">
		    <xsl:with-param name="value" select="format-number(wrbtr, '000000000000.000+;000000000000.000-')"/>
		    <xsl:with-param name="test-val" select="'00000000000000.000-'"/>
		</xsl:call-template> 

		

		<xsl:call-template name="txt:change-trailing-sign">
		    <xsl:with-param name="value" select="format-number(wmwst, '000000000000.000+;000000000000.000-')"/>
		    <xsl:with-param name="test-val" select="'00000000000000.000-'"/>
		</xsl:call-template>

		<xsl:call-template name = "str:dup">        
			<xsl:with-param name = "input" select = " ' ' "/>        
			<xsl:with-param name = "count">615</xsl:with-param>    
		</xsl:call-template> 
<!--
  <xsl:value-of select="facilityId"/>
  <xsl:value-of select="invoice"/>
  <xsl:value-of select="issueId"/>
  <xsl:value-of select="issueCostRevision"/>
  <xsl:value-of select="counter"/>
-->
<xsl:for-each select="cr658ItemViewBeanCollection">
<xsl:for-each select="cr658ItemViewBean">
  	<xsl:call-template name = "txt:justify">        
  		<xsl:with-param name = "width">5</xsl:with-param>        
  		<xsl:with-param name = "value" select = "recordId"/>
  	</xsl:call-template> 


		<xsl:call-template name = "txt:justify">        
			<xsl:with-param name = "width">4</xsl:with-param>        
			<xsl:with-param name = "value" select = "bukrs"/>    
		</xsl:call-template> 

		
		<xsl:call-template name = "txt:justify">        
			<xsl:with-param name = "width">16</xsl:with-param>        
			<xsl:with-param name = "value" select = "xblnr"/>
  			<xsl:with-param name="truncate">no</xsl:with-param>
		</xsl:call-template> 

		

		<xsl:call-template name = "txt:justify">        
			<xsl:with-param name = "width">3</xsl:with-param>        
			<xsl:with-param name = "value" select = "xitem"/>
		</xsl:call-template> 

		

		<xsl:call-template name = "txt:justify">        
			<xsl:with-param name = "width">8</xsl:with-param>        
			<xsl:with-param name = "value"> 
				<xsl:call-template name = "format-date">        
					<xsl:with-param name = "date" select = "bldat"/>  
                	        	<xsl:with-param name = "format" select = "2"/>                
				</xsl:call-template>
			</xsl:with-param>    
		</xsl:call-template>

		

		<xsl:call-template name = "txt:justify">        
			<xsl:with-param name = "width">10</xsl:with-param>        
			<xsl:with-param name = "value" select = "lifnr"/>
		</xsl:call-template>

		

		<xsl:call-template name = "txt:justify">        
			<xsl:with-param name = "width">1</xsl:with-param>        
			<xsl:with-param name = "value" select = "zmwsk"/>    
		</xsl:call-template>

		

		<xsl:call-template name = "txt:justify">        
			<xsl:with-param name = "width">2</xsl:with-param>        
			<xsl:with-param name = "value" select = "mwskz"/>    
		</xsl:call-template>

		
 
		<xsl:call-template name = "txt:justify">        
			<xsl:with-param name = "width">15</xsl:with-param>        
			<xsl:with-param name = "value" select = "txjcd"/>    
		</xsl:call-template> 

		

		<xsl:call-template name = "txt:justify">        
			<xsl:with-param name = "width">50</xsl:with-param>        
			<xsl:with-param name = "value" select = "sgtxt"/>    
		</xsl:call-template>

		

		<xsl:call-template name = "txt:justify">        
			<xsl:with-param name = "width">9</xsl:with-param>        
			<xsl:with-param name = "value" select = "matkl"/>    
		</xsl:call-template> 

		

		<xsl:call-template name = "txt:sapjustify">        
			<xsl:with-param name = "width">10</xsl:with-param>        
			<xsl:with-param name = "value" select = "kostl"/>    
		</xsl:call-template>

		

		<xsl:call-template name = "txt:sapjustify">        
			<xsl:with-param name = "width">10</xsl:with-param>        
			<xsl:with-param name = "value" select = "hkont"/>    
		</xsl:call-template>

		

		<xsl:call-template name = "txt:justify">        
			<xsl:with-param name = "width">20</xsl:with-param>        
			<xsl:with-param name = "value" select = "' '"/>    
		</xsl:call-template>

		

		<xsl:call-template name = "txt:justify">        
			<xsl:with-param name = "width">17</xsl:with-param>        
			<xsl:with-param name = "value" select = "' '"/>    
		</xsl:call-template>

		

		<xsl:call-template name = "txt:justify">        
			<xsl:with-param name = "width">9</xsl:with-param>        
			<xsl:with-param name = "value" select = "acctng01"/>    
		</xsl:call-template>

		


		<xsl:call-template name = "txt:justify">        
			<xsl:with-param name = "width">9</xsl:with-param>        
			<xsl:with-param name = "value" select = "acctng02"/>    
		</xsl:call-template>

		

		<xsl:call-template name = "txt:justify">        
			<xsl:with-param name = "width">9</xsl:with-param>        
			<xsl:with-param name = "value" select = "acctng03"/>    
		</xsl:call-template> 

		

		<xsl:call-template name = "txt:justify">        
			<xsl:with-param name = "width">20</xsl:with-param>        
			<xsl:with-param name = "value" select = "acctng04"/>    
		</xsl:call-template>

		

		<xsl:call-template name = "txt:justify">        
			<xsl:with-param name = "width">20</xsl:with-param>        
			<xsl:with-param name = "value" select = "acctng05"/>    
		</xsl:call-template> 

		

		<xsl:call-template name = "txt:justify">        
			<xsl:with-param name = "width">5</xsl:with-param>        
			<xsl:with-param name = "value" select = "zallowInd"/>    
		</xsl:call-template>

		

		<xsl:call-template name = "txt:justify">        
			<xsl:with-param name = "width">20</xsl:with-param>        
			<xsl:with-param name = "value" select = "assetNum"/>    
		</xsl:call-template>  

		

		<xsl:call-template name = "txt:justify">        
			<xsl:with-param name = "width">3</xsl:with-param>        
			<xsl:with-param name = "value" select = "waers"/>    
		</xsl:call-template>

		


		<xsl:call-template name="txt:change-trailing-sign">
		    <xsl:with-param name="value" select="format-number(wrbtr, '000000000000.000+;000000000000.000-')"/>
		    <xsl:with-param name="test-val" select="'00000000000000.000-'"/>
		</xsl:call-template>

		

		<xsl:call-template name="txt:change-trailing-sign">
		    <xsl:with-param name="value" select="format-number(wmwst, '000000000000.000+;000000000000.000-')"/>
		    <xsl:with-param name="test-val" select="'00000000000000.000-'"/>
		</xsl:call-template>

		

		<xsl:value-of select = "format-number(kbetr, '00.000')"/>

		
		<xsl:choose>
			<xsl:when test="not(menge)">
				<xsl:value-of select = "format-number(number('0'), '000000000.000')"/>
			</xsl:when>
			<xsl:otherwise>
				<xsl:value-of select = "format-number(menge, '000000000.000')"/>
			</xsl:otherwise>
		</xsl:choose> 

 
		<xsl:call-template name = "txt:justify">        
			<xsl:with-param name = "width">3</xsl:with-param>        
			<xsl:with-param name = "value" select = "meins"/>    
		</xsl:call-template>

		


		<xsl:call-template name = "txt:justify">        
			<xsl:with-param name = "width">5</xsl:with-param>        
			<xsl:with-param name = "value" select = "zkeyid"/>    
		</xsl:call-template>

		

		<xsl:call-template name = "txt:justify">        
			<xsl:with-param name = "width">60</xsl:with-param>        
			<xsl:with-param name = "value" select = "zemail"/>    
		</xsl:call-template>

		

		<xsl:call-template name = "txt:justify">        
			<xsl:with-param name = "width">1</xsl:with-param>        
			<xsl:with-param name = "value" select = "'N'"/>    
		</xsl:call-template>

		

		<xsl:call-template name = "txt:justify">        
			<xsl:with-param name = "width">5</xsl:with-param>        
			<xsl:with-param name = "value" select="zcust"/>
		</xsl:call-template>  

		

		<xsl:call-template name = "txt:justify">        
			<xsl:with-param name = "width">30</xsl:with-param>        
			<xsl:with-param name = "value" select = "zname"/>    
		</xsl:call-template>

		

		<xsl:call-template name = "txt:justify">        
			<xsl:with-param name = "width">20</xsl:with-param>        
			<xsl:with-param name = "value" select = "zextpo"/>    
		</xsl:call-template> 

		

		<xsl:value-of select = "format-number(zextpoln, '000000')"/>

		

		<xsl:call-template name = "txt:justify">        
			<xsl:with-param name = "width">22</xsl:with-param>        
			<xsl:with-param name = "value" select = "idnlf"/>    
		</xsl:call-template>	

		<xsl:value-of select = "format-number(zunpr, '000000.00')"/>

		<xsl:call-template name = "txt:justify">        
			<xsl:with-param name = "width">16</xsl:with-param>        
			<xsl:with-param name = "value" select = "xblnr"/>
	  		<xsl:with-param name="truncate">no</xsl:with-param>
		</xsl:call-template>

		<xsl:call-template name = "txt:justify">        
			<xsl:with-param name = "width">3</xsl:with-param>        
			<xsl:with-param name = "value" select = "counter"/>
		</xsl:call-template>

		

		<xsl:call-template name = "txt:justify">        
			<xsl:with-param name = "width">50</xsl:with-param>        
			<xsl:with-param name = "value" select = "ztier2"/>    
		</xsl:call-template> 

		

		<xsl:call-template name = "txt:justify">        
			<xsl:with-param name = "width">1</xsl:with-param>        
			<xsl:with-param name = "value" select = "accrueStx"/>    
		</xsl:call-template>
		<xsl:call-template name = "str:dup">        
			<xsl:with-param name = "input" select = " ' ' "/>        
			<xsl:with-param name = "count">177</xsl:with-param>    
		</xsl:call-template>
<!--
  <xsl:value-of select="facilityId"/>
  <xsl:value-of select="counter"/>
-->  
</xsl:for-each> 
</xsl:for-each> 
</xsl:for-each> 
        </xsl:template>
<xsl:template match = "TRAILER">
<!--
<xsl:for-each select="TRAILER">	
-->
		<xsl:text>END0A</xsl:text>


		<xsl:call-template name = "format-date">        
			<xsl:with-param name = "date" select = "../current-date-time"/>  
                        <xsl:with-param name = "format" select = "1"/>                
		</xsl:call-template>

		<xsl:value-of select = "format-number(INV_CNT, '000000000')"/>

		<xsl:value-of select = "format-number(INV_ITM_CNT, '000000000')"/>

		<xsl:call-template name="txt:change-trailing-sign">
		    <xsl:with-param name="value" select="format-number(INV_HASH, '00000000000000.000+;00000000000000.000-')"/>
		    <xsl:with-param name="test-val" select="'00000000000000.000-'"/>
		</xsl:call-template>

		<xsl:call-template name="txt:change-trailing-sign">
		    <xsl:with-param name="value" select="format-number(INV_ITM_HASH, '00000000000000.000+;00000000000000.000-')"/>
		    <xsl:with-param name="test-val" select="'00000000000000.000-'"/>
		</xsl:call-template>

		<xsl:call-template name = "str:dup">        
			<xsl:with-param name = "input" select = " ' ' "/>        
			<xsl:with-param name = "count">660</xsl:with-param>    
		</xsl:call-template>  
<!--
</xsl:for-each> 
-->
	</xsl:template>



<xsl:template name="format-date">
  <xsl:param name="date" />
  <xsl:param name="format"/>

  <xsl:variable name="monthName" select="substring-before(substring-after($date, ' '), ' ')" />
  <xsl:variable name="day" select="substring-before(substring-after(substring-after($date, ' '), ' '), ' ')" />
  <xsl:variable name="year" select="substring-after(substring-after(substring-after(substring-after(substring-after($date, ' '), ' '), ' '), ' '), ' ')" />

  <xsl:variable name="month" select="substring(substring-after('Jan01Feb02Mar03Apr04May05Jun06Jul07Aug08Sep09Oct10Nov11Dec12', $monthName), 1, 2)" />
  <xsl:variable name="day2" select="concat(translate(substring($day,1,1), '0', ''), substring($day,2,1))" />
  <xsl:variable name="month2" select="concat(translate(substring($month,1,1), '0', ''), substring($month,2,1))" />

  <xsl:variable name="hour" select="substring-before(substring-after(substring-after(substring-after($date, ' '), ' '), ' '), ':')" />
  <xsl:variable name="minute" select="substring-before(substring-after(substring-after(substring-after(substring-after($date, ' '), ' '), ' '), ':'), ':')" />
  <xsl:variable name="second" select="substring-before(substring-after(substring-after(substring-after(substring-after(substring-after($date, ' '), ' '), ' '), ':'), ':'), ' ')" />






  <xsl:choose>
    <xsl:when test="$format = 1">
     <xsl:value-of select="concat($year, $month, $day, '.', $hour, $minute, $second)" />
    </xsl:when>

    <xsl:when test="$format = 2">
     <xsl:value-of select="concat($year, $month, $day)" />
    </xsl:when>

    <xsl:otherwise>
     <xsl:value-of select="$date" />
    </xsl:otherwise>
  </xsl:choose>

</xsl:template>

</xsl:stylesheet>

