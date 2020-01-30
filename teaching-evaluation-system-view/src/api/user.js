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
export function queryMajorByCondition(departmentId) {
  return request({
    url: '/major/list/condition',
    method: 'get',
    params: { departmentId }
  })
}
export function addMajor(data) {
  return request({
    url: '/major/add',
    method: 'post',
    data
  })
}
export function addClass(data) {
  return request({
    url: '/class/add',
    method: 'post',
    data
  })
}
export function updateMajor(data) {
  return request({
    url: '/major/update',
    method: 'post',
    data
  })
}
export function updateClass(data) {
  return request({
    url: '/class/update',
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
export function deleteMajor(majorId) {
  return request({
    url: '/major/delete',
    method: 'get',
    params: { majorId }
  })
}
export function deleteClass(classId) {
  return request({
    url: '/class/delete',
    method: 'get',
    params: { classId }
  })
}
export function queryDepartment() {
  return request({
    url: '/department/list',
    method: 'get'
  })
}
export function listClass() {
  return request({
    url: '/class/list',
    method: 'get'
  })
}
export function updateDepartment(newDepartmentName, oldDepartmentName, id) {
  return request({
    url: '/department/update',
    method: 'get',
    params: { newDepartmentName, oldDepartmentName, id }
  })
}
export function logout() {
  return request({
    url: '/sign-out',
    method: 'get'
  })
}
