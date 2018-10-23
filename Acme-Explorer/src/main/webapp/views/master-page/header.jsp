<%--
 * header.jsp
 *
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>


<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>

<div>
	<a title="Logo" href="/Acme-Explorer"><img src="${logo }"
		alt="Logo" height="120px" /></a>
</div>

<div>
	<ul id="jMenu">
		<!-- Do not forget the "fNiv" class for the first level links !! -->


		<!-- ----------------------------ADMINISTRATOR--------------------------------------- -->

		<security:authorize access="hasRole('ADMINISTRATOR')">
			<li><a class="fNiv"><spring:message
						code="master.page.sysconfig" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="system-config/administrator/edit.do"><spring:message
								code="master.page.sysconfig.edit" /></a></li>
				</ul>
			</li>

			<li><a href="actor/administrator/dashboard.do" class="fNiv"><spring:message
						code="master.page.administrator.dashboard" /></a>
			</li>
				
			<li><a class="fNiv"><spring:message
						code="master.page.administrator.suspicious" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="actor/administrator/list.do"><spring:message
								code="master.page.administrator.suspicious.list" /></a></li>
				</ul>
			</li>
				
			<li><a class="fNiv"><spring:message
						code="master.page.legalText" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="legal-text/administrator/list.do"><spring:message
								code="master.page.legalText.list" /></a></li>
				</ul>
			</li>

			<li><a class="fNiv"><spring:message
						code="master.page.administrator.register" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="actor/create-ranger.do"><spring:message
								code="master.page.administrator.register.ranger" /></a></li>
					<li><a href="actor/administrator/create-manager.do"><spring:message
								code="master.page.administrator.register.manager" /></a></li>
				</ul>
			</li>
				
			<li><a class="fNiv"><spring:message
						code="master.page.administrator.tag" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="tag/administrator/list.do"><spring:message
								code="master.page.trip.list" /></a></li>
				</ul>
			</li>

		</security:authorize>



		<!-- ----------------------------EXPLORER--------------------------------------- -->

		<security:authorize access="hasRole('EXPLORER')">
			<li><a class="fNiv"><spring:message
						code="master.page.application" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="application/explorer/list.do"><spring:message
								code="master.page.application.list" /></a></li>
				</ul>
			</li>
			
			<li><a class="fNiv"><spring:message
						code="master.page.survivalClass" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="survival-class/explorer/list.do"><spring:message
								code="master.page.survivalClass.list" /></a></li>
				</ul>
			</li>

			<li><a class="fNiv"><spring:message
						code="master.page.finder" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="finder/explorer/edit.do"><spring:message
								code="master.page.finder.edit" /></a></li>
				</ul>
			</li>

		</security:authorize>


		<!-- ----------------------------MANAGER--------------------------------------- -->


		<security:authorize access="hasRole('MANAGER')">
			<li><a class="fNiv"><spring:message
						code="master.page.application" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="application/manager/list.do"><spring:message
								code="master.page.application.list" /></a></li>
				</ul>
			</li>
			
			<li><a class="fNiv"><spring:message
						code="master.page.survivalClass" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="survival-class/manager/list.do"><spring:message
								code="master.page.survivalClass.list" /></a></li>
				</ul>
			</li>
		</security:authorize>



		<!-- ----------------------------AUDITOR--------------------------------------- -->


		<security:authorize access="hasRole('AUDITOR')">
			<li><a class="fNiv"><spring:message
						code="master.page.audition" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="audition/auditor/list.do"><spring:message
								code="master.page.audition.list" /></a></li>
				</ul>
			</li>
			
			<li><a class="fNiv"><spring:message code="master.page.note" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="note/auditor/list.do"><spring:message
								code="master.page.note.list" /></a></li>
				</ul>
			</li>
		</security:authorize>

		<!-- ----------------------------NOT LOGGED--------------------------------------- -->

		<security:authorize access="isAnonymous()">
			<li><a class="fNiv" href="security/login.do"><spring:message
						code="master.page.login" /></a></li>
		</security:authorize>

		<!--  DECIDED NOT TO DO IT THIS WAY, WE SHOW THE SPONSORSHIP's BANNER IN DISPLAY TRIP INSTEAD
		<li><a class="fNiv"><spring:message
					code="master.page.sponsorship" /></a>
			<ul>
				<li class="arrow"></li>
				<li><a href="sponsorship/list.do"><spring:message
							code="master.page.sponsorship.list" /></a></li>
			</ul></li>-->

		<li><a class="fNiv"><spring:message
					code="master.page.category" /></a>
			<ul>
				<li class="arrow"></li>
				<li><a href="category/list.do"><spring:message
							code="master.page.category.list" /></a></li>
			</ul>
		</li>



		<li><a class="fNiv"><spring:message code="master.page.trip" /></a>
			<ul>
				<li class="arrow"></li>
				<li><a href="trip/list.do"><spring:message
							code="master.page.trip.list" /></a></li>
				<security:authorize access="hasRole('MANAGER')">
					<li><a href="trip/manager/list.do"><spring:message
								code="master.page.trip.list.manager" /></a></li>
					<li><a href="trip/manager/create.do"><spring:message
								code="master.page.trip.create" /></a></li>
				</security:authorize>
			</ul>
		</li>



		<!-- ----------------------------LOGGED--------------------------------------- -->

		<security:authorize access="isAuthenticated()">
			<li><a class="fNiv"> <spring:message
						code="master.page.profile" /> (<security:authentication
						property="principal.username" />)</a>
				<ul>
					<li class="arrow"></li>
					<li><a href="actor/display-principal.do"><spring:message
								code="master.page.actor.display" /></a></li>
					<li><a href="folder/list.do?folderId=0"><spring:message
								code="master.page.messages" /> </a></li>
					<li><a href="j_spring_security_logout"><spring:message
								code="master.page.logout" /> </a></li>
				</ul>
			</li>
		</security:authorize>
	</ul>
</div>

<div>
	<a href="?language=en">en</a> | <a href="?language=es">es</a>
</div>
