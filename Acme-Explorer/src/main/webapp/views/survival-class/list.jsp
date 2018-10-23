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
	name="survivalClasses" requestURI="${requestURI }" id="row">


	<!-- Attributes -->

	<spring:message code="survivalClass.title" var="titleHeader" />
	<display:column property="title" title="${titleHeader}" sortable="true" />

	<spring:message code="survivalClass.description" var="descriptionHeader" />
	<display:column property="description" title="${descriptionHeader}" sortable="false" />

	<spring:message code="survivalClass.moment" var="momentHeader" />
	<display:column property="moment" title="${momentHeader}" sortable="true" format="{0,date,dd/MM/yyyy HH:mm}" />

	<spring:message code="survivalClass.location" var="locationHeader" />
	<display:column property="location.name" title="${locationHeader}" sortable="false" />
	
	<spring:message code="survivalClass.latitude" var="latitudeHeader" />
	<display:column property="location.latitude" title="${latitudeHeader}" sortable="false" />
	
	<spring:message code="survivalClass.longitude" var="longitudeHeader" />
	<display:column property="location.longitude" title="${longitudeHeader}" sortable="false" />

	<spring:message code="survivalClass.trip" var="tripHeader" />
	<display:column property="trip.title" title="${tripHeader}" sortable="true" />

	<security:authorize access="hasRole('EXPLORER')">
		<spring:message code="survivalClass.manager" var="managerHeader" />
		<display:column property="manager.name" title="${managerHeader}" sortable="true" />
	</security:authorize>


	<!-- Action links -->

	<security:authorize access="hasRole('MANAGER')">
		<display:column>
			<input type="button"
				value="<spring:message code="survivalClass.edit" />"
				onclick="javascript: window.location.replace('survival-class/manager/edit.do?survivalClassId=${row.id}')" />
		</display:column>
	</security:authorize>
	
	<security:authorize access="hasRole('EXPLORER')">
		<display:column>
			<jstl:choose>
				<jstl:when test="${enrolledClasses.contains(row) }">
					<input type="button"
					value="<spring:message code="survivalClass.unenrol" />"
					onclick="javascript: window.location.replace('survival-class/explorer/unenrol.do?survivalClassId=${row.id}')" />
				</jstl:when>
				<jstl:otherwise>
				<input type="button"
					value="<spring:message code="survivalClass.enrol" />"
					onclick="javascript: window.location.replace('survival-class/explorer/enrol.do?survivalClassId=${row.id}')" />
				</jstl:otherwise>
			</jstl:choose>
		</display:column>
	</security:authorize>

</display:table>


<security:authorize access="hasRole('MANAGER')">
	<input type="button"
		value="<spring:message code="survivalClass.create" />"
		onclick="javascript: window.location.replace('survival-class/manager/create.do')" />
	<br/>
</security:authorize>