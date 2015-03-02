<%@page pageEncoding="utf-8" contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="struts" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>
<%@ taglib prefix="sjg" uri="/struts-jquery-grid-tags"%>

<struts:url var="userAddUrl" action="addForm" namespace="/user" />
<struts:url id="remoteurl" action="table" namespace="/user"/>

<script type="text/javascript">
function formatEditLink(cellvalue, options, rowObject) {
  return "<a href='/admin/user/editForm.html?user="+ cellvalue +"'>edit</a> | <a href='/admin/user/deleteForm.html?user="+ cellvalue +"'>delete</a>";
}
</script>


      <!--  Start MAIN -->
      <div class="main">
        <div class="sub_menu">
          <struts:a href="%{userAddUrl}">Add User</struts:a>
        </div>

        
        
        
        <sjg:grid
          id="gridedittable"
          caption="User"
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
    	
    	  <sjg:gridColumn name="uuid"              index="uuid"             title="UUID"              width="270" editable="false" sortable="true"  hidden="false" search="false" resizable="false" align="center" />
    	  <sjg:gridColumn name="username"          index="username"         title="Username"          width="190" editable="false" sortable="false" hidden="false" search="false" resizable="false" align="left" />
    	  <sjg:gridColumn name="firstname"         index="firstname"        title="Firstname"         width="200" editable="false" sortable="true"  hidden="false" search="false" resizable="false" align="left" />
    	  <sjg:gridColumn name="lastname"          index="lastname"         title="Lastname"          width="250" editable="false" sortable="true"  hidden="false" search="false" resizable="false" align="center" />
    	  <sjg:gridColumn name="email"             index="email"            title="EMail"             width="250" editable="false" sortable="true"  hidden="false" search="false" resizable="false" align="center" />
    	  <sjg:gridColumn name="uuid"              index="editbar"          title="Actions"           width="110" editable="false" sortable="false" hidden="false" search="false" resizable="false" align="right" formatter="formatEditLink" />    	
        </sjg:grid>

        <div style="width:100%; margin: 10px;">&nbsp;</div>
    
      </div>
      <!--  Ende MAIN -->