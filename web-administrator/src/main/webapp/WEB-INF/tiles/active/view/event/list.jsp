<%@page pageEncoding="utf-8" contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="struts" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>
<%@ taglib prefix="sjg" uri="/struts-jquery-grid-tags"%>

<struts:url var="eventAddUrl" action="addForm" namespace="/event" />
<struts:url id="remoteurl" action="table" namespace="/event"/>

<script type="text/javascript">
function formatEditLink(cellvalue, options, rowObject) {
  return "<a href='/admin/event/editForm.html?event="+ cellvalue +"'>edit</a> | <a href='/admin/event/deleteForm.html?event="+ cellvalue +"'>delete</a>";
}
</script>


      <!--  Start MAIN -->
      <div class="main">
        <div class="sub_menu">
          <struts:a href="%{eventAddUrl}">Add Event</struts:a>
        </div>

        <sjg:grid
          id="gridedittable"
          caption="Event"
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
    	    name="uuid" 
    	    index="uuid" 
    	    title="UUID" 
    	    width="280" 
    	    editable="false" 
    	    sortable="true" 
    	    hidden="false" 
    	    search="false" 
    	    resizable="false" 
    	    align="center" 
    	  />
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
    	    title="Actions" 
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