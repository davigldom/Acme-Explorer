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


<security:authorize access="isAuthenticated()">
	<security:authentication property="principal.username" var="user" />
</security:authorize>
<!-- Listing grid -->

<display:table pagesize="5" class="displaytag" keepStatus="true"
	name="emergencyContacts" requestURI="emergency-contact/explorer/list.do"
	id="row">


	<!-- Attributes -->

	<spring:message code="emergencyContact.name" var="nameHeader" />
	<display:column property="name" title="${nameHeader}" sortable="true" />

	<spring:message code="emergencyContact.email" var="emailHeader" />
	<display:column property="email" title="${emailHeader}"
		sortable="false" />

	<spring:message code="emergencyContact.phone" var="phoneHeader" />
	<display:column property="phone" title="${phoneHeader}"
		sortable="false" />


	<!-- Action links -->
	<security:authorize access="hasRole('EXPLORER')">
		<display:column>
			<jstl:if test="${actor.curriculum==null}">
				<input type="button" name="edit"
					value="<spring:message	code="emergencyContact.edit" />"
					onclick="javascript: relativeRedir('emergency-contact/explorer/edit.do?contactId=${row.id}');" />
			</jstl:if>
		</display:column>
	</security:authorize>
</display:table>

<br>

<security:authorize access="hasRole('EXPLORER')">
	<jstl:if test="${actor.curriculum==null}">
		<input type="button" name="create"
			value="<spring:message	code="emergencyContact.create" />"
			onclick="javascript: relativeRedir('emergency-contact/explorer/create.do');" />
	</jstl:if>
</security:authorize>

<input type="button" name="back"
	value="<spring:message	code="emergencyContact.back" />"
	onclick="javascript: relativeRedir('actor/display-principal.do');" />

