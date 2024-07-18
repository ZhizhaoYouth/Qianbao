const { defineConfig } = require("@vue/cli-service");
module.exports = defineConfig({
  transpileDependencies: true,
  devServer: {
    port: 8082,
    historyApiFallback: true,
    allowedHosts: "all",
    client: {
      overlay: false,
    },
  },
  lintOnSave: "warning",
});
