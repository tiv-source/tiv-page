<%@page pageEncoding="utf-8" contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="struts" uri="/struts-tags" %>

<struts:url var="manualAddUrl" action="addForm" namespace="/manual" />
<struts:url id="remoteurl" action="table" namespace="/manual"/>

<script type="text/javascript">
function formatEditLink(cellvalue, options, rowObject) {
  return "<a href='/admin/manual/editForm.html?manual="+ cellvalue +"' style='border-style: none;'>" + 
         "<img src='/admin/icons/16x16/pencil.png'/>" + 
         "</a>&nbsp;&nbsp;&nbsp;" + 
         "<a href='/admin/manual/deleteForm.html?manual="+ cellvalue +"' style='border-style: none;'>" + 
         "<img src='/admin/icons/16x16/delete.png'/>" + 
         "</a>";
}
</script>

<script type="text/javascript">
function formatTrueFalse(cellvalue, options, rowObject) {
  if (cellvalue) {
    return "<img src='/admin/icons/16x16/accept.png'/>";  
  } else {
    return "<img src='/admin/icons/16x16/oneway.png'/>";
  }
}
</script>

<script type="text/javascript">
function formatIsoDate(celldate, options, rowObject) {
  var newDate = new Date();
  newDate.setTime(Date.parse(celldate));
  return newDate.toLocaleFormat('%H:%M:%S - %d.%m.%Y');
}
</script>

<script type="text/javascript">
function formatPicture(cellvalue, options, rowObject) {
	return "<img src='/pictures/THUMBNAIL/" + cellvalue + "'/>";  
}
</script>


      <!--  Start MAIN -->
      <div class="main">
        <div class="sub_menu">
          <struts:a href="%{manualAddUrl}">
            <struts:text name="manual.add"/>
          </struts:a>
        </div>

        <sjg:grid
          id="gridedittable"
          caption="%{getText('manuals')}"
          dataType="json"
          href="%{remoteurl}"
          pager="true"
          navigator="true"
          navigatorAdd="false"
          navigatorSearch="false"
          navigatorEdit="false"
          navigatorView="false"
          navigatorDelete="false"
          gridModel="gridModel"
          rowList="5,10,15,20"
          rowNum="20"
          editinline="false"
          viewrecords="true"
        >
    	  <sjg:gridColumn 
    	    name="picture.pictureUrls.THUMBNAIL.url" 
    	    index="picture" 
    	    title="%{getText('picture')}" 
    	    width="330" 
    	    editable="false" 
    	    sortable="false" 
    	    hidden="false" 
    	    search="false" 
    	    resizable="false" 
    	    align="left" 
    	    formatter="formatPicture" 
    	  />
    	  <sjg:gridColumn 
    	    name="descriptionMap.DE.name" 
    	    index="name" 
    	    title="%{getText('descriptionMap.DE.name')}" 
    	    width="350" 
    	    editable="false" 
    	    sortable="true" 
    	    hidden="false" 
    	    search="false" 
    	    resizable="false" 
    	    align="left" 
    	  />
    	  <sjg:gridColumn 
    	    name="releaseDate" 
    	    index="releaseDate" 
    	    title="%{getText('releaseDate')}" 
    	    width="180" 
    	    editable="false" 
    	    sortable="true" 
    	    hidden="false" 
    	    search="false" 
    	    resizable="false" 
    	    align="center" 
    	  />
    	  <sjg:gridColumn 
    	    name="visible" 
    	    index="visible" 
    	    title="%{getText('visible')}" 
    	    width="70" 
    	    editable="false" 
    	    sortable="true" 
    	    hidden="false" 
    	    search="false" 
    	    resizable="false" 
    	    align="center" 
    	    formatter="formatTrueFalse"
    	  />
    	  <sjg:gridColumn 
    	    name="created" 
    	    index="created" 
    	    title="%{getText('created')}" 
    	    width="140" 
    	    editable="false" 
    	    sortable="true" 
    	    hidden="false" 
    	    search="false" 
    	    resizable="false" 
    	    align="center" 
    	    formatter="formatIsoDate"
    	  />
    	  <sjg:gridColumn 
    	    name="modified" 
    	    index="modified" 
    	    title="%{getText('modified')}" 
    	    width="140" 
    	    editable="false" 
    	    sortable="true" 
    	    hidden="false" 
    	    search="false" 
    	    resizable="false" 
    	    align="center" 
    	    formatter="formatIsoDate"
    	  />
    	  <sjg:gridColumn 
    	    name="modifiedBy" 
    	    index="modifiedBy" 
    	    title="%{getText('modifiedBy')}" 
    	    width="140" 
    	    editable="false" 
    	    sortable="true" 
    	    hidden="false" 
    	    search="false" 
    	    resizable="false" 
    	    align="left" 
    	  />
    	  <sjg:gridColumn 
    	    name="modifiedAddress" 
    	    index="modifiedAddress" 
    	    title="%{getText('modifiedAddress')}" 
    	    width="140" 
    	    editable="false" 
    	    sortable="true" 
    	    hidden="false" 
    	    search="false" 
    	    resizable="false" 
    	    align="left" 
    	  />


    	  <sjg:gridColumn 
    	    name="uuid" 
    	    index="editbar" 
    	    title="" 
    	    width="65" 
    	    editable="false" 
    	    sortable="false" 
    	    hidden="false" 
    	    search="false" 
    	    resizable="false" 
    	    align="right" 
    	    formatter="formatEditLink" 
    	  />    	
        </sjg:grid>

        <div style="width:100%; margin: 10px;">&nbsp;</div>
    
      </div>
      <!--  Ende MAIN -->