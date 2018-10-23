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


<form:form action="legal-text/administrator/edit.do"
	modelAttribute="legalText">

	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="trips" />
	<form:hidden path="moment" />



	<form:label path="title">
		<spring:message code="legalText.title"></spring:message>
	</form:label>
	<form:input path="title" />
	<form:errors cssClass="error" path="title"></form:errors>
	<br />

	<form:label path="body">
		<spring:message code="legalText.body"></spring:message>
	</form:label>
	<form:input path="body" />
	<form:errors cssClass="error" path="body"></form:errors>
	<br />


	<form:label path="applicableLaws">
		<spring:message code="legalText.applicableLaws"></spring:message>
	</form:label>
	<form:textarea path="applicableLaws" />
	<form:errors cssClass="error" path="applicableLaws"></form:errors>
	<br />


	<form:label path="definitive">
		<spring:message code="legalText.definitive"></spring:message>
	</form:label>
	<form:checkbox path="definitive" />
	<form:errors cssClass="error" path="definitive"></form:errors>
	<br />


	<input type="submit" name="save"
		value="<spring:message code="legalText.save"/>" />

	<jstl:if test="${legalText.id != 0}">
		<input type="submit" name="delete"
			value="<spring:message code="legalText.delete" />" />
	</jstl:if>

	<input type="button" name="cancel"
		value="<spring:message code="socialIdentity.cancel" />"
		onclick="javascript: relativeRedir('legal-text/administrator/list.do');" />


</form:form>