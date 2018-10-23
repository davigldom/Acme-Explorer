<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>


<!--TODO: Guardar el trip al que realiza la auditoria y mostrarlo de alguna manera para que al editar o crear, pueda recordar de que trip provenía -->
<jstl:choose>
	<jstl:when test="${draft==true}">
	
		<form:form action="audition/auditor/edit.do" modelAttribute="audition" method="post"> 
		
			<form:hidden path="id"/>
			<form:hidden path="version"/>
			<form:hidden path="auditor"/>
			<form:hidden path="trip"/>
			<form:hidden path="moment"/>
			
			
			<form:label path="title">
				<spring:message code="audition.title"></spring:message>
			</form:label>
			<form:input path="title"/>
			<form:errors cssClass="error" path="title"></form:errors>
			<br/>
			
			<form:label path="description">
				<spring:message code="audition.description"></spring:message>
			</form:label>
			<form:textarea path="description"/>
			<form:errors cssClass="error" path="description"></form:errors>
			<br/>
			
			
			<jstl:if test="${audition.id==0}">
				<form:hidden path="draft"/>
			</jstl:if>
			
			<jstl:if test="${audition.id!=0}">
				<form:label path="draft">
					<spring:message code="audition.draft"></spring:message>
				</form:label>
				<form:checkbox path="draft"/>
			</jstl:if>
			<br/>
			
			<jstl:choose>
				<jstl:when test="${audition.id==0 || attachments==null}">
					<form:hidden path="attachments"/>
				</jstl:when>
				<jstl:otherwise>
					<jstl:if test="${audition.id!=0}">
						<form:label path="attachments">
							<spring:message code="audition.attachments"></spring:message>
						</form:label>
					</jstl:if>
					<br/>
					<jstl:forEach var="attachment" items="${audition.attachments }">
						<form:input value="${attachment}" path="attachments"/>
						<form:errors cssClass="error" path="attachments"></form:errors>
						<br/>
					</jstl:forEach>
				</jstl:otherwise>
			</jstl:choose>
	
			<br/>
			
			<input type="submit" name="save" value="<spring:message code="audition.save"/>"/>
			
			<jstl:if test="${audition.id!=0}">
				<input type="submit" name="delete" value="<spring:message code="audition.delete"/>"/>
			</jstl:if>
			
			<input type="button" name="cancel" value="<spring:message code="audition.cancel" />" 
				onclick="javascript: relativeRedir('audition/auditor/list.do');" />
				
		</form:form>
	</jstl:when>
	
	
	<jstl:otherwise>
		<spring:message code="audition.cannot.edit" var="cannotEdit" />
		<jstl:out value="${cannotEdit}" />
	</jstl:otherwise>

</jstl:choose>