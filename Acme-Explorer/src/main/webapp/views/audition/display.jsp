<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>


<security:authorize access="isAuthenticated()">
	<security:authentication property="principal.id" var="principalId" />
</security:authorize>

<spring:message code="audition.title" var="title" />
<jstl:out value="${title }: " />
<jstl:out value="${audition.title }" />
<br>

<spring:message code="audition.description" var="description" />
<jstl:out value="${description}: " />
<jstl:out value="${audition.description}" />
<br>

<spring:message code="audition.trip" var="trip" />
<jstl:out value="${trip}: " />
<jstl:out value="${audition.trip.description}" />
<br>

<spring:message code="audition.attachments" var="attachmentsHead" /><br/>
<jstl:out value="${attachmentsHead }: "></jstl:out>
<br/>
<jstl:forEach var="attachment" items="${audition.attachments }">
	<a href="${attachment}">${attachment}</a>
	<br/>
</jstl:forEach>

<spring:message code="audition.draft" var="draftHeader" />
<jstl:if test="${row.draft==true }">
	<input type="checkbox" checked disabled>
</jstl:if>
		
<jstl:if test="${row.draft==false }">
	<input type="checkbox" disabled>
</jstl:if>
<br/>


<security:authorize access="hasRole('AUDITOR')">
	
<%-- 		<a href="audition/auditor/edit.do?auditionId=${row.id}"> --%>
<%-- 			<spring:message	code="audition.edit" /> --%>
<!-- 		</a> -->
		<jstl:if test="${audition.draft==true && audition.auditor.id==principalId}">
			<input type="button" name="edit"
					value="<spring:message	code="audition.edit" />"
					onclick="javascript: relativeRedir('audition/auditor/edit.do?auditionId=${audition.id}');" />
		
		
			<input type="submit" name="addAttachment"
					value="<spring:message code="audition.addAttachment" />"
					onclick="addAttachment()" />&nbsp;

		</jstl:if>
</security:authorize>

<input type="button" name="back"
		value="<spring:message code="audition.back" />"
		onclick="javascript: relativeRedir('audition/auditor/list.do');" />


<script type="text/javascript">
	function addAttachment() {
		var attachment = prompt("<spring:message code="audition.attachmentUrl" />");
		if (attachment==null){
			relativeRedir('audition/auditor/display.do?auditionId='+'${audition.id}');
		}
		else{
			relativeRedir('audition/auditor/addAttachment.do?attachment='+attachment+'&auditionId='+'${audition.id}');
		}
		
	}
</script>
