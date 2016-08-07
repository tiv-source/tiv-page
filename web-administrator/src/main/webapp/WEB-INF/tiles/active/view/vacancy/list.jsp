<%@page pageEncoding="utf-8" contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="struts" uri="/struts-tags" %>

<struts:url var="vacancyAddUrl" action="addForm" namespace="/vacancy" />
<struts:url id="remoteurl" action="table" namespace="/vacancy"/>

<script type="text/javascript">
function formatEditLink(cellvalue, options, rowObject) {
  return "<a href='/admin/vacancy/editForm.html?vacancy="+ cellvalue +"' style='border-style: none;'>" + 
         "<img src='/admin/icons/16x16/pencil.png'/>" + 
         "</a>&nbsp;&nbsp;&nbsp;" + 
         "<a href='/admin/vacancy/deleteForm.html?vacancy="+ cellvalue +"' style='border-style: none;'>" + 
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

      <!--  Start MAIN -->
      <div class="main">
        <div class="sub_menu">
          <struts:a href="%{vacancyAddUrl}">
            <struts:text name="vacancy.add"/>
          </struts:a>
        </div>

        <sjg:grid
          id="gridedittable"
          caption="%{getText('vacancies')}"
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
    	    index="name" 
    	    title="%{getText('vacancy.descriptionMap.DE.name')}" 
    	    width="450" 
    	    editable="false" 
    	    sortable="true" 
    	    hidden="false" 
    	    search="false" 
    	    resizable="false" 
    	    align="left" 
    	  />
    	  <sjg:gridColumn 
    	    name="location.descriptionMap.DE.name" 
    	    index="location" 
    	    title="%{getText('vacancy.location.descriptionMap.DE.name')}" 
    	    width="525" 
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
    	    title="%{getText('visible')}" 
    	    width="80" 
    	    editable="false" 
    	    sortable="true" 
    	    hidden="false" 
    	    search="false" 
    	    resizable="false" 
    	    align="center" 
    	    formatter="formatTrueFalse"
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
    	    width="300" 
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
    	    width="75" 
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