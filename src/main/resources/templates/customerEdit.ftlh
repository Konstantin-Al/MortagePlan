<#import "parts/body.ftlh" as c>
<#import "parts/header.ftlh" as h>

<@c.page>

    <@h.header "Customer editor"/>

<form action = "/customer" method="post">
    <input type="text" name="username" value="${customer.username}">

    <#list roles as role>
    <div style="margin-top: 15px">
        <label><input type="checkbox" name="${role}" ${customer.roles?seq_contains(role)?string("checked", "")}>${role}</label>
    </div>
    </#list>

    <input type="hidden" value = "${customer.customer_id}" name = "customer_id">
    <input type="hidden" name="_csrf" value="${_csrf.token}" />

    <button class="btn btn-primary" style="margin-top: 50px" type="submit" >Save</button>
</form>

</@c.page>