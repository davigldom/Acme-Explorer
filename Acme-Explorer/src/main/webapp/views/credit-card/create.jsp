<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
	
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">


<form:form action="credit-card/explorer/edit.do" modelAttribute="creditCard" method = "post"> 

	<form:hidden path="id"/>
	<form:hidden path="version"/>
	
	<form:hidden path="explorer"/>
	
	<form:label path="brandName">
		<spring:message code="credit-card.brandname"></spring:message>
	</form:label>
	<form:input path="brandName"/>
	<form:errors cssClass="error" path="brandName"></form:errors>
	<br/>
	
	<form:label path="holderName">
		<spring:message code="credit-card.holdername"></spring:message>
	</form:label>
	<form:input path="holderName"/>
	<form:errors cssClass="error" path="holderName"></form:errors>
	<br/>
	
	<form:label path="number">
		<spring:message code="credit-card.number"></spring:message>
	</form:label>
	<form:input path="number"/>
	<form:errors cssClass="error" path="number"></form:errors>
	<br/>

	<form:label path="CVV">
		<spring:message code="credit-card.cvv"></spring:message>
	</form:label>
	<form:input path="CVV"/>
	<form:errors cssClass="error" path="CVV"></form:errors>
	<br/>

	<form:label path="expirationMonth">
		<spring:message code="credit-card.expirationMonth"></spring:message>
	</form:label>
	<form:input path="expirationMonth"/>
	<form:errors cssClass="error" path="expirationMonth"></form:errors>
	<br/>

	<form:label path="expirationYear">
		<spring:message code="credit-card.expirationYear"></spring:message>
	</form:label>
	<form:input path="expirationYear"/>
	<form:errors cssClass="error" path="expirationYear"></form:errors>
	<br/>

	<input type="submit" name="save" value="<spring:message code="credit-card.save"/>"/>
	
	<input type="button" name="cancel" value="<spring:message code="credit-card.cancel" />" 
		onclick="javascript: relativeRedir('actor/display-principal.do');" />
		
</form:form>