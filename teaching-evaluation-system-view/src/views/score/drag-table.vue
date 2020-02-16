<template>
  <div class="app-container">
    <form action="${pageContext.request.contextPath }/subpj" method="post" onSubmit="return show_confirm();">
      <input type="hidden" name="eid" value=" ${param.eid }">
      <div align="center">
        <h3>综合得分总分:{{ this.formParams.countScore }}</h3>
        <h2>课程名称：{{ this.courseName }}&nbsp;&nbsp; 任课老师：{{ this.teacherName }}</h2></div>
      <table class="table table-striped table-bordered table-hover table-condensed">
        <tr>
          <th rowspan="2">序号</th>
          <th rowspan="2">评教指标内容</th>
          <th colspan="5" align="center">评价选项（单选）</th>
        </tr>
        <tr>
          非常同意(10分)
          同意(8分)
          一般(5分)
          不同意(3分)
          非常不同意(0分)
        </tr>
        <tr>
          <td>1</td>
          <td>有明确的教学目标与计划，教学进度安排得当</td>
          <el-radio-group v-model="radio1" style="padding-left: 15%" @change="radioValueChange()">
            <el-radio :label="10" />
            <el-radio :label="8" />
            <el-radio :label="5" />
            <el-radio :label="3" />
            <el-radio :label="0" />
          </el-radio-group>
        </tr>
        <tr>
          <td>2</td>
          <td>授课内容有深度，学生能获得大量有价值的课程信息</td>
          <el-radio-group v-model="radio2" style="padding-left: 15%" @change="radioValueChange()">
            <el-radio :label="10" />
            <el-radio :label="8" />
            <el-radio :label="5" />
            <el-radio :label="3" />
            <el-radio :label="0" />
          </el-radio-group>
        </tr>
        <tr>
          <td>3</td>
          <td>教学内容熟练，脱稿讲授</td>
          <el-radio-group v-model="radio3" style="padding-left: 15%" @change="radioValueChange()">
            <el-radio :label="10" />
            <el-radio :label="8" />
            <el-radio :label="5" />
            <el-radio :label="3" />
            <el-radio :label="0" />
          </el-radio-group>
        </tr>
        <tr>
          <td>4</td>
          <td>讲课思路清晰，重点突出，难点处理得当</td>
          <el-radio-group v-model="radio4" style="padding-left: 15%" @change="radioValueChange()">
            <el-radio :label="10" />
            <el-radio :label="8" />
            <el-radio :label="5" />
            <el-radio :label="3" />
            <el-radio :label="0" />
          </el-radio-group>
        </tr>
        <tr>
          <td>5</td>
          <td>讲课具有启发性，能引导学生独立分析与思考</td>
          <el-radio-group v-model="radio5" style="padding-left: 15%" @change="radioValueChange()">
            <el-radio :label="10" />
            <el-radio :label="8" />
            <el-radio :label="5" />
            <el-radio :label="3" />
            <el-radio :label="0" />
          </el-radio-group>
        </tr>
        <tr>
          <td>6</td>
          <td>讲课理论联系实际，能调动学生听课的积极性，进而引发学生对本课程的兴趣</td>
          <el-radio-group v-model="radio6" style="padding-left: 15%" @change="radioValueChange()">
            <el-radio :label="10" />
            <el-radio :label="8" />
            <el-radio :label="5" />
            <el-radio :label="3" />
            <el-radio :label="0" />
          </el-radio-group>
        </tr>
        <tr>
          <td>7</td>
          <td>注意精讲，适当练习，布置大作业，并及时认真批改</td>
          <el-radio-group v-model="radio7" style="padding-left: 15%" @change="radioValueChange()">
            <el-radio :label="10" />
            <el-radio :label="8" />
            <el-radio :label="5" />
            <el-radio :label="3" />
            <el-radio :label="0" />
          </el-radio-group>
        </tr>
        <tr>
          <td>8</td>
          <td>大作业联系实际，有利于课程内容的学习</td>
          <el-radio-group v-model="radio8" style="padding-left: 15%" @change="radioValueChange()">
            <el-radio :label="10" />
            <el-radio :label="8" />
            <el-radio :label="5" />
            <el-radio :label="3" />
            <el-radio :label="0" />
          </el-radio-group>
        </tr>
        <tr>
          <td>9</td>
          <td>开学初即向学生明确宣布累加式考核及评价方法</td>
          <el-radio-group v-model="radio9" style="padding-left: 15%" @change="radioValueChange()">
            <el-radio :label="10" />
            <el-radio :label="8" />
            <el-radio :label="5" />
            <el-radio :label="3" />
            <el-radio :label="0" />
          </el-radio-group>
        </tr>
        <tr>
          <td>10</td>
          <td>通过该课学习，学生能掌握本课程的知识并获得相应的能力提高</td>
          <el-radio-group v-model="radio10" style="padding-left: 15%" @change="radioValueChange()">
            <el-radio :label="10" />
            <el-radio :label="8" />
            <el-radio :label="5" />
            <el-radio :label="3" />
            <el-radio :label="0" />
          </el-radio-group>
        </tr>
        <tr>
          <td colspan="7">学生意见与建议：感谢您对以上指标的评价，如果您还有其他任何新的想法，宝贵的建议，请在下面的空白处留言。我们珍视每一位同学个性化的思考。</td>
        </tr>
        <tr>
          <td colspan="7">
            <el-input
              v-model="formParams.message"
              type="textarea"
              :rows="4"
              placeholder="请输入评教建议内容"
            />
          </td>
        </tr>

      </table>
      <br>
      <div align="center">
        <el-button type="primary" @click="submitForm()">提交</el-button>
        <el-button @click="resetForm()">重置</el-button>
      </div>
    </form>
  </div>
</template>
<script>
import { startScore } from '@/api/article'
import store from '@/store'
export default {
  data() {
    return {
      teacherName: '',
      courseName: '',
      radio1: 10,
      radio2: 10,
      radio3: 10,
      radio4: 10,
      radio5: 10,
      radio6: 10,
      radio7: 10,
      radio8: 10,
      radio9: 10,
      radio10: 10,
      formParams: {
        countScore: 0,
        message: '',
        courseId: '',
        teacherId: '',
        studentId: ''
      }
    }
  },
  created() {
    this.teacherName = this.$route.query.teacherName
    this.courseName = this.$route.query.courseName
    this.formParams.teacherId = this.$route.query.teacherId
    this.formParams.studentId = store.getters.token
    this.formParams.courseId = this.courseName
    this.caculateCount()
  },
  methods: {
    caculateCount() {
      this.formParams.countScore = this.radio1 + this.radio2 + this.radio3 + this.radio4 + this.radio5 + this.radio6 + this.radio7 + this.radio8 + this.radio9 + this.radio10
    },
    radioValueChange() {
      this.caculateCount()
    },
    submitForm() {
      this.teacherName = this.$route.query.teacherName
      this.courseName = this.$route.query.courseName
      this.formParams.teacherId = this.$route.query.teacherId
      this.formParams.studentId = store.getters.token
      this.formParams.courseId = this.courseName
      this.caculateCount()
      startScore(this.formParams).then(response => {
        if (response.success) {
          this.$notify({
            title: '成功',
            message: '评教成功',
            type: 'success',
            duration: 2000
          })
        }
      })
    }
  }
}
</script>
<style>
  .app-container {
    background-color: #ff4d51;
  }
</style>
