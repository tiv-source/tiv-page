<%@page pageEncoding="utf-8" contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="struts" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>
<%@ taglib prefix="sjg" uri="/struts-jquery-grid-tags"%>

<struts:url var="pageAddUrl" action="addForm" namespace="/page" />
<struts:url id="remoteurl" action="table" namespace="/files"/>

<script type="text/javascript">
function formatViewLink(cellvalue, options, rowObject) {
  return "<img src='/uploads/"+ cellvalue +"' style='max-width:200px; max-height:100px;' />";
}
</script>

<script type="text/javascript">
function formatEditLink(cellvalue, options, rowObject) {
  return "<a href='/admin/files/deleteForm.html?file="+ cellvalue +"'>delete</a>";
}
</script>


      <!--  Start MAIN -->
      <div class="main">
        <div class="sub_menu">
          <struts:a href="%{pageAddUrl}">Add File</struts:a>
        </div>

        <sjg:grid
          id="gridedittable"
          caption="Files"
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
    	    name="name" 
    	    index="view" 
    	    title="Bild" 
    	    width="555" 
    	    editable="false" 
    	    sortable="false" 
    	    hidden="false" 
    	    search="false" 
    	    resizable="false" 
    	    align="center" 
    	    formatter="formatViewLink" 
    	  />    	
    	  <sjg:gridColumn 
    	    name="name" 
    	    index="name" 
    	    title="Name" 
    	    width="500" 
    	    editable="false" 
    	    sortable="true" 
    	    hidden="false" 
    	    search="false" 
    	    resizable="false" 
    	    align="center" 
    	  />
    	  <sjg:gridColumn 
    	    name="name" 
    	    index="editbar" 
    	    title="" 
    	    width="400" 
    	    editable="false" 
    	    sortable="false" 
    	    hidden="false" 
    	    search="false" 
    	    resizable="false" 
    	    align="center" 
    	    formatter="formatEditLink" 
    	  />    	
        </sjg:grid>

        <div style="width:100%; margin: 10px;">&nbsp;</div>
    
      </div>
      <!--  Ende MAIN -->