<template>
  <div class="teacher">
    <!-- 按钮 -->
    <div class="btns">
      <el-col :span="20">
        <el-form :inline="true">
          <el-form-item>
            <el-input v-model="KeyWord" placeholder="请输入关键字" @input="findTeacherKeyword(KeyWord)" />
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
    </div>
    <!-- /按钮 -->

    <!-- 表格 -->
    <el-table ref="multipleTable" :data="teachers" border style="width: 100%" @selection-change="handleSelectionChange">
      <el-table-column prop="id" label="讲师编号" type="selection" width="100" align="center" />
      <el-table-column prop="name" label="讲师姓名" align="center" />
      <el-table-column prop="gender" label="性别" align="center" />
      <el-table-column prop="birthday" label="出生日期" align="center" />
      <el-table-column prop="startday" label="入职时间" align="center" />
      <el-table-column label="操作" align="center" width="180">
        <template #default="record">
          <el-button type="primary" icon="el-icon-edit" circle @click.prevent="editHandler(record.row)" />
          <el-button type="danger" icon="el-icon-delete" circle @click.prevent="deleteHandler(record.row.id)" />
        </template>
      </el-table-column>
    </el-table>
    <!-- / 表格 -->

    <!-- 模态框 -->
    <el-dialog :title="title" :visible.sync="visible">
      <el-form :model="teacher" :rules="rules">
        <div ref="znn" style="display:none;">{{ teacher.birthday | dateFormat }}</div>
        <div ref="xpf" style="display:none;">{{ teacher.startday | dateFormat }}</div>
        <el-form-item label="讲师名称" label-width="80px" prop="name">
          <el-input v-model="teacher.name" autocomplete="off" />
        </el-form-item>
        <el-form-item label="性别" label-width="80px">
          <el-radio v-model="teacher.gender" label="男">男</el-radio>
          <el-radio v-model="teacher.gender" label="女">女</el-radio>
        </el-form-item>
        <el-form-item label="出生日期" label-width="80px">
          <el-date-picker v-model="teacher.birthday" type="date" placeholder="选择出生日期" />
        </el-form-item>
        <el-form-item label="入职时间" label-width="80px">
          <el-date-picker v-model="teacher.startday" type="date" placeholder="选择入职时间" />
        </el-form-item>
        <!-- <el-form-item label="出生日期" label-width="80px" prop="name">
                    <el-input v-model="teacher.birthday" autocomplete="off"></el-input>
                </el-form-item>
                <el-form-item label="入职时间" label-width="80px" prop="name">
                    <el-input v-model="teacher.startday" autocomplete="off"></el-input>
                </el-form-item> -->
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="visible = false">取 消</el-button>
        <el-button type="primary" @click="handlerCommit">确 定</el-button>
      </div>
    </el-dialog>
    <!-- /模态框 -->
  </div>
</template>
<script>
import { mapState, mapActions } from 'vuex'
export default {
  data() {
    return {
      teacher: {},
      visible: false,
      title: '',
      KeyWord: '',
      rules: {
        name: [
          { required: true, message: '请输入区域名称', trigger: 'blur' },
          { min: 2, max: 20, message: '长度在 2 到 20 个字符', trigger: 'blur' }
        ]
      }
    }
  },
  created() {
    this.findAllTeachers()
  },
  computed: {
    ...mapState('teacher', ['teachers'])
  },
  methods: {
    ...mapActions('teacher', ['deleteTeacherByIds', 'saveOrUpdateTeacher', 'findTeacherByKeyword', 'findAllTeachers']),
    // 修改
    editHandler(row) {
      this.title = '修改讲师信息'
      this.visible = true
      this.teacher = row
    },
    // 保存
    handlerCommit() {
      const obj = {
        birthday: this.$refs.znn.innerText,
        startday: this.$refs.xpf.innerText,
        name: this.teacher.name,
        gender: this.teacher.gender
      }
      this.saveOrUpdateTeacher(obj).then(() => {
        // 提示框
        this.visible = false
        // 提示信息
        this.$message({
          type: 'success',
          message: '保存成功'
        })
      })
    },
    // 删除
    deleteHandler(id) {
      this.$confirm('确认删除吗？', '提示', {
        type: 'warning'
      }).then(() => {
        const obj = {
          list: id
        }
        this.deleteTeacherByIds(obj)
          .then((response) => {
            this.$message({ type: 'success', message: '删除成功' })
          })
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消删除'
        })
      })
    },
    // 当选择项改变时，触发该事件
    handleSelectionChange(val) {
      var idsBatch = []
      val.forEach((item) => {
        idsBatch.push(item.id)
      })
      this.id = idsBatch
    },
    // 批量删除
    batchDeleteHandler() {
      this.id.forEach((item) => {
        var obj = {
          list: item
        }
        this.deleteTeacherByIds(obj)
      })
    },
    // 添加
    toAddHandler() {
      const obj = {
        // birthday:this.t
      }

      this.visible = true
      this.title = '添加讲师信息'
    },
    // 关键字搜索
    findTeacherKeyword(KeyWord) {
      const obj = {
        keyword: KeyWord
      }
      this.findTeacherByKeyword(obj)
    }
  }
}
</script>
