// vue.config.js（Vue CLI 专用配置）
module.exports = {
  // 这里只保留 Vue CLI 支持的配置选项
  devServer: {
    port: 8080,
    proxy: {
      '/api': {
        target: 'http://localhost:8081',
        changeOrigin: true,
        pathRewrite: { '^/api': '' }
      }
    }
  },

  // 其他 Vue CLI 配置选项...
  lintOnSave: false,
  transpileDependencies: true
}