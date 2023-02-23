import { defineConfig } from "vite"
import path from "path"
import vue from "@vitejs/plugin-vue"
import ElementPlus from "unplugin-element-plus/vite"

export default defineConfig({
  plugins: [vue(), ElementPlus()],
  resolve: {
    // 配置路径别名
    alias: {
      // 需要在tsconfig.json继续配置
      "@": path.resolve(__dirname, "src")
    }
  },
  server: {
    https: false, // 是否开启 https
    open: false, // 是否自动在浏览器打开
    port: 4157, // 端口号
    host: "127.0.0.1", //如果将此设置为 0.0.0.0 或者 true 将监听所有地址，包括局域网和公网地址。
    strictPort: false //端口被占用自动退出
  },
  css: {
    preprocessorOptions: {
      less: {
        additionalData: ` @import (reference) "${path.resolve("src/assets/css/variable.less")}";`
      }
    }
  }
})
