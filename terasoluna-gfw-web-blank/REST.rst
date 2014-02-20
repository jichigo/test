RESTfull Web Service
================================================================================

.. contents:: 目次
   :depth: 3
   :local:

Overview
--------------------------------------------------------------------------------
本説では、RESTfull Web Serviceの基本的なアーキテクチャについて説明する。

RESTfull Web Serviceとは
^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
まずRESTとは、「\ **RE**\presentational \ **S**\tate \ **T**\ransfer」の略であり、
クライアント/サーバ間でデータをやりとりするアプリケーションを構築するための\ **アーキテクチャスタイル**\の一つである。

| RESTには、いくつかの重要な原則があり、これらの原則に従っているもの(システムなど)は \ **RESTful**\ と表現される。
| つまり、「RESTfull Web Service」とは、RESTの原則に従って構築されているWeb Serviceという事になる。

.. todo::

    RESTfull Web Service利用したアプリケーションの構成例の説明があった方がいいかな？
    
    * Client to Server(Web Browser <=> \ **RESTfull Web Service**\)
    * Server to Server(Web Browser <=> Web Application <=> \ **RESTfull Web Service**\)

|

RESTfull Web Serviceを構築するためのアーキテクチャについて
^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
| RESTfull Web Serviceを構築するためのアーキテクチャとして、ROAが存在する。
| ROAは、「\ **R**\esource \ **O**\riented \ **A**\rchitecture」の略であり、\ **RESTのアーキテクチャスタイル(原則)に従ったWeb Serviceを構築するための具体的なアーキテクチャ**\を定義している。
| RESTfull Web Serviceを作る際は、まずROAのアーキテクチャの理解を深めてほしい。

|

Web上のリソースとして公開
""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""
| **システム上で管理する情報をクライアントに提供する手段として、Web上のリソースとして公開する。**
| これは、HTTPプロトコルを使ってアクセスできるようにする事を意味している。

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
| **クライアントに公開するリソースには、Web上のリソースとして一意に識別できるURI(Universal Resource Identifier)を割り当てる。**
| 実際は、URIのサブセットであるURL(Uniform Resource Locator)を使用してリソースを一意に識別することになる。

| ROAでは、URIを使用してWeb上のリソースにアクセスできる状態になっていることを「アドレス可能性」と呼んでいる。
| これは同じURIを使用すれば、どこからでも同じリソースにアクセスできる状態になっている事を意味している。

| RESTfull Web Serviceに割り当てるURIは、\ **リソースの種類を表す名詞とリソースを一意に識別するための値(IDなど)の組み合わせとする。**\
| 例えば、ショッピングサイトを提供するWebシステムで扱う商品情報のURIは、以下のようになる。

* | \ `http://example.com/v1.0/items`\
  | 「**items**」の部分がリソースの種類を表す名詞となり、リソースの数が複数になる場合は、複数系の名詞を使用する。
  | 上記例では、商品情報である事を表す名詞の複数系を指定しており、商品情報を一括で操作する際に使用するURIとなる。

* | \ `http://example.com/v1.0/items/I312-535-01216`\
  | 「**I312-535-01216**」の部分がリソースを識別するための値となり、リソース毎に異なる値となる。
  | 上記例では、商品情報を一意に識別するための値として商品IDを指定しており、特定の商品情報を操作する際に使用するURIとなる。

 .. warning::
 
    RESTfull Web Serviceに割り当てるURIには、下記で示すような\ **操作を表す動詞を含んではいけない。**\
    
    * \ `http://example.com/v1.0/items?get&itemId=I312-535-01216`\
    * \ `http://example.com/v1.0/items?delete&itemId=I312-535-01216`\
    
    上記例では、 URIの中に\ **get**\や\ **delete**\という動詞を含んでいるため、RESTfull Web Serviceに割り当てるURIとして適切ではない。
    
    RESTfull Web Serviceでは、\ **リソースに対する操作はHTTPメソッド(GET,POST,PUT,DELETE)を使用して表現される。**\

|

HTTPメソッドによるリソースの操作
""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""
| **リソースに対する操作は、HTTPメソッド(GET,POST,PUT,DELETE)を使い分けることで実現する。**

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
      - | リソースを取得する。
      - | 安全性、べき等性。
    * - | (2)
      - | POST
      - | リソースの作成する。
      - | 作成したリソースのURIの割り当てはサーバが行い、割り当てたURIはレスポンスのLocationヘッダに設定してクライアントに返却する。
    * - | (4)
      - | PUT
      - | リソースを作成又は更新する。
      - | べき等性。
    * - | (5)
      - | PATCH
      - | リソースを差分更新する。
      - | べき等性。
    * - | (6)
      - | DELETE
      - | リソースを削除する。
      - | べき等性。
    * - | (7)
      - | HEAD
      - | リソースのメタ情報を取得する。
        | GETと同じ処理を行いヘッダのみ応答する。
      - | 安全性、べき等性。
    * - | (8)
      - | OPTIONS
      - | リソースに対して使用できるHTTPメソッドの取得する。
        | リソースを操作するためのメソッドではない。
      - | 安全性、べき等性。

 .. note:: **安全性の保証とは**
 
    ある数字に1を何回掛けても、数字がかわらない事(10に1を何回掛けても結果は10のままである事)を保証する。
    
    これは、同じ操作を何回行ってもリソースの状態が変わらない事を保証する事である。

 .. note:: **べき等性の保証とは**
 
    数字に0を何回掛けても0になる事(10に0を1回掛けても何回掛けても結果は共に0になる事)を保証する。
    
    これは、一度操作を行えば、その後で同じ操作を何回行ってもリソースの状態が変わらない事を保証する事である。
    ただし、別のクライアントが同じリソースの状態を変更している場合は、べき等性を保障する必要はなく、事前条件に対するエラーとして扱ってもよい。
    

 .. tip:: **クライアントがリソースに割り当てるURIを指定してリソースを作成する場合**
 
    リソースを作成する際に、クライアントによってリソースに割り当てるURIを指定する場合は、\ **作成するリソースに割り当てるURIに対して、PUTメソッドを呼び出すことで実現する。**\

    指定されたURIにリソースが存在しない場合はリソースを作成し、既にリソースが存在する場合はリソースの状態を更新するのが一般的な動作となる。

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
**リソースのフォーマットは、JSON又はXMLなどのデータ構造を表示する事ができるフォーマットを使用する。**

| ただし、リソースの種類によっては、JSONやXML以外のフォーマットを使ってもよい。
| 例えば、統計情報に分類される様なリソースでは、折れ線グラフを画像フォーマット(バイナリデータ)としてリソースを公開する事も考えられる。

| リソースのフォーマットとして、複数のフォーマットをサポートする場合は、リクエストのAcceptヘッダ及びContent-TypeのMIMEタイプによって切り替えを行う。

RESTfull Web Serviceで使用される代表的なMIMEタイプを以下に示す。

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
 
    レスポンスのフォーマットについては、Acceptヘッダによる切り替え以外に、拡張子として指定する方法がある。
    Spring MVCではAcceptヘッダによる切り替えに加えて、拡張子による切り替えもサポートしている。
    
    拡張子による切り替えの方が、Acceptヘッダによる切り替える場合に比べ、より直感的なURIとなる。
    特に理由がない場合は、拡張子によって切り替えることを推奨する。

    **[拡張子で切り替える場合のURI例]**
    
    * \ `http://example.com/v1.0/items.json`\
    * \ `http://example.com/v1.0/items.xml`\
    * \ `http://example.com/v1.0/items/I312-535-01216.json`\
    * \ `http://example.com/v1.0/items/I312-535-01216.xml`\
    

|

適切なHTTPステータスコードの使用
""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""
\ **クライアントへ返却するレスポンスには、適切なHTTPステータスコードを設定する。**\

| HTTPステータスコードには、クライアントから受け取ったリクエストをサーバがどのように処理したかを示す値を設定する。
| \ **これはHTTPの仕様であり、HTTPの仕様に準拠することを推奨する。**\

 .. tip:: **HTTPの仕様について**
 
    `RFC 2616(Hypertext Transfer Protocol -- HTTP/1.1)の6.1.1 Status Code and Reason Phrase <http://tools.ietf.org/search/rfc2616#section-6.1.1>`_ を参照されたい。

|

| ブラウザにHTMLを返却するような伝統的なWebシステムでは、処理結果に関係なく\ ``"200 OK"``\を応答し、処理結果はエンティティボディ(HTML)の中で表現するという事が一般的であった。
| HTMLを返却するような伝統的なWebアプリケーションでは、処理結果を判断するのはオペレータ(人間)のため、この仕組みでも問題が発生する事はなかった。
| しかし、この仕組みでRESTfull Web Serviceを構築した場合、以下のような問題が潜在的に存在することになるため、適切なHTTPステータスコードを設定することを推奨する。

 .. list-table::
    :header-rows: 1
    :widths: 10 90

    * - 項番
      - 潜在的な問題点
    * - | (1)
      - | 処理結果(成功と失敗)のみを判断すればよい場合でも、エンティティボディを解析処理が必須になるため、無駄な処理が必要になる。
    * - | (2)
      - | エラーハンドリングを行う際に、システム独自に定義されたエラーコードを意識する事が必須になるため、クライアント側のアーキテクチャ(設計及び実装)に悪影響を与える可能性がある。
    * - | (3)
      - | クライアント側でエラー原因を解析する際に、システム独自に定義されたエラーコードの意味を理解しておく必要があるため、直感的なエラー解析の妨げになる可能性がある。

|

関連のあるリソースへのリンク
""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""
| \ **リソースの中には、指定されたリソースと関連をもつ他のリソースへのリンク(URI)を含める。**\

| ROAでは、リソース状態の表現の中に、他のリソースへのリンクを含めることを「接続性」と呼んでいる。
| これは、関連をもつリソース同士が相互にリンクを保持し、そのリンクをたどる事で関連する全てのリソースにアクセスできる事を示している。

