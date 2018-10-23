<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<!-- Listing grid -->

<display:table pagesize="5" class="displaytag" keepStatus="true"
	name="notes" requestURI="${requestURI }" id="row">
		
		
	<!-- Attributes -->
	
	<spring:message code="note.creationMoment" var="creationMomentHeader" />
	<display:column property="creationMoment" title="${creationMomentHeader}" sortable="true" format="{0,date,dd/MM/yyyy HH:mm}" />
	
	<spring:message code="note.remark" var="remarkHeader" />
	<display:column property="remark" title="${remarkHeader}" sortable="false" />
	
	<security:authorize access="hasRole('MANAGER')">
	<spring:message code="note.auditor" var="auditorHeader" />
	<display:column property="auditor.name" title="${auditorHeader}" sortable="true" />
	</security:authorize>
	
	<spring:message code="note.trip" var="tripHeader" />
	<display:column property="trip.title" title="${tripHeader}" sortable="true" />
	
	<jstl:if test="${row.reply!=null}">
	<spring:message code="note.reply" var="replyHeader" />
	<display:column property="reply" title="${replyHeader}" sortable="false" />
	</jstl:if>
	
	<jstl:if test="${row.reply!=null}">
	<spring:message code="note.replyMoment" var="replyMomentHeader" />
	<display:column property="replyMoment" title="${replyMomentHeader}" sortable="true" format="{0,date,dd/MM/yyyy HH:mm}" />
	</jstl:if>
	
	
	<!-- Action links -->
	
	<jstl:if test="${row.reply==null}">
	<security:authorize access="hasRole('MANAGER')">
		<display:column>
			<input type="button"
				value="<spring:message code="note.create.reply" />"
				onclick="javascript: window.location.replace('note/manager/edit.do?noteId=${row.id}')" />
		</display:column>		
	</security:authorize>
	</jstl:if>

</display:table>

<security:authorize access="hasRole('AUDITOR')">
	<input type="button"
		value="<spring:message code="note.create" />"
		onclick="javascript: window.location.replace('note/auditor/create.do')" />
</security:authorize>
