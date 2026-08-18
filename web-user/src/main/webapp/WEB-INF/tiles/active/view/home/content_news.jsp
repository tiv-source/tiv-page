<%@page pageEncoding="utf-8" contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="struts" uri="/struts-tags"%>

    <!-- Anfang der News -->
    <div id="newsContainer">
      <struts:iterator value="newsList" status="newsStatus">

        <struts:url var="newsLink" action="index" namespace="/news/%{uuid}"/>

        <struts:a href="%{newsLink}">
          <div class="newsItem <struts:if test="#newsStatus.odd == true ">newsColor1</struts:if><struts:else>newsColor2</struts:else>">
            <div class="newsInfo">
              <h4><struts:property value="getName(getText('language'))" /></h4>
              <h6><struts:date name="releaseDate" format="dd.MM.yyyy" /></h6>
              <p><struts:property value="getDescription(getText('language'))" /></p>
            </div>
            <div class="newsImpression">
              <img alt="" src="/image/pictureitem/<struts:property value="uuid" />/normal.png" alt="<struts:property value="getName(getText('language'))" />" title="<struts:property value="getName(getText('language'))" />">
            </div>
            <hr>
          </div>
        </struts:a>

      </struts:iterator>
    </div>
    <!-- Ende der News -->