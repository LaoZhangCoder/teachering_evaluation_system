<template>
  <el-row>
    <el-col>
      <el-card style="height:80px;width: 300px;float: left; margin-left:3%">
        <svg-icon icon-class="student" style="width: 100px;height: 100px;margin-top: -30px" />
        <div style="padding: 14px; float: right;">
          <span class="count">{{count.studentCount}}</span>
          <span style="font-size: 10px">学生数量</span>
        </div>
      </el-card>
      <el-card style="height:80px;width: 300px;float: left; margin-left:3%">
        <svg-icon icon-class="classmate" style="width: 100px;height: 100px;margin-top: -30px" />
        <div style="padding: 14px; float: right">
          <span class="count">{{count.classCount}}</span>
          <span style="font-size: 10px">班级数量</span>
        </div>
      </el-card>
      <el-card style="height:80px;width: 300px;float: left; margin-left:3%">
        <svg-icon icon-class="teacher" style="width: 100px;height: 100px;margin-top: -30px" />
        <div style="padding: 14px; float: right">
          <span class="count">{{count.teacherCount}}</span>
          <span style="font-size: 10px">教师数量</span>
        </div>
      </el-card>
      <el-card style="height:80px;width: 300px;float: left; margin-left:3%">
        <svg-icon icon-class="book" style="width: 100px;height: 100px;margin-top: -30px" />
        <div style="padding: 14px; float: right">
          <span class="count">{{count.courseCount}}</span>
          <span style="font-size: 10px">课程</span>
        </div>
      </el-card>
    </el-col>
    <ciclechart />
  </el-row>
</template>

<style>
  .count {
    font-weight: bold;
    display: block;
    margin-left: 25%;
  }
</style>
<script>
import ciclechart from '@/views/charts/ciclechart'
import { queryDataCount } from '@/api/user'
export default {
  name: 'AppMain',
  components: { ciclechart },
  data() {
    return {
      count: {
        studentCount: '',
        classCount: '',
        teacherCount: '',
        courseCount: ''
      }
    }
  },
  created() {
    queryDataCount().then(response => {
      this.count = response.result
    })
  }
}
</script>
