# agent-langchain4j

> 基于 Spring Boot 3 + LangChain4j 的智能医疗交流与预约挂号系统后端项目。

本项目以“智能医疗问答 + 预约挂号 Agent”为核心场景，接入大语言模型、流式输出、对话记忆、RAG 知识检索、工具调用、用户登录认证、MySQL 业务数据存储和 MongoDB 对话记忆存储，适合作为学习 LangChain4j Agent 开发、医疗问答系统后端设计和 RAG 应用落地的参考项目。

---

## 目录

* [项目简介](#项目简介)
* [功能特性](#功能特性)
* [技术栈](#技术栈)
* [系统架构](#系统架构)
* [项目结构](#项目结构)
* [环境要求](#环境要求)
* [快速开始](#快速开始)
* [配置说明](#配置说明)
* [接口说明](#接口说明)
* [测试说明](#测试说明)
* [安全建议](#安全建议)
* [后续规划](#后续规划)
* [贡献指南](#贡献指南)
* [许可证](#许可证)

---

## 项目简介

`agent-langchain4j` 是一个面向智能医疗场景的 Java 后端项目。系统通过 LangChain4j 封装 AI Agent，使大模型不仅能够进行普通问答，还可以结合医院知识库、科室信息、医生信息和预约工具完成更贴近业务的智能交互。

典型使用场景包括：

* 用户咨询医院、科室、医生、疾病相关问题；
* 系统基于 RAG 从知识库中检索相关内容，辅助大模型生成回答；
* 用户表达挂号需求后，Agent 自动调用预约工具查询号源、确认信息并创建预约；
* 系统基于 `memoryId` 保存多轮对话上下文，实现连续对话体验；
* 通过流式接口逐步返回回答内容，提升交互体验。

---

## 功能特性

### 1. 智能医疗问答

* 支持接入通义千问、DashScope 兼容 OpenAI 协议模型、Ollama 本地模型等；
* 支持普通问答、多轮对话和医疗咨询场景提示词；
* 通过系统提示词约束智能体角色，使其围绕医疗交流和挂号业务进行回答。

### 2. RAG 检索增强生成

* 支持将医院信息、科室信息、医生信息等知识文档向量化；
* 使用 Embedding 模型生成文本向量；
* 支持通过向量数据库进行相关内容检索；
* 大模型回答前先检索知识库，提高回答的专业性和可解释性。

### 3. 多轮对话记忆

* 通过 `memoryId` 区分不同用户或不同会话；
* 使用 MongoDB 持久化存储聊天记忆；
* 支持窗口式对话记忆，控制上下文长度，避免无限膨胀。

### 4. Agent 工具调用

系统为智能体提供业务工具，使大模型可以根据用户意图调用后端方法：

* 查询是否有号源；
* 预约挂号；
* 取消预约挂号；
* 简单计算工具。

### 5. 流式输出

* 使用 Spring WebFlux 和 Reactor `Flux` 返回流式文本；
* 支持类似打字机效果的逐步输出；
* 适合前端聊天窗口实时展示回答内容。

### 6. 用户与权限基础能力

* 支持用户登录接口；
* 支持 JWT Token 生成与校验；
* 支持接口拦截器进行访问控制；
* 支持用户信息查询和管理相关接口。

### 7. 接口文档

* 集成 Knife4j / OpenAPI；
* 启动后可通过接口文档页面查看和调试接口。

---

## 技术栈

| 分类        | 技术                                               |
| --------- | ------------------------------------------------ |
| 后端框架      | Spring Boot 3.2.6                                |
| AI 开发框架   | LangChain4j 1.0.0-beta3                          |
| 大模型接入     | DashScope / OpenAI Compatible API / Ollama       |
| 流式响应      | Spring WebFlux、Reactor                           |
| RAG 检索    | Embedding、ContentRetriever、Pinecone Vector Store |
| 数据库       | MySQL、MongoDB                                    |
| ORM / 持久层 | MyBatis、MyBatis-Plus                             |
| 接口文档      | Knife4j OpenAPI3                                 |
| 认证鉴权      | JWT、拦截器                                          |
| 构建工具      | Maven                                            |
| 开发语言      | Java 17                                          |

---

## 系统架构

```text
用户 / 前端页面
    |
    | HTTP / SSE Stream
    v
Spring Boot Controller
    |
    | 1. 接收用户输入
    | 2. 生成个性化 Prompt
    | 3. 调用 LangChain4j Agent
    v
LangChain4j XiaoZhiAgent
    |
    |-- Chat Model / Streaming Chat Model
    |-- Chat Memory Provider
    |-- Content Retriever
    |-- Tool Calling
    |
    |-----------------------------|
    |                             |
    v                             v
MongoDB 对话记忆              Pinecone / 向量库
                                  |
                                  v
                             医疗知识库文档
    |
    v
MySQL 业务数据库
    |
    |-- 用户信息
    |-- 预约挂号记录
    |-- 科室 / 医生相关业务数据
```

核心流程：

1. 前端调用 `/xiaozhi/chat` 接口，并传入用户消息与 `memoryId`；
2. 后端通过 `PromptService` 生成面向医疗场景的提示词；
3. `XiaoZhiAgent` 接收用户消息，结合系统提示词、历史记忆和 RAG 检索结果生成回复；
4. 当用户表达预约、取消预约、查询号源等意图时，Agent 自动调用对应工具方法；
5. 系统将回答以流式文本返回给前端。

---

## 项目结构

```text
agent-langchain4j
├── src
│   ├── main
│   │   ├── java/com/cqupt/java/ai/langchain4j
│   │   │   ├── assistant          # LangChain4j Assistant / Agent 接口
│   │   │   ├── bean               # 请求参数、返回对象等 Bean
│   │   │   ├── config             # 模型、记忆、RAG、Web 配置
│   │   │   ├── context            # 上下文相关工具类
│   │   │   ├── controller         # 控制器接口层
│   │   │   ├── entity             # 实体类
│   │   │   ├── jwt                # JWT 工具类
│   │   │   ├── mapper             # MyBatis Mapper
│   │   │   ├── service            # 业务服务接口与实现
│   │   │   ├── store              # MongoDB 聊天记忆存储
│   │   │   ├── tokenInterceptor   # Token 拦截器
│   │   │   ├── tools              # Agent 可调用工具
│   │   │   └── XiaozhiApp.java    # Spring Boot 启动类
│   │   └── resources
│   │       ├── mapper             # MyBatis XML 映射文件
│   │       ├── application.properties
│   │       ├── my-prompt-template.txt
│   │       ├── my-prompt-template3.txt
│   │       └── xiaozhi-prompt-template.txt
│   └── test/java/com/cqupt/java/ai/langchain4j
│       ├── AIServiceTest.java
│       ├── AppointmentServiceTest.java
│       ├── ChatMemoryTest.java
│       ├── EmbeddingTest.java
│       ├── InterceptorTest.java
│       ├── JWTTest.java
│       ├── LLMTest.java
│       ├── MongoCrudTest.java
│       ├── PromptTest.java
│       ├── RAGTest.java
│       └── ToolsTest.java
├── pom.xml
├── README.md
├── README.en.md
└── LICENSE
```

---

## 环境要求

请先准备以下环境：

* JDK 17+
* Maven 3.8+
* MySQL 8.x 或兼容版本
* MongoDB 6.x 或兼容版本
* Pinecone 账号与 API Key，或替换为其他向量数据库
* DashScope / OpenAI Compatible API Key，或本地 Ollama 模型

可选环境：

* Ollama：用于本地大模型测试；
* Knife4j：项目启动后自动提供接口调试页面；
* Postman / Apifox：用于接口调试。

---

## 快速开始

### 1. 克隆项目

```bash
git clone https://github.com/JasperChenJH/agent-langchain4j.git
cd agent-langchain4j
```

### 2. 初始化数据库

创建 MySQL 数据库：

```sql
CREATE DATABASE guiguxiaozhi DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
```

> 说明：当前仓库未提供完整 SQL 初始化脚本时，需要根据 `entity`、`mapper` 和业务字段自行创建用户表、预约表等数据表。

启动 MongoDB：

```bash
mongod --dbpath /path/to/mongodb/data
```

默认使用的对话记忆数据库名称：

```text
chat_memory_db
```

### 3. 修改配置

建议不要直接在 `application.properties` 中写明文密钥。可以使用环境变量方式配置：

```properties
server.port=8080

# DashScope / OpenAI Compatible API
langchain4j.open-ai.chat-model.base-url=https://dashscope.aliyuncs.com/compatible-mode/v1
langchain4j.open-ai.chat-model.api-key=${DASHSCOPE_API_KEY}

# DashScope Chat Model
langchain4j.community.dashscope.chat-model.api-key=${DASHSCOPE_API_KEY}
langchain4j.community.dashscope.chat-model.model-name=qwen-max

# DashScope Embedding Model
langchain4j.community.dashscope.embedding-model.api-key=${DASHSCOPE_API_KEY}
langchain4j.community.dashscope.embedding-model.model-name=text-embedding-v3

# DashScope Streaming Chat Model
langchain4j.community.dashscope.streaming-chat-model.api-key=${DASHSCOPE_API_KEY}
langchain4j.community.dashscope.streaming-chat-model.model-name=qwen-plus

# Ollama，可选
langchain4j.ollama.chat-model.base-url=http://localhost:11434
langchain4j.ollama.chat-model.model-name=deepseek-r1:1.5b
langchain4j.ollama.chat-model.timeout=PT60S
langchain4j.ollama.chat-model.temperature=0.8

# MongoDB
spring.data.mongodb.uri=mongodb://localhost:27017/chat_memory_db

# MySQL
spring.datasource.url=jdbc:mysql://localhost:3306/guiguxiaozhi?useUnicode=true&characterEncoding=UTF-8&serverTimezone=Asia/Shanghai&useSSL=false
spring.datasource.username=${MYSQL_USERNAME}
spring.datasource.password=${MYSQL_PASSWORD}
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

# MyBatis / MyBatis-Plus
mybatis-plus.configuration.log-impl=org.apache.ibatis.logging.stdout.StdOutImpl
mybatis.configuration.log-impl=org.apache.ibatis.logging.stdout.StdOutImpl
mybatis.configuration.map-underscore-to-camel-case=true
```

在系统环境变量中配置：

```bash
export DASHSCOPE_API_KEY="你的 DashScope API Key"
export MYSQL_USERNAME="root"
export MYSQL_PASSWORD="你的 MySQL 密码"
export PINECONE_API_KEY="你的 Pinecone API Key"
```

Windows PowerShell 示例：

```powershell
$env:DASHSCOPE_API_KEY="你的 DashScope API Key"
$env:MYSQL_USERNAME="root"
$env:MYSQL_PASSWORD="你的 MySQL 密码"
$env:PINECONE_API_KEY="你的 Pinecone API Key"
```

### 4. 修改 Pinecone 配置

建议将 `EmbeddingStoreConfig` 中的 Pinecone API Key 改为环境变量读取，例如：

```java
@Value("${pinecone.api-key}")
private String pineconeApiKey;
```

并在配置文件中添加：

```properties
pinecone.api-key=${PINECONE_API_KEY}
pinecone.index=xiaozhi-index
pinecone.namespace=xiaozhi-namespace
pinecone.cloud=AWS
pinecone.region=us-east-1
```

### 5. 编译项目

```bash
mvn clean package
```

### 6. 启动项目

```bash
mvn spring-boot:run
```

或者运行打包后的 jar：

```bash
java -jar target/java-ai-langchain4j-1.0-SNAPSHOT.jar
```

项目默认端口：

```text
http://localhost:8080
```

---

## 配置说明

### 大模型配置

项目中预留了多种模型接入方式：

| 配置类型                                                     | 用途                 |
| -------------------------------------------------------- | ------------------ |
| `langchain4j.open-ai.chat-model.*`                       | 兼容 OpenAI 协议的大模型接口 |
| `langchain4j.community.dashscope.chat-model.*`           | DashScope 普通对话模型   |
| `langchain4j.community.dashscope.streaming-chat-model.*` | DashScope 流式对话模型   |
| `langchain4j.community.dashscope.embedding-model.*`      | DashScope 文本向量模型   |
| `langchain4j.ollama.chat-model.*`                        | 本地 Ollama 模型       |

### 对话记忆配置

系统使用 MongoDB 保存聊天记忆。每个用户或会话通过 `memoryId` 区分，便于实现多轮连续对话。

### RAG 配置

系统通过 `ContentRetriever` 进行知识库检索。当前设计中，RAG 流程大致为：

```text
用户问题 -> Embedding Model -> 向量数据库检索 -> 返回相关知识片段 -> 大模型生成回答
```

### 工具调用配置

Agent 中注册了工具类，支持在对话过程中自动触发业务方法。例如：

* 用户说“我想预约明天上午神经内科”，系统可以调用预约工具；
* 用户说“取消我明天的预约”，系统可以调用取消预约工具；
* 用户询问是否有号源，系统可以调用号源查询工具。

---

## 接口说明

### 1. 智能对话接口

```http
POST /xiaozhi/chat
Content-Type: application/json
Accept: text/stream
```

请求示例：

```json
{
  "memoryId": 10001,
  "message": "我想预约明天上午神经内科的号"
}
```

cURL 示例：

```bash
curl -N -X POST "http://localhost:8080/xiaozhi/chat" \
  -H "Content-Type: application/json" \
  -d '{"memoryId":10001,"message":"我想预约明天上午神经内科的号"}'
```

返回说明：

* 接口返回流式文本；
* 客户端可以逐步接收模型生成内容；
* 前端可以实现打字机式展示效果。

### 2. 登录接口

项目包含登录控制器和 JWT 相关逻辑，具体请求参数可根据 `LoginController` 和用户实体类查看。

### 3. 用户接口

项目包含用户控制器，可用于用户信息相关操作，具体接口路径和参数可查看 `UserController`。

### 4. 接口文档地址

项目启动后可尝试访问：

```text
http://localhost:8080/doc.html
```

或：

```text
http://localhost:8080/swagger-ui/index.html
```

具体地址取决于 Knife4j / OpenAPI 配置。

---

## 测试说明

测试目录中包含以下测试类：

| 测试类                      | 作用                |
| ------------------------ | ----------------- |
| `LLMTest`                | 大模型调用测试           |
| `AIServiceTest`          | AI Service 基础能力测试 |
| `ChatMemoryTest`         | 对话记忆测试            |
| `RAGTest`                | RAG 检索增强测试        |
| `EmbeddingTest`          | 文本向量化测试           |
| `ToolsTest`              | 工具调用测试            |
| `AppointmentServiceTest` | 预约业务测试            |
| `MongoCrudTest`          | MongoDB 增删改查测试    |
| `JWTTest`                | JWT 生成与校验测试       |
| `InterceptorTest`        | 拦截器测试             |
| `PromptTest`             | 提示词生成测试           |

运行测试：

```bash
mvn test
```

指定单个测试类：

```bash
mvn -Dtest=RAGTest test
```

---

## 安全建议

请注意：生产环境中不要将以下敏感信息直接提交到 Git 仓库：

* 大模型 API Key；
* DashScope API Key；
* Pinecone API Key；
* MySQL 用户名和密码；
* JWT 密钥；
* 服务器地址、数据库地址等敏感配置。

建议做法：

1. 将所有密钥改为环境变量读取；
2. 将真实配置放入 `application-local.properties`，并加入 `.gitignore`；
3. 提供 `application-example.properties` 作为配置模板；
4. 如果密钥已经提交到公开仓库，应立即在对应平台重新生成密钥并吊销旧密钥；
5. 使用 GitHub Secret Scanning 或其他工具检查仓库中的敏感信息。

推荐的 `.gitignore` 补充内容：

```gitignore
# local config
application-local.properties
application-dev.properties
*.env
.env

# IDE
.idea/
*.iml

# build output
target/

# logs
logs/
*.log
```

---

## 后续规划

* [ ] 补充数据库初始化 SQL；
* [ ] 将所有密钥改为环境变量配置；
* [ ] 增加统一异常处理；
* [ ] 增加统一返回结果对象；
* [ ] 完善用户注册、登录、权限控制；
* [ ] 完善医生排班和号源管理逻辑；
* [ ] 将 RAG 知识库导入流程做成独立接口或管理脚本；
* [ ] 增加 Docker Compose，一键启动 MySQL、MongoDB 和后端服务；
* [ ] 增加前端聊天页面示例；
* [ ] 完善接口文档和部署文档。

---

## 贡献指南

欢迎提交 Issue 或 Pull Request 改进项目。

参与方式：

1. Fork 本仓库；
2. 新建功能分支：

```bash
git checkout -b feat/your-feature-name
```

3. 提交代码：

```bash
git add .
git commit -m "feat: add your feature"
```

4. 推送分支：

```bash
git push origin feat/your-feature-name
```

5. 创建 Pull Request。

---

## 许可证

本项目基于 GPL-3.0 License 开源，详情请查看 [LICENSE](./LICENSE)。

---

## 致谢

本项目使用或参考了以下开源技术：

* Spring Boot
* LangChain4j
* MyBatis-Plus
* MongoDB
* Pinecone
* Knife4j
* Reactor / WebFlux
