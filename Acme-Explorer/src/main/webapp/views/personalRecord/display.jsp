<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

	

	<img src="http://conceptodefinicion.de/wp-content/uploads/2014/10/persona.jpg" alt="photo" height="200" width="200">
	
	<spring:message code="personalRecord.name" var="name"/>
	<p><jstl:out value="${name}: ${personalRecord.name}"></jstl:out></p>
	
	<spring:message code="personalRecord.email" var="email"/>
	<p><jstl:out value="${email}: ${personalRecord.email}"></jstl:out></p>
	
	<spring:message code="personalRecord.phone" var="phone"/>
	<p><jstl:out value="${phone}: ${personalRecord.phone}"></jstl:out></p>
	
	<spring:message code="personalRecord.linkedIn" var="linkedIn"/>
	
	<jstl:out value="${linkedIn}: "></jstl:out><a href="${personalRecord.linkedIn}">${personalRecord.linkedIn}</a>
	
	<br>
	<br>
	
	<input type="button" name="personalRecordButtonEdit"
		value="<spring:message code="personalRecord.edit" />"
		onclick="javascript: relativeRedir('personalRecord/ranger/edit.do?personalRecordId=${personalRecord.id}&curriculumId=${curriculumId}');" />
		
	<input type="button" name="personalRecordButtonDelete"
		value="<spring:message code="personalRecord.delete" />"
		onclick="myFunction()" />


	<script>
	function myFunction() {
	    var r = confirm("Are you sure?");
	    if (r == true) {
	    	javascript: relativeRedir('personalRecord/ranger/delete.do?personalRecordId=${personalRecord.id}');
	    }
	}
	</script>
