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

<form:form action="survival-class/manager/edit.do" modelAttribute="survivalClass"> 

	<form:hidden path="id"/>
	<form:hidden path="version"/>
	<form:hidden path="manager"/>
	
	<form:label path="title">
		<spring:message code="survivalClass.title" />:
	</form:label>
	<br/>
	<form:input path="title" size="30"/>
	<form:errors cssClass="error" path="title" ></form:errors>
	<br/><br/>
	
	<form:label path="description">
		<spring:message code="survivalClass.description" />:
	</form:label>
	<br/>
	<form:textarea path="description" rows="5" cols="40"/>
	<form:errors cssClass="error" path="description"></form:errors>
	<br/><br/>
	
	<form:label path="moment">
		<spring:message code="survivalClass.moment" />:
	</form:label>
	<br/>
	<form:input path="moment" placeholder="dd/MM/yyyy HH:mm"/>
	<form:errors cssClass="error" path="moment"></form:errors>
	<br/><br/>
	
	<form:label path="location">
		<spring:message code="survivalClass.location" />:
	</form:label>
	<br/>
	<form:input path="location" placeholder="Location-Latitude-Longitude" size="40"/>
	<form:errors cssClass="error" path="location"></form:errors>
	<br/><br/>
	
	<jstl:if test="${survivalClass.id==0}">
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
	<br/>
	</jstl:if>
	
	<jstl:if test="${survivalClass.id!=0}">
	<form:label path="trip">
		<spring:message code="survivalClass.trip" />:
	</form:label>
	<br/>
	<form:select id="trips" path="trip">
		<jstl:forEach var="trip" items="${trips}">
			<jstl:if test="${trip.id == tripId }">
				<form:option selected="true" value="${trip.id }">
					<jstl:out value="${trip.title }" />
				</form:option>
			</jstl:if>
			<jstl:if test="${trip.id != tripId }">
				<form:option value="${trip.id }">
					<jstl:out value="${trip.title }" />
				</form:option>
			</jstl:if>
		</jstl:forEach>
	</form:select>
	<form:errors cssClass="error" path="trip" />
	<br/>
	</jstl:if>

	<br/>
	
	<input type="submit" name="save" value="<spring:message code="survivalClass.save" />" />&nbsp; 
	
	<jstl:if test="${survivalClass.id != 0}">
		<input type="submit" name="delete"
			value="<spring:message code="survivalClass.delete" />"
			onclick="return confirm('<spring:message code="survivalClass.confirm.delete" />')" />&nbsp;
	</jstl:if>
	
	<input type="button" name="cancel"
		value="<spring:message code="survivalClass.cancel" />"
		onclick="javascript: window.location.replace('survival-class/manager/list.do')" />
	<br/>
	
		
</form:form>