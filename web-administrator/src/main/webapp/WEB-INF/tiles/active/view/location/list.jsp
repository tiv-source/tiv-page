<%@page pageEncoding="utf-8" contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="struts" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>
<%@ taglib prefix="sjg" uri="/struts-jquery-grid-tags"%>

<struts:url var="locationAddUrl" action="addForm" namespace="/location" />
<struts:url id="remoteurl" action="table" namespace="/location"/>

<script type="text/javascript">
function formatEditLink(cellvalue, options, rowObject) {
  return "<a href='/admin/location/overview.html?locationUuid="+ cellvalue +"' style='border-style: none;'><img src='/admin/icons/16x16/clock.png'/></a>&nbsp;&nbsp;&nbsp;<a href='/admin/location/editForm.html?location="+ cellvalue +"' style='border-style: none;'><img src='/admin/icons/16x16/pencil.png'/></a>&nbsp;&nbsp;&nbsp;<a href='/admin/location/deleteForm.html?location="+ cellvalue +"' style='border-style: none;'><img src='/admin/icons/16x16/cross.png'/></a>";
}
</script>

<script type="text/javascript">
function formatTrueFalse(cellvalue, options, rowObject) {
  if (cellvalue) {
    return "<img src='/admin/icons/16x16/accept.png'/>";  
  } else {
    return "<img src='/admin/icons/16x16/delete.png'/>";
  }
}
</script>


      <!--  Start MAIN -->
      <div class="main">
        <div class="sub_menu">
          <struts:a href="%{locationAddUrl}">Add Location</struts:a>
        </div>

        <sjg:grid
          id="gridedittable"
          caption="Location"
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
    	    name="descriptionMap.DE.name" 
    	    index="descriptionMap.DE.name" 
    	    title="Name" 
    	    width="280" 
    	    editable="false" 
    	    sortable="true" 
    	    hidden="false" 
    	    search="false" 
    	    resizable="false" 
    	    align="left" 
    	  />
    	  <sjg:gridColumn 
    	    name="address.zip" 
    	    index="address.zip" 
    	    title="PLZ" 
    	    width="70" 
    	    editable="false" 
    	    sortable="true" 
    	    hidden="false" 
    	    search="false" 
    	    resizable="false" 
    	    align="right" 
    	  />
    	  <sjg:gridColumn 
    	    name="address.city" 
    	    index="address.city" 
    	    title="Stadt" 
    	    width="140" 
    	    editable="false" 
    	    sortable="true" 
    	    hidden="false" 
    	    search="false" 
    	    resizable="false" 
    	    align="left" 
    	  />
    	  <sjg:gridColumn 
    	    name="address.country" 
    	    index="address.country" 
    	    title="Land" 
    	    width="140" 
    	    editable="false" 
    	    sortable="true" 
    	    hidden="false" 
    	    search="false" 
    	    resizable="false" 
    	    align="left" 
    	  />
    	  <sjg:gridColumn 
    	    name="contactDetails.telephone" 
    	    index="contactDetails.telephone" 
    	    title="Telefon" 
    	    width="210" 
    	    editable="false" 
    	    sortable="true" 
    	    hidden="false" 
    	    search="false" 
    	    resizable="false" 
    	    align="left" 
    	  />
    	  <sjg:gridColumn 
    	    name="contactDetails.fax" 
    	    index="contactDetails.fax" 
    	    title="Fax" 
    	    width="140" 
    	    editable="false" 
    	    sortable="true" 
    	    hidden="false" 
    	    search="false" 
    	    resizable="false" 
    	    align="left" 
    	  />
    	  <sjg:gridColumn 
    	    name="contactDetails.mobile" 
    	    index="contactDetails.mobile" 
    	    title="Mobile" 
    	    width="140" 
    	    editable="false" 
    	    sortable="true" 
    	    hidden="false" 
    	    search="false" 
    	    resizable="false" 
    	    align="left" 
    	  />
    	  <sjg:gridColumn 
    	    name="event" 
    	    index="event" 
    	    title="Event" 
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
    	    name="visible" 
    	    index="visible" 
    	    title="Visible" 
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
    	    title="Created" 
    	    width="140" 
    	    editable="false" 
    	    sortable="true" 
    	    hidden="false" 
    	    search="false" 
    	    resizable="false" 
    	    align="center" 
    	  />
    	  <sjg:gridColumn 
    	    name="uuid" 
    	    index="editbar" 
    	    title="" 
    	    width="70" 
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