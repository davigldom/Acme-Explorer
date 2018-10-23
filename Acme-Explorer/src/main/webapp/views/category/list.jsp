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


	<h2 style="display:inline-block;"><spring:message code="category.viewing"/>:  <jstl:out value="${category.name}"/> |</h2>
	<jstl:if test="${category.parent!=null}">
		<h3 style="display:inline-block;"><a href="category/list.do?categoryId=${category.parent.id}"><spring:message code="category.parent.goto"/></a></h3> <h2 style="display:inline-block;">|</h2>
	</jstl:if>
	<div style="display:inline-block;">
		<form action = "trip/search-category.do" method = "post">
			<input type="hidden" name="categoryId" value="${category.id}"/>
			<input type="submit" name="category" value="<spring:message code="category.trips" />"/>
		</form>
	</div> 
	
<display:table pagesize="5" class="displaytag" keepStatus="true"
	name="categories" requestURI="${requestURI}" id="row">
	
	<!-- Attributes -->
	
	<spring:message code="category.subcategories" var="subcategoriesHeader" />
	<display:column title="${subcategoriesHeader}" sortable="true">
	
		<a href="category/list.do?categoryId=${row.id}" title="<spring:message code="category.children.goto"/>"><jstl:out value="${row.name}"/></a>

	</display:column>


	<!-- Action links -->
	
	
	<display:column>
		<form action = "trip/search-category.do" method = "post">
			<input type="hidden" name="categoryId" value="${row.id}"/>
			<input type="submit" name="category" value="<spring:message code="category.trips" />" />
		</form>
	</display:column>
	
	
	<security:authorize access="hasRole('ADMINISTRATOR')">

		<display:column>
		
		<input type="button" name="editCategory" value="<spring:message code="category.edit" />" 
		onclick="javascript: relativeRedir('category/administrator/edit.do?categoryId=${row.id}');" />
			
		</display:column>	
				
	</security:authorize>

	</display:table>

	<security:authorize access="hasRole('ADMINISTRATOR')">
	
	<div>
		<input type="button" name="addChildren" value="<spring:message code="category.addChildren" />" 
		onclick="javascript: relativeRedir('category/administrator/create.do?categoryParentId=${category.id}');" />
	</div>
		
	</security:authorize> 
