<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp"%>

<c:set value="active" var="tab1Class"/>
<c:set value="tab-pane active" var="tab1PaneClass"/>

<c:set value="" var="tab2Class"/>
<c:set value="tab-pane" var="tab2PaneClass"/>


<c:set value="" var="tab3Class"/>
<c:set value="tab-pane" var="tab3PaneClass"/>

<c:if test="${openedTab == 2 || param.openedTab == 2}">
	<c:set value="" var="tab1Class"/>
	<c:set value="tab-pane" var="tab1PaneClass"/>
	
	<c:set value="active" var="tab2Class"/>
	<c:set value="tab-pane active" var="tab2PaneClass"/>
</c:if>

<c:if test="${openedTab == 3 || param.openedTab == 3}">
	<c:set value="" var="tab1Class"/>
	<c:set value="tab-pane" var="tab1PaneClass"/>
	
	<c:set value="active" var="tab3Class"/>
	<c:set value="tab-pane active" var="tab3PaneClass"/>
</c:if>
