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



<display:table name="tags" id="row"
	requestURI="tag/administrator/list.do" pagesize="3" class="displaytag">

	<spring:message code="tag.name" var="nameHeader" />
	<display:column property="name" title="${nameHeader}" sortable="true" />

	<security:authorize access="hasRole('ADMINISTRATOR')">
		<display:column>
			<jstl:if test="${empty row.tagValues}">
				<input type="button" name="edit"
					value="<spring:message	code="tag.edit" />"
					onclick="javascript: relativeRedir('tag/administrator/edit.do?tagId=${row.id}');" />

			</jstl:if>
		</display:column>
	</security:authorize>
</display:table>

<security:authorize access="hasRole('ADMINISTRATOR')">
	<div>
		<input type="button" name="create"
			value="<spring:message	code="tag.create" />"
			onclick="javascript: relativeRedir('tag/administrator/create.do');" />
	</div>
</security:authorize>

