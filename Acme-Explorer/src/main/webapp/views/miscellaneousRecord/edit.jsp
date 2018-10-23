<%--
 * edit.jsp
 *
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<form:form action="miscellaneousRecord/ranger/edit.do"
	modelAttribute="miscellaneousRecord" method="post">

	<form:hidden path="id" />
	<form:hidden path="version" />
	<input type="hidden" name="curriculumId" value="${curriculumId }" />


	<form:label path="title">
		<spring:message code="miscellaneousRecord.title"></spring:message>
	</form:label>
	<form:input path="title" />
	<form:errors cssClass="error" path="title"></form:errors>
	<br />

	<form:label path="attachment">
		<spring:message code="miscellaneousRecord.attachment"></spring:message>
	</form:label>
	<form:input path="attachment" />
	<form:errors cssClass="error" path="attachment"></form:errors>
	<br />

	<form:label path="comments">
		<spring:message code="miscellaneousRecord.comments"></spring:message>
	</form:label>
	<form:input path="comments" />
	<form:errors cssClass="error" path="comments"></form:errors>
	<br />



	<input type="submit" name="save"
		value="<spring:message code="miscellaneousRecord.save"/>" />

	<jstl:if test="${miscellaneousRecord.id!=0 }">
		<input type="submit" name="delete"
			value="<spring:message code="miscellaneousRecord.delete" />" />
	</jstl:if>
	<!-- 	<input type="button" name="personalRecordButtonDelete" -->
	<%-- 		value="<spring:message code="personalRecord.delete" />" --%>
	<!-- 		onclick="myFunction()" /> -->

	<input type="button" name="cancel"
		value="<spring:message code="miscellaneousRecord.cancel" />"
		onclick="javascript: relativeRedir('curriculum/display.do?curriculumId=${curriculumId}');" />


</form:form>


<script>
	function myFunction() {
		if (!confirm('Are you sure?')) {
			this.preventDefault();
		}
	}
</script>