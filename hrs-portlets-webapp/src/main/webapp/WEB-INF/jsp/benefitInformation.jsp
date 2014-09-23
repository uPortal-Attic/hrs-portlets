<%--

    Licensed to Jasig under one or more contributor license
    agreements. See the NOTICE file distributed with this work
    for additional information regarding copyright ownership.
    Jasig licenses this file to you under the Apache License,
    Version 2.0 (the "License"); you may not use this file
    except in compliance with the License. You may obtain a
    copy of the License at:

    http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing,
    software distributed under the License is distributed on
    an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
    KIND, either express or implied. See the License for the
    specific language governing permissions and limitations
    under the License.

--%>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ include file="/WEB-INF/jsp/include.jsp"%>
<%@ include file="/WEB-INF/jsp/header.jsp"%>

<div id="${n}dl-benefit-summary" class="fl-widget portlet dl-benefit-summary">
  <div class="dl-banner-links">
    <c:choose>
    <c:when test="${enrollmentFlag == 'O'}">
      <div class="dl-banner-link">
        <spring:message code="benefit.summary.enrollment.O.message" text="You have a benefit enrollment opportunity. Please enroll online by clicking the following link: "/>
        <a target="_blank" href="${hrsUrls['Open Enrollment/Hire Event']}"><spring:message code="open.enrollment" text="Open Enrollment" /></a>
      </div>
    </c:when>
    <c:when test="${enrollmentFlag == 'H'}">
      <div class="dl-banner-link">
        You have a benefit enrollment opportunity. Please enroll online by clicking the following link.
        <a target="_blank" href="${hrsUrls['Open Enrollment/Hire Event']}">Benefits Enrollment</a>. A Benefit Enrollment
        Deadlines worksheet is available in the Statements tab which details your enrollment deadlines by plan.
      </div>
    </c:when>
    </c:choose>
    <div class="dl-help-link">
      <a href="${helpUrl}" target="_blank">Help</a>
    </div>
  </div>
  
  <hrs:notification/>
  
  <div id="${n}dl-tabs" class="dl-tabs ui-tabs ui-widget ui-widget-content ui-corner-all">
    <ul class="ui-tabs-nav ui-helper-reset ui-helper-clearfix ui-widget-header ui-corner-all">
      <li class="ui-state-default ui-corner-top ui-tabs-selected ui-state-active"><a href="#${n}dl-benefits">Summary</a></li>
      <li class="ui-state-default ui-corner-top"><a href="#${n}dl-benefit-statements">Statements</a></li>
      <li class="ui-state-default ui-corner-top"><a href="#${n}dl-dependents">Dependents</a></li>
      <li class="ui-state-default ui-corner-top"><a href="#${n}dl-benefit-confirmations">Confirmations</a></li>
    </ul>
    <div id="${n}dl-benefits" class="dl-benefits ui-tabs-panel ui-widget-content ui-corner-bottom">
      <div class="coverage-header">
        <span>Balances as of the last pay check</span>
      </div>
      <div class="fl-pager">
        <hrs:pagerNavBar position="top" showSummary="${true}" />
        <div class="fl-container-flex dl-pager-table-data fl-pager-data">
          <table class="dl-table">
            <thead>
              <tr rsf:id="header:">
                <th class="flc-pager-sort-header" rsf:id="name"><a href="javascript:;">Benefit</a></th>
                <th class="flc-pager-sort-header" rsf:id="coverage"><a href="javascript:;">Coverage</a></th>
              </tr>
            </thead>
            <tbody>
                <tr rsf:id="row:">
                  <td class="dl-data-text"><span rsf:id="name"></span></td>
                  <td class="dl-data-text"><span rsf:id="coverage"></span></td>
                </tr>
            </tbody>
          </table>
        </div>
        <hrs:pagerNavBar position="bottom" />
      </div>
      <div class="dl-link">
        <a href="${hrsUrls['Benefits Summary']}" target="_blank">View Benefits Summary Detail</a>
      </div>
      <div class="dl-link">
        <a href="${hrsUrls['Update TSA Deductions']}" target="_blank">Update TSA Deductions</a>
      </div>
    </div>
    <div id="${n}dl-benefit-statements" class="dl-benefit-statements ui-tabs-panel ui-widget-content ui-corner-bottom ui-tabs-hide">
      <div class="fl-pager">
        <hrs:pagerNavBar position="top" showSummary="${true}" />
        <div class="fl-container-flex dl-pager-table-data fl-pager-data">
          <table class="dl-table">
            <thead>
              <tr rsf:id="header:">
                <th class="flc-pager-sort-header dl-col-5p" rsf:id="year"><a href="javascript:;">Year</a></th>
                <th class="flc-pager-sort-header" rsf:id="name"><a href="javascript:;">Statement</a></th>
              </tr>
            </thead>
            <tbody>
                <tr rsf:id="row:" class="dl-clickable">
                  <td class="dl-data-text"><a href="#" target="_blank" rsf:id="year"></td>
                  <td class="dl-data-text"><a href="#" target="_blank" rsf:id="name"></td>
                </tr>
            </tbody>
          </table>
        </div>
        <hrs:pagerNavBar position="bottom" />
        <div class="${n}-dl-benefit-statement-links">
	        <div class="dl-link">
	          <a href="https://uwservice.wisc.edu/help/wrs-benefits-statement.php" target="_blank">WRS Explanation of Statement of Benefits</a>
	        </div>
	        <div class="dl-link">
	          <a href="https://uwservice.wisc.edu/help/benefits-statement.php" target="_blank">Explanation of Staff Benefits Statement (Not produced after April 2011)</a>
	        </div>
        </div>
      </div>
    </div>
    <div id="${n}dl-dependents" class="dl-dependents ui-tabs-panel ui-widget-content ui-corner-bottom ui-tabs-hide">
      <div class="coverage-header">
        <span>Coverage as of </span>
        <span><fmt:formatDate value="${coverageDate}" pattern="MM/dd/yyyy" type="date"/></span>
      </div>
      <div class="fl-pager">
        <hrs:pagerNavBar position="top" showSummary="${true}" />
        <div class="fl-container-flex dl-pager-table-data fl-pager-data">
          <table class="dl-table">
            <thead>
              <tr rsf:id="header:">
                <th class="flc-pager-sort-header" rsf:id="name"><a href="javascript:;">Name</a></th>
                <th class="flc-pager-sort-header" rsf:id="relationship"><a href="javascript:;">Relationship</a></th>
              </tr>
            </thead>
            <tbody>
                <tr rsf:id="row:" class="dl-clickable">
                  <td class="dl-data-text"><span rsf:id="name"></span></td>
                  <td class="dl-data-text"><span rsf:id="relationship"></span></td>
                </tr>
            </tbody>
          </table>
        </div>
        <hrs:pagerNavBar position="bottom" />
      </div>
      <div class="dl-link">
        <a href="${hrsUrls['Dependent Information']}" target="_blank">View Dependent Details</a>
      </div>
      <div class="dl-link">
        <a href="${hrsUrls['Dependent Coverage']}" target="_blank">View Dependent Coverage</a>
      </div>
    </div>
    <div id="${n}dl-benefit-confirmations" class="dl-benefit-confirmations ui-tabs-panel ui-widget-content ui-corner-bottom ui-tabs-hide">
      <div class="fl-pager">
        <hrs:pagerNavBar position="top" showSummary="${true}" />
        <div class="fl-container-flex dl-pager-table-data fl-pager-data">
          <table class="dl-table">
            <thead>
              <tr rsf:id="header:">
                <th class="flc-pager-sort-header" rsf:id="name"><a href="javascript:;">Benefit Confirmations</a></th>
              </tr>
            </thead>
            <tbody>
                <tr rsf:id="row:" class="dl-clickable">
                  <td class="dl-data-text"><a href="#" target="_blank" rsf:id="name"></td>
                </tr>
            </tbody>
          </table>
        </div>
        <hrs:pagerNavBar position="bottom" />
      </div>
    </div>
  </div>
  
