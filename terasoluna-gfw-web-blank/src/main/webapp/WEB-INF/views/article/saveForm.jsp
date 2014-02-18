<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>Article Create Form Screen</title>
<link rel="stylesheet"
  href="${pageContext.request.contextPath}/resources/vendor/bootstrap-3.0.0/css/bootstrap.css">
<link rel="stylesheet"
  href="${pageContext.request.contextPath}/resources/app/css/styles.css">
<style type="text/css">
label {
    width: 100px !important;
}

input,textarea {
    width: 200px !important;
}
</style>
<script type="text/javascript"
  src="${pageContext.request.contextPath}/resources/vendor/jquery/jquery-1.10.2.js"></script>
</head>
<body>
  <div id="wrapper">
    <%-- screen name --%>
    <h1>Article Create Form Screen</h1>
    <%-- global message area --%>
    <t:messagesPanel />
    <%-- Search Form area --%>
    <div class="subArea">
      <form:form id="createForm"
        action="${pageContext.request.contextPath}/article/save"
        method="post" modelAttribute="articleForm">
        <div>
          <form:label path="articleId">Id</form:label>
          :
          <form:input path="articleId" />
          <form:errors path="articleId" />
        </div>
        <div>
          <form:label path="title">Title</form:label>
          :
          <form:input path="title" />
          <form:errors path="title" />
        </div>
        <div>
          <form:label path="overview">Overview</form:label>
          :
          <form:textarea path="overview" />
          <form:errors path="overview" />
        </div>
        <div>
          <form:label path="content">Content</form:label>
          :
          <form:textarea path="content" />
          <form:errors path="content" />
        </div>
        <div>
          <form:label path="publishedBy">Author</form:label>
          :
          <form:input path="publishedBy" />
          <form:errors path="publishedBy" />
        </div>
        <div>
          <form:label path="articleClassId">Article class</form:label>
          :
          <form:select path="articleClassId">
            <form:option value="">-</form:option>
            <form:options items="${CL_ARTICLE_CLASS}"/>
          </form:select>
          <form:errors path="articleClassId" />
        </div>
        <div class="formButtonArea">
          <form:button name="load" class="btn btn-primary">Load</form:button>
          <form:button name="persist" class="btn btn-primary">Create</form:button>
          <form:button name="merge" class="btn btn-primary">Update</form:button>
          <form:button name="remove" class="btn btn-primary">Delete</form:button>
        </div>
      </form:form>
    </div>
  </div>
</body>
</html>
