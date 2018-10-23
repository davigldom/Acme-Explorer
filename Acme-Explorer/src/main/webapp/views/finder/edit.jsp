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


<form:form action="finder/explorer/edit.do" modelAttribute="finder"> 

	<form:hidden path="id"/>
	<form:hidden path="version"/>
	<form:hidden path="moment"/>
	<form:hidden path="trips"/>
	
	<form:label path="keyWord">
		<spring:message code="finder.keyWord"></spring:message>
	</form:label>
	<form:input path="keyWord"/>
	<form:errors cssClass="error" path="keyWord"></form:errors>
	<br/>
	
	<form:label path="priceRange.minPrice">
		<spring:message code="finder.minPrice"></spring:message>
	</form:label>
	<form:input path="priceRange.minPrice"/>
	<form:errors cssClass="error" path="priceRange.minPrice"></form:errors>
	<br/>
	
	<form:label path="priceRange.maxPrice">
		<spring:message code="finder.maxPrice"></spring:message>
	</form:label>
	<form:input path="priceRange.maxPrice"/>
	<form:errors cssClass="error" path="priceRange.maxPrice"></form:errors>
	<br/>
	
	<form:label path="dateRange.start">
		<spring:message code="finder.minDate"></spring:message>
	</form:label>
	<form:input path="dateRange.start" placeholder="dd/MM/yyyy HH:mm"/>
	<form:errors cssClass="error" path="dateRange.start"></form:errors>
	<br/>
	
	<form:label path="dateRange.end">
		<spring:message code="finder.maxDate"></spring:message>
	</form:label>
	<form:input path="dateRange.end" placeholder="dd/MM/yyyy HH:mm"/>
	<form:errors cssClass="error" path="dateRange.end"></form:errors>
	<br/>
	
	<input type="submit" name="save" value="<spring:message code="finder.save"/>"/>
	
	
	<input type="button" name="cancel" value="<spring:message code="finder.cancel" />" 
		onclick="javascript: relativeRedir('welcome/index.do');" />
		
</form:form>