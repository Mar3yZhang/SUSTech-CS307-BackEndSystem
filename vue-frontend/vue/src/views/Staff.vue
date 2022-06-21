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
      <el-input v-model="name1" placeholder="please input the number" style="width: 20%" clearable></el-input>
      <el-button type="primary" @click="find" style="margin-left: 5px">query</el-button>
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
      <el-table-column prop="name" label="name" width="120"/>
      <el-table-column prop="age" label="age" width="120"/>
      <el-table-column prop="gender" label="gender" width="120"/>
      <el-radio v-model="form.gender" label="1" size="large">Male</el-radio>
      <el-radio v-model="form.gender" label="2" size="large">Female</el-radio>
      <el-table-column prop="number" label="number" width="180" sortable/>
      <el-table-column prop="supplyCenter" label="supplyCenter" width="180"/>
      <el-table-column prop="mobileNumber" label="mobileNumber" width="200"/>
      <el-table-column prop="type" label="type" width="180"/>
      <el-table-column fixed="right" label="operation" width="180">
        <template #default="scope">
          <el-button round size="small" @click="edit(scope.row)" v-if="user.role ===1">update</el-button>
          <el-button round size="small" @click="delete1(scope.row.number)" v-if="user.role ===1">delete</el-button>
        </template>
      </el-table-column>
    </el-table>
  </div>


  <!--  <el-button text @click="dialogVisible = true"-->
  <!--  >click to open the Dialog</el-button-->
  <!--  >-->

  <el-dialog v-model="dialogVisible" title="Tips" width="30%">
    <el-form label-width="120px">
      <el-form-item label="name">
        <el-input v-model="form.name" style width="80%"/>
      </el-form-item>
      <el-form-item label="age">
        <el-input v-model="form.age" style width="80%"/>
      </el-form-item>
      <el-form-item label="gender">
        <el-radio v-model="form.gender" label="Male" size="large">Male</el-radio>
        <el-radio v-model="form.gender" label="Female" size="large">Female</el-radio>
      </el-form-item>
      <el-form-item label="number">
        <el-input v-model="form.number" style width="80%"/>
      </el-form-item>
      <el-form-item label="supplyCenter">
        <el-input v-model="form.supplyCenter" style width="80%"/>
      </el-form-item>
      <el-form-item label="mobileNumber">
        <el-input v-model="form.mobileNumber" style width="80%"/>
      </el-form-item>
      <el-form-item label="type">
        <el-radio v-model="form.type" label="Director" size="large">Director</el-radio>
        <el-radio v-model="form.type" label="Salesman" size="large">Salesman</el-radio>
        <el-radio v-model="form.type" label="Supply Staff" size="large">Supply Staff</el-radio>
      </el-form-item>
    </el-form>
    <template #footer>
      <span class="dialog-footer">
        <el-button @click="dialogVisible = false">cancel</el-button>
        <el-button type="primary" @click="save"
        >confirm</el-button
        >
      </span>
    </template>
  </el-dialog>
</template>

<script>
import {Female, Male} from "@element-plus/icons";
import request from "../../util/request";
import axios from "axios";

export default {
  name: "staff",
  components: {Female, Male},
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


    request.get("http://localhost:8181/staff/get" + this.user.uss)
    this.load()

  },
  methods: {
    insert() {
      this.form = {},
          this.dialogVisible = true
    },
    save() {
      if (!this.editMode) {
        this.dialogVisible = false
        request.post("http://localhost:8181/staff/insertOneStaff", this.form).then(res => {
          console.log(res)
        })
      } else {
        this.dialogVisible = false
        request.put("http://localhost:8181/staff/updateStaff", this.form).then(res => {
          console.log(res)
        })

        this.editMode = false;
      }
      this.load()
    },
    delete1(number) {
      console.log("delete" + number)
      request.delete("http://localhost:8181/staff/delete/" + number).then(response => {
        console.log(response)
        if (response === true) {
          this.$message({
            type: "success",
            message: "delete the staff: " + number + " successfully! "
          })
        } else {
          this.$message({
            type: "error",
            message: "fail to delete the staff: " + number + "! "
          })
        }
        this.load()
      })

    },
    load() {
      request.get("http://localhost:8181/staff/show", {
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
      request.get("http://localhost:8181/staff/query", {
        params: {
          pageNum: this.pageNum,
          pageSize: this.pageSize,
          search: sessionStorage.getItem("user") || "{}"
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
    }
  }


}
</script>

<style scoped>

</style>