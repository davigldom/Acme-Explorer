<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt"	uri="http://java.sun.com/jsp/jstl/fmt"%> <!-- Añadida -->
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<p><spring:message code="application.list" /></p>

<display:table name="applications" id="row" requestURI="${requestURI}"
	pagesize="5" class="displaytag" keepStatus="true">
		
	<spring:message code="application.moment" var="momentHeader"/>
	<display:column property="moment" title="${momentHeader}" sortable="false" format="{0,date,dd/MM/yyyy HH:mm}" />
	
	<spring:message code="application.status" var="statusHeader"/>
	<display:column property="status" title="${statusHeader}" sortable="true"/>

	<security:authorize access="hasRole('MANAGER')">
		<spring:message code="application.explorer" var="explorerHeader"/>
		<display:column property="explorer.name" title="${explorerHeader}" sortable="false"/>
	</security:authorize>

	<spring:message code="application.trip" var="tripHeader"/>
	<display:column property="trip.title" title="${tripHeader}" sortable="false"/>
	
	<spring:message code="application.comments" var="commentsHeader"/>
	<display:column property="comments" title="${commentsHeader}" sortable="false"/>
	
	<spring:message code="application.rejectedreason" var="rejectedReasonHeader"/>
	<display:column property="rejectedReason" title="${rejectedReasonHeader}" sortable="false"/>
	
	<jsp:useBean id="now" class="java.util.Date" />
	<fmt:parseNumber value="${now.time / (1000*60*60*24)}" var="daysNow" />
	<fmt:parseNumber value="${row.trip.startDate.time / (1000*60*60*24)}" var="daysTrip" />	
	
	<spring:message code="application.options" var="applicationOptions"/>
	<display:column title="${applicationOptions}" sortable="false">
	
		<security:authorize access="hasRole('EXPLORER')">
			<jstl:if test="${row.status == 'DUE'}">	
				<input type="button"
					value="<spring:message code="application.addcreditcard" />"
					onclick="javascript: window.location.replace('application/explorer/edit.do?applicationId=${row.id}')" />
			</jstl:if>
			
			<jstl:if test="${row.status == 'ACCEPTED' && (daysTrip-daysNow)> 0}">
				<form action = "application/explorer/edit.do" method = "post">
					<input type="hidden" name="applicationId" value="${row.id}"/>
					<input type="submit" name="cancel" value="<spring:message code="application.cancel" />" />
				</form>	
			</jstl:if>
		</security:authorize>
	
		<security:authorize access="hasRole('MANAGER')">
		
			<jstl:if test="${row.status == 'PENDING' && (daysTrip-daysNow)> 0}">
				 <form action = "application/manager/edit.do" method = "post">
				 	<input type="hidden" name="applicationId" value="${row.id}"/>
					<input type="submit" name="due" value="<spring:message code="application.due" />" />
				</form>
				<input type="button"
					value="<spring:message code="application.reject" />"
					onclick="javascript: window.location.replace('application/manager/edit.do?applicationId=${row.id}')" />
			</jstl:if>
		</security:authorize>
	</display:column>
	
</display:table>

<script type="text/javascript">
	var trTags = document.getElementsByTagName("tr");
	for (var i = 0; i < trTags.length; i++) {
	  var tdStatus = trTags[i].children[1];
	  if (tdStatus.innerText == "REJECTED") {
		  trTags[i].style.backgroundColor = "grey";
	  } else if (tdStatus.innerText == "DUE") {
		  trTags[i].style.backgroundColor = "yellow";
	  } else if (tdStatus.innerText == "ACCEPTED") {
		  trTags[i].style.backgroundColor = "green";
	  } else if (tdStatus.innerText == "CANCELLED") {
		  trTags[i].style.backgroundColor = "cyan";
	  } else if (tdStatus.innerText == "PENDING" && ('${daysTrip - daysNow > 0}')) {
		  trTags[i].style.backgroundColor = "red";
	  } else{
		  trTags[i].style.backgroundColor = "white";
	  }
	}
</script>