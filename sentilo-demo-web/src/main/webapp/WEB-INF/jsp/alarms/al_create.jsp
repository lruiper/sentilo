<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/jsp/common/header.jsp"%>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp"%>

<div class="container-fluid">
    <div class="content">
    <div class="row-fluid">

        <div id="menu-left" class="span3">

            <div class="sidebar-nav">
                <ul class="nav nav-list">
                    <li><a href="${linkVirtualSensor}"  data-original-title=""><i class="icon-signal"></i><span><spring:message code="menu.send.virtualSensor" /></span></a></li>
                    <li><a href="${linkAlarmCreate}" class="current" data-original-title=""><i class="icon-warning-sign"></i><span> <spring:message code="menu.send.alarm" /></span></a></li>
                    <li><a href="${linkOrderCreate}" data-original-title=""><i class="icon-hand-right"></i><span><spring:message code="menu.send.order" /></span></a></li>
                 </ul>
            </div> 
            <br>
        </div>

		
        <div id="widget" class="span9">
          <div class="row-fluid">
       
        <div class="span12"> <br>
	         <div class="pull-right logo"></div><br><br>   
	         <div class=""><h1 class="lead"><spring:message code="alarm.section.title"/><br><small><spring:message code="alarm.new.title"/> </small></h1>
	            </div>
	        </div> 
         </div>
         
             <div class="row-fluid">
                <div class="span12">
                
                <%@include file="/WEB-INF/jsp/common/messages.jsp" %>
                       

                   	<div class="accordion" id="detailAccordion">
                        <div class="accordion-group">
                            <div class="accordion-heading">
                                <a class="accordion-toggle" data-toggle="collapse" data-parent="#detailAccordion" href="#detailAccordionCollapse">
                                    <i class="icon-warning-sign"></i> 
                                    <spring:message code="alarm.subsection.alert"/>
                                    <i class="icon-chevron-down pull-right"></i>
                                </a>
                            </div>
                            <div id="detailAccordionCollapse" class="accordion-body collapse in">
                            
                                <div class="accordion-inner">
                                
                                    	<form:form method="post" modelAttribute="alarm" action="publishAlarm" class="form-horizontal">
                                    	
											<fieldset>
												<div class="control-group">
													<form:label path="alarmId" class="control-label">
														<spring:message code="alarm.id" />
													</form:label>
													<div class="controls">
														<form:input path="alarmId" />
														<form:errors path="alarmId" cssClass="text-error" htmlEscape="false" />
													</div>
												</div>												
												<div class="control-group">
													<form:label path="tokenAuth" class="control-label">
														<spring:message code="alarm.tokenAuth" />
													</form:label>
													<div class="controls">
														<form:input path="tokenAuth" />
														<form:errors path="tokenAuth" cssClass="text-error" htmlEscape="false" />
													</div>
												</div>
												<div class="control-group">
													<form:label path="message" class="control-label">
														<spring:message code="alarm.message" />
													</form:label>
													<div class="controls">
														<form:input path="message" />
														<form:errors path="message" cssClass="text-error" htmlEscape="false" />
													</div>
												</div>
																						
												<div class="controls">
													<a href="#" onclick="$('form#alarm').submit();" class="btn">
														<spring:message code="button.send"/>
													</a>
												</div>
											</fieldset>
										</form:form>
                                    
		                             </div>
		                         </div>
		                     </div>
		                 </div>
		             </div>
		         </div>

			</div>
		</div>
	</div>
</div>

<%@include file="/WEB-INF/jsp/common/footer.jsp" %>