<%--
 * edit.jsp
 *
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<form:form action="${requestURI }" modelAttribute="note"> 

	<form:hidden path="id"/>
	<form:hidden path="version"/>
	<form:hidden path="creationMoment"/>
	<form:hidden path="auditor"/>
	
	<security:authorize access="hasRole('AUDITOR')">		
		
		<form:label path="remark">
			<spring:message code="note.remark"></spring:message>:
		</form:label>
		<br/>
		<form:input path="remark"/>
		<form:errors cssClass="error" path="remark"></form:errors>
		<br/><br/>
	
		<form:label path="trip">
			<spring:message code="note.trip" />:
		</form:label>
		<br/>
		<form:select id="trips" path="trip">
			<form:option value="0" label="----" />		
			<form:options items="${trips}" itemValue="id"
				itemLabel="title" />
		</form:select>
		<form:errors cssClass="error" path="trip" />
		<br/><br/>
	</security:authorize>
	
	<security:authorize access="hasRole('MANAGER')">
		<form:hidden path="remark"/>
		<form:hidden path="trip"/>
		<form:hidden path="replyMoment"/>
		
		<form:label path="reply">
			<spring:message code="note.reply"></spring:message>:
		</form:label>
		<br/>
		<form:input path="reply"/>
		<form:errors cssClass="error" path="reply"></form:errors>
		<br/><br/>		
	</security:authorize>
	
		
	<input type="submit" name="save" value="<spring:message code="note.save" />" />&nbsp; 
	
	<security:authorize access="hasRole('AUDITOR')">
		<input type="button" name="cancel"
			value="<spring:message code="note.cancel" />"
			onclick="javascript: window.location.replace('note/auditor/list.do')" />
		<br/>
	</security:authorize>
	
	<security:authorize access="hasRole('MANAGER')">
		<input type="button" name="cancel"
			value="<spring:message code="note.cancel" />"
			onclick="javascript: window.location.replace('note/manager/list.do?tripId=${tripId}')" />
		<br/>
	</security:authorize>
		
</form:form>