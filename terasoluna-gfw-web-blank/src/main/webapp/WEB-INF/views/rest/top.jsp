<!DOCTYPE html>
<html>
<head>
<title>Search Screen</title>

<meta charset="utf-8">
<meta name="_csrf_token" content="${_csrf.token}" />
<meta name="_csrf_headerName" content="${_csrf.headerName}" />
<meta name="contextPath" content="${pageContext.request.contextPath}" />

<link rel="stylesheet"
    href="${pageContext.request.contextPath}/resources/app/css/styles.css">

<script type="text/javascript"
    src="${pageContext.request.contextPath}/resources/vendor/jquery/jquery-1.10.2.js"></script>

<script type="text/javascript">

var csrfToken = $("meta[name='_csrf_token']").attr("content");
	var csrfHeaderName = $("meta[name='_csrf_headerName']").attr("content");
	var contextPath = $("meta[name='contextPath']").attr("content");

	$(document).ajaxSend(function(event, xhr, options) {
		xhr.setRequestHeader(csrfHeaderName, csrfToken);  <!-- (5) -->
	});
	
	function toJson(form) {
		var data = {};
		$(form.serializeArray()).each(function(i, v) {
			data[v.name] = v.value;
		});
		return JSON.stringify(data);
	}

	function getData() {
		return $("#data").val();
	}

	function requestJaxb() {
		request(getData(), "", "POST", "text/xml;charset=utf-8");
	}

	function requestSAXSource() {
		request(getData(), "SAXSource", "POST", "text/xml;charset=utf-8");
	}

	function requestSAXSource() {
		request(getData(), "DOMSource", "POST", "text/xml;charset=utf-8");
	}

	function requestSAXSource() {
		request(getData(), "StreamSource", "POST", "text/xml;charset=utf-8");
	}

	function searchByFreeWord() {
		$.ajax(contextPath + "/ajax/search", {
			type : "GET",
			data : $("#searchForm").serialize(),
			dataType : "json",

		}).done(function(json) {
			// render search result

		}).fail(function(xhr) {
		    alert(xhr.status);
		    if(400 < xhr.status && xhr.status <= 499){
    			var contentType = xhr.getResponseHeader('Content-Type');
    			if (contentType != null && contentType.indexOf("json") != -1) {
    				json = $.parseJSON(xhr.responseText);
    				$(json.errorResults).each(function(i, obj) {
    					// render error message.
    				});
    			}
            }

		});
		return false;
	}

	function plusForJson() {
		$.ajax(contextPath + "/ajax/plusForJson", {
			type : "POST",
			contentType : "application/json;charset=utf-8",
			data : toJson($("#calculationForm")),
			dataType : "json",
		}).done(function(json) {
			$("#calculationResult").text(json.resultNumber);

		}).fail(function(xhr) {
            // (11)
	        var messages = "";
            // (12)
	        if(400 <= xhr.status && xhr.status <= 499){
                // (13)
	            var contentType = xhr.getResponseHeader('Content-Type');
	            if (contentType != null && contentType.indexOf("json") != -1) {
                    // (14)
	                json = $.parseJSON(xhr.responseText);
	                $(json.errorResults).each(function(i, errorResult) {
	                    messages += ("<div>" + errorResult.message + "</div>");
	                });
	            } else {
                    // (15)
	                messages = ("<div>" + xhr.statusText + "</div>");
	            }
	        }else{
                // (16)
	            messages = ("<div>" + "System error occurred." + "</div>");
	        }
            // (17)
	        $("#calculationResult").html(messages);
		});

		return false;

		/*
		 request(toJson($("#calculationForm")), "plus", "POST",
		 "application/json;charset=utf-8", function calcResult(json) {
		 console.log("responseData:\n" + json);
		 $("#calculationResult").text(json.resultNumber);
		 }, function(xhr) {
		 console.log("responseData:\n" + xhr.responseText);
		 $("#calculationResult").text("");
		 var contentType = xhr.getResponseHeader('Content-Type');
		 if (contentType != null
		 && contentType.indexOf("json") != -1) {
		 json = $.parseJSON(xhr.responseText);
		 $(json.errorResults).each(function(i, obj) {
		 // render error message.
		 });
		 } else {
		 // render error message.
		 }
		 });
		 */
	}
	
	function handleError(xhr){
	}
	function plus4Form() {
		$.ajax(contextPath + "/ajax/plusForForm", {
			type : "POST",
			data : $("#calculationForm").serialize(),
			dataType : "json",
			beforeSend : function(xhr) {
				xhr.setRequestHeader(csrfHeaderName, csrfToken);
			}

		}).done(function(json) {
			$("#calculationResult").text(json.resultNumber);

		}).fail(function(xhr) {
			$("#calculationResult").text("");

		});

		return false;
	}
	function request(data, param, method, contentType, doneFunc, failFunc) {
		console.log("requestData:\n" + data);
		$.ajax({
			type : method,
			url : contextPath + "/ajax/xxe?" + param,
			contentType : contentType,
			data : data
		}).done(doneFunc).fail(failFunc);
	}
</script>
</head>
<body>

    <h1>Ajax XXE Test</h1>

    <t:messagesPanel></t:messagesPanel>

    <form id="form">
        <textarea id="data" name="data" rows="20" cols="80">${f:h(defaultData)}</textarea>
    </form>
    <button onclick="return requestJaxb()">Test Jaxb</button>
    <button onclick="return requestSAXSource()">Test SAXSource</button>
    <button onclick="return requestSAXSource()">Test DOMSource</button>
    <button onclick="return requestSAXSource()">Test
        StreamSource</button>

    <br>
    <form id="searchForm">
        <input name="freeWord" type="text">
        <button onclick="return searchByFreeWord()">Search</button>
    </form>

    <br>

    <form id="calculationForm">
        <input name="number1" type="text">+ <input
            name="number2" type="text">
        <button onclick="return plusForJson()">=</button>
        <span id="calculationResult"></span>
    </form>

</body>
</html>
