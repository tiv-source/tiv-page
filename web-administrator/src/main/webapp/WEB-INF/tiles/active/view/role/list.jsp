<%@page pageEncoding="utf-8" contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="struts" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>
<%@ taglib prefix="sjg" uri="/struts-jquery-grid-tags"%>

<struts:url var="roleAddUrl" action="addForm" namespace="/role" />
<struts:url id="remoteurl" action="table" namespace="/role"/>

<script type="text/javascript">
function formatEditLink(cellvalue, options, rowObject) {
  return "<a href='/admin/role/editForm.html?role="+ cellvalue +"' style='border-style: none;'>" + 
         "<img src='/admin/icons/16x16/pencil.png'/>" + 
         "</a>&nbsp;&nbsp;&nbsp;" + 
         "<a href='/admin/role/deleteForm.html?role="+ cellvalue +"' style='border-style: none;'>" + 
         "<img src='/admin/icons/16x16/delete.png'/>" + 
         "</a>";
}
</script>

<script type="text/javascript">
function formatIsoDate(celldate, options, rowObject) {
  var newDate = new Date();
  newDate.setTime(Date.parse(celldate));
  return newDate.toLocaleFormat('%H:%M - %d.%m.%Y');
}
</script>

      <!--  Start MAIN -->
      <div class="main">
        <div class="sub_menu">
          <struts:a href="%{roleAddUrl}">
            <struts:text name="role.add"/>
          </struts:a>
        </div>
        
        
        <sjg:grid
          id="gridedittable"
          caption="%{getText('roles')}"
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
    	      name="uuid"
    	      index="uuid"
    	      title="%{getText('role.uuid')}"
    	      width="420"
    	      editable="false"
    	      sortable="true"
    	      hidden="false"
    	      search="false"
    	      resizable="false"
    	      align="center"
    	  />
    	  <sjg:gridColumn
    	      name="technical"
    	      index="technical"
    	      title="%{getText('role.technical')}"
    	      width="420"
    	      editable="false"
    	      sortable="true"
    	      hidden="false"
    	      search="false"
    	      resizable="false"
    	      align="left"
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
    	      width="310"
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