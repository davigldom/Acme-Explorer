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
<spring:message code="curriculum.ticker" var="tickerTitle" />
<jstl:out value="${tickerTitle }: " />
<jstl:out value="${curriculum.ticker }" />
<br>


<spring:message code="curriculum.personalRecord"
	var="personalRecordTitle" />
<h3>
	<jstl:out value="${personalRecordTitle }: " />
</h3>
<div>
	<img
		src="${personalRecord.photo }"
		alt="photo" height="200" width="200">

	<spring:message code="personalRecord.name" var="name" />
	<p>
		<jstl:out value="${name}: ${curriculum.personalRecord.name}"></jstl:out>
	</p>

	<spring:message code="personalRecord.email" var="email" />
	<p>
		<jstl:out value="${email}: ${curriculum.personalRecord.email}"></jstl:out>
	</p>

	<spring:message code="personalRecord.phone" var="phone" />
	<p>
		<jstl:out value="${phone}: ${curriculum.personalRecord.phone}"></jstl:out>
	</p>

	<spring:message code="personalRecord.linkedIn" var="linkedIn" />
	<jstl:out value="${linkedIn}: "></jstl:out>
	<a href="${curriculum.personalRecord.linkedIn}">${curriculum.personalRecord.linkedIn}</a>


	<br> <br>
	<security:authorize access="hasRole('RANGER')">
		<jstl:if test="${user == curriculum.ranger.userAccount.username }">
			<input type="button" name="personalRecordButtonEdit"
				value="<spring:message code="personalRecord.edit" />"
				onclick="javascript: relativeRedir('personalRecord/ranger/edit.do?personalRecordId=${curriculum.personalRecord.id}&curriculumId=${curriculum.id }');" />
		</jstl:if>
	</security:authorize>
</div>


<spring:message code="curriculum.educationRecord"
	var="educationRecordTitle" />
<h3>
	<jstl:out value="${educationRecordTitle }: " />
</h3>
<div>
	<display:table name="curriculum.educationRecords" id="row"
		requestURI="${requestURI }" pagesize="3" class="displaytag" keepStatus="true">

		<spring:message code="educationRecord.titleOfDiploma"
			var="titleOfDiploma" />
		<display:column property="titleOfDiploma" title="${titleOfDiploma}"
			sortable="false" />

		<spring:message code="educationRecord.institution" var="institution" />
		<display:column property="institution" title="${institution}"
			sortable="false" />

		<spring:message code="educationRecord.attachment" var="attachment" />
		<display:column title="${attachment}">
			<a href="${row.attachment}">${row.attachment}</a>
		</display:column>

		<spring:message code="educationRecord.comments" var="comments" />
		<display:column property="comments" title="${comments}"
			sortable="false" />

		<spring:message code="educationRecord.educationPeriodStart"
			var="educationPeriodStart" />
		<display:column title="${educationPeriodStart}">
			<jstl:out value="${row.educationPeriod.start}"></jstl:out>
		</display:column>

		<spring:message code="educationRecord.educationPeriodEnd"
			var="educationPeriodEnd" />
		<display:column title="${educationPeriodEnd}">
			<jstl:out value="${row.educationPeriod.end}"></jstl:out>
		</display:column>
		<display:column>
			<security:authorize access="hasRole('RANGER')">
				<jstl:if test="${user == curriculum.ranger.userAccount.username }">
					<input type="button" name="educationRecordEdit"
						value="<spring:message code="educationRecord.edit" />"
						onclick="javascript: relativeRedir('educationRecord/ranger/edit.do?educationRecordId=${row.id }&curriculumId=${curriculum.id }');" />
				</jstl:if>
			</security:authorize>
		</display:column>
	</display:table>
	<security:authorize access="hasRole('RANGER')">
		<jstl:if test="${user == curriculum.ranger.userAccount.username }">
			<input type="button" name="educationRecordCreate"
				value="<spring:message code="educationRecord.create" />"
				onclick="javascript: relativeRedir('educationRecord/ranger/create.do?curriculumId=${curriculum.id }');" />
		</jstl:if>
	</security:authorize>
</div>





<spring:message code="curriculum.professionalRecord"
	var="professionalRecordTitle" />
<h3>
	<jstl:out value="${professionalRecordTitle }: " />
