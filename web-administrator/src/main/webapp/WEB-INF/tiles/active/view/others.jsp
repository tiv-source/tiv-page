<%@page pageEncoding="utf-8" contentType="text/html; charset=utf-8"%>
<%@ taglib prefix="struts" uri="/struts-tags"%>

<struts:url var="pageUrl"              action="index" namespace="/others/page" />
<struts:url var="appointmentUrl"       action="index" namespace="/others/appointment" />
<struts:url var="messageUrl"           action="index" namespace="/others/message" />
<struts:url var="vacancyUrl"           action="index" namespace="/others/vacancy" />
<struts:url var="galleryUrl"           action="index" namespace="/others/gallery" />
<struts:url var="pictureUrl"           action="index" namespace="/others/picture" />
<struts:url var="newsUrl"              action="index" namespace="/others/news" />
<struts:url var="manualUrl"            action="index" namespace="/others/manual" />
<struts:url var="companionGroupUrl"    action="index" namespace="/others/companiongroup" />
<struts:url var="companionUrl"         action="index" namespace="/others/companion" />

      <div id="title">
        <h5><struts:text name="navigation.category.others"/></h5>
      </div>
      
      <struts:a href="%{pageUrl}" title="%{getText('navigation.pages')}">
        <div class="buttoninfo typ1 fl_stop">
          <img src="/admin/buttons/tiv_page_button_page.png" alt="<struts:text name="navigation.pages.description"/>">
          <h5><struts:text name="navigation.pages"/></h5>
          <p><struts:text name="navigation.pages.description"/></p>
        </div>
      </struts:a>

      <struts:a href="%{messageUrl}" title="%{getText('navigation.messages')}">
        <div class="buttoninfo typ2">
          <img src="/admin/buttons/tiv_page_button_message.png" alt="<struts:text name="navigation.messages.description"/>">
          <h5><struts:text name="navigation.messages"/></h5>
          <p><struts:text name="navigation.messages.description"/></p>
        </div>
      </struts:a>

      <struts:a href="%{galleryUrl}" title="%{getText('navigation.galleries')}">
        <div class="buttoninfo typ1">
          <img src="/admin/buttons/tiv_page_button_gallery.png" alt="<struts:text name="navigation.galleries.description"/>">
          <h5><struts:text name="navigation.galleries"/></h5>
          <p><struts:text name="navigation.galleries.description"/></p>
        </div>
      </struts:a>

      <struts:a href="%{pictureUrl}" title="%{getText('navigation.pictures')}">
        <div class="buttoninfo typ2">
          <img src="/admin/buttons/tiv_page_button_picture.png" alt="<struts:text name="navigation.pictures.description"/>">
          <h5><struts:text name="navigation.pictures"/></h5>
          <p><struts:text name="navigation.pictures.description"/></p>
        </div>
      </struts:a>

      <struts:a href="%{appointmentUrl}" title="%{getText('navigation.appointments')}">
        <div class="buttoninfo typ1">
          <img src="/admin/buttons/tiv_page_button_vacancy.png" alt="<struts:text name="navigation.appointments.description"/>">
          <h5><struts:text name="navigation.appointments"/></h5>
          <p><struts:text name="navigation.appointments.description"/></p>
        </div>
      </struts:a>


      <struts:if test="getProperty('module.vacancy')">
        <struts:a href="%{vacancyUrl}" title="%{getText('navigation.vacancies')}">
          <div class="buttoninfo typ2">
            <img src="/admin/buttons/tiv_page_button_vacancy.png" alt="<struts:text name="navigation.vacancies.description"/>">
            <h5><struts:text name="navigation.vacancies"/></h5>
            <p><struts:text name="navigation.vacancies.description"/></p>
          </div>
        </struts:a>
      </struts:if>


      <struts:if test="getProperty('module.news')">
        <struts:a href="%{newsUrl}" title="%{getText('navigation.news')}">
          <div class="buttoninfo typ1">
            <img src="/admin/buttons/tiv_page_button_news.png" alt="<struts:text name="navigation.news.description"/>">
            <h5><struts:text name="navigation.news"/></h5>
            <p><struts:text name="navigation.news.description"/></p>
          </div>
        </struts:a>
      </struts:if>

      <struts:if test="getProperty('module.manual')">
        <struts:a href="%{manualUrl}" title="%{getText('navigation.manuals')}">
          <div class="buttoninfo typ2">
            <img src="/admin/buttons/tiv_page_button_manual.png" alt="<struts:text name="navigation.manuals.description"/>">
            <h5><struts:text name="navigation.manuals"/></h5>
            <p><struts:text name="navigation.manuals.description"/></p>
          </div>
        </struts:a>
      </struts:if>

      <struts:if test="getProperty('module.companion')">
        <struts:a href="%{companionUrl}" title="%{getText('navigation.companions')}">
          <div class="buttoninfo typ1">
            <img src="/admin/buttons/tiv_page_button_manual.png" alt="<struts:text name="navigation.companions.description"/>">
            <h5><struts:text name="navigation.companions"/></h5>
            <p><struts:text name="navigation.companions.description"/></p>
          </div>
        </struts:a>
      </struts:if>

      <struts:if test="getProperty('module.companion')">
        <struts:a href="%{companionGroupUrl}" title="%{getText('navigation.companionGroups')}">
          <div class="buttoninfo typ2">
            <img src="/admin/buttons/tiv_page_button_manual.png" alt="<struts:text name="navigation.companionGroups.description"/>">
            <h5><struts:text name="navigation.companionGroups"/></h5>
            <p><struts:text name="navigation.companionGroups.description"/></p>
          </div>
        </struts:a>
      </struts:if>



      <hr>

