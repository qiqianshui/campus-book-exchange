<template>
  <div class="container" style="max-width:360px;margin-top:80px">
    <div class="card">
      <h2>注册</h2>
      <input v-model="username" placeholder="用户名" />
      <input v-model="password" type="password" placeholder="密码（至少 4 位）" />
      <button class="primary" @click="submit">注册</button>
      <p style="font-size:13px">已有账号？<router-link to="/login">去登录</router-link></p>
      <p v-if="msg" style="color:#27ae60;font-size:13px">{{ msg }}</p>
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
const msg = ref('')
const err = ref('')
const auth = useAuthStore()
const router = useRouter()

async function submit() {
  msg.value = ''
  err.value = ''
  if (!username.value || !password.value) {
    err.value = '用户名和密码都不能为空'
    return
  }
  try {
    await auth.register(username.value, password.value)
    msg.value = '注册成功，正在跳转登录…'
    setTimeout(() => router.push('/login'), 800)
  } catch (e) {
    err.value = '注册失败，用户名可能已被占用'
  }
}
</script>
