<template>
  <div class="starry-sky">
    <canvas ref="canvas" style="position: absolute; width: 100%; height: 100%" />
    <div align="center" style="background-color: black">
      <h3 style="color:bisque;padding: 10px">综合得分总分:{{ this.formParams.countScore }}</h3>
      <h2 style="color:bisque; padding-bottom: 10px">课程名称：{{ this.courseName }}&nbsp;&nbsp; 任课老师：{{ this.teacherName }}</h2>
    </div>
    <el-table
      :data="tableData"
      style="width: 100%;padding: 10px 20%"
      :row-class-name="tableRowClassName"
    >
      <el-table-column
        prop="date"
        label="评教指标内容"
        width="380"
      />
      <el-table-column
        prop="name"
        label="得分"
        width="380"
      />
      <el-table-column
        prop="tag"
        label="打分"
        width="380"
      >
        <template slot-scope="scope">
          <el-rate
            v-model="scope.row.score"
            show-text
            @change="scoreHandle($event, scope.$index)"
          />

        </template>
      </el-table-column>
    </el-table>
    <div align="center" style="position: absolute;margin-left: 40%">
      <el-button type="primary" @click="submitForm()">提交</el-button>
      <el-button @click="resetForm()">返回</el-button>
    </div>
  </div>

</template>
<script>
import { startScore } from '@/api/article'
import store from '@/store'
export default {
  props: {
    point: {
      type: Number,
      default: 105 // 生成的星星（点）的个数, 默认25个
    },
    lineColor: {
      type: String,
      default: 'rgba(45,140,210,0.2)' // 线的颜色
    },
    roundColor: {
      type: String,
      default: 'red' // 星星的颜色
    }
  },
  data() {
    return {
      docWidth: 0, // 画布宽
      docHeight: 0, // 画布高
      context: null, // canvasDom的执行上下文
      circleArr: [], // 圆点数组
      timer: null, // 定时器对象
      teacherName: '',
      courseName: '',
      radio1: 0,
      radio2: 0,
      radio3: 0,
      radio4: 0,
      radio5: 0,
      radio6: 0,
      radio7: 0,
      radio8: 0,
      radio9: 0,
      radio10: 0,
      formParams: {
        countScore: 0,
        message: '',
        courseId: '',
        teacherId: '',
        studentId: ''
      },
      tableData: [{
        date: '有明确的教学目标与计划，教学进度安排得当',
        name: this.radio1,
        score: this.radio1
      }, {
        date: '授课内容有深度，学生能获得大量有价值的课程信息',
        name: this.radio2,
        score: this.radio2
      }, {
        date: '教学内容熟练，脱稿讲授',
        name: this.radio3,
        score: this.radio3
      }, {
        date: '讲课思路清晰，重点突出，难点处理得当',
        name: this.radio4,
        score: this.radio4
      }, {
        date: '讲课具有启发性，能引导学生独立分析与思考',
        name: this.radio5,
        score: this.radio5
      }, {
        date: '讲课理论联系实际，能调动学生听课的积极性，进而引发学生对本课程的兴趣',
        name: this.radio6,
        score: this.radio6
      }, {
        date: '注意精讲，适当练习，布置大作业，并及时认真批改',
        name: this.radio7,
        score: this.radio7
      }, {
        date: '大作业联系实际，有利于课程内容的学习',
        name: this.radio8,
        score: this.radio8
      }, {
        date: '开学初即向学生明确宣布累加式考核及评价方法',
        name: this.radio9,
        score: this.radio9
      }, {
        date: '通过该课学习，学生能掌握本课程的知识并获得相应的能力提高',
        name: this.radio10,
        score: this.radio10
      }
      ]
    }
  },
  mounted() {
    const canvasDom = this.$refs.canvas

    // 取画布的高宽来设置显示分辨率
    this.docWidth = canvasDom.offsetWidth
    this.docHeight = canvasDom.offsetHeight

    // 设置画布分辨率
    canvasDom.width = canvasDom.offsetWidth
    canvasDom.height = canvasDom.offsetHeight

    // 初始化canvas上下文
    this.context = canvasDom.getContext('2d')

    // 设置线条和星星颜色
    this.context.strokeStyle = this.lineColor
    this.context.lineWidth = 1
    this.context.fillStyle = this.roundColor

    // 初始化
    this.createCircleArr() // 设置星星数组
    this.draw() // 首屏绘制
    this.cycleDraw() // 循环绘制
  },
  created() {
    this.teacherName = this.$route.query.teacherName
    this.courseName = this.$route.query.courseName
    this.formParams.teacherId = this.$route.query.teacherId
    this.formParams.studentId = store.getters.token
    this.formParams.courseId = this.courseName
    this.caculateCount()
  },
  // eslint-disable-next-line no-dupe-keys
  beforeDestroy() {
    this.beforeDestory()
  },
  methods: {
    caculateCount() {
      this.formParams.countScore = (this.radio1 + this.radio2 + this.radio3 + this.radio4 + this.radio5 + this.radio6 + this.radio7 + this.radio8 + this.radio9 + this.radio10) * 2
    },
    /**
     * 生成min和max之间随机数
     */
    rangeRadom(max, min) {
      return Math.floor(Math.random() * (max - min + 1) + min)
    },
    /**
     * 绘制原点
     */
    drawCircle(context, x, y, r, moveX, moveY) {
      const circle = {
        x,
        y,
        r,
        moveX,
        moveY
      }
      context.beginPath()
      context.arc(circle.x, circle.y, circle.r, 0, 2 * Math.PI)
      context.closePath()
      context.fill()
      return circle
    },
    /**
     * 绘制线条
     */
    drawLine(context, beginX, beginY, closeX, closeY, opacity) {
      context.beginPath()
      context.strokeStyle = `rgba(0, 0, 0, ${opacity})`
      context.moveTo(beginX, beginY)
      context.lineTo(closeX, closeY)
      context.closePath()
      context.stroke()
    },
    /**
     * 生成圆点数组
     */
    createCircleArr() {
      for (let i = 0; i < this.point; i++) {
        this.circleArr.push(this.drawCircle(
          this.context,
          this.rangeRadom(this.docWidth, 0),
          this.rangeRadom(this.docHeight, 0),
          this.rangeRadom(15, 2),
          this.rangeRadom(10, -10) / 40,
          this.rangeRadom(10, -10) / 40
        ))
      }
    },
    /**
     * 每一帧绘制
     */
    draw() {
      const circleArr = this.circleArr
      this.context.clearRect(0, 0, this.docWidth, this.docHeight)
      // 循环绘制点
      for (let i = 0; i < this.point; i++) {
        this.drawCircle(this.context, circleArr[i].x, circleArr[i].y, circleArr[i].r)
      }
      // 循环绘制线
      for (let i = 0; i < this.point; i++) {
        for (let j = 0; j < this.point; j++) {
          if (i + j < this.point) {
            const A = Math.abs(circleArr[i + j].x - circleArr[i].x)
            const B = Math.abs(circleArr[i + j].y - circleArr[i].y)
            const lineLength = Math.sqrt(A * A + B * B)
            const C = 1 / lineLength * 7 - 0.009
            const lineOpacity = C > 0.03 ? 0.03 : C
            if (lineOpacity > 0) {
              this.drawLine(
                this.context,
                circleArr[i].x,
                circleArr[i].y,
                circleArr[i + j].x,
                circleArr[i + j].y,
                lineOpacity,
              )
            }
          }
        }
      }
    },
    /**
     * 循环绘制
     */
    cycleDraw() {
      this.timer = setInterval(() => {
        for (let i = 0; i < this.point; i++) {
          const cir = this.circleArr[i]
          cir.x += cir.moveX
          cir.y += cir.moveY
          if (cir.x > this.docWidth) {
            cir.x = 0
          } else if (cir.x < 0) {
            cir.x = this.docWidth
          }
          if (cir.y > this.docHeight) {
            cir.y = 0
          } else if (cir.y < 0) {
            cir.y = this.docHeight
          }
        }
        this.draw()
      }, 10)
    },
    beforeDestory() {
    // 记得摧毁定时器
      clearInterval(this.timer)
    },
    scoreHandle(value, index) {
      this.tableData[index].name = value * 2
      if (index === 0) {
        this.radio1 = value
      } else if (index === 1) {
        this.radio2 = value
      } else if (index === 2) {
        this.radio3 = value
      } else if (index === 3) {
        this.radio4 = value
      } else if (index === 4) {
        this.radio5 = value
      } else if (index === 5) {
        this.radio6 = value
      } else if (index === 6) {
        this.radio7 = value
      } else if (index === 7) {
        this.radio8 = value
      } else if (index === 8) {
        this.radio9 = value
      } else if (index === 9) {
        this.radio10 = value
      }
      this.caculateCount()
    },
    radioValueChange() {
      this.caculateCount()
    },
    tableRowClassName({ row, rowIndex }) {
      if (rowIndex === 1) {
        return 'warning-row'
      } else if (rowIndex === 3) {
        return 'success-row'
      }
      return ''
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
      this.$router.push('/student/score')
    },
    resetForm() {
      this.$router.push('/student/score')
    }
  }
}
</script>
<style>
  .starry-sky {
    width: 100%;
    height: 100%;
  }
  > canvas {
    width: 100%;
    height: 100%;
  }
  .el-table .warning-row {
    background: oldlace;
  }
  .el-table .success-row {
    background: #f0f9eb;
  }
</style>
