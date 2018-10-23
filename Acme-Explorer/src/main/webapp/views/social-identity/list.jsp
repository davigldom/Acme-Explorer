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


<security:authorize access="isAuthenticated()">
	<security:authentication property="principal.username" var="user" />
</security:authorize>
<!-- Listing grid -->


<display:table pagesize="5" class="displaytag" keepStatus="true"
	name="socialIdentities" requestURI="social-identity/list.do" id="row">


	<!-- Attributes -->

	<spring:message code="socialIdentity.nick" var="nickHeader" />
	<display:column property="nick" title="${nickHeader}" sortable="true" />

	<spring:message code="socialIdentity.nameOfSocialNetwork"
		var="socialNetworkHeader" />
	<display:column property="nameOfSocialNetwork"
		title="${socialNetworkHeader}" sortable="false" />

	<spring:message code="socialIdentity.link" var="linkHeader" />
	<display:column title="${linkHeader}" sortable="false">
		<a target="_blank" href="${row.link }"><jstl:out
				value="${row.link }" /></a>
	</display:column>

	<spring:message code="socialIdentity.photo" var="photoHeader" />
	<display:column title="${photoHeader}" sortable="false">
		<img src="${row.photo}" width="25%" height="25%	" alt="${row.nick }" />
	</display:column>

	<!-- Action links -->
	<security:authorize access="isAuthenticated()">

		<display:column>
			<jstl:if test="${user == username }">
				<input type="button" name="edit"
					value="<spring:message	code="socialIdentity.edit" />"
					onclick="javascript: relativeRedir('social-identity/edit.do?socialId=${row.id}');" />
			</jstl:if>
		</display:column>
	</security:authorize>
</display:table>

<br>

<security:authorize access="isAuthenticated()">
	<jstl:if test="${user == username }">
		<input type="button" name="create"
			value="<spring:message	code="socialIdentity.create" />"
			onclick="javascript: relativeRedir('social-identity/create.do');" />
	</jstl:if>
</security:authorize>
<input type="button" name="create"
	value="<spring:message	code="socialIdentity.back" />"
	onclick="javascript: relativeRedir('actor/display.do?actorId=${actorId}');" />
