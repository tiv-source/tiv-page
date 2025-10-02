<%@page pageEncoding="utf-8" contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="struts" uri="/struts-tags"%>

  <struts:if test="page.pictureOnPage">
    <div id="sitePicture">
      <img alt="" src="/pictures/FULL/<struts:property value="page.picture.pictureUrls.FULL.url" />">
    </div>
  </struts:if>

  <!-- Content Anfang -->
  <div id=content>

  <struts:property escapeHtml="false" value="page.getContent(getText('language'))" />

      <ul class="companiongroups">
        <li class="activLinkCompanionGroup">Alle Anbieter von A-Z</li>
        <struts:iterator value="companionGroup" status="companionGroupStatus">
          <struts:url var="companionGroupLink" action="index" namespace="/companion/%{technical}"/>
          <li>
            <struts:a href="%{companionGroupLink}">
              <struts:property value="getName(getText('language'))" />
            </struts:a>
          </li>
        </struts:iterator>
      </ul>

  <struts:iterator value="list" status="companionStatus">
      <div class="firm">
        <h4><struts:property value="name" /></h4>
        <div id="firmcontacts1">
          <h6><struts:property value="appendix" /></h6>
          <p><struts:property value="address.street" /></p>
          <p><struts:property value="address.zip" /> <struts:property value="address.city" /></p>
        </div>
        <div id="firmcontacts2">
          <p>
            <img src="<struts:property value="getProperty('project.icon.path')"/>telephone.png" alt="Telefon">
            <struts:property value="contactDetails.telephone" />
          </p>
          <p>
            <img src="<struts:property value="getProperty('project.icon.path')"/>fax.png" alt="Fax">
            <struts:property value="contactDetails.fax" />
          </p>
          <p>
            <img src="<struts:property value="getProperty('project.icon.path')"/>email.png" alt="E-Mail">
            <a href="mailto:<struts:property value="contactDetails.email" />" class="nonbreak2">
              <struts:property value="contactDetails.email" />
            </a>
          </p>
          <p>
            <img src="<struts:property value="getProperty('project.icon.path')"/>world.png" alt="Website">
            <a href="<struts:property value="contactDetails.homepage" />" target="blanc" class="nonbreak2">
              <struts:property value="contactDetails.homepage" />
            </a>
          </p>
          <br>
        </div>
      </div>

  </struts:iterator>



    <hr>
  </div>
  <!-- Content Ende -->
