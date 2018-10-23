
<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<form:form action="story/explorer/edit.do" modelAttribute="story" method = "post">

	<form:hidden path="id"/>
	<form:hidden path="version"/>
	
	<form:hidden path="explorer"/>
	<form:hidden path="trip"/>
	<form:hidden path="trip.id"/>
	
	<form:label path="title">
		<spring:message code="story.title"></spring:message>
	</form:label>
	<form:input path="title"/>
	<form:errors cssClass="error" path="title"></form:errors>
	<br/>
	
	<form:label path="text">		
		<spring:message code="story.text"></spring:message>
	</form:label>
	<form:input path="text" />
	<form:errors cssClass="error" path="text" />
	<br />
	
	<form:label path="attachments">
		<spring:message code="story.attachments"></spring:message>
	</form:label>
	<form:input path="attachments" />
	<form:errors cssClass="error" path="attachments" />
	<br />
	
	
	
	<input type="submit" name="save"  value="<spring:message code="story.save"/>" />&nbsp;
	
	<input type="button" name="cancel" value="<spring:message code="story.cancel"/>"
		onclick="javascript: relativeRedir('trip/list.do');" />
		
</form:form>


