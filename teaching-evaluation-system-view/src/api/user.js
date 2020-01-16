import request from '@/utils/request'
export function login(data) {
  return request({
    url: '/user/login',
    method: 'post',
    data
  })
}

export function getInfo(token) {
  return request({
    url: '/user/info',
    method: 'get',
    params: { token }
  })
}

export function addDepartment(departmentName) {
  return request({
    url: '/department/add',
    method: 'get',
    params: { departmentName }
  })
}

export function logout() {
  return request({
    url: '/sign-out',
    method: 'get'
  })
}
