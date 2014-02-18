
<!DOCTYPE html>
<html>
<head>
<title>Article Search</title>
<meta charset="utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
<meta name="viewport" content="width=device-width" />

<link rel="stylesheet"
  href="${pageContext.request.contextPath}/resources/app/css/styles.css"
  type="text/css" media="screen, projection">
<%-- 
<link rel="stylesheet"
  href="${pageContext.request.contextPath}/resources/app/css/pagination.css"
  type="text/css" media="screen, projection">
--%>
<link rel="stylesheet"
  href="${pageContext.request.contextPath}/resources/app/css/articleStyles.css"
  type="text/css" media="screen, projection">

<link rel="stylesheet"
  href="${pageContext.request.contextPath}/resources/vendor/bootstrap-3.0.0/css/bootstrap.css"
  type="text/css" media="screen, projection">


<style type="text/css">
/*
.pagination > .currentPageLink > a,
.pagination > .currentPageLink > a:hover,
.pagination > .currentPageLink > a:focus {
  z-index: 2;
  color: #ffffff;
  cursor: pointer;
  background-color: #428bca;
  border-color: #428bca;
}

.pagination > .hiddenPageLink > a {
  display: none;
}
    
    .pagination li {
        display: inline;
    }
    
    .pagination li>a {
        margin-left: 10px;
    }

*/
.paginationPart {
    text-align: center;
}

td {
background-color: white!important;
}

form div {
text-align: center;
}

</style>

</head>



<body>

  <div class="wrapper">

    <h1 class="pageTitle">File Upload Complate</h1>

      <t:messagesPanel />

      <table>
        <tr>
          <th width="35%">File to upload</th>
          <td width="65%">
            ${f:h(uploadFileInfo.fileName)}
          </td>
        </tr>
        <tr>
          <th width="35%">Description</th>
          <td width="65%">
            ${f:h(uploadFileInfo.description)}
          </td>
        </tr>
      </table>
  </div>
</body>
</html>