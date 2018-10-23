<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>



<security:authorize access="isAuthenticated()">
	<security:authentication property="principal.username" var="user" />
</security:authorize>
<jstl:if test="${trip.cancelled == true}">
	<spring:message code="trip.cancelled" var="isCancelled" />
	<b><jstl:out value="${ isCancelled}: " /></b>
	<br>
	<jstl:out value="${trip.cancelationReason}" />
</jstl:if>
<br>
<br>


<jstl:if test="${trip.published == true}">
	<spring:message code="trip.publication" var="isPublished" />
	<b><jstl:out value="${isPublished }: " /></b>
	<jstl:out value="${trip.publication}" />
</jstl:if>
<br>
<br>

<spring:message code="trip.description" var="description" />
<b><jstl:out value="${description }: " /></b>
<jstl:out value="${trip.description }" />
<br>
<br>

<spring:message code="trip.ticker" var="ticker" />
<b><jstl:out value="${ticker}: " /></b>
<jstl:out value="${trip.ticker}" />
<br>
<br>

<spring:message code="trip.price" var="price" />
<b><jstl:out value="${price }: " /></b>
<spring:message code="trip.englishPrice" var="englishPrice" />
<spring:message code="trip.spanishPrice" var="spanishPrice" />
<jstl:out value=" ${englishPrice}${trip.price } ${spanishPrice } " />
<br>
<br>

<spring:message code="trip.startDate" var="startDate" />
<b><jstl:out value="${startDate }: " /></b>
<jstl:out value="${trip.startDate}" />
<br>
<br>

<spring:message code="trip.endDate" var="endDate" />
<b><jstl:out value="${endDate }: " /></b>
<jstl:out value="${trip.endDate}" />
<br>
<br>

<spring:message code="trip.ranger" var="ranger" />
<b><jstl:out value="${ranger }: " /></b>
<jstl:out value="${trip.ranger.name}" />
<input type="button" name="seeProfile"
	value="<spring:message	code="trip.seeProfile" />"
	onclick="javascript: relativeRedir('actor/display.do?actorId=${trip.ranger.id}');" />
<br>
<br>

<spring:message code="trip.manager" var="manager" />
<b><jstl:out value="${manager }: " /></b>
<jstl:out value="${trip.manager.name }" />
<br>
<br>

<spring:message code="trip.category" var="category" />
<b><jstl:out value="${category }: " /></b>
<jstl:out value="${trip.category.name}" />
<br>
<br>

<spring:message code="trip.tags" var="tags" />
<b><jstl:out value="${tags }: " /></b>
<br>
<br>

<jstl:forEach var="tagValue" items="${trip.tagValues}">
	<div id="tags" style="margin: 20px;">
		<b><jstl:out value="${tagValue.tag.name}:" /></b>
		<jstl:out value="${tagValue.value }" />
		<br>
	</div>

	<security:authorize access="hasRole('MANAGER')">
		<jstl:if test="${trip.published==false }">
			<jstl:if test="${trip.manager.userAccount.username==user }">

				<input type="button" name="editTag"
					value="<spring:message	code="trip.edit.tag" />"
					onclick="javascript: relativeRedir('tagValue/manager/edit.do?tagValueId=${tagValue.id}');" />
				<br>
				<br>
			</jstl:if>
		</jstl:if>

	</security:authorize>
</jstl:forEach>

<security:authorize access="hasRole('MANAGER')">
	<jstl:if test="${trip.published==false }">
		<jstl:if test="${trip.manager.userAccount.username==user}">

			<input type="button" name="createTag"
				value="<spring:message	code="trip.create.tag" />"
				onclick="javascript: relativeRedir('tagValue/manager/create.do?tripId=${trip.id}');" />
		</jstl:if>
	</jstl:if>

</security:authorize>
<br>
<br>

<spring:message code="trip.legalText" var="legalText" />
<b><jstl:out value="${legalText }: " /></b>
<jstl:out value="${trip.legalText.title}" />
<br>
<br>

<spring:message code="trip.legalTextBody" var="legalTexBody" />
<b><jstl:out value="${legalTexBody }: " /></b>
<jstl:out value="${trip.legalText.body}" />
<br>
<br>

