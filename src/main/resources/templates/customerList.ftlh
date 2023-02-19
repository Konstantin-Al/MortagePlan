<#import "parts/body.ftlh" as c>
<#import "parts/login.ftlh" as l>
<#import "parts/header.ftlh" as h>

<@c.page>

    <@h.header "List of customers"/>

    <table style="margin-bottom: 50px">
        <tr>
            <th style="width: 200px">Name</th>
            <th style="width: 200px">Role</th>
            <th style="width: 100px"></th>
        </tr>

        <tbody>
        <#list customers as customer>
            <tr>
                <td>${customer.username}</td>
                <td><#list customer.roles as role>${role}<#sep>, </#list></td>
                <td><a href="/customer/${customer.customer_id}" class="btn btn-primary">edit</a></td>
            </tr>
        </#list>
        </tbody>

    </table>
    <a href="/registration" class="btn btn-primary">Add new customer</a>

</@c.page>
