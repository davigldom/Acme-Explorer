 <%--
 * login.jsp
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

<form:form action="j_spring_security_check" modelAttribute="credentials" id="loginform">

	<form:label path="username">
		<spring:message code="security.username" />
	</form:label>
	<form:input id="username" path="username" />	
	<form:errors class="error" path="username" />
	<br />

	<form:label path="password">
		<spring:message code="security.password" />
	</form:label>
	<form:password path="password" />	
	<form:errors class="error" path="password" />
	<br />
	
	
	<div id="error" class="error">
		<jstl:if test="${showError == true}">
			<span id="errorspan"><spring:message code="security.login.failed" /></span>
		</jstl:if>
	<p id="banned" style="display:none;"><spring:message code="security.banned" /></p>
	</div>
	
	
	<input type="submit" value="<spring:message code="security.login" />" />
	
</form:form>

<security:authorize access="!isAuthenticated()">
    <input type="button" name="signup"
		value="<spring:message	code="security.signup.explorer" />"
		onclick="javascript: relativeRedir('actor/create-explorer.do');" />
</security:authorize>

<security:authorize access="!isAuthenticated()">
    <input type="button" name="signup"
		value="<spring:message	code="security.signup.ranger" />"
		onclick="javascript: relativeRedir('actor/create-ranger.do');" />
</security:authorize>



<script type="text/javascript">

$(document).on('submit','form#loginform',function(e){
	var p = document.getElementById("banned");
	var errorspan = document.getElementById("errorspan");
	var input = document.getElementById("username").value;
	var bannedUsernames = '${bannedUsernames}';
	
	if(bannedUsernames.indexOf(input) != -1){
		e.preventDefault();
		p.style.display = "block";
		errorspan.style.display = "none";
	}
});

</script>