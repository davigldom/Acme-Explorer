<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt"	uri="http://java.sun.com/jsp/jstl/fmt"%> <!-- Añadida -->
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<display:table name="creditCards" id="row" requestURI="${requestURI}"
	pagesize="5" class="displaytag" keepStatus="true">
		
	<spring:message code="credit-card.brandname" var="brandHeader"/>
	<display:column property="brandName" title="${brandHeader}" sortable="false"/>
	
	<spring:message code="credit-card.holdername" var="holderHeader"/>
	<display:column property="holderName" title="${holderHeader}" sortable="false"/>
	
	<spring:message code="credit-card.number" var="numberHeader"/>
	<display:column property="number" title="${numberHeader}" sortable="false"/>
	
	<spring:message code="credit-card.cvv" var="cvvHeader"/>
	<display:column property="CVV" title="${cvvHeader}" sortable="false"/>
	
	<spring:message code="credit-card.expirationMonth" var="eMonthHeader"/>
	<display:column property="expirationMonth" title="${eMonthHeader}" sortable="false"/>
	
	<spring:message code="credit-card.expirationYear" var="eYearHeader"/>
	<display:column property="expirationYear" title="${eYearHeader}" sortable="false"/>
	
</display:table>

	<security:authorize access="hasRole('EXPLORER')">
		<div>
			<input type="button" name="create" value="<spring:message	code="credit-card.create" />" 
			 onclick="javascript: relativeRedir('credit-card/explorer/create.do');" />
		</div>
	</security:authorize>