下記に、ショッピングサイトの会員情報のリソースを例に、リソースの接続性について説明する。

 .. figure:: ./images_REST/RESTConnectivity.png
   :alt: Image of resource connectivity
   :width: 100%

 .. todo::

    * リンク項目の持ち方のベストプラクティスについては継続調査・・・。
    * どういう方針でリンクをはるのがベストプラクティスについては継続調査・・・。

 .. list-table::
    :header-rows: 1
    :widths: 10 90

    * - 項番
      - 説明
    * - | (1)
      - | 会員情報のリソースを取得(\ ``GET http://example.com/v1.0/memebers/M909-123-09123``\)を行うと、以下のJSONが返却される。

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
                "ordersUri" : "http://example.com/v1.0/memebers/M909-123-09123/orders",
                "authenticationsUri" : "http://example.com/v1.0/memebers/M909-123-09123/authentications"
            }
    
        | ハイライトした部分が、関連をもつ他のリソースへのリンク(URI)となる。
        | 上記例では、会員毎の注文履歴と認証履歴のリソースに対して接続性を保持している。
    * - | (2)
      - | 返却されたJSONに設定されているURIを使用して、注文履歴のリソースを取得(\ ``GET http://example.com/v1.0/memebers/M909-123-09123/orders``\)を行うと、以下のJSONが返却される。

         .. code-block:: json
            :emphasize-lines: 8,15,18
        
            {
                "orders" : [
                    {
                        "orderId" : "029b49d7-0efa-411b-bc5a-6570ce40ead8",
                        "orderDatetime" : "2013-12-27T20:34:50.897Z", 
                        "orderName" : "Note PC",
                        "shopName" : "Global PC Shop",
                        "orderUri" : "http://example.com/v1.0/memebers/M909-123-09123/orders/029b49d7-0efa-411b-bc5a-6570ce40ead8"
                    },
                    {
                        "orderId" : "79bf991d-d42d-4546-9265-c5d4d59a80eb",
                        "orderDatetime" : "2013-12-03T19:01:44.109Z", 
                        "orderName" : "Orange Juice 100%",
                        "shopName" : "Global Food Shop",
                        "orderUri" : "http://example.com/v1.0/memebers/M909-123-09123/orders/79bf991d-d42d-4546-9265-c5d4d59a80eb"
                    }
                ],
                "ownerMemberUri" : "http://example.com/v1.0/memebers/M909-123-09123"
            }

        | ハイライトした部分が、関連をもつ他のリソースへのリンク(URI)となる。
        | 上記例では、注文履歴のオーナの会員情報のリソース及び注文履歴のリソースに対する接続性を保持している。
    * - | (3)
      - | 注文履歴のオーナとなる会員情報のリソースを再度取得(\ ``GET http://example.com/v1.0/memebers/M909-123-09123``\)し、返却されたJSONに設定されているURIを使用して、認証履歴のリソースを取得(\ ``GET http://example.com/v1.0/memebers/M909-123-09123/authentications/``\)を行うと、以下のJSONが返却される。
        
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
                "ownerMemberUri" : "http://example.com/v1.0/memebers/M909-123-09123"
            }
        
        | ハイライトした部分が、関連をもつ他のリソースへのリンク(URI)となる。
        | 上記例では、認証履歴のオーナとなる会員情報のリソースに対して接続性を保持している。

|

RESTfull Web Serviceの実践
^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
| 詳細な説明を行う前に、まず「\ :ref:`RESTTutorial`\」を行うことで、TERASOLUNA Global FrameworkによるRESTfull Web Serviceの開発を体感していただきたい。
| なお、本章を全て読み終えた後にもう一度「\ :ref:`RESTTutorial`\"」を振り返ると、より理解が深まる。

|

How to design
--------------------------------------------------------------------------------
本説では、RESTfull Web Serviceの設計について説明する。

|

リソースの抽出
^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
Web上に公開するリソースを抽出する。

リソースを抽出する際の注意点を以下に示す。

 .. list-table::
    :header-rows: 1
    :widths: 10 90

    * - 項番
      - リソース抽出時の注意点
    * - | (1)
      - | Web上に公開するリソースは、データベースなどで管理されている情報になるが、\ **安易にデータベースのデータモデルをそのままリソースとして公開してはいけない。**\
        | データベースに格納されている項目の中には、クライアントに公開すべきでない項目もあるので、精査が必要である。
    * - | (2)
      - | \ **データベースの同じテーブルで管理されている情報であっても、情報の種類が異なる場合は、別のリソースとして公開する。**\
        | 本質的には別の情報だが、データ構造が同じという理由で同じテーブルで管理されているケースがあるので、精査が必要である。
    * - | (3)
      - | \ **イベントをリソースとして抽出してはいけない。**\
        | RESTfull Web Serviceでは、イベントで操作する情報をリソースとして抽出する。
        |
        | 例えば、ワークフロー機能で発生するイベント(承認、否認、差し戻しなど)から呼び出されるRESTfull Web Serviceを作成する場合は、ワークフローのフロー状態を管理するための情報をリソースとして抽出する。
        | RESTfull Web Serviceとしては、ワークフローのフロー状態を更新するためのAPIとしてPUTメソッドを公開することになる。

|

URIの割り当て
^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^

APIバージョンを識別するためのURIの割り当て
""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""
RESTfull Web Serviceは、複数のバージョンで稼働が必要になる可能性があるため、クライアントに公開するURIには、APIバージョンを識別するための値を含めるようにする事を推奨する。

具体的には、「``http://example.com/{APIバージョン}/{リソースを識別するためのパス}``\」といった形式のURIとする。

 .. note::
 
    URIにAPIバージョンが必要なのはクライアントに公開するURIのみである。ロードバランサやWebサーバを設ける場合は、アプリケーションサーバにデプロイするWebアプリケーション(war)にアクセスするためのURIにAPIバージョンが含まれている必要はない。

|

リソースを識別するためのURIの割り当て
""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""

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
      - | リソースを一括で操作する際に使用するURIとなる。
    * - | (2)
      - | /{リソースのコレクションを表す名詞/リソースの識別子(IDなど)}
      - | /members/M0001
      - | 特定のリソースを操作する際に使用するURIとなる。

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
      - | /members/M0001/orders
      - | 関連リソースを一括で操作する際に使用するURIとなる。
    * - | (4)
      - | {リソースのURI}/{関連リソースのコレクションを表す名詞}/{関連リソースの識別子(IDなど)}
      - | /members/M0001/orders/O0001
      - | 特定の関連リソースを操作する際に使用するURIとなる。

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
      - | /members/M0001/credential
      - | 要素が1件の関連リソースを操作する際に使用するURI。

|

HTTPメソッドの割り当て
^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
リソース毎に割り当てたURIに対して、以下のHTTPメソッドを割り当てる。

リソースコレクションのURIに対するHTTPメソッドの割り当て
""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""

 .. list-table::
    :header-rows: 1
    :widths: 10 20 70

    * - 項番
      - HTTPメソッド
      - API概要
    * - | (1)
      - | GET
      - | URIで指定されたリソースのコレクションを取得する。
    * - | (2)
      - | POST
      - | 指定されたリソースを作成しコレクションに追加する。
    * - | (3)
      - | PUT
      - | URIで指定されたリソースの一括更新を行う。
    * - | (4)
      - | DELETE
      - | URIで指定されたリソースの一括削除を行う。
    * - | (5)
      - | HEAD
      - | URIで指定されたリソースコレクションのメタ情報を取得する。
        | GETと同じ処理を行いヘッダのみ応答する。
    * - | (6)
      - | OPTIONS
      - | URIで指定されたリソースコレクションでサポートされているHTTPメソッド(API)のリストを応答する。

特定リソースのURIに対するHTTPメソッドの割り当て
""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""

 .. list-table::
    :header-rows: 1
    :widths: 10 20 70

    * - 項番
      - HTTPメソッド
      - API概要
    * - | (1)
      - | GET
      - | URIで指定されたリソースを取得する。
    * - | (2)
      - | PUT
      - | URIで指定されたリソースの作成又は更新を行う。
    * - | (3)
      - | DELETE
      - | URIで指定されたリソースの削除を行う。
    * - | (4)
      - | HEAD
      - | URIで指定されたリソースのメタ情報を取得する。
        | GETと同じ処理を行いヘッダのみ応答する。
    * - | (5)
      - | OPTIONS
      - | URIで指定されたリソースでサポートされているHTTPメソッド(API)のリストを応答する。

|

リソースのフォーマット
^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
.. todo::

    リソースのフォーマットをどのような指針で設計するかについて記載する。

|

HTTPステータスコード
^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
HTTPステータスコードは、以下の指針に則って応答する。

 .. list-table::
    :header-rows: 1
    :widths: 10 90

    * - 項番
      - 方針
    * - | (1)
      - | リクエストが成功した場合は、成功又は転送を示すHTTPステータスコード(2xx又は3xx系)を応答する。
    * - | (2)
      - | リクエストが失敗した原因がクライアント側にある場合は、クライアントエラーを示すHTTPステータスコード(4xx系)を応答する。
        | リクエストが失敗した原因はクライアントにはないが、クライアントの再操作によってリクエストが成功する可能性がある場合も、クライアントエラーとする。
    * - | (3)
      - | リクエストが失敗した原因がサーバ側にある場合は、サーバエラーを示すHTTPステータスコード(5xx系)を応答する。

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

 .. tip::
 
    \ ``"200 OK``\ と \ ``"204 No Content"``\の違いは、レスポンスボディにリソースの情報を出力する/しないの違いとなる。

|

リクエストが失敗した原因がクライアント側にある場合のHTTPステータスコード
""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""
リクエストが失敗した原因がクライアント側にある場合は、状況に応じて以下のHTTPステータスコードを応答する。

|

リソースを扱う個々のAPIで意識する必要があるステータスコードは以下の通り。

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
      - | 409
        | Conflict
      - | リクエストされた内容でリソースの状態を変更すると、リソースの状態に矛盾が発生ため処理を中止した事を通知するHTTPステータスコード。
      - | 排他エラーが発生した場合や業務エラーを検知した場合に応答する。
        | エンティティボディには矛盾の内容や矛盾を解決するために必要なエラー内容を出力する必要がある。

|

| リソースを扱う個々のAPIで意識する必要がないステータスコードは以下の通り。
| 以下のステータスコードは、フレームワークや共通処理として意識する必要がある。

 .. list-table::
    :header-rows: 1
    :widths: 10 20 30 40

    * - | 項番
      - | HTTP
        | ステータスコード
      - | 説明
      - | 適用条件
    * - | (4)
      - | 405
        | Method Not Allowed
      - | 使用されたHTTPメソッドが、指定されたリソースでサポートしていない事を通知するHTTPステータスコード。
      - | サポートされていないHTTPメソッドが使用された事を検知した場合に応答する。
        | レスポンスのAllowヘッダに、許可されているメソッドの列挙を設定する。
    * - | (5)
      - | 406
        | Not Acceptable
      - | 指定された形式でリソースの状態を応答する事が出来ないため、リクエストを受理できない事を通知するHTTPステータスコード。
      - | レスポンス形式として、Acceptヘッダで指定されたMIMEタイプをサポートしていない場合に応答する。
    * - | (6)
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

|

.. todo::

    * 304, 401, 403, 412については、レスポンスキャッシュ制御、認証・認可、排他制御を記載した上で記載すべき・・。

|

リソースの排他制御
^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^

.. todo::

    HTTPの標準ヘッダを使ったリソースの排他制御をどのように行うか記載する。
    
    次版かな・・・

|

リソースのキャッシュ制御
^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
.. todo::

    HTTPの標準ヘッダを使ったリソースのキャッシュ制御をどのように行うか記載する。

    次版かな・・・

|

認証・認可
^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
.. todo::

    認証及び認可制御をどのような指針で行うかについて記載する。
    RESTfull Web Serviceレベルでの認可やリソースレベルでの認可などを、どのように制御するかについて記載する。
    Basic認証？Digest認証？OAuth？などなど・・
    
    次版かな・・・

|

バージョニング
^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
.. todo::

    RESTfull Web Service自体のバージョン管理及び複数バージョンの並行稼働をどのように行うかについて記載する。
    
    次版かな・・・

|

.. _RESTHowToUse:

How to use
--------------------------------------------------------------------------------
RESTfull Web Serviceの作成方法について説明する。

|

Webアプリケーションの構成
^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
| RESTfull Web Serviceを構築する場合は、以下のいずれかの構成でWebアプリケーション(war)を構築する。
| **特に理由がない場合は、RESTfull Web Service専用のWebアプリケーションとして構築する事を推奨する。**

 .. list-table::
    :header-rows: 1
    :widths: 10 30 60

    * - 項番
      - 構成
      - 説明
    * - | (1)
      - | RESTfull Web Service専用のWebアプリケーションとして構築する。
      - | RESTfull Web Serviceを利用するクライアントアプリケーション(UI層のアプリケーション)との独立性を確保したい(する必要がある)場合は、RESTfull Web Service専用のWebアプリケーションとして構築することを推奨する。
        |
        | RESTfull Web Serviceを利用するクライアントアプリケーションが複数になる場合は、この方法でRESTfull Web Serviceを生成することになる。
    * - | (2)
      - | RESTfull Web Service用の\ ``DispatcherServlet``\を設けて構築する。
      - | RESTfull Web Serviceを利用するクライアントアプリケーション(UI層のアプリケーション)との独立性を確保する必要がない場合は、RESTfull Web Serviceとクライアントアプリケーションを一つのWebアプリケーション(war)として構築してもよい。
        |
        | ただし、RESTfull Web Service用のリクエストを受ける\ ``DispatcherServlet``\と、クライアントアプリケーション用のリクエストを受け取る\ ``DispatcherServlet``\は分割して構築することを強く推奨する。

 .. note:: **クライアントアプリケーション(UI層のアプリケーション)とは**

    ここで言うクライアントアプリケーション(UI層のアプリケーション)とは、HTML, JavaScriptなどのスクリプト, CSS(Cascading Style Sheets)といったクライアント層(UI層)のコンポーネントを応答するアプリケーションの事をさす。
    JSPなどのテンプレートエンジンによって生成されるHTMLも対象となる。

 .. note:: **DispatcherServletを分割する事を推奨する理由**

    Spring MVCでは、\ ``DispatcherServlet``\毎にアプリケーションの動作設定を定義することになる。
    そのため、RESTfull Web Serviceとクライアントアプリケーション(UI層のアプリケーション)のリクエストを同じ\ ``DispatcherServlet``\で受ける構成にしてしまうと、RESTfull Web Service又はクライアントアプリケーション固有の動作設定を定義する事ができなくなったり、設定が煩雑又は複雑になることがある。
    
    本ガイドラインでは、上記の様な問題が起こらないようにするために、RESTfull Web Serviceをクライアントアプリケーションを同じWebアプリケーション(war)として構築する場合は、\ ``DispatcherServlet``\を分割することを推奨している。

 .. todo::

    Webアプリケーション内の構成を、図で示した方がよい気がする・・。


|

アプリケーションの設定
^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^

RESTfull Web Serviceで必要となるSpring MVCのコンポーネントを有効化するための設定
""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""
| RESTfull Web Service用のbean定義ファイルを作成する。
| RESTfull Web Serviceを構築する際に最低限必要となる定義は、以下の通りである。

- :file:`spring-mvc.xml`

  RESTfull Web Serviceとクライアントアプリケーションを同じWebアプリケーションとして構築する場合は\ :file:`spring-mvc-rest.xml`\に定義し、
  「\ :ref:`RESTAppendixSettingsOfDeployInSameWarFileRestAndClientApplication`\」が必要となる。

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
    
        <!-- Scan & register components of RESTfull Web Service. -->
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
      - | RESTfull Web Service用のアプリケーション層のコンポーネント(ControllerやHelperクラスなど)をスキャンしてbean登録する。
        | \ ``"com.example.rest.app"``\ の部分はプロジェクト毎のパッケージ名に変更すること。
    * - | (3)
      - | RESTfull Web Serviceを提供するために必要となるSpring MVCのフレームワークコンポーネントをbean登録する。
        | 上記設定を行うことで、リソースのフォーマットとしてJSONとXMLを使用する事ができる。
        | ただし、リソースのフォーマットとしてXMLを使用する場合は、別途XXE Injection対策を行う必要があるため、「\ :ref:`RESTAppendixEnabledXXEInjectProtection`\」を行うこと。
        | 上記例では\ ``<mvc:annotation-driven />``\のみ指定しているが、ページネーション機能などを使用する場合は、必要に応じて\ ``<mvc:annotation-driven>``\の子要素を別途追加する必要がある。
        | 設定方法については、使用する機能のガイドラインを参照されたい。
    * - | (4)
      - | RESTfull Web Serviceを提供するために必要となるSpring MVCのインターセプタをbean登録する。
        | 上記例では、共通ライブラリから提供されている\ ``TraceLoggingInterceptor``\のみを定義しているが、データアクセスとしてJPAを使う場合は、別途\ ``OpenEntityManagerInViewInterceptor``\の設定を追加する必要がある。
        | \ ``OpenEntityManagerInViewInterceptor``\については、「\ :doc:`DataAccessJpa`\」を参照されたい。
    * - | (5)
      - | Spring MVCのフレームワークでハンドリングされた例外を、ログ出力するためのAOP定義を指定する。
        | \ ``HandlerExceptionResolverLoggingInterceptor``\については、「\ :doc:`ExceptionHandling`\」を参照されたい。

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
        <servlet-name>appServlet</servlet-name>
        <servlet-class>org.springframework.web.servlet.DispatcherServlet</servlet-class>
        <init-param>
            <param-name>contextConfigLocation</param-name>
            <param-value>classpath*:META-INF/spring/spring-mvc.xml</param-value>
        </init-param>
        <!-- (1) -->
        <init-param>
            <param-name>dispatchOptionsRequest</param-name>
            <param-value>true</param-value>
        </init-param>
        <load-on-startup>1</load-on-startup>
    </servlet>

    <!-- omitted -->

 .. list-table::
   :header-rows: 1
   :widths: 10 90

   * - | 項番
     - | 説明
   * - | (1)
     - | RESTfull Web Serviceのリクエストを受け付ける\ ``DispatcherServlet``\の初期化パラメータ(dispatchOptionsRequest)の値を、\ ``true``\に設定する。

|

Resourceクラスの作成
^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^

| 本ガイドラインでは、Web上に公開するリソースのデータを保持するクラスとして、Resourceクラスを設けることを推奨する。
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
        | 入力チェックの詳細については、「\ :doc:`Validation`\」を参照されたい。

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
    
        public static interface MemberCreating {
        }
    
        public static interface MemberUpdating {
        }

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

|

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

 .. tip::
 
    ページネーション検索が不要な場合は、Resourceクラスをリスト形式で保持するコレクション用のResourceクラスの作成は不要であり、Resourceクラスのリストを直接扱ってもよい。

    コレクション用のResourceクラスを作成しない場合は、以下のようなJSONとなる。
    
     .. code-block:: json

        [ {
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
        } ]
    
    Resourceクラスのリストを直接扱う場合のControllerのメソッドは以下のような定義となる。

     .. code-block:: java

        @RequestMapping(method = { RequestMethod.GET, RequestMethod.HEAD })
        @ResponseBody
        public ResponseEntity<List<MemberResource>> getMembers(
                @Validated MembersSearchQuery query) {
            // omitted
        }

|

 .. todo::

    * JacksonのアノテーションやJacksonの拡張ポイント等の説明が必要かな・・ > Appendix

|

RESTfull Web Serviceの作成
^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
RESTfull Web Service(Controller)はリソース毎に作成し、以下の2つのURIに対するAPIを提供する。

 .. list-table::
    :header-rows: 1
    :widths: 10 60 30

    * - 項番
      - URI形式
      - URIの具体例
    * - | (1)
      - | /{リソースのコレクションを表す名詞}
      - | /members
    * - | (2)
      - | /{リソースのコレクションを表す名詞}/{リソースの識別子(IDなど)}
      - | /members/M0001

|

Controllerクラスの作成
""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""
| 以下に、Controllerクラスの作成例を示す。
| 個々のAPI(メソッド)の実装例については、別途説明する。

 .. code-block:: java
    :emphasize-lines: 1, 5, 14, 23, 30-31, 39, 49, 57

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
                @RequestBody @Validated({ Default.class, MemberCreating.class })
                MemberResource requestedResource) {
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
                @RequestBody @Validated({ Default.class, MemberUpdating.class })
                MemberResource requestedResource) {
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
      - | Controllerに対して、リソースのコレクション用のURI(サーブレットパス)をマッピングする。
        | 具体的には、\ ``@RequestMapping``\アノテーションのvalue属性に、リソースのコレクションを表すサーブレットパスを指定する。
        | 上記例では、 \ ``/members``\ というサーブレットパスをマッピングしている。
    * - | (2)-(4)
      - | リソースのコレクションに対する操作を提供するAPIを作成する。
        | 上記例では、 \ ``/members``\というサーブレットパスに対するAPIとなる。
    * - | (5)-(8)
      - | 特定のリソースに対する操作を提供するAPIを作成する。
        | \ ``@RequestMapping``\アノテーションのvalue属性に\ ``"{memberId}"``\を指定することで、 \ ``/members/{memberId}``\というパターンのサーブレットパスに対するAPIとなる。
        | \ ``{memberId}``\の部分はパス変数と呼ばれ、メソッドの引数アノテーションとして\ ``@PathVariable("memberId")``\を指定することで、\ ``{memberId}``\部分に指定された値をメソッドの引数として受け取ることが出来る。
        | パス変数については、 「:ref:`controller_method_argument-pathvariable-label`」を参照されたい。

|

リソースコレクションに対するAPIの実装
^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^

リソースのコレクションを取得するAPIの実装
""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""
URIで指定されたリソースのコレクションを取得するAPIの実装例を、以下に示す。

- | 検索条件を受け取るためのJavaBeanの作成
  | リソースのコレクションを取得する際に、検索条件が必要な場合は、検索条件を受け取るためのJavaBeanの作成する。

 .. code-block:: java
    :emphasize-lines: 1, 5

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
        | 上記例では、\ ``/members?name=John``\ というリクエストの場合、JavaBeanのnameプロパティに \ ``"John"``\ という値が設定される。


- | Controllerの実装
  | リソースのコレクションを取得する処理を実装する。
  
 .. code-block:: java
    :emphasize-lines: 13, 14, 16, 17, 19, 24, 33

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
                Pageable pageable) { // (6)
    
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
        | HEADメソッドは、GETメソッドと同じ処理を行いヘッダ情報のみレスポンスする必要があるため、\ ``@RequestMapping``\アノテーションのmethod属性に、``RequestMethod.HEAD``\も指定する。
        | レスポンスBODYを空にする処理は、Servlet APIの標準機能で行われるため、Controllerの処理としてはGETメソッドと同じ処理を行えばよい。
    * - | (4)
      - | メソッドアノテーションとして、\ ``@org.springframework.web.bind.annotation.ResponseBody``\アノテーションを付与する。
        | このアノテーションを付与することで、返却したResourceオブジェクトがJSONやXMLにmarshalされ、レスポンスBODYに設定される。
    * - | (5)
      - | 検索条件を受け取るためのJavaBeanを引数に指定する。
        | 入力チェックが必要な場合は、引数アノテーションとして、\ ``@Validated``\アノテーションを付与する。入力チェックの詳細については、「\ :doc:`Validation`\」を参照されたい。
    * - | (6)
      - | ページネーション検索が必要な場合は、\ ``org.springframework.data.domain.Pageable``\を引数に指定する。
        | ページネーション検索の詳細については、「:doc:`Pagination`」を参照されたい。
    * - | (7)
      - | ドメイン層のServiceのメソッドを呼び出し、条件に一致するリソースの情報(Entityなど)を取得する。
        | ドメイン層の実装については、「:doc:`../ImplementationAtEachLayer/DomainLayer`」を参照されたい。
    * - | (8)
      - | 条件に一致したリソースの情報(Entityなど)をもとに、Web上に公開する情報を保持するResourceオブジェクトを生成する。
        | 上記例では、Beanマッピングライブラリを使用してEntityからResourceオブジェクトを生成している。Beanマッピングライブラリについては、「\ :doc:`Utilities/Dozer`\」を参照されたい。
        | **Resourceオブジェクトを生成するためのコード量が多くなる場合は、HelperクラスにResourceオブジェクトを生成するためのメソッドを作成することを推奨する。**
    * - | (9)
      - | \ ``org.springframework.http.ResponseEntity``\ オブジェクトを返却する。
        | ステータスコードには200(OK)を設定する。

|

リソースをコレクションに追加するAPIの実装
""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""
指定されたリソースを作成し、リソースのコレクションに追加するAPIの実装例を、以下に示す。


- | Controller
  | 指定されたリソースを作成し、リソースのコレクションに追加する処理を実装する。

 .. code-block:: java
    :emphasize-lines: 7, 13, 16-17, 19, 26, 32

    @RequestMapping("members")
    @Controller
    public class MembersRestController {
    
        // omitted

        // (1)
        @Value("${baseUri}/members/{member}")
        String uriTemplateText;

        // omitted

        @RequestMapping(method = RequestMethod.POST)  // (2)
        @ResponseBody
        public ResponseEntity<MemberResource> createMember(
                @RequestBody @Validated({ Default.class, MemberCreating.class }) 
                MemberResource requestedResource) { // (3)
    
            // (4)
            Member inputtedMember = beanMapper.map(requestedResource, Member.class);
            Member createdMember = memberSevice.createMember(inputtedMember);
    
            MemberResource responseResource = beanMapper.map(createdMember,
                    MemberResource.class);

            // (5)
            HttpHeaders responseHeaders = new HttpHeaders();
            UriTemplate uriTemplate = new UriTemplate(uriTemplateText);
            responseHeaders.setLocation(uriTemplate
                    .expand(responseResource.getMemberId()));
    
            // (6)
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
    * - | (1)
      - | URIのテンプレート文字列を取得する。(5)の処理にて、作成したリソースのURIを生成する際に使用する。
        | **URIのベース部分は環境依存値となるので、必ずプロパティファイルから取得すること。**
        | プロパティファイルから値を取得する方法の詳細ついては、「\ :doc:`PropertyManagement`\」を参照されたい。
        |
        | 上記例では、``@Value("${baseUri}/members/{member}")``\としているので、\ ``uriTemplateText``\には\ ``"http://example.com/v1.0/members/{member}"``\という値が設定される。
        | プロパティファイルには設定例については、(7)を参照されたい。
    * - | (2)
      - | \ ``@RequestMapping``\アノテーションのmethod属性に、\ ``RequestMethod.POST``\を指定する。
    * - | (3)
      - | 新規に作成するリソースの情報を受け取るためのJavaBean(Resourceクラス)を引数に指定する。
        | 引数アノテーションとして、``@org.springframework.web.bind.annotation.RequestBody``\アノテーションを付与する。
        | \ ``@RequestBody``\アノテーションを付与することで、リクエストBodyに設定されているJSONやXMLのデータがResourceオブジェクトにunmarshalされる。
        |
        | 入力チェックを有効化するために、引数アノテーションとして、\ ``@Validated``\アノテーションを付与する。入力チェックの詳細については、「\ :doc:`Validation`\」を参照されたい。
    * - | (4)
      - | ドメイン層のServiceのメソッドを呼び出し、新規にリソースを作成する。
        | ドメイン層の実装については、「:doc:`../ImplementationAtEachLayer/DomainLayer`」を参照されたい。
    * - | (5)
      - | **作成したリソースのURIを、レスポンスのLocationヘッダに設定する。**
        | 上記例では、\ ``responseResource.getMemberId()``\の返却値が\ ``"M12345"``\だった場合、 \ ``http://example.com/v1.0/members/M12345``\がURIとなる。
    * - | (6)
      - | \ ``ResponseEntity``\ オブジェクトを返却する。
        | ステータスコードには\ **201(Created)**\を設定する。

|

- | プロパティファイル
  | URIのベース部分をプロパティファイルに定義する。

 .. code-block:: properties
    :emphasize-lines: 1

    # (7)
    baseUri=http://example.com/v1.0

 .. list-table::
    :header-rows: 1
    :widths: 10 90

    * - 項番
      - 説明
    * - | (7)
      - | URIのベース部分は環境依存値となるのでプロパティファイルに定義する。
        | (5)の処理にて、レスポンスのLocationヘッダに設定するURIを生成する際に使用する。

|

応答されるヘッダは以下の通り。

 .. code-block:: guess
    :emphasize-lines: 1, 4

    HTTP/1.1 201 Created
    Server: Apache-Coyote/1.1
    X-Track: f246a1e7df964c01a2544fd4158da064
    Location: http://example.com/v1.0/members/M12345
    Content-Type: application/json;charset=UTF-8
    Transfer-Encoding: chunked
    Date: Tue, 18 Feb 2014 21:42:52 GMT

|

サポートされているHTTPメソッド(API)のリストを応答するAPIの実装
""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""
URIで指定されたリソースでサポートされているHTTPメソッド(API)のリストを応答するAPIの実装例を、以下に示す。

- | Controller
  | URIで指定されたリソースでサポートされているHTTPメソッド(API)のリストを応答する処理を実装する。

 .. code-block:: java
    :emphasize-lines: 7, 9, 11, 20

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

 .. tip::
 
    URIで指定されたリソースでサポートされているHTTPメソッド(API)のリストを応答する処理は、全てのリソースで同じようなロジックを組む事になるため、ユーティリティメソッド化する事を検討すること。
    ユーティリティメソッドのサンプルについては、「\ :ref:`RESTAppendixUtilityOfOptionsMethod`\」を参照されたい。

|

応答されるヘッダは以下の通り。

 .. code-block:: guess
    :emphasize-lines: 4

    HTTP/1.1 200 OK
    Server: Apache-Coyote/1.1
    X-Track: ba5c8e7bc66c43c0a4f91e757f2cc348
    Allow: GET,HEAD,POST,OPTIONS
    Content-Length: 0
    Date: Tue, 18 Feb 2014 21:50:57 GMT

|

特定リソースに対するAPIの実装
^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^

指定されたリソースを取得するAPIの実装
""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""
URIで指定されたリソースを取得するAPIの実装例を、以下に示す。

- | Controller
  | URIで指定されたリソースを取得する処理を実装する。

 .. code-block:: java
    :emphasize-lines: 7-8, 11, 13, 19

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
        | \ ``{memberId}``\には、リソースを一意に識別するための値が指定される。
    * - | (2)
      - | リソースを一意に識別するための値を、パス変数から取得する。
        | 引数アノテーションとして、\ ``@PathVariable("memberId")``\を指定することで、パス変数(\ ``{memberId}``\)に指定された値をメソッドの引数として受け取ることが出来る。
        | パス変数の詳細については、 「:ref:`controller_method_argument-pathvariable-label`」を参照されたい。
        | 上記例だと、URIが\ ``/members/M12345``\の場合、引数の\ ``memberId``\に\ ``"M12345"``\が格納される。
    * - | (3)
      - | ドメイン層のServiceのメソッドを呼び出し、パス変数から取得したIDに一致するリソースの情報(Entityなど)を取得する。
        | ドメイン層の実装については、「:doc:`../ImplementationAtEachLayer/DomainLayer`」を参照されたい。
    * - | (4)
      - | \ ``ResponseEntity``\ オブジェクトを返却する。
        | ステータスコードには200(OK)を設定する。

|

指定されたリソースを更新するAPIの実装
""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""
URIで指定されたリソースを更新するAPIの実装例を、以下に示す。

- | Controller
  | URIで指定されたリソースを更新する処理を実装する。

 .. code-block:: java
    :emphasize-lines: 7, 11-12, 14, 23

    @RequestMapping("members")
    @Controller
    public class MembersRestController {

        // omitted

        @RequestMapping(value = "{memberId}", method = RequestMethod.PUT) // (1)
        @ResponseBody
        public ResponseEntity<MemberResource> updateMember(
                @PathVariable("memberId") String memberId,
                @RequestBody @Validated({ Default.class, MemberUpdating.class })
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
        | \ ``{memberId}``\には、リソースを一意に識別するための値が指定される。
    * - | (2)
      - | リソースの更新内容を受け取るためのJavaBean(Resourceクラス)を引数に指定する。
        | 引数アノテーションとして、\ ``@RequestBody``\アノテーションを付与することで、リクエストBodyに設定されているJSONやXMLのデータがResourceオブジェクトにunmarshalされる。
        |
        | 入力チェックを有効化するために、引数アノテーションとして、\ ``@Validated``\アノテーションを付与する。
        | 入力チェックの詳細については、「\ :doc:`Validation`\」を参照されたい。
    * - | (3)
      - | ドメイン層のServiceのメソッドを呼び出し、パス変数から取得したIDに一致するリソースの情報(Entityなど)を更新する。
        | ドメイン層の実装については、「:doc:`../ImplementationAtEachLayer/DomainLayer`」を参照されたい。
    * - | (4)
      - | \ ``ResponseEntity``\ オブジェクトを返却する。
        | ステータスコードには200(OK)を設定する。

|

指定されたリソースを削除するAPIの実装
""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""
URIで指定されたリソースを削除するAPIの実装例を、以下に示す。

- | Controller
  | URIで指定されたリソースを削除する処理を実装する。

 .. code-block:: java
    :emphasize-lines: 7, 12, 15

    @RequestMapping("members")
    @Controller
    public class MembersRestController {

        // omitted

        @RequestMapping(value = "{memberId}", method = RequestMethod.DELETE) // (1)
        @ResponseBody
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
      - | ドメイン層のServiceのメソッドを呼び出し、パス変数から取得したIDに一致するリソースの情報(Entityなど)を削除する。
        | ドメイン層の実装については、「:doc:`../ImplementationAtEachLayer/DomainLayer`」を参照されたい。
    * - | (3)
      - | \ ``ResponseEntity``\ オブジェクトを返却する。
        | 上記例では、レスポンスBODYを返却しないので、ステータスコードには\ **204(NO_CONTENT)**\を設定している。

 .. note::
 
    削除したリソースの情報をレスポンスBODYに設定する場合は、ステータスコードには200(OK)を設定する。

|

応答されるヘッダは以下の通り。

 .. code-block:: guess
    :emphasize-lines: 1

    HTTP/1.1 204 No Content
    Server: Apache-Coyote/1.1
    X-Track: 207046c3c4014045b3b6a15ac6239b72
    Date: Tue, 18 Feb 2014 21:54:21 GMT

|

サポートされているHTTPメソッド(API)のリストを応答するAPIの実装
""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""
URIで指定されたリソースでサポートされているHTTPメソッド(API)のリストを応答するAPIの実装例を、以下に示す。

- | Controller
  | URIで指定されたリソースでサポートされているHTTPメソッド(API)のリストを応答する処理を実装する。

 .. code-block:: java
    :emphasize-lines: 12, 15

    @RequestMapping("members")
    @Controller
    public class MembersRestController {

        // omitted

        @RequestMapping(value = "{memberId}", method = RequestMethod.OPTIONS)
        @ResponseBody
        public ResponseEntity<Void> optionsMember(
                @PathVariable("memberId") String memberId) {

            // (1)
            memberSevice.getMember(memberId);
    
            // (2)
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
      - | ドメイン層のServiceのメソッドを呼び出し、パス変数から取得したIDに一致するリソースが存在するかチェックを行う。
    * - | (2)
      - | **URIで指定されたリソースでサポートされているHTTPメソッドを、Allowヘッダに設定する。**
        | 上記例では、 \ ``"/members/{memberId}"``\というパターンのサーブレットパスでサポートされているHTTPメソッドの一覧を設定している。

 .. tip::
 
    URIで指定されたリソースでサポートされているHTTPメソッド(API)のリストを応答する処理は、全てのリソースで同じようなロジックを組む事になるため、ユーティリティメソッド化する事を検討すること。
    ユーティリティメソッドのサンプルについては、「\ :ref:`RESTAppendixUtilityOfOptionsMethod`\」を参照されたい。

|

応答されるヘッダは以下の通り。

 .. code-block:: guess
    :emphasize-lines: 4

    HTTP/1.1 200 OK
    Server: Apache-Coyote/1.1
    X-Track: 4a703028e6c844239198fbfcd96dd30d
    Allow: GET,HEAD,PUT,DELETE,OPTIONS
    Content-Length: 0
    Date: Tue, 18 Feb 2014 21:56:44 GMT

|

例外のハンドリング
^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
RESTfull Web Serviceで発生した例外のハンドリング方法について説明する。

| Spring MVCから提供されている\ ``org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler``\を継承したクラスを作成し、\ ``@ControllerAdvice``\アノテーションを付与する方法でハンドリングする事を推奨する。

| \ ``ResponseEntityExceptionHandler``\は、Spring MVCのフレームワーク内で発生する例外を\ ``@ExceptionHandler``\アノテーションを使ってハンドリングするメソッドが実装されており、ハンドリングされる例外と設定されるHTTPステータスコードは、\ ``DefaultHandlerExceptionResolver``\と同様である。
| ハンドリングされる例外と設定されるHTTPステータスコードについては、「\ :ref:`exception-handling-appendix-defaulthandlerexceptionresolver-label`\」を参照されたい。

| \ ``ResponseEntityExceptionHandler``\のデフォルトの実装ではレスポンスBodyは空で返却されるが、レスポンスBodyにエラー情報を出力するように拡張する事ができる様になっている。
| 以下に、レスポンスBodyにエラー情報を出力するための実装例について説明する。

|

レスポンスBodyにエラー情報を出力するための実装
""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""
エラー情報を保持するJavaBeanを作成する。

 .. code-block:: java
    :emphasize-lines: 1, 11

    // (1)
    public class RestError implements Serializable {
    
        private static final long serialVersionUID = 1L;
    
        private final String code;

        private final String message;

        @JsonSerialize(include = Inclusion.NON_EMPTY)
        private final List<RestError> details = new ArrayList<>(); // (2)
    
        public RestError(String code, String message) {
            this.code = code;
            this.message = message;
        }

        public String getCode() {
            return code;
        }

        public String getMessage() {
            return message;
        }

        public List<RestError> getDetails() {
            return details;
        }

        public void addDetail(RestError detail) {
            details.add(detail);
        }

    }
    
 .. list-table::
    :header-rows: 1
    :widths: 10 90

    * - 項番
      - 説明
    * - | (1)
      - | エラー情報を保持するためのクラスを作成する。
        | 上記例では、エラーコード、エラーメッセージ、エラーの詳細情報のリストを保持するクラスとなっている。
    * - | (2)
      - | エラーの詳細情報のリストを保持するためのフィールド。
        | 入力チェックでエラーが発生した場合、エラー原因が複数存在する場合があるため、すべてのエラー情報をクライアントに返却する事が求められるケースがある。
        | そのような場合は、エラーの詳細情報をリストで保持するフィールドが必要になる。

|

エラー情報を保持するJavaBeanを生成するためのクラスを作成する。

 .. code-block:: java
    :emphasize-lines: 1, 7-8

    // (3)
    @Component
    public class RestErrorCreator extends ApplicationObjectSupport {
    
        public RestError createRestError(String code, String defaultMessage,
                Locale locale, Object... arguments) {
            String localizedMessage = getMessageSourceAccessor().getMessage(code,
                    arguments, defaultMessage, locale); // (4)
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
    * - | (4)
      - | エラーメッセージは、\ ``MessageSource``\より取得する。
        | メッセージの管理方法については、「\ :doc:`MessageManagement`\」を参照されたい。

|

\ ``ResponseEntityExceptionHandler``\のメソッドを拡張し、レスポンスBodyにエラー情報を出力するための実装を行う。

 .. code-block:: java
    :emphasize-lines: 1-2, 10, 16, 24

    @ControllerAdvice // (5)
    public class RestGlobalExceptionHandler extends ResponseEntityExceptionHandler {

        @Inject
        RestErrorCreator restErrorCreator;
    
        @Inject
        ExceptionCodeResolver exceptionCodeResolver;

        // (6)
        @Override
        protected ResponseEntity<Object> handleExceptionInternal(
                Exception ex, Object body, HttpHeaders headers,
                HttpStatus status, WebRequest request) {
            final Object errorBody;
            // (7)
            if (body == null) {
                String code = exceptionCodeResolver.resolveExceptionCode(ex);
                errorBody = restErrorCreator.createRestError(code,
                    ex.getLocalizedMessage(), request.getLocale());
            } else {
                errorBody = body;
            }
            // (8)
            return new ResponseEntity<>(errorBody, headers, status);
        }
        
        // omitted
    
    }


 .. list-table::
    :header-rows: 1
    :widths: 10 90

    * - 項番
      - 説明
    * - | (5)
      - | Spring MVCから提供されている\ ``ResponseEntityExceptionHandler``\を継承したクラスを作成し、\ ``@ControllerAdvice``\アノテーションを付与する。
    * - | (6)
      - | \ ``ResponseEntityExceptionHandler``\のhandleExceptionInternalメソッドをオーバライドする。
    * - | (7)
      - | レスポンスBodyに出力するJavaBeanの指定がない場合は、エラー情報を保持するJavaBeanオブジェクトを生成する。
        | 上記例では、共通ライブラリから提供している\ ``ExceptionCodeResolver``\を使用して、例外クラスをエラーコードを変換している。
        | \ ``ExceptionCodeResolver``\の設定例については、「\ :ref:`RESTAppendixSettingsOfExceptionCodeResolver`\」を参照されたい。
        |
        | レスポンスBodyに出力するJavaBeanの指定がある場合は、指定されたJavaBeanをそのまま使用する。
        | この処理は、別のエラーハンドリング処理にて、個別にエラー情報が生成される事を考慮している。
    * - | (8)
      - | レスポンス用のHTTP EntityのBody部分に、(7)で生成したエラー情報を設定し返却する。
        | 返却したエラー情報は、フレームワークによってJSONに変換されレスポンスされる。
        |
        | ステータスコードには、Spring MVCから提供されている\ ``ResponseEntityExceptionHandler``\によって適切な値が設定される。
        | 設定されるステータスコードについては、「\ :ref:`exception-handling-appendix-defaulthandlerexceptionresolver-label`\」を参照されたい。

|

指定したリソースでサポートしていないメソッドを使用した場合、以下のようなエラー応答が行われる。

 .. code-block:: guess
    :emphasize-lines: 1, 4, 5, 9

    HTTP/1.1 405 Method Not Allowed
    Server: Apache-Coyote/1.1
    X-Track: 9c249091aaa249418cd88bf257a25019
    Allow: GET,HEAD,POST,OPTIONS
    Content-Type: application/json;charset=UTF-8
    Transfer-Encoding: chunked
    Date: Tue, 18 Feb 2014 20:35:41 GMT
    
    {"code":"e.ex.fw.5004","message":"Request method is not supported."}

|

入力エラーのハンドリング
""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""
入力エラー（電文不正、単項目チェックエラー、相関項目チェックエラー）を応答するための実装例について説明する。

入力エラーを応答するためには、以下の３つの例外をハンドリングする必要がある。

 .. list-table::
    :header-rows: 1
    :widths: 10 35 55

    * - 項番
      - 例外
      - 説明
    * - | (1)
      - | org.springframework.web.bind.
        | MethodArgumentNotValidException
      - | リクエストBODYに指定されたJSONやXMLに対する入力チェックでエラーが発生した場合、本例外が発生する。
        | 具体的には、リソースのPOST又はPUT時に指定するリソースに不正な値が指定されている場合に発生する。
    * - | (2)
      - | org.springframework.validation.
        | BindException
      - | リクエストパラメータ(key=value形式のクエリ文字列)に対する入力チェックでエラーが発生した場合、本例外が発生する。
        | 具体的には、リソースコレクションのGET時に指定する検索条件に不正な値が指定されている場合に発生する。
    * - | (3)
      - | org.springframework.http.converter.
        | HttpMessageNotReadableException
      - | JSONやXMLからResourceオブジェクトを生成する際にエラーが発生した場合は、本例外が発生する。
        | 具体的には、JSONやXMLの構文不正やスキーマ定義に違反などがあった場合に発生する。

|

\ ``ResponseEntityExceptionHandler``\のメソッドを拡張し、レスポンスBodyに入力チェック用のエラー情報を出力するための実装を行う。

 .. code-block:: java
    :emphasize-lines: 12-13, 21-22, 29-30, 34-36, 42

    @ControllerAdvice
    public class RestGlobalExceptionHandler extends ResponseEntityExceptionHandler {

        @Inject
        RestErrorCreator restErrorCreator;
    
        @Inject
        ExceptionCodeResolver exceptionCodeResolver;

        // omitted

        // (1)
        @Override
        protected ResponseEntity<Object> handleMethodArgumentNotValid(
                MethodArgumentNotValidException ex, HttpHeaders headers,
                HttpStatus status, WebRequest request) {
            return handleBindingResult(ex, ex.getBindingResult(), headers, status,
                    request);
        }
    
        // (2)
        @Override
        protected ResponseEntity<Object> handleBindException(BindException ex,
                HttpHeaders headers, HttpStatus status, WebRequest request) {
            return handleBindingResult(ex, ex.getBindingResult(), headers, status,
                    request);
        }
        
        // (3)
        @Override
        protected ResponseEntity<Object> handleHttpMessageNotReadable(
                HttpMessageNotReadableException ex, HttpHeaders headers,
                HttpStatus status, WebRequest request) {
            if (ex.getCause() instanceof Exception) {
                return handleExceptionInternal((Exception) ex.getCause(), null,
                        headers, status, request);
            } else {
                return handleExceptionInternal(ex, null, headers, status, request);
            }
        }

        // (4)
        protected ResponseEntity<Object> handleBindingResult(Exception ex,
                BindingResult bindingResult, HttpHeaders headers,
                HttpStatus status, WebRequest request) {
            String code = exceptionCodeResolver.resolveExceptionCode(ex);
            RestError errorBody = restErrorCreator.createBindingResultRestError(
                    code, bindingResult, ex.getMessage(), request.getLocale());
            return handleExceptionInternal(ex, errorBody, headers, status, request);
        }

        // omitted
    
    }

 .. list-table::
    :header-rows: 1
    :widths: 10 90

    * - 項番
      - 説明
    * - | (1)
      - | \ ``ResponseEntityExceptionHandler``\のhandleMethodArgumentNotValidメソッドをオーバライドし、\ ``MethodArgumentNotValidException``\のエラーハンドリングを拡張する。
        | 上記例では、入力チェックエラーをハンドリングするための共通メソッドに処理を委譲している。
        | 
        | ステータスコードには\ **400(Bad Request)**\が設定され、指定されたリソースの項目値に不備がある事を通知する。
    * - | (2)
      - | \ ``ResponseEntityExceptionHandler``\のhandleBindExceptionメソッドをオーバライドし、\ ``BindException``\のエラーハンドリングを拡張する。
        | 上記例では、入力チェックエラーをハンドリングするための共通メソッドに処理を委譲している。
        | 
        | ステータスコードには\ **400(Bad Request)**\が設定され、指定されたリクエストパラメータに不備がある事を通知する。
    * - | (3)
      - | \ ``ResponseEntityExceptionHandler``\のhandleHttpMessageNotReadableメソッドをオーバライドし、\ ``HttpMessageNotReadableException``\のエラーハンドリングを拡張する。
        | 上記例では、細かくエラーハンドリングを行うために、原因例外を使ってエラーハンドリングしている。
        | 細かくエラーハンドリングをしなくてもよい場合は、オーバライドする必要はない。
        | 
        | ステータスコードには\ **400(Bad Request)**\が設定され、指定されたリソースのフォーマットなどに不備がある事を通知する。
    * - | (4)
      - | 入力チェックエラーをハンドリングするための処理を実装する。
        | 上記例では、handleMethodArgumentNotValidとhandleBindExceptionで同じ処理を実装する事になるので、共通メソッドとして本メソッドを作成している。

 .. tip:: **JSON使用時のエラーハンドリングについて**

    リソースのフォーマットとしてJSONを使用する場合、エラー原因によって以下の例外が原因例外に格納される。

     .. list-table::
        :header-rows: 1
        :widths: 10 35 55
    
        * - 項番
          - 例外クラス
          - 説明
        * - | (1)
          - | org.codehaus.jackson.
            | JsonParseException
          - | JSONとして不正な構文が含まれる場合に発生する。
        * - | (2)
          - | org.codehaus.jackson.map.exc.
            | UnrecognizedPropertyException
          - | Resourceオブジェクトに存在しないフィールドがJSONに指定されている場合に発生する。
        * - | (3)
          - | org.codehaus.jackson.map.
            | JsonMappingException
          - | JSONからResourceオブジェクトへ変換する際に、値の型変換が発生した場合に発生する。

|

入力チェックエラー用のエラー情報を保持するJavaBeanを生成するためのメソッドを作成する。

 .. code-block:: java
    :emphasize-lines: 6

    @Component
    public class RestErrorCreator extends ApplicationObjectSupport {

        // omitted

        // (5)
        public RestError createBindingResultRestError(String errorCode,
                BindingResult bindingResult, String defaultMessage, Locale locale) {

            RestError restError = createRestError(errorCode, defaultMessage, locale);

            for (FieldError fieldError : bindingResult.getFieldErrors()) {
                restError.addDetail(createRestError(fieldError, locale));
            }

            for (ObjectError objectError : bindingResult.getGlobalErrors()) {
                restError.addDetail(createRestError(objectError, locale));
            }

            return restError;
        }
        
        private RestError createRestError(
                DefaultMessageSourceResolvable messageResolvable, Locale locale) {
    
            String localizedMessage = getMessageSourceAccessor().getMessage(
                    messageResolvable, locale);
    
            return new RestError(messageResolvable.getCode(), localizedMessage);
        }
        
        // omitted

    }

 .. list-table::
    :header-rows: 1
    :widths: 10 90

    * - 項番
      - 説明
    * - | (5)
      - | 入力チェック用のエラー情報を生成するためのメソッドを作成する。
        | 上記例では、単項目チェックエラー(\ ``FieldError``\)と相関項目チェックエラー(\ ``ObjectError``\)を、エラーの詳細情報に追加している。

|

入力チェックエラーが発生した場合、以下のようなエラー応答が行われる。

 .. code-block:: guess
    :emphasize-lines: 1, 9

    HTTP/1.1 400 Bad Request
    Server: Apache-Coyote/1.1
    X-Track: 13522b3badf2432ba4cad0dc7aeaee80
    Content-Type: application/json;charset=UTF-8
    Transfer-Encoding: chunked
    Date: Wed, 19 Feb 2014 05:08:28 GMT
    Connection: close
    
    {"code":"e.ex.fw.5003","message":"Validation error occurred on item in the request parameters.","details":[{"code":"NotEmpty","message":"Please specify a value to the 'name'."}]}

|

JSONエラーが発生した場合、以下のようなエラー応答が行われる。

 .. code-block:: guess
    :emphasize-lines: 1, 9

    HTTP/1.1 400 Bad Request
    Server: Apache-Coyote/1.1
    X-Track: ca4c742a6bfd49e5bc01cd0b124738a1
    Content-Type: application/json;charset=UTF-8
    Transfer-Encoding: chunked
    Date: Wed, 19 Feb 2014 13:32:24 GMT
    Connection: close
    
    {"code":"e.ex.fw.5501","message":"Request body format error occurred."}

|

リソース未検出エラーのハンドリング
""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""
リソースが存在しない場合に、リソース未検出エラーを応答するための実装例について説明する。

| パス変数から取得したIDに一致するリソースが見つからない場合は、リソースが見つからない事を通知する例外を発生させる。
| リソースが見つからなかった事を通知する例外として、共通ライブラリより\ ``org.terasoluna.gfw.common.exception.ResourceNotFoundException``\を用意している。
| 以下に実装例を示す。

パス変数から取得したIDに一致するリソースが見つからない場合は、\ ``ResourceNotFoundException``\を発生させる。

 .. code-block:: java
    :emphasize-lines: 4-5

    public Member getMember(String memberId) {
        Member member = memberRepository.findOne(memberId);
        if (member == null) {
            throw new ResourceNotFoundException(ResultMessages.error().add(
                    "e.ex.fw.6002", memberId));
        }
        return member;
    }

|

エラーハンドリングを行うクラスに、リソースが見つからない事を通知する例外をハンドリングするためのメソッドを作成する。

 .. code-block:: java
    :emphasize-lines: 12-13, 17, 20

    @ControllerAdvice
    public class RestGlobalExceptionHandler extends ResponseEntityExceptionHandler {

        @Inject
        RestErrorCreator restErrorCreator;
    
        @Inject
        ExceptionCodeResolver exceptionCodeResolver;

        // omitted

        // (1)
        @ExceptionHandler(ResourceNotFoundException.class)
        public ResponseEntity<Object> handleResourceNotFoundException(
                ResourceNotFoundException ex, WebRequest request) {
            return handleResultMessagesNotificationException(ex, null,
                    HttpStatus.NOT_FOUND, request);
        }

        // (2)
        protected ResponseEntity<Object> handleResultMessagesNotificationException(
                ResultMessagesNotificationException ex, HttpHeaders headers,
                HttpStatus status, WebRequest request) {
            String code = exceptionCodeResolver.resolveExceptionCode(ex);
            RestError errorBody = restErrorCreator.createResultMessagesRestError(
                    code, ex.getResultMessages(), ex.getMessage(),
                    request.getLocale());
            return handleExceptionInternal(ex, errorBody, headers, status, request);
        }
        
        // omitted
    
    }

 .. list-table::
    :header-rows: 1
    :widths: 10 90

    * - 項番
      - 説明
    * - | (1)
      - | \ ``ResourceNotFoundException``\をハンドリングするためのメソッドを追加する。
        | メソッドアノテーションとして\ ``@ExceptionHandler(ResourceNotFoundException.class)``\を指定すると、\ ``ResourceNotFoundException``\の例外をハンドリングする事ができる。
        | 上記例では、\ ``ResourceNotFoundException``\の親クラス(\ ``ResultMessagesNotificationException``\)の例外をハンドリングするメソッドに処理を委譲している。
        |
        | ステータスコードには\ **404(Not Found)**\を設定し、指定されたリソースがサーバに存在しない事を通知する。
    * - | (2)
      - | \ ``ResourceNotFoundException``\の親クラス(\ ``ResultMessagesNotificationException``\)の例外をハンドリングするための処理を実装する。
        | 上記例では、以降で説明する業務エラーのハンドリング処理と同じ処理となるので、共通メソッドとして本メソッドを作成している。

|

\ ``ResultMessages``\用のエラー情報を保持するJavaBeanを生成するためのメソッドを作成する。

 .. code-block:: java
    :emphasize-lines: 6

    @Component
    public class RestErrorCreator extends ApplicationObjectSupport {

        // omitted

        // (3)
        public RestError createResultMessagesRestError(String errorCode,
                ResultMessages resultMessages, String defaultMessage, Locale locale) {
            RestError restError;
            if (resultMessages.getList().size() == 1) {
                ResultMessage resultMessage = resultMessages.getList().get(0);
                String messageCode = resultMessage.getCode();
                if (messageCode == null) {
                    messageCode = errorCode;
                }
                restError = createRestError(messageCode, resultMessage.getText(),
                        locale, resultMessage.getArgs());
            } else {
                restError = createRestError(errorCode, defaultMessage, locale);
                for (ResultMessage resultMessage : resultMessages.getList()) {
                    restError.addDetail(createRestError(resultMessage.getCode(),
                            resultMessage.getText(), locale,
                            resultMessage.getArgs()));
                }
            }
            return restError;
        }
        
        // omitted

    }

 .. list-table::
    :header-rows: 1
    :widths: 10 90

    * - 項番
      - 説明
    * - | (3)
      - | 処理結果からエラー情報を生成するためのメソッドを作成する。
        | 上記例では、\ ``ResultMessages``\が保持しているメッセージ情報を、エラーの詳細情報に追加している。

|

リソースが見つからない場合、以下のようなエラー応答が行われる。

 .. code-block:: guess
    :emphasize-lines: 1, 8

    HTTP/1.1 404 Not Found
    Server: Apache-Coyote/1.1
    X-Track: 5ee563877f3140fd904d0acf52eba398
    Content-Type: application/json;charset=UTF-8
    Transfer-Encoding: chunked
    Date: Wed, 19 Feb 2014 08:46:18 GMT
    
    {"code":"e.ex.fw.6002","message":"Specified member resource is not found. memberId = M2345"}

|

業務エラーのハンドリング
""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""
ビジネスルールの違反を検知した場合に、業務エラーを応答するための実装例について説明する。

ビジネスルールのチェックはServiceの処理として行い、ビジネスルールの違反を検知した場合は、業務例外を発生させる。
業務エラーの検知方法については、「\ :ref:`service-return-businesserrormessage-label`\」を参照されたい。

エラーハンドリングを行うクラスに、業務例外をハンドリングするためのメソッドを作成する。

 .. code-block:: java
    :emphasize-lines: 6-7, 11

    @ControllerAdvice
    public class RestGlobalExceptionHandler extends ResponseEntityExceptionHandler {
    
        // omitted

        // (1)
        @ExceptionHandler(BusinessException.class)
        public ResponseEntity<Object> handleBusinessException(BusinessException ex,
                WebRequest request) {
            return handleResultMessagesNotificationException(ex, null,
                    HttpStatus.CONFLICT, request);
        }
    
        // omitted
    
    }

 .. list-table::
    :header-rows: 1
    :widths: 10 90

    * - 項番
      - 説明
    * - | (1)
      - | \ ``BusinessException``\をハンドリングするためのメソッドを追加する。
        | メソッドアノテーションとして\ ``@ExceptionHandler(BusinessException.class)``\を指定すると、\ ``BusinessException``\の例外をハンドリングする事ができる。
        | 上記例では、\ ``BusinessException``\の親クラス(\ ``ResultMessagesNotificationException``\)の例外をハンドリングするメソッドに処理を委譲している。
        |
        | ステータスコードには\ **409(Conflict)**\を設定し、クライアントから指定されたリソース自体には不備はないが、サーバで保持しているリソースを操作するための条件が全て整っていない事を通知する。

|

業務エラーが発生した場合、以下のようなエラー応答が行われる。

 .. code-block:: guess
    :emphasize-lines: 1, 8

    HTTP/1.1 409 Conflict
    Server: Apache-Coyote/1.1
    X-Track: 37c1a899d5f74e7a9c24662292837ef7
    Content-Type: application/json;charset=UTF-8
    Transfer-Encoding: chunked
    Date: Wed, 19 Feb 2014 09:03:26 GMT
    
    {"code":"e.ex.mm.8001","message":"Specified Member is already exists."}

|

排他エラーのハンドリング
""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""
| 排他エラーが発生した場合に、排他エラーを応答するための実装例について説明する。
| 排他制御を行う場合は、排他エラーのハンドリングが必要となる。
| 排他制御の詳細については、「\ :doc:`ExclusionControl`\」を参照されたい。

エラーハンドリングを行うクラスに、排他エラーをハンドリングするためのメソッドを作成する。

 .. code-block:: java
    :emphasize-lines: 6-8, 12

    @ControllerAdvice
    public class RestGlobalExceptionHandler extends ResponseEntityExceptionHandler {
    
        // omitted

        // (1)
        @ExceptionHandler({ OptimisticLockingFailureException.class,
                PessimisticLockingFailureException.class })
        public ResponseEntity<Object> handleLockingFailureException(Exception ex,
                WebRequest request) {
            return handleExceptionInternal(ex, null, null,
                    HttpStatus.CONFLICT, request);
        }
    
        // omitted
    
    }

 .. list-table::
    :header-rows: 1
    :widths: 10 90

    * - 項番
      - 説明
    * - | (1)
      - | 排他エラー(\ ``OptimisticLockingFailureException``\と\ ``PessimisticLockingFailureException``\)をハンドリングするためのメソッドを追加する。
        | メソッドアノテーションとして\ ``@ExceptionHandler({ OptimisticLockingFailureException.class, PessimisticLockingFailureException.class })``\を指定すると、排他エラー(\ ``OptimisticLockingFailureException``\と\ ``PessimisticLockingFailureException``\)の例外をハンドリングする事ができる。
        |
        | ステータスコードには\ **409(Conflict)**\を設定し、クライアントから指定されたリソース自体には不備はないが、処理が競合したためリソースを操作するための条件を満たすことが出来なかった事を通知する。

|

排他エラーが発生した場合、以下のようなエラー応答が行われる。

 .. code-block:: guess
    :emphasize-lines: 1, 8

    HTTP/1.1 409 Conflict
    Server: Apache-Coyote/1.1
    X-Track: 85200b5a51be42b29840e482ee35087f
    Content-Type: application/json;charset=UTF-8
    Transfer-Encoding: chunked
    Date: Wed, 19 Feb 2014 16:32:45 GMT
    
    {"code":"e.ex.fw.8002","message":"Because the processing is conflict, has stopped the process. Please run again."}

|

システムエラーのハンドリング
""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""
システム異常を検知した場合に、システムエラーを応答するための実装例について説明する。

システム異常の検知した場合は、システム例外を発生させる。
システムエラーの検知方法については、「\ :ref:`service-return-systemerrormessage-label`\」を参照されたい。

エラーハンドリングを行うクラスに、システム例外をハンドリングするためのメソッドを作成する。

 .. code-block:: java
    :emphasize-lines: 6-7, 11

    @ControllerAdvice
    public class RestGlobalExceptionHandler extends ResponseEntityExceptionHandler {
    
        // omitted

        // (1)
        @ExceptionHandler(Exception.class)
        public ResponseEntity<Object> handleSystemError(Exception ex,
                WebRequest request) {
            return handleExceptionInternal(ex, null, null,
                    HttpStatus.INTERNAL_SERVER_ERROR, request);
        }
    
        // omitted
    
    }

 .. list-table::
    :header-rows: 1
    :widths: 10 90

    * - 項番
      - 説明
    * - | (1)
      - | \ ``Exception``\をハンドリングするためのメソッドを追加する。
        | メソッドアノテーションとして\ ``@ExceptionHandler(Exception.class)``\を指定すると、\ ``Exception``\の例外をハンドリングする事ができる。
        | 上記例では、使用している依存ライブラリから発生するシステム例外もハンドリング対象としている。
        |
        | ステータスコードには\ **500(Internal Server Error)**\を設定する。

|

システムエラーが発生した場合、以下のようなエラー応答が行われる。

 .. code-block:: guess
    :emphasize-lines: 1, 9

    HTTP/1.1 500 Internal Server Error
    Server: Apache-Coyote/1.1
    X-Track: 3625d5a040a744e49b0a9b3763a24e9c
    Content-Type: application/json;charset=UTF-8
    Transfer-Encoding: chunked
    Date: Wed, 19 Feb 2014 12:22:33 GMT
    Connection: close
    
    {"code":"e.ex.fw.9004","message":"System error occurred. Please notify error code to the administrator."}

|

サーブレットコンテナに通知されたエラーのハンドリング
^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
Filterでエラーが発生した場合や\ ``HttpServletResponse#sendError``\を使ってエラーレスポンスが行われた場合は、Spring MVCの例外ハンドリングの仕組みを使ってハンドリングできないため、
これらのエラーはサーブレットコンテナに通知される。

ここでは、サーブレットコンテナに通知されたエラーのハンドリング方法について説明する。

サーブレットコンテナに通知されたエラーは、\ :file:`web.xml`\の\ ``<error-page>``\要素の定義によってハンドリングし、
エラー応答を行うためのControllerを呼び出すようにする。

- :file:`web.xml`

 .. code-block:: xml
    :emphasize-lines: 3, 9

    <!-- omitted -->

    <!-- (1) -->
    <error-page>
        <error-code>404</error-code>
        <location>/error?messageCode=e.ex.fw.6001</location>
    </error-page>

    <!-- (2) -->
    <error-page>
        <exception-type>java.lang.Exception</exception-type>
        <location>/WEB-INF/views/common/error/unhandledSystemError.json</location>
    </error-page>

    <!-- omitted -->

 .. list-table::
    :header-rows: 1
    :widths: 10 90

    * - 項番
      - 説明
    * - | (1)
      - | 必要に応じてレスポンスコードによるエラーページの定義を追加する。
        | 上記例では、\ ``"404 Not Found"``\が発生した際に、「\ ``/error?errorCode=e.ex.fw.6001``\」というリクエストにマッピングされているControllerを呼び出してエラー応答を行っている。
        | Controllerの実装例は、この後説明する。
    * - | (2)
      - | 致命的なエラーをハンドリングするための定義を追加する。
        | 致命的なエラーが発生していた場合、レスポンス情報を作成する処理で二重障害が発生する可能性があるため、予め用意している静的なJSONを応答する事を推奨する。
        | 上記例では、「\ ``/WEB-INF/views/common/error/unhandledSystemError.json``\」に定義されている固定のJSONを応答している。

| 

エラー応答を行うためのControllerを作成する。

- Controller

 .. code-block:: java
    :emphasize-lines: 1-4, 6-7, 9, 13, 15, 18, 21

    // (3)
    @RequestMapping("error") 
    @Controller
    public class RestErrorPageController {

        @Inject
        RestErrorCreator restErrorCreator; // (4)

        // (5)
        @RequestMapping
        @ResponseBody
        public ResponseEntity<RestError> handleErrorPage(
                @RequestParam("errorCode") String errorCode, // (6)
                HttpServletRequest request, Locale locale) {
            // (7)
            HttpStatus httpStatus = HttpStatus.valueOf((Integer) request
                    .getAttribute(RequestDispatcher.ERROR_STATUS_CODE));
            // (8)
            RestError errorBody = restErrorCreator.createRestError(errorCode,
                    httpStatus.getReasonPhrase(), locale);
            // (9)
            return new ResponseEntity<>(errorBody, httpStatus);
        }
    }

 .. list-table::
    :header-rows: 1
    :widths: 10 90

    * - 項番
      - 説明
    * - | (3)
      - | エラー応答を行うためのControllerクラスを作成する。
        | 上記例では、「\ ``/error``\」というサーブレットパスにマッピングしている。
    * - | (4)
      - | エラー情報を作成するクラスをInjectする。
    * - | (5)
      - | エラー応答を行う処理メソッドを作成する。
        | 上記例では、レスポンスコード(\ ``<error-code>``\)を使ってエラーページのハンドリングを行うケースのみを考慮した実装になっている。
        | したがって、例外タイプ(\ ``<exception-type>``\)を使ってハンドリングしたエラーページの処理を本メソッドを使って行う場合は、別途考慮が必要である。
    * - | (6)
      - | エラーコードをリクエストパラメータとして受け取る。
    * - | (7)
      - | リクエストスコープに格納されているステータスコードを取得する。
    * - | (8)
      - | リクエストパラメータで受け取ったエラーコードに対応するエラー情報を生成する。
    * - | (9)
      - | (7)(8)で取得したエラー情報を応答する。

| 

致命的なエラーが発生した際に応答する静的なJSONを作成する。

- :file:`unhandledSystemError.json`

 .. code-block:: json
    
    {"code":"e.ex.fw.9999","message":"Unhandled system error occurred. Please notify error code to the administrator."}

|

存在しないパスへリクエストを送った場合、以下のようなエラー応答が行われる。

 .. code-block:: guess
    :emphasize-lines: 1, 8

    HTTP/1.1 404 Not Found
    Server: Apache-Coyote/1.1
    X-Track: 2ad50fb5ba2441699c91a5b01edef83f
    Content-Type: application/json;charset=UTF-8
    Transfer-Encoding: chunked
    Date: Wed, 19 Feb 2014 23:24:20 GMT
    
    {"code":"e.ex.fw.6001","message":"Specified resource is not found."}

|


致命的なエラーが発生した場合、以下のようなエラー応答が行われる。

 .. code-block:: guess
    :emphasize-lines: 1, 9

    HTTP/1.1 500 Internal Server Error
    Server: Apache-Coyote/1.1
    X-Track: 69db3854a19f439781584321d9ce8336
    Content-Type: application/json
    Content-Length: 115
    Date: Thu, 20 Feb 2014 00:13:43 GMT
    Connection: close
    
    {"code":"e.ex.fw.9999","message":"Unhandled system error occurred. Please notify error code to the administrator."}

|

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

 .. note::
 
    TERASOLUNA Global Framework 1.0.0 を使っている場合は、XXE Injection対策を行うためにSpring-oxmを依存するアーティファクトとして追加する必要がある。
    TERASOLUNA Global Framework 1.0.1 以降のバージョンではSpring-oxmを内包しているため、本設定は不要である。

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

.. _RESTAppendixSettingsOfDeployInSameWarFileRestAndClientApplication:

RESTfull Web Serviceとクライアントアプリケーションを同じWebアプリケーションとして動かす際の設定
^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^

.. _RESTAppendixDivideDispatcherServlet:

RESTfull Web Service用の\ ``DispatcherServlet``\を設ける方法
""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""

| RESTfull Web Service用のリクエストを受ける\ ``DispatcherServlet``\と、クライアントアプリケーション用のリクエストを受け取る\ ``DispatcherServlet``\を分割する方法について、以下に説明する。

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
      - | RESTfull Web Service用のリクエストを受けるServlet(\ ``DispatcherServlet``\)を追加する。
        | \ ``<servlet-name>``\要素に、サーブレットを一意に識別するための名前を指定する。
        | 上記例では、サーブレット名として\ ``"restAppServlet"``\を指定している。
    * - | (3)
      - | RESTfull Web Service用の\ ``DispatcherServlet``\を構築する際に使用するSpring MVCのbean定義ファイルを指定する。
        | 上記例では、Spring MVCのbean定義ファイルとして、クラスパス上にある\ :file:`META-INF/spring/spring-mvc-rest.xml`\を指定している。
    * - | (4)
      - | RESTfull Web Service用の\ ``DispatcherServlet``\へマッピングするサーブレットパスのパターンの指定を行う。
        | 上記例では、\ ``"/rest"``\又は\ ``"/rest/"``\配下のサーブレットパスをRESTfull Web Service用の\ ``DispatcherServlet``\にマッピングしている。
        | 具体的には、
        |   \ ``"/rest"``\
        |   \ ``"/rest/"``\
        |   \ ``"/rest/members"``\
        |   \ ``"/rest/members/xxxxx"``\
        | といったサーブレットパスが、RESTfull Web Service用の\ ``DispatcherServlet``\(\ ``"restAppServlet"``\)にマッピングされる。

 .. tip:: **サーブレットを分割した際の@RequestMappingアノテーションのvalue属性に指定する値について**

    サーブレットを分割した場合、\ ``@RequestMapping``\アノテーションのvalue属性に指定する値は、\ ``<url-pattern>``\要素で指定したワイルドカード(\ ``*``\)の部分の値を指定する。

    例えば、\ ``@RequestMapping(value = "members")``\と指定した場合、\ ``"/rest/members"``\といパスに対する処理を行うメソッドとしてデプロイされる。
    そのため、\ ``@RequestMapping``\アノテーションのvalue属性には、分割したサーブレットへマッピングするためパス(\ ``"rest"``\)を指定する必要はない。

    \ ``@RequestMapping(value = "rest/members")``\と指定すると、\ ``"/rest/rest/members"``\というパスに対する処理を行うメソッドとしてデプロイされてしまうので、注意すること。

|

.. _RESTAppendixUtilityOfOptionsMethod:

OPTIONSメソッドで実装する処理のユーティリティメソッド化について
^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
| URIで指定されたリソースがサポートしているHTTPメソッド(API)のリストを応答する処理は、全てのリソースで同じようなロジックとなるため、ユーティリティメソッド化した方がよい。
| ユーティリティメソッド化することで、Controllerの実装が非常にシンプルになる。
| 以下にユーティリティメソッドのサンプルを示す。

- ユーティリティメソッド

 .. code-block:: java

    public static ResponseEntity<Void> createEntityOfOptions(
            HttpMethod... allowedMethods) {

        Set<HttpMethod> allowedMethodSet = new LinkedHashSet<>(
                Arrays.asList(allowedMethods));
        if (!allowedMethodSet.contains(HttpMethod.OPTIONS)) {
            allowedMethodSet.add(HttpMethod.OPTIONS);
        }

        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.setAllow(allowedMethodSet);

        return new ResponseEntity<>(responseHeaders, HttpStatus.OK);
    }

- Controller

 .. code-block:: java

    @RequestMapping(method = RequestMethod.OPTIONS)
    @ResponseBody
    public ResponseEntity<Void> optionsMembers() {
        return RestResponseUtils.createEntityOfOptions(
            HttpMethod.GET, HttpMethod.HEAD, HttpMethod.POST);
    }

|

.. _RESTAppendixSettingsOfExceptionCodeResolver:

ExceptionCodeResolverを使ったエラーコードとメッセージの解決
^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
共通ライブラリより提供している\ ``ExceptionCodeResolver``\を使用して、例外クラスからエラーコードを解決するための設定例を以下に示す。

- | :file:`applicationContext.xml`
  | 例外クラスとエラーコード(例外コード)のマッピングを行う。

 .. code-block:: xml
    
    <!-- omitted -->

    <bean id="exceptionCodeResolver"
        class="org.terasoluna.gfw.common.exception.SimpleMappingExceptionCodeResolver">
        <property name="exceptionMappings">
            <map>
                <!-- omitted -->
                <entry key="MethodArgumentNotValidException" value="e.ex.fw.5002" />
                <entry key="BindException" value="e.ex.fw.5003" />
                <entry key="HttpRequestMethodNotSupportedException" value="e.ex.fw.5004" />
                <entry key="MediaTypeNotAcceptableException" value="e.ex.fw.5005" />
                <entry key="HttpMediaTypeNotSupportedException" value="e.ex.fw.5006" />
                <entry key="JsonParseException" value="e.ex.fw.5501" />
                <entry key="UnrecognizedPropertyException" value="e.ex.fw.5502" />
                <entry key="JsonMappingException" value="e.ex.fw.5503" />
                <entry key="ResourceNotFoundException" value="e.ex.fw.6001" />
                <entry key="BusinessException" value="e.ex.fw.8001" />
                <entry key="OptimisticLockingFailureException" value="e.ex.fw.8002" />
                <entry key="PessimisticLockingFailureException" value="e.ex.fw.8002" />
                <!-- omitted -->
            </map>
        </property>
        <property name="defaultExceptionCode" value="e.ex.fw.9001" />
    </bean>

    <!-- omitted -->

|

| エラーコードに対応するメッセージの設定例を以下に示す。
| メッセージの管理方法については、「\ :doc:`MessageManagement`\」を参照されたい。

- | :file:`application-messages.properties`
  | エラーコード(例外コード)に対応するメッセージの設定を行う。

 .. code-block:: properties

    # ommited

    e.ex.fw.5002 = Validation error occurred on item in the request body.
    e.ex.fw.5003 = Validation error occurred on item in the request parameters.
    e.ex.fw.5004 = Request method is not supported.
    e.ex.fw.5005 = Specified representation format is unsupported.
    e.ex.fw.5006 = Specified media type in the request body is unsupported.
    e.ex.fw.5501 = Request body format error occurred.
    e.ex.fw.5502 = Unknown field is specified.
    e.ex.fw.5503 = Type mismatch error occurred.

    e.ex.fw.6001 = Specified resource is not found.
    e.ex.fw.6002 = Specified member resource is not found. memberId = {0}

    e.ex.fw.8001 = Business error occurred.
    e.ex.fw.8002 = Because the processing is conflict, has stopped the process. Please run again.

    e.ex.fw.9001 = System error occurred. Please notify error code to the administrator.
    e.ex.fw.9004 = System error occurred. Please notify error code to the administrator.

    # ommited

    NotEmpty = Please specify a value to the ''{0}''.
    Size = Please specify a valid size of collections (or length of string) to the ''{0}''. Allowed range of size (or length) is {2}-{1}.
    ExistInCodeList.gender=Please specify a valid gender code to the ''{0}''. Allowed gender code is [MAN, WOMAN, UNKOWN].

    # ommited

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

