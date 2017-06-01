<%@page pageEncoding="utf-8" contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="struts" uri="/struts-tags"%>

  <struts:if test="page.pictureOnPage">
    <div>
      <img alt="" src="/pictures/FULL/<struts:property value="page.picture.pictureUrls.FULL.url" />" style="width: 100%;">
    </div>
  </struts:if>

  <div id="feedbackDescription">Freundlichkeit</div>
  <div id="feedback" class="feedbackStars"></div>

<script type="text/javascript">
$('#feedback').raty({
	scoreName: 'entity.score',
	path: '/public/raty/images',
	number: 7,
	hints: ['na ja', 'geht so', 'geht', 'gut', 'super', 'super gut', 'echt super gut'],
	score: 0
});
</script>




