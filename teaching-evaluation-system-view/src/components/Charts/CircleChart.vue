<template>
  <div :id="id" :class="className" :style="{height:height,width:width}" />
</template>

<script>
import echarts from 'echarts'
import resize from './mixins/resize'
import { queryProcessData } from '@/api/user'
export default {
  mixins: [resize],
  props: {
    className: {
      type: String,
      default: 'chart'
    },
    id: {
      type: String,
      default: 'chart'
    },
    width: {
      type: String,
      default: '200px'
    },
    height: {
      type: String,
      default: '200px'
    }
  },
  data() {
    return {
      chart: null,
      process: {
        complete: 0,
        unComplete: 0
      },
      dataObject: {
        title: {
          text: '',
          x: 'center',
          y: 'center',
          textStyle: {
            fontWeight: 'normal',
            color: '#0580f2',
            fontSize: '40'
          }
        },
        color: ['rgba(176, 212, 251, 1)'],
        legend: {
          show: true,
          itemGap: 12,
          data: ['已完成', '未完成']
        },

        series: {
          name: 'Line 1',
          type: 'pie',
          clockWise: true,
          radius: ['50%', '66%'],
          itemStyle: {
            normal: {
              label: {
                show: false
              },
              labelLine: {
                show: false
              }
            }
          },
          hoverAnimation: false,
          data: [{
            value: 80,
            name: '已完成',
            itemStyle: {
              normal: {
                color: { // 完成的圆环的颜色
                  colorStops: [{
                    offset: 0,
                    color: '#00cefc' // 0% 处的颜色
                  }, {
                    offset: 1,
                    color: '#367bec' // 100% 处的颜色
                  }]
                },
                label: {
                  show: false
                },
                labelLine: {
                  show: false
                }
              }
            }
          }, {
            name: '未完成',
            value: 20
          }]
        }
      }
    }
  },
  mounted() {
    this.initChart()
  },
  beforeDestroy() {
    if (!this.chart) {
      return
    }
    this.chart.dispose()
    this.chart = null
  },
  created() {
    queryProcessData().then(response => {
      this.process.complete = response.result.complete
      this.process.unComplete = response.result.unComplete
      this.dataObject.series.data[0].value = this.process.complete
      this.dataObject.title.text = '学生评教进度完成' + this.dataObject.series.data[0].value + '%'
      this.dataObject.series.data[1].value = this.process.unComplete
      this.chart = echarts.init(document.getElementById(this.id))
      this.chart.setOption(this.dataObject)
    })
  },
  methods: {
    initChart() {
    }
  }
}
</script>
