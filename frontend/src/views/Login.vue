<template>
  <div class="container" style="max-width:360px;margin-top:80px">
    <div class="card">
      <h2>登录</h2>
      <input v-model="username" placeholder="用户名" />
      <input v-model="password" type="password" placeholder="密码" />
      <button class="primary" @click="submit">登录</button>
      <p style="font-size:13px">还没有账号？<router-link to="/register">去注册</router-link></p>
      <p v-if="err" style="color:#e74c3c;font-size:13px">{{ err }}</p>
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import { useAuthStore } from '../store/auth'

const username = ref('')
const password = ref('')
const err = ref('')
const auth = useAuthStore()
const router = useRouter()

async function submit() {
  err.value = ''
  try {
    await auth.login(username.value, password.value)
    router.push('/books')
  } catch (e) {
    err.value = '登录失败，检查用户名或密码'
  }
}
</script>
