<template>
  <el-table
    :data="tableData.filter(data => !search || data.name.toLowerCase().includes(search.toLowerCase()))"
    style="width: 100%"
  >
    <el-table-column
      label="创建时间"
      prop="date"
    />
    <el-table-column
      label="院系名称"
      prop="name"
    />
    <el-table-column
      align="right"
    >
      <template slot="header" slot-scope="scope">
        <el-input
          v-model="search"
          size="mini"
          placeholder="输入关键字搜索"
        />
      </template>
      <template slot-scope="scope">
        <el-button
          size="mini"
          @click="handleEdit(scope.$index, scope.row)"
        >Edit</el-button>
        <el-button
          size="mini"
          type="danger"
          @click="handleDelete(scope.$index)"
        >Delete</el-button>
        <el-button type="success" size="mini" @click="open">add</el-button>
      </template>
    </el-table-column>
  </el-table>
</template>

<script>
import { addDepartment, deleteDepartment, queryDepartment, updateDepartment } from '@/api/user'

export default {
  data() {
    return {
      tableData: [],
      search: ''
    }
  },
  created() {
    this.loading = true
    queryDepartment().then(response => {
      this.tableData = response.result
    })
  },
  methods: {
    handleEdit(data) {
      this.$prompt('请输入要修改的院系名称', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        inputValue: this.tableData[data].name,
        inputPattern: /[]?/
      }).then(({ value }) => {
        this.handleUpdate(value, this.tableData[data].name,this.tableData[data].id)
      })
    },
    handleDelete(data) {
      deleteDepartment(this.tableData[data].name)
      location.reload()
    },
    open() {
      this.$prompt('请输入院系名称', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        inputPattern: /[]?/
      }).then(({ value }) => {
        this.handleAdd(value)
      })
    },
    handleAdd(name) {
      this.loading = true
      addDepartment(name).then(response => {
        queryDepartment().then(response => {
          this.tableData = response.result
        })
      })
    },
    handleUpdate(newValue, oldValue, id) {
      this.loading = true
      updateDepartment(newValue, oldValue, id).then(response => {
        queryDepartment().then(response => {
          this.tableData = response.result
        })
      })
    }
  }
}
</script>
