<template>
  <div style="height: 50px; line-height: 50px; border-bottom: 1px solid #ccc; display: flex">
    <div style="width: 200px; padding-left: 30px; font-weight: bold; color: #00bfff">
      Backstage_System
    </div>
    <div style="flex: 1"></div>
    <div style="width: 100px;padding-top:20px;font-weight: bold; color: #00ccff">
      <el-dropdown>
    <span class="el-dropdown-link">
      user
      <el-icon class="el-icon-arrow-down el-icon--right">
      </el-icon>
    </span>
        <template #dropdown>
          <el-dropdown-menu>
            <el-dropdown-item @click="$router.push('/create')">create new</el-dropdown-item>
            <el-dropdown-item @click="$router.push('/login')">log out</el-dropdown-item>
          </el-dropdown-menu>
        </template>
      </el-dropdown>
    </div>
  </div>

</template>

<script>

export default {
  name: "head"
}

</script>

<style scoped>
</style>