<template>
  <el-row>
    <el-col>
      <el-card style="height:80px;width: 300px;float: left; margin-left:3%">
        <svg-icon icon-class="student" style="width: 100px;height: 100px;margin-top: -30px" />
        <div style="padding: 14px; float: right;">
          <span class="count">263</span>
          <span style="font-size: 10px">学生数量</span>
        </div>
      </el-card>
      <el-card style="height:80px;width: 300px;float: left; margin-left:3%">
        <svg-icon icon-class="classmate" style="width: 100px;height: 100px;margin-top: -30px" />
        <div style="padding: 14px; float: right">
          <span class="count">9</span>
          <span style="font-size: 10px">班级数量</span>
        </div>
      </el-card>
      <el-card style="height:80px;width: 300px;float: left; margin-left:3%">
        <svg-icon icon-class="teacher" style="width: 100px;height: 100px;margin-top: -30px" />
        <div style="padding: 14px; float: right">
          <span class="count">15</span>
          <span style="font-size: 10px">教师数量</span>
        </div>
      </el-card>
      <el-card style="height:80px;width: 300px;float: left; margin-left:3%">
        <svg-icon icon-class="book" style="width: 100px;height: 100px;margin-top: -30px" />
        <div style="padding: 14px; float: right">
          <span class="count">15</span>
          <span style="font-size: 10px">科目数量</span>
        </div>
      </el-card>
    </el-col>
       <ciclechart></ciclechart>
  </el-row>
</template>

<style>
  .count{
    font-weight: bold;
    display: block;
    margin-left: 25%;
  }
</style>
<script>
import ciclechart from '@/views/charts/ciclechart'
export default {
  name: 'AppMain',
  components: { ciclechart }
}
</script>
