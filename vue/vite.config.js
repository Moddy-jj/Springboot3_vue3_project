import { fileURLToPath, URL } from 'node:url'

import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'
import path from 'node:path'

// https://vitejs.dev/config/
export default defineConfig({
  plugins: [
    vue(),
  ],
  resolve: {
    alias: {
      '@': fileURLToPath(new URL('./src', import.meta.url))
    }
  }
  ,
  server:{
    proxy:{
      '/api':{
        target:'http://localhost:8080',//后台服务源
        changeOrigin:true,//修改源
        rewrite:(path)=>path.replace(/^\/api/,'')///api替换
      }
    }
  }
})
