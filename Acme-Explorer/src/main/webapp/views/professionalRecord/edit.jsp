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

<form:form action="professionalRecord/ranger/edit.do"
	modelAttribute="professionalRecord" method="post">

	<form:hidden path="id" />
	<form:hidden path="version" />
	<input type="text" hidden="true" value="${curriculumId }"
		name="curriculumId" />

	<form:label path="companyName">
		<spring:message code="professionalRecord.companyName"></spring:message>
	</form:label>
	<form:input path="companyName" />
	<form:errors cssClass="error" path="companyName"></form:errors>
	<br />

	<form:label path="role">
		<spring:message code="professionalRecord.role"></spring:message>
	</form:label>
	<form:input path="role" />
	<form:errors cssClass="error" path="role"></form:errors>
	<br />

	<form:label path="attachment">
		<spring:message code="professionalRecord.attachment"></spring:message>
	</form:label>
	<form:input path="attachment" />
	<form:errors cssClass="error" path="attachment"></form:errors>
	<br />

	<form:label path="comments">
		<spring:message code="professionalRecord.comments"></spring:message>
	</form:label>
	<form:textarea path="comments" />
	<form:errors cssClass="error" path="comments"></form:errors>
	<br />

	<form:label path="professionalPeriod.start">
		<spring:message code="professionalRecord.professionalPeriodStart" />
	</form:label>
	<form:input path="professionalPeriod.start"
		placeholder="dd/MM/yyyy HH:mm" />
	<form:errors cssClass="error" path="professionalPeriod.start" />
	<br />

	<form:label path="professionalPeriod.end">
		<spring:message code="professionalRecord.professionalPeriodEnd" />
	</form:label>
	<form:input path="professionalPeriod.end"
		placeholder="dd/MM/yyyy HH:mm" />
	<form:errors cssClass="error" path="professionalPeriod.end" />
	<br />


	<input type="submit" name="save"
		value="<spring:message code="professionalRecord.save"/>" />

	<jstl:if test="${professionalRecord.id!=0 }">
		<input type="submit" name="delete"
			value="<spring:message code="professionalRecord.delete" />" />
	</jstl:if>

	<input type="button" name="cancel"
		value="<spring:message code="professionalRecord.cancel" />"
		onclick="javascript: relativeRedir('curriculum/display.do?curriculumId=${curriculumId}');" />

</form:form>