<%@page pageEncoding="utf-8" contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="struts" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>
<%@ taglib prefix="sjg" uri="/struts-jquery-grid-tags"%>

<struts:url var="userAddUrl" action="addForm" namespace="/user" />
<struts:url id="remoteurl" action="table" namespace="/user"/>

<script type="text/javascript">
function formatEditLink(cellvalue, options, rowObject) {
  return "<a href='/admin/user/editForm.html?user="+ cellvalue +"' style='border-style: none;'>" + 
         "<img src='/admin/icons/16x16/pencil.png'/>" + 
         "</a>&nbsp;&nbsp;&nbsp;" + 
         "<a href='/admin/user/deleteForm.html?user="+ cellvalue +"' style='border-style: none;'>" + 
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
          <struts:a href="%{userAddUrl}">
            <struts:text name="user.add"/>
          </struts:a>
        </div>

        <sjg:grid
          id="gridedittable"
          caption="%{getText('users')}"
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
    	      title="%{getText('user.uuid')}"
    	      width="210"
    	      editable="false"
    	      sortable="true"
    	      hidden="false"
    	      search="false"
    	      resizable="false"
    	      align="center"
    	  />
    	  <sjg:gridColumn 
    	      name="username"
    	      index="username"
    	      title="%{getText('user.username')}"
    	      width="210"
    	      editable="false"
    	      sortable="true"
    	      hidden="false"
    	      search="false"
    	      resizable="false"
    	      align="left"
    	  />
    	  <sjg:gridColumn 
    	      name="firstname"
    	      index="firstname"
    	      title="%{getText('user.firstname')}"
    	      width="210"
    	      editable="false"
    	      sortable="true"
    	      hidden="false"
    	      search="false"
    	      resizable="false"
    	      align="left"
    	  />
    	  <sjg:gridColumn 
    	      name="lastname"
    	      index="lastname"
    	      title="%{getText('user.lastname')}"
    	      width="210"
    	      editable="false"
    	      sortable="true"
    	      hidden="false"
    	      search="false"
    	      resizable="false"
    	      align="center"
    	  />
    	  <sjg:gridColumn 
    	      name="email"
    	      index="email"
    	      title="%{getText('user.email')}"
    	      width="210"
    	      editable="false"
    	      sortable="true"
    	      hidden="false"
    	      search="false"
    	      resizable="false"
    	      align="center"
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
    	      width="85"
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