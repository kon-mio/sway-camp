import { defineConfig } from "vite"
import path from "path"
import vue from "@vitejs/plugin-vue"
import ElementPlus from "unplugin-element-plus/vite"
import prismjs from "vite-plugin-prismjs"

export default defineConfig({
  plugins: [
    vue(),
    ElementPlus(),
    prismjs({
      languages: ["html", "js", "java", "less"],
      // languages: 'all',
      plugins: ["line-numbers"], //配置显示行号插件
      theme: "dark", //主题名称
      css: true
    })
  ],
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
