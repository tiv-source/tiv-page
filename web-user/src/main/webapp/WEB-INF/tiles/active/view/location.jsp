<%@page pageEncoding="utf-8" contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="struts" uri="/struts-tags"%>


    <struts:property escape="false" value="page.getContent(getText('language'))" />
    
    <struts:iterator value="list" status="locationStatus">
        <div>
            <div><img src="http://staticmap.openstreetmap.de/staticmap.php?center=<struts:property value="latitude" />,<struts:property value="longitude" />&zoom=18&size=280x200&maptype=mapnik&markers=<struts:property value="latitude" />,<struts:property value="longitude" />,lightblue" /></div>
            <div><struts:property value="getName(getText('language'))" /></div>
            <div><struts:property value="address.city" /></div>
            <div><struts:property value="address.zip" /></div>
        </div>
    </struts:iterator>



