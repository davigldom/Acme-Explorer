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

<form:form action="message/administrator/notification.do" modelAttribute="notification"> 

	<form:hidden path="id"/>
	<form:hidden path="version"/>
	<form:hidden path="moment"/>
	<form:hidden path="actors[0]"/>
	<form:hidden path="actors[1]"/>
	
	<form:label path="subject">
		<spring:message code="ourMessage.subject" />:
	</form:label>
	<br/>
	<form:input path="subject" size="50" />
	<form:errors cssClass="error" path="subject"></form:errors>
	<br/><br/>
			
	<form:label path="priorityLevel">
		<spring:message code="ourMessage.priorityLevel" />:
	</form:label>
	<br/>
	<form:select id="levels" path="priorityLevel">
		<spring:message code="ourMessage.high" var="messageHigh" />
		<spring:message code="ourMessage.med" var="messageMed" />
		<spring:message code="ourMessage.low" var="messageLow" />
		<form:option value="0" label="----" />		
		<form:option value="HIGH" label="${messageHigh }" />	
		<form:option value="MED" label="${messageMed }" />
		<form:option value="LOW" label="${messageLow }" />
	</form:select>
	<form:errors cssClass="error" path="priorityLevel" />
	<br/><br/>
			
	<form:label path="body">
		<spring:message code="ourMessage.body" />:
	</form:label>
	<br/>
	<form:textarea path="body" rows="25" cols="60"/>
	<form:errors cssClass="error" path="body"></form:errors>
	<br/><br/>
	
	<input type="submit" name="broadcast" value="<spring:message code="ourMessage.save" />" />&nbsp; 
	
	<input type="button" name="cancel"
		value="<spring:message code="ourMessage.cancel" />"
		onclick="javascript: window.location.replace('folder/list.do?folderId=0')" />
	<br/>
	
		
</form:form>