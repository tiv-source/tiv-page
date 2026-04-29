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

  <struts:if test="courses.size() > 0">
    <struts:iterator value="courses" status="courseStatus">
      <struts:url var="courseLink" action="index" namespace="/course/%{uuid}"/>

      <div class="informations">
        <!-- Anfang informations -->
        <div class="information">
          <!-- Anfang information -->
          <struts:a href="%{courseLink}">
            <section class="courseDescription">
              <!-- Anfang section courseDescription -->
              <h2><struts:property value="getName(getText('language'))" /></h2>
              <struts:if test="getProperty('course.list.number') == 'true'">
                <p class="courseNumber">
                  <struts:if test="getProperty('course.list.number.lable') == 'true'">
                    Kursnummer:
                  </struts:if>
                  <struts:property value="number" />
                </p>
              </struts:if>

              <struts:if test="getProperty('course.list.startDate.nice') == 'true'">
                <h3 class="courseDate">
                  <struts:if test="isInPast()">
                    Begann
                  </struts:if>
                  <struts:else>
                    Beginnt
                  </struts:else>
                  <struts:date name="startDate" nice="true" />
                </h3>
              </struts:if>
              <struts:else>
                <struts:if test="getProperty('course.list.startDate.normal') == 'true'">
                  <h3 class="courseDate">
                    <struts:if test="isInPast()">
                      Begann am
                    </struts:if>
                    <struts:else>
                      Beginnt am
                    </struts:else>
                    <struts:date name="startDate" format="dd.MM.yyyy"/> um <struts:date name="startDate" format="HH:mm" />
                  </h3>
                </struts:if>
              </struts:else>

              <struts:if test="getProperty('course.list.description') == 'true'">
                <p class="courseDescription"><struts:property value="getDescription(getText('language'))" /></p>
              </struts:if>
              <!-- Ende section courseDescription -->
            </section>
          </struts:a>
          
          <section class="courseLecturer">
            <!-- Anfang section courseLecturer -->
            <struts:if test="getProperty('course.list.lecturer') == 'true'">
              <h4 class="courseLecturer">
                <struts:set var="lecturerListSize" value="lecturers.size()" />
                <struts:if test="getProperty('course.list.lecturer.lable') == 'true'">
                  <struts:if test="#lecturerListSize < 2">
                    Dozent: 
                  </struts:if>
                  <struts:else>
                    Dozenten: 
                  </struts:else>
                </struts:if>
                <struts:iterator value="lecturers" status="lecturerStatus">
                  <struts:url var="lecturerLink" action="index" namespace="/lecturer/%{technical}"/>
                  <struts:if test="#lecturerStatus.first == true && #lecturerStatus.last == true"><struts:a href="%{lecturerLink}"><struts:property value="descriptionMap.DE.name" /></struts:a></struts:if>
                  <struts:if test="#lecturerStatus.first == true && #lecturerStatus.last == false && #lecturerStatus.index == #lecturerListSize - 2"><struts:a href="%{lecturerLink}"><struts:property value="descriptionMap.DE.name" /></struts:a></struts:if>
                  <struts:if test="#lecturerStatus.first == true && #lecturerStatus.last == false && #lecturerStatus.index != #lecturerListSize - 2"><struts:a href="%{lecturerLink}"><struts:property value="descriptionMap.DE.name" /></struts:a>,</struts:if>
                  <struts:if test="#lecturerStatus.first == false && #lecturerStatus.last == false && #lecturerStatus.index != #lecturerListSize - 2"><struts:a href="%{lecturerLink}"><struts:property value="descriptionMap.DE.name" /></struts:a>,</struts:if>
                  <struts:if test="#lecturerStatus.first == false && #lecturerStatus.last == false && #lecturerStatus.index == #lecturerListSize - 2"><struts:a href="%{lecturerLink}"><struts:property value="descriptionMap.DE.name" /></struts:a></struts:if>
                  <struts:if test="#lecturerStatus.first == false && #lecturerStatus.last == true"> und <struts:a href="%{lecturerLink}"><struts:property value="descriptionMap.DE.name" /></struts:a></struts:if>
                </struts:iterator>
              </h4>
            </struts:if>
            <!-- Ende section courseLecturer -->
          </section>

          <struts:a href="%{courseLink}">
            <section class="courseFacts">
              <!-- Anfang section courseFacts -->
              <struts:if test="getProperty('course.list.startDate.date') == 'true'">
                <p class="courseNormalDate">
                  <struts:if test="getProperty('course.list.startDate.time') == 'true'">
                    Beginnt um <struts:date name="startDate" format="HH:mm" /> 
                  </struts:if>
                  <struts:if test="getProperty('course.list.startDate.date.preposition') == 'true'">
                  am 
                  </struts:if>
                  <struts:date name="startDate" format="dd.MM.yyyy" /> 
                </p>
              </struts:if>

              <struts:if test="getProperty('course.list.price') == 'true'">
                <p class="coursePrice">
                  <struts:if test="getProperty('course.list.price.lable') == 'true'">
                    Teilnehmerbetrag:
                  </struts:if>
                  <struts:property value="getText('{0,number,#,##0.00}',{price})"/> &euro;
                </p>
              </struts:if>

              <struts:if test="getProperty('course.list.maxStudents') == 'true'">
                <p class="courseMaxStudents">
                  <struts:if test="getProperty('course.list.maxStudents.lable') == 'true'">
                    Maximale Teilnehmerzahl:
                  </struts:if>
                  <struts:property value="maxStudents" />
                </p>
              </struts:if>

              <struts:if test="getProperty('course.list.minimumAge') == 'true'">
                <p class="courseOverviewMinimumAge">
                  <struts:if test="getProperty('course.list.minimumAge.lable') == 'true'">
                    Mindestalter:
                  </struts:if>
                  <struts:property value="minimumAge" /> Jahre
                </p>
              </struts:if>

              <!-- Ende section courseFacts -->
            </section>
          </struts:a>

          <struts:if test="getProperty('course.list.location') == 'true'">
            <p class="courseLocation">
              <struts:if test="getProperty('course.list.location.lable') == 'true'">
                Veranstaltungsort:
              </struts:if>
              <struts:url var="locationLink" action="index" namespace="/location/%{location.uuid}"/>
              <struts:a href="%{locationLink}"><struts:property value="location.getName(getText('language'))" /></struts:a>
            </p>
          </struts:if>

          <struts:a href="%{courseLink}">
            <p class="courseStatus">
              <struts:if test="status() == 'GREEN'">
                <img 
                  id="courseStatusImage" 
                  src="/public/icon/traffic_light_green.svg" 
                  alt="<struts:property value="status()" />" 
                  title="<struts:property value="status()" />"
                /> 
              </struts:if>
              <struts:elseif test="status() == 'ORANGE'">
                <img
                  id="courseStatusImage" 
                  src="/public/icon/traffic_light_yellow.svg" 
                  alt="<struts:property value="status()" />" 
                  title="<struts:property value="status()" />"
                />
              </struts:elseif>
              <struts:else>
                <img
                  id="courseStatusImage" 
                  src="/public/icon/traffic_light_red.svg" 
                  alt="<struts:property value="status()" />" 
                  title="<struts:property value="status()" />"
                />
              </struts:else>
            </p>
          </struts:a>

          <!-- Ende information -->
        </div>



        <div class="impression">
          <struts:a href="%{courseLink}">
            <img src="/image/pictureitem/<struts:property value="uuid" />/large.png" alt="<struts:property value="getName(getText('language'))" />" title="<struts:property value="getName(getText('language'))" />">
          </struts:a>
        </div>

        <hr>
        <!-- Ende informations -->
      </div>

    </struts:iterator>
  </struts:if>
  <struts:else>
    <div class="noCourse">Zur Zeit liegen keine Kurs-Termine vor.</div>
  </struts:else>

    <struts:if test="previous != null">
      <struts:url var="previousUrl" escapeAmp="false">
        <struts:param name="pagination" value="%{previous}"/>
      </struts:url>
      <div class="pagination_left">
        <struts:a href="%{previousUrl}">
          <img src="<struts:property value="getProperty('pagination.icon.left')" />" alt="<struts:text name="pagination.previous" />">
          <p><struts:text name="pagination.previous" /></p>
        </struts:a>
      </div>
    </struts:if>

    <struts:if test="next != null">
      <struts:url var="nextUrl" escapeAmp="false">
        <struts:param name="pagination" value="%{next}"/>
      </struts:url>
      <div class="pagination_right">
        <struts:a href="%{nextUrl}">
          <img src="<struts:property value="getProperty('pagination.icon.right')" />" alt="<struts:text name="pagination.next" />">
          <p><struts:text name="pagination.next" /></p>
        </struts:a>
      </div>
      
    </struts:if>

    <hr>
  </div>
  <!-- Content Ende -->
