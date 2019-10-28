<!DOCTYPE html>
<html>
<head>
    <title>Home page</title>
    <meta charset="UTF-8">
</head>

<body>
<form method="POST" enctype="multipart/form-data"  action="/directory/users/upload">
    <input type="file" name="file"/><br/><br/>
    <input type="submit" value="Submit" id="Upload Users"/>
</form>
<form action="/directory/users" method="get">
    <input type="submit" value="Show users"/>
</form>
<form action="/directory/users/report" method="get">
    <input type="submit" value="Download users"/>
</form>
<form action="/directory/companies" method="get">
    <input type="submit" value="Show companies"/>
</form>
</body>
</html>