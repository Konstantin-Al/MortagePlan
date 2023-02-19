<#import "parts/body.ftlh" as c>
<#import "parts/login.ftlh" as l>
<#import "parts/header.ftlh" as h>

<@c.page>

    <@h.header "Login page"/>

<#--    <div if="${param.error}">-->
<#--        Invalid username and password.-->
<#--    </div>-->
<#--    <div if="${param.logout}">-->
<#--        You have been logged out.-->
<#--    </div>-->

    <@l.login "/login" "Sign in"/>

</@c.page>