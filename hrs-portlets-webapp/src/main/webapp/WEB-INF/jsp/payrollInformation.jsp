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

<style>
  .my-uw .dl-pager-navbar {
    height:auto;
    text-align:center;
  }
  .my-uw .dl-table {
    margin-bottom:30px;
  }
  .my-uw table.dl-table {
    border:1px solid #eee !important;
  }
  .my-uw table.dl-table tr:nth-child(even) {
    background-color:#eee;
  }
  .my-uw .hrs-notification-wrapper {
    padding:10px;
    margin-bottom:50px;
    text-align:center;
  }
  @media (max-width:768px) {
    .my-uw .dl-pager-links {
      display:none;
    }
  }

</style>

<div id="${n}dl-payroll-information" class="fl-widget portlet dl-payroll-information">
  
  <div id="${n}dl-tabs" class="dl-tabs ui-tabs ui-widget ui-widget-content ui-corner-all inner-nav-container">
    <ul class="ui-tabs-nav ui-helper-reset ui-helper-clearfix ui-widget-header ui-corner-all inner-nav">
      <li class="ui-state-default ui-corner-top ui-tabs-selected ui-state-active"><a href="#${n}dl-earning-statements">Earning Statements</a></li>
      <li class="ui-state-default ui-corner-top"><a href="#${n}dl-tax-statements">Tax Statements</a></li>
    </ul>
    <div id="${n}dl-earning-statements" class="dl-earning-statements ui-tabs-panel ui-widget-content ui-corner-bottom">
      <div class="data-table-description-header">
        <div class="data-table-description">
          Your Net Pay Check amount is reflected on each individual Earnings Statement
        </div>
        <div class="data-table-details">
          <form action="#">
            <label for="${n}dl-earnings-amount-toggle">Show earnings dollar amounts </label>
            <input id="${n}dl-earnings-amount-toggle" name="dl-earnings-amount-toggle" type="checkbox"/>
          </form>
        </div>
      </div>
      <div class="fl-pager">
        <hrs:pagerNavBar position="top" showSummary="${true}"/>
        <div class="fl-container-flex dl-pager-table-data fl-pager-data table-responsive">
          <table class="dl-table table">
            <thead>
              <tr rsf:id="header:">
                <th class="flc-pager-sort-header dl-col-25p" rsf:id="paid"><a href="javascript:;">Paid</a></th>
                <th class="flc-pager-sort-header dl-col-50p" rsf:id="earned"><a href="javascript:;">Earned</a></th>
                <th class="flc-pager-sort-header dl-col-25p" rsf:id="amount"><a href="javascript:;">Amount</a></th>
              </tr>
            </thead>
            <tbody>
                <tr rsf:id="row:" class="dl-clickable">
                  <td class="dl-data-text"><a href="#" target="_blank" rsf:id="paid"></a></td>
                  <td class="dl-data-text"><a ref="#" target="_blank" rsf:id="earned"></a></td>
                  <td class="dl-data-number"><a class="dl-earning-amount" href="#" target="_blank" rsf:id="amount"></a></td>      
                </tr>
            </tbody>
          </table>
        </div>
        <hrs:pagerNavBar position="bottom" />
      </div>
      <c:if test="${not empty understandingEarningUrl}">
          <div class="dl-link">
            <a href="${understandingEarningUrl}" target="_blank">Understanding Your Earnings Statement</a>
          </div>
      </c:if>
    </div>
    <div id="${n}dl-tax-statements" class="dl-tax-statements ui-tabs-panel ui-widget-content ui-corner-bottom ui-tabs-hide">
      <div class="tax-description">
        Note: W-2 Forms will be available the last week of January
      </div>
      <div class="fl-pager">
        <hrs:pagerNavBar position="top" showSummary="${true}" />
        <div class="fl-container-flex dl-pager-table-data fl-pager-data table-responsive">
          <table class="dl-table table">
            <thead>
              <tr rsf:id="header:">
                <th class="flc-pager-sort-header dl-col-5p" rsf:id="year"><a href="javascript:;">Year</a></th>
                <th class="flc-pager-sort-header" rsf:id="name"><a href="javascript:;">Statement</a></th>
              </tr>
            </thead>
            <tbody>
                <tr rsf:id="row:" class="dl-clickable">
                  <td class="hrs-data-text dl-col-5p"><a href="#" target="_blank" rsf:id="year"></a></td>
                  <td class="dl-data-text"><a href="#" target="_blank" rsf:id="name"></a></td>
                </tr>
            </tbody>
          </table>
        </div>
        <hrs:pagerNavBar position="bottom" />
      </div>
    </div>
  </div>
  
  <div>
    <hrs:notification/>
    <ul class="dl-payroll-links inline-link-list">
      <li><a href="https://uwservice.wisc.edu/docs/forms/pay-employee-withholding.pdf" target="_blank">Update your W4</a></li>
      <li><a href="https://uwservice.wisc.edu/docs/forms/pay-direct-deposit.pdf" target="_blank">Update your Direct Deposit</a></li>
      <li><a href="https://uwservice.wisc.edu/docs/publications/tax-w2-explanation.pdf" target="_blank">W-2 Explanation</a></li>
      <li><a href="https://uwservice.wisc.edu/docs/publications/itx-1042s-explanation.pdf" target="_blank">1042-S Explanation</a></li>
      <c:if test="${personalDataError or personalData.onVisa}">
          <li><a href="http://www.online-tax.net/" target="_blank">Glacier International Tax</a></li>
        <c:if test="${personalDataError or personalData.madisonEmpl}">
            <li><a href="https://www.cintax.us/gateway.asp?iid=P2642KCJ" target="_blank">CINTAX Nonresident Tax Preparation</a></li>
        </c:if>
      </c:if>
      <li><a href="${helpUrl}" target="_blank">Help</a></li>
    </ul>
  </div>
