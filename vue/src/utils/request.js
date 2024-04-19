//定制请求的实例

//导入axios  npm install axios
import axios from 'axios';
import router from '@/router'
import { ElMessage, ElResult } from 'element-plus';
//定义一个变量,记录公共的前缀  ,  baseURL
//const baseURL = 'http://localhost:8080';
const baseURL = '/api';
const instance = axios.create({ baseURL })
//拦截器
import { useTokenStore } from '@/stores/token.js';
instance.interceptors.request.use(
    (config) => {
        //请求前的回调
        //添加token
        let tokenStore = useTokenStore();
        //判断是否存在token
        if (tokenStore.token) {
            config.headers.Authorization = tokenStore.token
        }
        return config
    },
    (err) => {
        //请求失败的回调
        Promise.reject(err);
    }
)


instance.interceptors.response.use(
    result => {
        //判断业务状态码
        if(result.data.code === 0){
            return result.data;
        }
        //失败
        // alert(res.data.msg ? res.data.msg : '服务异常')
        ElMessage.error(result.data.msg ? result.data.msg : '服务异常')
        //异步操作的状态转换为失败
        return Promise.reject(result.data)
    },
    err => {
        //判断状态码，如果是401跳转到登录页
        if(err.response.status===401){
            ElMessage.error('请先登录！')
            router.push('/login')
        }else{
            ElMessage.error('服务异常');
        }
        return Promise.reject(err);
    }
)
export default instance;
