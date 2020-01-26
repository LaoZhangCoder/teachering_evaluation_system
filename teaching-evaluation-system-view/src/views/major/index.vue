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
      label="专业名称"
      prop="majorName"
    />
    <el-table-column
      label="所属院系"
      prop="departmentName"
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
          @click="handleEdit(scope.$index)"
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
import { listMajor, deleteMajor } from '@/api/user'
export default {
  data() {
    return {
      tableData: [],
      search: ''
    }
  },
  created() {
    this.loading = true
    listMajor().then(response => {
      this.tableData = response.result
    })
  },
  methods: {
    handleEdit(data) {
      this.$router.push({ path: '/major/update', query: { id: this.tableData[data].majorId, departmentId: this.tableData[data].departmentId }})
    },
    handleDelete(data) {
      deleteMajor(this.tableData[data].majorId)
      location.reload()
    },
    open() {
      this.$router.push('/major/add')
    }
  }
}
</script>
