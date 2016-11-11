<%@ taglib prefix="struts" uri="/struts-tags"%>

<struts:set var="revenue" value="event.price * reservation.quantity" />

<!-- Piwik -->
<script type="text/javascript">
  var _paq = _paq || [];
  _paq.push(['trackPageView']);
  _paq.push(['enableLinkTracking']);
  (function() {
    var u="<struts:property value="getProperty('piwik.url')"/>";
    _paq.push(['setTrackerUrl', u+'piwik.php']);
    _paq.push(['setSiteId', <struts:property value="getProperty('piwik.siteId')"/>]);
    _paq.push(['trackGoal', <struts:property value="event.piwikGoal" />, <struts:property value="#revenue" />]);
    var d=document, g=d.createElement('script'), s=d.getElementsByTagName('script')[0];
    g.type='text/javascript'; g.async=true; g.defer=true; g.src=u+'piwik.js'; s.parentNode.insertBefore(g,s);
  })();
</script>
<noscript><p><img src="<struts:property value="getProperty('piwik.url')"/>piwik.php?idsite=<struts:property value="getProperty('piwik.siteId')"/>;idgoal=<struts:property value="event.piwikGoal" />;revenue=<struts:property value="#revenue" />" style="border:0;" alt="" /></p></noscript>
<!-- End Piwik Code -->