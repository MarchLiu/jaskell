# English
...

# 中文版

## 安装和使用

## 主要功能

### Parsec 规则引擎

### SQL/JDBC 支持

编写一个轻量级的 SQL 辅助工具来简化 JDBC 使用，最初源自我去年与同事开发做市策略时的经验。在不依赖
IOC 框架或 ORM 的情况下，JDBC 使用起来太不方便了，这给我们带了了很大的麻烦。

今年暑假我再次遇到这个问题时，决心按自己喜欢的方式制造一个工具。我对这个工具的期待是：

 - 用宿主语言的语法来写 SQL，对 IDE 或代码编辑器的代码完成/提示尽可能友好
 - 代码的阅读体验应该尽可能接近 SQL，对 IDE 或代码编辑器的高亮尽可能友好
 - 这个库不应该依赖任何 ioc 或预编译工具，它仅仅是个字符串魔法就足够了
 - 因为仅仅是字符串魔法，它不应该局限于某一个数据库，而是把合法性问题作为可选项，或教给调用者自己
 - JDBC 对 SQL 参数的支持实在太简陋了，要解决这个问题
 - 支持 Java 和 Clojure
 - 适当支持一些便捷功能
 - 尽可能类型安全

为了在有限的时间内实现一个方便使用的工具类，这组工具并没有经过很精巧的设计。其依赖关系和类型约束都
不是很严谨，有写地方很混乱。总的来说，应遵循其使用方法，而不是设计和实现。它的目标是提供辅助而不是
完整的功能，所以在一定程度上还需要依赖使用者对 SQL 的熟练程度。

我是一个有十八年 SQL 经验的程序员，对于各种关系型数据库的 SQL 语法，特别是语言功能比较强的数据库
实现（例如 PostgreSQL 或 MSSQL）有相当的自信。jaskell.sql 对我这个夏天开发工作带来了很大的便
利和快乐。希望它也能对你有所帮助。

#### Java 语言中的 jaskell.sql

首先定义了两个规范：

 - jaskell.script.Directive 接口定义了可解析对象，它应该支持： 
  - script 方法，给出脚本结果
  - parameters 方法，给出内含的参数列表
 - jaskell.script.Parameter 类定义了默认的参数类型，它：
  - 要求调用者提供占位符，这个占位符会作为 Parameter 对象 script 方法的返回值。
  - Parameter 对象的 parameters 方法返回包含自身的一个单元素 List。
  - Parameter 可以通过设定类型的方式，对参数值做类型安全的读写。

这两个定义在 jaskell.script 包，因为我希望将来它们在更广泛的领域发挥作用。

然后是为 JDBC 提供的 Statement 和 Query 类型。Statement 类型提供了 
prepare、execute、setParameter 等功能，而 Query 进一步提供了 query 方法。
这些方法封装并简化了 JDBC 的使用定式。

我很喜欢 ADO.net 中 Commander 对象的 scalar 方法，所以在 Query 类型中也提供了
scalar 。它返回结果集第一行第一列的值。如果我们仅需要查询一个值，例如 
`select max(id) from table` 这种，scalar 就很方便了。它根据传入的 class 给出
对应类型的 Optional 封装。

接下来是 Insert、 Delete、Update 和 Select 类型，它们构造了对应语句的起始片段，
通过调用成员方法，就可以逐步生成完整的语句。当一组语法元素足够组成一个合法的执行语
句时，我们得到的会是一个 Statement 对象；当这些语法元素足够组成一个合法的查询时，
我们会得到一个 Query。

为了使用方便，我开发了 jaskell.sql.SQL 类型，它提供了一组静态函数，来封装对应的
对象构造，例如 select/insert/update/delete  方法。

#### Clojure 语言中的 jaskell.sql

Clojure 语言中的 jaskell.sql 支持比较简单，它尽量利用java 的资源，封装了自己的
 select、update、delete、insert 函数，返回对应的 Query 或 Statement。parse
函数是主要的内部逻辑，它遵循如下规则：
 
 - Keyword 类型返回其 name
 - 字符串类型返回其本身
 - Directive 对象返回其 script
 - 在这个项目中，我们将需要逗号连接的一系列元素统称为一组元素，那么：
  - Vector 被当作一组元素解析
  - `(in ...)` 会被解析为 `in (...)`，其参数被解析为一组
  - `(into :t [...])` 会被解析为 `into t(...)`，其参数被解析为一组
  - `(values ...)` 会被解析为 `values(...)`，其参数被解析为一组
  - 组中 as （等同于 :as) 及其左右的项被视为一个定义了别名的元素，
  `[:a :b as :yet :c]` 会被解析为 `a, b as yet, c` 
  - union 和 union all 连接的序列按照与 as 等同的规则解析，例如 
  `[(select 1) union (select 2)]` 会被解析为 `select 1 union select 2`
 
然后，我们将几乎所有其它元素都定义成 keyword ，就可以享受 Clojure 的便捷语法了，
几乎 SQL 就是形如 `(select [:a :b :c] from :table where :id := :v)` 这样的形式。
几个例外的工具函数包含：

 - p 函数表示 parameter 构造一个 JDBCParameter 对象
 - t 函数表示 text ，会解析为一段单引号包含的文本，注意这个函数会将文本中的单引号转义
 - q 函数表示 quot ，会将内容用双引号包含起来。注意这个函数会将文本中的双引号转义
 - br 函数表示 brackets ，会构造一个 Directive ，其 script 返回的是括号包围的参数解析结果，
 需要注意的是，如果br的第一个参数是 select/insert/delete/update 中的任一个，br会将参数序列
 视作一个子查询来构造，并且为这个子查询加上括号
 - br 函数表示 brackets ，会构造一个 Directive ，其 script 返回的是括号包围的，用逗号连接
 的参数解析结果，需要注意的是，如果br的第一个参数是 select/insert/delete/update 中的任一个，
 in 会将参数序列视作一个子查询来构造，并且为这个子查询加上括号，这是因为，in 的右边总是一个元素
 可比较的集合，它要么是一个子查询，要么是用逗号列举的。
 - f 函数表示 function ，返回函数调用
 - select 函数返回一个 Query ，将参数拼装成一条 select 查询
 - insert/delete/update 返回对应的 Statement，需要指出的是，如果语句的末尾是 
 `returning [...]`，得到的是一个 Query 对象
 - `(with [name0 as query0 name1 as query1...] statement)` 得到对应的 CTE 查询，递归
 查询则是 `(with recursive [name0 as query0 name1 as query1...] statement)`

### Clojure 支持

## 待解决的问题

## 更新

### 0.0.1 

Jaskell SQL 支持 `select where` 形式。 