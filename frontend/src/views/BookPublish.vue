<template>
  <div class="container" style="max-width:480px">
    <h2>发布教材</h2>
    <div class="card">
      <input v-model="form.title" placeholder="书名 *" />
      <input v-model="form.author" placeholder="作者" />
      <input v-model.number="form.price" type="number" placeholder="价格（元）*" />
      <select v-model="form.bookCondition">
        <option value="全新">全新</option>
        <option value="九成新">九成新</option>
        <option value="七成新">七成新</option>
        <option value="五成新">五成新</option>
      </select>
      <textarea v-model="form.description" placeholder="描述（专业、有无笔记等）" rows="3"></textarea>
      <input v-model="form.contact" placeholder="联系方式（微信 / QQ / 手机）" />
      <input v-model="form.imageUrl" placeholder="图片链接（可选）" />
      <button class="primary" @click="submit">发布</button>
      <p v-if="msg" style="color:#27ae60;font-size:13px">{{ msg }}</p>
      <p v-if="err" style="color:#e74c3c;font-size:13px">{{ err }}</p>
    </div>
  </div>
</template>

<script setup>
import { reactive, ref } from 'vue'
import { useRouter } from 'vue-router'
import request from '../utils/request'

const router = useRouter()
const form = reactive({
  title: '', author: '', price: null,
  bookCondition: '九成新', description: '', contact: '', imageUrl: ''
})
const msg = ref('')
const err = ref('')

async function submit() {
  msg.value = ''
  err.value = ''
  if (!form.title || form.price === null) {
    err.value = '书名和价格为必填'
    return
  }
  try {
    await request.post('/books', form)
    msg.value = '发布成功！'
    setTimeout(() => router.push('/books'), 600)
  } catch (e) {
    err.value = '发布失败，请确认已登录'
  }
}
</script>
