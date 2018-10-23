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


<form:form action="actor/${authority}/edit.do"
	modelAttribute="${authority}">

	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="folders" />
	<jstl:if test="${explorer.id!=0 && manager.id!=0 && ranger.id!=0}">
		<form:hidden path="userAccount" />
	</jstl:if>
	<jstl:if test="${explorer.id==0}">
		<form:hidden path="userAccount.authorities" />
		<form:hidden path="emergencyContacts" />
		<form:hidden path="survivalClasses" />
		<form:hidden path="applications" />
		<form:hidden path="stories" />
		<form:hidden path="finder" />
	</jstl:if>
	<jstl:if test="${ranger.id==0}">
		<form:hidden path="userAccount.authorities" />
		<form:hidden path="trips" />
	</jstl:if>
	
	<jstl:if test="${manager.id==0}">
		<form:hidden path="userAccount.authorities" />
		<form:hidden path="suspicious" />
		<form:hidden path="banned" />
		<form:hidden path="survivalClasses" />
		<form:hidden path="trips" />
	</jstl:if>
	
	<form:hidden path="socialIdentities" />





	<security:authorize access="hasRole('EXPLORER')">
		<form:hidden path="emergencyContacts" />
		<form:hidden path="survivalClasses" />
		<form:hidden path="applications" />
		<form:hidden path="finder" />
		<form:hidden path="stories" />
	</security:authorize>

	<security:authorize access="hasRole('MANAGER')">
		<form:hidden path="trips" />
		<form:hidden path="survivalClasses" />
	</security:authorize>

	<security:authorize access="hasRole('AUDITOR')">
		<form:hidden path="auditions" />
		<form:hidden path="notes" />
	</security:authorize>

	<security:authorize access="hasRole('SPONSOR')">
		<form:hidden path="sponsorships" />
	</security:authorize>

	<security:authorize access="hasRole('RANGER')">
		<form:hidden path="trips" />
	</security:authorize>

	<jstl:if test="${explorer.id==0 || ranger.id==0 || manager.id==0}">

		<form:label path="userAccount.username">
			<spring:message code="actor.username" />
		</form:label>
		<form:input path="userAccount.username" />
		<form:errors class="error" path="userAccount.username" />
		<br />

		<form:label path="userAccount.password">
			<spring:message code="actor.password" />
		</form:label>
		<form:input path="userAccount.password" type="password" value=""/>
		<form:errors class="error" path="userAccount.password" />
		<br />
	</jstl:if>

	<form:label path="name">
		<spring:message code="actor.name"></spring:message>
	</form:label>
	<form:input path="name" />
	<form:errors cssClass="error" path="name"></form:errors>
	<br />

	<form:label path="surname">
		<spring:message code="actor.surname"></spring:message>
	</form:label>
	<form:input path="surname" />
	<form:errors cssClass="error" path="surname"></form:errors>
	<br />


	<form:label path="email">
		<spring:message code="actor.email"></spring:message>
	</form:label>
	<form:input path="email" />
	<form:errors cssClass="error" path="email"></form:errors>
	<br />

	<form:label path="phone">
		<spring:message code="actor.phone"></spring:message>
	</form:label>
	<form:input path="phone" id="phone" onblur="javascript: checkPhone();"
		placeholder="+34 (123) 1234" />
	<form:errors cssClass="error" path="phone"></form:errors>
	<br />

	<form:label path="address">
		<spring:message code="actor.address"></spring:message>
	</form:label>
	<form:input path="address" />
	<form:errors cssClass="error" path="address"></form:errors>
	<br />

	<input type="submit" name="save"
		value="<spring:message code="actor.save"/>" />

	<jstl:if test="${explorer.id!=0 && ranger.id!=0}">
		<input type="button" name="cancel"
			value="<spring:message code="actor.cancel" />"
			onclick="javascript: relativeRedir('actor/display-principal.do');" />
	</jstl:if>
	<jstl:if test="${explorer.id==0 || manager.id==0 || ranger.id==0}">
		<input type="button" name="cancel"
			value="<spring:message code="actor.cancel" />"
			onclick="javascript: relativeRedir('welcome/index.do');" />
	</jstl:if>



</form:form>

<spring:message code="phone.validation.check" var="phoneCheck"></spring:message>

<script type="text/javascript">
	function checkPhone() {
		var target = document.getElementById("phone");
		var input = target.value;
		var regExp1 = new RegExp("(^[+]([1-9]{1,3})) ([(][1-9]{1,3}[)]) (\\d{4,}$)");
		var regExp2 = new RegExp("(^[+]([1-9]{1,3})) (\\d{4,}$)");
		var regExp3 = new RegExp("(^\\d{4,}$)");

		if ('${phone}' != input && regExp1.test(input) == false && regExp2.test(input) == false && regExp3.test(input) == false) {
			if (confirm(input + " " + '${phoneCheck}') == false) {
				target.focus();
				target.select();
			}
		} else if ('${phone}' != input && regExp1.test(input) == false && regExp2.test(input) == false && regExp3.test(input) == true) {
			target.value = '${defaultCountry}' + " " + input;
		}
	}
</script>