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
<%@ include file="/WEB-INF/jsp/bootstrapHeader.jsp"%>

<portlet:resourceURL var="earningStatementsUrl" id="earningStatements" escapeXml="false"/>
<portlet:resourceURL var="earningStatementPdfUrl" id="earning_statement.pdf" escapeXml="false">
	<portlet:param name="docId" value="--docId--"/>
</portlet:resourceURL>

<portlet:resourceURL var="taxStatementsUrl" id="taxStatements" escapeXml="false"/>
<portlet:resourceURL var="irsStatementPdfUrl" id="irs_statement.pdf" escapeXml="false">
	<portlet:param name="docId" value="--docId--"/>
</portlet:resourceURL>

<link rel="stylesheet" href="<c:url value="/css/payrollInfo.css"/>" type="text/css" />

<script src="//code.jquery.com/jquery-1.10.2.min.js"></script>
<script src="//ajax.aspnetcdn.com/ajax/jquery.dataTables/1.9.4/jquery.dataTables.min.js"></script>

<script type="text/javascript">
    // Bootstrap javascript fails if included multiple times on a page.
    // uPortal Bootstrap best practice: include bootstrap if and only if it is not present and save it to
    // portlets object. Bootstrap functions could be manually invoked via portlets.bootstrapjQuery variable.
    // All portlets using Bootstrap Javascript must use this approach.  Portlet's jQuery should be included
    // prior to this code block.

    var portlets = portlets || {};
    // If bootstrap is not present at uPortal jQuery nor a community bootstrap, dynamically load it.
    up.jQuery().carousel || portlets.bootstrapjQuery || document.write('<script src="//netdna.bootstrapcdn.com/bootstrap/3.1.1/js/bootstrap.min.js"><\/script>');

</script>
<script type="text/javascript">
    // Must be in separate script tag to insure bootstrap was dynamically loaded.
    portlets["${n}"] = {};
    portlets["${n}"].jQuery = jQuery.noConflict(true);
    // If bootstrap JS global variable was not defined, set it to the jQuery that has bootstrap attached to.
    portlets.bootstrapjQuery = portlets.bootstrapjQuery || (up.jQuery().carousel ? up.jQuery : portlets["${n}"].jQuery);
</script>

<div class="bootstrap-styles">
	<div class="container-fluid payroll">
		<div id="content">
			<ul id="tabs" class="nav nav-tabs" data-tabs="tabs">
				<li class="active"><a href="#${n}earnings" data-toggle="tab">Earnings Statements</a></li>
				<li><a href="#${n}taxes" data-toggle="tab">Tax Statements</a></li>
			</ul>
			<div id="my-tab-content" class="tab-content">
				<div class="tab-pane active" id="${n}earnings">
					<div class="row">
						<div class="col-sm-10 col-sm-offset-1 alert alert-info">
							Your Net Pay Check amount is reflected on each individual Earnings Statement
						</div>
					</div>
					<div class="row">
						<div class="col-sm-12 amounts-toggle text-right">
							<label>
								Display amounts
								<input type="checkbox" name="earnings-toggle" id="${n}earnings-toggle"></label>
							</div>
					</div>
					<div class="row">
						<div class="col-sm-12">
							<table id="${n}earnings-table" class="earnings-table table table-bordered table-striped">
								<thead>
									<tr>
										<th>Paid</th>
										<th>Earned</th>
										<th>Amount</th>
									</tr>
								</thead>
								<tbody>
									<tr>
										<td></td>
										<td></td>
										<td></td>
									</tr>
								</tbody>
							</table>
						</div>
					</div>
					<div class="row">
						<div class="col-sm-6">
							<a href="update_W4_link" class="btn btn-default" target="_blank">Update your W4</a>
						</div>
						<div class="col-sm-6 text-right">
							<a href="update_direct_deposit_link" class="btn btn-default" target="_blank">Update your Direct Deposit</a>
						</div>
					</div>
				</div> <%-- end earnings tab --%>

				<%-- Taxes Tab --%>		  
				<div class="tab-pane" id="${n}taxes">
					<div class="row">
						<div class="col-sm-10 col-sm-offset-1 alert alert-info"> <strong>Note:</strong>
							W-2 Forms will be available the last week of January
						</div>
					</div>
					<div class="row">
						<div class="col-sm-12">
							<table id="${n}taxes-table" class="table table-bordered table-striped">
								<thead>
									<tr>
										<th>Year</th>
										<th>Statement</th>
									</tr>
								</thead>
								<tbody>
									<tr>
										<td></td>
										<td></td>
									</tr>
								</tbody>
							</table>
						</div>
					</div>
					<div class="row btn-row">
						<div class="col-sm-6">
							<a href="tax-w2-explanation-link" class="btn btn-default" target="_blank">W-2 Explanation</a>
						</div>
						<div class="col-sm-6 text-right">
							<a href="itx-1042s-explanation-link" class="btn btn-default" target="_blank">1042-S Explanation</a>
						</div>
					</div>
					<c:if test="${personalDataError or personalData.onVisa}">
						<div class="row btn-row">
							<div class="col-sm-6">
								<a href="http://www.online-tax.net/" class="btn btn-default" target="_blank">Glacier International Tax</a>
							</div>
							<c:if test="${personalDataError or personalData.madisonEmpl}">
								<div class="col-sm-6 text-right">
									<a href="https://www.cintax.us/gateway.asp?iid=P2642KCJ" class="btn btn-default" target="_blank">CINTAX Nonresident Tax Preparation</a>
								</div>
							</c:if>
						</div>
					</c:if>
				</div> <%-- end taxes tab --%>
			</div>
		</div>
	</div><!-- container -->
</div>

<script src="<c:url value="/js/payrollInformation.js"/>"></script>
<script type="text/javascript">
    var options = {
        earningsToggle: '#${n}earnings-toggle',

        earningsUrl: '${earningStatementsUrl}',
        earningsPDFUrl: '${earningStatementPdfUrl}',
    
        taxesUrl: '${taxStatementsUrl}',
        taxesPDFUrl: '${irsStatementPdfUrl}',
    
        earningsTable: '#${n}earnings-table',
        taxesTable: '#${n}taxes-table'
    };
    portlets["${n}"].jQuery(payrollInformation(portlets["${n}"].jQuery, options));
</script>
