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
  return "<a href='/admin/files/deleteForm.html?file="+ cellvalue +"' style='border-style: none;'>" + 
         "<img src='/admin/icons/16x16/delete.png'/>" + 
         "</a>";
}
</script>


      <!--  Start MAIN -->
      <div class="main">
        <div class="sub_menu">
        </div>

        <sjg:grid
          id="gridedittable"
          caption="%{getText('files')}"
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
    	    index="picture" 
    	    title="%{getText('picture')}" 
    	    width="650" 
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
    	    title="%{getText('name')}" 
    	    width="650" 
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
    	    width="285" 
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