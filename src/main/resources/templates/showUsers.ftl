<!DOCTYPE html>
<html>
<head>
    <title>Users</title>
    <meta charset="UTF-8">
</head>
<body>
<h2>List of users</h2>

<#if users??>
    <table>
        <tr>
            <th>Id</th>
            <th>UserName</th>
            <th>FirstName</th>
            <th>LastName</th>
            <th>City</th>
        </tr>

        <#list users as user>
            <tr>
                <td>${user.id}</td>
                <td>${user.userName}</td>
                <td>${user.firstName}</td>
                <td>${user.lastName}</td>
                <td>${user.city}</td>
            </tr>
        </#list>
    </table>
<#else>
    <h5>List is empty</h5>
</#if>

<br>
<a href="/">Go Home</a>
</body>
</html>