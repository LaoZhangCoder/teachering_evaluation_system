import request from '@/utils/request'

export function searchUser(name) {
  return request({
    url: '/search/user',
    method: 'get',
    params: { name }
  })
}

export function transactionList(token) {
  return request({
    url: '/teacher/score/countList',
    method: 'get',
    params: { token }
  })
}