</div>

<portlet:resourceURL var="benefitSummaryUrl" id="benefitSummary" escapeXml="false"/>

<portlet:resourceURL var="benefitStatementsUrl" id="benefitStatements" escapeXml="false"/>

<portlet:resourceURL var="benefitsPdfUrl" id="benefits.pdf" escapeXml="false">
    <portlet:param name="mode" value="TMPLT_*.docType_TMPLT"/>
    <portlet:param name="year" value="TMPLT_*.year_TMPLT"/>
    <portlet:param name="docId" value="TMPLT_*.docId_TMPLT"/>
</portlet:resourceURL>

<portlet:resourceURL var="benefitConfirmationsUrl" id="benefitConfirmations" escapeXml="false"/>

<portlet:resourceURL var="benefitsConfirmationPdfUrl" id="benefitConfirmation.pdf" escapeXml="false">
    <portlet:param name="docId" value="TMPLT_*.docId_TMPLT"/>
</portlet:resourceURL>

<script type="text/javascript">
<rs:compressJs>
(function($, dl) {
    $(function() {
        dl.pager.init("#${n}dl-benefits", {
          columnDefs: [
            dl.pager.colDef("name", {sortable: true}),
            dl.pager.colDef("coverage", {sortable: true})
          ],
          dataList: {
              url: "${benefitSummaryUrl}",
              dataKey: "benefits",
              dataLoadErrorMsg: "<spring:message code="genericError" arguments="javascript:;" htmlEscape="false" javaScriptEscape="true" />"
          }
        });
        
        dl.pager.init("#${n}dl-dependents", {
          columnDefs: [ 
            dl.pager.colDef("name", {sortable: true}),
            dl.pager.colDef("relationship", {sortable: true})
          ],
          dataList: {
              url: "${benefitSummaryUrl}",
              dataKey: "dependents",
              dataLoadErrorMsg: "<spring:message code="genericError" arguments="javascript:;" htmlEscape="false" javaScriptEscape="true" />"
          }
        });
        
        var benefitStatementUrl = dl.util.templateUrl("${benefitsConfirmationPdfUrl}");
        dl.pager.init("#${n}dl-benefit-confirmations", {
          model: {
              /* sortKey: "name",
              sortDir: 1 */              
          },
          columnDefs: [ 
              dl.pager.linkColDef("name", benefitStatementUrl, {sortable: true})
          ],
          dataList: {
              url: "${benefitConfirmationsUrl}",
              dataKey: "report",
              dataLoadErrorMsg: "<spring:message code="genericError" arguments="javascript:;" htmlEscape="false" javaScriptEscape="true" />",
              dataLoadCallback: function (data) {
                  if (data == undefined || data.length == 0) {
                      //left in case we want do do something special in the future
                  }
              }
          }
        });
        
        var benefitConfirmationUrl = dl.util.templateUrl("${benefitsPdfUrl}");
        dl.pager.init("#${n}dl-benefit-statements", {
          model: {
              /* sortKey: "name",
              sortDir: 1 */              
          },
          columnDefs: [ 
              dl.pager.linkColDef("year", benefitStatementUrl, {sortable: true}),
              dl.pager.linkColDef("name", benefitStatementUrl, {sortable: true})
          ],
          dataList: {
              url: "${benefitStatementsUrl}",
              dataKey: "report",
              dataLoadErrorMsg: "<spring:message code="genericError" arguments="javascript:;" htmlEscape="false" javaScriptEscape="true" />",
              dataLoadCallback: function (data) {
                  if (data == undefined || data.length == 0) {
                      //Hide the ${n}-dl-benefit-statement-links
                      $('.${n}-dl-benefit-statement-links').hide();
                      
                  }
              }
          }
        });
        
        dl.tabs("#${n}dl-tabs");
        
        dl.util.clickableContainer("#${n}dl-benefit-summary");
    });    
})(dl_v1.jQuery, dl_v1);
</rs:compressJs>
</script>