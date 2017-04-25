<!DOCTYPE HTML>
<html xmlns:th="http://www.thymeleaf.org">
<head>
    <title>Create test run</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
</head>
<body>
	<h1>Create a test run</h1>
    <form action="#" th:action="@{/save}" th:object="${newRun}" method="post">
        <p>Path to text corpus: <input type="text" th:field="*{path}" /></p>
        <p><input type="submit" value="Submit" /> <input type="reset" value="Reset" /></p>
    </form>
</body>
</html>