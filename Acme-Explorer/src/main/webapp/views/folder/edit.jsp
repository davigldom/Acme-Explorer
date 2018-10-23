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

<form:form action="folder/edit.do" modelAttribute="folder"> 
	
	<form:hidden path="id"/>
	<form:hidden path="version"/>
	<form:hidden path="sysFolder"/>
	<form:hidden path="children"/>
	<form:hidden path="messages"/>
		
	<form:label path="name">
		<spring:message code="folder.name" />:
	</form:label>
	<br/>
	<form:input path="name"/>
	<form:errors cssClass="error" path="name"></form:errors>
	<br/><br/>
		
	<jstl:if test="${folder.id==0}">
	<form:label path="root">
		<spring:message code="folder.root" />:
	</form:label>
	<br/>
	<form:select id="roots" path="root">
		<form:option value="0" label="----" />		
		<form:options items="${roots}" itemValue="id"
			itemLabel="name" />
	</form:select>
	<form:errors cssClass="error" path="root" />
	<br/>
	</jstl:if>
		
	<jstl:if test="${folder.id!=0}">
	<form:label path="root">
		<spring:message code="folder.root" />:
	</form:label>
	<br/>
	<form:select id="roots" path="root">
		<form:option value="0" label="----" />
		<jstl:forEach var="root" items="${roots}">
			<jstl:if test="${root.id == rootId }">
				<form:option selected="true" value="${root.id }">
					<jstl:out value="${root.name }" />
				</form:option>
			</jstl:if>
			<jstl:if test="${root.id != rootId }">
				<form:option value="${root.id }">
					<jstl:out value="${root.name }" />
				</form:option>
			</jstl:if>
		</jstl:forEach>
	</form:select>
	<form:errors cssClass="error" path="root" />
	<br/>
	</jstl:if>
		
	<br/>
		
			
	<input type="submit" name="save" value="<spring:message code="folder.save" />" />&nbsp; 
		
	<jstl:if test="${folder.id != 0}">
		<input type="submit" name="delete"
			value="<spring:message code="folder.delete" />"
			onclick="return confirm('<spring:message code="folder.confirm.delete" />')" />&nbsp;
	</jstl:if>
		
	<input type="button" name="cancel"
		value="<spring:message code="folder.cancel" />"
		onclick="javascript: window.location.replace('folder/list.do?folderId=${folder.id}')" />
			
</form:form>