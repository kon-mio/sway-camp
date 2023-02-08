module.exports = {
  printWidth: 100, // 一行最多 100 字符
  tabWidth: 2, // 使用 2 个空格缩进
  useTabs: false, // 不使用缩进符，而使用空格
  semi: false, // 行尾不需要有分号
  singleQuote: false, // 使用单引号
  quoteProps: "as-needed", // 对象的 key 仅在必要时用引号
  trailingComma: "none", //解决对象尾项逗号问题
  bracketSpacing: true, // 大括号内的首尾需要空格
  rangeStart: 0, // 每个文件格式化的范围是文件的全部内容
  rangeEnd: Infinity,
  proseWrap: "preserve", // 使用默认的折行标准
  htmlWhitespaceSensitivity: "css", // 根据显示样式决定 html 要不要折行
  endOfLine: "lf", // 换行符使用 lf
  endOfLine: "auto" // 解决问题Delete `␍`
}
