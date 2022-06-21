<template>
  <div>
    <Header/>
    <div style="display: flex">
      <Aside/>
      <router-view style="flex: 1"/>
    </div>
  </div>
</template>

<script>
import Header from "@/components/head";
import Aside from "@/components/Aside";

export default {
  name: "Home",
  components: {
    Header,
    Aside
  }
}
</script>

<style scoped>

</style>