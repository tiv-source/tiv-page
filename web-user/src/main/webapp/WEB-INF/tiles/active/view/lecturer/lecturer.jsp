<%@page pageEncoding="utf-8" contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="struts" uri="/struts-tags"%>

  <struts:if test="page.pictureOnPage">
    <div id="sitePicture">
      <img alt="" src="/image/pictureitem/<struts:property value="page.uuid" />/large.png">
    </div>
  </struts:if>

  <!-- Content Anfang -->
  <div id=content>

    <struts:property escapeHtml="false" value="page.getContent(getText('language'))" />
    
  <struts:iterator value="list" status="lecturerStatus">
    <struts:url var="lecturerLink" action="index" namespace="/lecturer/%{technical}"/>
    <div class="lecturers <struts:if test="#lecturerStatus.odd == true ">color1</struts:if><struts:else>color2</struts:else>">
      <struts:a href="%{lecturerLink}">
        <div class="lecturer">
          <h4><struts:property value="getName(getText('language'))" /></h4>
          <p><struts:property value="address.street" /></p>
          <p><struts:property value="address.zip" /> <struts:property value="address.city" /></p>
        </div>

        <div class="impression">
          <img src="/image/pictureitem/<struts:property value="uuid" />/large.png" alt="<struts:property value="getName(getText('language'))" />" title="<struts:property value="getName(getText('language'))" />">
        </div>
      </struts:a>						
      <hr>
    </div>
    
  </struts:iterator>

    <hr>
  </div>
  <!-- Content Ende -->

