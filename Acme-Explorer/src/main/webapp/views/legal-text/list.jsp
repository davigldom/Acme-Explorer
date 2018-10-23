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

<!-- Listing grid -->

<display:table pagesize="5" class="displaytag" keepStatus="true"
	name="legalTexts" requestURI="legal-text/administrator/list.do"
	id="row">


	<!-- Attributes -->

	<spring:message code="legalText.title" var="titleHeader" />
	<display:column property="title" title="${titleHeader}" sortable="true" />

	<spring:message code="legalText.definitive" var="definitiveHeader" />
	<display:column title="${definitiveHeader}"
		sortable="false" >
		<jstl:if test="${row.definitive }">
		<spring:message code="legalText.is.definitive" var="isDefinitive" />
		</jstl:if>
		<jstl:if test="${!row.definitive }">
		<spring:message code="legalText.not.definitive" var="isDefinitive" />
		</jstl:if>
		
		<jstl:out value="${isDefinitive}"/>
		</display:column>


	<!-- Action links -->
	<security:authorize access="hasRole('ADMINISTRATOR')">
		<display:column>
			<jstl:if test="${!row.definitive}">

				<input type="button" name="edit"
					value="<spring:message	code="legalText.edit" />"
					onclick="javascript: relativeRedir('legal-text/administrator/edit.do?legalTextId=${row.id}');" />

			</jstl:if>
		</display:column>
	</security:authorize>
</display:table>

<br>

<security:authorize access="hasRole('ADMINISTRATOR')">
	<input type="button" name="create"
		value="<spring:message	code="legalText.create" />"
		onclick="javascript: relativeRedir('legal-text/administrator/create.do');" />
</security:authorize>
<input type="button" name="back"
	value="<spring:message	code="legalText.back" />"
	onclick="javascript: relativeRedir('welcome/index.do');" />
