# 校园二手教材流转平台

一个面向校园的二手教材发布 / 浏览 / 私信场景的全栈练手项目。**目标：本学期末考核——前后端联调 + 成功部署 + 能讲清实现过程。**

> ⚠️ 这是 AI 辅助生成的骨架。学长/考核要求：**必须理解核心代码、能独立修改和排错**。下面「核心代码讲解」一节请逐行看懂，不要只跟着跑起来。

---

## 一、技术栈

| 层 | 技术 | 作用 |
|---|---|---|
| 前端 | Vue 3 + Vite + Pinia + Vue Router + Axios | 页面、状态、路由、发请求 |
| 后端 | Spring Boot 3 + Spring Security + JWT | 接口、安全、登录鉴权 |
| 数据 | Spring Data JPA + H2（开发）/ MySQL（部署） | 数据库交互 |
| 部署 | 云服务器 / Docker | 公网可访问 |

---

## 二、环境准备（一次性）

- **JDK 17**（已为你装到 `C:\Users\ASUS\tools\jdk17.0.21_0`）
- **Maven 3.9.9**（已装到 `C:\Users\ASUS\tools\apache-maven-3.9.9`）
- **Node.js 22**（本机已有）

> 如果你在普通 Windows 终端（CMD / PowerShell）里操作，把 Maven 的 `bin` 加到 PATH 后用 `mvn` 即可。
> 如果在 Git Bash 里跑，Maven 启动脚本路径探测会失败；本项目已生成 **Maven Wrapper（`mvnw`/`mvnw.cmd`）**，直接用 `./mvnw` 即可，无需手动配 Maven。

---

## 三、如何运行

### 1. 启动后端（端口 8080）

本机已为你装好 JDK17 + Maven（在 `C:\Users\ASUS\tools`），直接一键启动：

- **Windows 用户**：双击 `backend/run.cmd` 即可。
- **Git Bash / 终端用户**：`cd backend && bash run.sh`

> 若你之后在别的机器上跑，需要先装 JDK17 + Maven，然后用 `mvn spring-boot:run`（`backend` 目录下已生成 `mvnw` 包装器，可免装 Maven）。

启动后：
- 接口基础地址：`http://localhost:8080/api`
- H2 数据库控制台：`http://localhost:8080/h2-console`（JDBC URL 填 `jdbc:h2:file:./data/campusbook`）

### 2. 启动前端（端口 5173）
```bash
cd frontend
npm install
npm run dev
```
打开 `http://localhost:5173` → 注册账号 → 登录 → 发布一本教材 → 在“浏览”里看到它。

> 前端通过 Vite 代理把 `/api` 转发到 8080，所以开发时**不会跨域**。

---

## 四、核心代码讲解（考核重点：能讲清）

### 1. 分层架构（Controller / Service / Repository / Entity）
一次“发布教材”请求的数据流：
```
浏览器提交表单
  → 前端 Axios POST /api/books
  → BookController.create()        ← 控制层：只负责接收请求、返回结果
  → BookService.create()           ← 服务层：写业务逻辑（设拥有者、设状态）
  → TextbookRepository.save()      ← 数据访问层：与数据库打交道
  → 落库到 textbooks 表            ← Entity 对应这张表
```
**为什么分层**：各层职责单一，改业务不动控制层，换数据库不动业务层。

### 2. 登录与 JWT（无状态鉴权）
- 注册：`UserService.register` 把密码用 **BCrypt** 加密后存库（明文密码绝不进数据库）。
- 登录：`AuthenticationManager` 校验账号密码 → 通过后 `JwtUtil.generateToken` 签发一个 **JWT**（把用户名、角色塞进去，24 小时有效）。
- 之后每次请求：前端在请求头带 `Authorization: Bearer <token>` → **`JwtFilter`** 拦截校验 → 把用户信息放进 Spring Security 的上下文 → 接口里用 `authentication.getName()` 拿到当前用户名。
- **为什么用 JWT 而不是 session**：JWT 自带用户信息、服务端不用存会话，适合前后端分离和后续部署到多台服务器。

### 3. 权限控制
- `SecurityConfig` 里 `/api/auth/**` 放行（注册登录不需要登录），其余接口 `authenticated()` 必须带有效 token。
- 删除教材时 `BookService.delete` 校验“只能删自己的”（对比 `owner.username` 与当前用户），这是**业务层权限**。

### 4. 前后端联调
- 前端 `src/utils/request.js`：统一给所有请求加 `/api` 前缀 + 自动附带 token；收到 401 自动跳登录页。
- 前端 `src/store/auth.js`（Pinia）：保存登录态到 `localStorage`，刷新不丢。
- 前端 `src/router/index.js`：路由守卫，未登录访问 `/books` 等页面会被踢回 `/login`。

---

## 五、部署（考核要求：成功部署）

**最简单路径（传统 Linux 服务器）：**
1. 买一台轻量云服务器（阿里云/腾讯云学生机，几十元/年），装 JDK17。
2. 后端打包：`cd backend && ./mvnw package -DskipTests`，得到 `target/book-exchange-0.0.1-SNAPSHOT.jar`。
3. 把 jar 传到服务器：`java -jar book-exchange-0.0.1-SNAPSHOT.jar` 后台运行（用 `nohup` 或 systemd）。
4. 前端打包：`cd frontend && npm run build`，把 `dist/` 传到服务器，用 Nginx 托管并反代 `/api` 到后端 8080。
5. 域名/防火墙放行 80、443、8080。

**进阶（Docker，可给你后续补）：** 写 `Dockerfile` + `docker-compose`（前端 Nginx + 后端 + MySQL 三容器），一条命令起全套。

> 部署时把 `application.yml` 的 H2 换成 MySQL：`spring.datasource.url=jdbc:mysql://localhost:3306/campusbook`、`driver-class-name=com.mysql.cj.jdbc.Driver`，并加 `mysql-connector-j` 依赖。

---

## 六、对照考核要求自查

| 考核点 | 本项目是否满足 | 你要能讲清的点 |
|---|---|---|
| 完整前端页面 | ✅ 登录/注册/浏览/发布/我的 5 个页面 | 组件化、路由、Axios 取数 |
| 后端接口 | ✅ 注册/登录/教材 CRUD | Controller/Service/Repository 分层与数据流 |
| 数据库交互 | ✅ JPA + H2（部署换 MySQL） | Entity 与表映射、Repository 查询 |
| 基本业务功能 | ✅ 教材发布/浏览/删除，权限隔离 | 为什么只能删自己的 |
| 登录和权限控制 | ✅ JWT + Spring Security | JWT 流程、为什么不用 session |
| 前后端联调 | ✅ Vite 代理 + token 拦截 | 请求怎么带上 token、401 怎么处理 |
| 成功部署 | ⬜ 需你按第五节操作 | 本地代码到线上每一步 |
| 讲清实现过程 | ⬜ 需你练讲解 | 用第四节的 4 块去讲 |

---

## 七、可以继续加的功能（加分项）
- 教材图片上传（目前是填图片链接，可改真上传）
- 私信 / 留言（买家联系卖家）
- 搜索后端化（现在搜索在前端做）
- 标记“已售出”按钮
- 管理员后台
