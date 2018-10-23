<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<form method="get" action="trip/search-word.do">
	<spring:message code="trip.search" var="searchButton" />
	<input type="text" name="keyword"> <input type="submit"
		value="${searchButton }">
</form>

<display:table pagesize="5" class="displaytag" keepStatus="true"
	name="trips" requestURI="${requestURI }" id="row">
	<!-- 
	We create a variable in order to check if the user is a manager or a trip is published,
	and show it if one of those cases
	 -->

	<!-- Attributes -->

	<spring:message code="trip.title" var="titleHeader" />
	<display:column property="title" title="${titleHeader}" sortable="true" />

	<spring:message code="trip.cancelled" var="cancelledHeader" />
	<jstl:if test="${row.cancelled}">
		<spring:message code="trip.cancelled.confirmation"
			var="cancelledConfirmation" />
	</jstl:if>
	<jstl:if test="${!row.cancelled}">
		<jstl:set value=""
			var="cancelledConfirmation" />
	</jstl:if>
	
	<display:column title="${cancelledHeader}" sortable="true">
		<jstl:out value="${cancelledConfirmation }" />
	</display:column>

	<spring:message code="trip.startDate" var="startDateHeader" />
	<display:column property="startDate" title="${startDateHeader}"
		sortable="true" format="{0,date,dd/MM/yyyy HH:mm}" />

	<spring:message code="trip.endDate" var="endDateHeader" />
	<display:column property="endDate" title="${endDateHeader}"
		sortable="true" format="{0,date,dd/MM/yyyy HH:mm}" />

	<spring:message code="trip.price" var="priceHeader" />
	<display:column property="price" title="${priceHeader}" sortable="true" />

	<spring:message code="trip.category" var="categoryHeader" />
	<display:column property="category.name" title="${categoryHeader}"
		sortable="true" />

	
	<display:column>
			<input type="button" name="display"
				value="<spring:message	code="trip.display" />"
				onclick="javascript: relativeRedir('trip/display.do?tripId=${row.id}');" />
		</display:column>

</display:table>

<!-- Action links -->

<security:authorize access="hasRole('MANAGER')">
	<div>
		<input type="button" name="create"
			value="<spring:message	code="trip.create" />"
			onclick="javascript: relativeRedir('trip/manager/create.do');" />
	</div>
</security:authorize>