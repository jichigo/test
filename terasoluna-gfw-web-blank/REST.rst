RESTfull Web Service
================================================================================

.. contents:: 目次
   :depth: 3
   :local:

Overview
--------------------------------------------------------------------------------

本説では、RESTfull Web Serviceをどのように設計し、実装するかについて、説明する。

.. todo::

    絶賛執筆中・・・。

|

RESTfull Web Serviceとは
^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
まずRESTとは、「\ **RE**\presentational \ **S**\tate \ **T**\ransfer」の略であり、
クライアント/サーバ間でデータをやりとりするアプリケーションを構築するための\ **アーキテクチャスタイル**\の一つである。

| RESTには、いくつかの重要な原則があり、これらの原則に従っているもの(システムなど)は \ **RESTful**\ と表現される。
| つまり、「RESTfull Web Service」とは、RESTの原則に従って構築されているWeb Serviceという事になる。

.. todo::

    RESTful Web Service利用時の構成例として、以下を図で示すとわかりやすいかな～。
    
    * Client to Server(Web Browser <=> \ **RESTful Web Service**\)
    * Server to Server(Web Browser <=> Web Application <=> \ **RESTful Web Service**\)

|

RESTfull Web Serviceを構築するためのアーキテクチャ
^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
| RESTfull Web Serviceを構築するためのアーキテクチャとして、ROAが存在する。
| ROAは、「\ **R**\esource \ **O**\riented \ **A**\rchitecture」の略であり、\ **RESTのアーキテクチャスタイル(原則)に従ったWeb Serviceを構築するための具体的なアーキテクチャ**\を定義している。
| 以下に、ROAのアーキテクチャについて説明する。

クライアントに提供する情報はWeb上のリソースとして公開
""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""
**システム上で管理する情報をクライアントに提供する手段として、Web上のリソースとして公開する。**

例えば、ショッピングサイトを提供するWebシステムであれば、以下のような情報がWeb上のリソースとして公開する事になる。

* 商品の情報
* 在庫の情報
* 注文の情報
* 会員の情報
* 会員毎の認証の情報(ログインIDとパスワードなど)
* 会員毎の注文履歴の情報
* 会員毎の認証履歴の情報
* and more ...

|

URIによるリソースの識別
""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""
| **クライアントに公開するリソースにURI(Universal Resource Identifier)を割り当て、Web上のリソースとして一意に識別できる状態にする。**
| 実際は、URIのサブセットであるURL(Uniform Resource Locator)を使用してリソースを一意に識別することになる。
| ROAでは、URIを使用してWeb上のリソースにアクセスできる状態になっていることを「アドレス可能性」と呼んでおり、これは同じURIを使用すれば、どこからでも同じリソースにアクセスできる事を示している。

| RESTfull Web Serviceで扱うURIは、\ **リソースの種類を表す名詞とリソースを識別するための値(IDなど)の組み合わせとする。**\
| 例えば、ショッピングサイトを提供するWebシステムで扱う商品情報のURIは、以下のようになる。

* | \ `http://example.com/items`\
  | **items** の部分がリソースの種類を表す名詞となり、リソースの数が複数になる場合は、複数系の名詞を使用する。
  | 上記例では、商品情報である事を表す名詞の複数系を指定しており、商品情報を一括で操作する際に使用するURIとなる。

* | \ `http://example.com/items/I312-535-01216`\
  | **I312-535-01216** の部分がリソースを識別するための値となり、リソース毎に異なる値となる。
  | 上記例では、商品情報を一意に識別するための値として商品IDを指定しており、特定の商品情報を操作する際に使用するURIとなる。

 .. warning::
 
    RESTfull Web Serviceで扱うURIには、下記で示すような\ **操作を表す動詞を含んではいけない。**\
    
    * \ `http://example.com/items?get&itemId=I312-535-01216`\
    * \ `http://example.com/items?delete&itemId=I312-535-01216`\
    
    上記例では、 URIの中に\ **get**\や\ **delete**\という動詞を含んでいるため、RESTfull Web Serviceで扱うURIとして適切ではない。
    
    RESTfull Web Serviceでは、\ **リソースに対する操作はHTTPメソッド(GET,POST,PUT,DELETE)を使用して表現される。**\

|

HTTPメソッドによるリソースの操作
""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""
| **リソースに対する操作は、HTTPメソッド(GET,POST,PUT,DELETEなど)を使い分けることで実現する。**
| ROAでは、HTTPメソッドの事を「統一インタフェース」と呼んでいる。
| これは、HTTPメソッドがWeb上で公開される全てのリソースに対して実行する事ができ、且つリソース毎にHTTPメソッドの意味が変わる事がないという事を意味している。

以下に、HTTPメソッドとリソースに対して行う操作の対応付けと、それぞれの操作が保証すべき事後条件について説明する。

 .. list-table::
    :header-rows: 1
    :widths: 10 20 35 35

    * - 項番
      - HTTPメソッド
      - リソースに対する操作
      - 操作が保証すべき事後条件
    * - | (1)
      - | GET
      - | リソースの取得
      - | 安全性、べき等性。
    * - | (2)
      - | POST
      - | リソースの作成
      - | 作成したリソースのURIの割り当てはサーバが行い、URIをクライアントに返却する。
    * - | (4)
      - | PUT
      - | リソースの作成又は更新
      - | べき等性。
    * - | (5)
      - | PATCH
      - | リソースの差分更新
      - | べき等性。
    * - | (6)
      - | DELETE
      - | リソースの削除
      - | べき等性。
    * - | (7)
      - | HEAD
      - | リソースのメタ情報の取得
      - | 安全性、べき等性。
    * - | (8)
      - | OPTIONS
      - | リソースに対して使用できるHTTPメソッドの調査。
        | リソースに対する操作ではない。
      - | 安全性、べき等性。

 .. note:: **安全性の保証とは**
 
    ある数字に1を何回掛けても、数字がかわらない事と同等である(10に1を何回掛けても結果は10のままである)。
    
    つまり、同じ操作を何回行っても元のリソースの状態が変わらない事を保証する事である。

 .. note:: **べき等性の保証とは**
 
    数字に0を何回掛けても0になる事と同等である(10に0を1回掛けても何回掛けても結果は共に0になる)。
    
    つまり、一度操作を行えば、その後で同じ操作を何回行ってもリソースの状態が変わらない事を保証する事である。
    ただし、別のクライアントが同じリソースの状態を変更している場合は、べき等性を保障する必要はなく、事前条件に対するエラー(排他エラー)として扱ってもよい。

 .. note:: **クライアントがリソースに割り当てるURIを指定してリソースを作成する場合**
 
    リソースを作成する際に、クライアントによってリソースに割り当てるURIを指定する場合は、\ **作成するリソースに割り当てるURIに対して、PUTメソッドを呼び出すことで実現する。**\

    指定されたURIにリソースが存在しない場合は新規にリソースを作成し、既にリソースが存在する場合はリソースの状態を更新するのが一般的な動作となる。

 .. todo::

    リソース作成時のPOSTとPUTの使い分けは、簡易的な図があった方がイメージしやすいだろうな～。
  
|

ストーレスなクライアント/サーバ間の通信
""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""
| **サーバ上でアプリケーションの状態は保持せずに、クライアントからリクエストされてきた情報のみで処理を行うようにする。**
| ROAでは、サーバ上でアプリケーションの状態を保持しない事を「ステートレス性」と呼んでいる。
| これは、アプリケーションサーバのメモリ(HTTPセッションなど)にアプリケーションの状態を保持しない事を意味し、リクエストされた情報のみでリソースに対する操作が完結できる状態にしておく事を意味している。

 .. note:: **アプリケーションの状態とは**
 
    Webページの遷移状態、入力値やプルダウン/チェックボックス/ラジオボタンなどの選択状態、認証状態などの事である。

|

リソースのフォーマット
""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""
| **リソースのフォーマットは、JSON又はXMLを使用する。**
| リソースの種類によっては、JSONやXML以外のフォーマットを使ってもよい。
| 例えば、統計情報に分類される様なリソースでは、折れ線グラフを画像フォーマット(バイナリデータ)として扱う使う事が考えられる。

| リソースのフォーマットとして、複数のフォーマットをサポートする場合は、HTTPのAcceptヘッダ及びContent-TypeのMIMEタイプによって切り替えを行う。

RESTful Web Serviceで使用される代表的なMIMEタイプを以下に示す。

 .. list-table::
    :header-rows: 1
    :widths: 10 30 60

    * - 項番
      - フォーマット
      - MIMEタイプ
    * - | (1)
      - | JSON
      - | application/json
    * - | (2)
      - | XML
      - | application/xml

 .. tip:: **拡張子によるレスポンス形式の切り替え**
 
    レスポンスのフォーマットについては、Acceptヘッダによる切り替え以外に、拡張子として指定する方法もある。
    Spring MVCではAcceptヘッダによる切り替えに加えて、拡張子による切り替えもサポートしている。
    
    拡張子で切り替える場合のURI例)
    
    * \ `http://example.com/items.json`\
    * \ `http://example.com/items.xml`\
    * \ `http://example.com/items/I312-535-01216.json`\
    * \ `http://example.com/items/I312-535-01216.xml`\
    
    拡張子による切り替えの方が、Acceptヘッダによって切り替える場合に比べると、より直感的なURIとなる。

|

適切なHTTPステータスコードの使用
""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""
| \ **クライアントへ返却するレスポンスには、適切なHTTPステータスコードを設定する。**\
| HTTPステータスコードには、クライアントから受け取ったリクエストをサーバがどのように処理したかを示す値を設定する。
| \ **これはHTTPの仕様であり、HTTPの仕様に準拠することを推奨する。**\

 .. tip:: **HTTPの仕様について**
 
    `RFC 2616(Hypertext Transfer Protocol -- HTTP/1.1)の6.1.1 Status Code and Reason Phrase <http://tools.ietf.org/search/rfc2616#section-6.1.1>`_ を参照されたい。

