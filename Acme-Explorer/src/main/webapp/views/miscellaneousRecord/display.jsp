<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

	
	<spring:message code="miscellaneousRecord.title" var="title"/>
	<p><jstl:out value="${title}: ${miscellaneousRecord.title}"></jstl:out></p>
	
	<spring:message code="miscellaneousRecord.attachment" var="attachment"/>
	<jstl:out value="${attachment}: "></jstl:out><a href="${miscellaneousRecord.attachment}">${miscellaneousRecord.attachment}</a>
	<br>
	<br>
	
	<spring:message code="miscellaneousRecord.comments" var="comments"/>
	<jstl:out value="${comments}: ${miscellaneousRecord.title}"></jstl:out>


	<br>
	<br>
	
	<security:authorize access="hasRole('RANGER')">
	
	<input type="button" name="miscellaneousRecordButtonEdit"
		value="<spring:message code="miscellaneousRecord.edit" />"
		onclick="javascript: relativeRedir('miscellaneousRecord/ranger/edit.do?miscellaneousRecordId=${miscellaneousRecord.id}&curriculumId=${curriculumId}');" />
	
	</security:authorize>

