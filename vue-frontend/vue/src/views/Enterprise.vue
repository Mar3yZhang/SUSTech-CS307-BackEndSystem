<template>
  <div class="staff" style="padding: 10px">
    <!--      功能部分-->
    <div style="margin: 10px 0">
      <el-button type="primary" @click="insert" v-if="user.role ===1">insert</el-button>
      <!--      <el-button type="primary">导入</el-button>-->
      <!--      <el-button type="primary">导出</el-button>-->
    </div>
    <!--    搜索区域-->
    <div style="margin: 10px 0">
      <el-input v-model="name1" placeholder="please input key word" style="width: 20%" clearable></el-input>
      <el-button type="primary" @click="find" style="margin-left: 5px"> query</el-button>
    </div>


    <div>
      <el-pagination
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
          :current-page="pageNum"
          :page-sizes="[5, 10, 15, 20]"
          :page-size="pageSize"
          layout="total, sizes, prev, pager, next, jumper"
          :total="total">
      </el-pagination>
    </div>

    <el-table :data="tableData" stripe border style="width: 100%">
      <el-table-column prop="name" label="name" width="200"/>
      <el-table-column prop="country" label="country" width="200"/>
      <el-table-column prop="city" label="city" width="200"/>
      <el-table-column prop="supplyCenter" label="supplyCenter" width="200"/>
      <el-table-column prop="industry" label="industry" width="200"/>
      <el-table-column fixed="right" label="operation" width="180">
        <template #default="scope">
          <el-button round size="small" @click="edit(scope.row)" v-if="user.role ===1">update</el-button>
          <el-button round size="small" @click="delete1(scope.row.name)" v-if="user.role ===1">delete</el-button>
        </template>
      </el-table-column>
    </el-table>

  </div>


  <!--  <el-button text @click="dialogVisible = true"-->
  <!--  >click to open the Dialog</el-button-->
  <!--  >-->

  <el-dialog v-model="dialogVisible" title="Tips" width="30%">
    <el-form :model=form label-width="120px">
      <el-form-item label="name">
        <el-input v-model="form.name" style width="80%"/>
      </el-form-item>
      <el-form-item label="country">
        <el-input v-model="form.country" style width="80%"/>
      </el-form-item>
      <el-form-item label="city">
        <el-input v-model="form.city" style width="80%"/>
      </el-form-item>
      <el-form-item label="supplyCenter">
        <el-input v-model="form.supplyCenter" style width="80%"/>
      </el-form-item>
      <el-form-item label="industry">
        <el-input v-model="form.industry" style width="80%"/>
      </el-form-item>
    </el-form>
    <template #footer scope>
      <span class="dialog-footer">
        <el-button @click="dialogVisible = false">cancel</el-button>
        <el-button type="primary" @click="save">confirm</el-button>
      </span>
    </template>
  </el-dialog>
</template>

<script>
import request from "../../util/request";
import axios from "axios";

export default {
  name: "Enterprise",
  data() {
    return {
      name1: '',
      pageNum: 1,
      form: {},
      user: {},
      pageSize: 10,
      total: 0,
      dialogVisible: false,
      tableData: [],
      editMode: false
    }
  },
  created() {
    let userSTR = sessionStorage.getItem("user") || "{}"
    this.user = JSON.parse(userSTR)
    this.load()
  },
  methods: {
    insert() {
      this.dialogVisible = true
      this.form = {}
    },
    save() {
      if (!this.editMode) {
        this.dialogVisible = false
        request.post("http://localhost:8181/enterprise/insertOneEnterprise", this.form).then(res => {
          console.log(res)
        })
      } else {
        request.put("http://localhost:8181/enterprise/updateEnterprise", this.form).then(response => {
          console.log(response)
          this.dialogVisible = false
          if (response) {
            this.$message({
              type: "success",
              message: "edit successfully!"
            })
          } else {
            this.$message({
              type: "error",
              message: "fail to edit"
            })
          }
          this.load()
          this.editMode = false;
        })
      }
    },
    delete1(name) {
      console.log("delete" + name)
      request.delete("http://localhost:8181/enterprise/delete/" + name).then(response => {
        console.log(response)
        if (response === true) {
          this.$message({
            type: "success",
            message: "delete the enterprise: " + name + " successfully! "
          })
        } else {
          this.$message({
            type: "error",
            message: "fail to delete the enterprise: " + name + " !"
          })
        }
        this.load()
      })

    },
    load() {
      request.get("http://localhost:8181/enterprise/show", {
        params: {
          pageNum: this.pageNum,
          pageSize: this.pageSize,
          number: this.name1
        }
      }).then(response => {
        ({records: this.tableData, total: this.total} = response.data);
        console.log(response)
      })
    },
    find() {
      request.get("http://localhost:8181/enterprise/query", {
        params: {
          pageNum: this.pageNum,
          pageSize: this.pageSize,
          search: this.name1
        }
      }).then(response => {
        ({records: this.tableData, total: this.total} = response.data);
        console.log(response)
      })
      this.load()
    },
    edit(row) {
      console.log('handleEdit')
      this.form = JSON.parse(JSON.stringify(row))
      this.editMode = true
      this.dialogVisible = true
    },
    handleSizeChange(pageSize) {
      console.log('handleSizeChange')
      this.pageSize = pageSize
      this.load()
    },
    handleCurrentChange(pageNum) {
      console.log('handleCurrentChange')
      this.pageNum = pageNum
      this.load()
    },
  }
}

</script>

<style scoped>

</style>