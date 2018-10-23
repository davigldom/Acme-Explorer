<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<display:table pagesize="5" class="displaytag" keepStatus="true"
	name="managers" requestURI="${requestURI }" id="row">
	<!-- 
	We create a variable in order to check if the user is a manager or a trip is published,
	and show it if one of those cases
	 -->

	<!-- Attributes -->

	<spring:message code="manager.name" var="nameHeader" />
	<display:column property="name" title="${nameHeader}" sortable="true" />
	
	<spring:message code="manager.surname" var="surnameHeader" />
	<display:column property="surname" title="${surnameHeader}" sortable="true" /> 
	

	<display:column>
			<jstl:if test="${row.suspicious == true && row.banned == false}">
				 <form action = "actor/manager/ban.do" method = "post">
				 	<input type="hidden" name=managerId value="${row.id}"/>
					<input type="submit" name="true" value="<spring:message code="manager.ban" />" />
				</form>
			</jstl:if>
			<jstl:if test="${row.banned == true}">
				  <form action = "actor/manager/ban.do" method = "post">
				 	<input type="hidden" name=managerId value="${row.id}"/>
					<input type="submit" name="false" value="<spring:message code="manager.unban" />" />
				</form>
			</jstl:if>
	</display:column>
</display:table>

<display:table pagesize="5" class="displaytag" keepStatus="true"
	name="rangers" requestURI="${requestURI }" id="row">
	<!-- 
	We create a variable in order to check if the user is a manager or a trip is published,
	and show it if one of those cases
	 -->

	<!-- Attributes -->

	<spring:message code="ranger.name" var="nameHeader" />
	<display:column property="name" title="${nameHeader}" sortable="true" />
	
	<spring:message code="ranger.surname" var="surnameHeader" />
	<display:column property="surname" title="${surnameHeader}" sortable="true" /> 
	
	<display:column>
			<jstl:if test="${row.banned==false}">
				 <form action = "actor/ranger/edit.do" method = "post">
				 	<input type="hidden" name="rangerId" value="${row.id}"/>
					<input type="submit" name="true" value="<spring:message code="ranger.ban" />" />
				</form>
			</jstl:if>
			<jstl:if test="${row.banned == true}">
				 <form action = "actor/ranger/edit.do" method = "post">
				 	<input type="hidden" name="rangerId" value="${row.id}"/>
					<input type="submit" name="false" value="<spring:message code="ranger.unban" />" />
				</form>
			</jstl:if>
	</display:column>
	
</display:table>

	<!-- Action links -->
	
	
	<!-- TO EDIT
	<security:authorize access="hasRole('ADMIN')">
		<display:column>
			<a href="category/administrator/edit.do?categoryId=${row.id}">
				<spring:message	code="category.edit" />
			</a>
		</display:column>		
	</security:authorize>-->


<!-- TO CREATE
<security:authorize access="hasRole('ADMIN')">
	<div>
		<a href="category/administrator/create.do"> <spring:message
				code="category.create" />
		</a>
	</div>
</security:authorize>-->
