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


<form:form action="category/administrator/edit.do" modelAttribute="category" method = "post"> 

	<spring:message code="category.editing"></spring:message> ${category.parent.name }
	<br>
	<br>	

	<form:hidden path="id"/>
	<form:hidden path="version"/>
	<form:hidden path="parent" />
	<form:hidden path="trips" />
	<form:hidden path="children" />
	
	<form:label path="name">
		<spring:message code="category.name"></spring:message>
	</form:label>
	<form:input path="name"/>
	<form:errors cssClass="error" path="name"></form:errors>
	<br/>
	

	<br/>
	
	
	
	<input type="submit" name="save" value="<spring:message code="category.save"/>"/>
	
	<jstl:if test="${category.id!=0 }">
	
			<input type="submit" name="delete" value="<spring:message code="category.delete" />"/>		
		
	</jstl:if>
	
<!-- 	<input type="button" name="personalRecordButtonDelete" -->
<%-- 		value="<spring:message code="personalRecord.delete" />" --%>
<!-- 		onclick="myFunction()" /> -->
	
	<input type="button" name="cancel" value="<spring:message code="personalRecord.cancel" />" 
		onclick="javascript: relativeRedir('category/list.do?categoryId=${category.parent.id}	');" />
	
		
</form:form>