</h3>
<div>
	<display:table name="curriculum.professionalRecords" id="row"
		requestURI="${requestURI }" pagesize="3" class="displaytag" keepStatus="true">

		<spring:message code="professionalRecord.companyName"
			var="companyName" />
		<display:column property="companyName" title="${companyName}"
			sortable="false" />

		<spring:message code="professionalRecord.role" var="role" />
		<display:column property="role" title="${role}" sortable="false" />

		<spring:message code="professionalRecord.attachment" var="attachment" />
		<display:column title="${attachment}">
			<a href="${row.attachment}">${row.attachment}</a>
		</display:column>

		<spring:message code="professionalRecord.comments" var="comments" />
		<display:column property="comments" title="${comments}"
			sortable="comments" />

		<spring:message code="professionalRecord.professionalPeriodStart"
			var="professionalPeriodStart" />
		<display:column title="${professionalPeriodStart}">
			<jstl:out value="${row.professionalPeriod.start}"></jstl:out>
		</display:column>

		<spring:message code="professionalRecord.professionalPeriodEnd"
			var="professionalPeriodEnd" />
		<display:column title="${professionalPeriodEnd}">
			<jstl:out value="${row.professionalPeriod.end}"></jstl:out>
		</display:column>
		<display:column>
			<security:authorize access="hasRole('RANGER')">
				<jstl:if test="${user == curriculum.ranger.userAccount.username }">
					<input type="button" name="professionalRecordEdit"
						value="<spring:message code="professionalRecord.edit" />"
						onclick="javascript: relativeRedir('professionalRecord/ranger/edit.do?professionalRecordId=${row.id }&curriculumId=${curriculum.id }');" />
				</jstl:if>
			</security:authorize>
		</display:column>
	</display:table>
	<security:authorize access="hasRole('RANGER')">
		<jstl:if test="${user == curriculum.ranger.userAccount.username }">
			<input type="button" name="professionalRecordCreate"
				value="<spring:message code="professionalRecord.create" />"
				onclick="javascript: relativeRedir('professionalRecord/ranger/create.do?curriculumId=${curriculum.id }');" />
		</jstl:if>
	</security:authorize>
</div>


<spring:message code="curriculum.endorserRecord"
	var="endorserRecordTitle" />
<h3>
	<jstl:out value="${endorserRecordTitle }: " />
</h3>
<div>
	<display:table name="curriculum.endorserRecords" id="row"
		requestURI="${requestURI }" pagesize="3" class="displaytag" keepStatus="true">

		<spring:message code="endorserRecord.name" var="name" />
		<display:column property="name" title="${name}" sortable="false" />

		<spring:message code="endorserRecord.email" var="email" />
		<display:column property="email" title="${email}" sortable="false" />

		<spring:message code="endorserRecord.phone" var="phone" />
		<display:column property="phone" title="${phone}" sortable="false" />

		<spring:message code="endorserRecord.linkedIn" var="linkedIn" />
		<display:column title="${linkedIn}">
			<a href="${row.linkedIn}">${row.linkedIn}</a>
		</display:column>

		<spring:message code="endorserRecord.comments" var="comments" />
		<display:column property="comments" title="${comments}"
			sortable="false" />

		<display:column>
			<security:authorize access="hasRole('RANGER')">
				<jstl:if test="${user == curriculum.ranger.userAccount.username }">
					<input type="button" name="endorserRecordEdit"
						value="<spring:message code="endorserRecord.edit" />"
						onclick="javascript: relativeRedir('endorserRecord/ranger/edit.do?endorserRecordId=${row.id }&curriculumId=${curriculum.id }');" />
				</jstl:if>
			</security:authorize>
		</display:column>
	</display:table>
	<security:authorize access="hasRole('RANGER')">
		<jstl:if test="${user == curriculum.ranger.userAccount.username }">
			<input type="button" name="endorserRecordCreate"
				value="<spring:message code="endorserRecord.create" />"
				onclick="javascript: relativeRedir('endorserRecord/ranger/create.do?curriculumId=${curriculum.id }');" />
		</jstl:if>
	</security:authorize>
</div>


<spring:message code="curriculum.miscellaneousRecord"
	var="miscellaneousRecordTitle" />
<h3>
	<jstl:out value="${miscellaneousRecordTitle }: " />
