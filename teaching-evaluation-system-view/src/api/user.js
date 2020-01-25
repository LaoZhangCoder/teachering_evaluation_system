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
export function listMajor() {
  return request({
    url: '/major/list',
    method: 'get'
  })
}
export function addMajor(data) {
  return request({
    url: '/major/add',
    method: 'post',
    data
  })
}
export function deleteDepartment(departmentName) {
  return request({
    url: '/department/delete',
    method: 'get',
    params: { departmentName }
  })
}
export function queryDepartment() {
  return request({
    url: '/department/list',
    method: 'get'
  })
}
export function updateDepartment(newDepartmentName, oldDepartmentName) {
  return request({
    url: '/department/update',
    method: 'get',
    params: { newDepartmentName, oldDepartmentName }
  })
}
export function logout() {
  return request({
    url: '/sign-out',
    method: 'get'
  })
}
