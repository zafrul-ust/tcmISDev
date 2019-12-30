package com.tcmis.client.report.beans;

import java.math.BigDecimal;
import java.sql.Array;
import java.sql.SQLData;
import java.sql.SQLException;
import java.sql.SQLInput;
import java.sql.SQLOutput;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.tcmis.common.framework.BaseDataBean;

/******************************************************************************
 * CLASSNAME: CatalogAddRequestOvBean <br>
 * @version: 1.0, Aug 20, 2010 <br>
 *****************************************************************************/

public class DeliveredReceiptDocsOvBean extends BaseDataBean implements SQLData {
 
		private String documentName;
		private String documentType;
		private Date documentDate;
		private String documentUrl;
		private BigDecimal receiptId;
		private BigDecimal documentId;
		
		public static final String sqlType = "CUSTOMER.DELIVERED_RECEIPT_DOCS_OBJ";
		
		public DeliveredReceiptDocsOvBean() {
		}
		
		
		
		public String getDocumentName() {
			return documentName;
		}



		public void setDocumentName(String documentName) {
			this.documentName = documentName;
		}



		public String getDocumentType() {
			return documentType;
		}



		public void setDocumentType(String documentType) {
			this.documentType = documentType;
		}



		public Date getDocumentDate() {
			return documentDate;
		}



		public void setDocumentDate(Date documentDate) {
			this.documentDate = documentDate;
		}



		public String getDocumentUrl() {
			return documentUrl;
		}



		public void setDocumentUrl(String documentUrl) {
			this.documentUrl = documentUrl;
		}
		
		
		
		public BigDecimal getReceiptId() {
			return receiptId;
		}



		public void setReceiptId(BigDecimal receiptId) {
			this.receiptId = receiptId;
		}



		public BigDecimal getDocumentId() {
			return documentId;
		}



		public void setDocumentId(BigDecimal documentId) {
			this.documentId = documentId;
		}



		public static String getSqltype() {
			return sqlType;
		}

		public String getSQLTypeName() throws SQLException {
			return sqlType;
		}



		public void readSQL(SQLInput stream, String typeName) throws SQLException {
			try {
				setDocumentUrl(stream.readString());
				setDocumentDate(stream.readTimestamp());
				setDocumentType(stream.readString());
				setDocumentName(stream.readString());
				setReceiptId(stream.readBigDecimal());
				setDocumentId(stream.readBigDecimal());
				
			}
			catch (SQLException e) {
				throw e;
			}
			catch (Exception e) {
				Log log = LogFactory.getLog(this.getClass());
				log.error("readSQL method failed", e);
			}
		}

		public void writeSQL(SQLOutput stream) throws SQLException {
			
		}
	
	
}