| ブラウザにHTMLを返却するような伝統的なWebシステムでは、処理結果に関係なく\ ``"200 OK"``\を応答し、処理結果はエンティティボディの中で表現するという事が少なからず存在したが、HTMLを応答するような伝統的なWebアプリケーションでは、この仕組みでも問題が発生する事はなかった。
| しかし、この仕組みでRESTfull Web Serviceを構築した場合、以下のような問題が潜在的に存在することになるため、適切なHTTPステータスコードを設定することを推奨する。

* | 処理結果(成功と失敗)のみを判断すればよい場合でも、エンティティボディを解析する必要があるため、非効率である。
* | エラーハンドリングを行う際に、システム独自に定義されたエラーコードを意識する必要があるため、クライアント側のアーキテクチャ(設計及び実装)に悪影響を与える可能性がある。
* | エラー原因を解析する際に、システム独自に定義されたエラーコードの意味を理解しておく必要があるため、直感的なエラー解析の妨げになる可能性がある。

|

関連のあるリソースへのリンク
""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""
| \ **リソースの表現(JSONやXML)の中には、指定されたリソースと関連をもつ他のリソースへのリンク(URI)を含める。**\
| ROAでは、リソース状態の表現の中に、他のリソースへのリンクを含めることを「接続性」と呼んでいる。
| これは、関連をもつリソース同士が相互にリンクを保持し、そのリンクをたどる事で関連する全てのリソースに接続できる事を示している。

下記に、ショッピングサイトの会員情報のリソースを例に、リソースの接続性について説明する。

 .. figure:: ./images_REST/RESTConnectivity.png
   :alt: Image of resource connectivity
   :width: 100%

.. todo::

    * リンク項目の持ち方のベストプラクティスについては継続調査・・・。
    * どういう方針でリンクをはるのがベストプラクティスについては継続調査・・・。

