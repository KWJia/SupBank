1.项目描述
- 
SupBank是一家在线欧洲银行公司，专注于存款账户，透支，信贷和投资产品。 与所有现有银行一样，SupBank面临国际和欧洲转账的国际SWIFT和SEPA支付系统的限制。
为了满足客户的需求，SupBank决定创建自己的系统来存储和传输银行交易信息。 这个新系统将具有以下功能：
•即时（接近）电线

•免收外汇

•分散化

•防篡改和安全

•用户无缝过渡

•完整的交易记录

在启动过程中，第一个可用的操作将是银行客户之间的转移。 我们选择了一套分包商，包括您，以开发最好的区块链概念证明。


2.功能表达
- 
   第一个PoC的主要目标是允许用户之间的交易。用户可以通过他们的钱包应用程序发送资金。一旦用户验证了交易，交易将被转换为令牌，然后在区块链的分散网络上提交以进行验证。
交易由以下人员组成：发件人公共地址，公共接收者
•发件人公共地址
•接收方公共地址（一个或多个）
•交易金额
区块链网络的每个节点都必须通过加密算法验证（或不验证）事务。每个事务都有时间戳，并在验证后添加到区块链中。
每个节点都必须有区块链的副本。只要区块链始终可用且分散，您就可以自由使用任何合适的存储引擎（数据库，文件......）。

2.1 软件开发

该软件分为3个主要部分：
•区块链引擎，用于验证和存储每个事务
•钱包应用程序，允许用户发送/接收交易和存储钱。
•允许用户浏览区块链的Web应用程序

2.1.1  Blockchain

您可以自由使用任何语言和库以及现有解决方案。您实施/选择的解决方案必须符合以下条件：

2.1.1.1 令牌

为避免货币之间的直接转换，您需要一个将在所有交易中使用的代币/货币。您可以自由选择令牌的价格/价值。

2.1.1.2 点对点网络

对等区块链网络防止任何参与者或参与者组控制底层基础设施或破坏整个系统。网络中的参与者都是平等的，并遵守相同的协议。
参与者或参与者组是节点。每当发生新事务时，网络中所有想要维护最新版本区块链的节点都必须通过添加新事务来更新其区块链副本。

2.1.1.3 区块链安全

解决方案必须防止欺诈和伪造。区块链必须是不可变的（在验证事务后没有删除/修改）。

2.1.1.4 共识机制

共识机制是区块链设计中最重要和最重要的方面。用户之间信任的基础是区块链上存储的数据不能被篡改。因此，它必须具有以下功能：
•高性能（低延迟，每秒大量事务）
•低功耗
•交易不可撤销

2.1.1.5 隐私

区块链必须是“私人设计”以尊重用户隐私。您必须实施“假名”解决方案，以确保所有数据都保存在不允许直接识别用户的形式中。

2.1.1.6 有用的资源

以下是可用于设计/选择系统的非限制性资源列表：
•https://www.youtube.com/watch?v=SccvFbyDaUI
•https://www.youtube.com/watch?v=SSo_EIwHSd4
•https://www.youtube.com/watch?v=149jOJk30eQs
•https://www.youtube.com/watch?v=Lx9zgZCMqXE
•https://bitcoin.org/en/developer-guide

2.1.2 钱包

钱包应用程序使用户能够与区块链引擎交互。它会让用户：
•创建帐户
•发送/接收交易
•查看他们目前有多少令牌
您可以自由使用您想要的任何语言/库/平台。对于POC，它可以是命令行工具或完整的桌面/移动应用程序。

2.1.3  Web应用程序

  Web应用程序允许用户以图形方式浏览区块链的内容。
区块链资源管理器的典型用例是检查给定地址/ pubkey的余额或验证区块链中是否包含给定的事务。

2.1.3.1 用户帐户

用户需要一个帐户才能使用Web应用程序功能。如果他们已经拥有Facebook或Google帐户，他们可以自动将其链接到他们的SupBank帐户。如果他们不这样做，他们可以使用他们的电子邮件地址创建帐户。

2.1.3.2  Web界面

主页显示最后的事务块以及已由不同节点验证的最后事务。
用户可以单击事务以获取详细信息（发件人/收件人/金额）。
用户还可以使用搜索栏按以下方式过滤交易：
•钱包地址（公钥）
•节点
•块标识符
状态页面显示网络的实时地图，包括所有已知节点。

2.2 支持architecure

您的POC必须包含具有多个节点的演示网络。网络必须能够执行所有必需的任务以确保正确的区块链操作。

3.可交付成果
- 
学生应在最终交付中包含以下内容：
  •包含项目源代码的zip存档。 源代码还必须附带使用的构建系统（项目文件，autotools，库，...），如果有的话。

•项目文件。

•技术文档，解释您对以下项目的选择和/或实施选择/细节（至少）：

•选择区块链解决方案

•网络地图

• 用户手册

第一份文件是一份学术文件。 将读者作为教师而不是客户来解决。 本文档可以是法语或英语，供您选择。 另一方面，客户必须能够理解用户手册。