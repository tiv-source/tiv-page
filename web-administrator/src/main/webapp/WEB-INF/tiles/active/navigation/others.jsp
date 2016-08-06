<%@page pageEncoding="utf-8" contentType="text/html; charset=utf-8"%>
<%@ taglib prefix="struts" uri="/struts-tags"%>

<struts:url var="publicUrl"            action="index" namespace="/others" />
<struts:url var="locationsUrl"         action="index" namespace="/locations" />
<struts:url var="maintenanceUrl"       action="index" namespace="/maintenance" />
<struts:url var="systemtUrl"           action="index" namespace="/system" />

<struts:url var="pageUrl"              action="index" namespace="/others/page" />
<struts:url var="messageUrl"           action="index" namespace="/others/message" />
<struts:url var="vacancyUrl"           action="index" namespace="/others/vacancy" />
<struts:url var="galleryUrl"           action="index" namespace="/others/gallery" />
<struts:url var="pictureUrl"           action="index" namespace="/others/picture" />
<struts:url var="newsUrl"              action="index" namespace="/others/news" />
<struts:url var="manualUrl"            action="index" namespace="/others/manual" />

   	  <div class="nav">
   	    <div id="navi">
   	      <ul id="orientation">
   	        <li class="activlink1"><struts:text name="navigation.category.others"/></li>
   	        <li><struts:a href="%{locationsUrl}"><struts:text name="navigation.category.locations"/></struts:a></li>
   	        <li><struts:a href="%{maintenanceUrl}"><struts:text name="navigation.category.maintenance"/></struts:a></li>
   	        <li><struts:a href="%{systemtUrl}"><struts:text name="navigation.category.system"/></struts:a></li>
   	      </ul>

          <div id="button_bar">

            <struts:a href="%{pageUrl}" title="%{getText('navigation.pages')}">
              <div class="button fl_stop">
                <img src="/admin/buttons/tiv_page_button_page.png" alt="<struts:text name="navigation.pages"/>">
                <p><struts:text name="navigation.pages"/></p>
              </div>
            </struts:a>

            <struts:a href="%{messageUrl}" title="%{getText('navigation.messages')}">
              <div class="button">
                <img src="/admin/buttons/tiv_page_button_message.png" alt="<struts:text name="navigation.messages"/>">
                <p><struts:text name="navigation.messages"/></p>
              </div>
            </struts:a>

            <struts:a href="%{vacancyUrl}" title="%{getText('navigation.vacancies')}">
              <div class="button">
                <img src="/admin/buttons/tiv_page_button_vacancy.png" alt="<struts:text name="navigation.vacancies"/>">
                <p><struts:text name="navigation.vacancies"/></p>
              </div>
            </struts:a>

            <struts:a href="%{galleryUrl}" title="%{getText('navigation.galleries')}">
              <div class="button">
                <img src="/admin/buttons/tiv_page_button_gallery.png" alt="<struts:text name="navigation.galleries"/>">
                <p><struts:text name="navigation.galleries"/></p>
              </div>
            </struts:a>

            <struts:a href="%{pictureUrl}" title="%{getText('navigation.pictures')}">
              <div class="button">
                <img src="/admin/buttons/tiv_page_button_picture.png" alt="<struts:text name="navigation.pictures"/>">
                <p><struts:text name="navigation.pictures"/></p>
              </div>
            </struts:a>

            <struts:a href="%{newsUrl}" title="%{getText('navigation.news')}">
              <div class="button">
                <img src="/admin/buttons/tiv_page_button_news.png" alt="<struts:text name="navigation.news"/>">
                <p><struts:text name="navigation.news"/></p>
              </div>
            </struts:a>

            <struts:a href="%{manualUrl}" title="%{getText('navigation.manuals')}">
              <div class="button">
                <img src="/admin/buttons/tiv_page_button_manual.png" alt="<struts:text name="navigation.manuals"/>">
                <p><struts:text name="navigation.manuals"/></p>
              </div>
            </struts:a>

            <hr>

         </div>
       </div>
     </div>