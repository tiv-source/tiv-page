<%@page pageEncoding="utf-8" contentType="text/html; charset=utf-8"%>
<%@ taglib prefix="struts" uri="/struts-tags"%>

<struts:url var="publicUrl"            action="index" namespace="/others" />
<struts:url var="locationsUrl"         action="index" namespace="/locations" />
<struts:url var="maintenanceUrl"       action="index" namespace="/maintenance" />
<struts:url var="systemtUrl"           action="index" namespace="/system" />

<struts:url var="pageUrl"              action="index" namespace="/others/page" />
<struts:url var="menuEntryUrl"         action="menuentry" namespace="/others" />

<struts:url var="appointmentUrl"       action="index" namespace="/others/appointment" />
<struts:url var="messageUrl"           action="index" namespace="/others/message" />
<struts:url var="vacancyUrl"           action="index" namespace="/others/vacancy" />
<struts:url var="galleryUrl"           action="index" namespace="/others/gallery" />
<struts:url var="pictureUrl"           action="index" namespace="/others/picture" />
<struts:url var="newsUrl"              action="index" namespace="/others/news" />
<struts:url var="manualUrl"            action="index" namespace="/others/manual" />
<struts:url var="companionGroupUrl"    action="index" namespace="/others/companiongroup" />
<struts:url var="companionUrl"         action="index" namespace="/others/companion" />
<struts:url var="sliderUrl"            action="index" namespace="/others/slider" />
<struts:url var="subsumptionUrl"       action="index" namespace="/others/subsumption" />
<struts:url var="pdfUrl"               action="index" namespace="/others/pdf" />
<struts:url var="exhibitionUrl"        action="index" namespace="/others/exhibition" />


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
                <br />
                <p class="ellipsis"><struts:text name="navigation.pages"/></p>
              </div>
            </struts:a>


            <struts:a href="%{menuEntryUrl}" title="%{getText('navigation.menuEntries')}">
              <div class="button">
                <img src="/admin/buttons/tiv_page_button_page.png" alt="<struts:text name="navigation.menuEntries"/>">
                <br />
                <p class="ellipsis"><struts:text name="navigation.menuEntries"/></p>
              </div>
            </struts:a>

            <struts:a href="%{subsumptionUrl}" title="%{getText('navigation.subsumptions')}">
              <div class="button">
                <img src="/admin/buttons/tiv_page_button_manual.png" alt="<struts:text name="navigation.subsumptions"/>">
                <br />
                <p class="ellipsis"><struts:text name="navigation.subsumptions"/></p>
              </div>
            </struts:a>



            <struts:a href="%{messageUrl}" title="%{getText('navigation.messages')}">
              <div class="button">
                <img src="/admin/buttons/tiv_page_button_message.png" alt="<struts:text name="navigation.messages"/>">
                <br />
                <p class="ellipsis"><struts:text name="navigation.messages"/></p>
              </div>
            </struts:a>

            <struts:a href="%{galleryUrl}" title="%{getText('navigation.galleries')}">
              <div class="button">
                <img src="/admin/buttons/tiv_page_button_gallery.png" alt="<struts:text name="navigation.galleries"/>">
                <br />
                <p class="ellipsis"><struts:text name="navigation.galleries"/></p>
              </div>
            </struts:a>

            <struts:a href="%{pictureUrl}" title="%{getText('navigation.pictures')}">
              <div class="button">
                <img src="/admin/buttons/tiv_page_button_picture.png" alt="<struts:text name="navigation.pictures"/>">
                <br />
                <p class="ellipsis"><struts:text name="navigation.pictures"/></p>
              </div>
            </struts:a>

            <struts:if test="getProperty('module.appointment')">
              <struts:a href="%{appointmentUrl}" title="%{getText('navigation.appointments')}">
                <div class="button">
                  <img src="/admin/buttons/tiv_page_button_message.png" alt="<struts:text name="navigation.appointments"/>">
                  <br />
                  <p class="ellipsis"><struts:text name="navigation.appointments"/></p>
                </div>
              </struts:a>
            </struts:if>

            <struts:if test="getProperty('module.vacancy')">
              <struts:a href="%{vacancyUrl}" title="%{getText('navigation.vacancies')}">
                <div class="button">
                  <img src="/admin/buttons/tiv_page_button_vacancy.png" alt="<struts:text name="navigation.vacancies"/>">
                  <br />
                  <p class="ellipsis"><struts:text name="navigation.vacancies"/></p>
                </div>
              </struts:a>
            </struts:if>

            <struts:if test="getProperty('module.news')">
              <struts:a href="%{newsUrl}" title="%{getText('navigation.news')}">
                <div class="button">
                  <img src="/admin/buttons/tiv_page_button_news.png" alt="<struts:text name="navigation.news"/>">
                  <br />
                  <p class="ellipsis"><struts:text name="navigation.news"/></p>
                </div>
              </struts:a>
            </struts:if>

            <struts:if test="getProperty('module.manual')">
              <struts:a href="%{manualUrl}" title="%{getText('navigation.manuals')}">
                <div class="button">
                  <img src="/admin/buttons/tiv_page_button_manual.png" alt="<struts:text name="navigation.manuals"/>">
                  <br />
                  <p class="ellipsis"><struts:text name="navigation.manuals"/></p>
                </div>
              </struts:a>
            </struts:if>

            <struts:if test="getProperty('module.companion')">
              <struts:a href="%{companionUrl}" title="%{getText('navigation.companions')}">
                <div class="button">
                  <img src="/admin/buttons/tiv_page_button_manual.png" alt="<struts:text name="navigation.companions"/>">
                  <br />
                  <p class="ellipsis"><struts:text name="navigation.companions"/></p>
                </div>
              </struts:a>
            </struts:if>

            <struts:if test="getProperty('module.companion')">
              <struts:a href="%{companionGroupUrl}" title="%{getText('navigation.companionGroups')}">
                <div class="button">
                  <img src="/admin/buttons/tiv_page_button_manual.png" alt="<struts:text name="navigation.companionGroups"/>">
                  <br />
                  <p class="ellipsis"><struts:text name="navigation.companionGroups"/></p>
                </div>
              </struts:a>
            </struts:if>

            <struts:if test="getProperty('module.slider')">
              <struts:a href="%{sliderUrl}" title="%{getText('navigation.slider')}">
                <div class="button">
                  <img src="/admin/buttons/tiv_page_button_manual.png" alt="<struts:text name="navigation.slider"/>">
                  <br />
                  <p class="ellipsis"><struts:text name="navigation.slider"/></p>
                </div>
              </struts:a>
            </struts:if>

            <struts:if test="getProperty('module.pdf')">
              <struts:a href="%{pdfUrl}" title="%{getText('navigation.pdf')}">
                <div class="button">
                  <img src="/admin/buttons/tiv_page_button_manual.png" alt="<struts:text name="navigation.pdf"/>">
                  <br />
                  <p class="ellipsis"><struts:text name="navigation.pdf"/></p>
                </div>
              </struts:a>
            </struts:if>

            <struts:if test="getProperty('module.exhibition')">
              <struts:a href="%{exhibitionUrl}" title="%{getText('navigation.exhibition')}">
                <div class="button">
                  <img src="/admin/buttons/tiv_page_button_manual.png" alt="<struts:text name="navigation.exhibition"/>">
                  <br />
                  <p class="ellipsis"><struts:text name="navigation.exhibition"/></p>
                </div>
              </struts:a>
            </struts:if>

            <hr>

         </div>
       </div>
     </div>