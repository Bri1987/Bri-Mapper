// index.ts
import axios from 'axios'
import type { AxiosInstance, AxiosRequestConfig } from 'axios'

class Request {
  // axios 实例
  instance: AxiosInstance

  constructor(config: AxiosRequestConfig) {
    this.instance = axios.create(config)
  }
  request(config: AxiosRequestConfig) {
    return this.instance.request(config)
  }
}

export default Request
// import Request from './request/request'
// import LocalCache from '../utils/cache'
// const myRequest = new Request({
//   baseURL: 'http://43.143.0.76:8889/api/private/v1',
//   timeout: 5000,
//   interceptors: {
//     requestInterceptor: (config: any) => {
//       const token = LocalCache.getCache('token')
//       if (token) {
//         config.headers.Authorization = `${token}`
//       }
//       return config
//     },
//     requestInterceptorCatch: (err) => {
//       return err
//     },
//     responseInterceptor: (res) => {
//       return res.data
//     },
//     responseInterceptorCatch: (err) => {
//       return err
//     }
//   }
// })

// export default myRequest