</h3>
<div>
	<display:table name="curriculum.miscellaneousRecords" id="row"
		requestURI="${requestURI }" pagesize="3" class="displaytag" keepStatus="true">

		<spring:message code="miscellaneousRecord.title" var="title" />
		<display:column property="title" title="${title}" sortable="false" />

		<spring:message code="miscellaneousRecord.attachment" var="attachment" />
		<display:column title="${attachment}">
			<a href="${row.attachment}">${row.attachment}</a>
		</display:column>

		<spring:message code="miscellaneousRecord.comments" var="comments" />
		<display:column property="comments" title="${comments}"
			sortable="false" />
		<display:column>
			<security:authorize access="hasRole('RANGER')">
				<jstl:if test="${user == curriculum.ranger.userAccount.username }">
					<input type="button" name="miscellaneousRecordEdit"
						value="<spring:message code="miscellaneousRecord.edit" />"
						onclick="javascript: relativeRedir('miscellaneousRecord/ranger/edit.do?miscellaneousRecordId=${row.id }&curriculumId=${curriculum.id }');" />
				</jstl:if>
			</security:authorize>
		</display:column>
	</display:table>
	
</div>

	<security:authorize access="hasRole('RANGER')">
		<jstl:if test="${user == curriculum.ranger.userAccount.username }">
			<input type="button" name="miscellaneousRecordCreate"
				value="<spring:message code="miscellaneousRecord.create" />"
				onclick="javascript: relativeRedir('miscellaneousRecord/ranger/create.do?curriculumId=${curriculum.id }');" />
		</jstl:if>
	</security:authorize>



<!-- <input type="button" name="personalRecordButton" -->
<%-- value="<spring:message code="curriculum.personalRecord" />" --%>
<%-- onclick="javascript: relativeRedir('curriculum/personalRecord/display.do?curriculumId=${curriculum.id}');" /> --%>
<!-- <br/> -->

<!-- <input type="button" name="educationRecordButton" -->
<%-- value="<spring:message code="curriculum.educationRecord" />" --%>
<%-- onclick="javascript: relativeRedir('curriculum/educationRecord/display.do?curriculumId=${curriculum.id}');" /> --%>
<!-- <br/> -->

<!-- <input type="button" name="professionalRecordButton" -->
<%-- value="<spring:message code="curriculum.professionalRecord" />" --%>
<%-- onclick="javascript: relativeRedir('curriculum/professionalRecord/display.do?curriculumId=${curriculum.id}');" /> --%>
<!-- <br/> -->

<!-- <input type="button" name="endorserRecordButton" -->
<%-- value="<spring:message code="curriculum.endorserRecord" />" --%>
<%-- onclick="javascript: relativeRedir('curriculum/endorserRecord/display.do?curriculumId=${curriculum.id}');" /> --%>
<!-- <br/> -->

<!-- <input type="button" name="miscellaneousRecordButton" -->
<%-- value="<spring:message code="curriculum.miscellaneousRecord" />" --%>
<%-- onclick="javascript: relativeRedir('curriculum/miscellaneousRecord/display.do?curriculumId=${curriculum.id}');" /> --%>


<security:authorize access="hasRole('RANGER')">
	<jstl:if test="${user == curriculum.ranger.userAccount.username }">
		<form:form action="curriculum/ranger/edit.do"
			modelAttribute="curriculum" method="post">
			<form:hidden path="id" />
			<form:hidden path="version" />
			<form:hidden path="ranger" />
			<form:hidden path="ticker" />
			<form:hidden path="personalRecord" />
			<form:hidden path="miscellaneousRecords" />
			<form:hidden path="educationRecords" />
			<form:hidden path="professionalRecords" />
			<form:hidden path="endorserRecords" />

			<input type="submit" name="delete"
				value="<spring:message code="curriculum.delete"/>" />

			<%--<jstl:if test="${audition.id!=0}">
			<input type="submit" name="delete" value="<spring:message code="audition.delete"/>"/>
			</jstl:if> --%>



		</form:form>
	</jstl:if>
</security:authorize>


<input type="button" name="back"
	value="<spring:message code="trip.back" />"
	onclick="javascript: relativeRedir('actor/display.do?actorId=${curriculum.ranger.id}');" />
