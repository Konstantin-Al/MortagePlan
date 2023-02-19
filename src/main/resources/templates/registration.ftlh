<#import "parts/body.ftlh" as c>
<#import "parts/login.ftlh" as l>

<@c.page>

    <div class="mb-2">Add new customer</div>

    <@l.login "/registration" "Add"/>

</@c.page>