</div>

<portlet:resourceURL var="earningStatementsUrl" id="earningStatements" escapeXml="false"/>
<portlet:resourceURL var="earningStatementPdfUrl" id="earning_statement.pdf" escapeXml="false">
    <portlet:param name="docId" value="TMPLT_*.docId_TMPLT"/>
</portlet:resourceURL>

<portlet:resourceURL var="taxStatementsUrl" id="taxStatements" escapeXml="false"/>
<portlet:resourceURL var="irsStatementPdfUrl" id="irs_statement.pdf" escapeXml="false">
    <portlet:param name="docId" value="TMPLT_*.docId_TMPLT"/>
</portlet:resourceURL>


<script type="text/javascript" language="javascript">
<rs:compressJs>
(function($, fluid, dl) {
    dl.jQuery(function() {
        
        <%-- Disable Right Clicks in Safari browsers --%>
        <%-- Safari (not webkit) does not honor context header for naming downloads
             If indexOf('Constructor')>0 then we are in Safari Browser--%>
        if (Object.prototype.toString.call(window.HTMLElement).indexOf('Constructor') > 0) {
            $(".dl-clickable").live("contextmenu", function() {
                return false;
            });
        }
        
        $(document).ready(function(){
        
        var updateAmmountVisibility = function(checkbox) {
            var checked = checkbox.is(':checked');
            var ammounts = $("#${n}dl-payroll-information table.dl-table a.dl-earning-amount");
            if (ammounts.length == 0) {
                $.log("No ammount fields found to update, returning true");
                return true;
            }
            
            var ammountData = ammounts.data();
            if (ammountData.visibilityUpdated == checked) {
                $.log("Earnings Toggle toggled " + checked + " matches current state, returning false");
                return false;
            }
            
            ammountData.visibilityUpdated = checked;
            $.log("Earnings Toggle toggled: " + checked + " updating " + ammounts.length + " ammount fields");
            if (checked) {
                ammounts.show();
            }
            else {
                ammounts.hide();
            }
            
            return true;
        };
        
        var earningsToggle = $("#${n}dl-earnings-amount-toggle");
        $.log("Earnings Toggle: " + earningsToggle.length);
        earningsToggle.change(function() {
            updateAmmountVisibility(earningsToggle);
        });
        
        //Setup the pager with no data right away, this helps avoid page flicker when the data comes back from the ajax request
        var earningStatementUrl = dl.util.templateUrl("${earningStatementPdfUrl}");
        dl.pager.init("#${n}dl-earning-statements", {
          model: {
              sortKey: "paid",
              sortDir: -1
          },
          summary: {
              type: "fluid.pager.summary", 
              options: {
                message: "%first-%last of %total statements"
              }
          },
          columnDefs: [ 
             dl.pager.linkColDef("paid", earningStatementUrl, {sortable: true, sortValueExtractor: dl.pager.dateExtractor}),
             dl.pager.linkColDef("earned", earningStatementUrl, {sortable: true}),
             dl.pager.linkColDef("amount", earningStatementUrl, {sortable: true, sortValueExtractor: dl.pager.currencyExtractor})
          ],
          listeners : {
              onModelChange : function() {
                  dl.scheduleToggleEarnings(earningsToggle, updateAmmountVisibility);
                  //updateAmmountVisibility(earningsToggle);
              }
          },
          dataList: {
              url: "${earningStatementsUrl}",
              dataKey: "report",
              dataLoadErrorMsg: "<spring:message code="genericError" arguments="javascript:;" htmlEscape="false" javaScriptEscape="true" />"
          }
        });
        

        var taxStatementUrl = dl.util.templateUrl("${irsStatementPdfUrl}");
        dl.pager.init("#${n}dl-tax-statements", {
          model: {
              sortKey: "year",
              sortDir: -1
          },
          summary: {
              type: "fluid.pager.summary", 
              options: {
                message: "%first-%last of %total statements"
              }
          },
          columnDefs: [
             dl.pager.linkColDef("year", taxStatementUrl, {sortable: true}),
             dl.pager.linkColDef("name", taxStatementUrl, {sortable: true})
          ],
          dataList: {
              url: "${taxStatementsUrl}",
              dataKey: "report",
              dataLoadErrorMsg: "<spring:message code="genericError" arguments="javascript:;" htmlEscape="false" javaScriptEscape="true" />"
          }
        });
        
        dl.tabs("#${n}dl-tabs");
        
        dl.util.clickableContainer("#${n}dl-payroll-information");
        });
    });    
})(dl_v1.jQuery, dl_v1.fluid, dl_v1);
</rs:compressJs>
</script>
