const {defineConfig} = require('@vue/cli-service')
module.exports = defineConfig({
    transpileDependencies: true,
    devServer: {
        host: 'localhost',
        port: "8080",
        open: true
    }
})

// module.exports = {
//     devServer: {
//         port: 8080,
//         proxy: {
//             '/api': {
//                 target: 'http://localhost:8181',
//                 changeOrigin: true,
//                 pathRewire: {
//                     '/api': ''
//                 }
//             }
//         }
//     }
// }