1. 会員情報のリソースを取得(\ ``GET http://example.com/memebers/M909-123-09123/``\)を行うと、以下のJSONが返却される。

 .. code-block:: json
    :emphasize-lines: 11-12
    
    {
        "memberId" : "M909-123-09123",
        "memberName" : "John Smith",
        "address" : {
            "address1" : "45 West 36th Street",
            "address2" : "7th Floor",
            "city" : "New York",
            "state" : "NY",
            "zipCode" : "10018"
        },
        "ordersUri" : "http://example.com/memebers/M909-123-09123/orders/",
        "authenticationsUri" : "http://example.com/memebers/M909-123-09123/authentications/"
    }

 | ハイライトした部分が、関連をもつ他のリソースへのリンク(URI)となる。
 | 上記例では、会員毎の注文履歴と認証履歴のリソースに対して接続性を保持している。

2. | 返却されたJSONに設定されているURIを使用して、注文履歴のリソースを取得(\ ``GET http://example.com/memebers/M909-123-09123/orders/``\)を行うと、以下のJSONが返却される。

 .. code-block:: json
    :emphasize-lines: 8,15,18

    {
        "orders" : [
            {
                "orderId" : "029b49d7-0efa-411b-bc5a-6570ce40ead8",
                "orderDatetime" : "2013-12-27T20:34:50.897Z", 
                "orderName" : "Note PC",
                "shopName" : "Global PC Shop",
                "orderUri" : "http://example.com/memebers/M909-123-09123/orders/029b49d7-0efa-411b-bc5a-6570ce40ead8"
            },
            {
                "orderId" : "79bf991d-d42d-4546-9265-c5d4d59a80eb",
                "orderDatetime" : "2013-12-03T19:01:44.109Z", 
                "orderName" : "Orange Juice 100%",
                "shopName" : "Global Food Shop",
                "orderUri" : "http://example.com/memebers/M909-123-09123/orders/79bf991d-d42d-4546-9265-c5d4d59a80eb"
            }
        ],
        "ownerMemberUri" : "http://example.com/memebers/M909-123-09123/"
    }

 | ハイライトした部分が、関連をもつ他のリソースへのリンク(URI)となる。
 | 上記例では、注文履歴のオーナの会員情報のリソース及び注文履歴のリソースに対する接続性を保持している。

3. 注文履歴のオーナとなる会員情報のリソースを再度取得(\ ``GET http://example.com/memebers/M909-123-09123/``\)し、返却されたJSONに設定されているURIを使用して、認証履歴のリソースを取得(\ ``GET http://example.com/memebers/M909-123-09123/authentications/``\)を行うと、以下のJSONが返却される。

 .. code-block:: json
    :emphasize-lines: 16

    {
        "authentications" : [
            {
                "authenticationId" : "6ae9613b-85b6-4dd1-83da-b53c43994433",
                "authenticationDatetime" : "2013-12-27T20:34:50.897Z", 
                "clientIpaddress" : "230.210.3.124",
                "authenticationResult" : true
            },
            {
                "authenticationId" : "103bf3c5-7707-46eb-b2d8-c00ce6243d5f",
                "authenticationDatetime" : "2013-12-26T10:03:45.001Z", 
                "clientIpaddress" : "230.210.3.124",
                "authenticationResult" : false
            }
        ],
        "ownerMemberUri" : "http://example.com/memebers/M909-123-09123/"
    }

 | ハイライトした部分が、関連をもつ他のリソースへのリンク(URI)となる。
 | 上記例では、認証履歴のオーナとなる会員情報のリソースに対して接続性を保持している。

|

RESTfull Web Serviceの実践
^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^

チュートリアル
""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""
| まず、\ :ref:`RESTTutorial`\を行うことで、 詳細な説明の前にまず手を動かして、TERASOLUNA Global FrameworkによるRESTfull Web Serviceの開発を体感していただきたい。
| なお、本章を読み終えた後にもう一度"\ :ref:`RESTTutorial`\"を振り返るとより理解が深まる。

|

How to design
--------------------------------------------------------------------------------

URIの割り当て
^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^

| Web上に公開するリソースに対して、以下の２つのURIを割り当てる。
| 下記の例では、会員情報をWeb上に公開する場合のURI例を記載している。

 .. list-table::
    :header-rows: 1
    :widths: 10 35 25 30

    * - 項番
      - URIの形式
      - URIの具体例
      - 説明
    * - | (1)
      - | /{リソースのコレクションを表す名詞}
      - | /members
      - | リソースを一括で操作する際に使用するURI。
    * - | (2)
      - | /{リソースのコレクションを表す名詞/リソースの識別子(IDなど)}
      - | /members/M000000001
      - | 特定のリソースを操作する際に使用するURI。

|

| Web上に公開する関連リソースへのURIは、ネストさせて表現する。
| 下記の例では、会員毎の注文情報をWeb上に公開する場合のURI例を記載している。
    
 .. list-table::
    :header-rows: 1
    :widths: 10 35 25 30
    
    * - 項番
      - URIの形式
      - URIの具体例
      - 説明
    * - | (3)
      - | {リソースのURI}/{関連リソースのコレクションを表す名詞}
      - | /members/M000000001/orders
      - | 関連リソースを一括で操作する際に使用するURI。
    * - | (4)
      - | {リソースのURI}/{関連リソースのコレクションを表す名詞}/{関連リソースの識別子(IDなど)}
      - | /members/M000000001/orders/O000000001
      - | 特定の関連リソースを操作する際に使用するURI。

|

| Web上に公開する関連リソースの要素が1件の場合は、関連リソースを示す名詞は複数系ではなく単数形とする。
| 下記の例では、会員毎の資格情報をWeb上に公開する場合のURI例を記載している。

 .. list-table::
    :header-rows: 1
    :widths: 10 35 25 30

    * - 項番
      - URIの形式
      - URIの具体例
      - 説明
    * - | (5)
      - | {リソースのURI}/{関連リソースを表す名詞}
      - | /members/M000000001/credential
      - | 要素が1件の関連リソースを操作する際に使用するURI。

|

HTTPメソッドの割り当て
^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
.. todo::

    抽出したリソース(URI)に対して、どのような指針でHTTPメソッドを割り当てるかについて記載する。


|

リソースのフォーマット
^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
.. todo::

    リソースのフォーマットをどのような指針で設計するかについて記載する。

|

HTTPステータスコード
^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
HTTPステータスコードは、以下の指針に則って応答する。

* | リクエストが成功した場合は、成功又は転送を示すHTTPステータスコード(2xx又は3xx系)を応答する。
* | リクエストが失敗した原因がクライアント側にある場合は、クライアントエラーを示すHTTPステータスコード(4xx系)を応答する。
* | リクエストが失敗した原因がサーバ側にある場合は、サーバエラーを示すHTTPステータスコード(5xx系)を応答する。

|

リクエストが成功した場合のHTTPステータスコード
""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""
リクエストが成功した場合は、状況に応じて以下のHTTPステータスコードを応答する。
 
 .. list-table::
    :header-rows: 1
    :widths: 10 20 30 40

    * - | 項番
      - | HTTP
        | ステータスコード
      - | 説明
      - | 適用条件
    * - | (1)
      - | 200
        | OK
      - | リクエストが成功した事を通知するHTTPステータスコード。
      - | リクエストが成功した結果として、レスポンスのエンティティボディに、リクエストに対応するリソースの情報を出力する際に応答する。
    * - | (2)
      - | 201
        | Created
      - | 新しいリソースを作成した事を通知するHTTPステータスコード。
      - | POSTメソッドを使用して、新しいリソースを作成した際に使用する。
        | レスポンスのLocationヘッダに、作成したリソースのURIを設定する。
    * - | (3)
      - | 204
        | No Content
      - | リクエストが成功した事を通知するHTTPステータスコード。
      - | リクエストが成功した結果として、レスポンスのエンティティボディに、リクエストに対応するリソースの情報を出力しない時に応答する。
    * - | (4)
      - | 304
        | Not Modified
      - | クライアントとサーバで保持しているリソースの状態が同じである(つまり、リソースの状態が更新されていない)事を通知するHTTPステータスコード。
      - | -

 .. note::
 
    \ ``"200 OK``\ と \ ``"204 No Content"``\の違いは、レスポンスボディにリソースの情報を出力する/しないの違いとなる。

|

リクエストが失敗した原因がクライアント側にある場合のHTTPステータスコード
""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""
リクエストが失敗した原因がクライアント側にある場合は、状況に応じて以下のHTTPステータスコードを応答する。

|

リソースを扱う個々のアプリケーションで意識する必要があるステータスコードは以下の通り。

 .. list-table::
    :header-rows: 1
    :widths: 10 20 30 40

    * - | 項番
      - | HTTP
        | ステータスコード
      - | 説明
      - | 適用条件
    * - | (1)
      - | 400
        | Bad Request
      - | リクエストの構文やリクエストされた値が間違っている事を通知するHTTPステータスコード。
      - | エンティティボディに指定されたJSONやXMLの形式不備を検出した場合や、JSONやXML又はリクエストパラメータに指定された入力値の不備を検出した場合に応答する。
    * - | (2)
      - | 404
        | Not Found
      - | 指定されたリソースが存在しない事を通知するHTTPステータスコード。
      - | 指定されたURIに対応するリソースがシステム内に存在しない場合に応答する。
    * - | (3)
      - | 405
        | Method Not Allowed
      - | 使用されたHTTPメソッドが、指定されたリソースでサポートしていない事を通知するHTTPステータスコード。
      - | サポートされていないHTTPメソッドが使用された事を検知した場合に応答する。
        | レスポンスのAllowヘッダに、許可されているメソッドの列挙を設定する。
    * - | (4)
      - | 409
        | Conflict
      - | リクエストされた内容でリソースの状態を変更すると、リソースの状態に矛盾が発生するため処理を通知するHTTPステータスコード。
      - | クライアントによって解決できる業務エラーを検知した場合に応答する。
        | エンティティボディには矛盾を解決するために必要なエラー内容を出力する必要がある。

|

| リソースを扱う個々のアプリケーションで意識する必要がないステータスコードは以下の通り。
| 以下のステータスコードは、共通処理として意識する必要がある。

 .. list-table::
    :header-rows: 1
    :widths: 10 20 30 40

    * - | 項番
      - | HTTP
        | ステータスコード
      - | 説明
      - | 適用条件
    * - | (5)
      - | 401
        | Unauthorized
      - | リソースへアクセスするために、認証が必要である事を通知するHTTPステータスコード。
      - | -
    * - | (6)
      - | 403
        | Forbidden
      - | サーバがリクエストの実行を拒否した事を通知するHTTPステータスコード。
      - | -
    * - | (7)
      - | 406
        | Not Acceptable
      - | 指定された形式でリソースの状態を応答する事が出来ないため、リクエストを受理できない事を通知するHTTPステータスコード。
      - | レスポンス形式として、Acceptヘッダで指定されたMIMEタイプをサポートしていない場合に応答する。
    * - | (8)
      - | 412
        | Precondition Failed
      - | If-Match, If-None-Match, If-Unmodified-Sinceヘッダで与えられる条件に対して、条件に一致しないものが含まれる事を通知するHTTPステータスコード。
      - | -
    * - | (9)
      - | 415
        | Unsupported Media Type
      - | エンティティボディに指定された形式をサポートしていないため、リクエストが受け取れない事を通知するHTTPステータスコード。
      - | リクエスト形式として、Content-Typeヘッダで指定された形式をサポートしていない場合に応答する。

|

リクエストが失敗した原因がサーバ側にある場合のHTTPステータスコード
""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""
リクエストが失敗した原因がサーバ側にある場合は、状況に応じて以下のHTTPステータスコードを応答する。

 .. list-table::
    :header-rows: 1
    :widths: 10 20 30 40

    * - | 項番
      - | HTTP
        | ステータスコード
      - | 説明
      - | 適用条件
    * - | (1)
      - | 500
        | Internal Server Error
      - | サーバ内部でエラーが発生した事を通知するHTTPステータスコード。
      - | サーバ内で予期しないエラーが発生した場合や、正常稼働時には発生してはいけない状態を検知した場合に応答する。


.. todo::

    * Createdにて、HTTP的にはレスポンスとして、リソースにリスト(特性とURI)を\ **返却すべき**\とあるが、特性ってなんだろ・・。
    
    * No Contentも微妙・・。
    
    * Not Modifiedはレスポンスのキャッシュ制御を絡めて説明する必要あり。
    
    * Unauthorized&Forbiddenは認証・認可を絡めて説明する必要あり。
    
    * クライアントによって矛盾を解決できる業務エラーってあるのかな？あった場合には何エラーにすべきか・・。
    
    * Precondition Failedは排他制御と絡めて説明する必要あり。

|

排他制御
^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^

.. todo::

    排他制御をどのような指針で行うかについて記載する。
    
    次版かな・・・

|

レスポンスのキャッシュ制御
^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
.. todo::

    レスポンスのキャッシュ制御をどのような指針で行うかについて記載する。

    次版かな・・・

|

認証・認可
^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
.. todo::

    認証及び認可制御をどのような指針で行うかについて記載する。
    Web Serviceレベルでの認可やリソースレベルでの認可などを、どのように制御するかについて記載する。
    Basic認証？Digest認証？OAuth？などなど・・
    
    次版かな・・・

|

バージョニング
^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^

.. todo::

    Web Service自体のバージョン管理及び複数バージョンの並行稼働に関する指針について記載する。
    URI内？リクエストパラメータ内？ヘッダ内(これはなさそう・・)？などなど
    
    次版かな・・・

|

.. _RESTHowToUse:

How to use
--------------------------------------------------------------------------------

Webアプリケーションの構成
^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
RESTful Web Serviceを構築する場合は、以下のいずれかの構成でWebアプリケーション(war)を構築することを推奨する。

 .. list-table::
    :header-rows: 1
    :widths: 10 30 60

    * - 項番
      - 構成
      - 説明
    * - | (1)
      - | RESTful Web Service専用のWebアプリケーションとして構築する。
      - | RESTful Web Serviceを利用するクライアントアプリケーション(UI層のアプリケーション)との独立性を確保したい(する必要がある)場合は、RESTful Web Service専用のWebアプリケーションとして構築することを推奨する。
        |
        | RESTful Web Serviceを利用するクライアントアプリケーションが複数になる場合は、この方法でRESTful Web Serviceを生成することになる。
    * - | (2)
      - | RESTful Web Service用の\ ``DispatcherServlet``\を設けて構築する。
      - | RESTful Web Serviceを利用するクライアントアプリケーション(UI層のアプリケーション)との独立性を確保する必要がない場合は、RESTful Web Serviceとクライアントアプリケーションを一つのWebアプリケーション(war)として構築してもよい。
        | ただし、RESTful Web Service用のリクエストを受ける\ ``DispatcherServlet``\と、クライアントアプリケーション用のリクエストを受け取る\ ``DispatcherServlet``\は分割して構築することを推奨する。

 .. note:: **クライアントアプリケーション(UI層のアプリケーション)とは**

    ここで言うクライアントアプリケーション(UI層のアプリケーション)とは、HTML, JavaScriptなどのスクリプト, CSS(Cascading Style Sheets)といったクライアント層(UI層)のコンポーネントを応答するアプリケーションの事をさす。
    JSPなどのテンプレートエンジンによって生成されるHTMLも対象となる。

 .. note:: **DispatcherServletを分割する事を推奨する理由**

    Spring MVCでは、\ ``DispatcherServlet``\毎にアプリケーションの動作設定を定義することになる。
    そのため、RESTful Web Serviceとクライアントアプリケーション(UI層のアプリケーション)のリクエストを同じ\ ``DispatcherServlet``\で受ける構成にしてしまうと、RESTful Web Service又はクライアントアプリケーション固有の動作設定を定義する事ができなくなったり、設定が煩雑又は複雑になることがある。
    
    本ガイドラインでは、上記の様な問題が起こらないようにするために、RESTful Web Serviceをクライアントアプリケーションを同じWebアプリケーション(war)として構築する場合は、\ ``DispatcherServlet``\を分割することを推奨している。

 .. todo::

    Webアプリケーション内の構成を、図で示した方がよい気がする・・。


|

アプリケーションの設定
^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^

RESTfull Web Serviceで必要となるSpring MVCの機能を有効化するための設定
""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""
| RESTful Web Service用のbean定義ファイルを作成する。
| RESTful Web Serviceを構築する際に最低限必要となる定義は、以下の通りである。

- :file:`spring-mvc.xml`

  RESTful Web Serviceとクライアントアプリケーションを同じWebアプリケーションとして構築する場合は\ :file:`spring-mvc-rest.xml`\ に定義する。
  RESTful Web Serviceとクライアントアプリケーションを同じWebアプリケーションとして構築する場合は、「\ :ref:`RESTSettingsOfDeployInSameWarFileRestAndClientApplication`\」が必要となる。

 .. code-block:: xml
    :emphasize-lines: 17, 22, 26, 30, 40

    <?xml version="1.0" encoding="UTF-8"?>
    <beans xmlns="http://www.springframework.org/schema/beans" 
        xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
        xmlns:context="http://www.springframework.org/schema/context"
        xmlns:mvc="http://www.springframework.org/schema/mvc"
        xmlns:util="http://www.springframework.org/schema/util"
        xmlns:aop="http://www.springframework.org/schema/aop"
        xsi:schemaLocation="
            http://www.springframework.org/schema/mvc http://www.springframework.org/schema/mvc/spring-mvc.xsd
            http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd
            http://www.springframework.org/schema/util http://www.springframework.org/schema/util/spring-util.xsd
            http://www.springframework.org/schema/context http://www.springframework.org/schema/context/spring-context.xsd
            http://www.springframework.org/schema/aop http://www.springframework.org/schema/aop/spring-aop.xsd
    ">

        <!-- Load properties files for placeholder. -->
        <!-- (1) -->
        <context:property-placeholder 
            location="classpath*:/META-INF/spring/*.properties" />
    
        <!-- Scan & register components of RESTful Web Service. -->
        <!-- (2) -->
        <context:component-scan base-package="com.example.rest.app" />
    
        <!-- Register components of Spring MVC. -->
        <!-- (3) -->
        <mvc:annotation-driven />
    
        <!-- Register components of interceptor. -->
        <!-- (4) -->
        <mvc:interceptors>
            <mvc:interceptor>
                <mvc:mapping path="/**" />
                <bean class="org.terasoluna.gfw.web.logging.TraceLoggingInterceptor" />
            </mvc:interceptor>
            <!-- omitted -->
        </mvc:interceptors>
    
        <!-- Register components of AOP. -->
        <!-- (5) -->
        <bean id="handlerExceptionResolverLoggingInterceptor" 
            class="org.terasoluna.gfw.web.exception.HandlerExceptionResolverLoggingInterceptor">
            <property name="exceptionLogger" ref="exceptionLogger" />
        </bean>
        <aop:config>
            <aop:advisor advice-ref="handlerExceptionResolverLoggingInterceptor"
                pointcut="execution(* org.springframework.web.servlet.HandlerExceptionResolver.resolveException(..))" />
        </aop:config>

    </beans>

 .. list-table::
    :header-rows: 1
    :widths: 10 90

    * - 項番
      - 説明
    * - | (1)
      - | アプリケーション層のコンポーネントでプロパティファイルに定義されている値を参照する必要がある場合は、\ ``<context:property-placeholder>``\要素を使用してプロパティファイルを読み込む必要がある。
        | プロパティファイルから値を取得する方法の詳細ついては、「:doc:`PropertyManagement`」を参照されたい。
    * - | (2)
      - | RESTful Web Service用のアプリケーション層のコンポーネント(ControllerやHelperクラスなど)をスキャンしてbean登録する。
        | \ ``"com.example.rest.app"``\ の部分はプロジェクト毎のパッケージ名に変更すること。
    * - | (3)
      - | RESTful Web Serviceを提供するために必要となるSpring MVCのフレームワークコンポーネントをbean登録する。
        | 上記設定を行うことで、リソースのフォーマットとしてJSONとXMLを使用する事ができる。
        | ただし、リソースのフォーマットとしてXMLを使用する場合は、別途XXE Injection対策を行う必要があるため、「\ :ref:`RESTAppendixEnabledXXEInjectProtection`\」を行う必要がある。
        | 上記例ではデフォルトのままだが、ページネーションなどの機能を使用する場合は、必要に応じて別途設定を追加する必要がある。
    * - | (4)
      - | RESTful Web Serviceを提供するために必要となるSpring MVCのインターセプタをbean登録する。
        | 上記例では、共通ライブラリから提供されている\ ``TraceLoggingInterceptor``\のみを定義しているが、データアクセスとしてJPAを使う場合は、別途\ ``OpenEntityManagerInViewInterceptor``\の設定を追加する必要がある。
        | \ ``OpenEntityManagerInViewInterceptor``\については、\ :doc:`DataAccessJpa`\を参照されたい。
    * - | (5)
      - | Spring MVCのフレームワークでハンドリングされた例外を、ログ出力するためのAOP定義を指定する。

 .. note::
 
    RESTful Web Serviceとクライアントアプリケーションを一つのWebアプリケーションとして構築する場合は、クライアントアプリケーション用の\ ``DispatcherServlet``\に、RESTful Web Service用のアプリケーション層のコンポーネントがスキャンされないようにする事を推奨する。

    スキャンされないようにする方法としては、アプリケーション層のコンポーネントを格納するベースパッケージを分けて、それぞれのSpring MVCのbean定義ファイルにて、\ ``<context:component-scan>``\要素のbase-package属性に必要なコンポーネントが格納されているベースパッケージ名を指定する方法が、もっともシンプルである。
    
    ベースパッケージを分けることができない場合は、\ ``<context:component-scan>``\要素の子要素として、\ ``<context:exclude-filter>``\要素や\ ``<context:include-filter>``\要素を使用する事で必要なコンポーネントのみをスキャンしbean登録する事ができる。
    詳細については、\ `Spring Framework Reference Documentationの「Using filters to customize scanning」 <http://docs.spring.io/spring/docs/3.2.x/spring-framework-reference/html/beans.html#beans-scanning-filters>`_\を参照されたい。

|

OPTIONSメソッドのリクエストをControllerにディスパッチするための設定
""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""
| \ ``DispatcherServlet``\のデフォルトの設定では、OPTIONSメソッドのリクエストはControllerにディスパッチされずに、\ ``DispatcherServlet``\が許可しているメソッドのリストをAllowヘッダに設定して応答する。
| そのため、URI毎に許可しているメソッドのリストをAllowヘッダに設定して応答する必要がある場合は、OPTIONSメソッドのリクエストをControllerへディスパッチするための設定を追加する必要となる。

- :file:`web.xml`

 .. code-block:: xml
    :emphasize-lines: 10-14

    <!-- omitted -->

    <servlet>
        <servlet-name>restAppServlet</servlet-name>
        <servlet-class>org.springframework.web.servlet.DispatcherServlet</servlet-class>
        <init-param>
            <param-name>contextConfigLocation</param-name>
            <param-value>classpath*:META-INF/spring/spring-mvc-rest.xml</param-value>
        </init-param>
        <!-- (1) -->
        <init-param>
            <param-name>dispatchOptionsRequest</param-name>
            <param-value>true</param-value>
        </init-param>
        <load-on-startup>2</load-on-startup>
    </servlet>

    <!-- omitted -->

 .. list-table::
   :header-rows: 1
   :widths: 10 90

   * - | 項番
     - | 説明
   * - | (1)
     - | RESTful Web Serviceのリクエストを受け付ける\ ``DispatcherServlet``\の初期化パラメータ(dispatchOptionsRequest)の値を、\ ``true``\に設定する。

|

Resourceクラスの作成
^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^

| 本ガイドラインでは、Web上に公開するリソースのデータを保持するクラスとして、Resourceクラスを設けることを推奨している。
| Resourceクラスの役割は以下の通りである。

 .. list-table::
    :header-rows: 1
    :widths: 10 30 60

    * - 項番
      - 役割
      - 説明
    * - | (1)
      - | リソースのデータ構造の定義を行う。
      - | Web上に公開するリソースのデータ構造を定義する。
        | データベースなどの永続層で管理しているデータの構造のままWeb上のリソースとして公開する事は、一般的には稀である。
    * - | (2)
      - | フォーマットに関する定義を行う。
      - | リソースのフォーマットに関する定義を、アノテーションを使って指定する。
        | 使用するアノテーションは、リソースの形式(JSON/XMLなど)よって異なり、JSON形式の場合はJacksonのアノテーション、XML形式の場合はJAXBのアノテーションを使用する事になる。
    * - | (3)
      - | 入力チェックルールの定義を行う。
      - | 項目毎の単項目の入力チェックルールを、Bean Validationのアノテーションを使って指定する。
        | 入力チェックの詳細については、「:doc:`Validation`」を参照されたい。

|

以下にResourceクラスの作成例を示す。

例として、リソースのフォーマットは以下のようなJSON形式であるものとする。

 .. code-block:: json

    {
        "memberId" : "af9b570e-8a7d-4d6f-ab49-81d8db3a2923",
        "firstName" : "John",
        "lastName" : "Smith",
        "gender" : "MAN",
        "emailAddress" : "john.smith@test.com",
        "phoneNumber" : "09012345678",
        "address" : "Tokyo,Japan"
    }

上記のようなJSON形式のリソースを扱うためには、以下のようなResourceクラスを作成する。

 .. code-block:: java

    public class MemberResource implements Serializable {
    
        private static final long serialVersionUID = 1L;
    
        @Null(groups = MemberCreating.class)
        @NotNull(groups = MemberUpdating.class)
        @Size(min = 36, max = 36, groups = MemberUpdating.class)
        private String memberId;
    
        @NotNull
        @Size(min = 1, max = 50)
        private String firstName;
    
        @NotNull
        @Size(min = 1, max = 50)
        private String lastName;
    
        @NotNull
        @Size(min = 1)
        @ExistInCodeList(codeListId = "CL_GENDER")
        private String gender;
    
        @NotNull
        @Size(min = 1, max = 256)
        private String emailAddress;
    
        @Size(max = 20)
        private String phoneNumber;
    
        @Size(max = 256)
        private String address;

        // getter/setter omitted
    
    }

また、リソースのコレクションを返却する場合は、Resourceクラスをリスト形式で保持するコレクション用のResourceクラスを作成する。

 .. code-block:: json

    {
        "members" : [ {
            "memberId" : "66278211-1cd2-4108-843e-d691f94ffd91",
            "firstName" : "FirstName",
            "lastName" : "LastName",
            "gender" : "MAN",
            "emailAddress" : "test@email.com",
            "phoneNumber" : null,
            "address" : null
        }, {
            "memberId" : "217d05f8-55fc-40c4-b889-5cc4ef39e0a1",
            "firstName" : "FirstName",
            "lastName" : "LastName",
            "gender" : "MAN",
            "emailAddress" : "test@email.com",
            "phoneNumber" : null,
            "address" : null
        }],
        "totalCount" : 2
    }

上記のようなJSON形式のリソースを扱うためには、以下のようなResourceクラスを作成する。

 .. code-block:: java

    public class MembersResource implements Serializable {
    
        private static final long serialVersionUID = 1L;
    
        private final List<MemberResource> members = new ArrayList<>();
    
        private long totalCount;
    
        // getter/setter omitted
    
    }
    
ただし、リスト以外の

.. todo::

    * JSONを例にとって、フォーマットの定義及び制御するためのアノテーションの説明を追加したい。(Appendixでもよいかも・・)
    
    * オブジェクトにラップせずに直接リストとして受け渡しする方法についても記載した方がよいか？

|

Controllerの作成
^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
| Controllerはリソース毎に作成する。
| Controllerでは、以下の2つのURIに対するAPIを提供する。

 .. list-table::
    :header-rows: 1
    :widths: 10 55 35

    * - 項番
      - URI形式
      - URIの具体例
    * - | (1)
      - | /{リソースのコレクションを表す名詞}
      - | /members
    * - | (2)
      - | /{リソースのコレクションを表す名詞}/{リソースの識別子(IDなど)}
      - | /members/M000000001

|

Controllerクラスの作成
""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""
| 以下に、Controllerクラスの作成例を示す。
| 個々のAPIの実装例については、別途説明するため、ここではどのようなメソッドが必要になるのかについて説明する。

 .. code-block:: java
    :emphasize-lines: 1, 5, 14, 22, 29, 38, 47, 55

    @RequestMapping("members") // (1)
    @Controller
    public class MembersRestController {
    
        // (2)
        @RequestMapping(method = { RequestMethod.GET, RequestMethod.HEAD })
        @ResponseBody
        public ResponseEntity<MembersResource> getMembers(
                @Validated MembersSearchQuery query,
                Pageable pageable) {
            // omitted
        }
    
        // (3)
        @RequestMapping(method = RequestMethod.POST)
        @ResponseBody
        public ResponseEntity<MemberResource> createMember(
                @RequestBody @Validated MemberResource requestedResource) {
            // omitted
        }
    
        // (4)
        @RequestMapping(method = RequestMethod.OPTIONS)
        @ResponseBody
        public ResponseEntity<Void> optionsMembers() {
            // omitted
        }
    
        // (5)
        @RequestMapping(value = "{memberId}",
                        method = { RequestMethod.GET, RequestMethod.HEAD })
        @ResponseBody
        public ResponseEntity<MemberResource> getMember(
                @PathVariable("memberId") String memberId) {
            // omitted
        }
    
        // (6)
        @RequestMapping(value = "{memberId}", method = RequestMethod.PUT)
        @ResponseBody
        public ResponseEntity<MemberResource> updateMember(
                @PathVariable("memberId") String memberId,
                @RequestBody @Validated MemberResource requestedResource) {
            // omitted
        }
    
        // (7)
        @RequestMapping(value = "{memberId}", method = RequestMethod.DELETE)
        @ResponseBody
        public ResponseEntity<Void> deleteMember(
                @PathVariable("memberId") String memberId) {
            // omitted
        }
    
        // (8)
        @RequestMapping(value = "{memberId}", method = RequestMethod.OPTIONS)
        @ResponseBody
        public ResponseEntity<Void> optionsMember(
                @PathVariable("memberId") String memberId) {
            // omitted
        }
        
    }

 .. list-table::
    :header-rows: 1
    :widths: 10 90

    * - 項番
      - 説明
    * - | (1)
      - | Controllerに対して、URI(サーブレットパス)をマッピングする。
        | 具体的には、\ ``@RequestMapping``\アノテーションのvalue属性に、リソースのコレクションを表すサーブレットパスを指定する。
        | 上記例では、 \ ``"/members"``\ というサーブレットパスをマッピングしている。
    * - | (2)-(4)
      - | リソー スのコレクションに対するAPI。
        | 上記例では、 \ ``"/members"``\というサーブレットパスに対するAPIとなる。
    * - | (5)-(8)
      - | 特定のリソースに対するAPI。
        | 上記例では、 \ ``"/members/{memberId}"``\というパターンのサーブレットパスに対するAPIとなる。
        | {memberId}の部分はパス変数と呼ばれ、メソッドの引数アノテーションとして\ ``@PathVariable("memberId")``\を指定することで、\ ``{memberId}``\部分に指定された値をメソッドの引数として受け取ることが出来る。
        | パス変数の詳細については、 「:ref:`controller_method_argument-pathvariable-label`」を参照されたい。

|

リソースコレクションに対するAPIの実装
^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
リソースコレクションに対するAPIの実装について、説明する。

 .. list-table::
    :header-rows: 1
    :widths: 10 65 25

    * - 項番
      - API概要
      - 使用するHTTPメソッド
    * - | (1)
      - | URIで指定されたリソースのコレクションを取得する。
      - | GET
    * - | (2)
      - | 指定されたリソースを作成しコレクションに追加する。
      - | POST
    * - | (3)
      - | URIで指定されたリソースでサポートされているHTTPメソッド(API)のリストを応答する。
      - | OPTIONS

 .. note::
 
    本ガイドラインでは説明を省略しているが、リソースの一括更新及び一括削除を行うAPIを提供する場合は、リソースコレクションに対してPUT及びDELETEメソッドの実装が必要となる。

|

リソースのコレクションを取得するAPIの実装
""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""
URIで指定されたリソースのコレクションを取得するAPIの実装例を、以下に示す。

- | 検索条件を受け取るためのJavaBeanの作成
  | リソースのコレクションを取得する際に、検索条件が必要な場合は、検索条件を受け取るためのJavaBeanの作成する。

 .. code-block:: java

    // (1)
    public class MembersSearchQuery implements Serializable {
        private static final long serialVersionUID = 1L;
    
        // (2)
        @NotEmpty
        private String name;
    
        public String getName() {
            return name;
        }
    
        public void setName(String name) {
            this.name = name;
        }
    
    }

 .. list-table::
    :header-rows: 1
    :widths: 10 90

    * - 項番
      - 説明
    * - | (1)
      - | 検索条件を受け取るためのJavaBeanを作成する
        | 検索条件が不要な場合は、JavaBeanの作成は不要である。
    * - | (2)
      - | プロパティ名は、リクエストパラメータのパラメータ名と一致させる。
        | 上記例では、\ ``"/members?name=John"``\ というリクエストの場合、JavaBeanのnameプロパティに \ ``"John"``\ が設定される。


- | Controllerの実装
  | リソースのコレクションを取得する処理を実装する。
  
 .. code-block:: java

    @RequestMapping("members")
    @Controller
    public class MembersRestController {
    
        // omitted

        @Inject
        MemberService memberSevice;
    
        @Inject
        Mapper beanMapper;
    
        @RequestMapping(method = { RequestMethod.GET, RequestMethod.HEAD }) // (3)
        @ResponseBody // (4)
        public ResponseEntity<MembersResource> getMembers(
                @Validated MembersSearchQuery query, // (5)
                Pageable pageable) {  // (6)
    
            // (7)
            MembersSearchCriteria criteria = beanMapper.map(query,
                    MembersSearchCriteria.class);
            Page<Member> page = memberSevice.searchMembers(criteria, pageable);
    
            // (8)
            MembersResource responseResource = new MembersResource();
            responseResource.setTotalCount(page.getTotalElements());
            for (Member member : page.getContent()) {
                MemberResource memberResource = beanMapper.map(member,
                        MemberResource.class);
                responseResource.addMemeber(memberResource);
            }
    
            // (9)
            return new ResponseEntity<MembersResource>(
                responseResource, HttpStatus.OK);
        }

        // omitted

    }

 .. list-table::
    :header-rows: 1
    :widths: 10 90

    * - 項番
      - 説明
    * - | (3)
      - | \ ``@RequestMapping``\アノテーションのmethod属性に、\ ``RequestMethod.GET``\と\ ``RequestMethod.HEAD``\を指定する。
        | HEADメソッドは、GETメソッドと同じ処理を行いヘッダ情報のみレスポンスする必要があるため、\ ``@RequestMapping``\アノテーションのmethod属性に、``RequestMethod.HEAD``\を指定している。
        | レスポンスBODYを空にする処理は、Servlet APIの標準機能で行われるため、Controllerの処理としてはGETメソッドと同じ処理を行えばよい。
    * - | (4)
      - | メソッドアノテーションとして、\ ``@org.springframework.web.bind.annotation.ResponseBody``\アノテーションを付与する。
        | このアノテーションを付与することで、返却したResourceオブジェクトがJSONやXML形式にmarshalされ、レスポンスBODYに設定される。
    * - | (5)
      - | 検索条件を受け取るためのJavaBeanを引数に指定する。
        | 入力チェックが必要な場合は、 \ ``@Validated``\アノテーションを付与する。入力チェックのエラーハンドリングについては、「 xxxxxx 」を参照されたい。
        | 入力チェックの詳細については、「 :doc:`Validation` 」を参照されたい。
    * - | (6)
      - | ページネーション検索が必要な場合は、\ ``org.springframework.data.domain.Pageable``\を引数に指定する。
        | ページネーション検索の詳細については、「:doc:`Pagination`」を参照されたい。
    * - | (7)
      - | Serviceのメソッドを呼び出し、条件に一致するリソースの情報(Entityなど)を取得する。
    * - | (8)
      - | 条件に一致したリソースの情報をもとに、Web上に公開する情報を保持するResourceオブジェクトを生成する。
        | **Resourceオブジェクトを生成するためのコード量が多くなる場合は、HelperクラスにResourceオブジェクトを生成するためのメソッドを作成することを推奨する。**
    * - | (9)
      - | \ ``org.springframework.http.ResponseEntity``\ オブジェクトを返却する。
        | ステータスコードには200(OK)を設定する。


.. todo::

    ページネーションに必要なパラメータの受け取りは、Pageableでいいのかな～。要検討・・。
    
|

リソースをコレクションに追加するAPIの実装
""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""
指定されたリソースを作成し、リソースのコレクションに追加するAPIの実装例を、以下に示す。

- プロパティファイル

 .. code-block:: properties

    # (1)
    baseUri=http://example.com

 .. list-table::
    :header-rows: 1
    :widths: 10 90

    * - 項番
      - 説明
    * - | (1)
      - | URIのベース部分は環境依存値となるのでプロパティファイルに定義する。
        | (8)の処理にて、レスポンスのLocationヘッダに設定するURIを生成する際に使用する。

- Controller

 .. code-block:: java

    @RequestMapping("members")
    @Controller
    public class MembersRestController {
    
        // omitted

        // (2)
        @Value("${baseUri}/members/{member}")
        String uriTemplateText;

        // omitted

        @RequestMapping(method = RequestMethod.POST)  // (3)
        @ResponseBody
        public ResponseEntity<MemberResource> createMember(
                @RequestBody @Validated({ Default.class, MemberCreating.class }) 
                MemberResource requestedResource) { // (4)
    
            // (5)
            Member inputtedMember = beanMapper.map(requestedResource, Member.class);
            Member createdMember = memberSevice.createMember(inputtedMember);
    
            MemberResource responseResource = beanMapper.map(createdMember,
                    MemberResource.class);

            // (6)
            HttpHeaders responseHeaders = new HttpHeaders();
            UriTemplate uriTemplate = new UriTemplate(uriTemplateText);
            responseHeaders.setLocation(uriTemplate
                    .expand(responseResource.getMemberId()));
    
            // (7)
            return new ResponseEntity<MemberResource>(
                responseResource, responseHeaders, HttpStatus.CREATED);
        }

        // omitted

    }

 .. list-table::
    :header-rows: 1
    :widths: 10 90

    * - 項番
      - 説明
    * - | (2)
      - | URIのテンプレート文字列を取得する。(8)の処理にて、作成したリソースのURIを生成する際に使用する。
        | **URIのベース部分は環境依存値となるので、必ずプロパティファイルから取得すること。**
        | ``@Value("${baseUri}/members/{member}")``\としているので、\ ``uriTemplateText``\には\ ``"http://example.com/members/{member}"``\が設定される。
    * - | (3)
      - | \ ``@RequestMapping``\アノテーションのmethod属性に、\ ``RequestMethod.POST``\を指定する。
    * - | (4)
      - | 新規に作成するリソースの情報を受け取るためのJavaBean(Resourceクラス)を引数に指定する。
        | ``@org.springframework.web.bind.annotation.RequestBody``\アノテーションを付与する。
        | \ ``@RequestBody``\アノテーションを付与することで、リクエストBodyに設定されているJSONやXML形式のデータがResourceオブジェクトにunmarshalされる。
        |
        | 入力チェックを有効化するために、\ ``@Validated``\アノテーションを付与する。入力チェックのエラーハンドリングについては、「 xxxxxx 」を参照されたい。
        | 入力チェックの詳細については、「 :doc:`Validation` 」を参照されたい。
    * - | (5)
      - | Serviceのメソッドを呼び出し、新規にリソースを作成する。
    * - | (6)
      - | **作成したリソースのURIを、レスポンスのLocationヘッダに設定する。**
        | 上記例では、\ ``responseResource.getMemberId()``\の返却値が\ ``"M12345"``\だった場合、 \ ``"http://example.com/members/M12345"``\がURIとなる。
    * - | (7)
      - | \ ``ResponseEntity``\ オブジェクトを返却する。
        | ステータスコードには\ **201(CREATED)**\を設定する。

|

サポートされているHTTPメソッド(API)のリストを応答するAPIの実装
""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""
URIで指定されたリソースでサポートされているHTTPメソッド(API)のリストを応答するAPIの実装例を、以下に示す。

- Controller

 .. code-block:: java

    @RequestMapping("members")
    @Controller
    public class MembersRestController {
    
        // omitted

        @RequestMapping(method = RequestMethod.OPTIONS) // (1)
        @ResponseBody
        public ResponseEntity<Void> optionsMembers() { // (2)

            // (3)
            HttpHeaders responseHeaders = new HttpHeaders();
            Set<HttpMethod> allowMethods = new LinkedHashSet<HttpMethod>();
            allowMethods.add(HttpMethod.GET);
            allowMethods.add(HttpMethod.HEAD);
            allowMethods.add(HttpMethod.POST);
            allowMethods.add(HttpMethod.OPTIONS);
            responseHeaders.setAllow(allowMethods);

            // (4)
            return new ResponseEntity<Void>(responseHeaders, HttpStatus.OK);
        }

    }

 .. list-table::
    :header-rows: 1
    :widths: 10 90

    * - 項番
      - 説明
    * - | (1)
      - | \ ``@RequestMapping``\アノテーションのmethod属性に、\ ``RequestMethod.OPTIONS``\を指定する。
    * - | (2)
      - | レスポンスBODYを空で応答するため、返り値の型を \ ``ResponseEntity<Void>``\ にする。
    * - | (3)
      - | **URIで指定されたリソースでサポートされているHTTPメソッドを、Allowヘッダに設定する。**
        | 上記例では、 \ ``"/members"``\というサーブレットパスでサポートされているHTTPメソッドの一覧を設定している。
    * - | (4)
      - | \ ``org.springframework.http.ResponseEntity``\ オブジェクトを返却する。
        | ステータスコードには200(OK)を設定する。

|

特定のリソースに対するAPIの実装
^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
特定のリソースに対するAPIの実装について、説明する。

 .. list-table::
    :header-rows: 1
    :widths: 10 65 25

    * - 項番
      - API概要
      - 使用するHTTPメソッド
    * - | (1)
      - | URIで指定されたリソースを取得する。
      - | GET
    * - | (2)
      - | URIで指定されたリソースを更新する。
      - | PUT
    * - | (3)
      - | URIで指定されたリソースを削除する。
      - | DELETE
    * - | (4)
      - | URIで指定されたリソースでサポートされているHTTPメソッド(API)のリストを応答する。
      - | OPTIONS

|

指定されたリソースを取得するAPIの実装
""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""
URIで指定されたリソースを取得するAPIの実装例を、以下に示す。

- Controller

 .. code-block:: java

    @RequestMapping("members")
    @Controller
    public class MembersRestController {

        // omitted

        @RequestMapping(value = "{memberId}", 
            method = { RequestMethod.GET, RequestMethod.HEAD }) // (1)
        @ResponseBody
        public ResponseEntity<MemberResource> getMember(
                @PathVariable("memberId") String memberId) { // (2)
    
            // (3)
            Member member = memberSevice.getMember(memberId);
    
            MemberResource responseResource = beanMapper.map(member,
                    MemberResource.class);
    
            // (4)
            return new ResponseEntity<MemberResource>(
                responseResource, HttpStatus.OK);
        }

        // omitted

    }

 .. list-table::
    :header-rows: 1
    :widths: 10 90

    * - 項番
      - 説明
    * - | (1)
      - | \ ``@RequestMapping``\アノテーションのvalue属性にパス変数(上記例では\ ``{memberId}``\)を、method属性に\ ``RequestMethod.GET``\と\ ``RequestMethod.HEAD``\を指定する。
    * - | (2)
      - | リソースを一意に識別するためのIDを、パス変数から取得する。
        | \ ``@PathVariable("memberId")``\を指定することで、パス変数(\ ``{memberId}``\)に指定された値をメソッドの引数として受け取ることが出来る。
        | パス変数の詳細については、 「:ref:`controller_method_argument-pathvariable-label`」を参照されたい。
    * - | (3)
      - | Serviceのメソッドを呼び出し、指定したIDに一致するリソースの情報(Entityなど)を取得する。
    * - | (4)
      - | \ ``ResponseEntity``\ オブジェクトを返却する。
        | ステータスコードには200(OK)を設定する。

|

指定されたリソースを更新するAPIの実装
""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""
URIで指定されたリソースを更新するAPIの実装例を、以下に示す。

- Controller

 .. code-block:: java

    @RequestMapping("members")
    @Controller
    public class MembersRestController {

        // omitted

        @RequestMapping(value = "{memberId}", method = RequestMethod.PUT) // (1)
        @ResponseBody
        public ResponseEntity<MemberResource> updateMember(
                @PathVariable("memberId") String memberId,
                @RequestBody @RequestBody @Validated({ Default.class, MemberUpdating.class }) 
                MemberResource requestedResource) { // (2)
    
            // (3)
            Member inputtedMember = beanMapper.map(
                requestedResource, Member.class);
            Member updatedMember = memberSevice.updateMember(
                memberId, inputtedMember);
    
            MemberResource responseResource = beanMapper.map(updatedMember,
                    MemberResource.class);
    
            // (4)
            return new ResponseEntity<MemberResource>(
                responseResource, HttpStatus.OK);
        }

        // omitted
        
    }

 .. list-table::
    :header-rows: 1
    :widths: 10 90

    * - 項番
      - 説明
    * - | (1)
      - | \ ``@RequestMapping``\アノテーションのvalue属性にパス変数(上記例では\ ``{memberId}``\)を、method属性に\ ``RequestMethod.PUT``\を指定する。
    * - | (2)
      - | リソースの更新内容を受け取るためのJavaBean(Resourceクラス)を引数に指定する。
        | \ ``@RequestBody``\アノテーションを付与することで、リクエストBodyに設定されているJSONやXML形式のデータがResourceオブジェクトにunmarshalされる。
        |
        | 入力チェックを有効化するために、\ ``@Validated``\アノテーションを付与する。
    * - | (3)
      - | Serviceのメソッドを呼び出し、指定したIDに一致するリソースの情報(Entityなど)を更新する。
    * - | (4)
      - | \ ``ResponseEntity``\ オブジェクトを返却する。
        | ステータスコードには200(OK)を設定する。

|

指定されたリソースを削除するAPIの実装
""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""
URIで指定されたリソースを削除するAPIの実装例を、以下に示す。

- Controller

 .. code-block:: java

    @RequestMapping("members")
    @Controller
    public class MembersRestController {

        // omitted

        @RequestMapping(value = "{memberId}", method = RequestMethod.DELETE) // (1)
        @ResponseBody // (2)
        public ResponseEntity<Void> deleteMember(
                @PathVariable("memberId") String memberId) {
    
            // (2)
            memberSevice.deleteMember(memberId);
            
            // (3)
            return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
        }

        // omitted
        
    }

 .. list-table::
    :header-rows: 1
    :widths: 10 90

    * - 項番
      - 説明
    * - | (1)
      - | \ ``@RequestMapping``\アノテーションのvalue属性にパス変数(上記例では\ ``{memberId}``\)を、method属性に\ ``RequestMethod.DELETE``\を指定する。
    * - | (2)
      - | Serviceのメソッドを呼び出し、指定したIDに一致するリソースの情報(Entityなど)を削除する。
    * - | (3)
      - | \ ``ResponseEntity``\ オブジェクトを返却する。
        | 上記例では、レスポンスBODYが空になるので、ステータスコードには\ **204(NO_CONTENT)**\を設定している。

 .. note::
 
    削除したリソースの情報をレスポンスBODYに設定する場合は、ステータスコードには200(OK)を設定する。

|

サポートされているHTTPメソッド(API)のリストを応答するAPIの実装
""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""
URIで指定されたリソースでサポートされているHTTPメソッド(API)のリストを応答するAPIの実装例を、以下に示す。

- Controller

 .. code-block:: java

    @RequestMapping("members")
    @Controller
    public class MembersRestController {

        // omitted

        @RequestMapping(value = "{memberId}", method = RequestMethod.OPTIONS)
        @ResponseBody
        public ResponseEntity<Void> optionsMember(
                @PathVariable("memberId") String memberId) {
    
            // (1)
            HttpHeaders responseHeaders = new HttpHeaders();
            Set<HttpMethod> allowMethods = new LinkedHashSet<HttpMethod>();
            allowMethods.add(HttpMethod.GET);
            allowMethods.add(HttpMethod.HEAD);
            allowMethods.add(HttpMethod.PUT);
            allowMethods.add(HttpMethod.DELETE);
            allowMethods.add(HttpMethod.OPTIONS);
            responseHeaders.setAllow(allowMethods);
    
            return new ResponseEntity<Void>(responseHeaders, HttpStatus.OK);
        }
    
        // omitted
        
    }


 .. list-table::
    :header-rows: 1
    :widths: 10 90

    * - 項番
      - 説明
    * - | (1)
      - | **URIで指定されたリソースでサポートされているHTTPメソッドを、Allowヘッダに設定する。**
        | 上記例では、 \ ``"/members/{memberId}"``\というパターンのサーブレットパスでサポートされているHTTPメソッドの一覧を設定している。

|

エラーのハンドリング
^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
RESTful Web Serviceで発生したエラーのハンドリング方法について説明する。

| Spring MVCから提供されている\ ``org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler``\を継承したクラスを作成し、\ ``@ControllerAdvice``\アノテーションを付与する方法でハンドリングする事を推奨する。
| \ ``ResponseEntityExceptionHandler``\は、Spring MVCのフレームワーク内で発生する例外を\ ``@ExceptionHandler``\アノテーションを使ってハンドリングするメソッドが実装されており、ハンドリングされる例外と設定されるHTTPステータスコードは、\ ``DefaultHandlerExceptionResolver``\と同様である。
| ハンドリングされる例外と設定されるHTTPステータスコードについては、「\ :ref:`exception-handling-appendix-defaulthandlerexceptionresolver-label`\」を参照されたい。
| また、デフォルトの実装ではレスポンスBodyは空で返却されるが、レスポンスBodyにエラー情報を出力するように拡張する事ができる様になっている。

エラーハンドリング用のクラスの作成
""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""
以下に、エラーハンドリングを行うクラスの作成例を示す。

 .. code-block:: java

    // (1)
    @ControllerAdvice
    public class RestGlobalExceptionHandler extends ResponseEntityExceptionHandler {
        // omitted
    }

 .. list-table::
    :header-rows: 1
    :widths: 10 90

    * - 項番
      - 説明
    * - | (1)
      - | Spring MVCから提供されている\ ``ResponseEntityExceptionHandler``\を継承したクラスを作成し、\ ``@ControllerAdvice``\アノテーションを付与する。

|

レスポンスBodyにエラー情報を出力する方法
""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""
以下に、レスポンスBodyにエラー情報を出力するための実装例を示す。

エラー情報を保持するJavaBeanを作成する。

 .. code-block:: java

    // (1)
    public class RestError implements Serializable {
    
        private static final long serialVersionUID = 1L;
    
        private final String code;
        private final String message;
        @JsonSerialize(include = Inclusion.NON_EMPTY)
        private final List<RestErrorDetail> details = new ArrayList<>();
    
        public RestError(String code, String message) {
            this.code = code;
            this.message = message;
        }
    
        // getter/setter omitted
    
    }
    
 .. list-table::
    :header-rows: 1
    :widths: 10 90

    * - 項番
      - 説明
    * - | (1)
      - | エラー情報を保持するためのクラスを作成する。
        | 上記例では、エラーコード、エラーメッセージ、エラーの詳細情報のリストを保持するクラスとなっている。

 .. code-block:: java

    // (2)
    public class RestErrorDetail implements Serializable {
    
        private static final long serialVersionUID = 1L;
    
        private final String code;
        private final String message;
    
        public RestErrorDetail(String code, String message) {
            this.code = code;
            this.message = message;
        }
    
        // getter/setter omitted
    
    }

 .. list-table::
    :header-rows: 1
    :widths: 10 90

    * - 項番
      - 説明
    * - | (2)
      - | 必要に応じてエラーの詳細情報を保持するためのクラスを作成する。
        | 入力チェックでエラーが発生した場合、エラー原因が複数存在する場合があるため、すべてのエラー情報をクライアントに返却する事が求められるケースがある。
        | そのような場合は、本クラスを用意する必要がある。

|

エラー情報を保持するJavaBeanを生成するためのクラスを作成する。

 .. code-block:: java

    // (3)
    public class RestErrorCreator {
    
        @Inject
        MessageSource messageSource;
    
        public RestError createRestError(String code,
                String defaultMessage,Locale locale,
                Object... arguments) {
            String localizedMessage = messageSource.getMessage(code, arguments,
                    defaultMessage, locale);
            return new RestError(code, localizedMessage);
        }

        // omitted

    }

 .. list-table::
    :header-rows: 1
    :widths: 10 90

    * - 項番
      - 説明
    * - | (3)
      - | 必要に応じて、エラー情報を生成するためのメソッドを提供するクラスを作成する。
        | このクラスの作成は必須ではないが、役割を明確に分担するために作成する事を推奨する。

|

エラーハンドリングを行うクラスのメソッドを拡張し、レスポンスBodyにエラー情報を出力するための実装を行う。

 .. code-block:: java

    @ControllerAdvice
    public class RestGlobalExceptionHandler extends ResponseEntityExceptionHandler {

        @Inject
        RestErrorCreator restErrorCreator;
    
        @Inject
        ExceptionCodeResolver exceptionCodeResolver;

        // (4)
        @Override
        protected ResponseEntity<Object> handleExceptionInternal(
                Exception ex, Object body, HttpHeaders headers,
                HttpStatus status, WebRequest request) {
            final Object errorBody;
            if (body == null) {
                // (5)
                String code = exceptionCodeResolver.resolveExceptionCode(ex);
                errorBody = restErrorCreator.createRestError(code,
                    ex.getLocalizedMessage(), request.getLocale());
            } else {
                errorBody = body;
            }
            // (6)
            return new ResponseEntity<Object>(errorBody, headers, status);
        }
        
        // omitted
    
    }


 .. list-table::
    :header-rows: 1
    :widths: 10 90

    * - 項番
      - 説明
    * - | (4)
      - | handleExceptionInternalメソッドをオーバライドする。
    * - | (5)
      - | レスポンスBodyを生成するためのエラー情報を保持するJavaBeanオブジェクトを生成する。
        | 上記例では、エラーコードは共通ライブラリから提供している\ ``ExceptionCodeResolver``\を使用して、例外クラスのクラス名とエラーコードをマッピングする事で解決している。
        | \ ``ExceptionCodeResolver``\については、「:doc:`ExceptionHandling`」を参照されたい。
    * - | (6)
      - | レスポンス用のHTTP EntityのBody部分に、(5)で生成したエラー情報を設定し返却する。

.. todo::

    出力されるJSONの例があった方がいいな・・・。


入力エラーのハンドリング
""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""

.. todo::

    入力エラーのエラーハンドリング方法について説明。

|

業務エラーのハンドリング
""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""
.. todo::

    業務エラーのエラーハンドリング方法について説明。

|

リソース未検出エラーのハンドリング
""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""
.. todo::

    リソース未検出エラーのエラーハンドリング方法について説明。

|

.. _RESTHowToExtend:

How to extend
--------------------------------------------------------------------------------


.. _RESTAppendix:

Appendix
--------------------------------------------------------------------------------

.. _RESTAppendixEnabledHttpMessageConverter:

<mvc:annotation-driven>指定時にデフォルトで有効化されるHttpMessageConverterについて
^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^

``<mvc:annotation-driven>`` 指定時にデフォルトで有効化される ``HttpMessageConverter`` は以下の通りである。

 .. list-table::
   :header-rows: 1
   :widths: 10 30 15 45

   * - | 項番
     - | クラス名
     - | 対象
       | フォーマット
     - | 説明
   * - 1.
     - | org.springframework.http.converter.json.
       | MappingJacksonHttpMessageConverter
     - | JSON
     - | リクエストBody又はレスポンスBodyとしてJSONを扱うための ``HttpMessageConverter`` 。
       | ブランクプロジェクトでは、 `Jackson 1.x <http://jackson.codehaus.org/>`_ 系を同封しているため、デフォルトの状態で使用することができる。
   * - 2.
     - | org.springframework.http.converter.xml.
       | Jaxb2RootElementHttpMessageConverter
     - | XML
     - | リクエストBody又はレスポンスBodyとしてXMLを扱うための ``HttpMessageConverter`` 。
       | JavaSE6からJAXB2.0が標準で同封されているため、デフォルトの状態で使用することができる。
       | \ **ただし、このクラスはXXE(XML External Entity) Injection対策が行われていないため、Spring-oxmから提供されているクラスを使用すること。**\
       | XXE Injection対策については、「:ref:`RESTAppendixEnabledXXEInjectProtection`」を参照されたい。

 .. warning:: **XXE(XML External Entity) Injection 対策について**

    執筆時点の適用バージョン(3.2.4)では、``Jaxb2RootElementHttpMessageConverter`` は `XXE(XML External Entity) Injection <https://www.owasp.org/index.php/XML_External_Entity_(XXE)_Processing>`_ 対策が行われていないため、使用しないこと。
    ``Jaxb2RootElementHttpMessageConverter`` は、Controllerの引数として以下のクラスを指定した際に実行されるため、 これらのクラスをControllerの引数として宣言する場合は、必ず XXE Injection対策を行う必要がある。
    XXE Injection対策については、「:ref:`RESTAppendixEnabledXXEInjectProtection`」を参照されたい。

     * ``@javax.xml.bind.annotation.XmlRootElement`` アノテーションが付与されたJavaBean
     * ``@javax.xml.bind.annotation.XmlType`` アノテーションが付与されたJavaBean

    また、 ``<mvc:annotation-driven>`` 指定時に有効化されるクラスとして ``org.springframework.http.converter.xml.SourceHttpMessageConverter`` があるが、このクラスも XXE(XML External Entity) Injection対策が行われていないので使用しないこと。

    ``SourceHttpMessageConverter`` は、Controllerの引数として以下のクラスを指定した際に実行されるため、 これらのクラスをControllerの引数として宣言することを原則禁止とする。
    また、下記にあげるクラスはLow LevelのXML APIなので、アーキテクチャの観点からも使用しない方がよい。

     * ``javax.xml.transform.dom.DOMSource``
     * ``javax.xml.transform.sax.SAXSource``
     * ``javax.xml.transform.stream.StreamSource``
     * ``javax.xml.transform.Source``

|

.. _RESTAppendixEnabledXXEInjectProtection:

XXE Injection対策の有効化
^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^

| **RESTfull Web ServiceでXML形式のデータを扱う場合は、XXE(XML External Entity) Injection対策として、以下の設定を追加すること。**
| JSON形式のデータを扱う場合は、本設定は不要である。

| TERASOLUNA Global Framework 1.0.0 を使っている場合は、XXE Injection対策を行うためにSpring-oxmを依存するアーティファクトとして追加する必要がある。
| TERASOLUNA Global Framework 1.0.1 以降のバージョンではSpring-oxmを内包しているため、本設定は不要である。

- :file:`pom.xml`

 .. code-block:: xml

    <!-- omitted -->

    <!-- (1) -->
    <dependency>
        <groupId>org.springframework</groupId>
        <artifactId>spring-oxm</artifactId>
        <version>${org.springframework-version}</version> <!-- (2) -->
    </dependency>

    <!-- omitted -->

 .. list-table::
   :header-rows: 1
   :widths: 10 90

   * - | 項番
     - | 説明
   * - | (1)
     - | Spring-oxm を依存アーティファクトとして追加する。
   * - | (2)
     - | Springのバージョンは、terasoluna-gfw-parent の :file:`pom.xml` に定義されているSpringのバージョン番号を管理するためのプレースフォルダ(${org.springframework-version})から取得すること。

|

XML用の\ ``HttpMessageConverter``\として、Spring-oxmから提供されている\ ``MarshallingHttpMessageConverter``\を使用する。

- :file:`spring-mvc.xml`

 .. code-block:: xml

    <!-- (1) -->
    <bean id="xmlMarshaller" class="org.springframework.oxm.jaxb.Jaxb2Marshaller">
        <property name="packagesToScan" value="com.examples.app" /> <!-- (2) -->
    </bean>

    <!-- ... -->

    <mvc:annotation-driven>

        <mvc:message-converters>
            <!-- (3) -->
            <bean class="org.springframework.http.converter.xml.MarshallingHttpMessageConverter">
                <property name="marshaller" ref="xmlMarshaller" /> <!-- (4) -->
                <property name="unmarshaller" ref="xmlMarshaller" /> <!-- (5) -->
            </bean>
        </mvc:message-converters>

        <!-- ... -->

    </mvc:annotation-driven>

    <!-- ... -->

 .. list-table::
   :header-rows: 1
   :widths: 10 90

   * - | 項番
     - | 説明
   * - | (1)
     - | ``Jaxb2Marshaller`` のbean定義を行う。
       | ``Jaxb2Marshaller`` はデフォルトの状態で XXE Injection対策が行われている。
   * - | (2)
     - | ``packagesToScan`` プロパティに JAXB用のJavaBean( ``javax.xml.bind.annotation.XmlRootElement`` アノテーションなどが付与されているJavaBean)が格納されているパッケージ名を指定する。
       | 指定したパッケージ配下に格納されているJAXB用のJavaBeanがスキャンされ、marshal、unmarshal対象のJavaBeanとして登録される。
       | ``<context:component-scan>`` の base-package属性と同じ仕組みでスキャンされる。
   * - | (3)
     - | ``<mvc:annotation-driven>`` の子要素である ``<mvc:message-converters>`` 要素に、 ``MarshallingHttpMessageConverter`` のbean定義を追加する。
   * - | (4)
     - | ``marshaller`` プロパティに (1)で定義した ``Jaxb2Marshaller`` のbeanを指定する。
   * - | (5)
     - | ``unmarshaller`` プロパティに (1)で定義した ``Jaxb2Marshaller`` のbeanを指定する。

|

.. _RESTSettingsOfDeployInSameWarFileRestAndClientApplication:

RESTful Web Serviceとクライアントアプリケーションを同じWebアプリケーションとして動かす際の設定
^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^

.. _RESTAppendixDivideDispatcherServlet:

RESTful Web Service用の\ ``DispatcherServlet``\を設ける方法
""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""

| RESTful Web Service用のリクエストを受ける\ ``DispatcherServlet``\と、クライアントアプリケーション用のリクエストを受け取る\ ``DispatcherServlet``\を分割する方法について、以下に説明する。

- :file:`web.xml`

 .. code-block:: xml
    :emphasize-lines: 3,17-19,19-20,24-25,33-37

    <!-- omitted -->

    <!-- (1) -->
    <servlet>
        <servlet-name>appServlet</servlet-name>
        <servlet-class>org.springframework.web.servlet.DispatcherServlet</servlet-class>
        <init-param>
            <param-name>contextConfigLocation</param-name>
            <param-value>classpath*:META-INF/spring/spring-mvc.xml</param-value>
        </init-param>
        <load-on-startup>1</load-on-startup>
    </servlet>
    <servlet-mapping>
        <servlet-name>appServlet</servlet-name>
        <url-pattern>/</url-pattern>
    </servlet-mapping>

    <!-- (2) -->
    <servlet>
        <servlet-name>restAppServlet</servlet-name>
        <servlet-class>org.springframework.web.servlet.DispatcherServlet</servlet-class>
        <init-param>
            <param-name>contextConfigLocation</param-name>
            <!-- (3) -->
            <param-value>classpath*:META-INF/spring/spring-mvc-rest.xml</param-value>
        </init-param>
        <init-param>
            <param-name>dispatchOptionsRequest</param-name>
            <param-value>true</param-value>
        </init-param>
        <load-on-startup>2</load-on-startup>
    </servlet>
    <!-- (4) -->
    <servlet-mapping>
        <servlet-name>restAppServlet</servlet-name>
        <url-pattern>/rest/*</url-pattern>
    </servlet-mapping>

    <!-- omitted -->

 .. list-table::
    :header-rows: 1
    :widths: 10 90

    * - 項番
      - 説明
    * - | (1)
      - | クライアントアプリケーション用のリクエストを受け取る\ ``DispatcherServlet``\とリクエストマッピング。
    * - | (2)
      - | RESTful Web Service用のリクエストを受けるServlet(\ ``DispatcherServlet``\)を追加する。
        | \ ``<servlet-name>``\要素に、サーブレットを一意に識別するための名前を指定する。
        | 上記例では、サーブレット名として\ ``"restAppServlet"``\を指定している。
    * - | (3)
      - | RESTful Web Service用の\ ``DispatcherServlet``\を構築する際に使用するSpring MVCのbean定義ファイルを指定する。
        | 上記例では、Spring MVCのbean定義ファイルとして、クラスパス上にある\ :file:`META-INF/spring/spring-mvc-rest.xml`\を指定している。
    * - | (4)
      - | RESTful Web Service用の\ ``DispatcherServlet``\へマッピングするサーブレットパスのパターンの指定を行う。
        | 上記例では、\ ``"/rest"``\又は\ ``"/rest/"``\配下のサーブレットパスをRESTful Web Service用の\ ``DispatcherServlet``\にマッピングしている。
        | 具体的には、
        |   \ ``"/rest"``\
        |   \ ``"/rest/"``\
        |   \ ``"/rest/members"``\
        |   \ ``"/rest/members/xxxxx"``\
        | といったサーブレットパスが、RESTful Web Service用の\ ``DispatcherServlet``\(\ ``"restAppServlet"``\)にマッピングされる。

 .. tip:: **サーブレットを分割した際の@RequestMappingアノテーションのvalue属性に指定する値について**

    \ ``@RequestMapping``\アノテーションのvalue属性に指定する値は、\ ``<url-pattern>``\要素で指定したワイルドカード(\ ``*``\)の部分の値を指定する。

    例えば、\ ``@RequestMapping(value = "members")``\と指定した場合、\ ``"/rest/members"``\というサーブレットパスに対する処理を行うメソッドとしてデプロイされる。
    そのため、\ ``@RequestMapping``\アノテーションのvalue属性には、分割したサーブレットへマッピングするためパス(\ ``"rest"``\)を指定する必要はない。

 .. todo::
 
    具体例を図を使って記載する。

 
|



.. _RESTTutorial:

RESTfull Web Serviceチュートリアル
--------------------------------------------------------------------------------

はじめに
^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^

作成するアプリケーションの説明
^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^

環境構築
^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^

アプリケーションの作成
^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^

おわりに
^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^

