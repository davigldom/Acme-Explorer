<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>




<form:form action="trip/manager/edit.do" modelAttribute="trip">
	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="manager" />
	<form:hidden path="sponsorships" />
	<form:hidden path="survivalClasses" />
	<%-- <form:hidden path="price"/> --%>
	<form:hidden path="cancelled" />
	<form:hidden id="cancelationReasonInput" path="cancelationReason" />
	<form:hidden path="published" />
	<form:hidden path="auditions" />
	<form:hidden path="notes" />
	<form:hidden path="applications" />
	<form:hidden path="publication" />
	<form:hidden path="tagValues" />
	<form:hidden path="stages" />
	<form:hidden path="ticker" />



	<form:label path="title">
		<spring:message code="trip.title" />
	</form:label>
	<form:input path="title" />
	<form:errors cssClass="error" path="title" />
	<br />

	<form:label path="price">
		<spring:message code="trip.price" />
	</form:label>
	<form:input path="price" disabled="true" />
	<form:errors cssClass="error" path="price" />
	<br />



	<%-- 	<div id="cancelationReasonDiv" style="display: none">
		<form:label path="cancelationReason">
			<spring:message code="trip.cancelationReason" />
		</form:label>
		<form:input id="cancelationReasonInput" path="cancelationReason" />
		<form:errors cssClass="error" path="cancelationReason" />
		<br />
	</div> --%>

	<form:label path="description">
		<spring:message code="trip.description" />
	</form:label>
	<form:input path="description" />
	<form:errors cssClass="error" path="description" />
	<br />

	<form:label path="startDate">
		<spring:message code="trip.startDate" />
	</form:label>
	<form:input path="startDate" placeholder="dd/MM/yyyy HH:mm" />
	<form:errors cssClass="error" path="startDate" />
	<br />

	<form:label path="endDate">
		<spring:message code="trip.endDate" />
	</form:label>
	<form:input path="endDate" placeholder="dd/MM/yyyy HH:mm" />
	<form:errors cssClass="error" path="endDate" />
	<br />

	<form:label path="requirements">
		<spring:message code="trip.requirements" />
	</form:label>
	<form:input path="requirements" />
	<form:errors cssClass="error" path="requirements" />
	<br />

	<form:label path="ranger">
		<spring:message code="trip.ranger" />
	</form:label>
	<form:select path="ranger">
		<jstl:forEach var="ranger" items="${rangers}">
			<jstl:if test="${ranger.id == trip.ranger.id }">
				<form:option selected="true" value="${ranger.id}">
					<jstl:out value="${ranger.name}" />
				</form:option>
			</jstl:if>
			<jstl:if test="${ranger.id != trip.ranger.id}">
				<form:option value="${ranger.id}">
					<jstl:out value="${ranger.name}" />
				</form:option>
			</jstl:if>
		</jstl:forEach>
	</form:select>
	<form:errors cssClass="error" path="ranger" />
	<br />

	<form:label path="category">
		<spring:message code="trip.category" />
	</form:label>
	<form:select path="category">
		<jstl:forEach var="category" items="${categories}">
			<jstl:if test="${category.id==trip.category.id }">
				<form:option selected="true" value="${category.id}">
					<spring:message code="trip.categoryParenthood" var="parenthood" />
					<jstl:choose>
						<jstl:when test="${category.name=='CATEGORY' }">
							<jstl:out value="${category.name}" />
						</jstl:when>
						<jstl:otherwise>
							<jstl:out
								value="${category.name}   (${parenthood} ${category.parent.name })" />
						</jstl:otherwise>
					</jstl:choose>
				</form:option>
			</jstl:if>

			<jstl:if test="${category.id!=trip.category.id}">
				<form:option value="${category.id}">
					<spring:message code="trip.categoryParenthood" var="parenthood" />
					<jstl:choose>
						<jstl:when test="${category.name=='CATEGORY' }">
							<jstl:out value="${category.name}" />
						</jstl:when>
						<jstl:otherwise>
							<jstl:out
								value="${category.name}   (${parenthood} ${category.parent.name })" />
						</jstl:otherwise>
					</jstl:choose>
				</form:option>
			</jstl:if>



		</jstl:forEach>
	</form:select>
	<form:errors cssClass="error" path="category" />
	<br />

	<form:label path="legalText">
		<spring:message code="trip.legalText" />
	</form:label>
	<form:select path="legalText">
		<jstl:forEach var="legalText" items="${legalTexts}">
			<jstl:if test="${legalText.id == trip.legalText.id }">
				<form:option selected="true" value="${legalText.id}">
					<jstl:out value="${legalText.title}" />
				</form:option>
			</jstl:if>

			<jstl:if test="${legalText.id != trip.legalText.id}">
				<form:option value="${legalText.id}">
					<jstl:out value="${legalText.title}" />
				</form:option>
			</jstl:if>

		</jstl:forEach>
	</form:select>
	<form:errors cssClass="error" path="legalText" />
	<br />



	<security:authorize access="hasRole('MANAGER')">
		<jstl:if test="${trip.id == 0 || trip.published==false}">
			<input type="submit" name="save"
				value="<spring:message code="trip.save" />">&nbsp;
			</jstl:if>
		<jstl:if test="${trip.id != 0}">
			<jstl:if test="${trip.published == false}">

				<input type="submit" name="delete"
					value="<spring:message code="trip.delete"/>" />
			</jstl:if>


		</jstl:if>


		<input type="button" name="back"
			value="<spring:message code="trip.back" />"
			onclick="javascript: relativeRedir('trip/manager/list.do');" />
	</security:authorize>
</form:form>


<script type="text/javascript">
	function cancelTrip() {
		var cancelationReason = prompt("<spring:message code="trip.cancelationReason" />");
		var cancelInput = document.getElementById("cancelationReasonInput");
		cancelInput.setAttribute("value", cancelationReason);

	}
</script>