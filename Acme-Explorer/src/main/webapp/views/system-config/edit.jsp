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

<form:form action="system-config/administrator/edit.do"
	modelAttribute="systemConfig">

	<form:hidden path="id" />
	<form:hidden path="version" />



	<form:label path="finderResults">
		<spring:message code="system.config.finderResults"></spring:message>
	</form:label>
	<form:input type="number" path="finderResults" />
	<form:errors cssClass="error" path="finderResults"></form:errors>
	<br />

	<form:label path="countryCode">
		<spring:message code="system.config.countryCode"></spring:message>
	</form:label>
	<form:input path="countryCode" />
	<form:errors cssClass="error" path="countryCode"></form:errors>
	<br />

	<form:label path="banner">
		<spring:message code="system.config.banner"></spring:message>
	</form:label>
	<form:input path="banner" />
	<form:errors cssClass="error" path="banner"></form:errors>
	<br />

	<form:label path="cacheHours">
		<spring:message code="system.config.cacheHours"></spring:message>
	</form:label>
	<form:input type="number" path="cacheHours" />
	<form:errors cssClass="error" path="cacheHours"></form:errors>
	<br />

	<form:label path="welcomeMessage">
		<spring:message code="system.config.welcomeMessage"></spring:message>
	</form:label>
	<form:input path="welcomeMessage" />
	<form:errors cssClass="error" path="welcomeMessage"></form:errors>
	<br />

	<form:label path="welcomeMessageEs">
		<spring:message code="system.config.welcomeMessageEs"></spring:message>
	</form:label>
	<form:input path="welcomeMessageEs" />
	<form:errors cssClass="error" path="welcomeMessageEs"></form:errors>
	<br />

	<form:label path="spamWords">
		<spring:message code="system.config.spamWords"></spring:message>
	</form:label>
	<spring:message code="system.config.spamWords.placeholder" var="spamPlaceholder"/>
	<form:input placeholder="${spamPlaceholder }" path="spamWords" />
	<form:errors cssClass="error" path="spamWords"></form:errors>
	<br />

	<form:label path="VAT">
		<spring:message code="system.config.VAT"></spring:message>
	</form:label>
	<form:input path="VAT" />
	<form:errors cssClass="error" path="VAT"></form:errors>
	<br />

	<input type="submit" name="save"
		value="<spring:message code="system.config.save" />" />&nbsp;
	
	<input type="button" name="cancel"
		value="<spring:message code="system.config.cancel" />"
		onclick="javascript: relativeRedir('/');" />


</form:form>