
<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<p><spring:message code="application.edit" /></p>

<form:form action="application/explorer/edit.do" modelAttribute="application" method = "post">

	<form:hidden path="id"/>
	<form:hidden path="version"/>
	
	<form:hidden path="moment"/>
	<form:hidden path="status"/>
	<form:hidden path="explorer"/>
	
	<jstl:if test="${rejectedReason == null}">
		<form:hidden path="rejectedReason"/>
	</jstl:if>
	
	<jstl:if test="${status != 'DUE'}">
		<form:hidden path="creditCard"/>
	</jstl:if>
	<br>
	<security:authorize access="hasRole('EXPLORER')">
		<jstl:if test="${status == 'DUE'}">		
			<spring:message code="application.creditcard"/>
				<form:select path="creditCard">
			    	<form:option value="0" label="----" />
			    	<form:options items="${creditCardOptions}" itemValue="id" itemLabel="number" />
			  	</form:select>
			  	<form:errors cssClass="error" path="creditCard"></form:errors>
		</jstl:if>
	</security:authorize>
	
	<form:hidden path="trip" /> <!-- Comes from previous view -->
	
	<jstl:if test="${comments == null}">
		<form:label path="comments">
			<spring:message code="application.comments"/>
		</form:label>:
		<form:textarea path="comments" />
		<form:errors cssClass="error" path="comments"></form:errors>
	</jstl:if>
	<br>
	<jstl:if test="${comments != null}">
		<form:label path="comments">
			<spring:message code="application.comments"/>
		</form:label>:
		<form:textarea path="comments" disabled="true"/>
		<form:errors cssClass="error" path="comments"></form:errors>
	</jstl:if>
	
	<input type="submit" name="save" value="<spring:message code="application.save" />" />&nbsp;
	
	<input type="button" name="cancel" value="<spring:message code="application.cancel" />"
		onclick="javascript: relativeRedir('application/explorer/list.do');" />
		
</form:form>


