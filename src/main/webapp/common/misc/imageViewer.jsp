<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="/WEB-INF/tcmis.tld" prefix="tcmis" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<html>
	<head>
		<link rel="stylesheet" type="text/css" href="/css/imageViewer.css" />
        <script type="text/javascript" src="/js/jquery/jquery-1.7.1.js" ></script>
        <script type="text/javascript" src="/js/jquery/jqueryui-1.8.11.js" ></script>
        <script type="text/javascript" src="/js/jquery/jquery.mousewheel.min.js" ></script>
        <script type="text/javascript" src="/js/jquery/jquery.iviewer.0.7.7.js" ></script>

		<c:choose>
			<c:when test="${not empty param.image}"><c:set var="imageNum" value="${param.image}" /></c:when>
			<c:otherwise><c:set var="imageNum" value="1"/></c:otherwise>
		</c:choose>
        
		<script type="text/javascript">
	        var $ = jQuery;
			var picture;
			var itemId;
			var itemImageId;
			var imagePath;
			var itemImageSet = false;
			
	        $(document).ready(function(){
	    		<c:forEach var="image" items="${imageCollection}" varStatus="status">
					<c:if test="${status.count == imageNum}">
						picture = $("#viewer").iviewer({src: "${image.relativeUrl}"});
						selectImage(${status.count}, '${image.relativeUrl}', ${image.itemPictureType}, ${image.itemPictureExists}, '${image.itemId}', '${image.itemImageId}');
					</c:if>
				</c:forEach>
			});
			
			var totalImages = <c:out value="${fn:length(imageCollection)}"/>;
			function selectImage(imageNumber, imageUrl, imageIsItem, imageItemPicExists, imageItemId, imageItemImageId) {
				for (var i=1; i<=totalImages; i++) {
					var thumbnailImage = document.getElementById('smallImage_' + i);
					if (i == imageNumber) {
						thumbnailImage.style.border = '5px solid #700';
					}
					else {
						thumbnailImage.style.border = '5px solid #ddd';
					}
				}
				picture.iviewer('loadImage', imageUrl);
				itemId = imageItemId;
				itemImageId = imageItemImageId;
				imagePath = imageUrl;
				checkForItemImage(imageUrl, imageIsItem, imageItemPicExists);
			}
			
			function checkForItemImage(imageUrl, imageIsItem, imageItemPicExists) {
				if (imageIsItem) {
					if (!itemImageSet && !imageItemPicExists) {
						alert("This item does NOT have a default image.  Please consider setting this image as the default image for this item.");
					}
					$("#imageSetter").show();
				}
				else {
					$("#imageSetter").hide();
				}
			}
			
			function setImageAsDefault() {
				itemImageSet = true;
				$("#imageSetter").hide();
				$.ajax({
					  url: "receiptImages.do",
					  type: "POST",
					  data: {uAction : "update", itemId: itemId, itemImageId: itemImageId, fileName: imagePath }
					});
			}
		</script>

	</head>
	<body>
		<div>
			<h3 style="float: left">${displayTitle}</h3><span id="imageSetter" style="float: right; display: none;"><A href="javascript:setImageAsDefault();">Set Image as Item Default Image</A></span>
		</div>
		<div class="wrapper">
            <div id="viewer" class="viewer"></div>
        </div>
		<div class="photos">
			<div id="slide-wrap">
				<div id="inner-wrap">
					<table border=0><TR><c:forEach var="image" items="${imageCollection}" varStatus="status"> 
					<td><a href="#${status.count}" onclick="selectImage(${status.count}, '${image.relativeUrl}', ${image.itemPictureType}, ${image.itemPictureExists}, '${image.itemId}', '${image.itemImageId}');"><img id="smallImage_${status.count}" src="/tcmIS/haas/thumbnail.do?image=${image.url}" 
					alt="<tcmis:jsReplace value="${image.text}"  processMultiLines="true"/>"  style="border: 5px solid <c:choose><c:when test="${status.count == imageNum}">#700</c:when><c:otherwise>#ddd</c:otherwise></c:choose>"/></a></td>
				</c:forEach></tr><TR>
				<c:forEach var="image" items="${imageCollection}" varStatus="status"><TD align="center">${image.text}</TD></c:forEach>
				</TR></TABLE>
				</div>
			</div>
		</div>
	</body>
</html>