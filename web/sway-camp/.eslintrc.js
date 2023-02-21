module.exports = {
  root: true,
  env: {
    browser: true,
    es2021: true,
    node: true
  },
  // Vue 解析
  parser: "vue-eslint-parser",
  parserOptions: {
    parser: "@typescript-eslint/parser",
    ecmaVersion: "latest",
    sourceType: "module"
  },
  plugins: ["vue", "@typescript-eslint"],
  extends: [
    "eslint:recommended", // eslint 推荐规范
    "@vue/typescript/recommended", // 校验 .vue 文件的 ts，需要安装 npm i @vue/eslint-config-typescript -D
    "plugin:@typescript-eslint/recommended", // ts 语法插件
    "plugin:vue/vue3-recommended", // vue3解析 https://eslint.vuejs.org/
    "plugin:prettier/recommended" // 使 eslint 使用 prettierrc 的规则来校验，避免两者之间的格式冲突，添加到数组的最后一个元素覆盖来去除不必要的规则冲突。
  ],
  rules: {
    eqeqeq: ["error", "always"],
    "vue/multi-word-component-names": 0
  }
}
