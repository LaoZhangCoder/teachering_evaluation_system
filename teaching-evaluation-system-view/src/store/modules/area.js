import request from '@/utils/request'
import querystring from 'querystring'
export default {
  namespaced: true,
  state: {
    areas: []
    // name:[]
  },
  mutations: {
    refreshArea(state, areas) {
      state.areas = areas
    },
    refreshAreaname(state, areas) {
      state.areas = areas
    }
  },
  actions: {
    // 通过区域名称查询区域信息
    async searchAreas({ commit }, name) {
      const response = await request.get('/user/area/selectbyName' + '?name=' + name)
      // +'?name='+name
      commit('refreshAreaname', response.data)
    },

    // 通过id删除区域信息
    async deleteAreas({ dispatch }, params) {
      // console.log(params)
      const response = await request.get('/user/area/deleteById', { params })
      dispatch('findAllAreas')
    },

    // 查询所有的区域信息
    async findAllAreas(context) {
      // 通过后台接口获取response
      const response = await request.get('/user/area/findAll')
      // console.log(response)
      context.commit('refreshArea', response.data)
    },

    // 保存或更新区域信息
    async addOrUpdateArea({ dispatch }, form) {
      const url = '/user/area/addOrupdate'
      await request({
        url,
        method: 'POST',
        headers: {
          'Content-Type': 'application/x-www-form-urlencoded'
        },
        data: querystring.stringify(form)
      })
      dispatch('findAllAreas')
      // return resareaareaponse;
    }
  }
}
