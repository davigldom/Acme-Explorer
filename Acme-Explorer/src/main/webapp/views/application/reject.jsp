<%--
 * action-2.jsp
 *
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<p><spring:message code="application.reject" /></p>

<form:form action="application/manager/edit.do" modelAttribute="application" method = "post">
	<input type="hidden" name="applicationId" value="${application.id}" />
	
	<jstl:if test="${rejectedReason == null}">
		<form:label path="rejectedReason">
			<spring:message code="application.rejectedreason"/>
		</form:label>:
		<form:textarea path="rejectedReason"/>
	</jstl:if>	
	
	<input type="submit" name="reject" value="<spring:message code="application.reject" />" />
	
	<input type="button" name="cancel" value="<spring:message code="application.cancel" />"
		onclick="javascript: relativeRedir('application/manager/list.do');" />
		
</form:form>


