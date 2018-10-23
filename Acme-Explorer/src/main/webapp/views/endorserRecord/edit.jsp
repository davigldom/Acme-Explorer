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

<form:form action="endorserRecord/ranger/edit.do" modelAttribute="endorserRecord" method = "post"> 

	<form:hidden path="id"/>
	<form:hidden path="version"/>
	<input type="text" hidden="true" value="${curriculumId }"
		name="curriculumId" />
	
	
	<form:label path="name">
		<spring:message code="endorserRecord.name"></spring:message>
	</form:label>
	<form:input path="name"/>
	<form:errors cssClass="error" path="name"></form:errors>
	<br/>
	
	<form:label path="email">
		<spring:message code="endorserRecord.email"></spring:message>
	</form:label>
	<form:input path="email"/>
	<form:errors cssClass="error" path="email"></form:errors>
	<br/>	
	
	<form:label path="phone">
		<spring:message code="endorserRecord.phone"></spring:message>
	</form:label>
	<form:input path="phone" id="phone" onblur="javascript: checkPhone();" placeholder="+34 (123) 1234"/>
	<form:errors cssClass="error" path="phone"></form:errors>
	<br/>
	
	<form:label path="linkedIn">
		<spring:message code="endorserRecord.linkedIn"></spring:message>
	</form:label>
	<form:input path="linkedIn"/>
	<form:errors cssClass="error" path="linkedIn"></form:errors>
	<br/>
	
	
	<form:label path="comments">
		<spring:message code="endorserRecord.comments"></spring:message>
	</form:label>
	<form:input path="comments"/>
	<form:errors cssClass="error" path="comments"></form:errors>
	<br/>
	
	
	
	<input type="submit" name="save" value="<spring:message code="endorserRecord.save"/>"/>
	
	<jstl:if test="${endorserRecord.id!=0 }">
	<input type="submit" name="delete" value="<spring:message code="endorserRecord.delete" />"/>		
	</jstl:if>
<!-- 	<input type="button" name="personalRecordButtonDelete" -->
<%-- 		value="<spring:message code="personalRecord.delete" />" --%>
<!-- 		onclick="myFunction()" /> -->
	
	<input type="button" name="cancel" value="<spring:message code="endorserRecord.cancel" />" 
		onclick="javascript: relativeRedir('/curriculum/display.do?curriculumId=${curriculumId}');" />
	
		
</form:form>

<spring:message code="phone.validation.check" var="phoneCheck"></spring:message>

<script type="text/javascript">

	function myFunction() {
		if (!confirm('Are you sure?')){
		    this.preventDefault();
		}
	}
	
	function checkPhone() {
		var target = document.getElementById("phone");
	    var input = target.value;
		var regExp1 = new RegExp("(^[+]([1-9]{1,3})) ([(][1-9]{1,3}[)]) (\\d{4,}$)");
		var regExp2 = new RegExp("(^[+]([1-9]{1,3})) (\\d{4,}$)");
		var regExp3 = new RegExp("(^\\d{4,}$)");
	    
		if('${phone}'!= input && regExp1.test(input)==false && regExp2.test(input)==false && regExp3.test(input)==false){
			if (confirm(input+" "+'${phoneCheck}') == false) {
		        target.focus();
		        target.select();
		    }
	    }else if('${phone}'!= input && regExp1.test(input)==false && regExp2.test(input)==false && regExp3.test(input)==true){
	    	target.value = '${defaultCountry}' + " " + input;
	    }
	}
</script>