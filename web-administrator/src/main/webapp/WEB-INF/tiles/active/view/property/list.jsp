<%@page pageEncoding="utf-8" contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="struts" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>
<%@ taglib prefix="sjg" uri="/struts-jquery-grid-tags"%>

<struts:url var="propertyAddUrl" action="addForm" namespace="/property" />
<struts:url id="remoteurl" action="table" namespace="/property"/>

<script type="text/javascript">
function formatEditLink(cellvalue, options, rowObject) {
  return "<a href='/admin/property/editForm.html?property="+ cellvalue +"' style='border-style: none;'>" + 
         "<img src='/admin/icons/16x16/pencil.png'/>" + 
         "</a>&nbsp;&nbsp;&nbsp;" + 
         "<a href='/admin/property/deleteForm.html?property="+ cellvalue +"' style='border-style: none;'>" + 
         "<img src='/admin/icons/16x16/delete.png'/>" + 
         "</a>";
}
</script>

      <!--  Start MAIN -->
      <div class="main">
        <div class="sub_menu">
          <struts:a href="%{propertyAddUrl}">
            <struts:text name="property.add"/>
          </struts:a>
        </div>
        
        <sjg:grid
          id="gridedittable"
          caption="%{getText('properties')}"
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
          rowNum="15"
          editinline="false"
          viewrecords="true"
        >
    	  <sjg:gridColumn 
    	      name="key"
    	      index="key"
    	      title="%{getText('property.key')}"
    	      width="560"
    	      editable="false"
    	      sortable="true"
    	      hidden="false"
    	      search="false"
    	      resizable="false"
    	      align="center"
    	  />
    	  <sjg:gridColumn
    	    name="value"
    	    index="value"
    	    title="%{getText('property.value')}"
    	    width="815"
    	    editable="false"
    	    sortable="true"
    	    hidden="false"
    	    search="false"
    	    resizable="false"
    	    align="left"
    	  />
    	  <sjg:gridColumn
    	      name="key"
    	      index="editbar"
    	      title=""
    	      width="210"
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