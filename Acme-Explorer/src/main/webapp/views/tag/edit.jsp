
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
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

<form:form action="tag/administrator/edit.do" modelAttribute="tag">

	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="tagValues" />



	<form:label path="name">
		<spring:message code="tag.name"></spring:message>
	</form:label>
	<form:input path="name" />
	<form:errors cssClass="error" path="name"></form:errors>
	<br />


	<input type="submit" name="save"
		value="<spring:message code="tag.save"/>" />

	<jstl:if test="${tag.id!=0}">
		<input type="submit" name="delete"
			value="<spring:message code="tag.delete"/>" />
	</jstl:if>


	<input type="button" name="cancel"
		value="<spring:message code="tag.cancel" />"
		onclick="javascript: relativeRedir('tag/administrator/list.do');" />

</form:form>