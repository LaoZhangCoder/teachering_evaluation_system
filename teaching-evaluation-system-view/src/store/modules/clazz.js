import request from '@/utils/request'
import querystring from 'querystring'
export default {
  namespaced: true,
  state: {
    clazzs: []
  },
  mutations: {
    refreshClazz(state, clazzs) {
      state.clazzs = clazzs
    }

  },
  actions: {
    // 查询所有
    async findAllClazzs({ commit }) {
      const response = await request.get('/user/Clazz/selectAll')
      commit('refreshClazz', response.data)
    }
  }
}
