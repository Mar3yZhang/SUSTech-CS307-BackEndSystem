<template>
  <div>
<!--    <div style="height: 50px;border-bottom: 1px solid #ccc; display: flex"></div>-->
  <el-menu
      style="width: 200px; min-height: calc(100vh - 50px)"
      default-active="staff"
      router
      class="el-menu-vertical-demo"
  >
    <el-submenu index="system">
      <template #title>
        <i class="el-icon-platform-element"></i>
        <span>系统管理</span>
      </template>
      <el-menu-item index="staff">Staff</el-menu-item>
      <el-menu-item index="enterprise">Enterprise</el-menu-item>
    </el-submenu>
  </el-menu>
  </div>
</template>

<script >
export default {
  name: "Aside"
}
</script>

<style scoped>

</style>