<spring:message code="trip.laws" var="laws" />
<b><jstl:out value="${laws}: " /></b>
<br>
<br>
<jstl:forEach var="applicableLaw"
	items="${trip.legalText.applicableLaws}">
	<jstl:out value="${applicableLaw}" />
</jstl:forEach>
<br>
<br>

<jstl:if test="${randomSponsorship != null}">
	<spring:message code="trip.sponsorship.banner" var="banner" />
	<jstl:out value="${banner }" />
	<br>
	<img src="${randomSponsorship.banner}" alt="${banner }"
		style="width: 304px; height: 228px">
</jstl:if>

<br>

<security:authorize access="hasRole('MANAGER')">
	<jstl:if test="${trip.manager.userAccount.username==user }">
		<jstl:if test="${trip.published==false }">


			<input type="button" name="edit"
				value="<spring:message	code="trip.edit" />"
				onclick="javascript: relativeRedir('trip/manager/edit.do?tripId=${trip.id}');" />


			<form id="publishForm" method="post" action="trip/manager/edit.do">
				<spring:message code="trip.publish" var="publishButton" />
				<input type="text" hidden="true" value="${trip.id }" name="tripId">
				<input type="submit" name="publish" value="${publishButton }">
			</form>
		</jstl:if>

		<jstl:if test="${trip.published }">
			<form id="cancelForm" method="post" action="trip/manager/edit.do">
				<spring:message code="trip.cancel" var="cancelButton" />
				<input type="text" hidden="true" value="${trip.id }" name="tripId">
				<input type="text" hidden="true" value="" name="cancelationReason"
					id="cancelationReasonInput" /> <input type="submit"
					onclick="cancelTrip()" name="cancel" value="${cancelButton }">
			</form>
		</jstl:if>
		<br>

		<input type="button" name="notesButton"
			value="<spring:message code="trip.seeNotes" />"
			onclick="javascript: relativeRedir('note/manager/list.do?tripId=${trip.id}');" />
	</jstl:if>
</security:authorize>

<input type="button" name="displayStages"
	value="<spring:message	code="trip.display.stages" />"
	onclick="javascript: relativeRedir('stage/list.do?tripId=${trip.id}');" />

<input type="button" name="auditionsButton"
	value="<spring:message code="trip.seeAuditions" />"
	onclick="javascript: relativeRedir('audition/list.do?tripId=${trip.id}');" />



<security:authorize access="hasRole('AUDITOR')">

	<input type="button" name="audit"
		value="<spring:message	code="trip.audit" />"
		onclick="javascript: relativeRedir('audition/auditor/create.do?tripId=${trip.id}');" />

</security:authorize>

<security:authorize access="hasRole('EXPLORER')">
	<jstl:if test="${isOver==true }">
		<input type="button" name="story"
			value="<spring:message	code="trip.story" />"
			onclick="javascript: relativeRedir('story/explorer/create.do?tripId=${trip.id}');" />
	</jstl:if>
	
	<jstl:if test="${canApplyDate==true }">
		<input type="button" name="apply"
			value="<spring:message	code="trip.apply" />"
			onclick="javascript: relativeRedir('application/explorer/create.do?tripId=${trip.id}');" />
	</jstl:if>
</security:authorize>

<security:authorize access="hasRole('SPONSOR')">

	<input type="button" name="sponsor"
		value="<spring:message	code="trip.sponsor" />"
		onclick="javascript: relativeRedir('sponsorship/sponsor/create.do?tripId=${trip.id}');" />

</security:authorize>



<input type="button" name="back"
	value="<spring:message code="trip.back" />"
	onclick="javascript: relativeRedir('trip/list.do');" />


<script type="text/javascript">
	$(document)
			.on(
					'submit',
					'form#cancelForm',
					function(e) {
						var cancelationReason = prompt("<spring:message code="trip.cancelationReason" />");
						if (cancelationReason == null
								|| cancelationReason == "") {
							e.preventDefault();
							alert('<spring:message code="trip.cancelationReason.error" />');
						} else {
							var cancelInput = document
									.getElementById("cancelationReasonInput");
							cancelInput
									.setAttribute("value", cancelationReason);
						}
					});
</script>
