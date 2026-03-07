<template>
  <div id="app">
    <header>
      <div class="logo"><span class="emoji">▶️</span> Joyya 视频</div>
      <nav>
        <RouterLink to="/" class="nav-link">首页</RouterLink>
        <RouterLink to="/videos/hot" class="nav-link">热门</RouterLink>
        <RouterLink to="/videos/upload" class="nav-link">上传</RouterLink>
        <RouterLink to="/videos/my" class="nav-link">我的</RouterLink>
      </nav>
    </header>
    
    <main class="main-container">
      <RouterView />
    </main>
    
    <footer>
      <p>© 2026 Joyya Video - B 站仿站项目</p>
    </footer>
  </div>
</template>

<script setup>
</script>

<style scoped>
header {
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 1rem 2rem;
  background: linear-gradient(135deg, #fb7299 0%, #fe496d 100%);
  color: white;
}

.logo {
  font-size: 1.5rem;
  font-weight: bold;
}

.logo .emoji {
  margin-right: 0.5rem;
}

nav {
  display: flex;
  gap: 2rem;
}

.nav-link {
  color: white;
  text-decoration: none;
  padding: 0.5rem 1rem;
  border-radius: 4px;
  transition: background 0.3s;
}

.nav-link:hover,
.nav-link.router-link-active {
  background: rgba(255, 255, 255, 0.2);
}

.main-container {
  min-height: calc(100vh - 180px);
  padding: 2rem;
  background: #f6f6f6;
}

footer {
  text-align: center;
  padding: 1rem;
  background: #333;
  color: white;
}
</style>
