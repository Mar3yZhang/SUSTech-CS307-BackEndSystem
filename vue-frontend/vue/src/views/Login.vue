<template>
  <div style="width: 100%; height: 100vh; background-color: #3c3d48; overflow: hidden">
    <div style="width: 400px; margin: 150px auto">
      <div style="color: #ffffff; font-size: 30px; text-align: center; padding: 30px 0">Log in</div>
      <el-form ref="form" :model="form" size="normal">
        <el-form-item prop="username">
          <el-input prefix-icon="el-icon-user-solid" v-model="form.username" placeholder="username"></el-input>
        </el-form-item>
        <el-form-item prop="password">
          <el-input prefix-icon="el-icon-lock" v-model="form.password" placeholder="password" show-password></el-input>
        </el-form-item>
        <el-form-item>
          <el-button style="width: 100%" type="primary" @click="login">log in</el-button>
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>

<script>
import request from "../../util/request";

export default {
  name: "Login",
  data() {
    return {
      form: {}
    }
  },
  created() {
    sessionStorage.removeItem("user")
  },
  methods: {
    login() {
      console.log('login')
      request.post("http://localhost:8181/users/login", this.form).then(response => {
        console.log(response)
        if (response.code === '114' || response.code === '514') {
          this.$message({
            type: 'error',
            message: response.msg
          })
        } else {
          this.$message({
            type: 'success',
            message: 'log in successfully! '
          })
          sessionStorage.setItem("user", JSON.stringify(response.data))
          console.log(response.data)
          this.$router.push('/')
        }
      })
    }
  }
}
</script>

<style scoped>

</style>