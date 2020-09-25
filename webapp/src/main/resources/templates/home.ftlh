<html>
<head>
    <title>Home Page</title>
</head>
    <body>
        <h1>Hello ${name}!</h1>
        <p>Welcome to ${appName}!</p>

        <form action="/hello">
            <input name="name" placeholder="Please, enter your name!" type="text">
            <input type="submit">
        </form>
    </body>
</html>