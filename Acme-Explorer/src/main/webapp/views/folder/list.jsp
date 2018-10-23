<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<!-- Listing grid -->

<jstl:if test="${rootName!=null}">
<spring:message code="folder.title" var="folderTitle" />
<h2><jstl:out value="${folderTitle } ${rootName }"></jstl:out></h2>
</jstl:if>

<jstl:choose>
	<jstl:when test="${thereAreChildren==true}">
		<display:table pagesize="5" class="displaytag" keepStatus="false"
			name="folders" requestURI="folder/list.do" id="row">
	
		
			<!-- Attributes -->
		
			<spring:message code="folder.name" var="nameHeader" />
			<display:column property="name" title="${nameHeader}" sortable="true" />
	
	
			<!-- Action links -->
		
			<display:column>
				<input type="button"
					value="<spring:message code="folder.enter" />"
					onclick="javascript: window.location.replace('folder/list.do?folderId=${row.id}')" />
			</display:column>
		
			<display:column>
				<jstl:if test="${row.sysFolder==false}">
					<input type="button"
						value="<spring:message code="folder.edit" />"
						onclick="javascript: window.location.replace('folder/edit.do?folderId=${row.id}')" />
				</jstl:if>
			</display:column>		
		
		</display:table>

	</jstl:when>
	<jstl:otherwise>
		<spring:message code="folder.error" var="folderError" />
		<div><b><jstl:out value="${folderError }"></jstl:out></b></div>
		<br/>
	</jstl:otherwise>
</jstl:choose>

	<input type="button"
			value="<spring:message code="folder.create" />"
			onclick="javascript: window.location.replace('folder/create.do')" />
			
	<jstl:if test="${folderId!=0}">	
		<input type="button"
			value="<spring:message code="folder.backRoot" />"
			onclick="javascript: window.location.replace('folder/list.do?folderId=${rootOfRootId }')" />
		<br/><br/>
	</jstl:if>

<jstl:if test="${folderId!=0}">
	<jstl:choose>
		<jstl:when test="${messages[0]!=null }">
			<display:table pagesize="5" class="displaytag" keepStatus="true"
				name="messages" requestURI="folder/list.do" id="rowMessage">
			
				<!-- Attributes -->
				
				<spring:message code="message.moment" var="momentHeader" />
				<display:column property="moment" title="${momentHeader}" sortable="true" format="{0,date,dd/MM/yyyy HH:mm}" />
				
				<spring:message code="message.subject" var="subjectHeader" />
				<display:column property="subject" title="${subjectHeader}" sortable="true" />
			
				<spring:message code="message.priorityLevel" var="levelHeader" />
				<display:column property="priorityLevel" title="${levelHeader}" sortable="true" />
				
				<spring:message code="message.sender" var="senderHeader" />
				<display:column title="${senderHeader}" sortable="true">
				<jstl:out value="${rowMessage.actors[0].name } ${rowMessage.actors[0].surname }"></jstl:out>
				</display:column>
				
				<spring:message code="message.recipient" var="recipientHeader" />
				<display:column title="${recipientHeader}" sortable="true">
				<jstl:out value="${rowMessage.actors[1].name } ${rowMessage.actors[1].surname }"></jstl:out>
				</display:column>
				
				<display:column>
					<input type="button"
						value="<spring:message code="message.display" />"
						onclick="javascript: window.location.replace('message/display.do?messageId=${rowMessage.id}')" />
				</display:column>
				
				<display:column>
					<input type="button"
						value="<spring:message code="message.change" />"
						onclick="javascript: window.location.replace('message/change-folder.do?messageId=${rowMessage.id}')" />
				</display:column>
				
			</display:table>
		</jstl:when>
		<jstl:otherwise>
			<spring:message code="message.error" var="messageError" />
			<div><b><jstl:out value="${messageError }"></jstl:out></b></div>
			<br/>
		</jstl:otherwise>
	</jstl:choose>
</jstl:if>

<input type="button"
	value="<spring:message code="message.create" />"
	onclick="javascript: window.location.replace('message/create.do')" />
	
<security:authorize access="hasRole('ADMINISTRATOR')">
	<input type="button"
		value="<spring:message code="message.notification" />"
		onclick="javascript: window.location.replace('message/administrator/notification.do')" />
	<br/>
</security:authorize>
	