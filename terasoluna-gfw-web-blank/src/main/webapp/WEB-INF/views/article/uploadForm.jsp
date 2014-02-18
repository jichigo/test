<!DOCTYPE html>
<html>
<head>
<title>Article Searchあ</title>
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

    <h1 class="pageTitle">File Upload</h1>

    <t:messagesPanel />

    <form:form
      action="${pageContext.request.contextPath}/article/upload" method="post"
      modelAttribute="fileUploadForm" enctype="multipart/form-data"> <!-- (1) (2) -->
      <table>
        <tr>
          <th width="35%">File to upload</th>
          <td width="65%">
            <form:input type="file" path="file" /> <!-- (3) -->
            ${f:h(fileUploadForm.fileName)}
            <form:errors path="file" />
          </td>
        </tr>
        <tr>
          <th width="35%">Description</th>
          <td width="65%">
            <form:input path="description" />
            <form:errors  path="description" />
          </td>
        </tr>
      </table>
      <div>
        <form:button name="confirm">Confirm</form:button>
      </div>

    </form:form>

<%-- 
    <form:form
      action="${pageContext.request.contextPath}/article/uploadFiles"
      method="post" enctype="multipart/form-data"
      modelAttribute="filesUploadForm">
      <!-- (1) -->
      <table>
        <tr>
          <td width="35%"><strong>File to upload</strong></td>
          <td width="35%"><form:input type="file" path="fileUploadForms[0].file" /></td>
          <td width="30%"><form:input path="fileUploadForms[0].description" /></td>
          <!-- (2) -->
        </tr>
        <tr>
          <td width="35%"><strong>File to upload</strong></td>
          <td width="35%"><form:input type="file" path="fileUploadForms[1].file" /></td>
          <td width="30%"><form:input path="fileUploadForms[1].description" /></td>
        </tr>
        <tr>
          <td>&nbsp;</td>
          <td>&nbsp;</td>
          <td><input type="submit" value="Upload" /></td>
        </tr>
      </table>
    </form:form>
--%>


    <h1 class="pageTitle">Files Upload</h1>

    <t:messagesPanel />

    <form:form
      action="${pageContext.request.contextPath}/article/uploadFiles" method="post"
      modelAttribute="filesUploadForm" enctype="multipart/form-data"> <!-- (1) -->
      <table>
        <tr>
          <th width="35%">File to upload</th>
          <td width="65%">
            <form:input type="file" path="fileUploadForms[0].file" /> <!-- (3) -->
            <form:errors path="fileUploadForms[0].file" />
          </td>
        </tr>
        <tr>
          <th width="35%">Description</th>
          <td width="65%">
            <form:input path="fileUploadForms[0].description" />
            <form:errors  path="fileUploadForms[0].description" />
          </td>
        </tr>
      </table>
      <table>
        <tr>
          <th width="35%">File to upload</th>
          <td width="65%">
            <form:input type="file" path="fileUploadForms[1].file" />
            <form:errors path="fileUploadForms[1].file" />
          </td>
        </tr>
        <tr>
          <th width="35%">Description</th>
          <td width="65%">
            <form:input path="fileUploadForms[1].description" />
            <form:errors  path="fileUploadForms[1].description" />
          </td>
        </tr>
      </table>
      <div>
        <form:button>Upload</form:button>
      </div>
    </form:form>

    <h1 class="pageTitle">Files Upload</h1>

    <form:form
      action="${pageContext.request.contextPath}/article/uploadFiles2" method="post"
      modelAttribute="filesUploadForm2" enctype="multipart/form-data"> <!-- (1) -->
      <table>
        <tr>
          <th width="35%">File to upload</th>
          <td width="65%">
            <form:input type="file" path="files" multiple="multiple" /> <!-- (2) -->
            <form:errors path="files" />
          </td>
        </tr>
      </table>
      <div>
        <form:button>Upload</form:button>
      </div>
    </form:form>


  </div>
</body>
</html>