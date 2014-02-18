
<div id="criteriaPart">
  <form:form action="${pageContext.request.contextPath}/article/list"
    method="get" modelAttribute="articleSearchCriteriaForm">
    <form:input path="word" />
    <form:button>Search</form:button>
    <br>
  </form:form>
</div>



<t:pagination page="${page}"
  queryTmpl="page={page}&size={size}&${f:query(articleSearchCriteriaForm)}" />


<ul>
  <li class="disabled"><a href="#">&lt;&lt;</a></li>
  <li class="disabled"><a href="#">&lt;</a></li>
  <li class="active"><a href="?page=0&size=6">1</a></li>
  <li><a href="?page=1&size=6">2</a></li>
  <li><a href="?page=2&size=6">3</a></li>
  <li><a href="?page=3&size=6">4</a></li>
  <li><a href="?page=4&size=6">5</a></li>
  <li><a href="?page=5&size=6">6</a></li>
  <li><a href="?page=6&size=6">7</a></li>
  <li><a href="?page=7&size=6">8</a></li>
  <li><a href="?page=8&size=6">9</a></li>
  <li><a href="?page=9&size=6">10</a></li>
  <li><a href="?page=1&size=6">&gt;</a></li>
  <li><a href="?page=9&size=6">&gt;&gt;</a></li>
</ul>

    <t:pagination page="${page}"
      queryTmpl="page={page}&size={size}&${f:query(articleSearchCriteriaForm)}" />
