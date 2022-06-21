<template>
  <div style="width: 100%; height: 100vh; background-color: #383a49; overflow: hidden">
    <div style="width: 400px; margin: 150px auto">
      <div style="color: azure; font-size: 30px; text-align: center; padding: 30px 0">Sign in</div>
      <el-form ref="form" :model="form" size="normal">
        <el-form-item prop="username">
          <el-input prefix-icon="el-icon-user-solid" v-model="form.username" placeholder="username"></el-input>
        </el-form-item>
        <el-form-item prop="password">
          <el-input prefix-icon="el-icon-lock" v-model="form.password" placeholder="password" show-password></el-input>
        </el-form-item>
        <el-form-item label="role">
          <el-radio v-model="form.role" label="1" size="large">Super</el-radio>
          <el-radio v-model="form.role" label="2" size="large">Common</el-radio>
        </el-form-item>
        <el-form-item>
          <el-button style="width: 100%" type="primary" @click="create">create</el-button>
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>

<script>
import request from "../../util/request";

export default {
  name: "Create",
  data() {
    return {
      form: {}
    }
  },

  methods: {
    create() {
      request.post("http://localhost:8181/users/create", this.form).then(response => {
        console.log(response)
        if (response.code === '114' || response.code === '514') {
          this.$message({
            type: 'error',
            message: response.msg
          })
        } else {
          this.$message({
            type: 'success',
            message: 'create!'
          })
          this.$router.push('/login')
        }
      })
    }
  }
}
</script>

<style scoped>

</style>