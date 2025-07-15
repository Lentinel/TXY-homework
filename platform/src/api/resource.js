import request from '@/utils/request'

// 上传资源
export function uploadResource(data) {
  return request({
    url: '/api/admin/common/upload',
    method: 'post',
    headers: {
      'Content-Type': 'multipart/form-data'
    },
    data
  })
}

// 删除资源
export function deleteResource(id) {
  return request({
    url: `/api/resource/${id}`,
    method: 'delete'
  })
}

// 更新资源信息
export function updateResource(id, data) {
  return request({
    url: `/api/resource/${id}`,
    method: 'put',
    data
  })
}