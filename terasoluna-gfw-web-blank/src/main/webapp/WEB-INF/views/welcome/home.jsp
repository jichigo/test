<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>Home</title>
<link rel="stylesheet"
    href="${pageContext.request.contextPath}/resources/app/css/styles.css">
</head>
<body>
    <div id="wrapper">
        <h1>Hello world!</h1>
        <form:form modelAttribute="helloWorldForm" method="post">
            <p>
                date1 in form :
                <form:input path="date1" />
            </p>
            <p>
                date2 in form :
                <form:input path="date2" />
            </p>
            <p>
                date3 in form :
                <form:input path="date3" />
            </p>
            <p>
                date4 in form :
                <form:input path="date4" />
            </p>
            <p>
                date5 in form :
                <form:input path="date5" />
            </p>
            <p>
                number1 in form :
                <form:input path="number1" />
            </p>
            <p>
                number2 in form :
                <form:input path="number2" />
            </p>
            <p>
                number3 in form :
                <form:input path="number3" />
            </p>
            <form:button name="hoge">Submit</form:button>
        </form:form>
        <div>
            <p>date1 in model : ${serverTime1}</p>
            <p>date2 in model : ${serverTime2}</p>
            <p>date3 in model : ${serverTime3}</p>
            <p>date4 in model : ${serverTime4}</p>
        </div>


    </div>

    <div>
        <a href="${pageContext.request.contextPath}/article/list">Go
            to the Article List</a>
    </div>
</body>
</html>
