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


      <!--  Start MAIN -->
      <div class="main">
        <div class="sub_menu">
          <struts:a href="%{roleAddUrl}">Add Role</struts:a>
        </div>
        
        
        <sjg:grid
          id="gridedittable"
          caption="Role"
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
    	
    	  <sjg:gridColumn name="uuid"        index="uuid"        title="UUID"        width="400" editable="false" sortable="true"  hidden="false" search="false" resizable="false" align="center" />
    	  <sjg:gridColumn name="technical"   index="technical"   title="Technical"   width="730" editable="false" sortable="true"  hidden="false" search="false" resizable="false" align="left" />
    	  <sjg:gridColumn name="uuid"        index="editbar"     title="Actions"     width="155" editable="false" sortable="false" hidden="false" search="false" resizable="false" align="right" formatter="formatEditLink" />    	
        </sjg:grid>

        <div style="width:100%; margin: 10px;">&nbsp;</div>
    
      </div>
      <!--  Ende MAIN -->