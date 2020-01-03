<template>
  <div class="clazz">
    <!-- 按钮 -->
    <!-- <div class="btns">
            <el-col :span="20">
                <el-form :inline="true">
                    <el-form-item>
                        <el-input v-model="KeyWord" placeholder="请输入关键字" @input="findTeacherKeyword(KeyWord)"></el-input>
                    </el-form-item>
                    <el-form-item v-model="teachers.name">
                        <el-button @click="findTeacherKeyword(KeyWord)">搜索</el-button>
                    </el-form-item>
                </el-form>
            </el-col>
            <el-col :span="4">
                <el-button type="primary" size="small" @click="toAddHandler">添加</el-button>
                <el-button type="danger" size="small" @click="batchDeleteHandler">批量删除</el-button>
            </el-col>
        </div> -->
    <!-- /按钮 -->
    <!-- 表格 -->
    <el-table ref="multipleTable" :data="clazzs" tooltip-effect="dark" style="width: 100%" @selection-change="handleSelectionChange">
      <el-table-column prop="id" label="班级编号" type="selection" width="55px" align="center" />
      <el-table-column prop="name" label="班级名称" align="center" />
      <el-table-column prop="serveNumber" label="问卷填写数量" align="center" />
      <el-table-column prop="studentNumber" label="学生数量" align="center" />
      <el-table-column prop="department.name" label="所属部门" align="center" />
      <el-table-column prop="teacher.name" label="班主任" align="center" />
      <el-table-column prop="description" label="班级描述" align="center" />
      <el-table-column label="操作" align="center" width="180">
        <template #default="record">
          <el-button type="primary" icon="el-icon-edit" circle @click.prevent="editHandler(record.row)" />
          <el-button type="danger" icon="el-icon-delete" circle @click.prevent="deleteHandler(record.row.id)" />
        </template>
      </el-table-column>
    </el-table>
    <!-- /表格 -->
  </div>
</template>
<script>
import { mapState, mapActions } from 'vuex'
export default {
  data() {
    return {

    }
  },
  created() {
    this.findAllClazzs()
  },
  computed: {
    ...mapState('clazz', ['clazzs'])
  },
  methods: {
    ...mapActions('clazz', ['findAllClazzs']),
    // 删除
    deleteHandler() {

    },
    // 修改
    editHandler() {

    },
    handleSelectionChange() {

    }
  }

}
</script>
