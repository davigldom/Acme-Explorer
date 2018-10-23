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

<form:form action="educationRecord/ranger/edit.do" modelAttribute="educationRecord" method = "post"> 

	<form:hidden path="id"/>
	<form:hidden path="version"/>
	<input type="text" hidden="true" value="${curriculumId }"
		name="curriculumId" />
	
	<form:label path="titleOfDiploma">
		<spring:message code="educationRecord.titleOfDiploma"></spring:message>
	</form:label>
	<form:input path="titleOfDiploma"/>
	<form:errors cssClass="error" path="titleOfDiploma"></form:errors>
	<br/>
	
	<form:label path="institution">
		<spring:message code="educationRecord.institution"></spring:message>
	</form:label>
	<form:input path="institution"/>
	<form:errors cssClass="error" path="institution"></form:errors>
	<br/>
	
	<form:label path="attachment">
		<spring:message code="educationRecord.attachment"></spring:message>
	</form:label>
	<form:input path="attachment"/>
	<form:errors cssClass="error" path="attachment"></form:errors>
	<br/>
	
	<form:label path="comments">
		<spring:message code="educationRecord.comments"></spring:message>
	</form:label>
	<form:textarea path="comments"/>
	<form:errors cssClass="error" path="comments"></form:errors>
	<br/>
	
	<form:label path="educationPeriod.start">
		<spring:message code="educationRecord.educationPeriodStart" />
	</form:label>
	<form:input path="educationPeriod.start" placeholder="dd/MM/yyyy HH:mm" />
	<form:errors cssClass="error" path="educationPeriod.start" />
	<br />
	
	<form:label path="educationPeriod.end">
		<spring:message code="educationRecord.educationPeriodEnd" />
	</form:label>
	<form:input path="educationPeriod.end" placeholder="dd/MM/yyyy HH:mm" />
	<form:errors cssClass="error" path="educationPeriod.end" />
	<br />
	
	
	<input type="submit" name="save" value="<spring:message code="educationRecord.save"/>"/>
	
	<jstl:if test="${educationRecord.id!=0 }">
	<input type="submit" name="delete" value="<spring:message code="educationRecord.delete" />"/>
	</jstl:if>
	
	<input type="button" name="cancel" value="<spring:message code="educationRecord.cancel" />" 
		onclick="javascript: relativeRedir('curriculum/display.do?curriculumId=${curriculumId}');" />
		
</form:form>