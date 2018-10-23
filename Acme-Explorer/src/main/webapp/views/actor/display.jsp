<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>




<security:authorize access="isAuthenticated()">
	<security:authentication property="principal.username" var="user" />
</security:authorize>
<spring:message code="actor.name" var="name" />
<jstl:out value="${name }: " />
<jstl:out value="${actor.name}" />
<br>

<spring:message code="actor.surname" var="surname" />
<jstl:out value="${surname}: " />
<jstl:out value="${actor.surname}" />
<br>

<spring:message code="actor.email" var="email" />
<jstl:out value="${email }: " />
<jstl:out value="${actor.email}" />
<br>

<spring:message code="actor.phone" var="phone" />
<jstl:out value="${phone }: " />
<jstl:out value="${actor.phone}" />
<br>

<spring:message code="actor.address" var="address" />
<jstl:out value="${address }: " />
<jstl:out value="${actor.address}" />
<br>

<input type="button" name="socialIdentities"
	value="<spring:message code="actor.seeSocialIdentities" />"
	onclick="javascript: relativeRedir('social-identity/list.do?actorId=${actor.id}');" />


<security:authorize access="hasRole('EXPLORER')">
	<jstl:if test="${actor.userAccount.authorities[0]=='EXPLORER' }">
		<input type="button" name="emergencyContacts"
			value="<spring:message code="actor.seeEmergencyContacts" />"
			onclick="javascript: relativeRedir('emergency-contact/explorer/list.do?explorerId=${actor.id}');" />

		<jstl:if test="${user == actor.userAccount.username }">
			<input type="button" name="creditCards"
				value="<spring:message code="actor.seeCreditCards" />"
				onclick="javascript: relativeRedir('credit-card/explorer/list.do');" />
		</jstl:if>
	</jstl:if>

</security:authorize>

<security:authorize access="hasRole('SPONSOR')">
	<jstl:if test="${user == actor.userAccount.username }">
		<input type="button" name="creditCards"
			value="<spring:message code="actor.seeCreditCards" />"
			onclick="javascript: relativeRedir('credit-card/sponsor/list.do');" />
	</jstl:if>
</security:authorize>


<security:authorize access="isAuthenticated()">
	<jstl:if test="${user == actor.userAccount.username }">
		<input type="button" name="edit"
			value="<spring:message code="actor.edit" />"
			onclick="javascript: relativeRedir('actor/edit.do');" />
	</jstl:if>

</security:authorize>

<input type="button" name="back"
	value="<spring:message code="actor.back" />"
	onclick="javascript: relativeRedir('welcome/index.do');" />
<br />
<br />


<jstl:if test="${actor.userAccount.authorities[0]=='RANGER' }">
	<jstl:if test="${actor.curriculum!=null}">
		<input type="button" name="rangerCurriculum"
			value="<spring:message code="actor.seeRangerCurriculum" />"
			onclick="javascript: relativeRedir('curriculum/display.do?curriculumId=${actor.curriculum.id}');" />
	</jstl:if>
</jstl:if>

<security:authorize access="hasRole('RANGER')">
	<jstl:if test="${user == actor.userAccount.username }">
		<jstl:if test="${actor.curriculum==null}">
			<input type="button" name="rangerRegisterCurriculum"
				value="<spring:message code="actor.registerRangerCurriculum" />"
				onclick="javascript: relativeRedir('personalRecord/ranger/create.do');" />
		</jstl:if>
	</jstl:if>
</security:authorize>
