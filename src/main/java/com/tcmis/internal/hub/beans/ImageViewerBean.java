package com.tcmis.internal.hub.beans;

import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;
import com.tcmis.common.util.StringHandler;

public class ImageViewerBean extends BaseDataBean {

	private BigDecimal itemId;
	private BigDecimal itemImageId;
	private String	relativeUrl;
	private String	text;
	private String	url;

	public ImageViewerBean() {
		super();
	}

	public BigDecimal getItemId() {
		return itemId;
	}

	public BigDecimal getItemImageId() {
		return itemImageId;
	}

	public String getRelativeUrl() {
		return relativeUrl;
	}

	public String getText() {
		return text;
	}

	public String getUrl() {
		return url;
	}

	public boolean isItemPictureExists() {
		return itemImageId != null;
	}

	public boolean isItemPictureType() {
		return "Item".equals(text);
	}

	public void setItemId(BigDecimal itemId) {
		this.itemId = itemId;
	}

	public void setItemImageId(BigDecimal itemImageId) {
		this.itemImageId = itemImageId;
	}

	public void setRelativeUrl(String filePath) {
		this.relativeUrl = filePath;
	}

	public void setText(String text) {
		if (!StringHandler.isBlankString(text)) {
			this.text = text.replaceFirst("Picture-", "");;
		}
	}

	public void setUrl(String url) {
		this.url = url;
		if (!StringHandler.isBlankString(url)) {
			setRelativeUrl(url.replaceFirst("https?://[^/]+", ""));
		}
	}


}
