<%@page pageEncoding="utf-8" contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="struts" uri="/struts-tags"%>

  <struts:if test="lecturer.pictureOnPage">
    <div id="sitePicture">
      <img alt="" src="/image/pictureitem/<struts:property value="lecturer.uuid" />/large.png">
    </div>
  </struts:if>

  <!-- Content Anfang -->
  <div id=content>

    <struts:property escapeHtml="false" value="lecturer.getContent(getText('language'))" />

  <struts:if test="lecturer.courses.size() > 0">
    <struts:iterator value="lecturer.courses" status="courseStatus">
      <struts:url var="courseLink" action="index" namespace="/course/%{uuid}"/>

      <div class="informations">
        <struts:a href="%{courseLink}">
          <div class="information">
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
              <h3 class="courseDate">Beginnt <struts:date name="startDate" nice="true" /></h3>
            </struts:if>
            <struts:else>
              <struts:if test="getProperty('course.list.startDate.normal') == 'true'">
                <h3 class="courseDate">Beginnt am <struts:date name="startDate" format="dd.MM.yyyy"/> um <struts:date name="startDate" format="HH:mm" /></h3>
              </struts:if>
            </struts:else>

            <struts:if test="getProperty('course.list.description') == 'true'">
              <p class="courseDescription"><struts:property value="getDescription(getText('language'))" /></p>
            </struts:if>

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
          </div>
        </struts:a>

        <struts:a href="%{courseLink}">
          <div class="impression">
            <img src="/image/pictureitem/<struts:property value="uuid" />/large.png" alt="<struts:property value="getName(getText('language'))" />" title="<struts:property value="getName(getText('language'))" />">
          </div>
        </struts:a>

        <hr>
      </div>
      

    </struts:iterator>
  </struts:if>
  <struts:else>
    <div class="noCourse">Zur Zeit liegen für diesen Dozenten keine Kurs-Termine vor.</div>
  </struts:else>

    <hr>
  </div>
  <!-- Content Ende -->

