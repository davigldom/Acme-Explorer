<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %><!-- Added -->

<h2><spring:message code="administrator.dashboard.applications"/></h2>

<h4><spring:message code="administrator.dashboard.applicationsPerTrip"/></h4>

<p><spring:message code="administrator.dashboard.average"/> : <jstl:out value="${averageApplicationsPerTrip}"></jstl:out></p>

<p><spring:message code="administrator.dashboard.minimum"/> : <jstl:out value="${minimumApplicationsPerTrip}"></jstl:out></p>
	
<p><spring:message code="administrator.dashboard.maximum"/> : <jstl:out value="${maximumApplicationsPerTrip}"></jstl:out></p>
	
<p><spring:message code="administrator.dashboard.standardDeviation"/> : <jstl:out value="${standardDeviationApplicationsPerTrip}"></jstl:out></p>


<h4><spring:message code="administrator.dashboard.applicationsRatio"/></h4>

<p><spring:message code="administrator.dashboard.applicationsPending"/> : <jstl:out value="${ratioApplicationsPending}"></jstl:out></p>

<p><spring:message code="administrator.dashboard.applicationsDue"/> : <jstl:out value="${ratioApplicationsDue}"></jstl:out></p>

<p><spring:message code="administrator.dashboard.applicationsAccepted"/> : <jstl:out value="${ratioApplicationsAccepted}"></jstl:out></p>

<p><spring:message code="administrator.dashboard.applicationsCancelled"/> : <jstl:out value="${ratioApplicationsCancelled}"></jstl:out></p>

<p><spring:message code="administrator.dashboard.applicationsRejected"/> : <jstl:out value="${ratioApplicationsRejected}"></jstl:out></p>


<h2><spring:message code="administrator.dashboard.trips"/></h2>

<p><spring:message code="administrator.dashboard.tripsCancelled"/> : <jstl:out value="${tripsCancelled}"></jstl:out> vs.  <jstl:out value="${tripsOrganised}"></jstl:out> <spring:message code="administrator.dashboard.tripsOrganised"/></p>

<h4><spring:message code="administrator.dashboard.managersPerTrip"/></h4>

<p><spring:message code="administrator.dashboard.average"/> : <jstl:out value="${averageManagersPerTrip}"></jstl:out></p>

<p><spring:message code="administrator.dashboard.minimum"/> : <jstl:out value="${minimumManagersPerTrip}"></jstl:out></p>
	
<p><spring:message code="administrator.dashboard.maximum"/> : <jstl:out value="${maximumManagersPerTrip}"></jstl:out></p>
	
<p><spring:message code="administrator.dashboard.standardDeviation"/> : <jstl:out value="${standardDeviationManagersPerTrip}"></jstl:out></p>


<h4><spring:message code="administrator.dashboard.rangersPerTrip"/></h4>

<p><spring:message code="administrator.dashboard.average"/> : <jstl:out value="${averageRangersPerTrip}"></jstl:out></p>

<p><spring:message code="administrator.dashboard.minimum"/> : <jstl:out value="${minimumRangersPerTrip}"></jstl:out></p>
	
<p><spring:message code="administrator.dashboard.maximum"/> : <jstl:out value="${maximumRangersPerTrip}"></jstl:out></p>
	
<p><spring:message code="administrator.dashboard.standardDeviation"/> : <jstl:out value="${standardDeviationRangersPerTrip}"></jstl:out></p>


<h4><spring:message code="administrator.dashboard.pricesOfTrip"/></h4>

<p><spring:message code="administrator.dashboard.average"/> : <jstl:out value="${averagePriceOfTrip}"></jstl:out></p>

<p><spring:message code="administrator.dashboard.minimum"/> : <jstl:out value="${minimumPriceOfTrip}"></jstl:out></p>
	
<p><spring:message code="administrator.dashboard.maximum"/> : <jstl:out value="${maximumPriceOfTrip}"></jstl:out></p>
	
<p><spring:message code="administrator.dashboard.standardDeviation"/> : <jstl:out value="${standardDeviationPriceOfTrip}"></jstl:out></p>


<h4><spring:message code="administrator.dashboard.notesPerTrip"/></h4>

<p><spring:message code="administrator.dashboard.average"/> : <jstl:out value="${averageNotesPerTrip}"></jstl:out></p>

<p><spring:message code="administrator.dashboard.minimum"/> : <jstl:out value="${minimumNotesPerTrip}"></jstl:out></p>
	
<p><spring:message code="administrator.dashboard.maximum"/> : <jstl:out value="${maximumNotesPerTrip}"></jstl:out></p>
	
<p><spring:message code="administrator.dashboard.standardDeviation"/> : <jstl:out value="${standardDeviationNotesPerTrip}"></jstl:out></p>


<h4><spring:message code="administrator.dashboard.auditRecordsPerTrip"/></h4>

<p><spring:message code="administrator.dashboard.average"/> : <jstl:out value="${averageAuditRecordsPerTrip}"></jstl:out></p>

<p><spring:message code="administrator.dashboard.minimum"/> : <jstl:out value="${minimumAuditRecordsPerTrip}"></jstl:out></p>
	
<p><spring:message code="administrator.dashboard.maximum"/> : <jstl:out value="${maximumAuditRecordsPerTrip}"></jstl:out></p>
	
<p><spring:message code="administrator.dashboard.standardDeviation"/> : <jstl:out value="${standardDeviationAuditRecordsPerTrip}"></jstl:out></p>

<p><spring:message code="administrator.dashboard.ratioTripsAuditRecord"/> : <jstl:out value="${tripsWithAnAuditRecord}"></jstl:out></p>


<h2><spring:message code="administrator.dashboard.rangers"/></h2>

<p><spring:message code="administrator.dashboard.rangersCurricula"/> : <jstl:out value="${ratioRangersCurriculaRegistered}"></jstl:out></p>

<p><spring:message code="administrator.dashboard.rangersCurriculumEndorsed"/> : <jstl:out value="${ratioRangersCurriculumEndorsed}"></jstl:out></p>

<p><spring:message code="administrator.dashboard.rangersSuspicious"/> : <jstl:out value="${ratioSuspiciousRangers}"></jstl:out></p>


<h2><spring:message code="administrator.dashboard.managers"/></h2>

<p><spring:message code="administrator.dashboard.managersSuspicious"/> : <jstl:out value="${ratioSuspiciousManagers}"></jstl:out></p>

<h2><spring:message code="administrator.dashboard.tenPercent"/></h2>

<display:table pagesize="5" class="displaytag" keepStatus="true"
	name="tenPercent" id="row" requestURI="administrator/dashboard.do">
	
	<spring:message code="administrator.dashboard.tenPercent.title" var="titleHeader" />
	<display:column property="title" title="${titleHeader}" />
	
</display:table>

<h2><spring:message code="administrator.dashboard.legalTexts"/></h2>

<display:table pagesize="5"  class="displaytag" keepStatus="true" name="legalTexts" id="row" requestURI="administrator/dashboard.do">
	
	<spring:message code="administrator.dashboard.legalTextReferenced.title" var="titleLtrHeader" />
	<display:column property="title" title="${titleLtrHeader}" sortable="true"></display:column>
	
	<spring:message code="administrator.dashboard.legalTextReferenced.number" var="numberLtrHeader" />
	<display:column title="${numberLtrHeader}" sortable="true">${fn:length(row.trips)}</display:column>
	
</display:table>