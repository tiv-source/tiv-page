<%@page pageEncoding="utf-8" contentType="text/html; charset=utf-8"%>
<%@ taglib prefix="struts" uri="/struts-tags"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>

<struts:url var="homeUrl"              action="index" namespace="/" />

<struts:url var="pageUrl"              action="index" namespace="/page" />
<struts:url var="locationUrl"          action="index" namespace="/location" />
<struts:url var="eventUrl"             action="index" namespace="/event" />
<struts:url var="messageUrl"           action="index" namespace="/message" />
<struts:url var="vacancyUrl"           action="index" namespace="/vacancy" />
<struts:url var="galleryUrl"           action="index" namespace="/gallery" />
<struts:url var="pictureUrl"           action="index" namespace="/picture" />
<struts:url var="queueUrl"             action="queue" namespace="/reservation" />
<struts:url var="newsUrl"              action="index" namespace="/news" />

<struts:url var="userUrl"              action="index" namespace="/user" />
<struts:url var="roleUrl"              action="index" namespace="/role" />
<struts:url var="propertyUrl"          action="index" namespace="/property" />

<struts:url var="backupUrl"            action="index" namespace="/backup" />
<struts:url var="restoreUrl"           action="index" namespace="/restore" />
<struts:url var="filesUrl"             action="index" namespace="/files" />


      <div class="nav">
        <div id="navigation">

          <div class="navCategorySelection" >
            <div class="wrapper">
              <ul id="navCategories">
                <li id="category_public"     >Public</li>
                <li id="category_statistics" >Statistics</li>
                <li id="category_maintenance">Maintenance</li>
                <li id="category_system"     >System</li>
              </ul>
            </div>
          </div>

          <div id="navGroupPublic" class="navGroup">
            <div class="wrapper">
              <ul>
                <li>
                  <struts:a href="%{pageUrl}" title="%{getText('navigation.pages')}" cssClass="navItem navPage">
                    <span class="navIcon page" style="background-image: url(/admin/buttons/tiv_page_button_page.png);">&nbsp;</span>
                    <span><struts:text name="navigation.pages"/></span>
                  </struts:a>
                </li>
                <li>
                  <struts:a href="%{locationUrl}" title="%{getText('navigation.locations')}" cssClass="navItem navLocation">
                    <span class="navIcon location" style="background-image: url(/admin/buttons/tiv_page_button_location.png);">&nbsp;</span>
                    <span><struts:text name="navigation.locations"/></span>
                  </struts:a>
                </li>
                <li>
                  <struts:a href="%{eventUrl}" title="%{getText('navigation.events')}" cssClass="navItem navEvent">
                    <span class="navIcon event" style="background-image: url(/admin/buttons/tiv_page_button_event.png);">&nbsp;</span>
                    <span><struts:text name="navigation.events"/></span>
                  </struts:a>
                </li>
                <li>
                  <struts:a href="%{messageUrl}" title="%{getText('navigation.messages')}" cssClass="navItem navEvent">
                    <span class="navIcon event" style="background-image: url(/admin/buttons/tiv_page_button_message.png);">&nbsp;</span>
                    <span><struts:text name="navigation.messages"/></span>
                  </struts:a>
                </li>
                <li>
                  <struts:a href="%{vacancyUrl}" title="%{getText('navigation.vacancies')}" cssClass="navItem navEvent">
                    <span class="navIcon event" style="background-image: url(/admin/buttons/tiv_page_button_vacancy.png);">&nbsp;</span>
                    <span><struts:text name="navigation.vacancies"/></span>
                  </struts:a>
                </li>
                <li>
                  <struts:a href="%{galleryUrl}" title="%{getText('navigation.galleries')}" cssClass="navItem navEvent">
                    <span class="navIcon event" style="background-image: url(/admin/buttons/tiv_page_button_gallery.png);">&nbsp;</span>
                    <span><struts:text name="navigation.galleries"/></span>
                  </struts:a>
                </li>
                <li>
                  <struts:a href="%{pictureUrl}" title="%{getText('navigation.pictures')}" cssClass="navItem navEvent">
                    <span class="navIcon event" style="background-image: url(/admin/buttons/tiv_page_button_picture.png);">&nbsp;</span>
                    <span><struts:text name="navigation.pictures"/></span>
                  </struts:a>
                </li>
                <li>
                  <struts:a href="%{queueUrl}" title="%{getText('navigation.reservation')}" cssClass="navItem navReservation">
                    <span class="navIcon event" style="background-image: url(/admin/buttons/tiv_page_button_reservation.png);">&nbsp;</span>
                    <span><struts:text name="navigation.reservation"/></span>
                  </struts:a>
                </li>
                <li>
                  <struts:a href="%{newsUrl}" title="%{getText('navigation.news')}" cssClass="navItem navNews">
                    <span class="navIcon event" style="background-image: url(/admin/buttons/tiv_page_button_news.png);">&nbsp;</span>
                    <span><struts:text name="navigation.news"/></span>
                  </struts:a>
                </li>
              </ul>
            </div>
          </div>

          <div id="navGroupStatistics" class="navGroup">
            <div class="wrapper">
              <ul>
                <li>
                  <struts:a href="%{backupUrl}" title="%{getText('navigation.backup')}" cssClass="navItem navAccessControl">
                    <span class="navIcon backup" style="background-image: url(/admin/buttons/tiv_page_button_backup.png);">&nbsp;</span>
                    <span><struts:text name="navigation.backup"/></span>
                  </struts:a>
                </li>
              </ul>
            </div>
          </div>

          <div id="navGroupSystem" class="navGroup">
            <div class="wrapper">
              <ul>
                <li>
                  <struts:a href="%{userUrl}" title="%{getText('navigation.users')}" cssClass="navItem navSystemUser">
                    <span class="navIcon systemuser" style="background-image: url(/admin/buttons/tiv_page_button_user.png;">&nbsp;</span>
                    <span><struts:text name="navigation.users"/></span>
                  </struts:a>
                </li>
                <li>
                  <struts:a href="%{roleUrl}" title="%{getText('navigation.roles')}" cssClass="navItem navSystemRole">
                    <span class="navIcon systemrole" style="background-image: url(/admin/buttons/tiv_page_button_role.png);">&nbsp;</span>
                    <span><struts:text name="navigation.roles"/></span>
                  </struts:a >
                </li>
                <li>
                  <struts:a href="%{propertyUrl}" title="%{getText('navigation.properties')}" cssClass="navItem navSystemProperties">
                    <span class="navIcon system properties" style="background-image: url(/admin/buttons/tiv_page_button_properties.png);">&nbsp;</span>
                    <span><struts:text name="navigation.properties"/></span>
                  </struts:a >
                </li>

              </ul>
            </div>
          </div>

          <div id="navGroupMaintenance" class="navGroup">
            <div class="wrapper">
              <ul>
                <li>
                  <struts:a href="%{backupUrl}" title="%{getText('navigation.backup')}" cssClass="navItem navAccessControl">
                    <span class="navIcon backup" style="background-image: url(/admin/buttons/tiv_page_button_backup.png);">&nbsp;</span>
                    <span><struts:text name="navigation.backup"/></span>
                  </struts:a>
                </li>
                <li>
                  <struts:a href="%{restoreUrl}" title="%{getText('navigation.restore')}" cssClass="navItem navAccessControl">
                    <span class="navIcon restore" style="background-image: url(/admin/buttons/tiv_page_button_restore.png);">&nbsp;</span>
                    <span><struts:text name="navigation.restore"/></span>
                  </struts:a>
                </li>
                <li>
                  <struts:a href="%{filesUrl}" title="%{getText('navigation.files')}" cssClass="navItem navAccessControl">
                    <span class="navIcon accesscontrol" style="background-image: url(/admin/buttons/tiv_page_button_file.png);">&nbsp;</span>
                    <span><struts:text name="navigation.files"/></span>
                  </struts:a>
                </li>
              </ul>
            </div>
          </div>

          <div id="navCap"></div>
        </div>
      </div>

