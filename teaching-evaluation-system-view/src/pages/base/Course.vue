<template>
  <div class="course">
    <!-- 按钮 -->
    <div class="btns">
      <el-col :span="20">
        <el-form :inline="true">
          <el-form-item>
            <el-input v-model="KeyWord" placeholder="请输入关键字" @input="findCourseKeyWord(KeyWord)" />
          </el-form-item>
          <el-form-item v-model="form.name">
            <el-button @click="findCourseKeyWord(KeyWord)">搜索</el-button>
            <!-- <el-button @click="handlerReset">重置</el-button> -->
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
    <el-table ref="multipleTable" :data="coursees" tooltip-effect="dark" style="width: 100%" @selection-change="handleSelectionChange">
      <el-table-column prop="id" label="课程编号" type="selection" width="55px" align="center" />
      <el-table-column prop="name" label="课程名称" align="center" />
      <el-table-column prop="period" label="课程周期" align="center" />
      <el-table-column prop="description" label="课程描述" align="center" />
      <el-table-column label="操作" align="center" width="180">
        <template #default="record">
          <el-button type="primary" icon="el-icon-edit" circle @click.prevent="editHandler(record.row)" />
          <el-button type="danger" icon="el-icon-delete" circle @click.prevent="deleteHandler(record.row.id)" />
        </template>
      </el-table-column>
    </el-table>
    <!-- /表格 -->

    <!-- 模态框 -->
    <el-dialog :title="title" :visible.sync="visible">
      {{ form }} <br>
      <el-form :model="form" :rules="rules">
        <el-form-item label="课程名称" label-width="80px" prop="name">
          <el-input v-model="form.name" autocomplete="off" />
        </el-form-item>
        <el-form-item label="课程周期" label-width="80px" prop="period">
          <el-input v-model="form.period" autocomplete="off" />
        </el-form-item>
        <el-form-item label="课程描述" label-width="80px">
          <el-input v-model="form.description" type="textarea" autocomplete="off" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="visible = false">取 消</el-button>
        <el-button type="primary" @click="handlerCommit">确 定</el-button>
      </div>
    </el-dialog>
    <!-- /模态框 -->
    <!-- {{areas}} -->
  </div>
</template>
<script>
import { mapState, mapGetters, mapMutations, mapActions } from 'vuex'
export default {
  data() {
    return {
      ids: [],
      KeyWord: '',
      title: '',
      form: {},
      visible: false,
      rules: {
        name: [
          { required: true, message: '请输入区域名称', trigger: 'blur' },
          { min: 2, max: 20, message: '长度在 2 到 20 个字符', trigger: 'blur' }
        ],
        period: [
          { required: true, message: '请输入联系方式', trigger: 'blur' }
        ]
      }
    }
  },
  created() {
    this.findAllCoursees()
  },
  computed: {
    ...mapState('course', ['coursees'])
  },
  methods: {
    ...mapActions('course', ['findAllCoursees', 'addOrupdateCourse', 'deleteCouersees', 'findInfoByKeyWord']),
    // 搜索
    findCourseKeyWord(KeyWord) {
      const obj = {
        keyWord: KeyWord
      }
      this.findInfoByKeyWord(obj)
    },
    // 重置
    // handlerReset(){
    //     this.findAllCoursees()

    // },
    // 保存课程信息
    handlerCommit() {
      this.addOrupdateCourse(this.form).then(() => {
        // 提示框
        this.visible = false
        // 提示信息
        this.$message({
          type: 'success',
          message: '保存成功'
        })
      })
    },
    // 添加课程
    toAddHandler() {
      // alert("添加")
      this.visible = true
      this.title = '添加课程信息'
      this.form = {}
    },
    // 批量删除
    batchDeleteHandler() {
      this.ids.forEach((item) => {
        var obj = {
          ids: item
        }
        this.deleteCouersees(obj)
      })
    },

    // 获取当前选中行id
    handleSelectionChange(val) {
      var idsBatch = []
      val.forEach((item) => {
        idsBatch.push(item.id)
      })
      this.ids = idsBatch
    },
    // 通过id删除课程信息
    deleteHandler(id) {
      this.$confirm('确认删除吗？', '提示', {
        type: 'warning'
      }).then(() => {
        const obj = {
          ids: id
        }
        this.deleteCouersees(obj)
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
    // 修改课程信息
    editHandler(row) {
      // 弹出模态框,使visible为true
      this.visible = true
      // 重置表单信息
      this.form = row
      this.title = '修改课程信息'
    }
  }
}
</script>
