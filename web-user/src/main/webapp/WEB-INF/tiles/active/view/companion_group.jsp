<%@page pageEncoding="utf-8" contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="struts" uri="/struts-tags"%>

  <struts:if test="companionGroup.pictureOnPage">
    <div id="sitePicture">
      <img alt="" src="/pictures/FULL/<struts:property value="companionGroup.picture.pictureUrls.FULL.url" />" style="width: 100%;">
    </div>
  </struts:if>

  <struts:property escapeHtml="false" value="page.getContent(getText('language'))" />

      <ul class="companiongroups">
        <struts:url var="companionGroupLinkBack" action="index" namespace="/companion"/>
        <li>
          <struts:a href="%{companionGroupLinkBack}">
            Alle Anbieter von A-Z
          </struts:a>
        </li>
        <struts:iterator value="groupList" status="groupListStatus">
          <struts:url var="companionGroupLink" action="index" namespace="/companion/%{technical}"/>
          <struts:if test="companionGroup.technical == technical">
            <li class="activLinkCompanionGroup">
              <struts:property value="getName(getText('language'))" />
            </li>
          </struts:if>
          <struts:else>
            <li>
              <struts:a href="%{companionGroupLink}">
                <struts:property value="getName(getText('language'))" />
              </struts:a>
            </li>
          </struts:else>
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
            <a href="<struts:property value="location.contactDetails.homepage" />" target="blanc" class="nonbreak2">
              <struts:property value="location.contactDetails.homepage" />
            </a>
          </p>
          <br>
        </div>
      </div>

  </struts:iterator>

  <hr>
  
