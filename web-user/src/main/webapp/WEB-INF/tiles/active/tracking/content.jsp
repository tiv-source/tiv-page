<%@page pageEncoding="utf-8" contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="struts" uri="/struts-tags"%>

<struts:if test="getProperty('piwik.enabled') == 'true'">
<!-- Piwik -->
<script type="text/javascript">
  var _paq = _paq || [];
  _paq.push(["setDomains", [<struts:property escapeHtml="false" value="getProperty('piwik.domains')"/>]]);
  _paq.push(['trackPageView']);
  _paq.push(['enableLinkTracking']);
  (function() {
    var u="<struts:property value="getProperty('piwik.url')"/>";
    _paq.push(['setTrackerUrl', u+'piwik.php']);
    _paq.push(['setSiteId', <struts:property value="getProperty('piwik.siteId')"/>]);
    var d=document, g=d.createElement('script'), s=d.getElementsByTagName('script')[0];
    g.type='text/javascript'; g.async=true; g.defer=true; g.src=u+'piwik.js'; s.parentNode.insertBefore(g,s);
  })();
</script>
<noscript><p><img src="<struts:property value="getProperty('piwik.url')"/>piwik.php?idsite=<struts:property value="getProperty('piwik.siteId')"/>" style="border:0;" alt="" /></p></noscript>
<!-- End Piwik Code -->
</struts:if>

