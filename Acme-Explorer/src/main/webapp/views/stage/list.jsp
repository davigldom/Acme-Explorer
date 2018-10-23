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



<display:table name="stages" id="row" requestURI="stage/list.do"
	pagesize="3" class="displaytag">

	<spring:message code="stage.title" var="titleHeader" />
	<display:column property="title" title="${titleHeader}" sortable="true" />

	<spring:message code="stage.description" var="descriptionHeader" />
	<display:column property="description" title="${descriptionHeader}"
		sortable="false" />

	<spring:message code="stage.price" var="priceHeader" />
	<display:column property="price" title="${priceHeader}" sortable="true" />

	<security:authorize access="hasRole('MANAGER')">
		<display:column>
			<input type="button" name="edit"
				value="<spring:message	code="stage.edit" />"
				onclick="javascript: relativeRedir('stage/edit.do?tripId=${tripId}&stageId=${row.id }');" />
		</display:column>
	</security:authorize>
</display:table>

<security:authorize access="hasRole('MANAGER')">
	
		<input type="button" name="create"
			value="<spring:message	code="stage.create" />"
			onclick="javascript: relativeRedir('stage/create.do?tripId=${tripId}');" />
	
</security:authorize>

<input type="button" name="back"
			value="<spring:message	code="stage.back" />"
			onclick="javascript: relativeRedir('trip/display.do?tripId=${tripId}');" />

