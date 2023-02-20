<#import "parts/body.ftlh" as c>
<#import "parts/header.ftlh" as h>
<#import "parts/header.ftlh" as h>
<#include "parts/security.ftlh">

<@c.page>

    <@h.header "Mortgage Calculation History"/>

    <a class="btn btn-primary" data-bs-toggle="collapse" href="#collapseCalculate" role="button" aria-expanded="false" aria-controls="collapseExample">
        New calculation
    </a>


    <div class="collapse" id="collapseCalculate">
        <div class="form-group">
            <form method="post" enctype="multipart/form-data">


                <#if isAdmin>

                    <div>
                        <label for="importFile" class="form-label">import File</label>
                        <input class="form-control" type="file" id="importedFile" name="importedFile" />
                        <button class="btn btn-success" name="action" value="import" type="submit" >Import</button>
                    </div>

                <#else>

                    <table style="background-color: dimgray; margin-top: 10px; width: 400px">
                        <tr>
                            <th>Total Loan</th>
                            <th>Interest</th>
                            <th>Years</th>
                        </tr>
                        <tr>
                            <td>
                                <input style="padding-left: 10px; width: 100px" type="String" name="total_loan" placeholder="totalLoan"/>
                            </td>
                            <td>
                                <input style="padding-left: 10px; width: 100px" type="String" name="interest" placeholder="interest"/>
                            </td>

                            <td>
                                <input style="padding-left: 10px; width: 100px" type="String" name="years" placeholder="years"/>
                            </td>

                            <td>
                                <button class="btn btn-primary" name="action" value="calculate" type="submit">Calculate</button>
                            </td>
                        </tr>
                    </table>

                </#if>
                <input type="hidden" name="_csrf" value="${_csrf.token}" />
            </form>
        </div>
    </div>

    <#if result_str?has_content>
        <h1>${result_str}</h1>
    </#if>



    <#if loanCals?has_content>
        <table style="margin-top: 50px">
        <tr>
            <th style="width: 200px">Date</th>
            <#if isAdmin>
                <th style="width: 150px">Customer</th>
            </#if>
            <th style="width: 100px">Total loan</th>
            <th style="width: 100px">Interest</th>
            <th style="width: 100px">Years</th>
            <th style="width: 100px">Fixed monthly payment</th>

        </tr>

        <#list loanCals as loanCal>

            <tr>
                <td>
                    <b>${loanCal.CD}</b>
                </td>
                <#if isAdmin>
                    <td>
                        <strong>${loanCal.getCustomer()}</strong>
                    </td>
                </#if>
                <td>
                    <b>${loanCal.totalLoan}</b>
                </td>
                <td style="text-align: center; padding-left: 10px">
                    <b>${loanCal.interest}</b>
                </td>
                <td style="text-align:center; padding-left: 10px">
                    <b>${loanCal.years}</b>
                </td>
                <td style="text-align: left; padding-left: 10px">
                    <b>${loanCal.calMortgage}</b>
                </td>
            </tr>
        </#list>
        </table>
    <#else>
        <div style="padding-top: 100px; text-align: center; font-size: 50px">
            No Records!
        </div>
    </#if>





</@c.page>
