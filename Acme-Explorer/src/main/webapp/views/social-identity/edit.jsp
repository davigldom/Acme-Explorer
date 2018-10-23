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


<form:form action="social-identity/edit.do"
	modelAttribute="socialIdentity">

	<form:hidden path="id" />
	<form:hidden path="version" />


	<form:label path="nick">
		<spring:message code="socialIdentity.nick"></spring:message>
	</form:label>
	<form:input path="nick" />
	<form:errors cssClass="error" path="nick"></form:errors>
	<br />

	<form:label path="nameOfSocialNetwork">
		<spring:message code="socialIdentity.nameOfSocialNetwork"></spring:message>
	</form:label>
	<form:input path="nameOfSocialNetwork" />
	<form:errors cssClass="error" path="nameOfSocialNetwork"></form:errors>
	<br />

	<form:label path="link">
		<spring:message code="socialIdentity.link"></spring:message>
	</form:label>
	<form:input path="link" />
	<form:errors cssClass="error" path="link"></form:errors>
	<br />

	<form:label path="photo">
		<spring:message code="socialIdentity.photo"></spring:message>
	</form:label>
	<form:input path="photo" />
	<form:errors cssClass="error" path="photo"></form:errors>
	<br />



	<input type="submit" name="save"
		value="<spring:message code="socialIdentity.save"/>" />

	<jstl:if test="${socialIdentity.id != 0}">
		<input type="submit" name="delete"
			value="<spring:message code="socialIdentity.delete" />" />
	</jstl:if>

	<input type="button" name="cancel"
		value="<spring:message code="socialIdentity.cancel" />"
		onclick="javascript: relativeRedir('actor/display-principal.do');" />


</form:form>