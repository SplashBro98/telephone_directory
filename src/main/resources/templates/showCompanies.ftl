<!DOCTYPE html>
<html>
<head>
    <title>Companies</title>
    <meta charset="UTF-8">
</head>
<body>
<h2>List of companies</h2>

<#if companies??>
    <table>
        <tr>
            <th>Id</th>
            <th>Name</th>
            <th>YearOfIssue</th>
        </tr>

        <#list companies as company>
            <tr>
                <td>${company.id}</td>
                <td>${company.name}</td>
                <td>${company.yearOfIssue}</td>
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