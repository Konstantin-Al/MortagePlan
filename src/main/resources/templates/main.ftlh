<#import "parts/body.ftlh" as c>
<#import "parts/header.ftlh" as h>
<#import "parts/header.ftlh" as h>


<@c.page>

    <@h.header "Mortgage Calculation History"/>

    <a class="btn btn-primary" data-bs-toggle="collapse" href="#collapseCalculate" role="button" aria-expanded="false" aria-controls="collapseExample">
        Calculate
    </a>


    <div class="collapse" id="collapseCalculate">
        <div class="form-group">
            <form method="post" enctype="multipart/form-data">
                <table style="background-color: dimgray; margin-top: 10px">
                    <tr>
                        <th style="width: 100px">Total Loan</th>
                        <th style="width: 100px">Interest</th>
                        <th style="width: 100px">Years</th>
                    </tr>
                    <tr>
                        <td>
                            <input style="padding-left: 10px" type="String" name="total_loan" placeholder="totalLoan"/>
                        </td>
                        <td>
                            <input style="padding-left: 10px; width: 500px" type="String" name="interest" placeholder="interest"/>
                        </td>

                        <td>
                            <input style="padding-left: 10px; width: 500px" type="String" name="years" placeholder="years"/>
                        </td>

                        <td>
                            <button class="btn btn-primary" name="action" value="calculate" type="submit">Calculate</button>
                        </td>
                    </tr>
                </table>

                <div>
                <label for="importFile" class="form-label">import File</label>
                <input class="form-control" type="file" id="importedFile" name="importedFile" />
                <button class="btn btn-success" name="action" value="import" type="submit" >Import</button>
                </div>

                <input type="hidden" name="_csrf" value="${_csrf.token}" />
            </form>
        </div>
    </div>




    <#if loanCals?has_content>
        <table style="margin-top: 50px">
        <tr>
            <th style="width: 120px">Date</th>
            <th style="width: 120px">Customer</th>
            <th style="width: 60px">Total loan</th>
            <th style="width: 200px">Interest</th>
            <th style="width: 500px">Years</th>

        </tr>

        <#list loanCals as loanCal>

            <tr>
                <td>
                    <b>${loanCal.CD}</b>
                </td>
                <td>
                    <strong>${loanCal.getCustomer()}</strong>
                </td>
                <td>
                    <b>${loanCal.totalLoan}</b>
                </td>
                <td style="text-align: left; padding-left: 10px">
                    <b>${loanCal.interest}</b>
                </td>
                <td style="text-align: left; padding-left: 10px">
                    <b>${loanCal.years}</b>
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
