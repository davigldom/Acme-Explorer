<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
	
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">


<p><spring:message code="sponsorship.create" /></p>

<p><spring:message code="sponsorship.required" /></p>

<form:form action="sponsorship/sponsor/edit.do" modelAttribute="sponsorship" method = "post"> 

	<form:hidden path="id"/>
	<form:hidden path="version"/>
	<form:hidden path="sponsor"/>
	<form:hidden path="trip"/>
	<form:hidden path="creditCard" />
	
	<form:label path="banner">
		<spring:message code="sponsorship.banner"></spring:message>
	</form:label>
	<form:input path="banner"/>
	<form:errors cssClass="error" path="banner"></form:errors>
	<br/>
	
	<form:label path="link">
		<spring:message code="sponsorship.link"></spring:message>
	</form:label>
	<form:textarea path="link"/>
	<form:errors cssClass="error" path="link"></form:errors>
	<br/>
	
	<input type="submit" name="save" value="<spring:message code="sponsorship.save"/>"/>
	
	<input type="button" name="cancel" value="<spring:message code="sponsorship.cancel" />" 
		onclick="javascript: relativeRedir('trip/list.do');" />
		
</form:form>
	


</body>
</html>