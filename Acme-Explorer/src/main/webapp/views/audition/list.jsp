<%--
 * list.jsp
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

<security:authorize access="isAuthenticated()">
	<security:authentication property="principal.id" var="principalId" />
</security:authorize>

<display:table name="auditions" id="row" requestURI="${requestURI }"
	pagesize="3" class="displaytag">

	<security:authorize access="hasRole('AUDITOR')">
		<display:column>
			<jstl:if test="${row.draft==true && isAutor==true}">
				<input type="button" name="edit"
					value="<spring:message	code="audition.edit" />"
					onclick="javascript: relativeRedir('audition/auditor/edit.do?auditionId=${row.id}');" />
			</jstl:if>
		</display:column>

		<display:column>
			<input type="button" name="display"
				value="<spring:message	code="audition.display" />"
				onclick="javascript: relativeRedir('audition/auditor/display.do?auditionId=${row.id}');" />
		</display:column>
	</security:authorize>

	<spring:message code="audition.title" var="titleHeader" />
	<display:column property="title" title="${titleHeader}"
		sortable="false" />

	<spring:message code="audition.moment" var="momentHeader" />
	<display:column property="moment" title="${momentHeader}"
		sortable="true" format="{0,date,dd/MM/yyyy HH:mm}" />

	<spring:message code="audition.description" var="descriptionHeader" />
	<display:column property="description" title="${descriptionHeader}"
		sortable="false" />

	<spring:message code="audition.trip" var="tripHeader" />
	<display:column property="trip.title" title="${tripHeader}"
		sortable="false" />

	<security:authorize access="hasRole('AUDITOR')">
		<spring:message code="audition.draft" var="draftHeader" />
		<display:column title="${draftHeader}" sortable="false">
			<jstl:if test="${row.draft==true }">
				<input type="checkbox" checked disabled>
				<%-- 			<jstl:out value="si"></jstl:out> --%>
			</jstl:if>

			<jstl:if test="${row.draft==false }">
				<input type="checkbox" disabled>
			</jstl:if>
		</display:column>
	</security:authorize>
</display:table>

<jstl:if test="${tripId!=0}">
	<input type="button"
		value="<spring:message code="audition.back" />"
		onclick="javascript: window.location.replace('trip/display.do?tripId=${tripId}')" />
	<br/><br/>
</jstl:if>

<%-- <a href="audition/auditor/create.do?tripId=${row.trip.id }&auditorId=${row.auditor.id }">  --%>
<%-- <spring:message code="audition.write"></spring:message> --%>
<!-- </a> -->

<!-- <input type="button" name="write" -->
<%-- value="<spring:message	code="audition.write" />" --%>
<%-- onclick="javascript: relativeRedir('audition/auditor/create.do?tripId=${row.trip.id }&auditorId=${row.auditor.id }');" /> --%>


<script type="text/javascript">
	function addAttachment() {
		var attachment = prompt("<spring:message code="audition.attachmentUrl" />");
		relativeRedir('audition/auditor/addAttachment.do?attachment='
				+ attachment + '&auditionId=' + '${row.id}');
	}
</script>
