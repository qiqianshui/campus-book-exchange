# Git 与 GitHub 入门（给第一次用的人）

## 一、为什么桌面没有 Git / GitHub？——这是正常的

- **Git**：装在电脑里的程序，但**没有窗口、没有图标可双击**。你用"命令行"跟它说话（就像 `python` 也没有桌面图标）。
  - 怎么打开：按 Win 键，搜 **"Git Bash"**（Git 装好就有），点开就是能输命令的窗口；VS Code 底部"终端"里也能直接用。
- **GitHub**：是个**网站**（github.com），不是装在你电脑上的软件。你注册的是它的**账号**，所以桌面当然不会有 GitHub 图标——用浏览器打开 github.com 登录就行。

> 一句话：Git 在你电脑里（命令操作），GitHub 在网站上（浏览器操作），它俩本来就不该出现在桌面。

## 二、它们的关系（重要）

```
你写的代码  ──Git 提交──▶  电脑里的"本地仓库"
                               │
                          Git 推送(push)
                               │
                               ▼
                   GitHub 网站上的"远程仓库"（云端备份 / 展示 / 协作）
```

- **Git** 负责"记录你每次改了什么"（版本控制）。
- **GitHub** 负责"把记录存到网上"，别人能看、能协作，换电脑也能拉下来。

## 三、上一条说的 ①（改邮箱）到底是什么意思

**背景**：我用命令帮你做了第一次提交（commit）。提交记录要写"这是谁提交的"，靠一个**邮箱地址**认人。我不确定你 GitHub 注册用的邮箱，先填了占位的 `pan.github@example.com`。

**问题**：GitHub 靠"提交里的邮箱"认人。邮箱对不上 → 你 Push 后这些提交显示成"未知用户"，而且你主页那片**绿色小方格（贡献图）不会亮**——等于白贡献。

**解决办法**（在 Git Bash 里粘贴运行）：

第 1 步：去 GitHub 查你的邮箱
- 浏览器打开 github.com → 右上角头像 → **Settings** → 左侧 **Emails**
- 记下 Primary email（若勾了 "Keep my email private"，就用它给的 `xxxx+你的名@users.noreply.github.com`）

第 2 步：把下面邮箱换成你的，运行
```bash
git config user.name "潘阳"
git config user.email "你刚查到的真实邮箱"
```

第 3 步：把已做好的第一次提交，改成用新邮箱署名
```bash
git commit --amend --reset-author --no-edit
```

> 做完再 Push，绿色方格就会亮。若暂时不在乎绿色方格，可跳过，不影响项目能跑、能交。

## 四、下一步（回顾）

1. 上面 ① 做完（或决定跳过）。
2. 浏览器打开 github.com → 右上角 **+** → **New repository** → 名字填 `campus-book-exchange`，选 **Public**，**别勾** "Add a README / .gitignore / license" 任何选项 → 点 Create。
3. 创建后，用下面几行（把 `<你的用户名>` 换成你 GitHub 账号名）：
```bash
git branch -M main
git remote add origin https://github.com/<你的用户名>/campus-book-exchange.git
git push -u origin main
```
4. 运行 `git push` 那行时，会**弹出浏览器让你登录 GitHub**，登录完代码就传上去了。

## 五、推送之后练"合并操作"（学长要求）

Push 成功后，练一遍分支 + PR，补齐"合并"这项：
```bash
git checkout -b feature/test      # 开新分支
# 随便改个文件保存
git add . && git commit -m "练习：测试分支"
git push -u origin feature/test   # 推到 GitHub
```
然后去 GitHub 网页 → 你的仓库 → 会出现 **"Compare & pull request"** 按钮 → 点它 → Create pull request → Merge。这就走通了"分支→提交→合并"的完整协作流程。
