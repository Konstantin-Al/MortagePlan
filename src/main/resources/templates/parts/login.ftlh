<#macro login path act>

    <form action="${path}" method="post">
        <div class="form-group row">
            <label class="col-sm-1 col-form-label">Name: </label>
            <div class="col-sm-3">
                <input type="text" name="username" class="form-control" placeholder="User name"/>
            </div>
        </div>

        <div class="form-group row">
            <label class="col-sm-1 col-form-label"> Password: </label>
            <div class="col-sm-3">
                <input type="password" name="password" class="form-control" placeholder="Password"/>
            </div>
        </div>

        <input type="hidden" name="_csrf" value="${_csrf.token}" />
        <button class="btn btn-primary" type="submit" value=${act}>${act}</button>
    </form>

</#macro>

<#---------------------------------------------------------------------------------------------->

<#macro logout name>

    <form action="/logout" method="post">
        <input type="hidden" name="_csrf" value="${_csrf.token}" />
        <button class="btn btn-primary" type="submit"> <#if name == "">Log in<#else>Log out</#if></button>
    </form>

</#macro>