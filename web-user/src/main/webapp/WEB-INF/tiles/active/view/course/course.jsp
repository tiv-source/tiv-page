<%@page pageEncoding="utf-8" contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="struts" uri="/struts-tags"%>

  <struts:if test="course.pictureOnPage">
    <div id="sitePicture">
      <img alt="" src="/image/pictureitem/<struts:property value="course.uuid" />/large.png">
    </div>
  </struts:if>

  <!-- Content Anfang -->
  <div id="content">
    <div id="courseOverviewContent">
      <struts:property escapeHtml="false" value="course.getContent(getText('language'))" />
    </div>

    <struts:if test="getProperty('course.overview.number') == 'true'">
      <p id="courseOverviewNumber">
        <struts:if test="getProperty('course.overview.number.lable') == 'true'">
          Kursnummer:
        </struts:if>
        <struts:property value="course.number" />
      </p>
    </struts:if>

    <struts:if test="getProperty('course.overview.startDate.nice') == 'true'">
      <h3 id="courseOverviewDate">
        <struts:if test="course.isInPast()">
          Begann
        </struts:if>
        <struts:else>
          Beginnt
        </struts:else>
        <struts:date name="course.startDate" nice="true" />
      </h3>
    </struts:if>
    <struts:else>
      <struts:if test="getProperty('course.overview.startDate.normal') == 'true'">
        <h3 id="courseOverviewDate">
          <struts:if test="course.isInPast()">
            Begann am
          </struts:if>
          <struts:else>
            Beginnt am
          </struts:else>
          <struts:date name="course.startDate" format="dd.MM.yyyy"/> um <struts:date name="course.startDate" format="HH:mm" />
        </h3>
      </struts:if>
    </struts:else>

    <struts:if test="getProperty('course.overview.lecturer') == 'true'">
      <h4 id="courseOverviewLecturer">
        <struts:set var="lecturerListSize" value="course.lecturers.size()" />
        <struts:if test="getProperty('course.overview.lecturer.lable') == 'true'">
          <struts:if test="#lecturerListSize < 2">
            Dozent:
          </struts:if>
          <struts:else>
            Dozenten:
          </struts:else>
        </struts:if>
        <struts:iterator value="course.lecturers" status="lecturerStatus">
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

    <struts:if test="getProperty('course.overview.price') == 'true'">
      <p id="courseOverviewPrice">
        <struts:if test="getProperty('course.overview.price.lable') == 'true'">
          Teilnehmerbetrag:
        </struts:if>
        <struts:property value="getText('{0,number,#,##0.00}',{course.price})"/> &euro;
      </p>
    </struts:if>

    <struts:if test="course.material">
      <p id="courseOverviewMaterial">
        Materialpreis: 
        <struts:property value="getText('{0,number,#,##0.00}',{course.materialPrice})"/> &euro;
      </p>
    </struts:if>

    <struts:if test="getProperty('course.overview.maxStudents') == 'true'">
      <p class="courseOverviewMaxStudents">
        <struts:if test="getProperty('course.overview.maxStudents.lable') == 'true'">
          Maximale Teilnehmerzahl:
        </struts:if>
        <struts:property value="course.maxStudents" />
      </p>
    </struts:if>

    <struts:if test="getProperty('course.overview.minimumAge') == 'true'">
      <p class="courseOverviewMinimumAge">
        <struts:if test="getProperty('course.overview.minimumAge.lable') == 'true'">
          Mindestalter:
        </struts:if>
        <struts:property value="course.minimumAge" /> Jahre
      </p>
    </struts:if>

    <struts:if test="getProperty('course.overview.location') == 'true'">
      <p class="courseOverviewLocation">
        <struts:if test="getProperty('course.overview.location.lable') == 'true'">
          Veranstaltungsort:
        </struts:if>
        <struts:url var="locationLink" action="index" namespace="/location/%{course.location.uuid}"/>
        <struts:a href="%{locationLink}"><struts:property value="course.location.getName(getText('language'))" /></struts:a>
      </p>
    </struts:if>

    <struts:if test="getProperty('course.overview.startDate.date') == 'true'">
      <p class="courseOverviewNormalDate">
        <struts:if test="getProperty('course.overview.startDate.time') == 'true'">
          Beginnt um <struts:date name="course.startDate" format="HH:mm" />
        </struts:if>
        <struts:if test="getProperty('course.overview.startDate.date.preposition') == 'true'">
          am
        </struts:if>
        <struts:date name="course.startDate" format="dd.MM.yyyy" />
      </p>
    </struts:if>

    <p class="courseOverviewStatus">
      <struts:if test="course.status() == 'GREEN'">
        <img
          id="courseOverviewStatusImage"
          src="/public/icon/traffic_light_green.svg"
          alt="<struts:property value="course.status()" />"
          title="<struts:property value="course.status()" />"
        />
      </struts:if>
      <struts:elseif test="course.status() == 'ORANGE'">
        <img
          id="courseOverviewStatusImage"
          src="/public/icon/traffic_light_yellow.svg"
          alt="<struts:property value="course.status()" />"
          title="<struts:property value="course.status()" />"
        />
      </struts:elseif>
      <struts:else>
        <img
          id="courseOverviewStatusImage"
          src="/public/icon/traffic_light_red.svg"
          alt="<struts:property value="course.status()" />"
          title="<struts:property value="course.status()" />"
        />
      </struts:else>
    </p>

    <div id="courseOverviewAppointments">
      <div id="courseOverviewAppointmentsLable">Termine:</div>
      <struts:iterator value="course.units" status="unitStatus">
        <div class="courseOverviewAppointment">
          <div class="courseOverviewAppointmentImage"><img alt="" src="/image/pictureitem/<struts:property value="uuid" />/large.png"></div>
          <p class="courseOverviewAppointmentName"><struts:property value="descriptionMap.DE.name" /></p>
          <p class="courseOverviewAppointmentDate"><struts:date name="pointInTime" format="EEE dd.MM.yyyy"/> <struts:date name="pointInTime" format="HH:mm" /> Uhr - <struts:date name="endDate" format="HH:mm" /> Uhr</p>
        </div>
      </struts:iterator>
    </div>

    <struts:if test="!course.isInPast()">
      <div id="courseRegistration">
        <struts:url var="registrationLink" action="form" namespace="/course/%{course.uuid}"/>
        <struts:if test="!waiting">
          <struts:a id="registrationLink" href="%{registrationLink}">
            Anmelden
          </struts:a>
        </struts:if>
        <struts:else>
          <struts:a id="waitingLink" href="%{registrationLink}">
            In Warteliste eintragen
          </struts:a>
        </struts:else>
        
      </div>
    </struts:if>

    <hr>
  </div>
  <!-- Content Ende -->

