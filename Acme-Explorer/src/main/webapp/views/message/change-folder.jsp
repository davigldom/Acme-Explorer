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
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<form method="post" action="message/edit.do">
	<input type="text" hidden="true" value="${messageId}" name="messageId" />

	<select id="folders" name="folderId">

		<jstl:forEach var="folder" items="${folders}">
			<option value="${folder.id }">
				<jstl:out value="${folder.name }" />
			</option>
		</jstl:forEach>

	</select> <br /> <br /> <input type="submit" name="changeFolder"
		value="<spring:message code="ourMessage.change" />" />&nbsp; <input
		type="button" name="cancel"
		value="<spring:message code="ourMessage.cancel" />"
		onclick="javascript: window.location.replace('folder/list.do?folderId=0')" />
	<br />


</form>