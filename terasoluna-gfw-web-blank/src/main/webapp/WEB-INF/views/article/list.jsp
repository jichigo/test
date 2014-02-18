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
    text-align:center;
}


</style>

</head>



<body>

  <div class="wrapper">

    <h1 class="pageTitle">Article Search</h1>


    <div id="criteriaPart">
      <form:form
        action="${pageContext.request.contextPath}/article/search"
        method="GET" modelAttribute="articleSearchCriteriaForm">
        <form:input path="word" cssErrorClass="" />
        <form:select path="sort">
            <form:option value="publishedDate,DESC">Newest</form:option>
            <form:option value="publishedDate,ASC">Oldest</form:option>
        </form:select>
        <form:button>Search</form:button>
        <br>
      </form:form>
    </div>


<a href="?${f:query(articleSearchCriteriaForm)}">search</a>

        ${f:query(articleSearchCriteriaForm)} <br>
        ${f:u('&xab=c%2Dd')} <br>
        
        <t:pagination outerElementClass="pagination" page="${page}"  pathTmpl="" queryTmpl="page={page}&size={size}&${f:query(articleSearchCriteriaForm)}" />

    <c:choose>

      <c:when test="${page != null && page.totalPages != 0}">

        <table class="maintable">
          <thead>

            <tr>
              <th class="no">No</th>
              <th class="articleClass">Class</th>
              <th class="title">Title</th>
              <th class="overview">Overview</th>
              <th class="date">Published Date</th>
            </tr>
          </thead>

          <c:forEach var="article" items="${page.content}"
            varStatus="rowStatus">
            <tr>
              <td class="no">${(page.number * page.size) +
                rowStatus.count}</td>
              <td class="articleClass">${f:h(CL_ARTICLE_CLASS[article.articleClass.code])}</td>
              <td class="title"><a
                href="${pageContext.request.contextPath}/article/${article.articleId}/read">${f:h(article.title)}</a>
              </td>
              <td class="overview">${f:h(article.overview)}</td>
              <td class="date">${f:h(article.publishedDate)}</td>
            </tr>
          </c:forEach>

        </table>


    <div class="paginationPart">
        <t:pagination page="${page}"
            outerElementClass="pagination"
            maxDisplayCount="0" />
        <div>
            <fmt:formatNumber value="${(page.number * page.size) + 1}" /> - 
            <fmt:formatNumber value="${(page.number * page.size) + page.numberOfElements}" />
        </div>
        <div>
            ${f:h(page.number + 1) } / 
            ${f:h(page.totalPages)} Pages
        </div>
        
        
<%-- 
queryTmpl="page={page}&size={size}&${f:query(articleSearchCriteriaForm)}&sort={sortOrderProperty},{sortOrderDirection}" 
        <div>
            <fmt:formatNumber value="${page.totalElements}" /> results
        </div>
--%>
    </div>

    <div class="paginationPart">
            (<fmt:formatNumber pattern="0.000" value="${searchedNanoTime / 1000 / 1000 / 1000}" /> seconds)

    <t:pagination page="${page}"
        outerElementClass="pagination"
        queryTmpl="page={page}&size={size}&word=${param.word}&sort={sortOrderProperty},{sortOrderDirection}" />
    </div>

    <div class="paginationPart">
    <t:pagination page="${page}"
        outerElementClass="pagination"
        queryTmpl="page={page}&size={size}&sort={sortOrderProperty},{sortOrderDirection}" />
    </div>

<!--  
    <t:pagination page="${page}"
        outerElementClass="pagination" />
-->

        <div class="paginationPart">

<%-- 
    <t:pagination page="${page}" 
                  outerElement="div"
                  outerElementClass="pagination"
                  innerElement="span"
                  disabledClass="hiddenPageLink"
                  activeClass="currentPageLink"
                  firstLinkText="First"
                  previousLinkText="Prev"
                  nextLinkText="Next"
                  lastLinkText="Last"
                  maxDisplayCount="5"
                   />

--%>

<%-- 
          <t:pagination page="${page}"
            queryTmpl="page={page}&size={size}&${f:query(articleSearchCriteriaForm)}" />
--%>



<%-- 
    <t:pagination page="${page}"
                  outerElementClass="pagination"
                  disabledHref="javascript:void(0);"
                  pathTmpl="${pageContext.request.contextPath}/article/list/{page}/{size}"
                  queryTmpl="sort={sortOrderProperty},{sortOrderDirection}" />
--%>
          <div class="paginationInformation">
            <%-- ... --%>
          </div>
        </div>


          <%-- 
          <t:pagination page="${page}"
            queryTmpl="page={page}&size={size}&${f:query(articleSearchCriteriaForm)}" />
          <t:pagination page="${page}"
            queryTmpl="page={page}&size={size}&sort={sortOrderProperty},{sortOrderDirection}&${f:query(articleSearchCriteriaForm)}" />

          <div class="paginationInformation">
            <div>
              <fmt:formatNumber value="${page.totalElements}" /> results
              (<fmt:formatNumber pattern="0.000" value="${searchedNanoTime / 1000 / 1000 / 1000}" /> seconds)
            </div>
            <div>
              ${f:h(page.number) + 1} / ${f:h(page.totalPages)} Pages
            </div>
          </div>
            --%>

          <%-- 
          <div class="paginationSimplePart" style="display: none;">

            <t:pagination page="${page}"
              queryTmpl="page={page}&size={size}&${f:query(articleSearchCriteriaForm)}" />

          <div class="paginationInformation">
            <div>
              <fmt:formatNumber value="${page.totalElements}" /> results
              (<fmt:formatNumber pattern="0.000" value="${searchedNanoTime / 1000 / 1000 / 1000}" /> seconds)
            </div>
            <div>
              ${f:h(page.number) + 1} / ${f:h(page.totalPages)} Pages
            </div>
          </div>
          </div>
--%>
      </c:when>

      <c:when test="${page != null && page.totalPages == 0}">
        <div class="message">Did not found article that match the
          criteria. Please specify the another criteria.</div>
      </c:when>

      <c:otherwise>
        <div class="message">Please specify the criteria.</div>
      </c:otherwise>

    </c:choose>

  </div>
</body>
</html>